package com.bootcamp.portal.web.model;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.web.EditObjectForm;

//TODO won't be used here?
public abstract class EditChildForm extends EditObjectForm {

	final static String FORM_ACTION_FOR_PARENT = "%s/edit/%s/%s";
	final static String FORM_ACTION = "%s";

	private long parentId;
	private String parentName;
	private String parentClassName;
	private String linkPrefix;

	public EditChildForm(AbstractEntity entity) {
		super(entity);
		updateTitle();
	}

	public void updateTitle() {
	}

	public long getParentId() {
		return this.parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
		updateTitle();
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
		updateTitle();
	}

	public String getDomainName() {
		return getObject().getClass().getSimpleName();
	}

	public String getParentClassName() {
		return parentClassName;
	}

	public void setParentClassName(String parentClassName) {
		this.parentClassName = parentClassName;
		updateTitle();
	}

	public String getLinkPrefix() {
		return linkPrefix;
	}

	public void setLinkPrefix(String linkPreffix) {
		this.linkPrefix = linkPreffix;
		updateTitle();
	}
}