package com.fabao.ledger.common.pojo;

public class ZTreeNode {
	
	public static final int TYPE_MENU=1;
	public static final int TYPE_ACT=2;

	private Long id;
	private Long pId;
	private String name;
	private boolean open=false;
	private int menuType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMenuType() {
		return menuType;
	}
	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
