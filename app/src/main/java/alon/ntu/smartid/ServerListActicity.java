//package alon.ntu.smartid;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class ServerListActicity extends Activity implements OnItemLongClickListener {
//
//	private static final String PREFS_NAME = "prefs_name";
//	private static final String SERVER_LIST = "server_list";
//	private static final String PUBLIC_KEY_LIST = "public_key_list";
//
//	private ListView mListView;
//	private ArrayAdapter<String> mAdapter;
//	private ArrayList<String> mServerList;
//	private ArrayList<String> mKeyList;
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_serverlist);
//
//		mListView = (ListView) findViewById(R.id.serverlist_list);
//		mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1) {
//			@Override
//			public View getView(int position, View convertView, ViewGroup parent) {
//				View view = super.getView(position, convertView, parent);
//				TextView text = (TextView) view.findViewById(android.R.id.text1);
//				text.setTextColor(Color.BLACK);
//			    return view;
//			}
//		};
//
//		mServerList = new ArrayList<String>();
//		mKeyList = new ArrayList<String>();
//
//		mServerList = loadFromPrefs(SERVER_LIST);
//		mKeyList = loadFromPrefs(PUBLIC_KEY_LIST);
//
//		Log.d("!!","!! " + mServerList.size());
//
//		if (mServerList.size() == 0) {
//			addDefaultItem();
//		}
//
//		for (String str : mServerList) {
//			mAdapter.add(str);
//		}
//
//		Intent intent = getIntent();
//		if (intent != null) {
//			String serverName = intent.getStringExtra("server_name");
//			String keyString = Utils.bytesToHex(Utils.getPublicKey());
//
//			addItem(serverName, keyString);
//		}
//
//
//		mListView.setAdapter(mAdapter);
//		mListView.invalidate();
//
//		mListView.setOnItemLongClickListener(this);
//	}
//
//	@Override
//	protected void onPause() {
//	    super.onPause();
//	    saveToPrefs(mServerList, SERVER_LIST);
//	    saveToPrefs(mKeyList, PUBLIC_KEY_LIST);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	private void saveToPrefs(ArrayList<String> list, String key) {
//		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//		Editor editor = prefs.edit();
//		StringBuilder sb = new StringBuilder();
//		for (String str : list) {
//			sb.append(str);
//			sb.append(',');
//		}
//		editor.putString(key, sb.toString());
//		editor.commit();
//	}
//
//	private void addDefaultItem() {
//		addItem("http://testserver.herokuapp.com", "aaaa");
//		addItem("http://testserver-2.herokuapp.com/",Utils.bytesToHex(Utils.getPublicKey()));
//	}
//
//	private void addItem(String name, String key) {
//		mServerList.add(name);
//		mKeyList.add(name);
//		mListView.invalidate();
//	}
//
//	private void deleteItem(int position) {
//		mAdapter.remove(mServerList.get(position));
//		mServerList.remove(position);
//		mKeyList.remove(position);
//		mListView.invalidate();
//	}
//
//	private ArrayList<String> loadFromPrefs(String key) {
//		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//		String str = prefs.getString(key, "");
//		Log.d("!!","!! prefs" +  key + ":" + str);
//		String[] arr = str.split(",");
//		ArrayList<String> ret = new ArrayList<String>();
//		if (str.length() > 2) {
//			for (String s : arr) {
//				ret.add(s);
//			}
//		}
//		return ret;
//	}
//
//	@Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
//            long id) {
//		AlertDialog.Builder builder = new Builder(this);
//		builder.setTitle("Server Information");
//		builder.setMessage(mServerList.get(position));
//		builder.setPositiveButton("Select", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// send Intent to Service/Utils to prepare key.
//				String key = mKeyList.get(position);
//				Utils.prepared_key = key.toString().getBytes();
//			}
//		});
//		builder.setNegativeButton("Cancel", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				deleteItem(position);
//				mListView.invalidate();
//			}
//		});
////		builder.setCancelable(true);
//		builder.show();
//
//	    return true;
//    }
//}
