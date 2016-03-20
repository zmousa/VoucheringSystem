package com.jee.model.voucher;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="voucher")
public class Voucher implements Serializable{
	
	private static final long serialVersionUID = 2099712109051758384L;

	public Voucher() {
		super();
	}

	public Voucher(String code, Category category, Integer value) {
		super();
		this.code = code;
		this.category = category;
		this.value = value;
	}
	
	@Id
	private String code;
	
	@ManyToOne
	@JoinColumn(name="category")
	private Category category;
	
	@Column(name="value")
	private Integer value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
