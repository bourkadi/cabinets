package com.bourgadix.ui.cabinets.prescription;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Medicament;
import com.bourgadix.dao.Treatment;
import com.bourgadix.dao.User;
import com.bourgadix.services.PrescriptionManagement;
import com.bourgadix.services.PrescriptionService;
import com.bourgadix.ui.cabinets.authentification.CurrentUser;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class Prescription extends FormLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6274336954264799400L;
	public static final String NAME = "Ajouter une ordonnance plz";
	public static final String URL = "prescription/add";
	private Client client;
	private ComboBox medicamentsList;
	private TextField description;
	private DaoService daoService = new Dao();
	private List<Medicament> medics = daoService.getAll(Medicament.class);
	private VerticalLayout verticalLayout = new VerticalLayout();
	private Set<Treatment> treatments = new HashSet<Treatment>(0);
	private Button save = new Button("Sauvegarder l'ordonnance ", FontAwesome.SAVE);
	private final PrescriptionService prescriptionService = new PrescriptionManagement();
	private RichTextArea richTextArea = new RichTextArea("Notes");
	private com.bourgadix.dao.Prescription prescription = null;

	public Prescription() {
		super();
		// TODO Auto-generated constructor stub
		addComponent(prepareTreatmentForm());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		setSizeFull();
		setMargin(true);
		String fragment = Page.getCurrent().getUriFragment();
		String v = fragment.split("/")[2];
		int i = Integer.parseInt(v);

		client = daoService.get(Client.class, i);

	}

	public ComboBox prepareMedicamentsList() {
		BeanItemContainer<Medicament> medicaments = new BeanItemContainer<Medicament>(Medicament.class);
		medicaments.addAll(medics);
		medicamentsList = new ComboBox("Le médicament");
		medicamentsList.setNullSelectionAllowed(false);
		medicamentsList.setItemCaptionPropertyId("label");
		medicamentsList.setInputPrompt("Médicament");

		medicamentsList.setContainerDataSource(medicaments);
		return medicamentsList;
	}

	public VerticalLayout prepareTreatmentForm() {

		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Button addtreatment = new Button("Ajouter un élement", FontAwesome.PLUS_SQUARE);

		description = new TextField("Consigne d'utilisation");
		save.addClickListener(savePrescription());
		layout.addComponent(save);
		horizontalLayout.addComponent(prepareMedicamentsList());
		horizontalLayout.addComponent(description);
		horizontalLayout.addComponent(addtreatment);

		layout.addComponent(horizontalLayout);

		layout.setMargin(true);

		addtreatment.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				// String m = ((Medicament)
				// medicamentsList.getValue()).getLabel();
				String d = description.getValue();
				verticalLayout.addComponent(prepareOneRow((Medicament) medicamentsList.getValue(), d));
				clear();

			}
		});

		layout.addComponent(verticalLayout);
		layout.addComponent(richTextArea);
		return layout;
	}

	public ClickListener savePrescription() {
		ClickListener clickListener = new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4850422094911357361L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// getClass().prescriptionService.addPrescription(treatments,
				// new Date(), client, user);
				String name = CurrentUser.get();
				User user = daoService.getByProperty(User.class, "username", name).get(0);
				prescription = prescriptionService.addPrescription(treatments, new Date(), richTextArea.getValue(),
						client, user);
				popupWindow();
			}
		};
		return clickListener;
	}

	public ClickListener addTreatment(final Medicament medicament, final String description) {
		ClickListener clickListener = new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4850422094911357361L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				verticalLayout.addComponent(prepareOneRow(medicament.getLabel(), description));
				clear();

			}
		};
		return clickListener;
	}

	public HorizontalLayout prepareOneRow(String medic, String desc) {
		Label label = new Label("- " + medic + " : " + desc);
		Button removeMedic = new Button("Enlever", FontAwesome.MINUS);
		removeMedic.addStyleName(ValoTheme.BUTTON_LINK);
		HorizontalLayout horizontalLayout = new HorizontalLayout(label, removeMedic);
		return horizontalLayout;
	}

	public HorizontalLayout prepareOneRow(Medicament medic, String desc) {
		Label label = new Label("- " + medic.getLabel() + " : " + desc);
		Button removeMedic = new Button("Enlever", FontAwesome.MINUS);
		removeMedic.addStyleName(ValoTheme.BUTTON_LINK);
		HorizontalLayout horizontalLayout = new HorizontalLayout(label, removeMedic);
		Treatment treatment = new Treatment();
		treatment.setDescription(desc);
		treatment.setMedicament(medic);
		treatments.add(treatment);
		removeMedic.addClickListener(removeRow(horizontalLayout, treatment));
		return horizontalLayout;
	}

	public ClickListener removeRow(final HorizontalLayout horizontalLayout, final Treatment treatment) {
		ClickListener clickListener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Notification.show("detach");
				treatments.remove(treatment);
				horizontalLayout.setVisible(false);
				for (Treatment component : treatments) {
					System.out.println(component.getMedicament().getLabel());
				}
			}
		};
		return clickListener;
	}

	public void popupWindow() {
		PrescriptionPrinter printer = new PrescriptionPrinter();
	//	printer.present(prescription);
		printer.print();
	}

	public void clear() {
		System.out.println("enter clear");
		this.description.clear();
		this.medicamentsList.clear();
		System.out.println("exit clear");
	}

	public com.bourgadix.dao.Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(com.bourgadix.dao.Prescription prescription) {
		this.prescription = prescription;
	}
}
