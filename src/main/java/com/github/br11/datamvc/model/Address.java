package com.github.br11.datamvc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String location;
	private Date since;

	public Address() {
		this(null, null);
	}

	public Address(String location, Date since) {
		super();
		this.location = location;
		this.since = since;
	}

	public long getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

}
