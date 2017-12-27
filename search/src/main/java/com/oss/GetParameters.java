//
//  GetParameters.java
//  search
//
//  Created by sule
//
package com.oss;

import com.sun.prism.shader.AlphaOne_RadialGradient_AlphaTest_Loader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class GetParameters extends HttpServlet {

	private static final long serialVersionUID = 1L;

	NLPProcesses process = new NLPProcesses();

	public GetParameters() throws SQLException, ClassNotFoundException {
        super();
        // TODO Auto-generated constructor stub
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
       
        String repository = request.getParameter("repo");
        String selectedPL = request.getParameter("pl");
        String topics = request.getParameter("topic");

        ArrayList<ArrayList<String>> results = process.processAndResults(topics,selectedPL,repository);
        
        int numberOfResults = results.size();
        ArrayList<String> firstLine = new ArrayList<String>();
        firstLine.add(Integer.toString(numberOfResults));
        firstLine.add(" results found for: " + topics);
        firstLine.add("");
        results.add(0, firstLine);
        
        request.setAttribute("results", results);
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request,response);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        doGet(request,response);
    }
}	

