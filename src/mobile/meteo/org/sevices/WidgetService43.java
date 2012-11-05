package mobile.meteo.org.sevices;

import java.util.Iterator;
import java.util.List;

import mobile.meteo.org.R;
import mobile.meteo.org.activity.Preferences;
import mobile.meteo.org.managers.DataManager;
import mobile.meteo.org.objects.WeatherItem;


import mobile.meteo.org.receivers.Widget43;
import mobile.meteo.org.receivers.Widget44;
import mobile.meteo.org.statics.Hashes;
import mobile.meteo.org.statics.Misk;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.widget.RemoteViews;


public class WidgetService43 extends WidgetService {
	
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
			List<WeatherItem> weatherItems = dataManager.getWeatherItemsAsList(); 
	
			dataManager.closeDatabase();
			dataManager = null;
			
			/*
			Intent activIntent = new Intent(this.getApplicationContext(), PogodnikActivity.class);
			*/
			Intent activIntent = new Intent(this.getApplicationContext(), Preferences.class);			
			PendingIntent activPendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, activIntent, 0);
			
			Intent clickIntent = new Intent(this.getApplicationContext(),Widget43.class);
			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,allWidgetIds);			
			PendingIntent updatePendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);			
	
			for (int widgetId : allWidgetIds) {
				// Create some random data
				
				if(currentDayWeatherItem != null) {				
				
					RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(),R.layout.widget43);	
					
					if(widgetbg) {
						remoteViews.setImageViewBitmap(R.id.widget_bg, getBackground(bg_opacity));
					} else {
						remoteViews.setImageViewBitmap(R.id.widget_bg, getBackground(0));
					}
									
					remoteViews.setTextViewText(R.id.widget_43_temp, currentDayWeatherItem.currentTemp);				
					remoteViews.setTextViewText(R.id.widget_43_n_temp, nextWI.currentTemp);				
					remoteViews.setTextViewText(R.id.widget_43_city, currentDayWeatherItem.city);						
					remoteViews.setTextViewText(R.id.widget_43_weather_desc, getString(Hashes.weatherDesc.get(currentDayWeatherItem.weather_icon)));	
					
					remoteViews.setTextViewText(R.id.widget_43_icon_w_data, 
							getString(R.string.humidity) + " " + Double.toString(currentDayWeatherItem.wet) + "% " +
							getString(R.string.wind) + " " + Integer.toString(currentDayWeatherItem.wind_direction) + (char) 0x00B0 +  ", " + Integer.toString(currentDayWeatherItem.wind_speed) + getString(R.string.mps)
							);	
					
					remoteViews.setTextViewText(R.id.widget_43_update_date, Misk.getDate(currentDayWeatherItem.inserted) );				
					remoteViews.setImageViewResource(R.id.widget_43_icon, Misk.getWeatherIconByWeatherId(currentDayWeatherItem.weather_icon));		
								
					
					remoteViews.setOnClickPendingIntent(R.id.widget43_content, activPendingIntent);					
					remoteViews.setOnClickPendingIntent(R.id.update, updatePendingIntent);
					
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
