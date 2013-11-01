package com.example.hypnal;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gdseed.mobilereader.MobileReader;
import com.gdseed.mobilereader.MobileReader.ReaderStatus;

public class App2Activity extends Activity {

	// -----------------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------------
	private static final String Algorithm = "DESede/ECB/NOPADDING";
	private final static String INTENT_ACTION_CALL_STATE = "com.gdseed.mobilereader.CALL_STATE";
	private final static String modelMS62x = "MS62x";
	private final static String modelMS22x = "MS22x";
	private TextView outMsg,cardNumber;
	private Button decodeButton;
	private Spinner spinnerModel;
	private MobileReader mobileReader;
	private IncomingCallServiceReceiver incomingCallServiceReceiver;
	private String phoneModel = new String();
	private String phoneSysCode = new String();
	private String phoneManufacturer = new String();
	private boolean playSounds = false;
	RestClient restClient = RestClient.getInstance();
	Intent intent;
	private EditText editText1, editText2;
	Button button,button2,button3;
	public static final String INTENT_ACTION_INCOMING_CALL = "com.gdseed.mobilereader.INCOMING_CALL";
	
	private TelephonyManager callManager;
	private IncomingCallListener incomingCallListener;
	
	String editTextStr;
	String editTextDateStr;
	String editTextPinStr;
	String editTextAmountStr;
	String ltrDate;
	Boolean secondCome=false;
	
	// -----------------------------------------------------------------------
	// Debug Function
	// -----------------------------------------------------------------------
	
//	private static final String LOG_TAG = MobileReaderCallStateService.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app2);
		mobileReader = new MobileReader(App2Activity.this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			secondCome= extras.getBoolean("KEY");
			ltrDate = extras.getString("KEY1");
			Log.i("mylog", "value " + secondCome);
			Log.i("mylog", "ltrDate " + ltrDate);
			if(secondCome){
				Toast.makeText(App2Activity.this,
						"For New Transaction", 15).show();
				EditText editText = (EditText)findViewById(R.id.editText1);
				editText.setText("");
				EditText editText2 = (EditText)findViewById(R.id.editText2);
				editText2.setText("");
				EditText editText3 = (EditText)findViewById(R.id.editText3);
				editText3.setText("");
				EditText editText5 = (EditText)findViewById(R.id.editText5);
				editText5.setText("");
			}
		}
		mobileReader.setOnDataListener(new MobileReader.CallInterface() {
			@Override
			public void call(ReaderStatus state) {
				Intent intent = new Intent(INTENT_ACTION_CALL_STATE);
				int tmp = state.ordinal();
				intent.putExtra("ReaderStatus", tmp);
				App2Activity.this.sendBroadcast(intent);
			}
		});
		
