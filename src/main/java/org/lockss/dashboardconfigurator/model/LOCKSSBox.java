package org.lockss.dashboardconfigurator.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lockss_box")
public class LOCKSSBox implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "ipaddress")
	private String ipAddress;
	
	public LOCKSSBox(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
	}

	protected LOCKSSBox() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddresss(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	@Override
	public String toString() {
		return String.format("LOCKSSBox[id=%d, ipAddress='%s']", id, ipAddress);
	}
}