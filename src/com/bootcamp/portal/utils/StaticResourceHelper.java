package com.bootcamp.portal.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class StaticResourceHelper {
	private static Logger LOGGER = Logger.getLogger(StaticResourceHelper.class);

	// js
	static private final String JS_INCLUDES = "/js_includes.js";
	static private final String MD5_MIN_JS = "/r/min.js.MD5";
	static private final String JS_INCLUDE_TEMPLATE = "<script src=\"%s\" type=\"text/javascript\"></script>";

	// css
	static private final String CSS_INCLUDES = "/css_includes.js";
	static private final String MD5_MIN_CSS = "/r/min.css.MD5";
	static private final String CSS_INCLUDE_TEMPLATE = "<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\" />";

	// images
	static private final String IMAGES_PATH_INTERNAL = "/r/images";

	static public String js(ServletContext context) {
		return getStaticInclude(context, MD5_MIN_JS, JS_INCLUDES,
				JS_INCLUDE_TEMPLATE);
	}

	static public String css(ServletContext context) {
		return getStaticInclude(context, MD5_MIN_CSS, CSS_INCLUDES,
				CSS_INCLUDE_TEMPLATE);
	}

	static private String getStaticInclude(ServletContext context,
			String md5FileName, String defaultIncludes,
			String includeRowTemplate) {
		return getContent(context, defaultIncludes); // local
	}

	static private String getContent(ServletContext context, String fileName) {
		InputStream in = context.getResourceAsStream(fileName);
		if (in == null) {
			return null;
		}
		String result = "";
		try {
			try {
				result = IOUtils.toString(in).trim();
			} catch (IOException e) {
				LOGGER.error("Error loading " + fileName, e);
			}
		} finally {
			IOUtils.closeQuietly(in);
		}
		return result;
	}

	static public String imgPath(ServletContext context) {
		return IMAGES_PATH_INTERNAL;
	}
}