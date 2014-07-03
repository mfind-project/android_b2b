package pl.apricotsoftware.mfind.utils;

import pl.apricotsoftware.mfind.MainActivity;
import pl.apricotsoftware.mfind.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/*
 * Klasa pomocnicza do obsługi połączenia internetowgo
 */
public abstract class ConnectionUtils {

	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (connectivityManager != null) {

			networkInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (!networkInfo.isAvailable()) {
				networkInfo = connectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			}
		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}

	public static AlertDialog.Builder showConnectionDialog(Context context) {
		final Context ctx = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setTitle(ctx.getResources().getString(R.string.dialog_title));
		builder.setMessage(ctx.getResources().getString(R.string.dialog_msg));

		builder.setPositiveButton(
				ctx.getResources().getString(R.string.dialog_yes),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ctx.startActivity(new Intent(
								Settings.ACTION_WIFI_SETTINGS));
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(
				ctx.getResources().getString(R.string.dialog_no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ctx.startActivity(new Intent(ctx, MainActivity.class));
					}
				});
		return builder;
	}

}
