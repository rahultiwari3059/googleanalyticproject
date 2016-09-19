package com.bridgelabz.jsonreader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
	public void reader(String location) {
		JSONParser parser = new JSONParser();
		try {
			// taking json file and
			Object obj = parser.parse(new FileReader(location));
			JSONObject obj1 = (JSONObject) obj;
			// get a String from the JSON object
			String name = (String) obj1.get("Name");
			// printing that name string
			System.out.println(name);
			// get a String from the JSON object
			String metrics = (String) obj1.get("metrics");
			// printing metrics
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// calling url
	public String callURL(String myURL) {
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
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
			throw new RuntimeException("Exception while calling URL:" + myURL, e);
		}
		return sb.toString();
	}

	public static void postResponse(String k22) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(k22);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray listOfStates = (JSONArray) jsonObject.get("reports");
			// for json array
			for (int i = 0; i < listOfStates.size(); i++) {
				// converting into jsonobject at the position of i
				JSONObject obj1 = (JSONObject) listOfStates.get(i);
				// coverting into jsonobject
				JSONObject obj2 = (JSONObject) obj1.get("data");
				// conerting into json object
				JSONObject jsonObject1 = (JSONObject) obj2;
				@SuppressWarnings("unused")
				// converting into jsonarray
				JSONArray minimumsarray = (JSONArray) jsonObject1.get("minimums");
				@SuppressWarnings("unused")
				// converting into jsonarray
				JSONArray maximumsarray = (JSONArray) jsonObject1.get("maximums");
				@SuppressWarnings("unused")
				// converting into jsonarray
				JSONArray totalsarray = (JSONArray) jsonObject1.get("totals");
				// converting into jsonarray
				JSONArray rowsarray = (JSONArray) jsonObject1.get("rows");
				for (int j = 0; j < rowsarray.size(); j++) {
					JSONObject rowobject = (JSONObject) rowsarray.get(j);
					JSONArray metricsarray = (JSONArray) rowobject.get("metrics");
					for (int l = 0; l < metricsarray.size(); l++) {
						JSONObject obj4 = (JSONObject) metricsarray.get(l);
						JSONArray valuesarray = (JSONArray) obj4.get("values");
						// converting json array to String
						String session = JSONArray.toJSONString(valuesarray);
						// retrieving from array
						session = session.substring(session.indexOf("[") + 2, session.indexOf("]") -1);
						// printing value
						System.out.println(session);
						// printing sessions value
						System.out.println("sessions:" + valuesarray);
						// retrieving jsonarray from dimention array
						JSONArray dimensionsarray = (JSONArray) rowobject.get("dimensions");
						// converting dimention json array into string
						String sessionDurationBuck = JSONArray.toJSONString(dimensionsarray);
						// retrieving substring from string
						sessionDurationBuck = sessionDurationBuck.substring(sessionDurationBuck.indexOf("[") + 2,
								sessionDurationBuck.indexOf("]") - 1);
						// printing substring
						System.out.println(sessionDurationBuck);
						// printing sessiondurationbucket
						System.out.println("ga:sessionDurationBucket:" + dimensionsarray);
						createCsv(session, sessionDurationBuck);
					}
				}
				JSONObject columnHeaderobject = (JSONObject) obj1.get("columnHeader");
				JSONObject dimentionobject = (JSONObject) columnHeaderobject;
				JSONObject metricHeaderobject = (JSONObject) dimentionobject.get("metricHeader");
				JSONObject metricHeaderEntriesobject = (JSONObject) metricHeaderobject;
				JSONArray metricHeaderEntriesarray = (JSONArray) metricHeaderEntriesobject.get("metricHeaderEntries");
				for (int k2 = 0; k2 < metricHeaderEntriesarray.size(); k2++) {
					JSONObject obj7 = (JSONObject) metricHeaderEntriesarray.get(k2);
				}
				JSONArray dimensionsarray = (JSONArray) columnHeaderobject.get("dimensions");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createCsv(String session, String sessionDurationBuck) {
		try {
			// initializing the boolean value
			boolean b = false;
			// creating the new csv file
			File file = new File("/home/bridgeit/Music/report.csv");
			// checking whether file already existing or not
			if (!file.exists()) {
				b = true;
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			// if file doesn't exists, then create it and appending values
			if (b) {
				file.createNewFile();
				bw.append("sessions");
				bw.append("^");
				bw.append("sessionDurationBucket");
				bw.append("^");
				bw.newLine();
			}
			bw.append(session);
			bw.append("^");
			bw.append(sessionDurationBuck);
			bw.append("^");
			bw.newLine();
			bw.close();
			// if operation is completed then printing done
			System.out.println("Done");
		} catch (Exception e) {
		}
	}
}