		initViews();
		startCallStateService();
		addListenerOnButton();
		initCallManager();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_app2, menu);
		mobileReader = new MobileReader(App2Activity.this);
		mobileReader.setOnDataListener(new MobileReader.CallInterface() {
			@Override
			public void call(ReaderStatus state) {
				Intent intent = new Intent(INTENT_ACTION_CALL_STATE);
				int tmp = state.ordinal();
				intent.putExtra("ReaderStatus", tmp);
				App2Activity.this.sendBroadcast(intent);
			}
		});
		initViews();
		startCallStateService();
		return true;
	}
	
	private void initViews() {
		
		decodeButton = (Button) findViewById(R.id.button3);		
		
		int defaultValue = ReaderStatus.DEVICE_PLUGOUT.ordinal();
		Log.i("mylog","defaultValue "+defaultValue);
	//	ReaderStatus state = ReaderStatus.values()[intent.getIntExtra("ReaderStatus", defaultValue)];
	outMsg = (TextView) findViewById(R.id.textView4);
		cardNumber = (TextView) findViewById(R.id.textView1);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		
		outMsg.setText("..");
		mobileReader.close();
	}
	
	@Override 
	public void onResume() {
		startCallStateService();
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		endCallStateService();
		mobileReader.close();
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		mobileReader.close();
		endCallStateService();
		resetUIControls();
		super.onPause();
	}

	@Override
	public void onStop() {
		mobileReader.close();
		endCallStateService();
		resetUIControls();
		super.onStop();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mobileReader.close();
			endCallStateService();
			resetUIControls();
			mobileReader.setOnDataListener(null);
		}
		
		return super.onKeyDown(keyCode, event);
	}

	private void startCallStateService() {
Log.i("mylog","startCallStateService");
		startService(new Intent(INTENT_ACTION_CALL_STATE));
		if (incomingCallServiceReceiver == null) {
			incomingCallServiceReceiver = new IncomingCallServiceReceiver();
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(INTENT_ACTION_CALL_STATE);
			this.registerReceiver(incomingCallServiceReceiver, intentFilter);
		}
	}

	private void endCallStateService() {
		stopService(new Intent(INTENT_ACTION_CALL_STATE));
		if (incomingCallServiceReceiver != null) {
			this.unregisterReceiver(incomingCallServiceReceiver);
			incomingCallServiceReceiver = null;
		}
	}

	public void resetUIControls() {
		outMsg.setText("");
		decodeButton.setEnabled(true);
		decodeButton.setText("START");
	}

	public void addListenerOnButton() {

		final Context context = this;

		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		decodeButton = (Button) findViewById(R.id.button3);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				if (validation())
					
				{
				Intent intent = new Intent(context, LatePayActivity.class);
                startActivity(intent);   
				}
			}


		});
		

		
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
			if (validation())				
				{
				String uname = RestClient.username;
				String[] headerArr = {"cardNum-"+editTextStr,"expDate-"+editTextDateStr,"pin-"+editTextPinStr,"amount-"+editTextAmountStr,"user-"+uname};
				JSONObject js;
				String strTS="";
				String strTSid="";
				try {
					js = new JSONObject(restClient.callWebService("transaction.jag","addMasterData&token="+RestClient.skey ,headerArr));
				
				Log.i("mylog", "json"+js.length());
				//Log.i("mylog", "json"+js.get("login"));
				strTS = js.get("tranaction").toString();
				strTSid = js.get("transactionId").toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Toast.makeText(App2Activity.this,
						" "+strTS, Toast.LENGTH_LONG).show();
				Transaction transaction = new Transaction();
				transaction.setTranacstionid(strTSid);
				//Intent i = new Intent(this, FindAndroidActivity.class);
				
				Intent intent = new Intent(context, Shop_Activity.class);
				intent.putExtra("KEY",transaction.getTranacstionid());
                startActivity(intent);   
				}
			}			
		});
		
		decodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.i("mylog","Decode button was clicked");
				if (decodeButton.getText().toString().equals("START")) {
					Log.i("mylog","Decode button was clicked");
					
					if (mobileReader.open(playSounds)) {
						Log.i("mylog","Decode button was  START ps clicked");
						
						resetUIControls();
						decodeButton.setText("STOP");
						outMsg.setTextColor(Color.BLUE);
						outMsg.setText("Swipe card please ...");
					}
				} else {
					Log.i("mylog","START ELSE Decode button was clicked");
					
					mobileReader.close();
					resetUIControls();
					decodeButton.setText("START");
				}
			}
		});

	}
	
	
	private void initCallManager() {
		if (callManager == null) {
			Log.i("test","Add Incoming Call Manager");
			callManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			incomingCallListener = new IncomingCallListener();
			callManager.listen(incomingCallListener, PhoneStateListener.LISTEN_CALL_STATE);		
		}
	}
	

		
		private void removeCallManager() {
			if (callManager != null) {
				//log("Remove Call Manager");
				callManager.listen(this.incomingCallListener, PhoneStateListener.LISTEN_NONE);
				callManager = null;
			}
		}
		
		private class IncomingCallListener extends PhoneStateListener {		
			@Override
			public void onCallStateChanged(int callstate, String incomingNumber) {			
				if (callstate == TelephonyManager.CALL_STATE_RINGING) {				
					//log("Incoming CALL!!!");
					sendBroadcast(new Intent(INTENT_ACTION_INCOMING_CALL));
				}
			}
		}
		

	public boolean validation(){
		boolean out=false;
		EditText editText = (EditText)findViewById(R.id.editText1);
		editTextStr = editText.getText().toString();
		EditText editTextDate = (EditText)findViewById(R.id.editText2);
		editTextDateStr = editTextDate.getText().toString();
		EditText editTextPin = (EditText)findViewById(R.id.editText3);
		editTextPinStr = editTextPin.getText().toString();
		EditText editTextAmount = (EditText)findViewById(R.id.editText5);
		editTextAmountStr = editTextAmount.getText().toString();
		
		if(editTextStr.equals("")){
			Toast.makeText(App2Activity.this,
					"Please Enter Card Number", 15).show();
		}else if (editTextStr.length()!=16) {
				Toast.makeText(App2Activity.this,
						"Please Enter Correct Card Number", 15).show();
			
		}else if(editTextDateStr.equals("")){
			Log.i("mylog","xxxxxxxxx"+editTextDateStr);
			Toast.makeText(App2Activity.this,
					"Please Enter Expiry Date", 15).show();
		}
		else if (editTextDateStr.length()!=4) {
			Log.i("mylog","editTextDateStr"+editTextDateStr.substring(0, 2));
			
			Toast.makeText(App2Activity.this,
					"Please Enter Correct Expiry Date [mm/yy]", 20).show();
		
	}
		else if(editTextPin.equals("")){
			Toast.makeText(App2Activity.this,
					"Please Enter CSC", 15).show();
		}
		/*else if (editTextPin.length()>3) {
			Toast.makeText(App2Activity.this,
					"Please Enter Correct Pin", 20).show();
		
	}*/
		else if(editTextAmountStr.equals("")){
			Toast.makeText(App2Activity.this,
					"Please Add Amount", 15).show();
		}
		else if (editTextAmountStr.length()>10) {
			Toast.makeText(App2Activity.this,
					"Amount is To much to submit", 20).show();
		
	}else {
		out=true;
	
}
		return out;
	}
	// -----------------------------------------------------------------------
	// Inner classes
	// -----------------------------------------------------------------------

	private class IncomingCallServiceReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("mylog","IncomingCallServiceReceiver");
			byte rawData[] = new byte[1024];
			int trackCount[] = new int[1];
			trackCount[0] = 0;
			int defaultValue = ReaderStatus.DEVICE_PLUGOUT.ordinal();
			ReaderStatus state = ReaderStatus.values()[intent.getIntExtra(
					"ReaderStatus", defaultValue)];
			Log.i("mylog","ReaderStatus "+state);
			
			if (ReaderStatus.BEGIN_RECEIVE == state) {
				outMsg.setTextColor(Color.BLUE);
				outMsg.setText("Receiving ...");
			} else if (ReaderStatus.TIMER_OUT == state) {
				mobileReader.close();
				resetUIControls();
				outMsg.setTextColor(Color.BLACK);
				outMsg.setText("Timer out ...");
				return;
			} else if (ReaderStatus.END_RECEIVE == state) {
				outMsg.setTextColor(Color.GREEN);
				outMsg.setText("Decoding ...");
			} else if (ReaderStatus.DEVICE_PLUGIN == state) {
				Log.i("mylog",""+state);
				
				boolean back = true;
				if (back) return;
				mobileReader.close();
				resetUIControls();
				if (mobileReader.open(playSounds)) {
					outMsg.setTextColor(Color.BLACK);
					decodeButton.setText("STOP");
					outMsg.setText("Swipe card please ...");
				}
				
				return;
			} else if (ReaderStatus.DEVICE_PLUGOUT == state) {
				mobileReader.close();
				resetUIControls();
			} else if (ReaderStatus.DECODE_OK == state) {
				int len = mobileReader.read(rawData);
				if (len < 1) {
					outMsg.setTextColor(Color.RED);
					outMsg.setText("Reader Error.");
					if (len < 1) {
						//mobileReader.writeRecoderToFile(getCurrentFileName() + "_null.raw");
					} else {
						//mobileReader.writeRecoderToFile(getCurrentFileName() + "_crc.raw");
					}
				} else {
					String display = new String();
					if (0x07 == rawData[0] || 0x50 == rawData[0]
							|| 0x48 == rawData[0] || 0x08 == rawData[0]
							|| 0x49 == rawData[0]) {
						outMsg.setTextColor(Color.RED);
						display = "Please update the lastest hardware!";
					} else if (0x60 == rawData[0]) {
						display = Lib12_ParseTrack(rawData, trackCount);
						if (trackCount[0] < 2) {
							outMsg.setTextColor(Color.RED);
						} else {
							outMsg.setTextColor(Color.BLUE);
						}
					} else {
						outMsg.setTextColor(Color.RED);
						display = "Unknown Format !!";
					//	mobileReader.writeRecoderToFile(getCurrentFileName() + "_unknown.raw");
					}

					if (0 == display.length()) {
						outMsg.setTextColor(Color.RED);
						outMsg.setText("Parse Data Error");
					} else {
						outMsg.setTextColor(Color.BLACK);
						outMsg.setText(display);
					}
				}

				mobileReader.close();
				decodeButton.setText("START");
				if (mobileReader.open(playSounds)) {
					decodeButton.setText("STOP");
				}
			} else {
				String errorInfo = new String();
				outMsg.setTextColor(Color.RED);
				if (ReaderStatus.DECODE_ERROR == state) {
					errorInfo = "Decode Error!";
				} else if (ReaderStatus.RECEIVE_ERROR == state) {
					errorInfo = "Receive Error!";
				} else if (ReaderStatus.BUF_OVERFLOW == state) {
					errorInfo = "Buf Overflow !";
				}

			//	mobileReader.writeRecoderToFile(getCurrentFileName() + "_error.raw");
				outMsg.setText(errorInfo);
				mobileReader.close();
				decodeButton.setText("START");
			}
		} // end-of onReceive
	}

	private String Lib12_ParseTrack(byte input[], int track[]) {
		int panLength = 0;
		int index = 0;
		String version = new String();
		String encryptMode = new String();
		String first6Pan = new String();
		String last4Pan = new String();
		String expiryDate = new String();
		String userName = new String();
		String ksn = new String();
		String encrypedData = new String();
		String ret = new String();
		String xxx = new String();
		String trackInfo = new String();
		byte byEncrypedData[];
		String decrypedData = new String();
		int tmp = 0;

		index = 1;
		version += (char) ('0' + input[index]);
		version += '.';
		version += (char) ('0' + input[++index]);

		tmp = input[++index];
		if (0 == tmp) {
			encryptMode = "unknown";
		} else if (1 == tmp) {
			encryptMode = "fixed key";
		} else {
			encryptMode = "dukpt";
		}

		panLength = input[++index];
		// xxx
		for (int i = 0; i < panLength - 10; i++) {
			xxx += "x";
		}

		// fist 4 pan
		index++;
		for (int i = 0; i < 6; i++) {
			tmp = input[(i >> 1) + index] & 0xff;
			tmp = i % 2 == 0 ? tmp >> 4 : tmp & 0x0f;
			tmp = tmp > 9 ? tmp - 10 + 'A' : tmp + '0';
			first6Pan += (char) tmp;
		}

		// last 4 pan
		index += 3;
		for (int i = 0; i < 4; i++) {
			tmp = input[(i >> 1) + index] & 0xff;
			tmp = i % 2 == 0 ? tmp >> 4 : tmp & 0x0f;
			tmp = tmp > 9 ? tmp - 10 + 'A' : tmp + '0';
			last4Pan += (char) tmp;
		}

		// expiry data
		index += 2;
		for (int i = 0; i < 4; i++) {
			tmp = input[(i >> 1) + index] & 0xff;
			tmp = i % 2 == 0 ? tmp >> 4 : tmp & 0x0f;
			tmp = tmp > 9 ? tmp - 10 + 'A' : tmp + '0';
			expiryDate += (char) tmp;
		}

		// User name
		index += 2;
		for (int i = 0; i < 26; i++) {
			userName += (char) input[i + index];
		}

		// ksn
		index += 26;
		for (int i = 0; i < 20; i++) {
			tmp = input[(i >> 1) + index] & 0xff;
			tmp = i % 2 == 0 ? tmp >>> 4 : tmp & 0x0f;
			tmp = tmp > 9 ? tmp - 10 + 'A' : tmp + '0';
			ksn += (char) tmp;
		}

		// encrypted data
		index += 10;
		for (int i = 0; i < 160; i++) {
			if (0 != i && 0 == (i % 32))
				encrypedData += '\n';
			tmp = input[(i >> 1) + index] & 0xff;
			tmp = i % 2 == 0 ? tmp >> 4 : tmp & 0x0f;
			tmp = tmp > 9 ? tmp - 10 + 'a' : tmp + '0';
			encrypedData += (char) tmp;
		}
		
		byEncrypedData = new byte[80];
		for (int i = 0; i < 80; i++) {
			byEncrypedData[i] = input[index + i];
		}

		index += 80;
		byte tmpTrackInfo = input[index];
		int trackCount = 0;
		for (int i = 0; i < 3; i++) {
			byte info = (byte) (tmpTrackInfo & (0x01 << i));
			if (0 != info) {
				trackInfo += (char) ('1' + i);
				trackCount++;
			}
		}
		
		if (null != track) {
			track[0] = trackCount;
		}

		/*ret = ("Firmware Version:" + version + "\n" + "Encryption Mode:"
				+ encryptMode + "\n" + "Track Info:" + trackInfo + "\n"
				+ "PAN:" + first6Pan + xxx + last4Pan + "\n" + "Expiry Date:"
				+ expiryDate + "\n" + "User Name:" + userName + "\n" + "KSN:"
				+ ksn + "\n" + "Encrypted Data:" + "\n" + encrypedData + "\n");*/
		ret="Card Data retrieved.";
		editText1.setText(first6Pan + xxx + last4Pan);
		editText2.setText(expiryDate);
		
		
		if (true) {
			return ret;
		}

		byte[] tmp_key = {0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0xfe, (byte)0xdc, (byte)0xba, (byte)0x98, 0x76, 0x54, 0x32, 0x10};
		for(int i = 0; i < byEncrypedData.length; i++) {
			if (0 != i && 0 == (i % 16)) {
				System.out.format("\n");
			}
			
			System.out.format("%02x ", byEncrypedData[i]);
		}
		byte[] srcBytes = decryptMode(tmp_key, byEncrypedData);
		for(int i = 0; i < srcBytes.length; i++) {
			if (0 != i && 0 == (i % 16)) {
				System.out.format("\n");
			}
			
			System.out.format("%02x ", srcBytes[i]);
		}
		
		for (int i = 0; i < 160; i++) {
			if (0 != i && 0 == (i % 32))
				decrypedData += '\n';
			tmp = srcBytes[(i >> 1)] & 0xff;
			tmp = i % 2 == 0 ? tmp >> 4 : tmp & 0x0f;
			tmp = tmp > 9 ? tmp - 10 + 'a' : tmp + '0';
			decrypedData += (char) tmp;
		}
		
		// ȡpan
		int panIndex = 1;
		String realPan = new String();
		for(int i = 0; i < panLength; i++) {
			tmp = srcBytes[(i + panIndex) >> 1];
			if (0 != (i % 2)) {
				tmp >>= 4;
			}
			
			tmp &= 0x0f;
			realPan += tmp;
		}
		
		
		ret += decrypedData;
		ret += "\nPan:" + realPan;
		return ret;
	}

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			Log.e("aa", e1.getMessage());
		} catch (javax.crypto.NoSuchPaddingException e2) {
			Log.e("aa", e2.getMessage());
		} catch (java.lang.Exception e3) {
			Log.e("aa", e3.getMessage());
		}
		
		return null;
	}
	
	
}

