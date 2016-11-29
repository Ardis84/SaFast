package safast.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SetCookie
 */
public class SetCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCookie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Cookie[] cookies = request.getCookies();
		
		HashMap<String, Object> map = new HashMap<>();
		
		if(cookies!=null && cookies.length>0){
			for (Cookie cookie : cookies) {
					map.put(cookie.getName(), cookie.getValue());
			}
		}
		
		
		String cookie = (String)request.getParameter("cookie");
		String[] arCookie = cookie.split("\\|");
		for (int i = 0; i < arCookie.length; i++) {
			String el = arCookie[i];
			String[] erEl = el.split("-");
			String name = erEl[0];
			String value = erEl[1];
			
			if(map.isEmpty() || !map.containsKey(name)){
				Cookie ck = new Cookie(name,value);
				ck.setMaxAge(60*60); //1 hour
				response.addCookie(ck);
			}
		}
		
		out.println("Cookie Creati");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
