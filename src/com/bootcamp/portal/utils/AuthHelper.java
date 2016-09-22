package com.bootcamp.portal.utils;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.bootcamp.portal.web.AuthenticatedUser;

public class AuthHelper {

	private static final String[] ALLOWED_URLS_FOR_AUTHORIZED_USER = {
			"/logout", "/logoutUser",  "/myaccount", "/account","/orders",
			"/createlot","/valid","/allcategories","/feedbackdata","/persons","/create_bid",
			"/delete_bid","/create_lot","/update_lot","/delete_lot","/check_password",
			 "/payment", "/redemption"
//			"/myaccount/orders_active", "/myaccount/orders_wfpay", "/myaccount/orders_bought",
//			"/myaccount/lots_active", "/myaccount/lots_archive", "/myaccount/lots_canceled",
//			"/myaccount/lots_sold", "/myaccount/lots_wfpaylot", "/myaccount/feedbacks",
//			"/myaccount/settings",
//			"/account/lots","/orders","/orders/active/"
			};

	private static final String[] ALLOWED_URLS_FOR_AUTHORIZED_ADMIN = {"/adminrequests/requests_pending", "/adminrequests/requests_accepted",
		"/adminrequests/requests_denied", "/adminrequests/requests_premium","/category/category_create", "/category/category_delete",
		"/category","/lotsrequests","/premium"};

	public static boolean hasPermission(AuthenticatedUser user,
			String startUrl, HttpSession session) {
		if (user == null)
			return false;

		if (user.getTypeId() == 1){
			return isAllowed(startUrl,
					(String[])ArrayUtils.addAll(
							ALLOWED_URLS_FOR_AUTHORIZED_ADMIN, ALLOWED_URLS_FOR_AUTHORIZED_USER)
					);
		}

		return isAllowed(startUrl, ALLOWED_URLS_FOR_AUTHORIZED_USER);
	}

	public static boolean isAllowed(String startUrl, String[] list) {
		if (startUrl != null) {
			if (StringUtils.equals(startUrl, "/")) {
				return true;
			}

			for (String s : list) {
				if (startUrl.startsWith(s)) {
					return true;
				}
			}
		}
		return false;
	}
}
