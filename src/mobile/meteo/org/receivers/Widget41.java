package mobile.meteo.org.receivers;

import mobile.meteo.org.sevices.WidgetService41;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Widget41 extends Widget {
	
	protected void initWidetService(Context context, AppWidgetManager appWidgetManager) {
		
		ComponentName thisWidget = new ComponentName(context, Widget41.class);
		
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(), WidgetService41.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		// Update the widgets via the service
		context.startService(intent);
	}	

}
