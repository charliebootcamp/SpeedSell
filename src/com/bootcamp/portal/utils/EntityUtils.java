package com.bootcamp.portal.utils;

import java.util.Iterator;
import java.util.Set;

import com.bootcamp.portal.domain.AbstractEntity;

public class EntityUtils {

	static public <T extends AbstractEntity> T getFirst(Set<T> entities) {
		if (entities == null)
			return null;
		Iterator<T> i = entities.iterator();
		while (i.hasNext()) {
			T e = i.next();
			return e;
		}
		return null;
	}

	static public <T extends AbstractEntity> T getByName(Set<T> entities,
			String name) {
		if (entities == null || name == null)
			return null;

		Iterator<T> i = entities.iterator();
		while (i.hasNext()) {
			T e = i.next();
			if (name.equals(e.getName())) {
				return e;
			}
			entities.iterator().next();
		}
		return null;
	}
}