package alon.ntu.smartid;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

public class Utils {

//	public static String getPublicKetString(Context context) {
//		SharedPreferences pref = context.getSharedPreferences(MainActivity.PREF_NAME, 0);
//		String public_key = pref.getString(MainActivity.KEY_PUBLIC_KEY, null);
//		
//		return public_key;
//	}
//	
	public static RSACipher cipher;
	public static byte[] prepared_key;

	public static byte[] getPublicKey() {
		return cipher.getPublicKey();
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String bytesToChar(byte[] bytes) {
		return bytesToChar(bytes, 0, bytes.length);
	}
	
	public static String bytesToChar(byte[] bytes, int start, int end) {
		String ret = "";
		for (int i = start ; i < end ; ++i) {
			ret += (char)(bytes[i] & 0xff);
		}
		return ret;
	}
	
	public static String retrieveServerInfo(byte[] nonce) {
		return bytesToChar(nonce, 0, 24);
	}
	
	public static String getUUID(Context context) {
		String uuid = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		Log.d("!!","!! get uuid: " + Utils.bytesToHex(uuid.getBytes()));
		return uuid;
	}
}
