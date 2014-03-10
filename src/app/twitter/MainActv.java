package app.twitter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.twitter.utils.AlertDialogManager;
import app.twitter.utils.CONS;
import app.twitter.utils.ConnectionDetector;

public class MainActv extends Activity {

	static String TWITTER_CONSUMER_KEY =
					"GPH7OdrScUbYQ1RpyLkzYg";
//	"LsCQaPOwd8k7WkyRFRZF4Q";
	
    static String TWITTER_CONSUMER_SECRET =
    				"bEWx5aLKLfNQQvqHCugZC7phMDqhKPT12Uwjajbr7o";
//    "KJbJu5IQrlwxW7Cwnax3mMzAc4j3n6Wd2dG125srgk";
 
//    // Preference Constants
//    static String PREFERENCE_NAME = "twitter_oauth";
//    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
//    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
//    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
//    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
// 
//    // Twitter oauth urls
//    static final String URL_TWITTER_AUTH = "auth_url";
//    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
//    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
 
//    // Login button
//    Button btnLoginTwitter;
//    // Update status button
//    Button btnUpdateStatus;
//    // Logout button
//    Button btnLogoutTwitter;
//    // EditText for update
//    EditText txtUpdate;
//    // lbl update
//    TextView lblUpdate;
//    TextView lblUserName;
 
    // Progress dialog
    ProgressDialog pDialog;
    
 // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
//    // Shared Preferences
//    private static SharedPreferences mSharedPreferences;
     
