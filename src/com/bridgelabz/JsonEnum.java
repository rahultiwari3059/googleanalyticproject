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
  public static void printResponse(GetReportsResponse response) {
	System.out.println(response);
	 JSONParser parser = new JSONParser();  
	  try {  
		  
		   Object obj = parser.parse("response");  
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
		   
	  	catch (Exception e) 
	  		{  
		   e.printStackTrace();  
	  
	  		}
	
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
        }
      }
   
  
    }
}

	
