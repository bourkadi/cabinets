package com.bourgadix.ui.cabinets.rdv;

import java.util.Locale;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.StatusVisit;
import com.bourgadix.dao.Visit;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class RdvView extends CssLayout {

	private Integer idVisit;
	private DaoService daoService = new Dao();
	private Button cancelRdv;

	public RdvView(Integer idVisit) {
		super();
		this.idVisit = idVisit;
		Visit visit = daoService.get(Visit.class, idVisit);
		final FormLayout content = new FormLayout();
		TextField textField = new TextField("Nom", visit.getClient().getName() + " " + visit.getClient().getLastname());
		DateField dateField = new DateField("Date de visite", visit.getDateVisitTime());
		dateField.setLocale(Locale.FRANCE);
		TextArea area = new TextArea("Détails", visit.getNote());
		dateField.setResolution(Resolution.MINUTE);
		cancelRdv = new Button("Annuler le rdv", FontAwesome.BAN);
		// cancelRdv.addStyleName(ValoTheme.BUTTON_LINK);
		cancelRdv.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelRdv.addClickListener(cancelRdv());
		content.addComponent(cancelRdv);
		content.addComponent(textField);
		content.addComponent(dateField);
		content.addComponent(area);
		content.addComponent(cancelRdv);
		content.setEnabled(true);
		content.setMargin(true);
		addComponent(content);

	}

	public ClickListener cancelRdv() {
		ClickListener clickListener = new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4850422094911357361L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Visit visit = daoService.get(Visit.class, idVisit);
				StatusVisit statusVisit=daoService.get(StatusVisit.class, 2);
				visit.setStatusVisit(statusVisit);
				daoService.update(visit);
				
				 Navigator navigator = getUI().getNavigator();
				 navigator.navigateTo(Rdv.URL);

			}
		};
		return clickListener;
	}

	public Integer getIdVisit() {
		return idVisit;
	}

	public void setIdVisit(Integer idVisit) {
		this.idVisit = idVisit;
	}

	public Button getCancelRdv() {
		return cancelRdv;
	}

	public void setCancelRdv(Button cancelRdv) {
		this.cancelRdv = cancelRdv;
	}

}
