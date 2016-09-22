package com.bootcamp.portal.web.controller.adminapp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.mgr.ChildManager;
import com.bootcamp.portal.mgr.ManagerFactory;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectParentRemovedException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.bootcamp.portal.utils.PackageUtils;
import com.bootcamp.portal.web.AuthenticatedUser;
import com.bootcamp.portal.web.EditObjectForm;
import com.bootcamp.portal.web.model.EditChildForm;

public class ChildObjectController extends BaseEntityController {
	final private static String URL_PRODUCER_CHILD_TAB_REDIRECT = "redirect:/%s/edit/%s/#Tab%s-tab";

	final private static String VIEW_OBJECT_EDIT_FORM = "edit/%s";
	// final private static String VIEW_AJAX_OBJECT_LIST = "std/ajax-grid";

	private long parentId;
	private Class<? extends AbstractEntity> parentClass;
	private String parentRemovedUrl;
	private String object;

	public ChildObjectController(String object,
			Class<? extends AbstractEntity> parentClass, long producerId,
			HttpServletRequest request) {
		super(getClassByName(object), null, null, null, request);
		this.mgr = ManagerFactory.getChildManager();
		this.object = object;
		this.parentId = producerId;
		this.parentClass = parentClass;
		this.editObjectUrl = getEditObjectUrl();
		updateRedirectToListUrl();
	}

	protected String getObject() {
		return this.object;
	}

	protected String getEditObjectUrl() {
		return String.format(VIEW_OBJECT_EDIT_FORM, object.toLowerCase());
	}

	private static Class<AbstractEntity> getClassByName(String name) {
		return PackageUtils.findEntityClassByName(name);
	}

	@Override
	protected String getParentRemovedRedirectUrl() {
		return StringUtils.defaultString(parentRemovedUrl,
				super.getParentRemovedRedirectUrl());
	}

	protected String getRedirectUrl(String name) {
		Class<?> c = getClassByName(name);
		return String.format(URL_PRODUCER_CHILD_TAB_REDIRECT,
				parentClass.getSimpleName(), parentId, c.getSimpleName());
	}

	protected ChildManager getChildManager() {
		return (ChildManager) mgr;
	}

	public String getParentRemovedUrl() {
		return parentRemovedUrl;
	}

	public void setParentRemovedUrl(String parentRemovedUrl) {
		this.parentRemovedUrl = parentRemovedUrl;
	}

	@Override
	protected void updateObject(EditObjectForm frm)
			throws BaseSaveObjectException {
		final EditChildForm form = (EditChildForm) frm;
		AbstractEntity parent = null;
		try {
			parent = getChildManager().entityById(parentClass,
					form.getParentId());
		} catch (ObjectRemovedException ex) {
			afterExceptionOnSaveObject(form);
			throw new ObjectParentRemovedException(String.format(
					BaseManager.ERROR_OBJECT_ALREADY_REMOVED,
					parentClass.getSimpleName()));
		}
		AbstractEntity e = getFormObjectForUpdate(form, parent);
		getChildManager().saveObject(e, null, parent);
		afterSaveObject(e, form);
	}

	protected void afterSaveObject(AbstractEntity e, EditObjectForm form) {
	}

	protected void afterExceptionOnSaveObject(EditObjectForm form) {
	}

	protected AbstractEntity getFormObjectForUpdate(EditObjectForm frm,
			AbstractEntity parent) throws BaseSaveObjectException {
		return frm.getObject();
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	private void updateRedirectToListUrl() {
		this.redirectToListUrl = getRedirectUrl(this.object);
	}

	@Override
	protected void validateEntity(AbstractEntity o)
			throws ObjectRemovedException {

	}

	public Class<? extends AbstractEntity> getParentClass() {
		return parentClass;
	}

	public AuthenticatedUser getUser() {
		return user;
	}
}