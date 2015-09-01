package com.sdu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sdu.dbUtils.DBManager;

public class AppDetail extends HttpServlet {

	private DBManager db = new DBManager();
	
	/**
	 * Constructor of the object.
	 */
	public AppDetail() {
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
		
	 	request.setCharacterEncoding("UTF-8");   
		String re = request.getParameter("REQUEST");
		
		JSONObject jo = JSONObject.fromObject(re);
		
		String back = "";
		
		String type = jo.getString("TYPE");
		
		
		if(type.equals("DETAIL")){
			String appID = jo.getString("APPID");
			back = db.getAppDetailInfo(appID);
		}else if(type.equals("CommentComment")){
			String uid = jo.getString("userID");
			String cid = jo.getString("cid");
			String content = jo.getString("content");
			
			back = db.commentBack(uid,cid,content);
		}else if(type.equals("Comment")){
			String uid = jo.getString("userID");
			String aid = jo.getString("aid");
			String content = jo.getString("content");
			String time = jo.getString("comTime");
			back = db.comment(uid,aid,content,time);
		}else if(type.equals("Zan")){
			String cid = jo.getString("cid");
			
			back = db.zan(cid);
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
