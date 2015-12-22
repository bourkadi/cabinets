package com.bourgadix.ui.cabinets.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class Settings extends CssLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4691562166459113687L;
	public static final String NAME = "Paramétrage";
	public static final String URL = "SETTINGS";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		//uploader();
		addComponent(getSettings());
	}
	public VerticalLayout getSettings() {
		VerticalLayout layout = new VerticalLayout();
		Button users = new Button("Gestion des utilisateurs");
		users.addStyleName(ValoTheme.BUTTON_LINK);
		layout.addComponent(users);	
		final Embedded image = new Embedded("Uploaded Image");
		image.setHeight("100px");
		image.setWidth("120px");

		FormLayout formLayout = new FormLayout();
		TextField cabinetName = new TextField("Nom du cabinet", "");
		cabinetName.setInputPrompt("Nom du Cabinet");
		ImageUploader receiver = new ImageUploader(); 
		
		receiver.setImage(image);
		Upload upload = new Upload("Logo", receiver);
		upload.addSucceededListener(receiver);
		

		formLayout.addComponent(cabinetName);
	
		formLayout.addComponent(upload);
		formLayout.addComponent(image);
		layout.addComponent(formLayout);
	
		return layout;

	}

/*	public void uploader() {
		// Show uploaded file in this placeholder
		final Embedded image = new Embedded("Uploaded Image");
		image.setVisible(false);

		// Implement both receiver that saves upload in a file and
		// listener for successful upload
		class ImageUploader implements Receiver, SucceededListener {
			*//**
			 * 
			 *//*
			private static final long serialVersionUID = 1886819434260358620L;
			public File file;

			public OutputStream receiveUpload(String filename, String mimeType) {
				// Create upload stream

				FileOutputStream fos = null; // Stream to write to
				try {
					// Open the file for writing.
					file = new File("tmp/uploads/" + filename);
					fos = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
							.show(Page.getCurrent());
					return null;
				}
				return fos; // Return the output stream to write to
			}

			public void uploadSucceeded(SucceededEvent event) {
				// Show the uploaded file in the image viewer
				image.setVisible(true);
				image.setSource(new FileResource(file));
				Notification.show(event.getFilename()+" uploaded with success", Notification.Type.HUMANIZED_MESSAGE);
			}

		}
		ImageUploader receiver = new ImageUploader(); 

		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Upload Image Here", receiver);
		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(receiver);
		        
		// Put the components in a panel
		Panel panel = new Panel("Cool Image Storage");
		Layout panelContent = new VerticalLayout();
		panelContent.addComponents(upload, image);
		panel.setContent(panelContent);
		addComponent(panel);
	}*/
}
