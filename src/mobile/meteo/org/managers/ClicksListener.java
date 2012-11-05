package mobile.meteo.org.managers;

import java.util.concurrent.TimeUnit;

import mobile.meteo.org.activity.Meteo;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;

public class ClicksListener implements OnClickListener {
	
	public static final int 
					ACTION_TEST = 1,
					ACTION_DARAW_SELECTED_DAY = 2,
					ACTION_CALL_PREFERENCES = 3,
					ACTION_UPDATE = 4;
	
	/**
	 * 
	 * 
	 * @param cont
	 * @param act
	 */
 	
 	public ClicksListener(Meteo cont, int act) {
 		ParentActivity = cont;       	
 		action = act; 		
 	}  
 	
 	public Meteo ParentActivity;
 	public int action = 0;

 	/**
 	 * 
 	 * 
 	 * 
 	 */
	@Override
	public void onClick(View v) {
		
		switch (action) {
		  case ACTION_TEST:
			  	//testing();
		        break;
		  case ACTION_DARAW_SELECTED_DAY: 
			  	//drawSelectedDayInAct(v.getId()); 	
		        break;
		        
		  case ACTION_CALL_PREFERENCES: 
			  	ParentActivity.runPreferencesActivity();
		        break;		        
		        
		  case ACTION_UPDATE: 
			  	update();			  	
		        break;	
		        
		 
		        
		  default:
		        // TODO
		}
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	
	
	public void testing() { 			
		ParentActivity.updadeBase(); 			
	} 
	
	/*
	 * 
	 * 
	 * 
	 * 
	
	
	public void drawSelectedDayInAct(int dayId) { 	
		
		WeatherItem toRender = null;
		
		try {
			toRender = ParentActivity.gerWeatherItemById(dayId);			
		} catch (Exception e) {
			toRender = Render.getDayFromListById(ParentActivity.weatherItems, dayId);
		}
		
		ParentActivity.render.drawDetailDay(toRender);
		ParentActivity.viewPager.setCurrentItem(0);
	} 
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	private boolean updateIsRunning = false;
	
	public void update() {
		
		if(updateIsRunning) return;
		
		updateIsRunning = true;
		
		try {
			
			AsyncWeatherLoader weatherDataLoader = new AsyncWeatherLoader();
			weatherDataLoader.execute(ParentActivity);
			weatherDataLoader.get(5000, TimeUnit.MILLISECONDS);
			
			ParentActivity.getWeatherData();
			ParentActivity.reDraw();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			updateIsRunning = false;
		}
	
		
	}
	
	
	
	
 }