package alon.ntu.smartid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class MyHostApduService extends HostApduService {

	public static final String MY_RECEIVER = "alon.ntu.smartid";
	public static final String KEY_COMMAND = "command";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("!!","!! my host apdu service create");
		registerReceiver(receiver, new IntentFilter("alon.ntu.smartid.service"));
	}
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("!!","!! get message from activity");
			sendResponseApdu(getNextMessage());
		}
	};
	
	@Override
	public void onDeactivated(int reason) {
		Log.d("!!","!! on deactivated" + reason);
		unregisterReceiver(receiver);
	}

	private byte[] getWelcomeMessage() {
		return new byte[]{(byte) 0x90, (byte)0x00, (byte)0xab, (byte)0xcd};
	}

	private byte[] getNextMessage() {
		return "abcdefg".getBytes();
	}

	@Override
	public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
		Log.d("!!", "!! Received: " + Utils.bytesToHex(apdu));
		
		if (selectAidApdu(apdu)) {
			Log.d("!!", "!! Application selected");
			return getWelcomeMessage();
		} else {
			Log.d("!!", "!! Received: " + Utils.bytesToHex(apdu));
			if (apdu[0] == (byte)0xbb) {
                Log.d("!!", "!! prepare to return UUID" + Utils.getUUID(getApplicationContext()).getBytes());
				sendResponseApdu(Utils.getUUID(getApplicationContext()).getBytes());
				return Utils.getUUID(getApplicationContext()).getBytes();
			} else if (apdu[0] == (byte)0xaa) {
				Intent intent = new Intent(MY_RECEIVER);
				intent.putExtra(KEY_COMMAND, apdu);
				sendBroadcast(intent);
				sendResponseApdu(Utils.getPublicKey());
				return Utils.getPublicKey();
			} else if (apdu[0] == (byte)0xcc) {
				// signature
				try {
					Utils.cipher.encrypt(apdu);
					sendResponseApdu(Utils.getPublicKey());
					return Utils.cipher.encrypt(apdu);
				} catch (Exception e) {

				}
				sendResponseApdu(Utils.getPublicKey());
				return null;
			} else if (apdu[0] == (byte)0xdd) {
				
			} else {
				sendResponseApdu("zzzzz".getBytes());
			}
			return null;
		}
	}

	private boolean selectAidApdu(byte[] apdu) {
		return apdu.length >= 2 && apdu[0] == (byte)0 && apdu[1] == (byte)0xa4;
	}
	
}
