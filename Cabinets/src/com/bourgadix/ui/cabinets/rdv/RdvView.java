package com.bourgadix.ui.cabinets.rdv;

import java.util.Locale;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Visit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class RdvView extends CssLayout {
	
	private Integer idVisit;
	private DaoService daoService = new Dao();

	public RdvView(Integer idVisit) {
		super();
		this.idVisit = idVisit;
		Visit visit = daoService.get(Visit.class, idVisit);
		final FormLayout content = new FormLayout();
		TextField textField = new TextField("Nom", visit.getClient().getName()+" "+ visit.getClient().getLastname());
		DateField dateField = new DateField("Date de visite",
				visit.getDateVisitTime());
		dateField.setLocale(Locale.FRANCE);
		TextArea area=new  TextArea("Détails", visit.getNote());
		dateField.setResolution(Resolution.MINUTE);
		content.addComponent(textField);
		content.addComponent(dateField);
		content.addComponent(area);
		content.setEnabled(false);
		content.setMargin(true);
		addComponent(content);
		
	}

	public Integer getIdVisit() {
		return idVisit;
	}

	public void setIdVisit(Integer idVisit) {
		this.idVisit = idVisit;
	}
	

}
