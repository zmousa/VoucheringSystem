package com.jee.model.voucher;

import java.io.Serializable;

public class ParticipationDetail implements Serializable{
	private static final long serialVersionUID = -5878374353935406690L;
	
	public ParticipationDetail() {
		super();
	}

	public ParticipationDetail(Participation participation, Category category, Integer amount) {
		super();
		this.participation = participation;
		this.category = category;
		this.amount = amount;
	}
	
	private Participation participation;
	private Category category;
	private Integer amount;

	public synchronized Participation getParticipation() {
		return participation;
	}

	public synchronized void setParticipation(Participation participation) {
		this.participation = participation;
	}

	public synchronized Category getCategory() {
		return category;
	}

	public synchronized void setCategory(Category category) {
		this.category = category;
	}

	public synchronized Integer getAmount() {
		return amount;
	}

	public synchronized void setAmount(Integer amount) {
		this.amount = amount;
	} 
}
