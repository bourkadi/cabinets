package com.bourgadix.ui.cabinets.clients;

import java.util.List;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Country;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Sexe;
import com.bourgadix.services.ClientManagement;
import com.bourgadix.services.ClientsService;
import com.bourgadix.services.Message;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AddClient extends FormLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7427078119549951468L;
	private Button save = new Button("Sauvegaredr");
	private Button cancel = new Button("Annuler");
	private TextField firstName = new TextField("Prénom");
	private TextField lastName = new TextField("Nom");
	private TextField phone = new TextField("Téléphone");
	private TextField email = new TextField("Email");
	private DateField birthDate = new PopupDateField("Date de naissance");
	private ComboBox countries = new ComboBox("Nationalité");
	private TextField identity = new TextField("Pièce d'identité");
	private TextArea richText = new TextArea("Notes");
	private OptionGroup checkbox = new OptionGroup("Sexe");
	private TextField lieuNaissance = new TextField("Lieu de naissance");
	private TextArea adress = new TextArea("Adresse");
	private DaoService daoService = new Dao();
	private int idc;

	private Client client;
	BeanFieldGroup<Client> formFieldBindings;
	public static final String NAME = "Ajouter un client";
	public static final String URL = "client/add";

	private List<Country> countriesList = daoService.getCountriesList();
	private List<Sexe> sexList = daoService.getSexeList();

	public AddClient() {
		super();
		configureComponents();
		buildLayout();
	}

	public AddClient(int idc) {
		super();
		this.idc = idc;
		configureComponents();
		buildLayout();
	}

	public AddClient(Component... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		System.out.println("hello");
	}

	private void configureComponents() {

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		// setVisible(false);
		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					save(event);
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		cancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void buildLayout() {
		// setSizeUndefined();
	

		setSizeFull();
		setMargin(true);
		configureComponent();
		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);
		birthDate.setShowISOWeekNumbers(true);

		fillCountries();
		fillSexe();
		checkbox.setIcon(FontAwesome.CHILD);

		birthDate.setIcon(FontAwesome.CALENDAR);
		email.setIcon(FontAwesome.SEND);
		phone.setIcon(FontAwesome.PHONE);
		firstName.setIcon(FontAwesome.PENCIL_SQUARE);
		lastName.setIcon(FontAwesome.PENCIL_SQUARE_O);

		VerticalLayout lay1 = new VerticalLayout(firstName, lastName, checkbox, birthDate, lieuNaissance, countries);
		richText.setImmediate(true);
		richText.setSizeFull();

		VerticalLayout vlay = new VerticalLayout(email, phone, identity);
		HorizontalLayout horizontalLayout = new HorizontalLayout(vlay, adress);
		vlay.setMargin(new MarginInfo(false, true, false, false));
		VerticalLayout lay2 = new VerticalLayout(horizontalLayout, richText);
		lay2.setMargin(new MarginInfo(false, false, false, true));
		HorizontalLayout layout = new HorizontalLayout(lay1, lay2);
		System.out.println("this is i=>" + this.getIdc());

		if (this.getIdc() != 0) {
	
			
			System.out.println("this is i=>" + this.getIdc());
			Client client = daoService.get(Client.class, this.getIdc());
			
			
			birthDate.setValue(client.getBirthdate());
			firstName.setValue(client.getName());
			lastName.setValue(client.getLastname());
			countries.select(countriesList.get(client.getCountry().getIdcountry()-1));
			checkbox.select(sexList.get(client.getSexe().getIdsexe()-1));
			checkbox.select(client.getSexe());
			phone.setValue(client.getPhone());
			richText.setValue(client.getNote());
			identity.setValue(client.getIdentityNumber());
			adress.setValue(client.getAdress());
			lieuNaissance.setValue(client.getBirthplace());
		}
		addComponents(actions, layout);

	}

	private void fillCountries() {
		BeanItemContainer<Country> nations = new BeanItemContainer<Country>(Country.class);
		nations.addAll(countriesList);
		countries.setNullSelectionAllowed(false);
		countries.setItemCaptionPropertyId("country");
		countries.setInputPrompt("Nationality");
		countries.setIcon(FontAwesome.BOOKMARK);
		countries.setContainerDataSource(nations);

	}

	private void fillSexe() {
		// List<Sexe> sexList = daoService.getAll(Sexe.class);
		BeanItemContainer<Sexe> sexes = new BeanItemContainer<Sexe>(Sexe.class);
		sexes.addAll(sexList);
		checkbox.setItemCaptionPropertyId("sexe");
		checkbox.setRequired(true);
		checkbox.setRequiredError("Vous devdez chisir un sexe");
		checkbox.setContainerDataSource(sexes);

	}

	public void save(Button.ClickEvent event) throws CommitException {

		// Commit the fields from UI to DAO
		// formFieldBindings.commit();

		// Save DAO to backend with direct synchronous service API
		if (validate()) {
			System.out.println("inside save");
			ClientsService service = new ClientManagement();
			int c = ((Country) countries.getValue()).getIdcountry();
			Message message = null;
			if (this.getIdc() != 0) {
				message = service.updateClient("amine", this.getIdc(), firstName.getValue(), lastName.getValue(),
						identity.getValue(), lieuNaissance.getValue(), birthDate.getValue(),
						((Sexe) checkbox.getValue()).getIdsexe(), c, phone.getValue(), phone.getValue(),
						adress.getValue(), richText.getValue());

	
			} else {
				message = service.addClient("amine", firstName.getValue(), lastName.getValue(), identity.getValue(),
						lieuNaissance.getValue(), birthDate.getValue(), ((Sexe) checkbox.getValue()).getIdsexe(), c,
						phone.getValue(), phone.getValue(), adress.getValue(), richText.getValue());
			}
			Notification.show(message.getMessage(), Type.HUMANIZED_MESSAGE);
			getUI().getNavigator().addView(ClientView.NAME, ClientView.class);
			getUI().getNavigator().navigateTo(ClientView.NAME + "/" + message.getIdClient());
		} else {
			Notification.show("Erreur", "Vous devez remplir les champs", Type.ERROR_MESSAGE);
		}
	}

	public Boolean validate() {
		if (firstName.getValue().trim().isEmpty() || lastName.getValue().trim().isEmpty()
				|| countries.getValue().equals(null)) {
			return false;
		}
		return true;
	}

	public void configureComponent() {
		firstName.setDescription("Veuillez saisir le nom du client");
		firstName.setRequired(true);
		firstName.setRequiredError("Vous devez saisir ce champs");
		lastName.setRequired(true);
		lastName.setRequiredError("Vous devez saisir ce champs");
		email.addValidator(new EmailValidator("Vous devez saisir une adresse email valide"));
	}

	public void cancel(Button.ClickEvent event) {
		// Place to call business logic.
		Notification.show("Cancelled");

	}

	public OptionGroup getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(OptionGroup checkbox) {
		this.checkbox = checkbox;
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	public TextField getPhone() {
		return phone;
	}

	public void setPhone(TextField phone) {
		this.phone = phone;
	}

	public TextField getEmail() {
		return email;
	}

	public void setEmail(TextField email) {
		this.email = email;
	}

	public DateField getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(DateField birthDate) {
		this.birthDate = birthDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ComboBox getCountries() {
		return countries;
	}

	public void setCountries(ComboBox countries) {
		this.countries = countries;
	}

	public int getIdc() {
		return idc;
	}

	public void setIdc(int idc) {
		this.idc = idc;
	}

}
