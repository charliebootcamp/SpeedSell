package com.bootcamp.portal.web.controller.adminapp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootcamp.portal.domain.AbstractEntity;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectParentRemovedException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.bootcamp.portal.utils.ModelUtils;
import com.bootcamp.portal.utils.TextUtil;
import com.bootcamp.portal.web.AuthenticatedUser;
import com.bootcamp.portal.web.EditObjectForm;
import com.bootcamp.portal.web.WebConfig;
import com.bootcamp.portal.web.model.UserMessage;

public class BaseEntityController {
	final public static String VIEW_BASIC_GRID = "std/basic-grid";

	protected Class<? extends AbstractEntity> entityClass;
	protected Class<? extends EditObjectForm> editFormClass;

	protected BaseManager mgr;
	protected String redirectToListUrl;
	protected String editObjectUrl;
	protected AuthenticatedUser user;
	// has sense for add, edit operation only for now
	private String initUrl;
	protected boolean useRefererUrl;
	private HttpServletRequest request;

	public BaseEntityController(Class<? extends AbstractEntity> entityClass,
			Class<? extends EditObjectForm> editFormClass,
			String redirectToListUrl, String editObjectUrl,
			HttpServletRequest request) {
		this.entityClass = entityClass;
		this.editFormClass = editFormClass;
		this.redirectToListUrl = redirectToListUrl;
		this.editObjectUrl = editObjectUrl;
		this.request = request;
		if (request != null) {
			user = WebConfig.getCurrentUser(request.getSession());
			setInitUrl(request.getRequestURI());
		}
	}

	protected void addAdditionAttributes(Model model) {
	}

	protected String listViewUrl() {
		return VIEW_BASIC_GRID;
	}

	protected String getParentRemovedRedirectUrl() {
		return listViewUrl();
	}

	public String add(Model model) {
		return add(model, null);
	}

	public String add(Model model, final RedirectAttributes redirectAttrs) {
		if (!ModelUtils.hasModel(model)) {
			try {
				AbstractEntity o = (AbstractEntity) entityClass.newInstance();
				setupNewObjectInstance(o);
				addEditFormToModel(model, o);
			} catch (InstantiationException | IllegalAccessException
					| SecurityException | IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (BaseSaveObjectException ex) {
				ModelUtils.msg(redirectAttrs, ex);
				return redirectToListUrl;
			}
		}
		return editObjectUrl;
	}

	public String edit(long id, Model model,
			final RedirectAttributes redirectAttrs) {
		try {
			// has model - if is redirect
			if (!ModelUtils.hasModel(model)) {
				AbstractEntity o = getEntityForEdit(id, entityClass);
				validateEntity(o);
				addEditFormToModel(model, o);
			}
			return editObjectUrl;
		} catch (BaseSaveObjectException ex) {
			ModelUtils.msg(redirectAttrs, ex);
			return getRedirectFromEdit();
		}
	}

	protected String getRedirectFromEdit() {
		return redirectToListUrl;
	}

	protected AbstractEntity getEntityForEdit(Long id,
			Class<? extends AbstractEntity> entityClass)
			throws ObjectRemovedException {
		return mgr.entityByIdEager(entityClass, id);
	}

	protected void validateEntity(AbstractEntity o)
			throws ObjectRemovedException {
	}

	private void addEditFormToModel(Model model, AbstractEntity o)
			throws BaseSaveObjectException {
		try {
			Constructor<? extends EditObjectForm> cnstr = editFormClass
					.getConstructor(o.getClass());
			EditObjectForm frm = cnstr.newInstance(o);
			frm.setRedirectUrl(redirectToListUrl.replace("redirect:", ""));
			frm.setInitUrl(initUrl);
			setupEditForm(frm);
			if (useRefererUrl) {
				frm.setRefererUrl(TextUtil.getRefererUrl(this.request));
				if (frm.getRefererUrl() != null) {
					frm.setRedirectUrl(frm.getRefererUrl());
				}
			}
			ModelUtils.model(model, frm);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public String delete(long id, Model model,
			final RedirectAttributes redirectAttrs) {
		try {
			AbstractEntity o = mgr.entityById(entityClass, id);
			beforeDelete(id);
			mgr.hardDeleteObject(o);
			ModelUtils.msg(redirectAttrs, "Successfully deleted "
					+ o.getClass().getSimpleName());
		} catch (BaseSaveObjectException e) {
			ModelUtils.msg(redirectAttrs, e);
		}
		return redirectToListUrl;
	}

	protected void beforeDelete(long id) throws BaseSaveObjectException {
	}

	public String update(EditObjectForm form, Model model,
			final RedirectAttributes redirectAttrs) {
		try {
			boolean isNew = form.isNew();
			String act = form.isNew() ? "created" : "updated";
			updateObject(form);
			ModelUtils.msg(redirectAttrs, "Successfully " + act + " object");
			return getUrlToRedirectAfterSuccessfulUpdate(form, isNew);
		} catch (ObjectRemovedException e) {
			ModelUtils.msg(redirectAttrs, e);
			return getUrlToRedirect(form);
		} catch (ObjectParentRemovedException e) {
			ModelUtils.msg(redirectAttrs, e);
			return getParentRemovedRedirectUrl();
		} catch (BaseSaveObjectException e) {
			form.setMessage(new UserMessage(e));
			form.setRedirectUrl(getUrlToRedirect(form).replace("redirect:", ""));
			form.updateTitle();
			try {
				setupEditForm(form);
			} catch (BaseSaveObjectException e1) {
				// should never happen - skip
			}
			if (form.getInitUrl() != null) {
				ModelUtils.msg(redirectAttrs, e);
				ModelUtils.model(redirectAttrs, form);
				return "redirect:" + form.getInitUrl();
			}

			ModelUtils.model(model, form);
			ModelUtils.msg(model, e);
			return editObjectUrl;
		}
	}

	protected String getUrlToRedirectAfterSuccessfulUpdate(EditObjectForm form,
			boolean isNew) {
		return getUrlToRedirect(form);
	}

	private String getUrlToRedirect(EditObjectForm form) {
		return StringUtils.isEmpty(form.getRefererUrl()) ? redirectToListUrl
				: "redirect:" + form.getRefererUrl();
	}

	protected void updateObject(EditObjectForm frm)
			throws BaseSaveObjectException {
		AbstractEntity o = getFormObjectForUpdate(frm);
		if (o != null) {
			mgr.saveOrUpdate(o);
		}
	}

	public void setRedirectToListUrl(String redirectToListUrl) {
		this.redirectToListUrl = redirectToListUrl;
	}

	public void setEditObjectUrl(String editObjectUrl) {
		this.editObjectUrl = editObjectUrl;
	}

	protected void setupNewObjectInstance(AbstractEntity o) {
	}

	protected void setupEditForm(EditObjectForm frm)
			throws BaseSaveObjectException {
	}

	protected AbstractEntity getFormObjectForUpdate(EditObjectForm frm)
			throws BaseSaveObjectException {
		return frm.getObject();
	}

	public Class<? extends AbstractEntity> getEntityClass() {
		return entityClass;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public boolean isUseRefererUrl() {
		return useRefererUrl;
	}

	public void setUseRefererUrl(boolean useRefererUrl) {
		this.useRefererUrl = useRefererUrl;
	}

	protected HttpServletRequest getRequest() {
		return request;
	}

}