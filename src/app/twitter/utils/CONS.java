package app.twitter.utils;

import android.content.SharedPreferences;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CONS {

	public static class General {
		
		public static Vibrator vib;
		
	}
	
	public static class UIS {
		
	    // Login button
	    public static Button btnLoginTwitter;
	    // Update status button
	    public static Button btnUpdateStatus;
	    // Logout button
	    public static Button btnLogoutTwitter;
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

		
	}//public static class PREFS
	
}
