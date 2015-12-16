package com.bourgadix.ui.cabinets.prescription;
/* 
 * A class to present and print the prescription
 */

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Prescription;
import com.bourgadix.dao.Treatment;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PrescriptionView extends CssLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1544969011289517595L;
	public static final String URL = "prescription";
	public static final String NAME = "Ordonnance";

	private Integer i;

	public PrescriptionView() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		setSizeFull();
		String fragment = Page.getCurrent().getUriFragment();
		String v = fragment.split("/")[2];
		setI(Integer.parseInt(v));
		DaoService daoService = new Dao();
		Prescription prescription = daoService.get(Prescription.class, i);
		PrescriptionPresenter prescriptionPresenter = new PrescriptionPresenter(prescription);
		addComponent(prescriptionPresenter);

	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

}
