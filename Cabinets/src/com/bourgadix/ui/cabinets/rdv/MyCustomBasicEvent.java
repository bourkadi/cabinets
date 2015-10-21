package com.bourgadix.ui.cabinets.rdv;

import com.vaadin.ui.components.calendar.event.BasicEvent;

public class MyCustomBasicEvent extends BasicEvent {
	
	private Integer idVisit;

	public MyCustomBasicEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdVisit() {
		return idVisit;
	}

	public void setIdVisit(Integer idVisit) {
		this.idVisit = idVisit;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6496644195095926926L;

}
