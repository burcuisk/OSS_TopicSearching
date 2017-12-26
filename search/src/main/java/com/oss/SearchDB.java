//
//  SearchDB.java
//  search
//
//  Created by sule
//
package com.oss;

import java.util.ArrayList;

class SearchDB {
	String topic;
	String repository;
	String[] PL;
	String[] category;

	SearchDB(String topic, String repository, String[] PL, String[] category) {
		// TODO Auto-generated constructor stub
		this.topic = topic;
		this.repository = repository;
		this.PL = PL;
		this.category = category;	
	}


	ArrayList<String> getResults(){
		
		// get results from DB
		
		ArrayList<String> results = new ArrayList<String>();
		for(int i = 0; i<=2; i++){
			results.add(topic+" "+repository+" "+PL[0]+" "+category[0]);
		}
		 return results;
	}
}