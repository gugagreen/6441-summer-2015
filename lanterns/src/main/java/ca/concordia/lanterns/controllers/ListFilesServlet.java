package ca.concordia.lanterns.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListFilesServlet extends HttpServlet {
	private static final long serialVersionUID = -6984739323315979240L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		// first check if saving directory exists
		File dir = new File(FileUploadServlet.UPLOAD_DIRECTORY);
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			
			HttpSession session = request.getSession();
			session.setAttribute("files", files);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("listfiles.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("message", "Unable to show files");
			response.sendRedirect("/error.jsp");
		}
	};
}
