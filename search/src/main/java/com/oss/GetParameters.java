//
//  GetParameters.java
//  search
//
//  Created by sule
//
package com.oss;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class GetParameters extends HttpServlet {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GetParameters() {
        super();
        // TODO Auto-generated constructor stub
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
       
        String repository = request.getParameter("repo");
        String selectedPL[] = request.getParameterValues("pl");
        String topics = request.getParameter("topic");
        String categories[] = request.getParameterValues("cat");
        
        
        //request.setAttribute("repo", repository);
        //request.setAttribute("pls", selectedPL);
        //request.setAttribute("topic", topics);
        
        SearchDB search = new SearchDB(topics, repository, selectedPL, categories);
        ArrayList<String> results = search.getResults();        
        
        request.setAttribute("results", results);
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request,response);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        doGet(request,response);
    }
}	

