package app.twitter.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//import android.view
import android.widget.Toast;

/****************************************
 * Copy & pasted from => C:\WORKS\WORKSPACES_ANDROID\ShoppingList\src\shoppinglist\main\DBUtils.java
 ****************************************/
public class DBUtils extends SQLiteOpenHelper{

	/*****************************************************************
	 * Class fields
	 *****************************************************************/
	// Activity
	Activity activity;
	
	//
	Context context;

	private String dbName;

	/*****************************************************************
	 * Constructor
	 *****************************************************************/
	public DBUtils(Context context, String dbName) {
		super(context, dbName, null, 1);
		
		// Initialize activity
		this.activity = (Activity) context;
		
		this.context = context;
		
		this.dbName = dbName;
		
	}//public DBUtils(Context context)

	/*******************************************************
	 * Methods
	 *******************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}//public void onCreate(SQLiteDatabase db)

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	/****************************************
	 * createTable_generic()
	 * 
	 * @param columns => Use skimmed columns (same for types)
	 * @return false => 1. Table exists<br>
	 * 					2. SQLException<br>
	 * 
	 ****************************************/
	public boolean
	createTable(
			SQLiteDatabase db,
			String tableName,
			
			String[] columns,
			String[] types) {
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		
		//
//		if (!tableExists(db, tableName)) {
		if (tableExists(db, tableName)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(CONS.DB.SQLToken_ID);
//		sb.append(android.provider.BaseColumns._ID +
//				" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append(CONS.DB.SQLToken_TimeStamps);
//		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
//			db.execSQL(sql);
			db.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			
			return true;
		} catch (SQLException e) {
			// Log
			Log.d(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			return false;
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)

	public boolean tableExists(SQLiteDatabase db, String tableName) {
		// The table exists?
		Cursor cursor = db.rawQuery(
									"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
									tableName + "'", null);
		
		((Activity) context).startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
			return true;
		} else {//if (cursor.getCount() > 0)
			return false;
		}//if (cursor.getCount() > 0)
	}//public boolean tableExists(String tableName)

	public boolean dropTable(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO �����������ꂽ catch �u���b�N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	/*********************************
	 * @param columnNames Timestamps => not auto-inserted
	 *********************************/
	public boolean insertData
	(SQLiteDatabase db, String tableName, 
		String[] col_names, String[] values) {
		
////		String sql = "SELECT * FROM TABLE " + DBUtils.table_name_memo_patterns;
//		String sql = "SELECT * FROM " + DBUtils.table_name_memo_patterns;
//		
//		Cursor c = db.rawQuery(sql, null);
//		
//		
//		
//		// Log
//		Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount() + " / " +
//				"c.getColumnCount() => " + c.getColumnCount());
//		
//		c.close();
		
		
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < col_names.length; i++) {
				val.put(col_names[i], values[i]);
			}//for (int i = 0; i < col_names.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + col_names[0] + " => " + values[0] + 
//				" / " + col_names[3] + " => " + values[3] + ")");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
	}//public insertData(String tableName, String[] col_names, String[] values)

	/*********************************
	 * @param columnNames Timestamps => not auto-inserted
	 *********************************/
	public boolean insertData
	(SQLiteDatabase db, String tableName, 
			String[] columnNames, long[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
	}//public insertData(String tableName, String[] columnNames, String[] values)
	
	public boolean deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = isInDB_long(db, tableName, file_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(file_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (file_id = " + String.valueOf(file_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(file_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName + 
						" WHERE file_id = '" + String.valueOf(file_id) + "'";
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(file_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean deleteData(SQLiteDatabase db, String tableName, long file_id)

	/****************************************
	 *
	 * 
	 * <Caller> 
	 * 1. deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id)
	 * 
	 * <Desc> 
	 * 1. REF=> http://stackoverflow.com/questions/3369888/android-sqlite-insert-unique
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE file_id = '" +
						String.valueOf(file_id) + "'";
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id)


	public static boolean update_data_table_name(Activity actv,
			SQLiteDatabase wdb, String t_name, long db_id, String new_name) {
		
		String sql = "UPDATE " + t_name + " SET "
//					+ "table_name='" + new_name
					+ "table_name='" + new_name + "'"
					+ " WHERE " + android.provider.BaseColumns._ID + " = "
					+ String.valueOf(db_id);
//					+ " WHERE " + android.provider.BaseColumns._ID + " = '"
//					+ String.valueOf(db_id) + "'";
				
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql=" + sql);
		
		//						"file_id", 		"file_path", "file_name", "date_added", "date_modified"
		//static String[] cols = 
		//	{"file_id", 		"file_path", "file_name", "date_added",
		//		"date_modified", "memos", "tags"};


		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done");
			
		//	Methods.toastAndLog(actv, "Data updated", 2000);
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			return false;
		}

	}//public static boolean update_data_table_name

	/*********************************
	 * @return
	 * false => 1. Column already exists<br>
	 * 			2. SQLException
	 *********************************/
	public static boolean
	add_Column_To_Table
	(Activity actv,
			String dbName, String tableName,
			String column_name, String data_type) {
		/*********************************
		 * 1. Column already exists?
		 * 2. db setup
		 * 
		 * 3. Build sql
		 * 4. Exec sql
		 * 
		 * 5. Close db
		 *********************************/
		/*********************************
		 * 1. Column already exists?
		 *********************************/
		String[] cols = DBUtils.get_Column_List(actv, dbName, tableName);
		
		//debug
		for (String col_name : cols) {

			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "col: " + col_name);
			
		}//for (String col_name : cols)

		
		for (String col_name : cols) {
			
			if (col_name.equals(column_name)) {
				
				// debug
				Toast.makeText(actv, "Column exists: " + column_name, Toast.LENGTH_SHORT).show();
				
				// Log
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Column exists: " + column_name);
				
				return false;
				
			}
			
		}//for (String col_name : cols)
		
		// debug
		Toast.makeText(actv, "Column doesn't exist: " + column_name, Toast.LENGTH_SHORT).show();
		
		/*********************************
		 * 2. db setup
		 *********************************/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/*********************************
		 * 3. Build sql
		 *********************************/
		// REF[20121001_140817] => http://stackoverflow.com/questions/8291673/how-to-add-new-column-to-android-sqlite-database
		
		String sql = "ALTER TABLE " + tableName + 
					" ADD COLUMN " + column_name + 
					" " + data_type;
		
		/*********************************
		 * 4. Exec sql
		 *********************************/
		try {
//			db.execSQL(sql);
			wdb.execSQL(sql);
			
			// Log
			Log.d(actv.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Column added => " + column_name);

			/*********************************
			 * 5. Close db
			 *********************************/
			wdb.close();
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e(actv.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			/*********************************
			 * 5. Close db
			 *********************************/
			wdb.close();

			return false;
		}//try

		/*********************************
		 * 5. Close db
		 *********************************/


		
	}//add_Column_To_Table

	public static String[]
	get_Column_List
	(Activity actv, String dbName, String tableName) {
		/*********************************
		 * 1. Set up db
		 * 2. Cursor null?
		 * 3. Get names
		 * 
		 * 4. Close db
		 * 5. Return
		 *********************************/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		//=> source: http://stackoverflow.com/questions/4681744/android-get-list-of-tables : "Just had to do the same. This seems to work:"
		String q = "SELECT * FROM " + tableName;
		
		/*********************************
		 * 2. Cursor null?
		 *********************************/
		Cursor c = null;
		
		try {
			c = rdb.rawQuery(q, null);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount(): " + c.getCount());

		} catch (Exception e) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return null;
		}
		
		/*********************************
		 * 3. Get names
		 *********************************/
		String[] column_names = c.getColumnNames();
		
		/*********************************
		 * 4. Close db
		 *********************************/
		rdb.close();
		
		/*********************************
		 * 5. Return
		 *********************************/
		return column_names;
		
//		return null;
	}//get_Column_List

	/*********************************
	 * @return
	 * false => 1. FileNotFoundException<br>
	 * 2. IOException
	 *********************************/
	public static boolean restore_Db
	(Activity actv, String src, String dst) {
		/*********************************
		 * 1. Setup db
		 * 2. Setup: File paths
		 * 3. Setup: File objects
		 * 4. Copy file
		 * 
		 *********************************/
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]",
				"src=" + src
				+ "/"
				+ "dst=" + dst);
		
		/*********************************
		 * 4. Copy file
		 *********************************/
		try {
			FileChannel iChannel = new FileInputStream(src).getChannel();
			FileChannel oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "File copied: " + src);
			
			// debug
			Toast.makeText(actv, "DB restoration => Done", Toast.LENGTH_LONG).show();
			
			return true;
	
		} catch (FileNotFoundException e) {

			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		} catch (IOException e) {

			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		}//try
		
	}//public static boolean restore_Db

	public static int restore_Db(Activity actv) {
    	
    	// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: restore_db()");

		/*********************************
		 * Get the absolute path of the latest backup file
		 *********************************/
		// Get the most recently-created db file
//		String src_dir = "/mnt/sdcard-ext/IFM9_backup";
		String src_dir = CONS.DB.dpath_Db_Backup;
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "No files in the dir: " + src_dir);
			
			return CONS.ReturnValues.FileNotFoundException;
			
		}//if (src_dir_files.length == condition)
		
		// Latest file
		File f_src_latest = src_dir_files[0];
		
		
		for (File file : src_dir_files) {
			
			if (f_src_latest.lastModified() < file.lastModified()) {
						
				f_src_latest = file;
				
			}//if (variable == condition)
			
		}//for (File file : src_dir_files)
		
		// Show the path of the latest file
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		/*********************************
		 * Restore file
		 *********************************/
		String src = f_src_latest.getAbsolutePath();
		String dst = StringUtils.join(
						new String[]{
//							"/data/data/ifm9.main/databases",
							CONS.DB.dpath_Db,
							CONS.DB.dbName_twt},
						File.separator);

		// Log
		String log_msg = "src=" + src
						+ "||"
						+ "dst=" + dst;

		Log.d("[" + "DBUtils.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		boolean res = DBUtils.restore_Db(actv, src, dst);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		if (res == true) {
			
			return CONS.ReturnValues.DB_RESTORE_SUCCESSFUL;
			
		} else {//if (res == true)
			
			return CONS.ReturnValues.DB_RESTORE_FAILED;
			
		}//if (res == true)
		
		
	}//private void restore_db()

	/*********************************
	 * updateData_LM_Uploaded(Loc loc)
	 * 
	 * @return true => update successful<br>
	 * 	false =>
	 * 	<pre>1. Transaction unsuccessful
	 * 2. Exception</pre>
	 *********************************/
//	public boolean updateData_LM_Uploaded(Loc loc) {
//		// TODO Auto-generated method stub
//		/***************************************
//		 * Build value set
//		 ***************************************/
//		ContentValues cv = new ContentValues();
//
////		android.provider.BaseColumns._ID,	// 0
////		"created_at",						// 1
////		"modified_at",						// 2
////
////		"longitude",						// 3
////		"latitude",							// 4
////		"memo",								// 5
////		
////		"uploaded_at"						// 6
//		
//		cv.put(CONS.DB.cols_Locations_Names[0], loc.getId());
//		cv.put(CONS.DB.cols_Locations_Names[1], loc.getCreated_at());
//		cv.put(CONS.DB.cols_Locations_Names[2], loc.getModified_at());
//		
//		cv.put(CONS.DB.cols_Locations_Names[3], loc.getLongitude());
//		cv.put(CONS.DB.cols_Locations_Names[4], loc.getLatitude());
//		cv.put(CONS.DB.cols_Locations_Names[5], loc.getMemo());
//		
//		cv.put(CONS.DB.cols_Locations_Names[6],
//				Methods.getTimeLabel(Methods.getMillSeconds_now()));
//		
//		/***************************************
//		 * Setup db
//		 ***************************************/
//		SQLiteDatabase wdb = this.getWritableDatabase();
//		
//		try {
//			//
//			wdb.beginTransaction();
//			
//			// Insert data
////			long res = wdb.insert(CONS.tableName, null, cv);
//			long res = wdb.update(
//					CONS.DB.tname_Location,
//					cv,
//					android.provider.BaseColumns._ID + " = ?",
//					new String[]{String.valueOf(loc.getId())});
//			
//			// Log
//			String log_msg = "loc.getId() => " + String.valueOf(loc.getId());
//
//			Log.d("["
//					+ "DBUtils.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//			if (res < 1) {
//				
//				// Log
//				Log.d("DBUtils.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber()
//							+ ":"
//							+ Thread.currentThread().getStackTrace()[2]
//									.getMethodName() + "]",
//						"Update => Returned less than 1");
//				
//				wdb.close();
//				
//				return false;
//				
//			}	
//			
//			// Set as successful
//			wdb.setTransactionSuccessful();
//			
//			// End transaction
//			wdb.endTransaction();
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "Update => Successful");
//			
//			wdb.close();
//			
//			return true;
//			
//		} catch (Exception e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString());
//			
//			wdb.close();
//			
//			return false;
//			
//		}//try
//		
//	}//public boolean updateData_SI_all_V2(ShoppingItem si)

