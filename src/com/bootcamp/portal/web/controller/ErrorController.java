package com.bootcamp.portal.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("errors")
public class ErrorController {

	@RequestMapping("errors")
	public String errorPage() {
		return "404";
	}
}