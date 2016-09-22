package com.bootcamp.portal.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootcamp.portal.mgr.annotation.PermissionRequired;

// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class IndexController {

	@RequestMapping("/")
	public String index() {
		
		return "index";
	}
	
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/errors/404")
	public String error404() {
		return "errors/404";
	}
	@RequestMapping("/errors/403")
	public String error403() {
		return "errors/403";
	}
	@RequestMapping("/errors/500")
	public String error500() {
		return "errors/500";
	}

	@RequestMapping("/contacts")
	public String contacts() {
		return "contacts";
	}
	
	@RequestMapping("/category/category_create")
	public String category_create() {
		return "category/category_create";
	}
	
//	@RequestMapping("/verification")
//	public String validate() {
//		return "/registration/loginPage";
//	}
	
	@RequestMapping("/category/category_delete")
	public String category_delete() {
		return "category/category_delete";
	}

	@RequestMapping("/adminrequests/requests_accepted")
	public String requests_accepted() {
		return "adminrequests/requests_accepted";
	}
	
	@RequestMapping("/adminrequests/requests_denied")
	public String requests_denied() {
		return "adminrequests/requests_denied";
	}
	
	@RequestMapping("/adminrequests/requests_pending")
	public String requests_pending() {
		return "adminrequests/requests_pending";
	}
	
	@RequestMapping("/adminrequests/requests_premium")
	public String requests_premium() {
		return "adminrequests/requests_premium";
	}

	@RequestMapping("/lot")
	public String lot() {
		return "lot";
	}

	@RequestMapping("/lots")
	public String lots() {
		return "lots";
	}
	
	@RequestMapping("/myaccount/orders_active")
	public String orders_active() {
		return "myaccount/orders_active";
	}
	
	@RequestMapping("/myaccount/orders_wfpay")
	public String orders_wfpay() {
		return "myaccount/orders_wfpay";
	}
	
	@RequestMapping("/myaccount/orders_bought")
	public String orders_bought() {
		return "myaccount/orders_bought";
	}	
	
	@RequestMapping("/myaccount/lots_active")
	public String lots_active() {
		return "myaccount/lots_active";
	}
	
	@RequestMapping("/myaccount/lots_archive")
	public String lots_archive() {
		return "myaccount/lots_archive";
	}	
	
	@RequestMapping("/myaccount/lots_canceled")
	public String lots_canceled() {
		return "myaccount/lots_canceled";
	}	
	
	@RequestMapping("/myaccount/lots_created")
	public String lots_created() {
		return "myaccount/lots_created";
	}	
	
	@RequestMapping("/myaccount/lots_sold")
	public String lots_sold() {
		return "myaccount/lots_sold";
	}	
	
	@RequestMapping("/myaccount/lots_wfpaylot")
	public String lots_wfpaylot() {
		return "myaccount/lots_wfpaylot";
	}	
	
	@RequestMapping("/myaccount/feedbacks")
	public String feedbacks() {
		return "myaccount/feedbacks";
	}
	
	@RequestMapping("/myaccount/settings")
	public String settings() {
		return "myaccount/settings";
	}
	

	@RequestMapping("/registration/registrationPage")
	public String registration(){
		return "registration/registrationPage";
	}
	
	@RequestMapping("/createlot")
	public String createlot() {
		return "createlot";
	}
	

	@RequestMapping("/user_lots")
	public String user_lots() {
		return "user_lots";
	}
	
	@RequestMapping("/user_feedbacks")
	public String user_feedbacks() {
		return "user_feedbacks";
	}
	
	@RequestMapping("/searching")
	public String searching() {
		return "searching";
	}
	
	@RequestMapping("/payment")
	public String payment() {
		return "/payment";
	}
	
	@RequestMapping("/question")
	public String question() {
		return "question";
	}
}