package com.bourgadix.ui.cabinets.rdv;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Country;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.TypeVisit;
import com.bourgadix.dao.Visit;
import com.bourgadix.ui.cabinets.authentification.CurrentUser;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.themes.ValoTheme;

public class Rdv extends CssLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496774816932822421L;
	public static final String NAME = "Gestion des Rendez-vous";
	public static final String URL = "RDV";

	private Button monthlyView = new Button("Par mois");
	private Button addVisitButton = new Button("Ajouter une visite");
	private DaoService daoService = new Dao();

	void monthlyview(Layout layout) {
		// BEGIN-EXAMPLE: calendar.monthlyview
		// Create the calendar
		Calendar calendar = new Calendar("My Calendar");
		calendar.setWidth("600px"); // Undefined by default
		calendar.setHeight("400px"); // Undefined by default

		// Use US English for date/time representation
		calendar.setLocale(new Locale("en", "US"));

		// Set start date to first date in this month
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.set(java.util.Calendar.MONTH, 1);
		startDate.set(java.util.Calendar.DATE, 1);
		calendar.setStartDate(startDate.getTime());

		// Set end date to last day of this month
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.set(java.util.Calendar.DATE, 1);
		endDate.roll(java.util.Calendar.DATE, -1);
		calendar.setEndDate(endDate.getTime());

		// Add a short event
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		end.add(java.util.Calendar.HOUR, 2);
		calendar.addEvent(new BasicEvent("Calendar study",
				"Learning how to use Vaadin Calendar", start.getTime(), end
						.getTime()));

		// Add an all-day event
		GregorianCalendar daystart = new GregorianCalendar();
		GregorianCalendar dayend = new GregorianCalendar();
		daystart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		dayend.set(java.util.Calendar.HOUR_OF_DAY, 23);
		BasicEvent dayEvent = new BasicEvent("All-day Long", "This is the Day",
				daystart.getTime(), dayend.getTime());
		dayEvent.setAllDay(true);
		calendar.addEvent(dayEvent);

		// Add an all-week event
		GregorianCalendar weekstart = new GregorianCalendar();
		GregorianCalendar weekend = new GregorianCalendar();
		weekstart.setFirstDayOfWeek(java.util.Calendar.SUNDAY);
		weekstart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		weekstart
				.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
		weekend.set(java.util.Calendar.HOUR_OF_DAY, 23);
		weekend.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SATURDAY);
		BasicEvent weekEvent = new BasicEvent("A long week",
				"This is a long long week", weekstart.getTime(),
				weekend.getTime());
		// weekEvent.setAllDay(true);
		calendar.addEvent(weekEvent);

		layout.addComponent(calendar);
		// END-EXAMPLE: calendar.monthlyview
	}

	public Rdv() {
		setSizeUndefined();
		// Create the calendar
		String user=CurrentUser.get();
		Notification.show("This is the user>"+user);
		final Calendar calendar = new Calendar(new MyEventProvider());
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		calendar.setLocale(new Locale("fr", "FR"));
		calendar.setFirstVisibleHourOfDay(7);
		calendar.setLastVisibleHourOfDay(19);

		monthlyView.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
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
				new Notification("Event clicked: " + e.getCaption(), e
						.getDescription()).show(Page.getCurrent());
				showVisitWindow(e.getIdVisit());

			}
		});
		addVisitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		addVisitButton.setIcon(FontAwesome.CLOCK_O);
		addVisitButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showVisitsForm();
			}
		});
		HorizontalLayout horizontalLayout = new HorizontalLayout(monthlyView,
				addVisitButton);

		VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout,
				calendar);
		verticalLayout.setMargin(true);
		addComponent(verticalLayout);

	}

	public void showVisitsForm() {
		final Window window = new Window("Ajout d'une visite");
		window.setWidth(300.0f, Unit.PIXELS);
		window.center();
		window.setModal(true);
		RdvForm content = new RdvForm();
		window.setContent(content);
		window.setWidth(700, UNITS_PIXELS);
		UI.getCurrent().addWindow(window);
	}

	public void showVisitForm() {
		final Window window = new Window("Ajout d'une visite");
		window.setWidth(300.0f, Unit.PIXELS);
		window.center();
		window.setModal(true);
		final FormLayout content = new FormLayout();

		DateField dateField = new DateField("Date de visite");
		dateField.setResolution(Resolution.MINUTE);
		dateField.setLocale(Locale.FRANCE);
		content.addComponent(fillClients());
		content.addComponent(dateField);
		content.addComponent(fillVisitType());
		RichTextArea area = new RichTextArea("Note");
		content.addComponent(area);
		Button submit = new Button("Enregistrer");
		content.addComponent(submit);
		content.setMargin(true);
		window.setContent(content);
		window.setWidth(700, UNITS_PIXELS);
		UI.getCurrent().addWindow(window);
	}

	public ComboBox fillClients() {
		ComboBox box = new ComboBox("Choisir un client");
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
		NativeSelect select = new NativeSelect("Type de visite");
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

	public HorizontalLayout submitHlayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();

		return null;
	}

	public void showVisitWindow(int idv) {

		final Window window = new Window("Window");
		window.setWidth(300.0f, Unit.PIXELS);
		window.center();
		window.setModal(true);
		RdvView content=new RdvView(idv);
		window.setContent(content);
		window.setWidth(450, UNITS_PIXELS);
		UI.getCurrent().addWindow(window);
	}

	public Date getFirstDateOfTheMonth() {
		DateTime dt = new DateTime();
		// LocalDate lastDayOfMonth = dt.dayOfMonth().withMaximumValue();
		return null;
	}

	public static Date firstDayOfTheMonth() {
		MutableDateTime mdt = new MutableDateTime();
		// mdt.addMonths(1);
		mdt.setDayOfMonth(1);
		mdt.setMillisOfDay(0); // if you want to make sure you're at midnight
		return mdt.toDate();
	}

	public static void main(String[] args) {
		System.out.println("first Day:>+" + firstDayOfTheMonth().toString());
	}

	/*
	 * public Rdv() { setSizeFull(); // Create the calendar Calendar calendar =
	 * new Calendar("My Contextual Calendar"); //calendar.setWidth("600px"); //
	 * Undefined by default //calendar.setHeight("300px"); // Undefined by
	 * default
	 * 
	 * // Use US English for date/time representation calendar.setLocale(new
	 * Locale("fr", "FR")); calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
	 * 
	 * // Handle the context menu selection
	 * 
	 * 
	 * Action.Handler actionHandler = new Action.Handler() { Action
	 * addEventAction = new Action("Add Event"); Action deleteEventAction = new
	 * Action("Delete Event");
	 * 
	 * @Override public Action[] getActions(Object target, Object sender) { //
	 * The target should be a CalendarDateRage for the // entire day from
	 * midnight to midnight. if (! (target instanceof CalendarDateRange)) return
	 * null; CalendarDateRange dateRange = (CalendarDateRange) target;
	 * 
	 * // The sender is the Calendar object if (! (sender instanceof Calendar))
	 * return null; Calendar calendar = (Calendar) sender;
	 * 
	 * // List all the events on the requested day List<CalendarEvent> events =
	 * calendar.getEvents(dateRange.getStart(), dateRange.getEnd());
	 * 
	 * // You can have some logic here, using the date // information. if
	 * (events.size() == 0) return new Action[] {addEventAction}; else return
	 * new Action[] {addEventAction, deleteEventAction}; }
	 * 
	 * @Override public void handleAction(Action action, Object sender, Object
	 * target) { // The sender is the Calendar object Calendar calendar =
	 * (Calendar) sender;
	 * 
	 * if (action == addEventAction) { // Check that the click was not done on
	 * an event if (target instanceof Date) { Date date = (Date) target; // Add
	 * an event from now to plus one hour GregorianCalendar start = new
	 * GregorianCalendar(); start.setTime(date); GregorianCalendar end = new
	 * GregorianCalendar(); end.setTime(date); end.add(java.util.Calendar.HOUR,
	 * 7); calendar.addEvent(new BasicEvent("Calendar study",
	 * "Learning how to use Vaadin Calendar", start.getTime(), end.getTime()));
	 * } else Notification.show("Can't add on an event"); } else if (action ==
	 * deleteEventAction) { // Check if the action was clicked on top of an
	 * event if (target instanceof CalendarEvent) { CalendarEvent event =
	 * (CalendarEvent) target; calendar.removeEvent(event); } else
	 * Notification.show("No event to delete"); } } };
	 * calendar.addActionHandler(actionHandler);
	 * 
	 * 
	 * addComponent(calendar); }
	 */

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}