	/*********************************
	 * updateData_SI_all_V2(ShoppingItem si)
	 * @param actv 
	 * 
	 * @return true => update successful<br>
	 * 	false =>
	 * 	<pre>1. Transaction unsuccessful
	 * 2. Exception</pre>
	 *********************************/
//	public static boolean update_Loc_Memo(Activity actv, Loc loc) {
//		// TODO Auto-generated method stub
//		/***************************************
//		 * Build value set
//		 ***************************************/
//		ContentValues cv = _update_Loc_Memo__BuildValues(loc);
//////		ContentValues cv = new ContentValues();
////		
////		/*
////			android.provider.BaseColumns._ID,	// 0
////			"created_at",						// 1
////			"modified_at",						// 2
////
////			"longitude",						// 3
////			"latitude",							// 4
////			"memo",								// 5
////			
////			"uploaded_at"						// 6
////		*/
////		
////		cv.put(CONS.cols_SI_full[0], si.getStore());
////		cv.put(CONS.cols_SI_full[1], si.getName());
////		cv.put(CONS.cols_SI_full[2], si.getPrice());
////		cv.put(CONS.cols_SI_full[3], si.getGenre());
////		cv.put(CONS.cols_SI_full[4], si.getYomi());
////		
////		cv.put(CONS.cols_SI_full[6], si.getCreated_at());
////		cv.put(CONS.cols_SI_full[7], si.getUpdated_at());
////		cv.put(CONS.cols_SI_full[8], si.getPosted_at());
//		
//		/***************************************
//		 * Setup db
//		 ***************************************/
//		DBUtils dbm = new DBUtils(actv);
//		
//		SQLiteDatabase wdb = dbm.getWritableDatabase();
//		
//		try {
//			//
//			wdb.beginTransaction();
//			
//			long res = wdb.update(
//					CONS.DB.tname_Location,
//					cv,
//					android.provider.BaseColumns._ID + " = ?",
//					new String[]{String.valueOf(loc.getId())});
//			
//			if (res < 1) {
//				
//				// Log
//				Log.d("DBUtils.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber()
//							+ ":"
//							+ Thread.currentThread().getStackTrace()[2]
//									.getMethodName() + "]",
//						"Update => Returned less than 1");
//				
//				wdb.close();
//				
//				return false;
//				
//			}	
//			
//			// Set as successful
//			wdb.setTransactionSuccessful();
//			
//			// End transaction
//			wdb.endTransaction();
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "Update => Successful");
//			
//			wdb.close();
//			
//			return true;
//			
//		} catch (Exception e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString());
//			
//			wdb.close();
//			
//			return false;
//			
//		}//try
//		
//	}//public boolean updateData_SI_all_V2(ShoppingItem si)

//	private static ContentValues
//	_update_Loc_Memo__BuildValues(Loc loc) {
//		// TODO Auto-generated method stub
//		ContentValues cv = new ContentValues();
//		
//		/*
//			android.provider.BaseColumns._ID,	// 0
//			"created_at",						// 1
//			"modified_at",						// 2
//
//			"longitude",						// 3
//			"latitude",							// 4
//			"memo",								// 5
//			
//			"uploaded_at"						// 6
//		*/
//		
//		cv.put(CONS.DB.cols_Locations_Names[5], loc.getMemo());
//		cv.put(CONS.DB.cols_Locations_Names[2], 
//					Methods.getTimeLabel(
//						Methods.getMillSeconds_now(),
//						CONS.Others.TimeLabelTypes.Serial));
//		
//		return cv;
//		
//	}//_update_Loc_Memo__BuildValues(Loc loc)

}//public class DBUtils

