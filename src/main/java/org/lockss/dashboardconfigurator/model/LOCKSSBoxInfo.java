package org.lockss.dashboardconfigurator.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lockss_box_info")
public class LOCKSSBoxInfo implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	@JoinColumn(name = "box", insertable = false, updatable = false)
	private LOCKSSBox box;

	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "country")
	private String country;

	@Column(name = "name")
	private String name;
	
	@Column(name = "database")
	private boolean database;
	
	@Column(name = "dashboard")
	private boolean dashboard;
	
	
	
	public LOCKSSBoxInfo(LOCKSSBox box, String userName, String password, Double longitude, Double latitude,
			String country, String name, boolean database, boolean dashboard) {
		super();
		this.box = box;
		this.userName = userName;
		this.password = password;
		this.longitude = longitude;
		this.latitude = latitude;
		this.country = country;
		this.name = name;
		this.database = database;
		this.dashboard = dashboard;
	}

	public boolean isDatabase() {
		return database;
	}

	public void setDatabase(boolean database) {
		this.database = database;
	}

	public boolean isDashboard() {
		return dashboard;
	}

	public void setDashboard(boolean dashboard) {
		this.dashboard = dashboard;
	}

	protected LOCKSSBoxInfo() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public LOCKSSBox getBox() {
		return box;
	}

	public void setBox(LOCKSSBox box) {
		this.box = box;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("LOCKSSBoxInfo[id=%d ]", id);
	}
}