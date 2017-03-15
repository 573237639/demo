package com.fabao.ledger.common.pojo;

import com.fabaoframework.modules.utils.IUser;


/**
 * 存放用户session的对象
 * @author fangzuo
 *
 */
public class SessionUser extends IUser {

	private static final long serialVersionUID = 2514512303237966816L;

	private String fabaoId;

	private String email;
	private String mobile;
	
	private Integer identityType;
	
	
	public String getFabaoId() {
		return fabaoId;
	}

	public void setFabaoId(String fabaoId) {
		this.fabaoId = fabaoId;
	}

	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
