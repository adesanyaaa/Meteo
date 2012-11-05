package mobile.meteo.org.receivers;

import mobile.meteo.org.managers.AsyncWeatherLoader;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Alarm extends BroadcastReceiver {    
    @Override
    public void onReceive(Context context, Intent intent) {  
    	
    	new AsyncWeatherLoader().execute(context);    
    }

	public void SetAlarm(Context context) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		long weatherUpdatePeriod = Long.parseLong(prefs.getString("updatePeriod", "30")) * 60000;
		
	    AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    Intent i = new Intent(context, Alarm.class);
	    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);	    
    
    	am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + weatherUpdatePeriod, weatherUpdatePeriod, pi); // Millisec * Second * Minute   
    	//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, 2000, pi); 
	    
	}
	
	public void CancelAlarm(Context context) {
		
	    Intent intent = new Intent(context, Alarm.class);
	    PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    alarmManager.cancel(sender);
	    sender.cancel();
	}
}