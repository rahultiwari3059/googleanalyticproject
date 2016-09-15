package com.bridgelabz;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  
import org.json.simple.parser.JSONParser;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

import net.sf.json.JSON;

public class HelloAnalyticsReport1 {
  private static final String APPLICATION_NAME = "AppyGAReports";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String KEY_FILE_LOCATION = "/home/bridgeit/Desktop/springexp/HelloAnalytics/AppyGAReports-35a6c523765c.p12";
  private static final String SERVICE_ACCOUNT_EMAIL = "appystorereport@appygareports.iam.gserviceaccount.com";
  private static final String VIEW_ID = "109302262";
  public static void main(String[] args) {
    try {
      Analytics service = initializeAnalyticsReporting();
     // GetReportsResponse response = getReport(service);
     // printResponse(response);
      String profileId="109302262";
      GaData gaData = executeDataQuery(service, profileId);
      printGaData(gaData);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  /**
   * Initializes an authorized Analytics Reporting service object.
   *
   * @return The analytics reporting service object.
   * @throws IOException
   * @throws GeneralSecurityException
   */
private static Analytics initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GoogleCredential credential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
        .setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
        .setServiceAccountScopes(AnalyticsReportingScopes.all())
        .build();
    if (!credential.refreshToken()) {
        throw new RuntimeException("Failed OAuth to refresh the token");
      }


    // Construct the Analytics Reporting service object.
    return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME).build();
  }

  /**
   * Query the Analytics Reporting API V4.
   * Constructs a request for the sessions for the past seven days.
   * Returns the API response.
   *
   * @param service
   * @return GetReportResponse
   * @throws IOException
   */

private static GaData executeDataQuery(Analytics analytics, String profileId) throws IOException {
    return analytics.data().ga().get("ga:" + profileId, // Table Id. ga: + profile id.
        "2012-08-27", // Start date.
        "2012-09-07", // End date.
        "ga:visits") // Metrics.
        .setDimensions("ga:sessions,ga:screenviews,ga:exits,ga:exitRate")
        .setSort("-ga:visits,ga:source")
        .setFilters("ga:screenName==SplashScreen")
        .setMaxResults(25)
        .execute();
  }

private static void printGaData(GaData results) {
    System.out.println(
        "printing results for profile: " + results.getProfileInfo().getProfileName());

    if (results.getRows() == null || results.getRows().isEmpty()) {
      System.out.println("No results Found.");
    } else {

      // Print column headers.
      for (ColumnHeaders header : results.getColumnHeaders()) {
        System.out.printf("%30s", header.getName());
      }
      System.out.println();

      // Print actual data.
      for (List<String> row : results.getRows()) {
        for (String column : row) {
          System.out.printf("%30s", column);
        }
        System.out.println();
      }

      System.out.println();
    }
  }












/*
  private static GetReportsResponse getReport(Analytics service) throws IOException {
   
	 // Create the DateRange object.
    DateRange dateRange = new DateRange();
    dateRange.setStartDate("7DaysAgo");
    dateRange.setEndDate("today");

    // Create the Metrics object.
    Metric sessions = new Metric()
        .setExpression("ga:sessions")
        .setAlias("sessions");

    //Create the Dimensions object.
    Dimension browser = new Dimension()
        .setName("ga:sessionDurationBucket");

    // Create the ReportRequest object.
    ReportRequest request = new ReportRequest()
        .setViewId(VIEW_ID)
        .setDateRanges(Arrays.asList(dateRange))
        .setDimensions(Arrays.asList(browser))
        .setMetrics(Arrays.asList(sessions));

    ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
    requests.add(request);

    // Create the GetReportsRequest object.
    GetReportsRequest getReport = new GetReportsRequest()
        .setReportRequests(requests);

    // Call the batchGet method.
    GetReportsResponse response = service.reports().batchGet(getReport).execute();

    // Return the response.
    return response;
  }
*/
  /**
   * Parses and prints the Analytics Reporting API V4 response.
   *
   * @param response the Analytics Reporting API V4 response.
   */
  private static void printResponse(GetReportsResponse response) {
	System.out.println(response);
	 GetReportsResponse k2 = response;
	for (Report report: response.getReports()) {
      ColumnHeader header = report.getColumnHeader();
      List<String> dimensionHeaders = header.getDimensions();
      List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
      List<ReportRow> rows = report.getData().getRows();
      if (rows == null) {
         System.out.println("No data found for " + VIEW_ID);
         return;
      }
      //System.out.println(rows);
      for (ReportRow row: rows) {
        List<String> dimensions = row.getDimensions();
        System.out.println(dimensions);
        List<DateRangeValues> metrics = row.getMetrics();
        for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
          System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
        }
        for (int j = 0; j < metrics.size(); j++) {
          System.out.print("Date Range (" + j + "): ");
          com.google.api.services.analyticsreporting.v4.model.DateRangeValues values = metrics.get(j);
          for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
            System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
          }
        }	
        // calling postresponce method to read json file 
        postResponse(k2.toString());
        }
    }
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