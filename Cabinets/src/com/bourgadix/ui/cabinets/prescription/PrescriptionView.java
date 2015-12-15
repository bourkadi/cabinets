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
	private final Button print = new Button("Print");
	private DaoService daoService = new Dao();

	public void present(Prescription prescription) {
		final Window window = new Window("Window");
		window.setWidth(300.0f, Unit.PIXELS);
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
		window.setContent(content);
		window.setModal(true);
		window.center();
		UI.getCurrent().addWindow(window);
	}

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
		Prescription prescription=daoService.get(Prescription.class, i);
		present(prescription);
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

}
