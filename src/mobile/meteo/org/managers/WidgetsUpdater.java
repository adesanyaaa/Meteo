package mobile.meteo.org.managers;

import mobile.meteo.org.receivers.Widget41;
import mobile.meteo.org.receivers.Widget43;
import mobile.meteo.org.receivers.Widget44;
import mobile.meteo.org.sevices.WidgetService41;
import mobile.meteo.org.sevices.WidgetService43;
import mobile.meteo.org.sevices.WidgetService44;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class WidgetsUpdater {
	
	private Context context;	
	private AppWidgetManager appWidgetManager;
	
	public WidgetsUpdater(Context c) {
		
		context = c;		
		appWidgetManager = AppWidgetManager.getInstance(context);		
	}
	
	public void updateWidgets() {
		
		update41();
		update43();
		update44();
	}
	
	public void update41() {
		
		/////// update 4x1 widget		
		ComponentName widget41 = new ComponentName(context, Widget41.class);	
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(widget41);
		
		if( allWidgetIds.length > 0 ) {
			// Build the intent to call the service
			Intent widget41ServiceIntent = new Intent(context.getApplicationContext(), WidgetService41.class);
			widget41ServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);	
			// Update the widgets via the service
			context.startService(widget41ServiceIntent);
		}
	}
	
	public void update43() {
		
		/////// update 4x1 widget		
		ComponentName widget43 = new ComponentName(context, Widget43.class);	
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(widget43);
		
		if( allWidgetIds.length > 0 ) {
			// Build the intent to call the service
			Intent widget43ServiceIntent = new Intent(context.getApplicationContext(), WidgetService43.class);
			widget43ServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);	
			// Update the widgets via the service
			context.startService(widget43ServiceIntent);
		}
	}
	
	public void update44() {
		
		/////// update 4x1 widget		
		ComponentName widget44 = new ComponentName(context, Widget44.class);	
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(widget44);
		
		if( allWidgetIds.length > 0 ) {
			// Build the intent to call the service
			Intent widget44ServiceIntent = new Intent(context.getApplicationContext(), WidgetService44.class);
			widget44ServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);	
			// Update the widgets via the service
			context.startService(widget44ServiceIntent);
		}
	}
}
