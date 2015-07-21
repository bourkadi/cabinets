package com.bourgadix.services;

import java.io.Serializable;

/**
 * Important class to orgnaize the communication between service classes
 * 
 * */

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4126394317301641144L;

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String message;
	private Boolean value;
	private  Integer idClient;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public synchronized Integer getIdClient() {
		return idClient;
	}

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}
}
