package com.jee.model.voucher;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="participation")
public class Participation implements Serializable{
	
	private static final long serialVersionUID = 2099712109051758384L;

	public Participation() {
		super();
	}

	public Participation(String id, Beneficiary beneficiary, Distribution distribution) {
		super();
		this.id = id;
		this.beneficiary = beneficiary;
		this.distribution = distribution;
	}
	
	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name="distribution_id")
	private Distribution distribution;
	
	@ManyToOne
	@JoinColumn(name="beneficiary_id")
	private Beneficiary beneficiary;
	
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

	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

}
