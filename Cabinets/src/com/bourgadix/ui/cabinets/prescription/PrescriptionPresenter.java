package com.bourgadix.ui.cabinets.prescription;

import com.bourgadix.dao.Prescription;
import com.bourgadix.dao.Treatment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PrescriptionPresenter extends CssLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4796554080535926141L;

	private Prescription prescription;
	private final Button print = new Button("Print");

	public void present() {
		setSizeFull();
		Label label=new Label("Ordonnance");
		
		final FormLayout content = new FormLayout();
		content.addComponent(new Label(prescription.getClient().getName()));
		content.addComponent(new Label(prescription.getUser().getUsername()));
		for (Treatment treatment : prescription.getTreatments()) {
			content.addComponent(new Label(treatment.getMedicament().getLabel() + " :" + treatment.getDescription()));
		}
		content.addComponent(new Label(prescription.getNote()));
		content.addComponent(new Label(prescription.getPrescriptiondate().toString()));
		print.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7225712980929086903L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});
		content.addComponent(print);
		addComponent(content);

	}

	public PrescriptionPresenter() {
		super();
		// TODO Auto-generated constructor stub
		
	}

	public PrescriptionPresenter(Component... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}

	public PrescriptionPresenter(Prescription prescription) {
		super();
		this.prescription = prescription;
		present();
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}
}
