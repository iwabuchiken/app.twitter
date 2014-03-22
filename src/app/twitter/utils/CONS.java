package app.twitter.utils;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class CONS {

	public static class TwitterData {
		
		public static String TWITTER_CONSUMER_KEY =
				"GPH7OdrScUbYQ1RpyLkzYg";
		//"LsCQaPOwd8k7WkyRFRZF4Q";
		
		public static String TWITTER_CONSUMER_SECRET =
						"bEWx5aLKLfNQQvqHCugZC7phMDqhKPT12Uwjajbr7o";
		//"KJbJu5IQrlwxW7Cwnax3mMzAc4j3n6Wd2dG125srgk";

		 // Twitter
	    public static Twitter twitter;
	    public static RequestToken requestToken;
	    
	    public static int numOfTweets	= 40;
		
	    public static List<Status> statuses	= null;
	    
	}
	
	public static class General {
		
		public static Vibrator vib;
		
		public static int VIB_LENGTH = 50;
	}
	
	public static class UIS_Twt {
		
		public static Button btn_Back;
		
		public static Button btn_Twt;
		
		public static Button btn_Pattern;
		
		public static EditText et_Twt;
		
		public static GridView gv_Pattern;
		
	}
	
	public static class UIS {
		
	    // Login button
	    public static Button btnLoginTwitter;
	    // Update status button
	    public static Button btnUpdateStatus;
	    // Logout button
	    public static Button btnLogoutTwitter;
	    // Timeline button
	    public static Button btnTimeLine;
	    // Tweet button
	    public static Button btnTweet;
	    
	    // EditText for update
	    public static EditText txtUpdate;
	    // lbl update
	    public static TextView lblUpdate;
	    public static TextView lblUserName;

		
	}
	
	public static class URLS {
		
		public static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
		 
	    // Twitter oauth urls
	    public static final String URL_TWITTER_AUTH = "auth_url";
	    public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	    public static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	}
	
	public static class PREFS {
		
		// Shared Preferences
	    public static SharedPreferences mSharedPreferences;
	    
	    // Preference Constants
	    public static String PREFERENCE_NAME = "twitter_oauth";
	    public static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	    public static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	    public static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	    public static final String pName_TWT	= "pref_twt";
		
		public static final String pKey_CurrentItemPosition
									= "current_item_position";
		
		public static final int pVal_CurrentItemPosition_Initial
									= -1;
		
		public static int pVal_CurrentItemPosition;
		
		
		public static final String pKey_ShowMemoDialog
									= "show_memo_dialog";
		
	}//public static class PREFS

	public static class DB {
		/*********************************
		 * DB names
		 *********************************/
		public static final String dbName_twt = "db_TWT.db";
		
		/*********************************
		 * Table: patterns
		 *********************************/
		public static final String tname_Patterns	= "patterns";
		
		public static final String[] cols_Patterns_Names	= {
			
				android.provider.BaseColumns._ID,	// 0
				"created_at",						// 1
				"modified_at",						// 2

				"word",						// 3
				"uploaded_at"						// 6
				
		};
		
		public static final String[] cols_Patterns_Types	= {
			
				"INTEGER",			// 0
				"TEXT",			// 1
				"TEXT",			// 2
				
				"TEXT",		// 3
				"TEXT",		// 4
				
		};
		
		public static final String[] cols_Patterns_Names_skimmed	= {
			
			"word",						// 0
			"uploaded_at"						// 3
			
		};
		
		public static final String[] cols_Patterns_Types_skimmed	= {
			
				"TEXT",				// 0
				"TEXT",				// 1
				
		};
		
		/*********************************
		 * Paths
		 *********************************/
		public static String dpath_ExternalStorage = "/mnt/sdcard-ext";

//		public static String dirPath_db = "/data/data/shoppinglist.main/databases";
		public static String dpath_Db = "/data/data/app.twitter/databases";
		
		public static String fname_Db_Backup_trunk = "TWT_backup";

		public static String fname_Db_Backup_ext = ".bk";

		public static String dpath_Db_Backup = 
							dpath_ExternalStorage + "/TWT_backup";

		/*********************************
		 * SQL
		 *********************************/
		public static String SQLToken_ID = 
							android.provider.BaseColumns._ID
							+ " INTEGER PRIMARY KEY AUTOINCREMENT, ";
		
		public static String SQLToken_TimeStamps = 
							"created_at TEXT, modified_at TEXT, ";
		
	}//public static class DB

	public static class ReturnValues {
		/*********************************
		 * DB: Successes: 10 ~
		 *********************************/
		public static final int DB_BACKUP_SUCCESSFUL	= 10;
		
		public static final int DB_RESTORE_SUCCESSFUL	= 11;
		
		/*********************************
		 * Tweet
		 *********************************/
		public static final int SendTweet_Success		= 20;
		
		/*********************************
		 * Operation failed: -10 ~
		 *********************************/
		public static final int QueryFailed		= -10;
		
		public static final int BuildJOBodyFailed	= -11;
		
		public static final int BuildEntityFailed	= -12;
		
		public static final int BuildHttpPostFailed	= -13;
		
		public static final int HttpPostFailed		= -14;
		
		public static final int PostedButNotUpdated	= -15;
		
		public static final int ServerError			= -16;
		
		public static final int ClientError			= -17;
		
		public static final int ParamVariableNull	= -18;
		
		public static final int BuildLocsFailed		= -19;

		public static final int NotUpdated_NotPosted	= -100;
		
		/*********************************
		 * DB-related: -20 ~
		 *********************************/
		public static final int DB_DOESNT_EXIST			= -20;
		
		public static final int DB_CANT_CREATE_FOLDER	= -21;
		
		public static final int DB_RESTORE_FAILED		= -22;
		
		/*********************************
		 * File-related: -30 ~
		 *********************************/
		public static final int FileNotFoundException	= -30;
		
		public static final int IOException			= -31;

		/*********************************
		 * Tweet
		 *********************************/
		public static final int SendTweet_Failed		= -40;
		
		/*********************************
		 * Others: > 0, <= -90
		 *********************************/
		public static final int OK				= 1;
		
		public static final int NOP				= -90;
		
		public static final int FAILED			= -91;
		
		public static final int MAGINITUDE_ONE	= 1000;
		
	}//public static class ReturnValues

	public static class Others {
		
		public static Vibrator vib;
		
		public static final int VIB_LENGTH = 50;
		
		public static final int VIB_LENGTH_LONG = 100;
		
		public static enum TimeLabelTypes {
			
			Readable,
			Serial,
			
		}

		public static enum SortTypes {
			
			LocList_Time_Desc,
			
		}
		
		public static String SpaceChar		= " ";
		
		public static final int Default_LocStringLength	= 12;
		
	}//public static class Others

	public static class Sqls {
		
		public static String _CreateTable_Patterns_20140321_113430 =
				"CREATE TABLE"
				+ " "
				+ CONS.DB.tname_Patterns
				+ " "
				+ "("
				+ CONS.DB.SQLToken_ID
				+ " "
				+ CONS.DB.SQLToken_TimeStamps
				
				+ CONS.DB.cols_Patterns_Names_skimmed[0]
				+ " "
				+ CONS.DB.cols_Patterns_Types_skimmed[0]
				
				+ ", "
				+ CONS.DB.cols_Patterns_Names_skimmed[1]
				+ " "
				+ CONS.DB.cols_Patterns_Types_skimmed[1]
				+ ")";
				
	}//public static class Sqls

	public static class TaskData {
		
		public static enum TaskItems {
			
			SendTweet,
			
		}
		
	}

}
