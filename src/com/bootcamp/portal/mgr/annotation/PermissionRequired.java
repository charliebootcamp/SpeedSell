package com.bootcamp.portal.mgr.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRequired {
	boolean allowedForUser() default false;
}
