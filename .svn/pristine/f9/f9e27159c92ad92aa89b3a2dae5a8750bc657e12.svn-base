package com.bourgadix.ui.cabinets.rdv;

import java.util.List;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.TypeVisit;
import com.bourgadix.dao.Visit;
import com.bourgadix.services.VisitManagement;
import com.bourgadix.services.VisitsService;
import com.bourgadix.ui.cabinets.authentification.CurrentUser;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;

public class RdvForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8542376727633189323L;

	private DaoService daoService = new Dao();

	private DateField dateField = new DateField("Date de visite");
	private RichTextArea area = new RichTextArea("Note");
	private Button submit = new Button("Réserver");
	private ComboBox box = new ComboBox("Choisir un client");
	private NativeSelect select = new NativeSelect("Type de visite");
	private Client client;

	public RdvForm() {
		super();
		setMargin(true);
		dateField.setResolution(Resolution.MINUTE);
		// addComponent(fillClients());
		addComponent(dateField);
		addComponent(fillVisitType());
		addComponent(area);
		submit.addClickListener(submitRdv());
		addComponent(submit);

	}

	public ClickListener submitRdv() {
		ClickListener clickListener = new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4850422094911357361L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (validateRdv()) {
					VisitsService service = new VisitManagement();
					String name = CurrentUser.get();
					TypeVisit typeVisit = (TypeVisit) getSelect().getValue();
					service.addVisit(name, client, 1, typeVisit,
							area.getValue(), dateField.getValue());
					Navigator navigator = getUI().getNavigator();
					navigator.navigateTo(Rdv.URL);
				}
			}
		};
		return clickListener;
	}

	public RdvForm(ComboBox box) {
		super();
		this.box = box;
		setMargin(true);
		dateField.setResolution(Resolution.MINUTE);
		addComponent(fillClients());
		addComponent(dateField);
		addComponent(fillVisitType());
		addComponent(area);

		addComponent(submit);
	}

	public Boolean validateRdv() {
		return true;
	}

	public ComboBox fillClients() {
		BeanItemContainer<Client> nations = new BeanItemContainer<Client>(
				Client.class);
		nations.addAll(daoService.getAll(Client.class));
		box.setNullSelectionAllowed(false);
		box.setItemCaptionPropertyId("lastname");
		// box.setItemCaption("idclient", "lastname"+" "+"name");
		box.setInputPrompt("Nom ou prénom");

		box.setContainerDataSource(nations);
		return box;
	}

	public NativeSelect fillVisitType() {
		List<TypeVisit> list = daoService.getAll(TypeVisit.class);
		BeanItemContainer<TypeVisit> container = new BeanItemContainer<TypeVisit>(
				TypeVisit.class);
		container.addAll(list);
		select.setNullSelectionAllowed(true);
		select.setItemCaptionPropertyId("typeVisit");
		select.setContainerDataSource(container);
		select.setRequired(true);
		select.setRequiredError("Vous devez choisir une valeur");
		// select.set("Choisissez une valeur");
		return select;
	}

	public DateField getDateField() {
		return dateField;
	}

	public void setDateField(DateField dateField) {
		this.dateField = dateField;
	}

	public RichTextArea getArea() {
		return area;
	}

	public void setArea(RichTextArea area) {
		this.area = area;
	}

	public ComboBox getBox() {
		return box;
	}

	public void setBox(ComboBox box) {
		this.box = box;
	}

	public NativeSelect getSelect() {
		return select;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setSelect(NativeSelect select) {
		this.select = select;
	}
}
