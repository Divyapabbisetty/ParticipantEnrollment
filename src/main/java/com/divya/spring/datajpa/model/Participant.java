package com.divya.spring.datajpa.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "PARTICIPANT_ENROLL")
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@Column(name = "dateOfBirth")
	private Date dateOfBirth;
	
	@Column(name = "activationStatus")
	private boolean activationStatus;
	
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<DependentParticipant> dependents =  new ArrayList<>();

	public Participant() {

	}

	public Participant( String firstName, String lastName, String phoneNumber,Date dateOfBirth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public List<DependentParticipant> getDependents() {
		return dependents;
	}

	public void setDependents(List<DependentParticipant> dependents) {
		this.dependents = dependents;
	}
	
	public boolean isActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	@Override
	public String toString() {
		return "Participant [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", dateOfBirth=" + dateOfBirth + ", activationStatus=" + activationStatus
				+ ", dependents=" + dependents + "]";
	}



}
