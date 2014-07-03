package pl.apricotsoftware.mfind;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import pl.apricotsoftware.mfind.utils.ConnectionUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

/*
 * Klasa odpowiadająca za przesłanie danych zczytanych z kodu aztec na serwer
 */
public class SendingAztecActivity extends Activity {

	private static final String TAG = "SendingAztecActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step2_2);

		Intent intent = getIntent();
		String url = intent.getStringExtra(MainActivity.URL_EXTRA);
		String token = intent.getStringExtra(MainActivity.TOKEN_EXTRA);
		String message = intent
				.getStringExtra(MainActivity.CAPTURED_CODE_EXTRA);

		Log.d(TAG, "url:" + url);
		Log.d(TAG, "url:" + ((MyApplication) getApplication()).getURL_EXTRA());
		Log.d(TAG, "token:" + token);
		Log.d(TAG,
				"token:" + ((MyApplication) getApplication()).getTOKEN_EXTRA());
		Log.d(TAG, "message:" + message);
		Log.d(TAG,
				"message:"
						+ ((MyApplication) getApplication())
								.getCAPTURED_CODE_EXTRA());

		// Toast.makeText(this, "Wysłany URL: " + url,
		// Toast.LENGTH_LONG).show();
		// Toast.makeText(this, "Wysłany token: " + token,
		// Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (ConnectionUtils.isConnected(this)) {
			JSONPoster jsonPoster = new JSONPoster();
			jsonPoster
					.execute(
							((MyApplication) getApplication()).getURL_EXTRA(),
							((MyApplication) getApplication()).getTOKEN_EXTRA(),
							((MyApplication) getApplication())
									.getCAPTURED_CODE_EXTRA());
		} else {
			ConnectionUtils.showConnectionDialog(this).show();
		}
	}

	private void sentAztec() {
		Intent intent = new Intent(this, SentAztecActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_in, R.anim.push_out);
	}

	public void cancelSending(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	class JSONPoster extends AsyncTask<String, Integer, String> {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected String doInBackground(String... params) {
			String response = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(params[0]);
			JSONObject holder = new JSONObject();

			try {
				holder.put("token", params[1]);
				holder.put("aztec", params[2]);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			StringEntity se = null;
			try {
				se = new StringEntity(holder.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			httpost.setEntity(se);
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-type", "application/json");
			String authorizationString = "Basic "
					+ Base64.encodeToString(
							("ipadmini" + ":" + "miniipad").getBytes(),
							Base64.NO_WRAP);
			httpost.setHeader("Authorization", authorizationString);

			ResponseHandler responseHandler = new BasicResponseHandler();
			try {
				response = httpclient.execute(httpost, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d(TAG, "post return:" + result);
			sentAztec();
		}
	}

}
