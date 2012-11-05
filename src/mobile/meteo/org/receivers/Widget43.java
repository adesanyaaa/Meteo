package mobile.meteo.org.receivers;

import mobile.meteo.org.sevices.WidgetService43;
import mobile.meteo.org.sevices.WidgetService44;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Widget43 extends Widget {
	
	protected void initWidetService(Context context, AppWidgetManager appWidgetManager) {
		
		ComponentName thisWidget = new ComponentName(context, Widget43.class);
		
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(), WidgetService43.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		// Update the widgets via the service
		context.startService(intent);
		
	}	

}
