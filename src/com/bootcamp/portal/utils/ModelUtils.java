package com.bootcamp.portal.utils;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootcamp.portal.web.model.UserMessage;

public class ModelUtils {

	final private static String MODEL = "model";
	final private static String MESSAGE = "message";

	static public void model(Model model, Object obj) {
		if (model != null) {
			model.addAttribute(MODEL, obj);
		}
	}

	static public void model(final RedirectAttributes redirectAttrs, Object obj) {
		if (redirectAttrs != null) {
			redirectAttrs.addFlashAttribute(MODEL, obj);
		}
	}

	static public boolean hasModel(Model model) {
		return model != null && model.containsAttribute(MODEL);
	}

	@SuppressWarnings("unchecked")
	static public <T> T getModel(Model model) {
		return (T) model.asMap().get(MODEL);
	}

	static public void msg(final RedirectAttributes redirectAttrs, String msg) {
		if (redirectAttrs != null) {
			redirectAttrs.addFlashAttribute(MESSAGE, new UserMessage(msg));
		}
	}

	static public void msg(final RedirectAttributes redirectAttrs, Exception e) {
		if (redirectAttrs != null && e.getMessage() != null) {
			redirectAttrs.addFlashAttribute(MESSAGE, new UserMessage(e));
		}
	}

	static public void msg(Model model, String msg) {
		if (model != null) {
			model.addAttribute(MESSAGE, new UserMessage(msg));
		}
	}

	static public void msg(Model model, Exception e) {
		if (model != null && e.getMessage() != null) {
			model.addAttribute(MESSAGE, new UserMessage(e));
		}
	}

	static public boolean hasMsg(final RedirectAttributes redirectAttrs) {
		return redirectAttrs != null
				&& redirectAttrs.getFlashAttributes().containsKey(MESSAGE);
	}

	static public boolean hasMsg(Model model) {
		return model != null && model.containsAttribute(MESSAGE);
	}

	static public String getMsg(final RedirectAttributes redirectAttrs) {
		if (hasMsg(redirectAttrs)) {
			UserMessage r = (UserMessage) redirectAttrs.getFlashAttributes()
					.get(MESSAGE);
			return r.getMessageText();
		}
		return null;
	}

}