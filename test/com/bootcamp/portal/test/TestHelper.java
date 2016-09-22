package com.bootcamp.portal.test;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.utils.PackageUtils;
import com.bootcamp.portal.utils.TextUtil;

@Component
public class TestHelper {
	@Autowired
	private SessionFactory sessionFactory;

	// classes for cleaning. Order is important!
	private static Class<?>[] classesToCleanUp = {};

	private static String clearDBSQL;

	static {
		List<Class<?>> classesToDelete = Arrays.asList(classesToCleanUp);
		List<Class<?>> allClasses = PackageUtils.findClassesByAnnotation(
				AbstractEntity.class, Entity.class);
		for (Class<?> c : allClasses) {
			if (Modifier.isAbstract(c.getModifiers())
					|| c.getAnnotation(DiscriminatorValue.class) == null) {
				// should be in the list for deleting
				if (!classesToDelete.contains(c)) {
					throw new RuntimeException(
							String.format(
									"Entity Class %s is not listed in the list for cleaning! Could you be so kind to fix it?",
									c));
				}
			}
		}
		constructClearDBSQL();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static void constructClearDBSQL() {
		List<String> tables = new ArrayList<>();
		for (Class<?> c : classesToCleanUp) {
			tables.add(c.getSimpleName().toLowerCase());
		}
		String param = "";
		for (String tableName : tables) {
			if (!StringUtils.isEmpty(param)) {
				param += ",";
			}
			param += "'" + tableName + "'";
		}
		clearDBSQL = "SELECT cleardb(ARRAY[" + param + "]);";
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void cleanupDb() {
		try {
			sessionFactory.getCurrentSession().createSQLQuery(clearDBSQL)
					.uniqueResult();
		} catch (Exception e) {
			// OK
		}
		sessionFactory.getCurrentSession().flush();
	}

	@Transactional
	public void saveOrUpdate(Object obj) {
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
	}

	@Transactional(readOnly = true)
	public int count(Class<?> entityClass) {
		return count(entityClass, null);
	}

	@Transactional(readOnly = true)
	public int count(Class<?> entityClass, Boolean active) {
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(
				entityClass);
		if (active != null) {
			cri.add(Restrictions.eq("active", active));
		}
		Number n = (Number) cri.setProjection(Projections.rowCount())
				.uniqueResult();
		return n.intValue();
	}

	@Transactional
	public void delete(AbstractEntity entity) {
		String hql = String.format("delete from %s where id=%d", entity
				.getClass().getSimpleName(), entity.getId());
		sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
		sessionFactory.getCurrentSession().flush();
	}

	@Transactional
	public void update(AbstractEntity entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	public String generateBucketName() {
		return TextUtil.createFileName("test").replace("#", "-");
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> T byId(Class<T> clazz, Long id,
			String... eagerFields) {
		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(clazz);
		for (String eagerField : eagerFields) {
			crit.createAlias(eagerField, eagerField, JoinType.LEFT_OUTER_JOIN);
		}
		return (T) crit.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> T byName(Class<T> clazz,
			String nameField, String name, String... eagerFields) {
		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(clazz);
		for (String eagerField : eagerFields) {
			crit.createAlias(eagerField, eagerField, JoinType.LEFT_OUTER_JOIN);
		}
		return (T) crit.add(Restrictions.eq(nameField, name)).uniqueResult();
	}

}