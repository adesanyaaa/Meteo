package mobile.meteo.org.activity;

import mobile.meteo.org.R;
import mobile.meteo.org.managers.WidgetsUpdater;
import mobile.meteo.org.receivers.Widget41;
import mobile.meteo.org.sevices.WidgetService41;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;


public class Preferences extends PreferenceActivity {
	
	private static boolean isRunning = false;
	
	SharedPreferences prefs;
	Preference customPref;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
			if(Preferences.isRunning) this.finish();
			else Preferences.isRunning = true;
			
			
		
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences); 
            
            prefs = PreferenceManager.getDefaultSharedPreferences(this); /* getSharedPreferences("preferences", Activity.MODE_PRIVATE); */    		
    		String currentCityName = prefs.getString("cityName", getString(R.string.defaultCity));	            
            
            customPref = (Preference) findPreference("cid");
            customPref.setSummary(getString(R.string.chosen) + currentCityName);
            
            customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

                                    public boolean onPreferenceClick(Preference preference) {
                                    	
                                    	Intent cityPickActivity = new Intent(getBaseContext(), CityPickActivity.class);
                                    	startActivity(cityPickActivity);   
                                    	return true;
                                    }

                            });
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String currentCityName = prefs.getString("cityName", getString(R.string.defaultCity));	    
		customPref.setSummary(getString(R.string.chosen) + currentCityName);		
	}
	
	@Override
	protected void onStop() {
		Preferences.isRunning = false;
		super.onStop();
		
		upadeWidgets(this);
		
		
	}
	
	/**
	 * 
	 * 
	 * @param context
	 */
	private void upadeWidgets(Context context) {
		
		WidgetsUpdater wiUp = new WidgetsUpdater(context);		
		wiUp.updateWidgets();
		wiUp = null;
		
	}

}




