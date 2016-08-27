package com.bridgelabz;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonEnum {

	public static void main(String[] args) {
		readFile();
	}

	public static void readFile() {
		String jsonData = "";
	
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/Test.JSON"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			jsonData = sb.toString();
			System.out.println(jsonData);
			JSONObject jobj = new JSONObject(jsonData);
			JSONArray jarr = new JSONArray(jobj.getJSONArray("reports").toString());
			System.out.println(jarr);
			for (int i = 0; i <= jarr.length(); i++) {
				System.out.println("Keyword: " + jarr.getString(i));
		
				//JSONObject jobj1 = new JSONObject(data);
				
			}
			JSONObject jobj1 = new JSONObject("data");
			JSONArray jarr1 = new JSONArray(jobj1.getJSONArray("minimums").toString());
			System.out.println(jarr1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
