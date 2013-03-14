package com.TDDD24Project.client;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class UploadImage extends Composite {
	
	// A panel where the thumbnails of uploaded images will be shown
	  private FlowPanel panelImages = new FlowPanel();
	  private UserWidget parent;

	  public UploadImage(UserWidget object) {
		  this.parent=  object;
	    // Attach the image viewer to the document
	    RootPanel.get("main").add(panelImages);
	    
	    // Create a new uploader panel and attach it to the document
	    MultiUploader defaultUploader = new MultiUploader();
	    RootPanel.get("main").add(defaultUploader);

	    // Add a finish handler which will load the image once the upload finishes
	    defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
	    initWidget(defaultUploader);
	  }

	  // Load the image in the document and in the case of success attach it to the viewer
	  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {

	        new PreloadedImage(uploader.fileUrl(), showImage);
	        
	        // The server sends useful information to the client by default
	        UploadedInfo info = uploader.getServerInfo();
	        System.out.println("File name " + info.name);
	        System.out.println("File content-type " + info.ctype);
	        System.out.println("File size " + info.size);

	        // You can send any customized message and parse it 
	        System.out.println("Server message " + info.message);
	        parent.setUserImage("images/"+info.name);
	      }
	    }
	  };

	  // Attach an image to the pictures viewer
	  private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
	    public void onLoad(PreloadedImage image) {
	      image.setWidth("75px");
	      panelImages.add(image);
	    }
	  };
}
