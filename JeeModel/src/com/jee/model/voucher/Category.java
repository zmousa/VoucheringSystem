package com.jee.model.voucher;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category implements Serializable{
	
	private static final long serialVersionUID = 2099712109051758384L;

	public Category() {
		super();
	}

	public Category(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	
	@Id
	private String id;
	
	@Column(name="description")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
