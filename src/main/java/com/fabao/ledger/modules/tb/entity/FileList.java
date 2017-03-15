package com.fabao.ledger.modules.tb.entity;

public class FileList {
	public static final String ITEM = "item";
	public static final String NAME = "name";
	public static final String CONTENT = "content";
	private String name;
	private String content;
	private Item item;

	
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
