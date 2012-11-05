package mobile.meteo.org.activity;

import java.util.ArrayList;
import java.util.List;

import mobile.meteo.org.R;
import mobile.meteo.org.imps.CityArrayAdapter;
import mobile.meteo.org.managers.DataManager;
import mobile.meteo.org.objects.City;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CityPickActivity extends ListActivity {	
	
	protected CityArrayAdapter adapter;	
	
	protected DataManager dataManager;
	SharedPreferences prefs;
	
	protected City selectedCity;
	
	protected List<City> cities = new ArrayList<City>();	
	
	protected EditText city_name;
	protected String searchWord = "";
	
	
	
	/**
	 * 
	 * 
	 */
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		dataManager = new DataManager(this);	
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this); /* getSharedPreferences("preferences", Activity.MODE_PRIVATE); */
				
		setContentView(R.layout.city_pick_pref);
		
		city_name = (EditText) findViewById(R.id.city_name);		
		city_name.addTextChangedListener(new MyTextWatcher());			
		
		ListView listView = this.getListView(); 
		
		cities = dataManager.getCitiesListByWord(searchWord);		
		
		adapter = new CityArrayAdapter(this, cities);
		
		listView.setAdapter(adapter);
	}	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		dataManager.closeDatabase();
		dataManager = null;
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {	
		
		selectedCity = adapter.getItem(position);
		
		int selectedCityID = selectedCity.id;
		
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt("cid", selectedCityID);
		editor.putString("cityName", selectedCity.name /* dataManager.getCityNameById(selectedCityID) */ );
		editor.commit();
		
		
		this.finish();
		/*
		Intent mainActivity = new Intent(getBaseContext(), PogodnikActivity.class);
    	startActivity(mainActivity);   
    	*/    	
	}
	
	
	
	protected void reFillList() {	
			
		adapter.clear();
		
		for(City city : dataManager.getCitiesListByWord(searchWord)) {
			adapter.add(city);
		}
		
	}
	
	
	/**
	 * 
	 * @author yuriy
	 *
	 */
	protected class MyTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			searchWord = s.toString();
			reFillList();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,int count) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	
	
}