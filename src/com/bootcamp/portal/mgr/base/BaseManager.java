package com.bootcamp.portal.mgr.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.NonUniqueException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.bootcamp.portal.utils.PackageUtils;
import com.bootcamp.portal.utils.TextUtil;

public class BaseManager {
	public final static String ERROR_OBJECT_ALREADY_REMOVED = "Cannot save changes. %s has already been removed";

	protected static Logger LOGGER = Logger.getLogger(BaseManager.class);

	static public abstract class DataRowPacker<T extends AbstractEntity> {
		protected abstract void pack(Object[] fields, T object);

		public void packObject(Object[] fields, T object) {
			fields[0] = object.getId().toString();
			pack(fields, object);
		}
	}

	@Autowired
	protected SessionFactory sessionFactory;

	public Session getCurrentSession() {
			return sessionFactory.getCurrentSession();
	}

	@Transactional(readOnly = true)
	public <T> T entityByIdEager(Class<T> entity, Long id)
			throws ObjectRemovedException {
		return entityById(entity, id,
				findAllEagerFields(entity).toArray(new String[0]));
	}

	@Transactional(readOnly = true)
	public <T> T entityByCriteriaEager(Class<T> entity, Criterion criteria)
			throws ObjectRemovedException {
		return entityByCriteria(entity, criteria, findAllEagerFields(entity)
				.toArray(new String[0]));
	}

	@Transactional(readOnly = true)
	public <T> T entityByIdEagerRecursive(Class<T> entity, Long id)
			throws ObjectRemovedException {
		return entityById(entity, id,
				findAllEagerFields(entity, true).toArray(new String[0]));
	}

	@Transactional(readOnly = true)
	public <T> T entityById(Class<T> entity, Long id, String... eagerColumns)
			throws ObjectRemovedException {
		return entityByCriteria(entity, Restrictions.eq("id", id), eagerColumns);
	}

