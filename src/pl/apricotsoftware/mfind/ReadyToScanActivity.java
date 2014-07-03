package pl.apricotsoftware.mfind;

import pl.apricotsoftware.mfind.R;
import pl.apricotsoftware.mfind.utils.ConnectionUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manateeworks.BarcodeScanner;
import com.manateeworks.cameraDemo.ActivityCapture;

public class ReadyToScanActivity extends Activity {

	private static final String TAG = "ReadyToScanActivity";
	private String mUrl;
	private String mToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step2);

		Intent intent = getIntent();
		mUrl = intent.getStringExtra(MainActivity.URL_EXTRA);
		mToken = intent.getStringExtra(MainActivity.TOKEN_EXTRA);

		// Toast.makeText(this, "URL: "+mUrl, Toast.LENGTH_LONG).show();
		// Toast.makeText(this, "token: "+mToken, Toast.LENGTH_LONG).show();
	}

	public void scanAZTEC(View view) {
		if (ConnectionUtils.isConnected(this)) {
			Intent intent = new Intent(this, ActivityCapture.class);
			intent.putExtra(ActivityCapture.SCANNING_CODE_EXTRA,
					BarcodeScanner.FOUND_AZTEC);
			startActivityForResult(intent, BarcodeScanner.FOUND_AZTEC);
			overridePendingTransition(R.anim.push_in, R.anim.push_out);
		} else {
			ConnectionUtils.showConnectionDialog(this).show();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.d(TAG, "" + resultCode);
		if (intent != null) {
			String message = null;

			Bundle extras = intent.getExtras();
			message = extras.getString(MainActivity.CAPTURED_CODE_EXTRA);
			((MyApplication) getApplication()).setCAPTURED_CODE_EXTRA(message);
			if (requestCode == BarcodeScanner.FOUND_AZTEC) {

				Intent sendingIntent = new Intent(this,
						SendingAztecActivity.class);
				sendingIntent.putExtra(MainActivity.CAPTURED_CODE_EXTRA,
						message);
				sendingIntent.putExtra(MainActivity.URL_EXTRA, mUrl);
				sendingIntent.putExtra(MainActivity.TOKEN_EXTRA, mToken);
				startActivity(sendingIntent);
				overridePendingTransition(R.anim.push_in, R.anim.push_out);
			} else {
				Toast.makeText(this, getString(R.string.toast_wrong_code),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			finish();
		}
	}

}
