package com.bourgadix.ui.cabinets.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class ImageUploader implements Receiver, SucceededListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1886819434260358620L;
	public File file;
	private  Embedded image = new Embedded("Uploaded Image");

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

	public void setImage(Embedded image) {
		this.image = image;
	}

	public Embedded getImage() {
		return image;
	}

	public void uploadSucceeded(SucceededEvent event) {
		// Show the uploaded file in the image viewer
		// image.setVisible(true);
		image.setSource(new FileResource(file));
		Notification.show(event.getFilename() + " uploaded with success", Notification.Type.HUMANIZED_MESSAGE);
	}

}
