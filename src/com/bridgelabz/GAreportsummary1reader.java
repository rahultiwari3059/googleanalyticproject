package com.bridgelabz;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GAreportsummary1reader {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		ArrayList<String> dimentionarraylist = new ArrayList<String>();
		ArrayList<String> metricarraylist = new ArrayList<String>();
		ArrayList<String> dimensionfilterarraylist = new ArrayList<String>();
		try {
			Object obj = parser
					.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/GAreportsummary1.JSON"));
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;
			// System.out.println(jsonObject);
			//// reading GAReportInfoarray
			JSONArray GAReportInfoarray = (JSONArray) jsonObject.get("GAReportInfo");
			// System.out.println(GAReportInfoarray);
			for (int i = 0; i < GAReportInfoarray.size(); i++) {
				JSONObject GAReportInfoobject = (JSONObject) GAReportInfoarray.get(i);
				System.out.println(GAReportInfoobject);

				String id = (String) GAReportInfoobject.get("GAID");
				System.out.println(id);
				String name = (String) GAReportInfoobject.get("GAdiscription");
				System.out.println(name);
				JSONArray dimensionsarray = (JSONArray) GAReportInfoobject.get("dimension");

				for (int j = 0; j < dimensionsarray.size(); j++) {
					System.out.println(dimensionsarray.get(j));
					dimentionarraylist.add((String) dimensionsarray.get(j));
				}
				JSONArray metricarray = (JSONArray) GAReportInfoobject.get("metric");
				for (int k = 0; k < metricarray.size(); k++) {
					System.out.println(metricarray.get(k));
					metricarraylist.add((String) metricarray.get(k));
				}
				JSONArray dimensionfilterarray = (JSONArray) GAReportInfoobject.get("dimensionfilter");
				System.out.println(dimensionfilterarray.size());
				for (int l = 0; l < dimensionfilterarray.size(); l++) {
					System.out.println(dimensionfilterarray.get(l));
					dimensionfilterarraylist.add((String) dimensionfilterarray.get(l));
				}
			}

		} catch (Exception e) {
e.printStackTrace();
		}
	}
}
