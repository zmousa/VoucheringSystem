package com.jee.model.voucher;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="distribution_detail")
public class DistributionDetail implements Serializable{
	
	private static final long serialVersionUID = 2099712109051758384L;

	public DistributionDetail() {
		super();
	}

	public DistributionDetail(String id, Distribution distribution, Category category, Integer amount) {
		super();
		this.id = id;
		this.distribution = distribution;
		this.category = category;
		this.amount = amount;
	}
	
	public DistributionDetail(String id, Category category, Integer amount) {
		super();
		this.id = id;
		this.category = category;
		this.amount = amount;
	}
	
	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name="distribution_id")
	private Distribution distribution;
	
	
	@ManyToOne
	@JoinColumn(name="category")
	private Category category;

	@Column(name="amount")
	private Integer amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
