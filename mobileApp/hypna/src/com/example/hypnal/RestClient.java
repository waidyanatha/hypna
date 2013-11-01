package com.example.hypnal;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class RestClient {
	
	//String url = "http://192.168.169.1:9763/hypna/";
	String url = "http://202.69.197.115:9763/hypna/";
	String result;
	String delimiter = "-";
	String subdelimiter = "_";
	static String skey=null;
	static String username=null;
	private static RestClient instance = null;
	 
    private RestClient() {       }

    public static RestClient getInstance() {
            if (instance == null) {
                    synchronized (RestClient .class){
                            if (instance == null) {
                                    instance = new RestClient ();
                            }
                  }
            }
            return instance;
    }

	public String callWebService(String page,String action) {
		// Log.i("mylog", "callWebService endx"+q);
		HttpClient httpclient = new DefaultHttpClient();
		String restCall = url + page+"?action=" + action;
		Log.i("mylog", "Rest URL " + restCall);
		HttpGet request = new HttpGet(restCall);

		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.i("mylog", "callWebService endxxcc" + e);
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("mylog", "callWebService endxxcc" + e);
		}
		httpclient.getConnectionManager().shutdown();

		Log.i("mylog", result);
		return result;
	} // end callWebService()

	// adding headers
	/*
	 * adding header example [<key>-<value>,<key>-<value>]
	 */
	public String callWebService(String page, String action, String[] header) {
		Log.i("mylog", "callWebService header.length" + header.length);
		Log.i("mylog", "callWebService header.length" + header[0]);

		Log.i("mylog", "callWebService header.length" + header[1]);

		HttpClient httpclient = new DefaultHttpClient();
		String restCall = url +page+ "?action=" + action;
		Log.i("mylog", "Rest URL " + restCall);
		HttpGet request = new HttpGet(restCall);
		for (int i = 0; i < header.length; i++) {
			String str = header[i];
			Log.i("mylog", "Rest URL header " + i + str);

			String[] temp = str.split(delimiter);
			Log.i("mylog", "Rest URL header " + temp[0] + "  " + temp[1]);
			try {
				request.addHeader(temp[0], temp[1]);
			} catch (ArrayIndexOutOfBoundsException e) {

				e.printStackTrace();
				Log.i("mylog", "callWebService endxxcc" + e);
			}
		}

		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.i("mylog", "callWebService endxxcc" + e);
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("mylog", "callWebService endxxcc" + e);
		}
		httpclient.getConnectionManager().shutdown();

		Log.i("mylog", result);
		return result;
	} // end callWebService()
	
	
	/*
	 * 
	 * patch due to database strucutre location names
	 */
	
	public String callWebServicePatch(String page, String action, String[] header) {
		Log.i("mylog", "callWebService header.length" + header.length);
		Log.i("mylog", "callWebService header.length" + header[0]);

		Log.i("mylog", "callWebService header.length" + header[1]);

		HttpClient httpclient = new DefaultHttpClient();
		String restCall = url +page+ "?action=" + action;
		Log.i("mylog", "Rest URL " + restCall);
		HttpGet request = new HttpGet(restCall);
		for (int i = 0; i < header.length; i++) {
			String str = header[i];
			Log.i("mylog", "Rest URL header " + i + str);

			String[] temp = str.split(subdelimiter);
			Log.i("mylog", "Rest URL header " + temp[0] + "  " + temp[1]);
			try {
				request.addHeader(temp[0], temp[1]);
			} catch (ArrayIndexOutOfBoundsException e) {

				e.printStackTrace();
				Log.i("mylog", "callWebService endxxcc" + e);
			}
		}

		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.i("mylog", "callWebService endxxcc" + e);
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("mylog", "callWebService endxxcc" + e);
		}
		httpclient.getConnectionManager().shutdown();

		Log.i("mylog", result);
		return result;
	} // end callWebService()

}
