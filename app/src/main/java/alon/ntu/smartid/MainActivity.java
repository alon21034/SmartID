package alon.ntu.smartid;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public static final String PREF_NAME = "prefs";
	
	public static final String KEY_PUBLIC_KEY = "public_key";
	public static final String KEY_PRIVATE_KEY = "private_key";
	public static final String KEY_MODULUS = "modulus";
	
	private Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		registerReceiver(receiver, new IntentFilter(MyHostApduService.MY_RECEIVER));

		mButton = (Button) findViewById(R.id.button_main);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(getApplicationContext(), ServerListActicity.class);
				//startActivity(intent);
			}
		});
		mButton.setVisibility(View.GONE);
		
		try {
			Utils.cipher = new RSACipher();
			Utils.prepared_key = Utils.cipher.getPublicKey();
			Log.d("!!","!! " + Utils.getPublicKey().length);
			Log.d("!!","!! " + Utils.bytesToHex(Utils.getPublicKey()));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		mButton = (Button) findViewById(R.id.button_main);

	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MyHostApduService.MY_RECEIVER.equals(intent.getAction())) {
				Log.d("!!","!! got intent in activity");
//				Intent activityIntnet = new Intent();
//				activityIntnet.putExtra("server_name", intent.getByteArrayExtra(MyHostApduService.KEY_COMMAND));
//				activityIntnet.setClass(getApplicationContext(), ServerListActicity.class);
				//startActivity(activityIntnet);
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
