package com.spirngboot.rest.entity;

public class Files {
	private String id;
	private String tag;
	private String name;
	private String type;

	public Files() {
		super();
	}

	public Files(String tag, String name, String type) {
		super();
		this.tag = tag;
		this.name = name;
		this.type = type;
		
	}

	public String getID() {
		return id;
	}

	public void setID(String type) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
