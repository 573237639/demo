package com.fabao.ledger.modules.sys.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不做用户登录和权限校验
 * @author bst
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoUser {
	public boolean  isVlidate() default false;
}
