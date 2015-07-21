package com.bourgadix.ui.cabinets;

import com.bourgadix.ui.cabinets.clients.ClientList;
import com.bourgadix.ui.cabinets.rdv.Rdv;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class MainScreen extends HorizontalLayout {
	private Menu menu;
	
	 public MainScreen(CabinetsUI ui) {
		   setStyleName("main-screen");

	        CssLayout viewContainer = new CssLayout();
	        viewContainer.addStyleName("valo-content");
	        viewContainer.setSizeFull();
	
	        final Navigator navigator = new Navigator(ui, viewContainer);
	        navigator.setErrorView(ErrorView.class);

	        menu=new Menu(navigator);
	     //   menu.addView(AddClient.class,  AddClient.URL, AddClient.NAME, FontAwesome.PLUS);
	        menu.addView(Rdv.class, Rdv.URL, Rdv.NAME, FontAwesome.CALENDAR);
	        menu.addView(ClientList.class,  ClientList.URL, ClientList.NAME, FontAwesome.LIST);


	        
	        navigator.addViewChangeListener(viewChangeListener);

	        addComponent(menu);
	        addComponent(viewContainer);
	        setExpandRatio(viewContainer, 1);
	        setSizeFull();
	 }
	// notify the view menu about view changes so that it can display which view
	    // is currently active
	    ViewChangeListener viewChangeListener = new ViewChangeListener() {

	        @Override
	        public boolean beforeViewChange(ViewChangeEvent event) {
	            return true;
	        }

	        @Override
	        public void afterViewChange(ViewChangeEvent event) {
	            menu.setActiveView(event.getViewName());
	        }

	    };
}
