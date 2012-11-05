package mobile.meteo.org.receivers;

import java.util.concurrent.TimeUnit;

import mobile.meteo.org.managers.AsyncWeatherLoader;
import mobile.meteo.org.managers.WidgetsUpdater;
import mobile.meteo.org.sevices.WidgetService41;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionReceiver extends BroadcastReceiver {	
	
	Alarm al = new Alarm();

	@Override
	public void onReceive(Context context, Intent intent) {
		
		try {
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);		 
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			boolean isConnected = activeNetwork.isConnectedOrConnecting();
			
			if(isConnected) {
				
				AsyncWeatherLoader weatherLoader = new AsyncWeatherLoader();
				weatherLoader.execute(context);
				weatherLoader.get(2000, TimeUnit.MILLISECONDS);
				
				updateWidgets(context);
				
				al.SetAlarm(context);
				
			} else {
				al.CancelAlarm(context);
			}
			
		} catch (Exception e) {
			al.CancelAlarm(context);
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param context
	 */
	
	private void updateWidgets(Context context) {
		
		WidgetsUpdater wiUp = new WidgetsUpdater(context);		
		wiUp.updateWidgets();
		wiUp = null;

	}
	

}