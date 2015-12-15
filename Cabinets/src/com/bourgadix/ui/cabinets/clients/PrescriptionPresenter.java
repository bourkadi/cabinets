package com.bourgadix.ui.cabinets.clients;

import java.util.Collection;
import java.util.List;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Prescription;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;

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
		toGrid();
	}

	public PrescriptionPresenter(Component... children) {
		super(children);
		// TODO Auto-generated constructor stub
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
