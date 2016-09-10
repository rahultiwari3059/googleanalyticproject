package com.bridgelabz;
import com.bridgelabz.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.sun.net.httpserver.Filter;

public class HelloAnalyticsReporting {
	static Util u;
  private static final String APPLICATION_NAME = "AppyStore";
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
  /**OUR_API_KEY
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
        //getting access token
    	String refreshToken = null;
    	credential.setRefreshToken(refreshToken);
    	credential.refreshToken();
    	//printing access token
    	//System.out.println(credential.getAccessToken());
        //printing 
    	 Util u= new Util();
    	  System.out.println(u.callURL("https://www.googleapis.com/analytics/v3/management/segments?max-results=3&start-index=1&key=SRvcnEEyfsyNZXEi86VA-1NH&access_token="+credential.getAccessToken()));
    	if (!credential.refreshToken()) {
        throw new RuntimeException("Failed OAuth to refresh the token");
      }
      //Construct the Analytics Reporting service object.
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
    dateRange.setStartDate("7daysAgo");
    dateRange.setEndDate("today");
    // Create the Metrics object.
    Metric sessions = new Metric()
     .setExpression("ga:bounces")
     .setExpression("ga:totalEvents")
     .setExpression("ga:hits")
     .setAlias("sessions");
     //Create the Dimensions object.
    Dimension sessionDurationBucket = new Dimension()
     .setName("ga:sessionDurationBucket");
    // Create the ReportRequest object.
    ReportRequest request = new ReportRequest()
        .setViewId(VIEW_ID)
        .setDateRanges(Arrays.asList(dateRange))
        .setDimensions(Arrays.asList(sessionDurationBucket))
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
	Util u = new Util();
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
        	  //calling util method to read json
        	 u.postResponse(k2.toString());

          }
        }
      }
    }
  }

} 