package com.bourgadix.ui.cabinets.clients;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.joda.time.MutableDateTime;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Country;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Visit;
import com.bourgadix.services.VisitManagement;
import com.bourgadix.services.VisitsService;
import com.bourgadix.ui.cabinets.CabinetsUI;
import com.bourgadix.ui.cabinets.rdv.EventsOfUserProvider;
import com.bourgadix.ui.cabinets.rdv.MyCustomBasicEvent;
import com.bourgadix.ui.cabinets.rdv.RdvForm;
import com.bourgadix.ui.cabinets.rdv.RdvView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
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
	private AddClient addClient;

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
				System.out.println("This is the client=>" + getClient().getName());
				window.setContent(content);
				window.setWidth(700, UNITS_PIXELS);
				UI.getCurrent().addWindow(window);
			}
		});
		Button editButton = new Button("Modifier");
		button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		button.setIcon(FontAwesome.EDIT);
		editButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addClient.setEnabled(true);
			}
		});
		horizontalLayout.addComponent(button);
		horizontalLayout.addComponent(editButton);

		return horizontalLayout;
	}

	public void prepareClient(Client client) {
		// sheet.setHeight(100.0f, Unit.PERCENTAGE);
		sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		sheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		HorizontalLayout layout = info();
		sheet.addTab(layout, "Info", FontAwesome.INFO);
		CssLayout layout1 = calendarOfClient(client.getIdclient());
		VerticalLayout layout2 = this.visitHistory(client.getIdclient());
		sheet.addTab(layout1, "Calendrier", FontAwesome.CALENDAR);
		sheet.addTab(layout2, "Historique des visites", FontAwesome.HISTORY);
		addComponent(horizentalActionMenu());
		addComponent(sheet);

	}

	public HorizontalLayout info() {
		/*
		 * TextField firstName = new TextField("Prénom"); TextField lastName =
		 * new TextField("Nom"); TextField phone = new TextField("Téléphone");
		 * TextField email = new TextField("Email"); DateField birthDate = new
		 * PopupDateField("Date de naissance"); ComboBox countries = new
		 * ComboBox("Nationalité"); TextField identity = new TextField(
		 * "Pièce d'identité"); TextArea richText = new TextArea("Notes");
		 * OptionGroup checkbox = new OptionGroup("Sexe"); TextField
		 * lieuNaissance = new TextField("Lieu de naissance"); TextArea adress =
		 * new TextArea("Adresse");
		 * 
		 * birthDate.setShowISOWeekNumbers(true);
		 * birthDate.setValue(client.getBirthdate());
		 * 
		 * 
		 * checkbox.setIcon(FontAwesome.CHILD);
		 * 
		 * birthDate.setIcon(FontAwesome.CALENDAR);
		 * email.setIcon(FontAwesome.SEND);
		 * 
		 * phone.setIcon(FontAwesome.PHONE);
		 * firstName.setIcon(FontAwesome.PENCIL_SQUARE);
		 * firstName.setValue(client.getName());
		 * 
		 * lastName.setIcon(FontAwesome.PENCIL_SQUARE_O);
		 * lastName.setValue(client.getLastname());
		 * 
		 * 
		 * email.addValidator(new EmailValidator(
		 * "Vous devez saisir une adresse email valide"));
		 * countries=fillCountries();
		 * 
		 * 
		 * VerticalLayout lay1 = new VerticalLayout(firstName, lastName,
		 * checkbox, birthDate, lieuNaissance, countries);
		 * richText.setImmediate(true); richText.setSizeFull();
		 * 
		 * VerticalLayout vlay = new VerticalLayout(email, phone, identity);
		 * HorizontalLayout horizontalLayout = new HorizontalLayout(vlay,
		 * adress); vlay.setMargin(new MarginInfo(false, true, false, false));
		 * VerticalLayout lay2 = new VerticalLayout(horizontalLayout, richText);
		 * lay2.setMargin(new MarginInfo(false, false, false, true));
		 */
		 addClient = new AddClient(client.getIdclient());
		addComponent(addClient);
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponent(addClient);
		layout.setMargin(true);
		addClient.setEnabled(false);
		return layout;
	}

	private ComboBox fillCountries() {
		ComboBox countries = new ComboBox("Nationalité");
		countries.setImmediate(true);
		BeanItemContainer<Country> nations = new BeanItemContainer<Country>(Country.class);
		nations.addAll(daoService.getAll(Country.class));
		countries.setNullSelectionAllowed(true);
		countries.setItemCaptionPropertyId("country");
		// countries.setInputPrompt("Nationality");
		countries.setIcon(FontAwesome.BOOKMARK);
		countries.select(nations.getItem(10));
		countries.setContainerDataSource(nations);

		return countries;
	}

	public VerticalLayout visitHistory(int id) {
		VisitsService service = new VisitManagement();

		List<Visit> list = service.getVisitsOfClient(id, null, null);
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
		for (Visit visit : list) {

			Label label = new Label(
					sdf.format(visit.getDateVisitTime()) + " : " + visit.getStatusVisit().getStatusVisit());
			verticalLayout.addComponent(label);
		}
		return verticalLayout;
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
				MyCustomBasicEvent e = (MyCustomBasicEvent) event.getCalendarEvent();

				// Do something with it

				showVisitWindow(e.getIdVisit());

			}
		});
		VerticalLayout layout = new VerticalLayout(monthlyView, calendar);
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

		final Window window = new Window("Window");
		window.setWidth(300.0f, Unit.PIXELS);
		window.center();
		window.setModal(true);
		RdvView content = new RdvView(idv);
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

	public AddClient getAddClient() {
		return addClient;
	}

	public void setAddClient(AddClient addClient) {
		this.addClient = addClient;
	}
}
