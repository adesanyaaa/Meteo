package mobile.meteo.org.sevices;

import mobile.meteo.org.R;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param opacity 0-100
	 * @return
	 */
	protected static Bitmap getBackground(int opacity) {
		try
	    {
			
			opacity = (int)(2.55 * opacity);
			
	        Bitmap.Config config = Bitmap.Config.ARGB_8888; // Bitmap.Config.ARGB_8888 Bitmap.Config.ARGB_4444 to be used as these two config constant supports transparency
	        Bitmap bitmap = Bitmap.createBitmap(2, 2, config); // Create a Bitmap
	 
	        Canvas canvas =  new Canvas(bitmap); // Load the Bitmap to the Canvas	        
	        int colour = (opacity & 0xFF) << 24;
	        canvas.drawColor(colour);
	        return bitmap;
	    }
	    catch (Exception e)
	    {
	        return null;
	    }
	}
	
	

}
