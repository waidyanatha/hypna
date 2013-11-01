package com.example.hypnal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Shop_Activity extends Activity {

	Button button, button2,button3  ;
	String tvalue;
	private Spinner spinner1;
	RestClient restClient = RestClient.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tvalue = extras.getString("KEY");
			Log.i("mylog", "value " + tvalue);
		}
		addItemsOnSpinner1();
		addListenerOnButton();
	}

	private void addItemsOnSpinner1() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		String[] strArr;
		JSONObject js;
		JSONArray ja;
		try {
			js = new JSONObject(restClient.callWebService("location.jag",
					"listlocationNames&token=" + RestClient.skey));

			Log.i("mylog", "json" + js.length());
			ja = js.getJSONArray("results");
			Log.i("mylog", "json" + ja.length());
			for (int i = 0; i < ja.length(); i++) {
				// Log.i("mylog",
				// "json loc name"+ja.getJSONObject(i).get("loc_name"));
				list.add(ja.getJSONObject(i).get("loc_name").toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String strTS = "";

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_shop, menu);
		return true;
	}

	public void addListenerOnButton() {

		final Context context = this;

		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				JSONObject js = null;
				String strlt = "";
				String locaname = spinner1.getSelectedItem().toString();
				Log.i("mylog", "locanamex " + locaname);
				Log.i("mylog", "tvalue " + tvalue);
				String[] headerArr = { "locationName_" + locaname,
						"tpk_" + tvalue };

				try {
					js = new JSONObject(restClient.callWebServicePatch(
							"transaction.jag", "updateLocData&token="
									+ RestClient.skey, headerArr));
					strlt = js.get("tranaction").toString();
					Log.i("mylog", "json" + js.length());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Toast.makeText(Shop_Activity.this,
						strlt + " Data is sent to server" + tvalue, 15).show();
				Intent intent = new Intent(context, FinalActivity.class);
				startActivity(intent);

			}
		});

		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(context,AddLocationActivity.class );
				intent.putExtra("KEY",tvalue);
                startActivity(intent);  

			}

		});
		
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(context,FinalActivity.class );
				intent.putExtra("KEY",tvalue);
                startActivity(intent);  

			}

		});
		
	

	}
}
