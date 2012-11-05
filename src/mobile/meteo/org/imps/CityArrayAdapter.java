package mobile.meteo.org.imps;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import mobile.meteo.org.R;
import mobile.meteo.org.objects.City;;

public class CityArrayAdapter extends ArrayAdapter<City> {
	
	private final Context context;
	private final List<City> values;
	
	

	public CityArrayAdapter(Context context, List<City> cities) {
		super(context, R.layout.city_list_item, cities);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		values = cities;
	}
	
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.city_list_item, parent, false);
	    
	    TextView city = (TextView) rowView.findViewById(R.id.city);
	    TextView country = (TextView) rowView.findViewById(R.id.country);	    
	    
	    city.setText(values.get(position).name);
	    country.setText(values.get(position).country);
	
	    return rowView;
	  }

	

	

	
	

}
