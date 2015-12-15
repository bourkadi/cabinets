package com.bourgadix.ui.cabinets.clients;

import com.bourgadix.dao.Client;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class ClientList extends CssLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public static final String NAME = "Liste des clients";
	public static final String URL = "client/list";

	private ClientGrid grid;

	private Button newClient;
	private Button searchButton;
	private TextField filter = new TextField();
	private Navigator navigator;

	public ClientList() {
		setSizeFull();
		HorizontalLayout topLayout = createTopBar();
		final VerticalLayout barAndGridLayout = new VerticalLayout();
		barAndGridLayout.addComponent(topLayout);
		addStyleName("crud-view");
		// if (!filter.getValue().trim().isEmpty()) {
		grid = new ClientGrid();
		filter.setWidth(100, UNITS_PERCENTAGE);
		filter.addShortcutListener(new ShortcutListener("", KeyCode.ENTER,
				new int[] {}) {

			/**
					 * 
					 */
					private static final long serialVersionUID = 6303106721111844488L;

			@Override
			public void handleAction(Object sender, Object target) {
				// TODO Auto-generated method stub
				barAndGridLayout.removeComponent(grid);

				grid = new ClientGrid(filter.getValue());
				if (grid.getContainerDataSource().size() > 0) {
					barAndGridLayout.addComponent(grid);

					barAndGridLayout.setExpandRatio(grid, 1);

				} else {
					Notification.show("Rien pour cette recherche",
							Notification.TYPE_WARNING_MESSAGE);
				}
				grid.addSelectionListener(new SelectionListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void select(SelectionEvent event) {
						// viewLogic.rowSelected(grid.getSelectedRow());
						Client client = (Client) grid.getSelectedRow();
						System.out.println("cool select");
						navigator = getUI().getNavigator();
						navigator.addView(ClientView.URL, ClientView.class);
						navigator.navigateTo(ClientView.URL + "/"
								+ client.getIdclient());
					}
				});
				filter.clear();
			}
		});

		barAndGridLayout.setMargin(true);
		barAndGridLayout.setSpacing(true);
		barAndGridLayout.setSizeFull();

		barAndGridLayout.setStyleName("crud-main-layout");
		addComponent(barAndGridLayout);
	}

	public CssLayout populateGrid() {
		CssLayout cssLayout = new CssLayout();
		grid = new ClientGrid();
		cssLayout.removeComponent(grid);
		grid = new ClientGrid(filter.getValue());
		cssLayout.addComponent(grid);
		return cssLayout;
	}

	public HorizontalLayout createTopBar() {

		filter.setStyleName("filter-textfield");
		filter.setInputPrompt("Chercher un client");
		// ResetButtonForTextField.extend(filter);
		filter.setImmediate(true);

		// filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
		// @Override
		// public void textChange(FieldEvents.TextChangeEvent event) {
		// // grid.setFilter(event.getText());
		// }
		// });

		// filter.setIcon(FontAwesome.SEARCH);

		newClient = new Button("Ajouter un client");
		newClient.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newClient.setIcon(FontAwesome.PLUS_CIRCLE);

		newClient.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				navigator = getUI().getNavigator();
				navigator.addView(AddClient.URL, AddClient.class);
				navigator.navigateTo(AddClient.URL);
			}
		});

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setSpacing(true);
		topLayout.setWidth("100%");
		topLayout.addComponent(filter);
		topLayout.addComponent(newClient);

		topLayout.setExpandRatio(filter, 2);
		topLayout.setStyleName("top-bar");
		return topLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public ClientGrid getGrid() {
		return grid;
	}

	public void setGrid(ClientGrid grid) {
		this.grid = grid;
	}

	public Button getNewClient() {
		return newClient;
	}

	public void setNewClient(Button newClient) {
		this.newClient = newClient;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

}
