package it.gspRiva.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -783485413499621058L;
	
	private static HttpSession sessionTo;
	private static HttpServletRequest request;
	
	public static void setSession(HttpServletRequest req, String username, String password) {
		request = req;
		sessionTo = request.getSession();
		sessionTo.setMaxInactiveInterval(20*60);
		
		if (sessionTo.isNew()) {
	         sessionTo.setAttribute(username, password);
		} 
	}
	
	public static boolean getSessioneAttiva() {
		return false;
	}
	
	public static HttpSession getSessione() {
		return request.getSession();
	}
	
		  
}