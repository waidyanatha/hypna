package com.example.hypnal;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocationActivity extends Activity {

	Button button, button2;
	String tvalue;
	String editTextStr, editTextStr1, editTextStr2;
	RestClient restClient = RestClient.getInstance();
	 // GPSTracker class
    GPSTracker gps;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_location);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tvalue = extras.getString("KEY");
			Log.i("mylog", "value " + tvalue);
		}
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		final Context context = this;

		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (validation()) {
					JSONObject js = null;
					JSONObject js1 = null;
					String strlt = "";
					String strlt1 = "";
					boolean strlterror = true;

					String[] headerArr = { "locname_" + editTextStr,
							"longitude_" + editTextStr1,
							"latitude_" + editTextStr2 };
					String[] headerArr1 = { "locationName_" + editTextStr,
							"tpk_" + tvalue };
					try {
						js = new JSONObject(restClient.callWebServicePatch(
								"location.jag", "addLocation&token="
										+ RestClient.skey, headerArr));
						strlt = js.get("results").toString();
						strlterror = Boolean.parseBoolean(js.get("error")
								.toString());

						Log.i("mylog", "json" + js.length());
						if (!strlterror) {
							js1 = new JSONObject(restClient
									.callWebServicePatch("transaction.jag",
											"updateLocData&token="
													+ RestClient.skey,
											headerArr1));
							strlt1 = js.get("tranaction").toString();
							Log.i("mylog", "json" + js.length());
						} else {
							Toast.makeText(AddLocationActivity.this,
									"Server Error on saving " + tvalue, 15)
									.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Toast.makeText(AddLocationActivity.this,
							strlt + " is saved" + tvalue, 15).show();
					Intent intent = new Intent(context, FinalActivity.class);
					intent.putExtra("KEY", tvalue);
					startActivity(intent);
				}
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 // create class object
                gps = new GPSTracker(AddLocationActivity.this);
 
                // check if GPS enabled
                if(gps.canGetLocation()){
 
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    EditText editText2 = (EditText)findViewById(R.id.editText2);
    				editText2.setText(""+longitude);
    				 EditText editText3 = (EditText)findViewById(R.id.editText3);
     				editText3.setText(""+latitude);
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
			}
		});

	}

	public boolean validation() {
		boolean out = false;
		EditText editText = (EditText) findViewById(R.id.editText1);
		editTextStr = editText.getText().toString();
		EditText editText1 = (EditText) findViewById(R.id.editText2);
		editTextStr1 = editText1.getText().toString();
		EditText editText2 = (EditText) findViewById(R.id.editText3);
		editTextStr2 = editText2.getText().toString();
		if (editTextStr.equals("")) {
			Toast.makeText(AddLocationActivity.this,
					"Please Enter Location Name", 15).show();
		} else if (editTextStr1.equals("")) {
			Toast.makeText(AddLocationActivity.this,
					"Longitude can not be empty", 15).show();
		} else if (editTextStr2.equals("")) {
			Toast.makeText(AddLocationActivity.this,
					"Latitude can not be empty", 15).show();
		} else {
			out = true;
		}
		return out;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_location, menu);
		return true;
	}

}
