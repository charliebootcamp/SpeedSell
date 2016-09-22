package com.bootcamp.portal.test;

import static org.junit.Assert.assertEquals;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.bootcamp.portal.AppConfig;
import com.bootcamp.portal.web.WebConfig;

public abstract class AbstractTestCase {
	protected static AnnotationConfigWebApplicationContext applicationContext;
	protected static TestHelper testHelper;

	protected AbstractTestCase() {
		testHelper.cleanupDb();
	}

	static {
		AppConfig.init("test");
		applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.setServletContext(new MockServletContext());
		applicationContext.register(WebConfig.class, TestHelper.class);
		applicationContext.refresh();

		testHelper = applicationContext.getBean(TestHelper.class);
	}

	protected static void assertCount(Class<?> entityClass, int expected) {
		assertEquals(expected, testHelper.count(entityClass));
	}

}