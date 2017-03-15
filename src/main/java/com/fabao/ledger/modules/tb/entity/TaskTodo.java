package com.fabao.ledger.modules.tb.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("TaskTodo")
public class TaskTodo {
	public static final String ITEM = "item";
	private Item item;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
}