	@Transactional(readOnly = true)
	public <T> T entityByCriteria(Class<T> entity, Criterion crit,
			String... eagerColumns) throws ObjectRemovedException {
		return entityByCriteria(entity, crit, Order.asc("id"), true,
				eagerColumns);
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public <T> T entityByCriteria(Class<T> entity, Criterion crit, Order order,
			boolean useLazyInitialization, String... eagerColumns)
			throws ObjectRemovedException {
		Set<String> joinColumns = new HashSet<>();
		Set<String> initColumns = new HashSet<>();
		TextUtil.parseEagerColumns(eagerColumns, joinColumns, initColumns);
		Criteria c = listCriteria(
				entity,
				useLazyInitialization ? new ArrayList<>(joinColumns) : Arrays
						.asList(eagerColumns), order).add(crit);

		@SuppressWarnings("unchecked")
		T e = (T) c.uniqueResult();
		if (e == null) {
			throw new ObjectRemovedException(String.format(
					ERROR_OBJECT_ALREADY_REMOVED, entity.getSimpleName()));
		}

		for (String f : initColumns) {
			try {
				Method m = entity.getMethod("get"
						+ TextUtil.firstLetterUpper(f));
				if (m != null) {
					Hibernate.initialize(m.invoke(e));
				}
			} catch (NoSuchMethodException | SecurityException
					| HibernateException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e1) {
				LOGGER.error("Reflection error", e1);
			}
		}
		return e;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> void saveOrUpdate(List<T> notSavingList)
			throws BaseSaveObjectException {
		saveOrUpdate(null, (List<AbstractEntity>) notSavingList, null);
	}

	public void saveOrUpdate(AbstractEntity d,
			List<AbstractEntity> entitiesForDelete)
			throws BaseSaveObjectException {
		saveOrUpdate(d, null, entitiesForDelete);
	}

	public <T extends AbstractEntity> void saveOrUpdate(T d,
			List<T> objectList, List<T> entitiesForDelete)
			throws BaseSaveObjectException {
		Session ses = sessionFactory.openSession();
		Transaction tx = ses.beginTransaction();
		boolean isNew = d == null ? false : d.getId() == null;
		try {

			if (entitiesForDelete != null) {
				for (AbstractEntity e : entitiesForDelete) {
					ses.delete(e);
				}
			}
			if (d != null) {
				ses.saveOrUpdate(d);
			}

			if (objectList != null) {
				for (AbstractEntity e : objectList) {
					ses.saveOrUpdate(e);
				}
			}
			tx.commit();
		} catch (ConstraintViolationException ex) {
			if (isNew) {
				d.setId(null);
			}
			String message = d.getClass().getSimpleName();
			tx.rollback();
			throw new NonUniqueException(message);
		} catch (StaleStateException ex) {
			tx.rollback();
			throw new ObjectRemovedException(String.format(
					ERROR_OBJECT_ALREADY_REMOVED, d.getClass().getSimpleName()));
		} catch (RuntimeException ex) {
			tx.rollback();
			throw ex;
		} finally {
			ses.close();
		}
	}

	public void saveOrUpdate(AbstractEntity d) throws BaseSaveObjectException {
		saveOrUpdate(d, null);
	}

	public Criteria listCriteria(Class<?> entity, List<String> eagerColumns) {
		return listCriteria(entity, eagerColumns, null);
	}

	public Criteria listCriteria(Class<?> entity, List<String> eagerColumns,
			Order order) {
		Criteria cri = getCurrentSession().createCriteria(entity,
				entity.getSimpleName());
		if (eagerColumns != null) {
			for (String item : eagerColumns) {
				cri.createAlias(item, item, JoinType.LEFT_OUTER_JOIN);
			}
		}
		if (order != null) {
			cri.addOrder(order);
		}
		cri.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return cri;
	}

	public Criteria byId(Class<?> entity, Long id, List<String> eagerColumns) {
		Criteria cri = listCriteria(entity, eagerColumns, null).add(
				Restrictions.eq("id", id));
		return cri;
	}

	public List<String> findAllEagerFields(Class<?> entity) {
		return findAllEagerFields(entity, false);
	}

	public List<String> findAllEagerFields(Class<?> entity,
			boolean eagerRecursively) {
		return PackageUtils.findAllEagerFields(entity, eagerRecursively);
	}

	@Transactional(readOnly = true)
	public boolean checkEntityExist(Class<?> entity, Long id) {
		return byId(entity, id, null).uniqueResult() != null;
	}

	@Transactional(readOnly = true)
	public <T> List<T> objectList(Class<T> clazz, Criterion restrictions,
			String... eagerFields) {
		return objectList(clazz, restrictions, null,
				eagerFields != null ? Arrays.asList(eagerFields) : null,
				Order.asc("id"));
	}

	@Transactional(readOnly = true)
	public <T> List<T> objectList(Class<T> clazz, Criterion restrictions,
			Integer maxItemCount, String... eagerFields) {
		return objectList(clazz, restrictions, maxItemCount,
				Arrays.asList(eagerFields), Order.asc("id"));
	}

	@Transactional(readOnly = true)
	public <T> List<T> objectList(Class<T> clazz, Criterion restrictions,
			Order order, String... eagerFields) {
		return objectList(clazz, restrictions, null,
				Arrays.asList(eagerFields), order);
	}

	@Transactional(readOnly = true)
	public <T> List<T> objectList(Class<T> clazz, Criterion restrictions,
			Integer maxItemCount, List<String> eagerFields, Order order) {
		return objectList(clazz, restrictions, maxItemCount, eagerFields,
				order, null, null);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> objectList(Class<T> clazz, Criterion restrictions,
			Integer maxItemCount, List<String> eagerFields, Order order,
			Projection projection, ResultTransformer resultTransformer) {
		Criteria c = listCriteria(clazz, eagerFields, order);
		if (restrictions != null) {
			c.add(restrictions);
		}
		if (projection != null) {
			c.setProjection(projection);
		}
		if (resultTransformer != null) {
			c.setResultTransformer(resultTransformer);
		}
		if (maxItemCount != null) {
			c.setMaxResults(maxItemCount);
		}

		return c.list();
	}

	@Transactional(readOnly = true)
	public <T extends AbstractEntity> List<T> listForCombobox(Class<T> clazz,
			boolean emptyFirst, Criterion restriction) throws RuntimeException {
		List<T> list = new ArrayList<>();
		try {
			if (emptyFirst) {
				T e = (T) clazz.newInstance();
				e.setId(0L);
				list.add(e);
			}
			for (T o : objectList(clazz, restriction, null, null,
					Order.asc("name"))) {
				list.add(o);
			}
		} catch (InstantiationException | IllegalAccessException e1) {
			throw new RuntimeException(e1.getMessage());
		}
		return list;
	}

	public void hardDelete(Collection<? extends AbstractEntity> entities)
			throws BaseSaveObjectException {
		Session ses = sessionFactory.openSession();
		Transaction tx = ses.beginTransaction();
		try {
			for (AbstractEntity d : entities) {
				ses.delete(d);
			}
			tx.commit();
		} catch (StaleStateException ex) {
			tx.rollback();
			throw new ObjectRemovedException(String.format(
					ERROR_OBJECT_ALREADY_REMOVED, "FIXME"));
		} catch (RuntimeException ex) {
			tx.rollback();
			throw ex;
		} finally {
			ses.close();
		}
	}

	public void hardDeleteObject(AbstractEntity entity)
			throws BaseSaveObjectException {
		Set<AbstractEntity> s = new HashSet<>();
		s.add(entity);
		hardDelete(s);
	}

	@Transactional(readOnly = true)
	public long getCount(Class<? extends AbstractEntity> clazz,
			Criterion restriction, List<String> eagerColumns) {
		long result = 0;
		Criteria c = listCriteria(clazz, eagerColumns, null);
		if (restriction != null) {
			c.add(restriction);
		}
		c.setProjection(Projections.projectionList().add(
				Projections.count("id")));
		List<?> r = c.list();
		if (r != null && r.size() == 1) {
			result = (long) (r.get(0) == null ? 0 : r.get(0));
		}
		return result;
	}

}