package com.jee.model.voucher;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="distribution")
public class Distribution implements Serializable{
	
	private static final long serialVersionUID = 2099712109051758384L;

	public Distribution() {
		super();
	}

	public Distribution(String id, Date createDate) {
		super();
		this.id = id;
		this.createDate = createDate;
	}
	
	@Id
	private String id;
	
	@Column(name="create_date")
	private Date createDate;
	
	@OneToMany(mappedBy="distribution")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DistributionDetail> distributionDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<DistributionDetail> getDistributionDetails() {
		return distributionDetails;
	}

	public void setDistributionDetails(List<DistributionDetail> distributionDetails) {
		this.distributionDetails = distributionDetails;
	}

}
