package pl.apricotsoftware.mfind;

import pl.apricotsoftware.mfind.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
 * Ekran prezentowany po uruchomieniu aplikacji tzw. splash screen  
 */

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				startActivity(intent);
				SplashActivity.this.overridePendingTransition(R.anim.push_in,
						R.anim.push_out);
				SplashActivity.this.finish();
			}
		}).start();
	}
}
