package pl.apricotsoftware.mfind;

import pl.apricotsoftware.mfind.utils.ConnectionUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manateeworks.BarcodeScanner;
import com.manateeworks.cameraDemo.ActivityCapture;

/*
 * Pierwszy funkcjonalny ekran aplikacji tzw. ekran główny prezentowany po zamknięciu {@link SplashActivity}
 */

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	public static final String URL_EXTRA = "url";
	public static final String TOKEN_EXTRA = "token";
	public static final String CAPTURED_CODE_EXTRA = "message";

	public MainActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step1);

		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// Intent intent = new Intent(this, ActivityCapture.class);
		// startActivityForResult(intent, BarcodeScanner.FOUND_QR);
		overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
	}

	public void scanNow(View view) {
		if (ConnectionUtils.isConnected(this)) {
			Intent intent = new Intent(this, ActivityCapture.class);
			startActivityForResult(intent, BarcodeScanner.FOUND_QR);
			overridePendingTransition(R.anim.push_in, R.anim.push_out);
		} else {
			ConnectionUtils.showConnectionDialog(this).show();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		String message = null;
		Log.d(TAG, "" + resultCode);

		if (intent != null) {
			if (requestCode == BarcodeScanner.FOUND_QR) {
				Bundle extras = intent.getExtras();
				message = extras.getString("message");

				String url = null;
				String token = null;
				if (message != null && message.contains("?")) {
					url = message.substring(0, message.indexOf("?"));
					token = message.substring(message.indexOf("=") + 1);

					Intent capturedQRIntent = new Intent(this,
							ReadyToScanActivity.class);
					capturedQRIntent.putExtra(URL_EXTRA, url);
					capturedQRIntent.putExtra(TOKEN_EXTRA, token);
					((MyApplication) getApplication()).setURL_EXTRA(url);
					((MyApplication) getApplication()).setTOKEN_EXTRA(token);
					startActivity(capturedQRIntent);
					overridePendingTransition(R.anim.push_in, R.anim.push_out);
				} else {
					Toast.makeText(this,
							getString(R.string.toast_wrong_code_data),
							Toast.LENGTH_LONG).show();
				}

			} else {
				Toast.makeText(this, getString(R.string.toast_wrong_code),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			// finish();
		}
	}

}
