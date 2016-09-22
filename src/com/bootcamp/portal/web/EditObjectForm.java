package com.bootcamp.portal.web;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.web.model.UserMessage;

public class EditObjectForm {
	private AbstractEntity object;
	private String title;
	private String entityName;
	private UserMessage message;
	private String redirectUrl;
	private boolean saveandattach;
	private String initUrl;
	private String refererUrl;
	private String activeTab;
	private boolean showOtherTabs = true;

	public EditObjectForm(AbstractEntity object, String title) {
		this.object = object;
		this.entityName = object.getClass().getSimpleName();
		this.title = title;
	}

	public EditObjectForm(AbstractEntity object) {
		this(object, null);
	}

	public AbstractEntity getObject() {
		return object;
	}

	public void setObject(AbstractEntity object) {
		this.object = object;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public UserMessage getMessage() {
		return message;
	}

	public void setMessage(UserMessage message) {
		this.message = message;
	}

	public boolean isNew() {
		return object.getId() == null;
	}

	public void updateTitle() {
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public boolean isSaveandattach() {
		return saveandattach;
	}

	public void setSaveandattach(boolean saveandattach) {
		this.saveandattach = saveandattach;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public boolean isShowOtherTabs() {
		return showOtherTabs;
	}

	public void setShowOtherTabs(boolean showOtherTabs) {
		this.showOtherTabs = showOtherTabs;
	}
}
