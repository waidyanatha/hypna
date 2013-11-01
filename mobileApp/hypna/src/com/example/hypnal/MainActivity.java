package com.example.hypnal;

//import com.mkyong.android.App2Activity;
//import com.mkyong.android.R;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button button;
	EditText editText;
	String url = "http://192.168.169.1:9763/POS/login.jag";
	String result;
	String delimiter = "-";
	RestClient restClient = RestClient.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void addListenerOnButton() {

		final Context context = this;

		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				EditText editText = (EditText)findViewById(R.id.editText1);
				EditText editText2 = (EditText)findViewById(R.id.editText2);
				
				//connect("http://192.168.169.1:9763/POS/");

				String editTextStr = editText.getText().toString();
				String editTextStr2 = editText2.getText().toString();
				
				Log.i("mylog","out is username"+editTextStr);
				if(editTextStr.equals("")){
					Toast.makeText(MainActivity.this,
							"Please Enter UserName", 15).show();
				}else 
				if(editTextStr2.equals("")){
					Toast.makeText(MainActivity.this,
							"Please Enter Password", 15).show();
				}else{
					if(login(editTextStr, editTextStr2))
					{
						RestClient.username = editTextStr;
				Toast.makeText(MainActivity.this,
						"Login in as "+editTextStr +".", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, App2Activity.class);
                startActivity(intent);  
					}else{
						Toast.makeText(MainActivity.this,
								editTextStr +"is Login is false", Toast.LENGTH_LONG).show();
					}
				}
			}

		});

	}
	
	public boolean login(String user,String pass){
		boolean out=false;
		try {
			String[] headerArr = {"username-"+user,"password-"+pass};
			JSONObject js = new JSONObject(restClient.callWebService("login.jag","login",headerArr));
		//	Log.i("mylog", "json"+js.length());
			Log.i("mylog", "json"+js.get("login"));
			RestClient.skey = js.get("sessionId").toString();
			if(Boolean.parseBoolean(js.get("login").toString())){
				out=true;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
  
		
}
