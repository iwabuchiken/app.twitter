package app.twitter;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.twitter.listeners.button.BOCL;
import app.twitter.listeners.button.BOTL;
import app.twitter.utils.AlertDialogManager;
import app.twitter.utils.CONS;
import app.twitter.utils.ConnectionDetector;
import app.twitter.utils.Methods;
import app.twitter.utils.Methods_Dlg;
import app.twitter.utils.Methods_twt;
import app.twitter.utils.Tags;

public class MainActv extends Activity {

    // Progress dialog
    ProgressDialog pDialog;
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		// Log
		String log_msg = "onStart()";

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		_Setup_RefreshScreen();
		
//		do_test();
		
	}

    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// Log
		String log_msg = "onResume()";

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		_Setup_ValidateLogin();
		
		_Setup_RefreshScreen();

	}


	private void _Setup_ValidateLogin() {
		// TODO Auto-generated method stub
		
		Methods_twt.validate_Login(this);
		
	}


	// Internet Connection detector
    private ConnectionDetector cd;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actv_main);
		
		this.setTitle(this.getClass().getName());
		
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
        if(CONS.TwitterData.TWITTER_CONSUMER_KEY.trim().length() == 0
        		|| CONS.TwitterData.TWITTER_CONSUMER_SECRET.trim().length() == 0){
        	
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
        
//        // Already logged in?
//        _Setup_Validate_Login_2();
        
        
        // Listeners
        _Setup_SetListeners();
        
        // OAuth
        _Setup_OAuth();
        
        // General settings
        _Setup_General();
        
//        _Setup_RefreshScreen();
        
//        do_test();
        
	}//protected void onCreate(Bundle savedInstanceState)

	private void do_test() {
		// TODO Auto-generated method stub
		
		_do_test__GetPrefValues();
	}


	private void _do_test__GetPrefValues() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = this
							.getSharedPreferences(
								this.getString(R.string.prefs_shared_prefs_name),
								Context.MODE_PRIVATE);
		
		boolean saveText = prefs.getBoolean(
					this.getString(R.string.prefs_save_text_key), false);
		
		String timeLineSize = prefs.getString(
//				int timeLineSize = prefs.getInt(
				this.getString(R.string.prefs_timeline_size_key), "-1");
		
		String log_msg = "saveText => " + saveText
				+ "/"
				+ "timeLineSize => ";
		
		if (Methods.is_numeric(timeLineSize)) {
			
			log_msg += timeLineSize;
			
		} else {//if (Methods.is_numeric(timeLineSize))
			
			log_msg += "Not a number: " + timeLineSize;
			
		}//if (Methods.is_numeric(timeLineSize))
		
		
		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
	}


	private void _Setup_Validate_Login_2() {
		// TODO Auto-generated method stub
		Methods_twt.validate_Login_2(this);
	}


	private void _Setup_RefreshScreen() {
		// TODO Auto-generated method stub
		if (Methods_twt.isTwitterLoggedInAlready()) {
			
			// Log
			String log_msg = "Already logged in";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			_Setup_UIs();
			
			// Show Update Twitter
//			CONS.UIS.lblUpdate.setVisibility(View.VISIBLE);
//			CONS.UIS.txtUpdate.setVisibility(View.VISIBLE);
			
//			CONS.UIS.btnUpdateStatus.setVisibility(View.VISIBLE);
			CONS.UIS.btnLogoutTwitter.setVisibility(View.VISIBLE);
			CONS.UIS.btnTimeLine.setVisibility(View.VISIBLE);
			CONS.UIS.btnTweet.setVisibility(View.VISIBLE);
			
		} else {//if (isTwitterLoggedInAlready())
			
			// Log
			String log_msg = "Not yet logged in";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.UIS.btnTimeLine.setVisibility(View.GONE);
			CONS.UIS.btnTweet.setVisibility(View.GONE);
			
		}//if (isTwitterLoggedInAlready())
		
        
	}//private void _Setup_RefreshScreen()

	private void _Setup_General() {
		// TODO Auto-generated method stub
		CONS.General.vib =
				(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	private void _Setup_OAuth() {
		// TODO Auto-generated method stub
        /** This if conditions is tested once is
         * redirected from twitter page. Parse the uri to get oAuth
         * Verifier
         * */
        if (!Methods_twt.isTwitterLoggedInAlready()) {
        	
        	// Log
        	String log_msg = "Twitter => Not yet logged in";

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
                    AccessToken accessToken =
                    		CONS.TwitterData.twitter.getOAuthAccessToken(
                    				CONS.TwitterData.requestToken, verifier);
     
                    
                    // Log
					log_msg = "Token=" + accessToken.getToken()
								+ "/"
								+ "TokenSecret=" + accessToken.getTokenSecret();

					Log.d("["
							+ "MainActv.java : "
							+ +Thread.currentThread().getStackTrace()[2]
									.getLineNumber()
							+ " : "
							+ Thread.currentThread().getStackTrace()[2]
									.getMethodName() + "]", log_msg);
                    
                    // Shared Preferences
                    Editor e = CONS.PREFS.mSharedPreferences.edit();
     
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(CONS.PREFS.PREF_KEY_OAUTH_TOKEN,
                    				accessToken.getToken());
                    e.putString(CONS.PREFS.PREF_KEY_OAUTH_SECRET,
                            		accessToken.getTokenSecret());
                    
                    // Store login status - true
                    e.putBoolean(CONS.PREFS.PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
     
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
     
                    // Hide login button
                    CONS.UIS.btnLoginTwitter.setVisibility(View.GONE);
     
                    // Show Update Twitter
//                    CONS.UIS.lblUpdate.setVisibility(View.VISIBLE);
//                    CONS.UIS.txtUpdate.setVisibility(View.VISIBLE);
//                    CONS.UIS.btnUpdateStatus.setVisibility(View.VISIBLE);
                    CONS.UIS.btnLogoutTwitter.setVisibility(View.VISIBLE);
                    CONS.UIS.btnTimeLine.setVisibility(View.VISIBLE);
                     
                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = CONS.TwitterData.twitter.showUser(userID);
//                    User user = CONS.Twitter.twitter.showUser(userID);
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
        	String log_msg = "Already logged in";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
        	
        }//if (!isTwitterLoggedInAlready())

	}//private void _Setup_OAuth()

	private void _Setup_UIs() {
		
		// Log
		String log_msg = "_Setup_UIs()";

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		// TODO Auto-generated method stub
		CONS.UIS.btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
//        CONS.UIS.btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        CONS.UIS.btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        CONS.UIS.btnTimeLine = (Button) findViewById(R.id.btnTimeLine);
        CONS.UIS.btnTweet = (Button) findViewById(R.id.btnTweet);
        
//        CONS.UIS.txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
//        CONS.UIS.lblUpdate = (TextView) findViewById(R.id.lblUpdate);
//        CONS.UIS.lblUserName = (TextView) findViewById(R.id.lblUserName);
        
	}

	private void _Setup_SetListeners() {
		// TODO Auto-generated method stub
        /**
         * Twitter login button click event will call loginToTwitter() function
         * */
		CONS.UIS.btnLoginTwitter.setTag(Tags.ButtonTags.LOGIN);
        CONS.UIS.btnLoginTwitter.setOnClickListener(new BOCL(this));
        CONS.UIS.btnLoginTwitter.setOnTouchListener(new BOTL(this));
        
        /**
         * Button click event for logout from twitter
         * */
        CONS.UIS.btnLogoutTwitter.setTag(Tags.ButtonTags.LOGOUT);
        CONS.UIS.btnLogoutTwitter.setOnClickListener(new BOCL(this));
        CONS.UIS.btnLogoutTwitter.setOnTouchListener(new BOTL(this));
         
        /**
         * Button click event: Timeline
         * */
        CONS.UIS.btnTimeLine.setTag(Tags.ButtonTags.TIMELINE);
        CONS.UIS.btnTimeLine.setOnClickListener(new BOCL(this));
        CONS.UIS.btnTimeLine.setOnTouchListener(new BOTL(this));
        	
        /**
         * Button click event: Tweet
         * */
        CONS.UIS.btnTweet.setTag(Tags.ButtonTags.TWEET);
        CONS.UIS.btnTweet.setOnClickListener(new BOCL(this));
        CONS.UIS.btnTweet.setOnTouchListener(new BOTL(this));
        
	}//private void _Setup_SetListeners()


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
			
			case_Menu_Logout();
//			this.logoutFromTwitter();
			
			break;
			
		case R.id.menu_main_admin://--------------------
			
			case_Menu_Admin();
//			this.logoutFromTwitter();
			
			break;
			
		case R.id.menu_main_settings://--------------------
			
			case_Menu_Settings();
//			this.logoutFromTwitter();
			
			break;
			
		case R.id.menu_main_horizontal_listview://--------------------
			
			case_Menu_HorizontalListview();
//			this.logoutFromTwitter();
			
			break;
			
		default://-------------------------------------
			break;
	
		}
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	private void case_Menu_HorizontalListview() {
		// TODO Auto-generated method stub
		
		String className = "app.twitter.specials.HorizontalListViewDemo";
		
		Methods_twt.start_Activity(this, className);
		
	}


	private void case_Menu_Settings() {
		
		// TODO Auto-generated method stub
		Methods_twt.start_Settings(this);
		
	}


	private void case_Menu_Admin() {
		// TODO Auto-generated method stub
		
		Methods_Dlg.dlg_Admin(this);
		
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		Methods.confirm_quit(this, keyCode);
		
		return super.onKeyDown(keyCode, event);
	}

	private void case_RefreshScreen() {
		// TODO Auto-generated method stub
		
	}

	private void case_Menu_Logout() {
		// TODO Auto-generated method stub
		if (!Methods_twt.isTwitterLoggedInAlready()) {
			
			// Log
			String log_msg = "Already logged out";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			String toa_msg = "Already logged out";
			Toast.makeText(this, toa_msg, Toast.LENGTH_SHORT).show();
			
		} else {
			
			Methods_twt.logoutFromTwitter(this);
//			this.logoutFromTwitter();
			
		}
	}

	private void case_Menu_Tweet() {
		// TODO Auto-generated method stub
//		Twitter twitter = TwitterFactory.getSingleton();
		
		String msg = "ABCDE";

		if (Methods_twt.isTwitterLoggedInAlready()) {
			
		    try {
		    	
				Status status = CONS.TwitterData.twitter.updateStatus(msg);
				
				// Log
				String log_msg = "update => done: " + status.getText();
	
				Log.d("["
						+ "MainActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + " : "
						+ Thread.currentThread().getStackTrace()[2].getMethodName()
						+ "]", log_msg);
				
				// debug
				String toa_msg = "update => done: " + status.getText();
				Toast.makeText(this, toa_msg, Toast.LENGTH_SHORT).show();
				
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
		} else {//if (isTwitterLoggedInAlready)
			
			// Log
			String log_msg = "Login => Not yet";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber()
					+ " : "
					+ Thread.currentThread().getStackTrace()[2]
							.getMethodName() + "]", log_msg);
			
			// debug
			String toa_msg = "Login => Not yet";
			Toast.makeText(this, toa_msg, Toast.LENGTH_SHORT).show();
			
		}//if (isTwitterLoggedInAlready)
	    
	}//private void case_Menu_Tweet()
	
	private void case_Menu_Query() {
		// TODO Auto-generated method stub
		//test
        if (Methods_twt.isTwitterLoggedInAlready()) {
			
        	_Debug_D_2_v_2_0__Query();
			
		} else {//if (isTwitterLoggedInAlready)
			
			// Log
			String log_msg = "Login => Not yet";

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber()
					+ " : "
					+ Thread.currentThread().getStackTrace()[2]
							.getMethodName() + "]", log_msg);
			
		}//if (isTwitterLoggedInAlready)

	}//private void case_Menu_Query()
 
    private void _Debug_D_2_v_2_0__Query() {
		// TODO Auto-generated method stub
    	String q = "from:sujipikaiki since:2014-01-01";
//    	String q = "from:sujipikaiki";
    	
    	// Log
		String log_msg = "Starting a query => " + q;

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
    	
		String since = "2014-01-01";
		Query query3 = new Query(q);
//		Query query3 = new Query(q).since(since);
		
		query3.setCount(30);
		
		// Log
		log_msg = "since=" + since;

		Log.d("[" + "MainActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
//		Query query3 = (new Query(q));
//		Query query3 = new Query("from:imsrk").rpp(100);
		
		try {
			QueryResult result3 = CONS.TwitterData.twitter.search(query3);
			
			int res = result3.getCount();
			
			// Log
			log_msg = "result => " + String.valueOf(res);

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			List<Status> statuses = result3.getTweets();
			
			// Log
			log_msg = "statuses => " + statuses.size();

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			for (Status status : statuses) {
				
				// Log
				log_msg = "status=" + status.getText();

				Log.d("["
						+ "MainActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
			}
			
			boolean b_res = result3.hasNext();
			
			// Log
			log_msg = "hasNext => " + b_res;

			Log.d("["
					+ "MainActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//private void _Debug_D_2_v_2_0__Query()

}//public class MainActv extends Activity
