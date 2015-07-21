package com.bourgadix.ui.cabinets.clients;

import java.util.Collection;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Medicament;
import com.vaadin.client.ui.FontIcon;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Grid;

public class ClientGrid extends Grid {

	private static final long serialVersionUID = -3632952414500978755L;
	private DaoService daoService = new Dao();
	private String name;

	public ClientGrid() {
		super();
	}

	public ClientGrid(String name) {
		super();
		setSizeFull();
		this.name = name;
		Collection<Client> med = daoService.getByProperty(Client.class,
				"lastname", name);

		BeanItemContainer<Client> container = new BeanItemContainer<Client>(
				Client.class, med);
		container.addNestedContainerProperty("sexe.sexe");
		container.addNestedContainerProperty("country.country");

		// addColumn("idclient", Integer.class)
		// .setRenderer(new NumberRenderer("%02d")).setHeaderCaption("##")
		// .setExpandRatio(0);
		addColumn("name", String.class).setExpandRatio(2);
		addColumn("sexe.sexe", String.class).setExpandRatio(2)
				.setHeaderCaption("Gender");
		addColumn("country.country", String.class).setExpandRatio(2)
				.setHeaderCaption("Country");
		
		setContainerDataSource(container);
	}

	private BeanItemContainer<Medicament> getContainer() {
		return (BeanItemContainer<Medicament>) super.getContainerDataSource();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
