package mobile.meteo.org.receivers;

import java.util.concurrent.TimeUnit;

import mobile.meteo.org.managers.AsyncWeatherLoader;
import mobile.meteo.org.sevices.LocationService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;

import android.content.Context;
import android.content.Intent;



abstract public class Widget extends AppWidgetProvider {
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {	
		
		initServices(context);
		initWidetService(context, appWidgetManager);
	}
		
	protected void initWidetService(Context context, AppWidgetManager appWidgetManager) {}	
			
	private void initServices(Context context) {
		
		try {
			AsyncWeatherLoader weatherDataLoader = new AsyncWeatherLoader();
			weatherDataLoader.execute(context);
			weatherDataLoader.get(2000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			System.out.println("trouble with widget data loading");
		}
		
		Intent locationService = new Intent(context, LocationService.class);
		context.startService(locationService);
		
		Alarm al = new Alarm();
		al.SetAlarm(context);
	}
}