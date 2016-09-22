package com.bootcamp.portal.domain;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = -6737145219436362384L;

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String getName();

	public AbstractEntity() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		Long id = getId();
		if (id == null)
			return false;

		AbstractEntity other = (AbstractEntity) obj;
		return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		Long id = getId();
		if (id != null)
			return id.hashCode();

		return super.hashCode();
	}
}
