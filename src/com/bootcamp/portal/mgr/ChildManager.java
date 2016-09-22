package com.bootcamp.portal.mgr;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChildManager extends BaseManager {

	private String getPropertyStartWith(List<String> propertyList,
			String preffix) {
		for (String prop : propertyList) {
			if (prop.toLowerCase().startsWith(preffix.toLowerCase())) {
				return prop;
			}
		}
		return null;
	}

	private Criterion getCriterion4ChildList(
			Class<? extends AbstractEntity> parentClass, Class<?> clazz,
			Long parentId) throws ObjectRemovedException {
		if (!checkEntityExist(parentClass, parentId)) {
			throw new ObjectRemovedException(String.format(
					BaseManager.ERROR_OBJECT_ALREADY_REMOVED,
					parentClass.getSimpleName()));
		}
		// search parent "property" name for forming criteria
		String parentField = getPropertyStartWith(findAllEagerFields(clazz),
				parentClass.getSimpleName());
		return Restrictions.eq(parentField.concat(".id"), parentId);
	}

	@Transactional(readOnly = true)
	public <T> List<T> childList(Class<? extends AbstractEntity> parentClass,
			Class<T> clazz, Long parentId, Criterion restriction, Order order)
			throws ObjectRemovedException {
		Criterion r = getCriterion4ChildList(parentClass, clazz, parentId);
		if (restriction != null) {
			r = Restrictions.and(restriction, r);
		}
		return objectList(clazz, r, null, findAllEagerFields(clazz), order);
	}

	@Transactional(readOnly = true)
	public <T> List<T> childList(Class<? extends AbstractEntity> parentClass,
			Class<T> clazz, Long parentId, Criterion restriction)
			throws ObjectRemovedException {
		return childList(parentClass, clazz, parentId, restriction, null);
	}

	@Transactional
	public void saveObject(AbstractEntity entity, List<String> eagerField,
			AbstractEntity parent) throws BaseSaveObjectException {
		if (entity == null)
			return;

		getCurrentSession().evict(parent);
		saveOrUpdate(entity);
	}
}