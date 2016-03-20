package com.jee.model.voucher;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.jee.model.exception.AgeRangeException;
import com.jee.model.exception.GenderException;

@Entity
@Table(name="beneficiary")
public class Beneficiary implements Serializable{
	private static final long serialVersionUID = 7951045390904522767L;

	public static final String MALE = "M";
	public static final String FEMALE = "F";
	public static final int MIN_AGE = 1;
	public static final int MAX_AGE = 120;
	
	public Beneficiary() {
		super();
	}

	public Beneficiary(String id, String name, String gender, Integer age) throws GenderException, AgeRangeException {
		super();
		this.id = id;
		this.name = name;
		setGender(gender);
		setAge(age);
	}
	
	@Id
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="age")
	private Integer age;
	
	@OneToMany(mappedBy="beneficiary")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Participation> participations;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) throws GenderException {
		if (!MALE.equals(gender) && !FEMALE.equals(gender))
			throw new GenderException();
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) throws AgeRangeException {
		if (age < MIN_AGE || age > MAX_AGE)
			throw new AgeRangeException();
		this.age = age;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}
}
