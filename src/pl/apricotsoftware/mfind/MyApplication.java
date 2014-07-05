package pl.apricotsoftware.mfind;

import java.util.HashMap;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/*
 * Globalny obiekt aplikacji
 */

public class MyApplication extends Application {

	private String URL_EXTRA;
	private String TOKEN_EXTRA;
	private String CAPTURED_CODE_EXTRA;

	public String getURL_EXTRA() {
		return URL_EXTRA;
	}

	public void setURL_EXTRA(String uRL_EXTRA) {
		URL_EXTRA = uRL_EXTRA;
	}

	public String getTOKEN_EXTRA() {
		return TOKEN_EXTRA;
	}

	public void setTOKEN_EXTRA(String tOKEN_EXTRA) {
		TOKEN_EXTRA = tOKEN_EXTRA;
	}

	public String getCAPTURED_CODE_EXTRA() {
		return CAPTURED_CODE_EXTRA;
	}

	public void setCAPTURED_CODE_EXTRA(String cAPTURED_CODE_EXTRA) {
		CAPTURED_CODE_EXTRA = cAPTURED_CODE_EXTRA;
	}
	
	public enum TrackerName {
		APP_TRACKER, 
		GLOBAL_TRACKER, 
		ECOMMERCE_TRACKER, 
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

			Tracker t = analytics.newTracker(R.xml.global_tracker);
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);
	}

}
