package com.fabao.ledger.modules.tb.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("taskdesc")
public class Taskdesc {
	public static final String ITEM = "item";
	public static final String NAME = "name";
	public static final String CONTENT = "content";
//	private String name;
//	private String content;
	@XStreamImplicit(itemFieldName="item")//设置重复的节点名，可能会导致无法反序列化
	private List<Item> item;

//	private Item item;
//	public Item getItem() {
//		return item;
//	}
//	public void setItem(Item item) {
//		this.item = item;
//	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
	
}
