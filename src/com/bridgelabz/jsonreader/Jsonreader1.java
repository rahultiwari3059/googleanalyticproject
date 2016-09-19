package com.bridgelabz.jsonreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Jsonreader1 {

	public static void main(String[] args) {
		Jsonreader1 js = new Jsonreader1();
		ArrayList<String> values = new ArrayList<String>();
		;
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader("/home/bridgeit/Desktop/springexp/HelloAnalytics/result1.JSON"));
			// converting object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;

			// covering report array into JSONArray
			JSONArray reportarray = (JSONArray) jsonObject.get("reports");

			for (int j = 0; j < reportarray.size(); j++) {
				// converting JSONArray into JSONObject
				JSONObject obj3 = (JSONObject) reportarray.get(j);
				// converting columnHeaderobject to JSONObject
				JSONObject columnHeaderobject = (JSONObject) obj3.get("columnHeader");
				// converting metricHeaderobject to JSONObject
				JSONObject metricHeaderobject = (JSONObject) columnHeaderobject.get("metricHeader");
				// covering metricHeaderEntries array into JSONArray
				JSONArray metricHeaderEntriesarray = (JSONArray) metricHeaderobject.get("metricHeaderEntries");

				for (int i = 0; i < metricHeaderEntriesarray.size(); i++) {
					JSONObject obj1 = (JSONObject) metricHeaderEntriesarray.get(i);

					String var1 = (String) obj1.get("name");
					System.out.println(var1);

				}
				JSONObject dataobject = (JSONObject) obj3.get("data");

				JSONArray reportarray1 = (JSONArray) dataobject.get("totals");

				System.out.println(reportarray1.size());

				for (int k = 0; k < reportarray1.size(); k++) {
					JSONObject obj9 = (JSONObject) reportarray1.get(k);

					JSONArray valuearray = (JSONArray) obj9.get("values");

					for (int n = 0; n < valuearray.size(); n++) {
						// adding into String arraylist
						values.add((String) valuearray.get(n));

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		for (String string : values) {
			System.out.println("values" + string);
		}
		// calling createcsv method
		js.createCsv(values);
	}

	public void createCsv(ArrayList<String> list) {
		try {
			System.out.println("Done");
			// initializing the boolean value
			boolean b = false;
			// creating the new csv file
			File file = new File("/home/bridgeit/Music/report2.csv");
			// checking whether file already existing or not
			if (!file.exists()) {
				b = true;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			// if file doesn't exists, then create it and appending values
			if (b) {
				file.createNewFile();
				bw.append("Number of users on Splash screen");
				bw.newLine();
				bw.append("sessions");
				bw.append("^");
				bw.append("screenviews");
				bw.append("^");
				bw.append("exits");
				bw.append("^");
				bw.append("exitRate");
				bw.append("^");
				bw.newLine();
			}
			bw.append(list.get(0));
			bw.append("^");
			bw.append(list.get(1));
			bw.append("^");
			bw.append(list.get(2));
			bw.append("^");
			bw.append(list.get(3));
			bw.append("^");

			bw.newLine();
			bw.close();
			// if operation is completed then printing done
			System.out.println("Done");
		} catch (Exception e) {
		}
	}
}