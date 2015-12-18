package com.bourgadix.ui.cabinets.clients;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Prescription;
import com.bourgadix.ui.cabinets.prescription.PrescriptionView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class PrescriptionPresenter extends CssLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 783763213272752807L;
	private DaoService daoService = new Dao();
	private int client;

	public PrescriptionPresenter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrescriptionPresenter(int client) {
		super();
		this.client = client;
		toList();
	}

	public PrescriptionPresenter(Component... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}

	public void toList() {
		List<Prescription> list = daoService.getPrescriptionsByClient(client);
		Notification.show("la taille est" + list.size());
		System.out.println("la taille est" + list.size());
		for (Prescription prescription : list) {
			final String url=PrescriptionView.URL+"/id/"+prescription.getIdprescription();
			addComponent(listLine(prescription.getClient().getName(), prescription.getClient().getLastname(),
					prescription.getCreatedate(), url));
		}
	}

	public VerticalLayout listLine(String name, String lastname, int dateunix, final String url) {
		VerticalLayout vLayout = new VerticalLayout();
		Date date = new Date((long) dateunix * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
		String dt = sdf.format(date);
		Label content = new Label("\n" + name + " " + lastname + " :" + dt + "\n");
		Button link = new Button("View", new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Navigator navigator = getUI().getNavigator();
				navigator.addView(PrescriptionView.URL, PrescriptionView.class);
				navigator.navigateTo(url);
			}
		});
		link.addStyleName(ValoTheme.BUTTON_LINK);
	//	link.setIcon(FontAwesome.EYE);
		// content.setContentMode(ContentMode.HTML);
		HorizontalLayout horizontalLayout=new HorizontalLayout(content,link);
		vLayout.addComponent(horizontalLayout);
		return vLayout;
	}

	public void toGrid() {
		Collection<Prescription> list = daoService.getByProperty(Prescription.class, "client.idclient", client);
		BeanItemContainer<Prescription> container = new BeanItemContainer<Prescription>(Prescription.class, list);
		container.addNestedContainerProperty("client.name");
		container.addNestedContainerProperty("client.lastname");
		System.out.println("Inside the presenter, idc is== " + client);
		Grid grid = new Grid();
		grid.addColumn("client.name", String.class).setExpandRatio(2);
		grid.addColumn("client.lastname", String.class).setExpandRatio(2);
		grid.addColumn("createdate", String.class).setExpandRatio(2).setHeaderCaption("Date de creation");

		grid.setContainerDataSource(container);
		addComponent(grid);

	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public static void main(String[] args) {
		DaoService daoService = new Dao();
		List<Prescription> list = daoService.getByProperty(Prescription.class, "client.idclient", 2);
		System.out.println("this is it:" + list.size());

	}
}
