package com.example.hypnal;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class LatePayActivity extends Activity {

	Button button;
	 DatePicker datePicker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_late_pay);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_late_pay, menu);
		return true;
	}
	
	public void addListenerOnButton() {

		final Context context = this;

		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				if (validation())
					
				{
				Intent intent = new Intent(context, App2Activity.class);
				datePicker = (DatePicker)findViewById(R.id.datePicker1);
				String date = "2013-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
				intent.putExtra("KEY1",date);
				Toast.makeText(LatePayActivity.this,
						date+ " is pay date", 15).show();
                startActivity(intent);   
				}
			}

			private boolean validation() {
				boolean out=false;
				datePicker = (DatePicker)findViewById(R.id.datePicker1);
				Log.i("mylog","out is username"+datePicker.getYear()+":"+datePicker.getMonth());
				Date date = new Date();
				
				if(datePicker.getYear() >= date.getYear() && datePicker.getMonth()>= date.getMonth() && datePicker.getDayOfMonth() >date.getDate()){
					out=true;
				}else{
					Toast.makeText(LatePayActivity.this,
							"Please pick valid Date ", 15).show();
				}
				
				return out;
			}


		});
	}

}
