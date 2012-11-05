package mobile.meteo.org.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import mobile.meteo.org.App;

import mobile.meteo.org.R;
import mobile.meteo.org.managers.AsyncWeatherLoader;
import mobile.meteo.org.managers.DataManager;
import mobile.meteo.org.objects.WeatherItem;
import mobile.meteo.org.receivers.Alarm;
import mobile.meteo.org.sevices.LocationService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class Meteo extends Activity {
	
	SharedPreferences prefs;
	private long currentTime;
	public List<WeatherItem> weatherItems, allWeather;
	public int currentOrientation;
	
	public WeatherItem currentWeatherItem, nextWeathrItem; 
	
	private Render render = new Render(this);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this); /*getSharedPreferences("preferences", Activity.MODE_PRIVATE);*/
		currentTime = System.currentTimeMillis();
		
		initServices(this);
		dealWithData();
		
		setContentViewByCurrentOrientation(); 
    } 
	
	/**
	 * 
	 * 
	 */
	private void setContentViewByCurrentOrientation() {
		
		currentOrientation = getScreenOrientation();    
    	
        if(currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
        	helpInitVertical();        	
        } else {        	
        	helpInitHorizontal();        	
        }  
        
        render.renderData();
	}
	
	/**
	 * 
	 * 
	 */
	
	public void reDraw() {
		
		render.renderData();		
	}
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	private int getScreenOrientation()
    {
        Display getOrient = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else { 
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;            
            } else { 
                 orientation = Configuration.ORIENTATION_LANDSCAPE;             
            }
        }
        return orientation;
    }
	
	/**
	 * 
	 * 
	 */
	private void helpInitVertical() {
    	
    	setContentView(R.layout.main);  
    }
    
    /**
     * 
     * 
     */
    private void helpInitHorizontal() {    	
    	
    	setContentView(R.layout.main_land);       	
    }
	

	
	/**
	 * 
	 * 
	 * 
	 */
	
	private void dealWithData() {
		
		long lastWeatherLoad = prefs.getLong("lastLoad", 0);
		long weatherUpdatePeriod = Long.parseLong(prefs.getString("updatePeriod", "30")) * 60000;
		
		if(lastWeatherLoad == 0 || lastWeatherLoad + weatherUpdatePeriod < currentTime ) loadData();		
		
		getWeatherData();
	}
	
	/**
	 * 
	 * 
	 */
	private void loadData() {	
		
		try {
			AsyncWeatherLoader weatherDataLoader = new AsyncWeatherLoader();
			weatherDataLoader.execute(this.getApplicationContext());
			weatherDataLoader.get(2000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	
	public void getWeatherData() {
		
		DataManager dataManager = new DataManager(this.getApplicationContext());
		
		weatherItems = dataManager.getWeatherItemsAsList();
		currentWeatherItem = dataManager.getCurrentWeatherItem();
		allWeather = dataManager.getAllWeatherItems();
		nextWeathrItem = dataManager.getNextDayPeriodWeatherItem();
		
		dataManager.closeDatabase();
		dataManager = null;
	}
	
	
	/**
	 * 
	 * 
	 * @param context
	 */
	private void initServices(Context context) {
		
		Intent locationService = new Intent(context, LocationService.class);
		context.startService(locationService);
		
		Alarm al = new Alarm();
		al.SetAlarm(context);
	}
	
	
	
	/* 
 	Menu call block begins, its all about show menu and start menu Activity 
 	BEGIN
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	
		
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.xml.menu, menu);
		
		//runPreferencesActivity();		
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {		
			case R.id.base_settings:     
				runPreferencesActivity();    
	        break;
		}
		
	    return true;
	}
	
	
	public void runPreferencesActivity() {
		
		Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
		startActivity(settingsActivity);   
	}	

	
	/* 
	 	Menu call block ends
	 	END
	*/
	

}
