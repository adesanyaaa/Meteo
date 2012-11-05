package mobile.meteo.org.statics;

import java.util.HashMap;

import mobile.meteo.org.R;

public class Hashes {
	
	public static final HashMap<Integer, Integer> dayPeriods = new HashMap<Integer, Integer>();
    static {
    	
    	dayPeriods.put(1, R.string.morning);
		dayPeriods.put(2, R.string.day);
		dayPeriods.put(3, R.string.evening);
		dayPeriods.put(4, R.string.night);
    	
    	/*
    	dayPeriods.put(1, "Утро");
		dayPeriods.put(2, "День");
		dayPeriods.put(3, "Вечер");
		dayPeriods.put(4, "Ночь");
		*/
    }
    
    public static final HashMap<Integer, Integer> weekDays = new HashMap<Integer, Integer>();
    static {
    	
    	weekDays.put(1, R.string.sunday);
		weekDays.put(2, R.string.monday);
		weekDays.put(3, R.string.tuesday);
		weekDays.put(4, R.string.wednesday);
		weekDays.put(5, R.string.thursday);
		weekDays.put(6, R.string.friday);
		weekDays.put(7, R.string.saturday);
    	
    	
    	/*
    	weekDays.put(1, "Воскресенье");
		weekDays.put(2, "Понедельник");
		weekDays.put(3, "Вторник");
		weekDays.put(4, "Среда");
		weekDays.put(5, "Четверг");
		weekDays.put(6, "Пятница");
		weekDays.put(7, "Суббота");
		*/
    }
    
    public static final HashMap<Integer, Integer> weekDaysShort = new HashMap<Integer, Integer>();
    static {
    	
    	weekDaysShort.put(1, R.string.sun);
    	weekDaysShort.put(2, R.string.mon);
		weekDaysShort.put(3, R.string.tue);
		weekDaysShort.put(4, R.string.wed);
		weekDaysShort.put(5, R.string.thu);
		weekDaysShort.put(6, R.string.fri);
		weekDaysShort.put(7, R.string.sat);
    	
    	/*
    	weekDaysShort.put(1, "Вс");
    	weekDaysShort.put(2, "Пн");
		weekDaysShort.put(3, "Вт");
		weekDaysShort.put(4, "Ср");
		weekDaysShort.put(5, "Чт");
		weekDaysShort.put(6, "Пт");
		weekDaysShort.put(7, "Сб");
		*/
    }
    /* weather icons BEGIN*/
    
    public static final HashMap<Integer, Integer> weatherIcons = new HashMap<Integer, Integer>();
    static {
    	weatherIcons.put(0, R.drawable.d000);    	
    	weatherIcons.put(10, R.drawable.d200);
    	weatherIcons.put(20, R.drawable.d300);
    	weatherIcons.put(30, R.drawable.d400);
    	weatherIcons.put(40, R.drawable.d410);
    	weatherIcons.put(70, R.drawable.d411);
    	weatherIcons.put(50, R.drawable.d420);
    	weatherIcons.put(90, R.drawable.d422);
    	weatherIcons.put(100, R.drawable.d432);
    	weatherIcons.put(60, R.drawable.d440);
    	weatherIcons.put(80, R.drawable.d431);
    	weatherIcons.put(255, R.drawable.d200);
    }  
    
    
    public static final HashMap<Integer, Integer> nightWeatherIcons = new HashMap<Integer, Integer>();
    static {
    	nightWeatherIcons.put(0, R.drawable.n000);    
    	nightWeatherIcons.put(10, R.drawable.n200); 
    	nightWeatherIcons.put(20, R.drawable.n300);
    	nightWeatherIcons.put(30, R.drawable.n400);
    	nightWeatherIcons.put(40, R.drawable.n410);
    	nightWeatherIcons.put(70, R.drawable.n411);    	
    	nightWeatherIcons.put(50, R.drawable.n420);    	
    	nightWeatherIcons.put(90, R.drawable.n422);
    	nightWeatherIcons.put(100, R.drawable.n432);
    	nightWeatherIcons.put(60, R.drawable.n440);
    	nightWeatherIcons.put(80, R.drawable.n431);
    	nightWeatherIcons.put(255, R.drawable.n200);
    }  
    
    /* weather icons END*/
    
    
    /* weather description */
    
    public static final HashMap<Integer, Integer> weatherDesc = new HashMap<Integer, Integer>();
    static {   
    	
    	weatherDesc.put(0, R.string.w_0);    	
    	weatherDesc.put(10, R.string.w_10);
    	weatherDesc.put(20, R.string.w_20);
    	weatherDesc.put(30, R.string.w_30);
    	weatherDesc.put(40, R.string.w_40);
    	weatherDesc.put(50, R.string.w_50);
    	weatherDesc.put(60, R.string.w_60);
    	weatherDesc.put(70, R.string.w_70);
    	weatherDesc.put(80, R.string.w_80);
    	weatherDesc.put(90, R.string.w_90);
    	weatherDesc.put(100, R.string.w_100);
    	weatherDesc.put(255, R.string.w_255);
    	
    	/*
    	weatherDesc.put(0, "Ясно");    	
    	weatherDesc.put(10, "Малооблачно");
    	weatherDesc.put(20, "Облачно");
    	weatherDesc.put(30, "Пасмурно");
    	weatherDesc.put(40, "Пасмурно, небольшой дождь");
    	weatherDesc.put(50, "Пасмурно, дождь");
    	weatherDesc.put(60, "Пасмурно, гроза");
    	weatherDesc.put(70, "Пасмурно, град");
    	weatherDesc.put(80, "Пасмурно, временами снег с дождем");
    	weatherDesc.put(90, "Пасмурно, временами снег");
    	weatherDesc.put(100, "Пасмурно, снег");
    	weatherDesc.put(255, "Малооблачно");
    	*/
    }  

}
