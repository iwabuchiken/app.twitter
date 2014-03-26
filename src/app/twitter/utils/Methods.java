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
import java.text.ParseException;
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


	public static boolean is_numeric(String str) {
		
		// Log
		Log.d("[" + "Methods.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "str=" + str);
		
		// REF=> http://www.coderanch.com/t/401142/java/java/check-if-String-value-numeric # Hurkpan Potgieter Greenhorn
		String regex = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
		
//		Pattern p = Pattern.compile( "([0-9]*)\\.[0]" );
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(str);
		
		return m.matches(); //TRUE
		
	}//public static boolean is_numeric(String str)

	/*********************************
	 * @param timeLabel => "yyyy/MM/dd HH:mm:ss"
	 * @return success => long class value<br>
	 * 		failed => -1
	 *********************************/
	public static long
	get_MillSeconds_FromTimeLabel
	(String timeLabel) {
		//REF http://stackoverflow.com/questions/16910344/how-to-convert-string-into-date-time-format-in-java
		SimpleDateFormat sdf =
					new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
		try {
			
			Date date = sdf.parse(timeLabel);
			
			return date.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -1;
			
		}
		
	}//get_MillSeconds_FromTimeLabel

	/*********************************
	 * @param today "yyyy/MM/dd"
	 * 		target "yyyy/MM/dd"
	 * 		dateLabel_SeparaterChar "/"
	 * @return failed => -1
	 *********************************/
	public static long
	get_DateDiff
	(String today, String target, String dateLabel_SeparaterChar) {
		// TODO Auto-generated method stub
		String[] today_a = today.split(dateLabel_SeparaterChar);
		String[] target_a = target.split(dateLabel_SeparaterChar);

		//REF http://stackoverflow.com/questions/3796841/getting-the-difference-between-date-in-days-in-java answered Sep 26 '10 at 9:00
		Calendar cal_Today = Calendar.getInstance();
		Calendar cal_Target = Calendar.getInstance();
		
		cal_Today.set(
				Integer.parseInt(today_a[0]),
				Integer.parseInt(today_a[1]),
				Integer.parseInt(today_a[2])
		);
		cal_Target.set(
				Integer.parseInt(target_a[0]),
				Integer.parseInt(target_a[1]),
				Integer.parseInt(target_a[2])
		);
		
		Date date_Today = cal_Today.getTime();
		Date date_Target = cal_Target.getTime();
		
		long time_Today = date_Today.getTime();
		long time_Target = date_Target.getTime();
		long diffTime = time_Today - time_Target;
//		long diffTime = time_Target - time_Today;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		
		// Log
		String log_msg = "today=" + today
						+ "/"
						+ "target=" + target
						+ "startTime=" + String.valueOf(time_Today)
						+ "/"
						+ "endTime=" +  String.valueOf(time_Target)
						+ "/"
						+ "diffTime=" + String.valueOf(diffTime);

		Log.d("[" + "Methods.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		return diffDays;
		
	}//get_DateDiff(long today, long twt_Date)
	
	/*********************************
	 * @return failed => -1
	 *********************************/
	public static long
	get_DateDiff_Deprecated(long today, long twt_Date) {
		// TODO Auto-generated method stub
		String d1 = "2014/03/25 21:57:41";
		
		String d2 = "2014/03/24 21:57:42";
		
		SimpleDateFormat sdf =
				new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		try {
			
			Date date1 = sdf.parse(d1);
			Date date2 = sdf.parse(d2);
			
			long L1 = date1.getTime();
			long L2 = date2.getTime();
			
			long diff_Time = L1 - L2;
			
			long diff_Days = diff_Time / (1000 * 60 * 60 * 24);
			
			// Log
			String log_msg = "d1=" + d1
					+ "/"
					+ "d2=" + d2
					+ "/"
					+ "diff_Time=" + String.valueOf(diff_Time)
					+ "/"
					+ "diff_Days=" + String.valueOf(diff_Days)
					;
			
			Log.d("["
					+ "Methods.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
							+ Thread.currentThread().getStackTrace()[2].getMethodName()
							+ "]", log_msg);
			
			return diff_Days;
			
		} catch (ParseException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -1;
			
		}		
		
	}//get_DateDiff(long today, long twt_Date)
	
}//public class Methods

