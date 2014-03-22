package app.twitter.utils;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class M_sql {

	public static void
	createTable_Patterns () {
		
		String sql = CONS.Sqls._CreateTable_Patterns_20140321_113430;
		
		
		
	}
	
	public static boolean
	exec_Sql(Activity actv, String dbName, String tName, String sql) {
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		//
//		if (!tableExists(db, tableName)) {
		if (dbu.tableExists(wdb, tName)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
//			db.execSQL(sql);
			wdb.execSQL(sql);
			
			// Log
			Log.d(actv.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql done => " + sql);
			
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.d(actv.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			return false;
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)

}
