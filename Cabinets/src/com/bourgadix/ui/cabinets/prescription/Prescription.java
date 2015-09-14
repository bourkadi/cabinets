package com.bourgadix.ui.cabinets.prescription;

import java.util.List;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Medicament;
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
	private Grid grid = new Grid();
	Tree sample = new Tree("Hardware Inventory");

	public Prescription() {
		super();
		// TODO Auto-generated constructor stub
		prepareGrid();
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
			
				String m = ((Medicament) medicamentsList.getValue()).getLabel();
				String d = description.getValue();
				// verticalLayout.addComponent(prepareOneRow(m, d));
				verticalLayout.addComponent(addToGrid(m, d));
				verticalLayout.addComponent(addToTree(m, d));

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

	public Label prepareOneRow(String medic, String desc) {
		Label label = new Label("- " + medic + " : " + desc);

		return label;
	}

	public Grid addToGrid(String medic, String desc) {

		// grid.setSizeFull();

		
		grid.addRow(medic, desc,"Delete");
		return grid;
	}
	public void prepareGrid(){
		grid.addColumn("Medicament", String.class);

		grid.addColumn("Description", String.class);

		grid.addColumn("Delete", String.class).setRenderer(new ButtonRenderer(new RendererClickListener() {
			@Override
			public void click(RendererClickEvent e) {
				Notification.show("Deleting item " + e.getItemId());
			}
		}));
		Image image = new Image();
		image.setSource(new ExternalResource("https://vaadin.com/vaadin-theme/images/vaadin/vaadin-logo-small.png"));
 
 

	}
	

	public Tree addToTree(String medic, String desc) {
       
		sample.addItem(medic+":"+desc);
		return sample;
	}

 
}
