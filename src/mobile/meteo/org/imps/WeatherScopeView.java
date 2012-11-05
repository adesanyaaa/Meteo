package mobile.meteo.org.imps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mobile.meteo.org.R;
import mobile.meteo.org.objects.WeatherItem;
import mobile.meteo.org.statics.Misk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class WeatherScopeView extends View {
	
	protected List<WeatherScope> weatherScopes  = new ArrayList<WeatherScope>();
	private Context appContext;

	
	/**
	 * 
	 * 
	 * 
	 * @param context
	 * @param attrs
	 */

    public WeatherScopeView(Context context, AttributeSet attrs) {
    	super(context, attrs); 
    	appContext = context;    	
    }
    
    /**
     * 
     * 
     * 
     * 
     */

    @Override
    public void onDraw(Canvas canvas) {    	
    	super.onDraw(canvas);
    	
    	if(weatherScopes.size() > 0 ) {	
    		
    		canvas.drawColor(Color.argb(500, 230, 243, 249));
    		
    		for (Iterator<WeatherScope> it = weatherScopes.iterator(); it.hasNext(); ) {	
    			it.next().drawSelf(canvas);    			
    		}
    		

    	}
    }
    
    /**
     * 
     * 
     */
    public void addScope(List<WeatherItem> wi, int scopeColor, int textColor, String paramToScope) {
    	
    	weatherScopes.add(new WeatherScope(wi, scopeColor, textColor, paramToScope));
    }
    
    /**
     * 
     * 
     */    
    
    public void removeScopes() {
    	weatherScopes.clear();
    }
    
    /**
     * 
     * 
     * @author yuriy
     *
     */
    public class WeatherScope {
    	
    	public List<WeatherItem> weatherItems;
    	public Paint paint = new Paint();
    	public Paint textpaint = new Paint();
    	public List<Point> pointsToDraw;
    	public String param;
    	public double[] extrems;    	
    	
    	public WeatherScope(List<WeatherItem> wi, int scopeColor, int textColor, String paramToScope) {
    		
    		int i = 0;
    		
    		param = paramToScope;
    		
    		weatherItems = wi;
    		
    		pointsToDraw = new ArrayList<Point>();
    		
    		for (Iterator<WeatherItem> it = weatherItems.iterator(); it.hasNext(); ) {			
    			pointsToDraw.add(new Point(i, it.next().getParamInt(paramToScope)));
    			i++;
    		}
    		    		
    		textpaint.setColor(textColor);
        	textpaint.setTextSize(13);
        	
            paint.setColor(scopeColor);
            paint.setStrokeWidth(5);
            
            extrems = getPointsExtrems(pointsToDraw);
    	}
    	
    	/**
         * 
         * 
         * 
         */        
        protected double[] getPointsExtrems(List<Point> points) {
        	
        	double[] extrems = new double[4];
        	int i;
        	Point tmpPoint;  
        		
    		Iterator<Point> iter = points.iterator();
    		
    		i = 0;    		
    		
    		while (iter.hasNext()) {			
    			
    			tmpPoint = iter.next();
    			
    			if(i == 0) {
    				extrems[0] = tmpPoint.x;// min x
    				extrems[1] = tmpPoint.y;// min y
    				extrems[2] = tmpPoint.x;// max x
    				extrems[3] = tmpPoint.y;// max y
    			} else {    				
    				extrems[0] = (tmpPoint.x < extrems[0]) ? tmpPoint.x : extrems[0];// min x
    				extrems[1] = (tmpPoint.y < extrems[1]) ? tmpPoint.y : extrems[1];// min y
    				extrems[2] = (tmpPoint.x > extrems[2]) ? tmpPoint.x : extrems[2];// max x
    				extrems[3] = (tmpPoint.y > extrems[3]) ? tmpPoint.y : extrems[3];// max y
    			}    					
    			i++;	
    			
    		} 
    		
    		if(extrems[2] - extrems[0] == 0 ) {
    			extrems[2] += 1;
    			extrems[0] -= 1;
    		}
    		if(extrems[3] - extrems[1] == 0 ) {
    			extrems[3] += 1;
    			extrems[1] -= 1;
    			
    		}
        	
        	return extrems;
        }
        
        
        public void drawSelf(Canvas c) {
        	
        	int canvasWidth, canvasHeight, canvasDrW, canvasDrH, paddingH, paddingV = 50, bitmapPadding,  i = 0;  
        	int fontSize, padding;
        	double kx, ky;
        	Point prevPoint, currentPoint, tmpPoint;   
    		
    		canvasWidth = c.getWidth();
        	canvasHeight = c.getHeight();
        	
        	prevPoint = new Point(0, 0);
        	
        	paddingH = 0; /* canvasWidth / (pointsToDraw.size() + 3)  ;*/
        	paddingV = (int)(canvasHeight * 0.25);
        	
        	canvasDrW = canvasWidth - 2 * paddingH;
        	canvasDrH = canvasHeight - 2 * paddingV;	 	        	
        	
        	kx =  canvasDrW / (extrems[2] - extrems[0]);
        	ky =  canvasDrH / (extrems[3] - extrems[1]); 
    		
    		Iterator<Point> iter = pointsToDraw.iterator();	 
    		
    		Path path = new Path();
    		
    		while (iter.hasNext()) {      			
    			
    			tmpPoint = iter.next();
    			
    			currentPoint = new Point(
	    					(int)((tmpPoint.x - extrems[0])  * kx + paddingH),
	    					(int)(canvasHeight - (tmpPoint.y - extrems[1]) * ky - paddingV )
    					); 
    			
    			if(i == 0) {
    				prevPoint = currentPoint;
    				path.moveTo(currentPoint.x, currentPoint.y);
    			} else {   
    				
    				path.lineTo(currentPoint.x, currentPoint.y);
    			}
    			i++;  	
    			
    		}
    		
    		path.lineTo(canvasWidth, canvasHeight);
    		path.lineTo(0, canvasHeight);    		
    		
    		c.drawPath(path, paint);
    		
    		fontSize = (int)(canvasHeight * 0.035);  
    		padding = (int)Math.ceil(fontSize * 0.2);
    		
    		textpaint.setTextSize(fontSize);
    		
    		for (Iterator<Point> it = pointsToDraw.iterator(); it.hasNext(); ) {
    			
    			tmpPoint = it.next();
    			
    			currentPoint = new Point(
    							(int)((tmpPoint.x - extrems[0])  * kx + paddingH + 5),
    							(int)(canvasHeight - (tmpPoint.y - extrems[1]) * ky - paddingV + 5)
    					); 
    			
    			if(currentPoint.x + 80 > canvasWidth) currentPoint.x -= 80;
    			if(currentPoint.y + (fontSize + padding) * 4 > canvasHeight) currentPoint.y -= (fontSize + padding) * 4;
    			
    			c.drawText(Misk.getDayOfWeekAsString(Misk.getDayOfWeekFromTimestamp(weatherItems.get(tmpPoint.x).date), appContext ), currentPoint.x, currentPoint.y, textpaint);
    			c.drawText(weatherItems.get(tmpPoint.x).getParamString(param), currentPoint.x, currentPoint.y + (fontSize + padding), textpaint);
    			c.drawText(Misk.getDateDM(weatherItems.get(tmpPoint.x).date), currentPoint.x, currentPoint.y + (fontSize + padding) * 2, textpaint);
    		}        	
        }
    	
    }
    
    
	

}