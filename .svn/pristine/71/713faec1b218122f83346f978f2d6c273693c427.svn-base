package com.bourgadix.ui.cabinets;

import javax.servlet.annotation.WebServlet;

import com.bourgadix.ui.cabinets.authentification.AccessControl;
import com.bourgadix.ui.cabinets.authentification.BasicAccessControl;
import com.bourgadix.ui.cabinets.authentification.LoginScreen;
import com.bourgadix.ui.cabinets.authentification.LoginScreen.LoginListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("cabinets")
public class CabinetsUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = CabinetsUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private AccessControl accessControl = new BasicAccessControl();

	@Override
	protected void init(VaadinRequest request) {
		Responsive.makeResponsive(this);
		setLocale(request.getLocale());
		getPage().setTitle("Cabinet");
		if (!accessControl.isUserSignedIn()) {
			setContent(new LoginScreen(accessControl, new LoginListener() {
				@Override
				public void loginSuccessful() {
					showMainView();
				}
			}));
		} else {
			showMainView();
		}
		// layout.addComponent(mainLayout);
	}

	protected void showMainView() {
		addStyleName(ValoTheme.UI_WITH_MENU);
		setContent(new MainScreen(CabinetsUI.this));

		getNavigator().navigateTo(getNavigator().getState());
	}

}