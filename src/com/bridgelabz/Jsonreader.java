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
			   System.out.println(listOfStates);
			   //for json array 
			   for(int i=0;i<listOfStates.size();i++)
			   {
				   JSONObject obj1=(JSONObject) listOfStates.get(i);
				   //printing data object 
				   System.out.println(obj1);
				   
				   JSONObject abc=(JSONObject) obj1.get("data");
				   System.out.println(abc);
				   JSONObject jsonObject1 = (JSONObject) abc;
				   JSONArray list1 = (JSONArray) jsonObject1.get("minimums");
				   System.out.println(list1);
				   JSONArray list2 = (JSONArray) jsonObject1.get("maximums");
				   System.out.println(list2);
				   JSONArray list3 = (JSONArray) jsonObject1.get("totals");
				   System.out.println(list3);		
				   JSONArray list5 = (JSONArray) jsonObject1.get("rows");
				   System.out.println(list5);
				     			
				   for(int j=0;j<list5.size();j++)
						 {
							   JSONObject obj5=(JSONObject) list5.get(j);
							   System.out.println(obj5);
							   JSONArray list6 = (JSONArray) obj5.get("metrics");
							   System.out.println(list6);
							   for(int l=0;l<list6.size();l++)
							   		{
							   			JSONObject obj11=(JSONObject) list6.get(l); 
							   			System.out.println(obj11);
							            JSONArray list16 = (JSONArray) obj11.get("values");
								   		System.out.println(list16);
								   		JSONArray list7 = (JSONArray) obj5.get("dimensions");
									    System.out.println(list7);
							   		}
							
						   }
				   JSONObject abc2=(JSONObject) obj1.get("columnHeader");
				   JSONObject jsonObject12 = (JSONObject) abc2;
				   System.out.println(jsonObject12);
				   JSONObject abc3=(JSONObject) jsonObject12.get("metricHeader");
				   JSONObject jsonObject13 = (JSONObject) abc3;
				   System.out.println(jsonObject13);
				   JSONArray list12 = (JSONArray) jsonObject13.get("metricHeaderEntries");
				   System.out.println(list12);
				  for(int k2=0;k2<list12.size();k2++)
				   {
					  JSONObject obj11=(JSONObject) list12.get(k2);  
					  System.out.println(obj11);
					/*  for(int k3=0;k3<obj11.size();k3++)
					  {
						  JSONObject obj12=(JSONObject) obj11.get(k3);  
						  System.out.println(obj12);
					  }*/
				
				   }
				   JSONArray listOfStates3 = (JSONArray) abc2.get("dimensions");
				   System.out.println(listOfStates3);
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