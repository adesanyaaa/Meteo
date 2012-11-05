package mobile.meteo.org.sevices;

import mobile.meteo.org.R;
import mobile.meteo.org.activity.Preferences;
import mobile.meteo.org.managers.DataManager;
import mobile.meteo.org.objects.WeatherItem;
import mobile.meteo.org.receivers.Widget41;
import mobile.meteo.org.statics.Misk;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public class WidgetService41 extends WidgetService {
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);	

		try {	
			
			//// prefs			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			
			boolean widgetbg = prefs.getBoolean("widgetbg", true);
			int bg_opacity = prefs.getInt("bg_opacity", 50);	
			
			/////////////////
			
			int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);		
			
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
			
			DataManager dataManager = new DataManager(this);
			
			WeatherItem currentDayWeatherItem = dataManager.getCurrentWeatherItem();
			WeatherItem nextWI = dataManager.getNextDayPeriodWeatherItem();
							
			dataManager.closeDatabase();
			dataManager = null;
			
			/*
			Intent activIntent = new Intent(this.getApplicationContext(), PogodnikActivity.class);
			*/
			Intent activIntent = new Intent(this.getApplicationContext(), Preferences.class);			
			PendingIntent activPendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, activIntent, 0);
			
			Intent clickIntent = new Intent(this.getApplicationContext(),Widget41.class);
			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,allWidgetIds);			
			PendingIntent updatePendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);			
	
			for (int widgetId : allWidgetIds) {
				
				if(currentDayWeatherItem != null) {
				
					// Create some random data
					RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(),R.layout.widget41);	
					
					if(widgetbg) {
						remoteViews.setImageViewBitmap(R.id.widget_bg, getBackground(bg_opacity));
					} else {
						remoteViews.setImageViewBitmap(R.id.widget_bg, getBackground(0));
					}
					
					remoteViews.setTextViewText(R.id.w_temp, currentDayWeatherItem.currentTemp);				
					remoteViews.setTextViewText(R.id.w_nextTemp,  Misk.getNextDayPeriodAstring(this) + " " +/* nextDayPeriodDetail.temp */ nextWI.currentTemp);				
					remoteViews.setTextViewText(R.id.w_city, currentDayWeatherItem.city);				
					remoteViews.setTextViewText(R.id.w_preshure, Double.toString(currentDayWeatherItem.preshure) + getString(R.string.mmrtst));
					remoteViews.setTextViewText(R.id.w_wet, Double.toString(currentDayWeatherItem.wet) + "%");
					remoteViews.setTextViewText(R.id.w_wind, Integer.toString(currentDayWeatherItem.wind_direction) + (char) 0x00B0 + ", " + Integer.toString(currentDayWeatherItem.wind_speed) + getString(R.string.mps));			
					remoteViews.setTextViewText(R.id.update_date, getString(R.string.updated) + Misk.getDate(currentDayWeatherItem.inserted) );
					
					remoteViews.setImageViewResource(R.id.w_weatherIcon, Misk.getWeatherIconByWeatherId(currentDayWeatherItem.weather_icon));					
					
					remoteViews.setOnClickPendingIntent(R.id.w_4x1_city_icon, activPendingIntent);	
					remoteViews.setOnClickPendingIntent(R.id.w_4x1_temps, activPendingIntent);	
					remoteViews.setOnClickPendingIntent(R.id.w_4x1_city_weather_temps, activPendingIntent);	
					
					remoteViews.setOnClickPendingIntent(R.id.update_widget, updatePendingIntent);
					
					appWidgetManager.updateAppWidget(widgetId, remoteViews);
					
				} else {
					RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(),R.layout.widget_no_data);	
					remoteViews.setOnClickPendingIntent(R.id.widget_nd_update_btn, updatePendingIntent);
					appWidgetManager.updateAppWidget(widgetId, remoteViews);					
				}
				
			}
			
		} catch(Exception e) {			
			System.out.println(e);			
		}

		stopSelf();
		
	}

}
