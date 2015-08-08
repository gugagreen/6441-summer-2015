package ca.concordia.lanterns.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -6984739323315979240L;
	
	public static final String UPLOAD_DIRECTORY = System.getProperty("user.home") + File.separator + "lanterns";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		response.sendRedirect("upload.jsp");
	};

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean error = false;
		// process only if its multipart content
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				// first check if saving directory exists
				File dir = new File(UPLOAD_DIRECTORY);
				if (!dir.exists()) {
					dir.mkdir();
				}
				
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						String name = new File(item.getName()).getName();
						String filePath = UPLOAD_DIRECTORY + File.separator + name;
						System.out.println("Saving file to: " + filePath);
						item.write(new File(filePath));
					}
					System.out.println("Finished saving file.");
				}

				// File uploaded successfully
				System.out.println("File Uploaded Successfully");
			} catch (Exception ex) {
				request.setAttribute("message", "File Upload Failed due to " + ex);
				error = true;
			}
		} else {
			error = true;
			request.setAttribute("message", "Sorry this Servlet only handles file upload request");
		}
		
		if (error) {
			response.sendRedirect("/error");
		} else {
			response.sendRedirect("/listfiles");
		}

	}
}
