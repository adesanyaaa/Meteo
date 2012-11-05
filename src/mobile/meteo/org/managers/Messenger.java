package mobile.meteo.org.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

public class Messenger {
	
	private static Messenger instance = null;
	
	private HttpClient hc;
	private HttpPost post;
	private HttpResponse rp = null;
	private List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	
	private String URL;
	private String ResponseString;
	
	
	/**
	 * 
	 * 
	 */
	private Messenger() {
		
		HttpParams httpParameters = new BasicHttpParams();
		
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);		
		
		hc = new DefaultHttpClient(httpParameters);		
		post = new HttpPost("");
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public static Messenger getInstance() {
		
		if(instance == null) {
			instance = new Messenger();
		}	
		
		return instance;		
	}
	
	/**
	 * 
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	
	public int setPostParam(String name, String value) {
		
		postParams.add(new BasicNameValuePair(name, value));	
		
		return postParams.size(); 		
	}
	
	
	/***
	 * 
	 * 
	 * 
	 * 
	 */
	
	public int setPostParam(BasicNameValuePair pair) {
		
		postParams.add(pair);	
		
		return postParams.size(); 		
	}
	
	/**
	 * 
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	
	public int setPostParams(List<NameValuePair> params) {
		
		postParams = params;
		
		return postParams.size(); 		
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	
	public boolean clearPostParams() {
		
		postParams.clear();
		
		return postParams.size() == 0 ? true : false;	
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param url
	 */
	
	public void setURL(String url) {
		URL = url;		
	}
		
	/**
	 * 
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	private boolean execute() throws ClientProtocolException, IOException {
		
		post = new HttpPost(URL);
		post.setEntity(new UrlEncodedFormEntity(postParams, HTTP.UTF_8));		
		rp = hc.execute(post);		
		return rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK;	
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param url
	 * @param post
	 * @return
	 */
	public String post(String url, List<NameValuePair> post) {
		
		try {		
			setURL(url);
			setPostParams(post);		
			execute();		
			getStringFromResponse();	
			return ResponseString;
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}	
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param url
	 * @param post
	 * @return
	 */
	public String post(String url, BasicNameValuePair pair) {
		
		try {		
			setURL(url);
			clearPostParams();
			setPostParam(pair);		
			execute();		
			getStringFromResponse();	
			return ResponseString;
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}	
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param url
	 * @return
	 */
	public String post(String url) {
		
		try {		
			setURL(url);			
			execute();		
			getStringFromResponse();	
			return ResponseString;
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}	
	}
	
	
	
	/**
	 * 
	 * 
	 * 	
	 * @return
	 */
	public String post() {
		
		try {					
			execute();		
			getStringFromResponse();	
			return ResponseString;
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}	
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @throws IOException
	 */	
	
	private void getStringFromResponse() throws IOException {
		
		if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
 		{     			
 			BufferedReader reader = new BufferedReader(new InputStreamReader(rp.getEntity().getContent(), "UTF-8"));     			
 			StringBuilder builder = new StringBuilder();
 			
 			for (String line = null; (line = reader.readLine()) != null;) {
 			    builder.append(line).append("\n");
 			}
 			
 			ResponseString = builder.toString();		 			
 		}		
				
	}
	
	
	
	
	
	
	

}