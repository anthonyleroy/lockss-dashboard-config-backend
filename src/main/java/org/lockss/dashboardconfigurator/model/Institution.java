package org.lockss.dashboardconfigurator.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "institution")
public class Institution implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "address")
	private String address;

	@Column(name = "name")
	private String name;

	@Column(name = "shortname")
	private String shortname;
	
	@OneToOne
	@JoinColumn(name = "institution")
	private Institution institution;

	@OneToOne
	@JoinColumn(name = "person")
	private Person person;

	
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	protected Institution() {
	}

	public long getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	@Override
	public String toString() {
		return String.format("Institution[id=%d, address='%s', name='%s', shortname='%s']", id, address, name, shortname);
	}
}