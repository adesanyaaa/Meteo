package mobile.meteo.org.managers;

import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;


public class AsyncWeatherLoader extends AsyncTask<Context, Void, Void> {
	
	private final static String feedUrl =  "http://grungestudio.in.ua/weather",
								feedKey1 = "1234123412341234",
								feedKey2 = "1234123412341234";
	
	private MCrypt crypto = new MCrypt(feedKey1, feedKey2);
	

	@Override
	protected Void doInBackground(Context... params) {
		
		Messenger messenger = Messenger.getInstance();			
		Context context = params[0];
		
		String weatherData = "";
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context); /* context.getSharedPreferences("preferences", Activity.MODE_PRIVATE); */
		
		boolean detectLocation = prefs.getBoolean("regionDetectMode", true);
		boolean isLoationDetected = prefs.contains("lat");
		float lat = prefs.getFloat("lat", (float) 50.43);
		float lon = prefs.getFloat("lon", (float) 30.52);
		int cid = prefs.getInt("cid", 23);	
		
		if(detectLocation && isLoationDetected) {
			
			messenger.clearPostParams();
			messenger.setPostParam(new BasicNameValuePair("lat", Float.toString(lat)));
    		messenger.setPostParam(new BasicNameValuePair("lon", Float.toString(lon)));
    		messenger.setPostParam(new BasicNameValuePair("lang", Locale.getDefault().getLanguage()));
    		weatherData = messenger.post(feedUrl);	     		
		} else {
			
			messenger.clearPostParams();
			messenger.setPostParam(new BasicNameValuePair("cid", Integer.toString(cid)));
			messenger.setPostParam(new BasicNameValuePair("lang", Locale.getDefault().getLanguage()));
    		weatherData = messenger.post(feedUrl);	 
		}
		
		weatherData = decrypt(weatherData);
		
		if(!weatherData.equals("")) {
			putWeatherToBase(context, weatherData, prefs);
		}
		
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param weatherData
	 */
	private void putWeatherToBase(Context context, String weatherData, SharedPreferences prefs) {
			
		DataManager dataManager = new DataManager(context);		
		
		try {
			
			dataManager.deleteAllWeatherItems();    		
			dataManager.insertWeatherItemsFromJSONString(weatherData);  
			
			Editor editor = prefs.edit();
			editor.putLong("lastLoad", System.currentTimeMillis());
			editor.commit();				
		} catch(Exception e) {
			System.out.println(e);					
		} finally {				
			dataManager.closeDatabase();
			dataManager = null;
		}
	}
	
	/**
	 * 
	 * 
	 */
	
	private String decrypt(String string) {		
		
		try {	
			
			String str = new String( crypto.decrypt( string ) );
			str = str.substring(0, str.indexOf("}]}") + 3);
			
			return str;
		} catch (Exception e) {			
			return "";			
		}
	}
	
	
	
	
}