package com.bourgadix.ui.cabinets.prescription;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

public class Prescription extends CssLayout implements View {
	
	public Prescription() {
		super();
		// TODO Auto-generated constructor stub
		addComponent(new Label("Hello"));
	}

	public static final String NAME = "Ajouter une ordonnance";
	public static final String URL = "prescription/add";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		Notification.show("Hello");
	}

}
