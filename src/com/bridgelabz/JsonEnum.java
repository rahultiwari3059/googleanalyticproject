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
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
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

public class JsonEnum {
  private static final String APPLICATION_NAME = "AppyGAReports";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String KEY_FILE_LOCATION = "/home/bridgeit/Desktop/springexp/HelloAnalytics/AppyGAReports-35a6c523765c.p12";
  private static final String SERVICE_ACCOUNT_EMAIL = "appystorereport@appygareports.iam.gserviceaccount.com";
  private static final String VIEW_ID = "109302262";
  public static void main(String[] args) {
    try {
      AnalyticsReporting service = initializeAnalyticsReporting();

      GetReportsResponse response = getReport(service);
      printResponse(response);
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
private static AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

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
    return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
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
  private static GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
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

  /**
   * Parses and prints the Analytics Reporting API V4 response.
   *
   * @param response the Analytics Reporting API V4 response.
   */
  private static void printResponse(GetReportsResponse response) {
	System.out.println(response);
	 GetReportsResponse k2 = response;
	 //System.out.println(k2);
    for (Report report: response.getReports()) {
      ColumnHeader header = report.getColumnHeader();
     // System.out.println(header);
      List<String> dimensionHeaders = header.getDimensions();
     // System.out.println(dimensionHeaders);
      List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
      //System.out.println(metricHeaders);
      List<ReportRow> rows = report.getData().getRows();
      //System.out.println(rows);
     
	
      if (rows == null) {
         System.out.println("No data found for " + VIEW_ID);
         return;
      }

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
			  // System.out.println(k22);
			   Object obj = parser.parse(k22);  
			   JSONObject jsonObject = (JSONObject) obj;
			   //printing json content
			  // System.out.println(jsonObject);
			   //taking json object
			   JSONArray listOfStates = (JSONArray) jsonObject.get("reports");
			   //printing json object report
			  // System.out.println(listOfStates);
			   //for json array 
			   for(int i=0;i<listOfStates.size();i++)
			   {
				   JSONObject obj1=(JSONObject) listOfStates.get(i);
				   //printing data object 
				   //System.out.println(obj1);
				   JSONObject obj2=(JSONObject) obj1.get("data");
				   //System.out.println(obj2);
				   JSONObject jsonObject1 = (JSONObject) obj2;
				   JSONArray jsnarr = (JSONArray) jsonObject1.get("minimums");
				   //System.out.println(jsnarr);
				   JSONArray jsnarr1 = (JSONArray) jsonObject1.get("maximums");
				   //System.out.println(jsnarr1);
				   JSONArray jsnarr3 = (JSONArray) jsonObject1.get("totals");
				   //System.out.println(jsnarr3);		
				   JSONArray jsnarr4 = (JSONArray) jsonObject1.get("rows");
				   //System.out.println(jsnarr4);	
				          for(int j=0;j<jsnarr4.size();j++)
						  {
							   JSONObject obj3=(JSONObject) jsnarr4.get(j);
							   //System.out.println(obj3);
							   JSONArray jsnarr5 = (JSONArray) obj3.get("metrics");
							   //System.out.println(jsnarr5);
							       for(int l=0;l<jsnarr5.size();l++)
							   		{
							   			JSONObject obj4=(JSONObject) jsnarr5.get(l); 
							   	    	//System.out.println(obj4);
							            JSONArray jsnarr6 = (JSONArray) obj4.get("values");
								   		System.out.println("sessions:"+jsnarr6);
								   		JSONArray jsnarr7 = (JSONArray) obj3.get("dimensions");
									    System.out.println("ga:sessionDurationBucket:"+jsnarr7);
							   		}
						   }
				   			JSONObject obj5=(JSONObject) obj1.get("columnHeader");
				   			JSONObject jsonObject12 = (JSONObject) obj5;
				   			//System.out.println(jsonObject12);
				   			JSONObject obj6=(JSONObject) jsonObject12.get("metricHeader");
				   			JSONObject jsonObject13 = (JSONObject) obj6;
				   			//System.out.println(jsonObject13);
				   			JSONArray jsnarr8 = (JSONArray) jsonObject13.get("metricHeaderEntries");
				   			//System.out.println(jsnarr8);
				   			for(int k2=0;k2<jsnarr8.size();k2++)
				   			{
				   				JSONObject obj7=(JSONObject) jsnarr8.get(k2);  
				   				//System.out.println(obj7);
				   			}
				   				JSONArray listOfStates3 = (JSONArray) obj5.get("dimensions");
				   				//System.out.println(listOfStates3);
			   			}
			  
		  		}
		  		catch (Exception e) 
		  		{  
		  	     e.printStackTrace();  
		  		}
    	}		
}