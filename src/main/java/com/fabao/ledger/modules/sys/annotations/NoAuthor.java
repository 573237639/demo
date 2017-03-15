package com.fabao.ledger.modules.sys.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 只做用户登录校验，不做用户权限校验
 * @author bst
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuthor {

	public boolean  isVlidate() default false;
	
}
