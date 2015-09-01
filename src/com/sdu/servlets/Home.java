package com.sdu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;
import net.sf.json.util.JSONUtils;

import com.sdu.dbUtils.DBManager;
import com.sdu.utils.JsonUtils;
import com.sdu.utils.StaticVar;

public class Home extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBManager db = new DBManager();

	/**
	 * Constructor of the object.
	 */
	public Home() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("connected");
		
		String re = request.getParameter("REQUEST");
		
		JSONObject jo = JSONObject.fromObject(re);
		
		String back = "";
		
		if(jo.containsKey("Home")){
			back = db.getHomeAppBriefInfo();
		}else{
			back = db.getMoreAppBriefInfo(jo.getInt(StaticVar.PAGE));
		}
		
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(back);
		out.flush();
		out.close();
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
