package mobile.meteo.org.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import mobile.meteo.org.R;
import mobile.meteo.org.imps.WeatherScopeView;
import mobile.meteo.org.managers.ClicksListener;

import mobile.meteo.org.objects.WeatherItem;
import mobile.meteo.org.statics.Hashes;
import mobile.meteo.org.statics.Misk;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class Render {
	
	Meteo client;
	
	public Render(Meteo met) {
		
		client = met;
	}
	
	/**
	 * 
	 * 
	 */
	public void renderData() {
		
		if(client.weatherItems.size() == 0) {
			
			setNoDataLayout();
			bindPortraitButtons();	
			
			return;
		}
    	
    	if(client.currentOrientation == Configuration.ORIENTATION_PORTRAIT) {    
    		
    		drawCurrentDay();
    		fillForecast();
    		bindPortraitButtons();		
    		
        } else {
        	
        	drawScopes();        	
        	bindLandscapeButtons();
        }   
	}
	
	/**
	 * 
	 * 
	 */
	private void setNoDataLayout() {
		
		client.setContentView(R.layout.no_data);
	}
	
	/**
	 * 
	 * 
	 */
	
	private void drawCurrentDay() {
		
		TextView current_date = (TextView) client.findViewById(R.id.current_date);
		current_date.setText( Misk.getDayOfWeekAsStringShort(Misk.getDayOfWeekFromTimestamp(client.currentWeatherItem.inserted), client) + " " +
							  Misk.getDateDM(client.currentWeatherItem.inserted));
		
		TextView current_city = (TextView) client.findViewById(R.id.current_city);
		current_city.setText(client.currentWeatherItem.city);
		
		TextView current_weather_descr = (TextView) client.findViewById(R.id.current_weather_descr);
		current_weather_descr.setText(Hashes.weatherDesc.get(client.currentWeatherItem.weather_icon));
		
		TextView current_preshure = (TextView) client.findViewById(R.id.current_preshure);
		current_preshure.setText(Integer.toString(client.currentWeatherItem.preshure) + client.getString(R.string.mmrtst));
		
		TextView current_wind = (TextView) client.findViewById(R.id.current_wind);
		current_wind.setText(Integer.toString(client.currentWeatherItem.wind_direction) + (char) 0x00B0 + ", " +
							 Integer.toString(client.currentWeatherItem.wind_speed)	+ client.getString(R.string.mps));
		
		TextView current_wet = (TextView) client.findViewById(R.id.current_wet);
		current_wet.setText(Integer.toString(client.currentWeatherItem.wet) + "%");
		
		ImageView current_weather_icon = (ImageView) client.findViewById(R.id.current_weather_icon);		
		current_weather_icon.setImageResource(Misk.getWeatherIconByWeatherId(client.currentWeatherItem.weather_icon));
		
		TextView current_temp = (TextView) client.findViewById(R.id.current_temp);
		current_temp.setText(client.currentWeatherItem.currentTemp);
		
		TextView current_next_temp_period = (TextView) client.findViewById(R.id.current_next_temp_period);
		current_next_temp_period.setText(Misk.getNextDayPeriodAstring(client));
		
		TextView current_next_temp = (TextView) client.findViewById(R.id.current_next_temp);
		current_next_temp.setText(client.nextWeathrItem.currentTemp);
		
		TextView updated_date = (TextView) client.findViewById(R.id.updated_date);
		updated_date.setText(client.getString(R.string.updated) + Misk.getDate(client.currentWeatherItem.inserted));
		
	}
	
	
	/**
	 * 
	 * 
	 */
	
	private void fillForecast() {
		
		LayoutInflater vi = (LayoutInflater) client.getLayoutInflater();		
		LinearLayout forecastLayout = (LinearLayout) client.findViewById(R.id.forecast); 
		forecastLayout.removeAllViews();  
		
		WeatherItem tmpjObject;
		View v; 
		
		Iterator<WeatherItem> iter = client.weatherItems.iterator();   
    	
    	while (iter.hasNext()) {   		
    		
			tmpjObject = (WeatherItem) iter.next();
				
	        v = vi.inflate(R.layout.forecast_item, null);		        
	     
	        TextView short_week_day = (TextView) v.findViewById(R.id.short_week_day);
	        short_week_day.setText(Misk.getDayOfWeekAsString(Misk.getDayOfWeekFromTimestamp(tmpjObject.date), client));
	        
	        TextView short_date = (TextView) v.findViewById(R.id.short_date);
	        short_date.setText(Misk.getDateDM(tmpjObject.date));
	        
	        TextView short_weather_descr = (TextView) v.findViewById(R.id.short_weather_descr);
	        short_weather_descr.setText(Hashes.weatherDesc.get(tmpjObject.weather_icon));
	        
	        TextView short_day_temp = (TextView) v.findViewById(R.id.short_day_temp);
	        short_day_temp.setText(tmpjObject.currentTemp);
	        
	        TextView short_night_temp = (TextView) v.findViewById(R.id.short_night_temp);
	        short_night_temp.setText(tmpjObject.night_temp);
	        
	        TextView short_wind_dir = (TextView) v.findViewById(R.id.short_wind_dir);
	        short_wind_dir.setText(Integer.toString(tmpjObject.wind_direction) + (char) 0x00B0);
	        
	        TextView short_wind_speed = (TextView) v.findViewById(R.id.short_wind_speed);
	        short_wind_speed.setText(Integer.toString(tmpjObject.wind_speed) + client.getString(R.string.mps));
	        
	        ImageView short_weather_icon = (ImageView) v.findViewById(R.id.short_weather_icon);	       
	        short_weather_icon.setImageResource(Misk.getWeatherIconByWeatherId(tmpjObject.weather_icon));
	    	
	        forecastLayout.addView(v);	
							
		}  		
	}
	
	/**
	 * 
	 * 
	 */
	private void bindPortraitButtons() {
		
		ImageView update_btn = (ImageView) client.findViewById(R.id.update_btn);
		update_btn.setOnClickListener(new ClicksListener(client, ClicksListener.ACTION_UPDATE));
		
		ImageView settings_btn = (ImageView) client.findViewById(R.id.settings_btn);
		settings_btn.setOnClickListener(new ClicksListener(client, ClicksListener.ACTION_CALL_PREFERENCES));
	}
	
	/**
	 * 
	 * 
	 */
	private void drawScopes() {		
		
		TextView current_city_land = (TextView) client.findViewById(R.id.current_city_land);
		current_city_land.setText(client.currentWeatherItem.city);
		
		drawTempScope();
	}
	
	/**
	 * 
	 * 
	 */
	private void drawTempScope() {
		
		WeatherScopeView weatherScope = (WeatherScopeView) client.findViewById(R.id.scope_temp); 
		
		weatherScope.removeScopes();	
		
		weatherScope.addScope(client.weatherItems, Color.argb(50, 125, 153, 215), Color.argb(0, 0, 9, 102), "wet");		
		weatherScope.addScope(client.weatherItems, Color.argb(50, 0, 190, 215), Color.argb(0, 0, 9, 102), "presh");		
		weatherScope.addScope(client.weatherItems, Color.argb(220, 125, 190, 215), Color.argb(230, 1, 127, 189), "temp");
		
		weatherScope.invalidate();
	}
	
	/**
	 * 
	 * 
	 */
	private void drawWetScope() {
		
		WeatherScopeView weatherScope = (WeatherScopeView) client.findViewById(R.id.scope_temp); 
		
		weatherScope.removeScopes();		
		
		weatherScope.addScope(client.weatherItems, Color.argb(50, 0, 190, 215), Color.argb(0, 0, 9, 102), "presh");		
		weatherScope.addScope(client.weatherItems, Color.argb(50, 125, 190, 215), Color.argb(0, 0, 9, 102), "temp");
		weatherScope.addScope(client.weatherItems, Color.argb(220, 125, 153, 215), Color.argb(230, 1, 127, 189), "wet");		
		
		weatherScope.invalidate();
	}
	
	/**
	 * 
	 * 
	 */
	private void drawPreshScope() {
		
		WeatherScopeView weatherScope = (WeatherScopeView) client.findViewById(R.id.scope_temp); 
		
		weatherScope.removeScopes();	
		
		weatherScope.addScope(client.weatherItems, Color.argb(50, 125, 153, 215), Color.argb(0, 0, 9, 102), "wet");			
		weatherScope.addScope(client.weatherItems, Color.argb(50, 125, 190, 215), Color.argb(0, 1, 127, 189), "temp");
		weatherScope.addScope(client.weatherItems, Color.argb(200, 0, 190, 215), Color.argb(230, 1, 127, 189), "presh");		
		
		weatherScope.invalidate();
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 */
	
	private void bindLandscapeButtons() {
		
		ImageView update_btn_land = (ImageView) client.findViewById(R.id.update_btn_land);
		update_btn_land.setOnClickListener(new ClicksListener(client, ClicksListener.ACTION_UPDATE));
		
		ImageView settings_btn_land = (ImageView) client.findViewById(R.id.settings_btn_land);
		settings_btn_land.setOnClickListener(new ClicksListener(client, ClicksListener.ACTION_CALL_PREFERENCES));
		
		ImageView btn_land_wet = (ImageView) client.findViewById(R.id.btn_land_wet);
		btn_land_wet.setOnClickListener(new ScopeClickListener(ScopeClickListener.SHOW_WET_SCOPE));
		
		ImageView btn_land_presh = (ImageView) client.findViewById(R.id.btn_land_presh);
		btn_land_presh.setOnClickListener(new ScopeClickListener(ScopeClickListener.SHOW_PRESH_SCOPE));
		
		ImageView btn_land_temp = (ImageView) client.findViewById(R.id.btn_land_temp);
		btn_land_temp.setOnClickListener(new ScopeClickListener(ScopeClickListener.SHOW_TEMP_SCOPE));
		
	}
	
	
	/**
	 * 
	 * 
	 * @author yuriy
	 *
	 */
	public class ScopeClickListener implements OnClickListener {
		
		public static final int SHOW_TEMP_SCOPE = 1,
				  SHOW_WET_SCOPE = 2,
				  SHOW_PRESH_SCOPE = 3;
		
		int action;
		
		public ScopeClickListener(int act) {
			action = act;
		}		

		@Override
		public void onClick(View v) {
			
			switch(action) {
			
				case SHOW_TEMP_SCOPE:
					drawTempScope();
					break;
					
				case SHOW_WET_SCOPE:
					drawWetScope();
					break;
					
				case SHOW_PRESH_SCOPE:
					drawPreshScope();
					break;	
			
			}
		}
	}
}


























