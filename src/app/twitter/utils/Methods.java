package app.twitter.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;
import android.os.Looper;
import app.twitter.R;
import app.twitter.listeners.DL;

// Apache
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {

	public static void confirm_quit(Activity actv, int keyCode) {
		
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
	        dialog.setTitle(actv.getString(R.string.generic_tv_confirm));
	        dialog.setMessage("終了しますか？");
	        
	        dialog.setPositiveButton(
	        				actv.getString(R.string.generic_bt_ok),
	        				new DL(actv, dialog, 0));
	        
	        dialog.setNegativeButton(
	        				actv.getString(R.string.generic_bt_cancel),
	        				new DL(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

	public static int
	backupDb
	(Activity actv, String dbName, String dirPathBk) {
		/*----------------------------
		* 1. Prep => File names
		* 2. Prep => Files
		* 2-2. Folder exists?
		* 3. Copy
		----------------------------*/
		//String time_label = Methods.get_TimeLabel(Methods.getMillSeconds_now());
		String timeLabel = Methods.getTimeLabel(Methods.getMillSeconds_now());
		
		String db_src = StringUtils.join(
					new String[]{
							CONS.DB.dpath_Db,
							CONS.DB.dbName_twt},
//							CONS.DB.dbName_LM},
					File.separator);
		
		String db_dst = StringUtils.join(
					new String[]{
							CONS.DB.dpath_Db_Backup,
							CONS.DB.fname_Db_Backup_trunk},
					File.separator);
		
		db_dst = db_dst + "_" + timeLabel + CONS.DB.fname_Db_Backup_ext;
		
		// Log
		Log.d("Methods.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "db_src=" + db_src + "/" + "db_dst=" + db_dst);

		
		/*----------------------------
		* 2. Prep => Files
		----------------------------*/
		File src = new File(db_src);
		File dst = new File(db_dst);
		
		/*********************************
		* DB file exists?
		*********************************/
		File f = new File(db_src);
		
		if (f.exists()) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "DB file exists=" + f.getAbsolutePath());
		} else {//if (f.exists())
		
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "File doesn't exist=" + f.getAbsolutePath());
			
			return CONS.ReturnValues.DB_DOESNT_EXIST;
		
		}//if (f.exists())
	
		/*----------------------------
		* 2-2. Folder exists?
		----------------------------*/
		File db_backup = new File(dirPathBk);
		
		if (!db_backup.exists()) {
		
			try {
				db_backup.mkdir();
			
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]",
						"Folder created: " + db_backup.getAbsolutePath());
				
			} catch (Exception e) {
				
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]",
						"Create folder => Failed");
				
				return CONS.ReturnValues.DB_CANT_CREATE_FOLDER;
				
			}
		
		} else {//if (!db_backup.exists())
		
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Folder exists: " + db_backup.getAbsolutePath());
			
		}//if (!db_backup.exists())
	
		/*----------------------------
		* 3. Copy
		----------------------------*/
		try {
			FileChannel iChannel = new FileInputStream(src).getChannel();
			FileChannel oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "File copied");
			
			return CONS.ReturnValues.DB_BACKUP_SUCCESSFUL;
		
		} catch (FileNotFoundException e) {
		
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Exception: " + e.toString());
			
			return CONS.ReturnValues.FileNotFoundException;
		
		} catch (IOException e) {
		
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Exception: " + e.toString());
			
			return CONS.ReturnValues.IOException;
		
		}//try
	
	}//backupDb

	/*********************************
	 * @return "yyyy/MM/dd HH:mm:ss"
	 *********************************/
	public static String getTimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)
	
	/*********************************
	 * @return "yyyy/MM/dd HH:mm:ss"
	 *********************************/
	public static String
	getTimeLabel(long millSec, CONS.Others.TimeLabelTypes type) {
		
		SimpleDateFormat sdf1 = null;
		
		switch(type) {
		
		case Readable:
			
			sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			break;
			
		case Serial:
			
			sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			break;
			
		default:
			
			sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			break;
			
		}//switch(type)
		
		return sdf1.format(new Date(millSec));
		
	}//getTimeLabel(long millSec, CONS.Others.TimeLabelTypes type)

	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	
}//public class Methods

