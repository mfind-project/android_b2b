package pl.apricotsoftware.mfind;

import android.app.Application;

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

}
