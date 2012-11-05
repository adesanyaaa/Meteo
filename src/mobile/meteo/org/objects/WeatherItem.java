package mobile.meteo.org.objects;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

public class WeatherItem extends JSONObject {
	
	public int id;
	public int date;
	public int hour;
	public String city;
	public int preshure;
	public int wet;
	public int temp;
	public int n_temp;
	public int temp_flik;
	public int wind_direction;
	public int wind_speed;
	public int weather_icon;
	public int inserted;
	
	public static DecimalFormat stringTemp = new DecimalFormat("###");
	
	/**
	 * 
	 * 
	 * @param c
	 */
	
	public WeatherItem(Cursor c) {
		
		id = c.getInt(0);
		date = c.getInt(1);
		hour = c.getInt(2);
		city = c.getString(3);
		preshure = c.getInt(4);
		wet = c.getInt(5);
		temp = c.getInt(6);		
		temp_flik = c.getInt(7);
		wind_direction = c.getInt(8);
		wind_speed = c.getInt(9);		
		weather_icon = c.getInt(10);
		inserted = c.getInt(11);
		n_temp = c.getInt(12);	
		
		setStringParams();
	}
	
	public WeatherItem(JSONObject json) throws JSONException {		
		super(json.toString());
		
		initVars();
	}	
	
	
	/**
	 * 
	 * 
	 */
	
	public void initVars() {
		
		try {
			
			date = this.getInt("date");
			hour = this.getInt("hour");
			city = this.getString("city");
			preshure = this.getInt("preshure");
			wet = this.getInt("wet");
			temp = this.getInt("temp");
			temp_flik = this.getInt("temp_flik");
			wind_direction = this.getInt("wind_direction");
			wind_speed = this.getInt("wind_speed");
			weather_icon = this.getInt("weather_icon");	
		
		} catch (Exception e) {
			System.out.println("JSON init faild");
		}
		
		setStringParams();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public ContentValues getContentValues() {
		
		ContentValues contentValues = new ContentValues();
		
		contentValues.put("date", date);	
		contentValues.put("hour", hour);	
		contentValues.put("city", city);	
		contentValues.put("preshure", preshure);	
		contentValues.put("wet", wet);	
		contentValues.put("temp", temp);	
		contentValues.put("temp_flik", temp_flik);	
		contentValues.put("wind_direction", wind_direction);	
		contentValues.put("wind_speed", wind_speed);	
		contentValues.put("weather_icon", weather_icon);			
    	contentValues.put("inserted", (int)(System.currentTimeMillis()/1000));   		
		
		return contentValues;
	}
	
	/**
	 * String params
	 */
	
	public String currentTemp, night_temp;
	
	/**
	 * 
	 * 
	 */
	private void setStringParams() {
		
		currentTemp = _tempToStringNC(temp);
		night_temp = _tempToStringNC(n_temp);		
	}
	
	/**
	 * 
	 * 
	 * @param temp
	 * @return
	 */	
	protected String _tempToStringNC(int temp) {
		
		String answ;
		
		if(temp > 0) {
			answ = "+" + stringTemp.format(temp) + (char) 0x00B0;
		} else {
			answ = stringTemp.format(temp) + (char) 0x00B0;
		}
		
		return answ;		
	}
	
	
	/**
	 * 
	 * 
	 * @param paramName
	 * @return
	 */
	public int getParamInt(String paramName) {
		
		if(paramName.equals("temp")) return this.temp;
		
		if(paramName.equals("presh")) return this.preshure;
		
		if(paramName.equals("wet")) return this.wet;
		
		return this.temp; 
	}
	
	/**
	 * 
	 * 
	 * @param paramName
	 * @return
	 */
	public String getParamString(String paramName) {
		
		if(paramName.equals("temp")) return this.currentTemp;
		
		if(paramName.equals("presh")) return Integer.toString(this.preshure) + " мм.рт.ст.";
		
		if(paramName.equals("wet")) return Integer.toString(this.wet) + " %";
		
		return this.currentTemp; 
	}

	
	
	
	

}