    // Internet Connection detector
    private ConnectionDetector cd;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actv_main);
		
		// Log
		String log_msg = "Starting => onCreate()";

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         
        cd = new ConnectionDetector(getApplicationContext());
 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActv.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
         
        // Check if twitter keys are set
        if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
            // Internet Connection is not present
            alert.showAlertDialog(
            		MainActv.this,
            		"Twitter oAuth tokens",
            		"Please set your twitter oauth tokens first!",
            		false);
            
            // stop executing code by return
            return;
            
        }
 
        // All UI elements
        _Setup_UIs();
        
        // Shared Preferences
        CONS.PREFS.mSharedPreferences = getApplicationContext().getSharedPreferences(
//        		mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);
        
        // Listeners
        _Setup_SetListeners();
        
        /** This if conditions is tested once is
         * redirected from twitter page. Parse the uri to get oAuth
         * Verifier
         * */
        if (!isTwitterLoggedInAlready()) {
        	
        	// Log
			log_msg = "Twitter => Not yet logged in";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
        	
            Uri uri = getIntent().getData();
            if (uri != null
            		&& uri.toString().startsWith(CONS.URLS.TWITTER_CALLBACK_URL)) {
            	
            	// Log
				log_msg = "uri => not null ("
						+ "uri="
						+ uri.toString()
						+ ")";

				Log.d("["
						+ "MainActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
            	
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(CONS.URLS.URL_TWITTER_OAUTH_VERIFIER);
     
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
     
                    // Shared Preferences
                    Editor e = CONS.PREFS.mSharedPreferences.edit();
     
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(CONS.PREFS.PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(CONS.PREFS.PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(CONS.PREFS.PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
     
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
     
                    // Hide login button
                    CONS.UIS.btnLoginTwitter.setVisibility(View.GONE);
     
                    // Show Update Twitter
                    CONS.UIS.lblUpdate.setVisibility(View.VISIBLE);
                    CONS.UIS.txtUpdate.setVisibility(View.VISIBLE);
                    CONS.UIS.btnUpdateStatus.setVisibility(View.VISIBLE);
                    CONS.UIS.btnLogoutTwitter.setVisibility(View.VISIBLE);
                     
                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();
                     
                    // Displaying in xml ui
                    CONS.UIS.lblUserName.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
                    
                } catch (Exception e) {
                	
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                    
                }//try
                
            } else {//if (uri != null
            	
            	// Log
				log_msg = "uri => null";

				Log.d("["
						+ "MainActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
            	
            }//if (uri != null
            
        } else {//if (!isTwitterLoggedInAlready())
        	
        	// Log
			log_msg = "Already logged in";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
        	
        }//if (!isTwitterLoggedInAlready())
        
	}//protected void onCreate(Bundle savedInstanceState)

	private void _Setup_UIs() {
		
		// TODO Auto-generated method stub
		CONS.UIS.btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
        CONS.UIS.btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        CONS.UIS.btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        CONS.UIS.txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
        CONS.UIS.lblUpdate = (TextView) findViewById(R.id.lblUpdate);
        CONS.UIS.lblUserName = (TextView) findViewById(R.id.lblUserName);
        
	}

	private void _Setup_SetListeners() {
		// TODO Auto-generated method stub
        /**
         * Twitter login button click event will call loginToTwitter() function
         * */
        CONS.UIS.btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
     
            @Override
            public void onClick(View arg0) {
                // Call login twitter function
                loginToTwitter();
            }
        });
        
        /**
         * Button click event for logout from twitter
         * */
        CONS.UIS.btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {
         
            @Override
            public void onClick(View arg0) {
                // Call logout twitter function
                logoutFromTwitter();
            }
        });

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_actv, menu);
		return true;
		
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		
		case R.id.menu_main_logout://--------------------
			
			this.logoutFromTwitter();
			
			break;
			
			
		default://-------------------------------------
			break;
	
		}
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
        	
        	// Log
			String log_msg = "Starting => login process";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
        	
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
            
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            // Log
			log_msg = "twitter inctance => Created";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
            
            // Log
			log_msg = "Setup => Done."
					+ " Starting to get a request token...";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
            
            try {
                requestToken = twitter
                        .getOAuthRequestToken(CONS.URLS.TWITTER_CALLBACK_URL);
                
                // Log
				log_msg = "requestToken => "
							+ requestToken.getToken();

				Log.d("["
						+ "MainActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
                
				// Log
				log_msg = "Starting activity... ("
							+ "AuthenticationURL => "
							+ requestToken.getAuthenticationURL()
							+ ")";

				Log.d("["
						+ "MainActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
                
            } catch (TwitterException e) {
            	
                e.printStackTrace();
                
            }
            
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }//if (!isTwitterLoggedInAlready())
        
    }//private void loginToTwitter()
 
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return CONS.PREFS.mSharedPreferences.getBoolean(CONS.PREFS.PREF_KEY_TWITTER_LOGIN, false);
    }

    /**
     * Function to logout from twitter
     * It will just clear the application shared preferences
     * */
    private void logoutFromTwitter() {
        // Clear the shared preferences
        Editor e = CONS.PREFS.mSharedPreferences.edit();
        e.remove(CONS.PREFS.PREF_KEY_OAUTH_TOKEN);
        e.remove(CONS.PREFS.PREF_KEY_OAUTH_SECRET);
        e.remove(CONS.PREFS.PREF_KEY_TWITTER_LOGIN);
        e.commit();
     
        // After this take the appropriate action
        // I am showing the hiding/showing buttons again
        // You might not needed this code
        CONS.UIS.btnLogoutTwitter.setVisibility(View.GONE);
        CONS.UIS.btnUpdateStatus.setVisibility(View.GONE);
        CONS.UIS.txtUpdate.setVisibility(View.GONE);
        CONS.UIS.lblUpdate.setVisibility(View.GONE);
        CONS.UIS.lblUserName.setText("");
        CONS.UIS.lblUserName.setVisibility(View.GONE);
     
        CONS.UIS.btnLoginTwitter.setVisibility(View.VISIBLE);
        
        // Log
		String log_msg = "Logout => Done";

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		// debug
		String toa_msg = "Logout => Done";
		Toast.makeText(this, toa_msg, Toast.LENGTH_SHORT).show();
		
    }//private void logoutFromTwitter()
    
}//public class MainActv extends Activity
