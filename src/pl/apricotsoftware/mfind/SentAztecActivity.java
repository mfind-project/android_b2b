package pl.apricotsoftware.mfind;

import pl.apricotsoftware.mfind.MyApplication.TrackerName;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.manateeworks.BarcodeScanner;
import com.manateeworks.cameraDemo.ActivityCapture;

/*
 * Ekran wyświetlany po przesłaniu danych z kodu aztec, umożliwia on rozpoczęcie kolejnego skanowania bądź zamknięcie aplikacji.
 */

public class SentAztecActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step3);
		setTracker(this.getClass().getSimpleName());

	}
	
	private void setTracker(String viewName) {
		Tracker t = ((MyApplication) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(viewName);
		t.send(new HitBuilders.AppViewBuilder().build());
	}

	public void closeApp(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
		overridePendingTransition(R.anim.push_in, R.anim.push_out);
	}

	public void startOver(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(R.anim.push_in, R.anim.push_out);
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		// closeApp(null);
		Intent intent = new Intent(this, ActivityCapture.class);
		intent.putExtra(ActivityCapture.SCANNING_CODE_EXTRA,
				BarcodeScanner.FOUND_AZTEC);
		startActivityForResult(intent, BarcodeScanner.FOUND_AZTEC);
		this.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (intent != null) {
			String message = null;

			Bundle extras = intent.getExtras();
			message = extras.getString(MainActivity.CAPTURED_CODE_EXTRA);

			if (requestCode == BarcodeScanner.FOUND_AZTEC) {

				Intent sendingIntent = new Intent(this,
						SendingAztecActivity.class);
				sendingIntent.putExtra(MainActivity.CAPTURED_CODE_EXTRA,
						message);

				sendingIntent.putExtra(MainActivity.URL_EXTRA,
						((MyApplication) getApplication()).getURL_EXTRA());
				sendingIntent.putExtra(MainActivity.TOKEN_EXTRA,
						((MyApplication) getApplication()).getTOKEN_EXTRA());
				startActivity(sendingIntent);

			} else {
				Toast.makeText(this, getString(R.string.toast_wrong_code),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			finish();
		}
	}

}
