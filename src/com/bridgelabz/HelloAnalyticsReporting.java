package com.bridgelabz;

import com.bridgelabz.*;
import java.io.File;
import java.io.IOException;
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
import com.google.api.services.analyticsreporting.v4.model.DimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;


public class HelloAnalyticsReporting {
	static Util u;
	private static final String APPLICATION_NAME = "Appystore test app";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String KEY_FILE_LOCATION = "/home/bridgeit/Desktop/springexp/HelloAnalytics/AppyGAReports-35a6c523765c.p12";
	private static final String SERVICE_ACCOUNT_EMAIL = "appystorereport@appygareports.iam.gserviceaccount.com";
	private static final String VIEW_ID = "ga:111820853";
	private static final Object POST_INDEX_PATH = null;

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
	 * OUR_API_KEY Initializes an authorized Analytics Reporting service object.
	 *
	 * @return The analytics reporting service object.
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private static AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
				.setServiceAccountScopes(AnalyticsReportingScopes.all()).build();
		// getting access token
		String refreshToken = null;
		credential.setRefreshToken(refreshToken);
		credential.refreshToken();
		// printing access token
		System.out.println(credential.getAccessToken());
		// printing
		Util u = new Util();
		
		//System.out.println(u.callURL("https://www.googleapis.com/analytics/v3/data/ga?ids=ga%3A111820853&start-date=2016-09-02&end-date=2016-09-05&metrics=ga%3AtotalEvents&dimensions=ga%3AeventCategory%2Cga%3Adimension1%2Cga%3Adate&filters=ga%3AeventCategory%3D%3DApp%20Open%3Bga%3Adimension15%3D%3D10&access_token="+credential.getAccessToken()));

		if (!credential.refreshToken()) {
			throw new RuntimeException("Failed OAuth to refresh the token");
		}
		// Construct the Analytics Reporting service object.
		return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}
	/**
	 * Query the Analytics Reporting API V4. Constructs a request for the
	 * sessions for the past seven days. Returns the API response.
	 *
	 * @param service
	 * @return GetReportResponse
	 * @throws IOException
	 */

	private static GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
		// Create the DateRange object.
		DateRange dateRange = new DateRange();
		dateRange.setStartDate("2016-09-02");
		dateRange.setEndDate("2016-09-03");
		// Create the Metrics object.
		Metric metric1 = new Metric().setExpression("ga:totalEvents");
	/*	Metric metric2 = new Metric() .setExpression("ga:screenviews");
		Metric metric3 = new Metric() .setExpression("ga:exits");
		Metric metric4 = new Metric() .setExpression("ga:exitRate");*/
		 // adding metric into metric ArrayList
		ArrayList<Metric> metriclist = new ArrayList<Metric>();
		metriclist.add(0, metric1);
	/*	metriclist.add(1,metric2); 
		metriclist.add(2,metric3);
		metriclist.add(3,metric4);*/
		 
		// Create the Dimensions object.
		Dimension dimens = new Dimension();
		Dimension dimens1 = new Dimension();
		Dimension dimens2 = new Dimension();
		ArrayList<Dimension> dimensList = new ArrayList<Dimension>();
		dimensList.add(dimens.setName("ga:eventCategory"));
		dimensList.add(dimens1.setName("ga:dimension1"));
		dimensList.add(dimens2.setName("ga:date"));
		//dimensList.add(dimens.set("ga:eventCategory", metric1));
	
		// created DimensionFilter object
		DimensionFilter dimensionFilter = new DimensionFilter();

		// creating ArrayList of DimensionFilter
		
		  ArrayList<DimensionFilter> dimFilters = new
		  ArrayList<DimensionFilter>();
		  dimFilters.add(dimensionFilter.set("ga:eventCategory==App Open", dimensList));
		  dimFilters.add(dimensionFilter.set("ga:dimension15==10",dimensList));
		  
		 //creating DimensionFilterClause object 
		  DimensionFilterClause  dimensionFilterPathClause = new DimensionFilterClause();
		  //dimensionFilterPathClause.getFilters();
		 
		 //making ArrayList of DimensionFilterClause
		  ArrayList<DimensionFilterClause> dmfilterclauselist= new ArrayList<DimensionFilterClause>();
		  //adding dimFilters to it
		  dmfilterclauselist.add(dimensionFilterPathClause.setFilters(dimFilters));
	
		// Create the ReportRequest object.
		ReportRequest request = new ReportRequest().setViewId(VIEW_ID)
			.setDateRanges(Arrays.asList(dateRange))
			.setMetrics(metriclist)
			.setDimensions(dimensList)
			.setDimensionFilterClauses(dmfilterclauselist);

		ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);
		// Create the GetReportsRequest object.
		GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
		// Call the batchGet method.
		GetReportsResponse response = service.reports().batchGet(getReport).execute();
		// Return the response.

		return response;
	}

	/**
	 * Parses and prints the Analytics Reporting API V4 response.
	 *
	 * @param response
	 *            the Analytics Reporting API V4 response.
	 */
	private static void printResponse(GetReportsResponse response) {
		System.out.println(response);
		Util u = new Util();
		GetReportsResponse k2 = response;
		for (Report report : response.getReports()) {
			ColumnHeader header = report.getColumnHeader();
			List<String> dimensionHeaders = header.getDimensions();
			List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
			List<ReportRow> rows = report.getData().getRows();
			if (rows == null) {
				System.out.println("No data found for " + VIEW_ID);
				return;
			}
			for (ReportRow row : rows) {
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
						// calling util method to read json
						u.postResponse(k2.toString());

					}
				}
			}
		}
	}

}