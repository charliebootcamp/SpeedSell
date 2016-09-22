package com.bootcamp.portal.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootcamp.portal.domain.User;
import com.bootcamp.portal.mgr.UserManager;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.utils.ModelUtils;
import com.bootcamp.portal.web.AuthenticatedUser;
import com.bootcamp.portal.web.WebConfig;
import com.bootcamp.portal.web.model.ProfileForm;

@Controller
@RequestMapping(value = { "user" })
@PermissionRequired
public class UserController {

	@Autowired
	private UserManager userManager;

	protected static final Logger LOGGER = Logger
			.getLogger(UserController.class);

	@RequestMapping("list")
	public String list(Model model, HttpServletRequest request) {
//		List<User> users = ManagerFactory.getUserManager().getUsers(request);
//
//		List<UserDto> dtos = new ArrayList<UserDto>();
//		for (User t : users) {
//			dtos.add(new UserDto(t));
//		}
//		ModelUtils.model(model, dtos);

		return "list/userlist";
	}

	@RequestMapping("profile")
	public String edit(Model model, HttpSession session,
			final RedirectAttributes redirectAttrs, HttpServletRequest request) {

		AuthenticatedUser user = WebConfig.getCurrentUser(session);
		if (user == null) {
			return "login";
		}

		User t = userManager.getByEmail(user.getEmail());
		if (t == null) {
			return "login";
		}

		ProfileForm f = new ProfileForm(t);
		ModelUtils.model(model, f);
		return "edit/profile";
	}

	private static String toUTF(String v) {
		try {
			return new String(v.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	@RequestMapping("update")
	public String update(ProfileForm form, Model model, HttpSession session,
			final RedirectAttributes redirectAttrs, HttpServletRequest request) {

		AuthenticatedUser user = WebConfig.getCurrentUser(session);
		if (user == null) {
			return "login";
		}

		User t = userManager.getByEmail(user.getEmail());
		if (t == null) {
			return "login";
		}

		User n = form.getObject();
		t.setName(toUTF(n.getName()));

		try {
			userManager.saveOrUpdate(t);
		} catch (BaseSaveObjectException e) {
			LOGGER.error(e.getMessage());
		}
		// return "redirect:/user/view/" + t.getId(); // user details view
		return "redirect:user/list";
	}
}