package com.courseplanner;

import java.util.ArrayList;

import com.google.appengine.api.datastore.Text;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

public class MajorDatastoreBuilder {
	
	public static void jsonToDatastore( String jsonString) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		ArrayList<Entity> entitiesToWriteListA = new ArrayList<Entity>();
		ArrayList<Entity> entitiesToWriteListB = new ArrayList<Entity>();
		
		Entity majorParent = new Entity( "MajorTable");
		datastore.put( majorParent);
		
		Gson gson = new Gson();
		Major[] majorTable = gson.fromJson( jsonString, Major[].class);
		
		for ( int i = 0; i < majorTable.length; i++) {
			Entity major = new Entity( "Major", majorParent.getKey());
			entitiesToWriteListA.add( major);
			
			major.setProperty( "Name", majorTable[i].getName());
			major.setProperty( "Description", new Text(majorTable[i].getDescription()));
			
			String[] reqCourseStr = majorTable[i].getReqCourse();
			for (int j = 0; j < reqCourseStr.length; j++) {
				Entity reqCourse = new Entity( "ReqCourse", major.getKey());
				reqCourse.setProperty( "CourseID", new Text(reqCourseStr[j]));
				
				entitiesToWriteListB.add( reqCourse);
			}
			
			String[] electiveStr = majorTable[i].getElective();
			for (int j = 0; j < electiveStr.length; j++) {
				Entity elective = new Entity( "Elective", major.getKey());
				elective.setProperty( "CourseID", new Text(electiveStr[j]));
				
				entitiesToWriteListB.add( elective);
			}
			
			String[] miscElectiveStr = majorTable[i].getMiscElective();
			for (int j = 0; j < miscElectiveStr.length; j++) {
				Entity miscElective = new Entity( "MiscElective", major.getKey());
				miscElective.setProperty( "Description", new Text(miscElectiveStr[j]));
				
				entitiesToWriteListB.add( miscElective);
			}

		}
		
		datastore.put( entitiesToWriteListA);
		datastore.put( entitiesToWriteListB);
		
	}
}
