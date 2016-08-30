package com.bridgelabz;
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  
import org.json.simple.parser.JSONParser;

public class Jsonreader
{
	
	public static void main(String[] args)
	{  
		  
		  JSONParser parser = new JSONParser();  
		  try {  
			  
			   Object obj = parser.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/Test.JSON"));  
			   JSONObject jsonObject = (JSONObject) obj;
			   //printing json content
			   System.out.println(jsonObject);
			   //taking json object
			   JSONArray listOfStates = (JSONArray) jsonObject.get("reports");
			   //printing json object report
			  // System.out.println(listOfStates);
			   //for json array 
			   for(int i=0;i<listOfStates.size();i++)
			   {
				   JSONObject obj1=(JSONObject) listOfStates.get(i);
				   //printing data object 
				  // System.out.println(obj1);
				   
				   JSONObject obj2=(JSONObject) obj1.get("data");
				 //  System.out.println(obj2);
				   JSONObject jsonObject1 = (JSONObject) obj2;
				   JSONArray jsnarr = (JSONArray) jsonObject1.get("minimums");
				 //  System.out.println(jsnarr);
				   JSONArray jsnarr1 = (JSONArray) jsonObject1.get("maximums");
				  // System.out.println(jsnarr1);
				   JSONArray jsnarr3 = (JSONArray) jsonObject1.get("totals");
				 //  System.out.println(jsnarr3);		
				   JSONArray jsnarr4 = (JSONArray) jsonObject1.get("rows");
				 //  System.out.println(jsnarr4);
				     			
				   for(int j=0;j<jsnarr4.size();j++)
						 {
							   JSONObject obj3=(JSONObject) jsnarr4.get(j);
							  // System.out.println(obj3);
							   JSONArray jsnarr5 = (JSONArray) obj3.get("metrics");
							 //  System.out.println(jsnarr5);
							   for(int l=0;l<jsnarr5.size();l++)
							   		{
							   			JSONObject obj4=(JSONObject) jsnarr5.get(l); 
							   			System.out.println(obj4);
							            JSONArray jsnarr6 = (JSONArray) obj4.get("values");
								   		System.out.println(jsnarr6);
								   		JSONArray jsnarr7 = (JSONArray) obj3.get("dimensions");
									    System.out.println(jsnarr7);
							   		}
							
						   }
				   JSONObject obj5=(JSONObject) obj1.get("columnHeader");
				   JSONObject jsonObject12 = (JSONObject) obj5;
				  // System.out.println(jsonObject12);
				   JSONObject obj6=(JSONObject) jsonObject12.get("metricHeader");
				   JSONObject jsonObject13 = (JSONObject) obj6;
				   //System.out.println(jsonObject13);
				   JSONArray jsnarr8 = (JSONArray) jsonObject13.get("metricHeaderEntries");
				   //System.out.println(jsnarr8);
				   		for(int k2=0;k2<jsnarr8.size();k2++)
				         {
					      JSONObject obj7=(JSONObject) jsnarr8.get(k2);  
					     // System.out.println(obj7);
				         }
				         JSONArray listOfStates3 = (JSONArray) obj5.get("dimensions");
				        // System.out.println(listOfStates3);
			     }
			  
		  	  }
			     catch (FileNotFoundException e) 
		  		   {  
				     e.printStackTrace();  
		  		   }
		  	     catch (Exception e) 
		  	       {  
			         e.printStackTrace();  
		           }
     }		
}