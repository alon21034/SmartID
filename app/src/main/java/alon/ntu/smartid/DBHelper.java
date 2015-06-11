//package alon.ntu.smartid;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DBHelper extends SQLiteOpenHelper {
//
//	private final static int DB_VERSION = 1;
//	private final static String DB_NAME = "smart_id_database";
//	private final static String TABLE_NAME = "user_table";
//
//	private final static String SERVER_NAME = "server_name";
//	private final static String PRIVATE_KEY = "private_key";
//
//	public DBHelper(Context context) {
//		super(context, DB_NAME, null, DB_VERSION);
//	}
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		final String SQL = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "( " +
//				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//				SERVER_NAME + " TEXT, " +
//				PRIVATE_KEY + " TEXT," +
//				");";
//		db.execSQL(SQL);
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		final String SQL = "DROP TABLE " + TABLE_NAME;
//		db.execSQL(SQL);
//	}
//
//	public static void addData(Context context, String server_info, String private_key) {
//		DBHelper helper = new DBHelper(context);
//		SQLiteDatabase db = helper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(SERVER_NAME, server_info);
//		values.put(PRIVATE_KEY, private_key);
//		db.insert(TABLE_NAME, null, values);
//		db.close();
//	}
//
//	public static String[] getServerInfos(Context context) {
//		return getData(context, SERVER_NAME);
//	}
//
//	public static String[] getPrivateKeys(Context context) {
//		return getData(context, PRIVATE_KEY);
//	}
//
//	public static String[] getData(Context context, String columnName) {
//		DBHelper helper = new DBHelper(context);
//		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor c = db.query(TABLE_NAME, new String[]{columnName}, null, null, null, null, null);
//		String[] ret = new String[c.getCount()];
//
//		int row_num = c.getCount();
//		if (row_num != 0) {
//			c.moveToFirst();
//			for (int i = 0 ; i < row_num ; ++i) {
//				ret[i] = c.getString(0);
//				c.moveToFirst();
//			}
//		}
//		return ret;
//	}
//
//}
