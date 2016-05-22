package com.bourgadix.ui.cabinets.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.joda.time.LocalDate;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.User;
import com.bourgadix.dao.Variable;
import com.bourgadix.services.SettingsManagement;
import com.bourgadix.services.SettingsService;
import com.bourgadix.ui.cabinets.authentification.CurrentUser;
import com.bourgadix.ui.cabinets.prescription.PrescriptionView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class Settings extends CssLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4691562166459113687L;
	public static final String NAME = "Paramétrage";
	public static final String URL = "SETTINGS";
	private DaoService dao = new Dao();

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		// uploader();
		addComponent(getSettings());
	}

	public VerticalLayout getSettings() {
		VerticalLayout layout = new VerticalLayout();
		Button users = new Button("Gestion des utilisateurs");
		users.addStyleName(ValoTheme.BUTTON_LINK);
		layout.addComponent(users);
		final Embedded image = new Embedded();

		image.setSource(new ThemeResource("img/logo.png"));
		image.setHeight("100px");
		image.setWidth("120px");

		FormLayout formLayout = new FormLayout();
		TextField cabinetName = new TextField("Nom du cabinet", "");
		cabinetName.setInputPrompt("Nom du Cabinet");
		cabinetName.setValue(dao.get(Variable.class, 1).getValue());
		cabinetName.setWidth(300f, Unit.PIXELS);
		ImageUploader receiver = new ImageUploader();

		receiver.setImage(image);
		Upload upload = new Upload("Logo", receiver);
		upload.addSucceededListener(receiver);

		formLayout.addComponent(cabinetName);

		formLayout.addComponent(upload);
		formLayout.addComponent(image);
		formLayout.addComponent(startingHoursSelect());
		formLayout.addComponent(endingHoursSelect());
		formLayout.addComponent(startingDayOfWeek());
		formLayout.addComponent(endingDayOfWeek());
		Button saveSettings = new Button("Enregistrer les changements");
		/*saveSettings.addClickListener(
				saveSettings(cabinetName.getValue(), Integer.parseInt(startingHoursSelect().getValue().toString()),
						Integer.parseInt(endingHoursSelect().getValue().toString()),
						Integer.parseInt(startingDayOfWeek().getValue().toString()),
						Integer.parseInt(endingDayOfWeek().getValue().toString())));*/
		formLayout.addComponent(saveSettings);
		layout.addComponent(formLayout);

		return layout;

	}

	public NativeSelect startingHoursSelect() {
		NativeSelect sample = new NativeSelect("Veuillez choisir l'heure d'ouverture");
		for (int i = 0; i < 10; i++) {
			sample.addItem(i);
			sample.setItemCaption(i, "0" + i);
		}
		for (int i = 10; i < 24; i++) {
			sample.addItem(i);
			// sample.setItemCaption(i,""+i);
		}
		sample.setNullSelectionAllowed(false);
		sample.setValue(Integer.parseInt(dao.get(Variable.class, 2).getValue()));
		Notification.show("Data:=>"+sample.getValue().toString());
		sample.setImmediate(true);
		return sample;
	}

	public NativeSelect endingHoursSelect() {
		NativeSelect sample = new NativeSelect("Veuillez choisir l'heure de fermeture");
		for (int i = 0; i < 10; i++) {
			sample.addItem(i);
			sample.setItemCaption(i, "0" + i);
		}
		for (int i = 10; i < 24; i++) {
			sample.addItem(i);
			// sample.setItemCaption(i,""+i);
		}
		sample.setNullSelectionAllowed(false);
		sample.setValue(Integer.parseInt(dao.get(Variable.class, 3).getValue()));
		sample.setImmediate(true);
		return sample;
	}

	public NativeSelect startingDayOfWeek() {
		NativeSelect sample = new NativeSelect("Veuillez choisir le premier jour de la semaine");
		for (int i = 1; i < 8; i++) {
			sample.addItem(i);
		}
		sample.setItemCaption(1, "Lundi");
		sample.setItemCaption(2, "Mardi");
		sample.setItemCaption(3, "Mercredi");
		sample.setItemCaption(4, "Jeudi");
		sample.setItemCaption(5, "Vendredi");
		sample.setItemCaption(6, "Samedi");
		sample.setItemCaption(7, "Dimanche");
		sample.setValue(Integer.parseInt(dao.get(Variable.class, 4).getValue()));

		return sample;
	}

	public NativeSelect endingDayOfWeek() {
		NativeSelect sample = new NativeSelect("Veuillez choisir le dernier jour de la semaine");
		for (int i = 1; i < 8; i++) {
			sample.addItem(i);
		}
		sample.setItemCaption(1, "Lundi");
		sample.setItemCaption(2, "Mardi");
		sample.setItemCaption(3, "Mercredi");
		sample.setItemCaption(4, "Jeudi");
		sample.setItemCaption(5, "Vendredi");
		sample.setItemCaption(6, "Samedi");
		sample.setItemCaption(7, "Dimanche");
		sample.setValue(Integer.parseInt(dao.get(Variable.class, 5).getValue()));

		return sample;
	}

	public ClickListener saveSettings(final String name, final int startingHour, final int endingHour,
			final int firstDay, final int lastDay) {
		ClickListener clickListener = new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9127691339011140817L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SettingsService settingsService = new SettingsManagement();
				settingsService.updateSettings(name, startingHour, endingHour, firstDay, lastDay);
			}
		};
		return clickListener;
	}

	public static void main(String[] args) {
		LocalDate newDate = new LocalDate();
		System.out.println(newDate.withDayOfWeek(3).toString());
	}
	/*
	 * public void uploader() { // Show uploaded file in this placeholder final
	 * Embedded image = new Embedded("Uploaded Image"); image.setVisible(false);
	 * 
	 * // Implement both receiver that saves upload in a file and // listener
	 * for successful upload class ImageUploader implements Receiver,
	 * SucceededListener {
	 *//**
		* 
		*//*
		 * private static final long serialVersionUID = 1886819434260358620L;
		 * public File file;
		 * 
		 * public OutputStream receiveUpload(String filename, String mimeType) {
		 * // Create upload stream
		 * 
		 * FileOutputStream fos = null; // Stream to write to try { // Open the
		 * file for writing. file = new File("tmp/uploads/" + filename); fos =
		 * new FileOutputStream(file); } catch (final
		 * java.io.FileNotFoundException e) { new Notification(
		 * "Could not open file<br/>", e.getMessage(),
		 * Notification.Type.ERROR_MESSAGE) .show(Page.getCurrent()); return
		 * null; } return fos; // Return the output stream to write to }
		 * 
		 * public void uploadSucceeded(SucceededEvent event) { // Show the
		 * uploaded file in the image viewer image.setVisible(true);
		 * image.setSource(new FileResource(file));
		 * Notification.show(event.getFilename()+" uploaded with success",
		 * Notification.Type.HUMANIZED_MESSAGE); }
		 * 
		 * } ImageUploader receiver = new ImageUploader();
		 * 
		 * // Create the upload with a caption and set receiver later Upload
		 * upload = new Upload("Upload Image Here", receiver);
		 * upload.setButtonCaption("Start Upload");
		 * upload.addSucceededListener(receiver);
		 * 
		 * // Put the components in a panel Panel panel = new Panel(
		 * "Cool Image Storage"); Layout panelContent = new VerticalLayout();
		 * panelContent.addComponents(upload, image);
		 * panel.setContent(panelContent); addComponent(panel); }
		 */
}
