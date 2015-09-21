package com.bourgadix.ui.cabinets.prescription;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Medicament;
import com.bourgadix.dao.Treatment;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class Prescription extends FormLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6274336954264799400L;
	public static final String NAME = "Ajouter une ordonnance";
	public static final String URL = "prescription/add";
	private Client client;
	private ComboBox medicamentsList;
	private TextField description;
	private DaoService daoService = new Dao();
	private List<Medicament> medics = daoService.getAll(Medicament.class);
	private VerticalLayout verticalLayout = new VerticalLayout();
	private Set<Treatment> treatments = new HashSet<Treatment>(0);

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
		layout.addComponent(prepareMedicamentsList());
		description = new TextField("Consigne d'utilisation");
		layout.addComponent(description);
		layout.setMargin(true);
		Button addtreatment = new Button("Ajouter un autre élement", FontAwesome.PLUS_SQUARE);

		addtreatment.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				// String m = ((Medicament)
				// medicamentsList.getValue()).getLabel();
				String d = description.getValue();
				verticalLayout.addComponent(prepareOneRow((Medicament) medicamentsList.getValue(), d));

			}
		});
		layout.addComponent(addtreatment);
		layout.addComponent(verticalLayout);
		return layout;
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

}
