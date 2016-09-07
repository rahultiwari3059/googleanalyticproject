package com.bridgelabz;
import java.io.BufferedReader;
import java.io.FileNotFoundException;  
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  
import org.json.simple.parser.JSONParser;
public class Util 
{
	public void reader(String location) {
		JSONParser parser = new JSONParser();  
		try {  
			// taking json file and 
			Object obj = parser.parse(new FileReader(location));  
			JSONObject obj1 = (JSONObject) obj;
			// get a String from the JSON object
			String name = (String) obj1.get("Name");
			//printing that name string
			System.out.println(name);
			// get a String from the JSON object
			String metrics = (String) obj1.get("metrics");
			//printing metrics 
			System.out.println(metrics);
			// get an array from the JSON object
			JSONArray dimention8element = (JSONArray) obj1.get("dimention8"); 
			@SuppressWarnings("unchecked")
            // taking each value from the json array separately
			Iterator<String> iterator = dimention8element.iterator();  
			while (iterator.hasNext()) {  
				System.out.println(iterator.next());  
				}  
			// get an array from the JSON object
			JSONArray dimention = (JSONArray) obj1.get("dimensions");
			@SuppressWarnings("unchecked")
            // taking each value from the json array separately
			Iterator<String> iterator1 = dimention.iterator();  
			while (iterator1.hasNext()) {  
			System.out.println(iterator1.next());  
			} 
			// get an array from the JSON object
			JSONArray filters = (JSONArray) obj1.get("filters");
	       @SuppressWarnings("unchecked")
           // taking each value from the json array separately
	       Iterator<String> iterator2 = filters.iterator();  
	       while (iterator2.hasNext()) {  
	    	   System.out.println(iterator2.next());  
	       } 
		   }
	      catch (FileNotFoundException e) 
			{  
	    	  e.printStackTrace();  
			}
		 catch (Exception e1) 
			{  
			 e1.printStackTrace();  
			}    
	}
	// calling url
	  public  String callURL(String myURL) {
			//System.out.println("Requeted URL:" + myURL);
			StringBuilder sb = new StringBuilder();
			URLConnection urlConn = null;
			InputStreamReader in = null;
			try {
				URL url = new URL(myURL);
				urlConn = url.openConnection();
				if (urlConn != null)
					urlConn.setReadTimeout(60 * 1000);
				if (urlConn != null && urlConn.getInputStream() != null) {
					in = new InputStreamReader(urlConn.getInputStream(),
							Charset.defaultCharset());
					BufferedReader bufferedReader = new BufferedReader(in);
					if (bufferedReader != null) {
						int cp;
						while ((cp = bufferedReader.read()) != -1) {
								sb.append((char) cp);
						}
						bufferedReader.close();
					}
				}
			in.close();
			} catch (Exception e) {
				throw new RuntimeException("Exception while calling URL:"+ myURL, e);
			} 

			return sb.toString();
		}
	  public static void  postResponse(String  k22)
	  {
	      	 JSONParser parser = new JSONParser();  
	  		  try {  
	  			   Object obj = parser.parse(k22);  
	  			   JSONObject jsonObject = (JSONObject) obj;
	  			   JSONArray listOfStates = (JSONArray) jsonObject.get("reports");
	  			   //for json array 
	  			   for(int i=0;i<listOfStates.size();i++)
	  			   {
	  				   JSONObject obj1=(JSONObject) listOfStates.get(i);
	  				   
	  				   JSONObject obj2=(JSONObject) obj1.get("data");
	  				  
	  				   JSONObject jsonObject1 = (JSONObject) obj2;
	  				   JSONArray minimumsarray = (JSONArray) jsonObject1.get("minimums");
	  				   JSONArray maximumsarray = (JSONArray) jsonObject1.get("maximums");
	  				   JSONArray totalsarray = (JSONArray) jsonObject1.get("totals");
	  				   JSONArray rowsarray = (JSONArray) jsonObject1.get("rows");
	  				   		for(int j=0;j<rowsarray.size();j++)
	  				   		{
	  				   			JSONObject rowobject=(JSONObject) rowsarray.get(j);
	  				   			JSONArray metricsarray = (JSONArray) rowobject.get("metrics");
	  				   			for(int l=0;l<metricsarray.size();l++)
	  				   			{
	  				   				JSONObject obj4=(JSONObject) metricsarray.get(l); 
	  				   				JSONArray valuesarray = (JSONArray) obj4.get("values");
	  				   				System.out.println("sessions:"+valuesarray);
	  				   				JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");
	  				   				System.out.println("ga:sessionDurationBucket:"+dimensionsarray);
	  				   			}
	  				   		}
	  				   				JSONObject columnHeaderobject=(JSONObject) obj1.get("columnHeader");
	  				   				JSONObject dimentionobject = (JSONObject) columnHeaderobject;
	  				   				JSONObject metricHeaderobject=(JSONObject) dimentionobject.get("metricHeader");
	  				   				JSONObject metricHeaderEntriesobject = (JSONObject) metricHeaderobject;
	  				   				JSONArray metricHeaderEntriesarray = (JSONArray) metricHeaderEntriesobject.get("metricHeaderEntries");
	  				   				for(int k2=0;k2<metricHeaderEntriesarray.size();k2++)
	  				   				{
	  				   	   			JSONObject obj7=(JSONObject) metricHeaderEntriesarray.get(k2);
	  				   				}
	  				   				JSONArray dimensionsarray = (JSONArray) columnHeaderobject.get("dimensions");
	  				  }
	  		  	      }
	  		  		catch (Exception e) 
	  		  		{  
	  		  	     e.printStackTrace();  
	  		  		}
	      	}	
}


