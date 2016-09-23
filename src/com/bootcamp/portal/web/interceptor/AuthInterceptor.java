package com.bootcamp.portal.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bootcamp.portal.utils.AuthHelper;
import com.bootcamp.portal.web.AuthenticatedUser;
import com.bootcamp.portal.web.WebConfig;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	protected static final Logger LOGGER = Logger
			.getLogger(AuthInterceptor.class);
	private static final String[] ALLOWED_URLS = { 
		"/errors", 
		"/categories", "/home", "/lot", "/index", "/lots", "/lotdata", "/bid",
		"/registration", "/checkUsername", "/checkPhone", "/checkEmail", "/authorization", 
		"/verification", "/login","/do-verification", "/do-login", "/logout" , "/resetPass",
		"/currentuser","/state","/lots","/contacts","/account","/orders","/user_lots",
		"/user_feedbacks","/searching","/search","/persons","/stat", "/changePswdReq", 
		"/resetPswd","/uact","/send_email","/confirmsell","/question","/image","/ldlimit","/limitlotsbycat", "/alllotdata",
		"/limitlotsbyscat", "/edit_lot"

			};
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		 response.addHeader("P3P",
		 "CP=\"This site does not have a p3p policy.\"");
		 String path = request.getServletPath();
		 if (!StringUtils.isEmpty(path) && !path.contains("/error")) {
		 LOGGER.info("*** PREHANDLE *** " + path);
		 }
		 // allow requests to /login controller
//		 if (AuthHelper.isAllowed(path, ALLOWED_URLS)) {
//			 return true;
//			 }
		
		 AuthenticatedUser user =
		 WebConfig.getCurrentUser(request.getSession());
		 LOGGER.info("*** preHandle *** " + request.getSession().getAttribute("authenticatedUser"));
		 if (AuthHelper.isAllowed(path, ALLOWED_URLS)){
			return true; 
		 } else if (user != null && AuthHelper.hasPermission(user, path,
					request.getSession())) {
			 return true;
		 }
		
		 StringBuilder redirectUrl = new StringBuilder("/speedsell/errors/403");
		 response.sendRedirect(redirectUrl.toString());
		 LOGGER.info("Not allowed path: " + path);
		 return false;
	}
}