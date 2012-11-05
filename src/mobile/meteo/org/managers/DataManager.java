package mobile.meteo.org.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mobile.meteo.org.objects.City;
import mobile.meteo.org.objects.WeatherItem;
import mobile.meteo.org.statics.Misk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DataManager {
	
	public static final int DATABASE_VERSION = 1;
	
	public final static String DB_NAME = "main";	
	public final static String DB_PATH = "/data/data/mobile.meteo.org/databases/";	
	public final static String WEATHER_ITEMS = "weather_items";	
	public final static String WEATHER_CURRENT = "weather_current";		

	public SQLiteDatabase db;
	public Context parent;
	
	private DatabaseHelper databaseHelper;
	
	public DataManager(Context context) {
		// TODO Auto-generated constructor stub				
		parent = context;			
		initDatabase();
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	
	public void initDatabase() {
		
		try {
			databaseHelper = new DatabaseHelper(parent);
			databaseHelper.createDataBase();
			databaseHelper.openDataBase();
			db = databaseHelper.myDataBase;
		
		} catch (Exception e) {
		
		}	
	}
	
	
	/**
	 * 
	 * 
	 * 
	 */
	
	public void closeDatabase() {
		
		databaseHelper.close();
	}
	
	/**
	 * 
	 * 
	 */
	
	public void deleteAllWeatherItems() {
		
		db.delete(WEATHER_ITEMS, null, null);	
		db.delete(WEATHER_CURRENT, null, null);
	}
	
	
	/**
	 * 
	 * 
	 * @param weather
	 * @return
	 */
	public boolean insertWeatherItemsFromJSONString(String weather) {
		
		try {   
			
			JSONObject allData = new JSONObject(weather);	
			
			try {
				insertCurrentWeatherData(allData.getJSONObject("current"));				
			} catch (Exception e) {				
				System.out.println(e);
			}
			
			insertForecastWeatherData(allData.getJSONArray("forecast"));	
			
		} catch (JSONException e) {		
				
		}  
		
		return true;
	}
	
	/**
	 * 
	 * 
	 * @param current
	 * @return
	 * @throws JSONException
	 */
	
	protected long insertCurrentWeatherData(JSONObject jObject) throws JSONException {
		
		WeatherItem currentWeather = new WeatherItem(jObject);
		
		if(currentWeather.date == 0) return 0;
		
		return db.insert(WEATHER_CURRENT, null, currentWeather.getContentValues());
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param jArray
	 * @throws JSONException
	 */
	protected void insertForecastWeatherData(JSONArray jArray) throws JSONException {
		
		JSONObject tmpjObject;	
		
		for(int i = 0; i < jArray.length(); i++) {				
			tmpjObject = jArray.getJSONObject(i);
			insertWeatherDayFromJSONObject(tmpjObject);	
			
		}	
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param jObject
	 * @throws JSONException 
	 */
	
	protected void insertWeatherDayFromJSONObject(JSONObject jObject) throws JSONException {
		
		WeatherItem item = new WeatherItem(jObject);
		
		db.insert(WEATHER_ITEMS, null, item.getContentValues());
	}
	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	
	public Cursor getWeatherItems() {
		
		String sql = "SELECT wt.*, " +		
						"(SELECT temp FROM " + WEATHER_ITEMS + " WHERE date >= wt.date AND hour = '3' LIMIT 0,1 )" +
					" FROM " + WEATHER_ITEMS + " AS wt" +
					" WHERE wt.hour = '15'" +
					" GROUP BY date ORDER BY date;";
		
		Cursor c = db.rawQuery(sql, null);
		
		return c;		
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	
	public Cursor getCurrentWeatherItemCursor() {
		
		Cursor c;
		
		String sql = "SELECT wt.*, " +		
						"(SELECT temp FROM " + WEATHER_ITEMS + " WHERE date > wt.date AND hour = '3' ORDER BY date LIMIT 0,1 )" +
					" FROM " + WEATHER_CURRENT + " AS wt" +
					" WHERE (wt.date - " + Misk.getTimestamp() + ") < 5000 " +
					" LIMIT 0,1;";

		c = db.rawQuery(sql, null);
		
	
		
		if(c.getCount() == 0) {
			c.close();			
		} else return c;		
		
		
			
		sql = "SELECT wt.*, " +		
				"(SELECT temp FROM " + WEATHER_ITEMS + " WHERE date >= wt.date AND hour = '3' ORDER BY date LIMIT 0,1 )" +
			" FROM " + WEATHER_ITEMS + " AS wt" +
			" WHERE wt.date < " + Misk.getTimestamp() + " AND wt.hour = '" + Integer.toString(Misk.dayPeriodAsHour(Misk.getCurrentDayPeriodAsInteger())) + "'  " +	
			" LIMIT 0,1;";
		
		c = db.rawQuery(sql, null);		
		
	

		return c;		
	}
	
	
	/**
	 * 
	 * 
	 * @param id
	 * @return
	 */
	
	public Cursor getWeatherItemCursor(int id) {		
		
		String sql = "SELECT wt.*, " +		
				"(SELECT temp FROM " + WEATHER_ITEMS + " WHERE date = wt.date AND hour = '3'  )" +
			" FROM " + WEATHER_ITEMS + " AS wt" +
			" WHERE wt.date = (SELECT date FROM " + WEATHER_ITEMS + " WHERE " + WEATHER_ITEMS + "._id = " + Integer.toString(id) + ") " +	
			" AND wt.hour = '15'" +
			" GROUP BY date ORDER BY date;";
		
		Cursor c = db.rawQuery(sql, null);
		
		return c;
		
	}
	
	
	/**
	 * 
	 * 
	 * @param item
	 * @return
	 */
	public WeatherItem getNextDayPeriodWeatherItem() {
		
		String sql = "SELECT wt.*, 0 " +
					" FROM " + WEATHER_ITEMS + " AS wt" +
					" WHERE wt.hour = '" + Integer.toString(Misk.dayPeriodAsHour(Misk.getNextDayPeriodAsInteger())) + "'  " +					
					" ORDER BY date LIMIT 0,1;";
		
		Cursor c = db.rawQuery(sql, null);
		if(c.getCount() == 0) {
			c.close();
			return null;
		} else {
			c.moveToFirst();
			WeatherItem wi = new WeatherItem(c);
			c.close();
			return wi;
		}
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	
	public List<WeatherItem> getWeatherItemsAsList() {
		
		List<WeatherItem> weatherItems = new ArrayList<WeatherItem>();
		WeatherItem tmpObject;
    	
    	Cursor c = getWeatherItems();  
    	
    	c.moveToFirst();
    	while (!c.isAfterLast()) { 
    		
    		tmpObject = new WeatherItem(c);
    		weatherItems.add(tmpObject);
    		
    		c.moveToNext();
    	}
    	c.close();  
    	
    	return weatherItems;
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	public WeatherItem getCurrentWeatherItem() {		
    	
    	Cursor c = getCurrentWeatherItemCursor();  
    	
    	if(c.getCount() == 0) {
    		c.close();  
    		return null;
    	}
    	
    	WeatherItem tmpObject;
    	
    	c.moveToFirst();
    	tmpObject = new WeatherItem(c);
    	c.close();  
    	
    
    	
    	return tmpObject;
	}
	
	
	
	/**
	 * 
	 * 
	 * @param id
	 * @return
	 */
	
	public WeatherItem getWeatherItemsAsList(int id) {
		
		WeatherItem tmpObject;
		
    	Cursor c = getWeatherItemCursor(id);  
    	
    	c.moveToFirst();
    	tmpObject = new WeatherItem(c);
    	c.close();  
    	
    	return tmpObject;
	}

	
	/**
	 * 
	 * 
	 * 
	 * @param id
	 * @return
	 
	
	public List<WeatherItemDetail> getWeatherItemDetailsAsList(int id) {
		
		List<WeatherItemDetail> weatherItemDetails = new ArrayList<WeatherItemDetail>();
		WeatherItemDetail tmpObject;
    	Cursor c = getWeatherItemDetail(id);  
    	
    	c.moveToFirst();
    	while (!c.isAfterLast()) { 
    		
    		tmpObject = new WeatherItemDetail(c);
    		weatherItemDetails.add(tmpObject);
    		
    		c.moveToNext();
    	}
    	c.close();  
    	
    	return weatherItemDetails;
	}
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	public int getLastUpdatedItemDate() {
		
		int LastUpdatedItemDate;		
		Cursor c = db.rawQuery("SELECT MAX(inserted) as lastDate FROM " + WEATHER_ITEMS + ";", null);
		
		c.moveToPosition(0);
		LastUpdatedItemDate = c.getInt(0);
        c.close();
		
		return LastUpdatedItemDate;
		
	}
	
	
	/**
	 * 
	 * 
	 * @param word
	 * @return
	 */
	
	public List<City> getCitiesListByWord(String word) {
		
		String appLang = Locale.getDefault().getLanguage();
		Cursor c;
		
		List<City> answer = new ArrayList<City>();
		
		word = word.toLowerCase();
		
		if(appLang.equals("ru") || appLang.equals("ua")) {
		
			c = db.rawQuery("SELECT _id,  name, country " +
								   "FROM cities " +
								   "WHERE searchName LIKE '" + word + "%'" +
								   "ORDER BY name_en LIMIT 0, 30;", null);
		} else {
			
			c = db.rawQuery("SELECT _id, name_en, country_en " +
					   "FROM cities " +
					   "WHERE searchName_en LIKE '" + word + "%'" +
					   "ORDER BY name_en LIMIT 0, 30;", null);
			
		}
		
		if(c.getCount() > 0) {			
			c.moveToFirst();
	    	while (!c.isAfterLast()) { 	    		
	    		//answer.add(c.getString(0));
	    		answer.add(new City(
	    					c.getInt(0),
	    					c.getString(1),
	    					c.getString(2)
	    				));
	    		c.moveToNext();
	    	}	    	
		}
		
		c.close();  
		
		return answer;
	}
	
	
	/**
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public String getCityNameById(int id) {
		
		String answer;
		String appLang = Locale.getDefault().getLanguage();
		Cursor c;
		
		if(appLang.equals("ru") || appLang.equals("ua")) {
		
			c = db.rawQuery("SELECT name " +
								   "FROM cities " +
								   "WHERE _id = '" + id + "'" , null);
		} else {
			
			c = db.rawQuery("SELECT name_en " +
					   "FROM cities " +
					   "WHERE _id = '" + id + "'" , null);
		}
		
		if(c.getCount() > 0) {			
			c.moveToFirst();
			answer = c.getString(0);	    	  	
		} else {
			answer = null;
		}
		
		c.close();  
		
		return answer;
	}
	
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	public int getCityIDByName(String cityName) {
		
		int cityId;	
		
		String appLang = Locale.getDefault().getLanguage();
		Cursor c;
		
		if(appLang.equals("ru") || appLang.equals("ua")) {			
		
			c = db.rawQuery("SELECT _id FROM cities WHERE name LIKE '" + cityName + "' ;", null);
		} else {
			
			c = db.rawQuery("SELECT _id FROM cities WHERE name_en LIKE '" + cityName + "' ;", null);
		}
			
		
		c.moveToPosition(0);
		cityId = c.getInt(0);
        c.close();
		
		return cityId;
		
	}

	
	/**
	 * 
	 * 
	 * @return
	 */
	public List<WeatherItem> getAllWeatherItems() {
		
		List<WeatherItem> weatherItems = new ArrayList<WeatherItem>();
		
		String sql = "SELECT wt.*, 0" +					
					" FROM " + WEATHER_ITEMS + " AS wt" +				
					" ORDER BY date, hour;";

		Cursor c = db.rawQuery(sql, null);
		
		if(c.getCount() == 0) {		
			c.close();			
		} else {
			
			c.moveToFirst();
	    	while (!c.isAfterLast()) { 
	    		weatherItems.add(new WeatherItem(c));	    		
	    		c.moveToNext();
	    	}
	    	c.close();  
		}
		
		return weatherItems;
	}

}
