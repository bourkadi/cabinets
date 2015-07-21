package com.bourgadix.ui.cabinets.clients;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.MutableDateTime;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Visit;
import com.bourgadix.ui.cabinets.CabinetsUI;
import com.bourgadix.ui.cabinets.rdv.EventsOfUserProvider;
import com.bourgadix.ui.cabinets.rdv.MyCustomBasicEvent;
import com.bourgadix.ui.cabinets.rdv.MyEventProvider;
import com.bourgadix.ui.cabinets.rdv.RdvForm;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.themes.ValoTheme;

public class ClientView extends FormLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2854443181871270175L;
	public static final String URL = "client";
	public static final String NAME = "client";

	private Client client;
	private TabSheet sheet = new TabSheet();
	private int idc = 0;
	private DaoService daoService = new Dao();

	public ClientView() {
		super();
		String fragment = CabinetsUI.getCurrent().getPage().getUriFragment();
		String v = fragment.split("/")[1];
		System.out.println("This is v=>" + v);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		String fragment = Page.getCurrent().getUriFragment();
		String v = fragment.split("/")[1];
		int i = Integer.parseInt(v);
		System.out.println("This is parameters=>" + event.getParameters());
		DaoService daoService = new Dao();
		client = daoService.get(Client.class, i);

		prepareClient(client);
	}

	public HorizontalLayout horizentalActionMenu() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Button button = new Button("Ajouter un RDV");
		button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		button.setIcon(FontAwesome.CLOCK_O);
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				final Window window = new Window("Ajout d'une visite");
				window.setWidth(300.0f, Unit.PIXELS);
				window.center();
				window.setModal(true);
				RdvForm content = new RdvForm();
				content.setClient(getClient());
				System.out.println("This is the client=>"
						+ getClient().getName());
				window.setContent(content);
				window.setWidth(700, UNITS_PIXELS);
				UI.getCurrent().addWindow(window);
			}
		});
		horizontalLayout.addComponent(button);

		return horizontalLayout;
	}

	public void prepareClient(Client client) {
		// sheet.setHeight(100.0f, Unit.PERCENTAGE);
		sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		sheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		TextField firstName = new TextField("Prénom");
		TextField lastName = new TextField("Nom");
		TextField phone = new TextField("Téléphone");
		TextField email = new TextField("Email");
		DateField birthDate = new PopupDateField("Date de naissance");
		ComboBox countries = new ComboBox("Nationalité");
		TextField identity = new TextField("Pièce d'identité");
		TextArea richText = new TextArea("Notes");
		OptionGroup checkbox = new OptionGroup("Sexe");
		TextField lieuNaissance = new TextField("Lieu de naissance");
		TextArea adress = new TextArea("Adresse");

		birthDate.setShowISOWeekNumbers(true);
		birthDate.setValue(client.getBirthdate());
		birthDate.setEnabled(false);

		checkbox.setIcon(FontAwesome.CHILD);

		birthDate.setIcon(FontAwesome.CALENDAR);
		email.setIcon(FontAwesome.SEND);

		phone.setIcon(FontAwesome.PHONE);
		firstName.setIcon(FontAwesome.PENCIL_SQUARE);
		firstName.setValue(client.getName());
		firstName.setEnabled(false);
		lastName.setIcon(FontAwesome.PENCIL_SQUARE_O);
		lastName.setValue(client.getLastname());
		lastName.setEnabled(false);

		email.addValidator(new EmailValidator(
				"Vous devez saisir une adresse email valide"));

		VerticalLayout lay1 = new VerticalLayout(firstName, lastName, checkbox,
				birthDate, lieuNaissance, countries);
		richText.setImmediate(true);
		richText.setSizeFull();

		VerticalLayout vlay = new VerticalLayout(email, phone, identity);
		HorizontalLayout horizontalLayout = new HorizontalLayout(vlay, adress);
		vlay.setMargin(new MarginInfo(false, true, false, false));
		VerticalLayout lay2 = new VerticalLayout(horizontalLayout, richText);
		lay2.setMargin(new MarginInfo(false, false, false, true));
		HorizontalLayout layout = new HorizontalLayout(lay1, lay2);
		layout.setMargin(true);
		sheet.addTab(layout, "Info", FontAwesome.INFO);
		CssLayout layout1 = calendarOfClient(client.getIdclient());
		VerticalLayout layout2 = new VerticalLayout(
				new Label(
						"testaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
						ContentMode.HTML));
		sheet.addTab(layout1, "Calendrier", FontAwesome.CALENDAR);
		sheet.addTab(layout2, "Historique des visites", FontAwesome.HISTORY);
		addComponent(horizentalActionMenu());
		addComponent(sheet);

	}

	public CssLayout calendarOfClient(int i) {
		CssLayout cssLayout = new CssLayout();
		cssLayout.setWidthUndefined();
		
		Button monthlyView = new Button("Par mois");
		EventsOfUserProvider provider = new EventsOfUserProvider();
		provider.setIdc(i);
		final Calendar calendar = new Calendar(provider);

		calendar.setLocale(new Locale("fr", "FR"));
		calendar.setFirstVisibleHourOfDay(7);
		calendar.setLastVisibleHourOfDay(19);

		monthlyView.addStyleName(ValoTheme.LAYOUT_CARD);
		monthlyView.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				GregorianCalendar calStart = new GregorianCalendar();
				calendar.setStartDate(firstDayOfTheMonth());

				// Set end date to last day of this month
				GregorianCalendar calEnd = new GregorianCalendar();
				calEnd.set(java.util.Calendar.DATE, 1);
				calEnd.roll(java.util.Calendar.DATE, -1);
				calendar.setEndDate(calEnd.getTime());
				// start navigation ex

			}
		});

		calendar.setHandler(new CalendarComponentEvents.EventClickHandler() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 862508712735459561L;

			public void eventClick(EventClick event) {
				MyCustomBasicEvent e = (MyCustomBasicEvent) event
						.getCalendarEvent();

				// Do something with it

				showVisitWindow(e.getIdVisit());

			}
		});
		VerticalLayout layout=new VerticalLayout(monthlyView,calendar);
		layout.setMargin(true);
		cssLayout.addComponent(layout);	
		return cssLayout;
	}

	public static Date firstDayOfTheMonth() {
		MutableDateTime mdt = new MutableDateTime();
		// mdt.addMonths(1);
		mdt.setDayOfMonth(1);
		mdt.setMillisOfDay(0); // if you want to make sure you're at midnight
		return mdt.toDate();
	}

	public void showVisitWindow(int idv) {

		Visit visit = daoService.get(Visit.class, idv);
		final Window window = new Window("Window");
		window.setWidth(300.0f, Unit.PIXELS);
		window.center();
		window.setModal(true);
		final FormLayout content = new FormLayout();
		TextField textField = new TextField("Nom", visit.getClient().getName());
		DateField dateField = new DateField("Date de visite",
				visit.getDateVisitTime());
		dateField.setResolution(Resolution.MINUTE);
		content.addComponent(textField);
		content.addComponent(dateField);
		content.setMargin(true);
		window.setContent(content);
		window.setWidth(450, UNITS_PIXELS);
		UI.getCurrent().addWindow(window);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getIdc() {
		return idc;
	}

	public void setIdc(int idc) {
		this.idc = idc;
	}
}
