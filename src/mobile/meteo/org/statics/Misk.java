package mobile.meteo.org.statics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;


public class Misk {
	
	/**
	 * 
	 * 
	 * @return
	 */
	public static int getCurrentDayPeriodAsInteger() {
		
		int dayHour;
		
		Date dNow = new Date(System.currentTimeMillis());
	    SimpleDateFormat ft = new SimpleDateFormat("HH");
	    
	    dayHour = Integer.parseInt(ft.format(dNow));
	    
	    if( dayHour > 4 && dayHour < 11 ) {
	    	return 1;
	    } else if( dayHour > 10 && dayHour < 17 ) {
	    	return 2;	    	
	    } else if( dayHour > 16 && dayHour < 22 ) {
	    	return 3;		    	
	    } else {
	    	return 4;
	    }
	}
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	public static boolean isNowDay() {
		
		Calendar cal = Calendar.getInstance(); 
		int currentMounth = cal.get(Calendar.MONTH);
		
		if(currentMounth > 4 || currentMounth < 10) {
			return Misk.getCurrentDayPeriodAsInteger() < 4 ? true : false;
		} else {
			return Misk.getCurrentDayPeriodAsInteger() < 3 ? true : false;
		}
		
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public static boolean isNowDay(int dayPeriod) {
		
		Calendar cal = Calendar.getInstance(); 
		int currentMounth = cal.get(Calendar.MONTH);
		
		if(currentMounth > 4 || currentMounth < 10) {
			return dayPeriod < 4 ? true : false;
		} else {
			return dayPeriod < 3 ? true : false;
		}
		
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	public static int getNextDayPeriodAsInteger() {		
		
		int currentDayPeriod = Misk.getCurrentDayPeriodAsInteger();		
	    
	    return ( currentDayPeriod > 3 ) ? 1 : currentDayPeriod + 1;
	}
	
	/**
	 * 
	 * 
	 * @param dayPeriod
	 * @return
	 */
	public static int dayPeriodAsHour(int dayPeriod) {
		
		int hour = 15;
		
		switch(dayPeriod) {
		
			case 1:
				hour = 9;
				break;
				
			case 2:
				hour = 15;
				break;
				
			case 3:
				hour = 21;
				break;
				
			case 4:
				hour = 3;
				break;	
		}		
		
		return hour;
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static String getTimestamp() {
		
		return Long.toString(System.currentTimeMillis()/1000);
	}	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static int getCurrentDayOfWeekAsInteger() {
		
		final Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.DAY_OF_WEEK); 
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static int getDayOfWeekFromTimestamp(int timestamp) {
		
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis((long)timestamp * 1000);
		
		return c.get(Calendar.DAY_OF_WEEK); 
	}
	
	
	/**
	 * 
	 * 
	 * 
	 */
	
	public static int getTimestampDate(int timestamp) {
		
		final Calendar c = Calendar.getInstance();		
		
		c.setTimeInMillis((long)timestamp * 1000);		
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		return (int)(c.getTimeInMillis()/1000);
	}
	
	/**
	 * 
	 */
	
	public static String getDate(int timestamp) {
		
		Date dNow = new Date((long)timestamp * 1000);
	    SimpleDateFormat ft = new SimpleDateFormat("dd-MM HH:mm");
	    
	    return ft.format(dNow);	
	}
	
	/**
	 * 
	 * 
	 * @param weatherIconId
	 * @return
	 */
	
	public static int getWeatherIconByWeatherId(int weatherIconId) {
		
		return Misk.isNowDay() ? Hashes.weatherIcons.get(weatherIconId) : Hashes.nightWeatherIcons.get(weatherIconId);		
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	
	public static String getNextDayPeriodAstring(Context context) {
		
		return context.getString(Hashes.dayPeriods.get(Misk.getNextDayPeriodAsInteger()));		
	}
		
	
	/**
	 * 
	 * 
	 * 
	 */
	
	public static String getDateDM(int timestamp) {
		
		Date dNow = new Date((long)timestamp * 1000);
	    SimpleDateFormat ft = new SimpleDateFormat("dd-MM");
	    
	    return ft.format(dNow);	
	}
	
	/**
	 * 
	 * 
	 */
	
	public static String getDate() {
		
		Date dNow = new Date(System.currentTimeMillis());
	    SimpleDateFormat ft = new SimpleDateFormat("dd-MM HH:mm");
	    
	    return ft.format(dNow);	
	}
	
	/**
	 * 
	 * 
	 */
	
	public static String getDayOfWeekAsString(int day, Context context) {
		
		return context.getString(Hashes.weekDays.get(day));
		
	}
	
	/**
	 * 
	 * 
	 */
	
	public static String getDayOfWeekAsStringShort(int day, Context context) {
		
		return context.getString(Hashes.weekDaysShort.get(day));
		
	}
	

}
