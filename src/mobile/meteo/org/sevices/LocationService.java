package mobile.meteo.org.sevices;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class LocationService extends Service {
	
	protected LocatorListener LocList;
	protected LocationManager locManager;
	protected Criteria providerCritera;	
	public String currentLocationProvider;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 
	 * 
	 */
	
	public void onCreate() {		
    	super.onCreate(); 
    	
		locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);		
		LocList = new LocatorListener();			
		startRequstingUpdates();
    }
	
	/**
	 * 
	 * 
	 * 
	 */
	protected void startRequstingUpdates() {		
	
		try {
			providerCritera = new Criteria();		
			currentLocationProvider = locManager.getBestProvider(providerCritera, true);
		} catch (Exception e) {
			
		} finally {
			
			if(currentLocationProvider != null) {
				locManager.requestLocationUpdates(currentLocationProvider, 0, 0, LocList);
			} else {
				locManager.requestLocationUpdates(locManager.NETWORK_PROVIDER, 0, 0, LocList);
			}
		}
	}
	
	
	/**
	 * 
	 * 
	 * @author yuriy
	 *
	 */
	
	protected class LocatorListener implements LocationListener {
	
		public Location location = null;			
		
		public LocatorListener() {	
		
		}	
		
		public void onLocationChanged(Location loc) { 	
		
			location = loc;			
			setLocationToApp(loc);
			
		} 	
		
		public void onProviderDisabled(String provider) {} 		
		public void onProviderEnabled(String provider) {} 	
		public void onStatusChanged(String provider, int status, Bundle extras) {} 	    
	
	}
	
	/**
	 * 
	 * 
	 * @param loc
	 */
	private void setLocationToApp(Location loc) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); /*    this.getSharedPreferences("preferences", Activity.MODE_PRIVATE);		*/
		Editor editor = prefs.edit();
		editor.putFloat("lat", (float) loc.getLatitude());
		editor.putFloat("lon", (float) loc.getLongitude());
		editor.commit();
		
		stopSelf();	
	}
	
	
	

}
