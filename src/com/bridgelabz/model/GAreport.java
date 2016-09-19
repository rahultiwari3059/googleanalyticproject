package com.bridgelabz.model;

import java.util.Date;
// this is for creating table in database
/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;*/

public class GAreport {
	private String connectiontype;
	private String exit;
	private String exitrate;
	private String sessions;
	private String sessionid;
	private String eventcatogery;
	private String totalevent;
	private String androidId;
	private String gadiscription;
	private String gaid;
	private String ScreenViews;

	public GAreport() {
	}

	public GAreport(String gaid, String gadiscription, Date date,  String totalevent,String androidId,
			String eventcatogery, String sessionid, String sessions, String exitrate, String exit,
			String connectiontype,String ScreenViews) {
		setGaid(gaid);
		setGadiscription(gadiscription);
		setAndroidId(androidId);
		setTotalevent(totalevent);
		setEventcatogery(eventcatogery);
		setSessionid(sessionid);
		setSessions(sessions);
		setExitrate(exitrate);
		setExit(exit);
		setConnectiontype(connectiontype);
		setScrrenViews(ScreenViews);
	}
	public String getScrrenViews() {
		return ScreenViews;
	}

	public void setScrrenViews(String scrrenViews) {
		ScreenViews = scrrenViews;
	}

	public boolean isNew() {
		return gaid==null;
		}
	public void setConnectiontype(String connectiontype) {
		this.connectiontype = connectiontype;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public void setExitrate(String exitrate) {
		this.exitrate = exitrate;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public void setEventcatogery(String eventcatogery) {
		this.eventcatogery = eventcatogery;
	}

	public void setTotalevent(String totalevent) {
		this.totalevent = totalevent;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public void setGadiscription(String gadiscription) {
		this.gadiscription = gadiscription;
	}

	public void setGaid(String gaid) {
		this.gaid = gaid;
	}

	public String getConnectiontype() {
		return connectiontype;
	}

	public String getExit() {
		return exit;
	}

	public String getExitrate() {
		return exitrate;
	}

	public String getSessions() {
		return sessions;
	}

	public String getSessionid() {
		return sessionid;
	}

	public String getEventcatogery() {
		return eventcatogery;
	}

	public String getTotalevent() {
		return totalevent;
	}

	public String getAndroidId() {
		return androidId;
	}

	public String getGadiscription() {
		return gadiscription;
	}

	public String getGaid() {
		return gaid;
	}
}