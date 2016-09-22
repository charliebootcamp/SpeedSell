package com.bootcamp.portal.mgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.User;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.bootcamp.portal.utils.TextUtil;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManager extends BaseManager {

	private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();

	@Transactional
	public List<User> getUsers(HttpServletRequest request) {
		List<User> result = objectList(User.class, null);
		return result;
	}

	@Transactional
	public User getByEmail(String email) {
		try {
			return (User) entityByCriteria(User.class,
					Restrictions.eq("email", email));
		} catch (ObjectRemovedException e) {
			return null;
		}
	}

	@Transactional
	public User addUser(String email, String password) throws Exception {
		User t = new User();
		t.setEmail(email);
		String encodedPwd = passwordEncoder.encodePassword(password, null);
		t.setPassword(encodedPwd);
		t.setVerificationCode(TextUtil.getRandomString());
		try {
			saveOrUpdate(t);
		} catch (Exception e) {
			throw new Exception("Exception on User saving");
		}
		return t;
	}

	@Transactional
	public void verifyUser(User user, String code) throws Exception {
		if (user.getVerified() == Boolean.TRUE) {
			throw new Exception("User is already verified");
		}

		if (!StringUtils.equals(user.getVerificationCode(), code)) {
			throw new Exception("Code is wrong");
		}

		try {
			user.setVerified(true);
			user.setVerificationCode(null);
			saveOrUpdate(user);
		} catch (Exception e) {
			throw new Exception("Error on saving user");
		}
	}

	@Transactional
	public User authenticate(String email, String password) {
		User t = getByEmail(email);
		if (t == null) {
			return null;
		}

		if (!passwordEncoder.isPasswordValid(t.getPassword(), password, null)) {
			return null;
		}

		return t;
	}

}