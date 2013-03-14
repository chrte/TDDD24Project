package com.TDDD24Project.server;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

public class SampleUploadServlet extends UploadAction {

	private static final long serialVersionUID = 1L;

	Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	/**
	 * Maintain a list with received files and their content types. 
	 */
	Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

	/**
	 * Override executeAction to save the received files in a custom place
	 * and delete this items from session.  
	 */
	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		int cont = 0;
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				cont++;
				try {

					//Create a new file based on the remote file name in the client
					String saveName = item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
					System.out.println("Save name : "+saveName);
					File file =new File("images/" + saveName);

					item.write(file);
					/// Save a list with the received files
					receivedFiles.put(item.getFieldName(), file);
					receivedContentTypes.put(item.getFieldName(), item.getContentType());

					/// Compose a xml message with the full file information
					response += "<file-" + cont + "-field>" + item.getFieldName() + "</file-" + cont + "-field>\n";
					response += "<file-" + cont + "-name>" + item.getName() + "</file-" + cont + "-name>\n";
					response += "<file-" + cont + "-size>" + item.getSize() + "</file-" + cont + "-size>\n";
					response += "<file-" + cont + "-type>" + item.getContentType() + "</file-" + cont + "type>\n";
				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}
		}

		/// Remove files from session because we have a copy of them
		removeSessionFileItems(request);

		/// Send information of the received files to the client.
		return "<response>\n" + response + "</response>\n";
	}
}