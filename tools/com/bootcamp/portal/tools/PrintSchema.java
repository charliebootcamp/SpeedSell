package com.bootcamp.portal.tools;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bootcamp.portal.AppConfig;

public class PrintSchema {
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		SchemaExport se = new SchemaExport(ctx.getBean(AppConfig.class)
				.getHbmConfiguration());
		se.setDelimiter(";");
		se.create(true, false);
	}
}
