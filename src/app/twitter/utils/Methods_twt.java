package app.twitter.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import app.twitter.PrefActv;
import app.twitter.R;
import app.twitter.TLActv;
import app.twitter.TwtActv;
import app.twitter.listeners.ILCL;
import app.twitter.listeners.dialogs.DOI_CL;
import app.twitter.models.Twt;
import app.twitter.tasks.Task_SendTweet;

public class Methods_twt {

	public static void loginToTwitter(Activity actv) {
		// TODO Auto-generated method stub
        // Check if already logged in
        if (!Methods_twt.isTwitterLoggedInAlready()) {
        	
        	// Log
			String log_msg = "Starting => login process";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
        	
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(CONS.TwitterData.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(CONS.TwitterData.TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
            
            TwitterFactory factory = new TwitterFactory(configuration);
            CONS.TwitterData.twitter = factory.getInstance();
 
            // Log
			log_msg = "twitter inctance => Created";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
            
//			//test
//			_Debug_D_2_v_2_0__Query();
			
			
            // Log
			log_msg = "Setup => Done."
					+ " Starting to get a request token...";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
            
            try {
                CONS.TwitterData.requestToken = CONS.TwitterData.twitter
                        .getOAuthRequestToken(CONS.URLS.TWITTER_CALLBACK_URL);
                
                // Log
				log_msg = "CONS.TwitterData.requestToken => "
							+ CONS.TwitterData.requestToken.getToken();

				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
                
				// Log
				log_msg = "Starting activity... ("
							+ "AuthenticationURL => "
							+ CONS.TwitterData.requestToken.getAuthenticationURL()
							+ ")";

				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
                actv.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(CONS.TwitterData.requestToken.getAuthenticationURL())));
                
            } catch (TwitterException e) {
            	
                e.printStackTrace();
                
            }
            
        } else {
            // user already logged into twitter
            Toast.makeText(actv.getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }//if (!isTwitterLoggedInAlready())

	}

    public static boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return CONS.PREFS.mSharedPreferences
        		.getBoolean(CONS.PREFS.PREF_KEY_TWITTER_LOGIN, false);
    }

    public static void logoutFromTwitter(Activity actv) {
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
//        CONS.UIS.btnUpdateStatus.setVisibility(View.GONE);
//        CONS.UIS.txtUpdate.setVisibility(View.GONE);
//        CONS.UIS.lblUpdate.setVisibility(View.GONE);
//        CONS.UIS.lblUserName.setText("");
//        CONS.UIS.lblUserName.setVisibility(View.GONE);
     
        CONS.UIS.btnLoginTwitter.setVisibility(View.VISIBLE);
        CONS.UIS.btnTimeLine.setVisibility(View.GONE);
        CONS.UIS.btnTweet.setVisibility(View.GONE);
        
        // twitter instance => null
        CONS.TwitterData.twitter = null;
        
        // Log
		String log_msg = "Logout => Done";

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		// debug
		String toa_msg = "Logout => Done";
		Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
		
    }//private void logoutFromTwitter()

    /*********************************
	 * @return null => 1. Login undone<br>
	 * 				2. Access token => null<br>
	 * 				3. TwitterException
	 *********************************/
    public static List<Status>
    get_TimeLine(Activity actv, int numOfTweets) {
    	
    	// Init twitter
    	if (CONS.TwitterData.twitter == null) {
			
//    		Methods_twt.initTwitter(actv);
    		
    		// Log
			String log_msg = "twitter => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			String toa_msg = "Login => not yet";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
    		
			return null;
			
		}
    	
    	//debug
    	// Log
		String log_msg;
		try {
			log_msg = "token => "
							+ CONS.TwitterData.twitter
								.getOAuthAccessToken().getToken();
			
			Log.d("[" + "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			return null;
			
		}

    	
    	List<Status> statuses = new ArrayList<Status>();
    	
		if (Methods_twt.isTwitterLoggedInAlready()) {
			
			Paging pg = new Paging();
			
//			int numberOfTweets = 40;
//			int numberOfTweets = 50;
//			int numberOfTweets = 100;
			long lastID = Long.MAX_VALUE;
			
//			List<Status> statuses = new ArrayList<Status>();
			
			while (statuses.size () < numOfTweets) {
				
				// Log
				log_msg = "Starting => get time line";

				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
				try {
					statuses.addAll(CONS.TwitterData.twitter.getHomeTimeline(pg));
					
					for (Status t: statuses) 
						if(t.getId() < lastID) lastID = t.getId();
					
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Log
				log_msg = "statuses.size() => " + statuses.size();
				
				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
								+ " : "
								+ Thread.currentThread().getStackTrace()[2]
										.getMethodName() + "]", log_msg);
				
				pg.setMaxId(lastID-1);
				
			}//while (statuses.size () < numberOfTweets)
			
			if (statuses.size() > numOfTweets) {
				
				statuses = statuses.subList(0, numOfTweets);
				
				log_msg = "statuses.size() => " + statuses.size();
				
				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
								+ " : "
								+ Thread.currentThread().getStackTrace()[2]
										.getMethodName() + "]", log_msg);
				
			}//if (statuses.size() > numOfTweets)
			
			// Log
			

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			
//			for (Status st : statuses) {
//				
//				// Log
//				log_msg = "st.getText()=" + st.getText()
//								+ "(id="
//								+ st.getId()
//								+ "/"
//								+ "created="
//								+ st.getCreatedAt()
//								+ ")";
//
//				Log.d("["
//						+ "Methods_twt.java : "
//						+ +Thread.currentThread().getStackTrace()[2]
//								.getLineNumber()
//						+ " : "
//						+ Thread.currentThread().getStackTrace()[2]
//								.getMethodName() + "]", log_msg);
//				
//			}
			
		} else {//if (isTwitterLoggedInAlready)
			
			// Log
			log_msg = "Login => Not yet";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber()
					+ " : "
					+ Thread.currentThread().getStackTrace()[2]
							.getMethodName() + "]", log_msg);
			
			// debug
			String toa_msg = "Login => Not yet";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
			
			return null;
			
		}//if (isTwitterLoggedInAlready)

    	return statuses;
    	
    }//get_TimeLine(Activity actv, int numOfTweets)

    public static void initTwitter(Activity actv) {
    	
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONS.TwitterData.TWITTER_CONSUMER_KEY);
        builder.setOAuthConsumerSecret(CONS.TwitterData.TWITTER_CONSUMER_SECRET);
        Configuration configuration = builder.build();
        
        TwitterFactory factory = new TwitterFactory(configuration);
        CONS.TwitterData.twitter = factory.getInstance();

    }

	public static void validate_Login(Activity actv) {
		// TODO Auto-generated method stub
		
		// Token => exists?
		Methods_twt.validate_Login_2(actv);
		
		if (CONS.TwitterData.twitter == null) {
			
			// Log
			String log_msg = "twitter login => Not yet";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			Methods_twt.setPref_ResetLoggedIn(actv);
			
		} else {//if (CONS.TwitterData.twitter == null)
			
			// Log
			String log_msg = "twitter => not null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		}//if (CONS.TwitterData.twitter == null)
		
		
	}

	public static void
	setPref_ResetLoggedIn(Activity actv) {
		
			CONS.PREFS.mSharedPreferences = 
					actv.getApplicationContext()
						.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
			
	        Editor e = CONS.PREFS.mSharedPreferences.edit();
	        e.remove(CONS.PREFS.PREF_KEY_OAUTH_TOKEN);
	        e.remove(CONS.PREFS.PREF_KEY_OAUTH_SECRET);
	        e.remove(CONS.PREFS.PREF_KEY_TWITTER_LOGIN);
	        e.commit();
	     
	        // After this take the appropriate action
	        // I am showing the hiding/showing buttons again
	        // You might not needed this code
	        CONS.UIS.btnLogoutTwitter.setVisibility(View.GONE);
//	        CONS.UIS.btnUpdateStatus.setVisibility(View.GONE);
//	        CONS.UIS.txtUpdate.setVisibility(View.GONE);
//	        CONS.UIS.lblUpdate.setVisibility(View.GONE);
//	        CONS.UIS.lblUserName.setText("");
//	        CONS.UIS.lblUserName.setVisibility(View.GONE);
	     
	        CONS.UIS.btnLoginTwitter.setVisibility(View.VISIBLE);

	        // Log
			String log_msg = "Reset pref => done";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
	}//setPref_ResetLoggedIn(Activity actv)

	public static void start_TimeLine(Activity actv) {
//		public static void start_TimeLine(Activity actv, int numOfTweets) {
		// TODO Auto-generated method stub
		/*********************************
		 * Get: num of tweets
		 *********************************/
		SharedPreferences prefs = actv
				.getSharedPreferences(
					actv.getString(R.string.prefs_shared_prefs_name),
					Context.MODE_PRIVATE);
		
		String timeLineSize = prefs.getString(
//				int timeLineSize = prefs.getInt(
				actv.getString(R.string.prefs_timeline_size_key), "-1");
		
		int numOfTweets = CONS.TwitterData.defaultNumOfTweets;
		
		if (Methods.is_numeric(timeLineSize)) {
			
			numOfTweets = Integer.parseInt(timeLineSize);
			
		}//if (Methods.is_numeric(timeLineSize))

		// Log
		String log_msg = "numOfTweets => " + String.valueOf(numOfTweets);

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		CONS.TwitterData.statuses = Methods_twt.get_TimeLine(actv, numOfTweets);
//		List<Status> statuses = Methods_twt.get_TimeLine(actv, numOfTweets);
		
		if (CONS.TwitterData.statuses == null) {
			
			// Log
			log_msg = "statuses => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			return;
			
		}
		
		/*********************************
		 * Start: Activity
		 *********************************/
		Intent i = new Intent();
		
		i.setClass(actv, TLActv.class);
		
		/*********************************
		 * 3. Start
		 *********************************/
		actv.startActivity(i);
		
		//REF no animation http://stackoverflow.com/questions/6972295/switching-activities-without-animation answered Nov 19 '13 at 21:42
		actv.overridePendingTransition(0, 0);

	}
	
	public static void start_Settings(Activity actv) {
		/*********************************
		 * Start: Activity
		 *********************************/
		Intent i = new Intent();
		
		i.setClass(actv, PrefActv.class);
		
		/*********************************
		 * 3. Start
		 *********************************/
		actv.startActivity(i);
		
		//REF no animation http://stackoverflow.com/questions/6972295/switching-activities-without-animation answered Nov 19 '13 at 21:42
		actv.overridePendingTransition(0, 0);
		
	}//public static void start_Settings(Activity actv)
	
	public static void
	start_Activity(Activity actv, String className) {
		/*********************************
		 * Start: Activity
		 *********************************/
		Intent i = new Intent();
		
		Class cl;
		
		try {
			
			cl = Class.forName(className);
			
			i.setClass(actv, cl);
			
			// Log
			String log_msg = "Class => set: "
							+ cl.getName();

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
			
		}
		
//		i.setClass(actv, PrefActv.class);
		
		/*********************************
		 * 3. Start
		 *********************************/
		actv.startActivity(i);
		
		//REF no animation http://stackoverflow.com/questions/6972295/switching-activities-without-animation answered Nov 19 '13 at 21:42
		actv.overridePendingTransition(0, 0);
		
	}//public static void start_Settings(Activity actv)

	public static List<Twt>
	get_TwtsFromStatuses
	(Activity actv, List<Status> statuses) {
		// TODO Auto-generated method stub
		List<Twt> twts = new ArrayList<Twt>();
		
		for (Status st : statuses) {
			
			Twt twt = new Twt.Builder()
						.setTwtCreatedAt(st.getCreatedAt().toLocaleString())
						.setTwtId(st.getId())
						.setText(st.getText())
						.build();
			
			if (twt != null) {
				
				twts.add(twt);
				
			} else {//if (twt != null)
				
				continue;
				
			}//if (twt != null)
			
		}//for (Status st : statuses)
		
		return twts;
		
	}//get_TwtsFromStatuses

	public static void start_TwtActv(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		i.setClass(actv, TwtActv.class);
		
		/*********************************
		 * 3. Start
		 *********************************/
		actv.startActivity(i);
		
		//REF no animation http://stackoverflow.com/questions/6972295/switching-activities-without-animation answered Nov 19 '13 at 21:42
		
		actv.overridePendingTransition(0, 0);
	}

	public static void backTo_MainActv(Activity actv) {
		// TODO Auto-generated method stub
		
		Methods_twt.saveTempText(actv);
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);
		
	}

	/*********************************
	 * @return success => 20 (SendTweet_Success)<br>
	 * 		failed => -40 (SendTweet_Failed)
	 *********************************/
	public static int
	send_Tweet_Execute(Activity actv, String msg) {

	    try {
    	
			Status status = CONS.TwitterData.twitter.updateStatus(msg);
			
			// Log
			String log_msg = "update => done: " + status.getText();
	
			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			return CONS.ReturnValues.SendTweet_Success;
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
			return CONS.ReturnValues.SendTweet_Failed;
			
		}//try

	}//send_Tweet_Execute(Activity actv, String msg)
	
	public static void send_Tweet(Activity actv) {
		// TODO Auto-generated method stub
//		if (Methods_twt.isTwitterLoggedInAlready()) {
		if (Methods_twt.isTwitterLoggedInAlready()
				&& CONS.UIS_Twt.et_Twt != null
				&& CONS.UIS_Twt.et_Twt.getText() != null) {
			
			// Get: text
			String msg = CONS.UIS_Twt.et_Twt.getText().toString();

			Log.d("["
			+ "Methods_twt.java : "
			+ +Thread.currentThread().getStackTrace()[2]
					.getLineNumber() + " : "
			+ Thread.currentThread().getStackTrace()[2].getMethodName()
			+ "]", msg);

			Task_SendTweet task_ = new Task_SendTweet(actv, msg);
			
			// debug
			Toast.makeText(actv, "Starting a task...", Toast.LENGTH_LONG)
					.show();
			
			task_.execute(CONS.TaskData.TaskItems.SendTweet.toString());
			
			
//			String msg = CONS.UIS_Twt.et_Twt.getText().toString();
//			
//			// Log
//			String log_msg = "msg=" + msg;
//
//			Log.d("["
//					+ "Methods_twt.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//		    try {
//		    	
//				Status status = CONS.TwitterData.twitter.updateStatus(msg);
//				
//				// Log
//				log_msg = "update => done: " + status.getText();
//	
//				Log.d("["
//						+ "Methods_twt.java : "
//						+ +Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + " : "
//						+ Thread.currentThread().getStackTrace()[2].getMethodName()
//						+ "]", log_msg);
//				
//				// debug
//				String toa_msg = "update => done: " + status.getText();
//				Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
//				
//			} catch (TwitterException e) {
//				// TODO Auto-generated catch block
//				
//				e.printStackTrace();
//				
//			}//try
	    
		} else {//if (isTwitterLoggedInAlready)
			
			// Log
			String log_msg = "Login => Not yet";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber()
					+ " : "
					+ Thread.currentThread().getStackTrace()[2]
							.getMethodName() + "]", log_msg);
			
			// debug
			String toa_msg = "Login => Not yet";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
			
		}//if (isTwitterLoggedInAlready)

	}//public static void send_Tweet(Activity actv)

	public static void validate_Login_2(Activity actv) {
		// TODO Auto-generated method stub
		if (CONS.TwitterData.twitter == null) {

			Methods_twt._validate_Login_2__SetToken(actv);
			
//			String accessToken = CONS.PREFS.mSharedPreferences
//							.getString(CONS.PREFS.PREF_KEY_OAUTH_TOKEN, null);
//			
//			String accessTokenSecret = CONS.PREFS.mSharedPreferences
//					.getString(CONS.PREFS.PREF_KEY_OAUTH_SECRET, null);
//			
//			// Log
//			String log_msg = "token=" + accessToken
//							+ "/"
//							+ "secret=" + accessTokenSecret;
//
//			Log.d("["
//					+ "Methods_twt.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//			if (accessToken != null && accessTokenSecret != null) {
//				//REF http://twitter4j.org/en/code-examples.html
//				AccessToken at = new AccessToken(accessToken, accessTokenSecret);
//				
//				TwitterFactory factory = new TwitterFactory();
//				CONS.TwitterData.twitter = factory.getInstance();
//				
//				CONS.TwitterData.twitter.setOAuthConsumer(
//								CONS.TwitterData.TWITTER_CONSUMER_KEY,
//								CONS.TwitterData.TWITTER_CONSUMER_SECRET);
//				
//				CONS.TwitterData.twitter.setOAuthAccessToken(at);
//				
////	            builder.setOAuthConsumerKey(CONS.TwitterData.TWITTER_CONSUMER_KEY);
////	            builder.setOAuthConsumerSecret(CONS.TwitterData.TWITTER_CONSUMER_SECRET);
//
//			}
			
		} else {//if (CONS.TwitterData.twitter == null)
			
			// Log
			String log_msg = "CONS.TwitterData.twitter => not null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		}//if (CONS.TwitterData.twitter == null)
		
	}
	
	
	public static void
	_validate_Login_2__SetToken(Activity actv) {

		String accessToken = CONS.PREFS.mSharedPreferences
				.getString(CONS.PREFS.PREF_KEY_OAUTH_TOKEN, null);

		String accessTokenSecret = CONS.PREFS.mSharedPreferences
				.getString(CONS.PREFS.PREF_KEY_OAUTH_SECRET, null);

		// Log
		String log_msg = "token=" + accessToken
						+ "/"
						+ "secret=" + accessTokenSecret;
		
		Log.d("["
				+ "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		if (accessToken != null && accessTokenSecret != null) {
			//REF http://twitter4j.org/en/code-examples.html
			AccessToken at = new AccessToken(accessToken, accessTokenSecret);
			
			TwitterFactory factory = new TwitterFactory();
			CONS.TwitterData.twitter = factory.getInstance();
			
			CONS.TwitterData.twitter.setOAuthConsumer(
							CONS.TwitterData.TWITTER_CONSUMER_KEY,
							CONS.TwitterData.TWITTER_CONSUMER_SECRET);
			
			CONS.TwitterData.twitter.setOAuthAccessToken(at);
			
		//    builder.setOAuthConsumerKey(CONS.TwitterData.TWITTER_CONSUMER_KEY);
		//    builder.setOAuthConsumerSecret(CONS.TwitterData.TWITTER_CONSUMER_SECRET);

		}//if (accessToken != null && accessTokenSecret != null)

	}//_validate_Login_2__SetToken(Activity actv)
	
	private static AccessToken loadAccessToken() {
		
		String token = CONS.PREFS.mSharedPreferences
					.getString(CONS.PREFS.PREF_KEY_OAUTH_TOKEN, null);
		
		String tokenSecret = CONS.PREFS.mSharedPreferences
				.getString(CONS.PREFS.PREF_KEY_OAUTH_SECRET, null);
		
		if (token == null || tokenSecret == null) {
			
			// Log
			String log_msg = "AccessToken => can't create";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			return null;
			
		}
		
	    // load from a persistent store
	    return new AccessToken(token, tokenSecret);
	    
	}//private static AccessToken loadAccessToken(int useId)

	public static void
	setup_GridView(Activity actv) {
		// TODO Auto-generated method stub
		/*********************************
		 * View: GridView
		 *********************************/
		GridView gv = (GridView) actv.findViewById(R.id.actv_twt_gv);
		
		/*********************************
		 * Setup: DB
		 *********************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_twt);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		boolean res = dbu.tableExists(rdb, CONS.DB.tname_Patterns);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + CONS.DB.tname_Patterns);
			
		} else {//if (res == false)
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + CONS.DB.tname_Patterns);

			rdb.close();
			
			return;
			
		}		
		
		/*********************************
		 * Get: Patterns list
		 *********************************/
//		List<String> patterns =
		CONS.TwitterData.patternsList =
				_setup_GridView__GetPatternsList(actv, rdb);
		
		Methods_twt.sort_PatternsList(CONS.TwitterData.patternsList);
		
		/*********************************
		 * Close: DB
		 *********************************/
		rdb.close();
		
		/*********************************
		 * Adapter
		 *********************************/
		CONS.TwitterData.adp_Patterns = new ArrayAdapter<String>(
//				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
				R.layout.actv_tweet_grid_view,
				CONS.TwitterData.patternsList
//				patterns
		);

		gv.setAdapter(CONS.TwitterData.adp_Patterns);
//		gv.setAdapter(adapter);
		
		/*********************************
		 * Set: Listener
		 *********************************/
		gv.setTag(Tags.DialogItemTags.GV_Tweet);
		
		// OnClick
		gv.setOnItemClickListener(new DOI_CL(actv));
		
		// OnItemLongClick
		gv.setOnItemLongClickListener(new ILCL(actv));
		
	}//setup_GridView(Activity actv)

	/*********************************
	 * @return null => Query failed
	 *********************************/
	public static List<String>
	_setup_GridView__GetPatternsList
	(Activity actv, SQLiteDatabase rdb) {
		// TODO Auto-generated method stub
		Cursor c = rdb.query(
				CONS.DB.tname_Patterns,
				CONS.DB.cols_Patterns_Names,
				null, null, null, null, null);
		//CONS.cols_full, null, null, null, null, null);
		
		if (c == null) {
			
			// Log
			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "c == null");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		/*********************************
		 * Get: strings
		 *********************************/
		List<String> patterns = new ArrayList<String>();
		
		while (c.moveToNext()) {
			
			String val = c.getString(3);
		//	String val = c.getString(8);
			
			if (val != null) {
				
				patterns.add(val);
				
			}
			
			
		}//while (c.moveToNext()) {

		return patterns;
		
	}//_setup_GridView__GetPatternsList

	
	public static void
	add_Pattern2Text(Activity actv, String item) {
		// TODO Auto-generated method stub
		EditText et = CONS.UIS_Twt.et_Twt;
		
		if (et == null) {
			
			// Log
			String log_msg = "CONS.UIS_Twt.et_Twt => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			String toa_msg = "Sorry. EditText is null";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		String content = et.getText().toString();
		
		content += item;
//		content += item + " ";
		
		et.setText(content);
		
		et.setSelection(et.getText().toString().length());
		
	}//add_Pattern2Text(Activity actv, String item)

	public static boolean
	insertData_Patterns(Activity actv, String word) {
		// TODO Auto-generated method stub
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_twt);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		boolean result = dbu.insertData(
						wdb,
						CONS.DB.tname_Patterns,
						new String[]{
							CONS.DB.cols_Patterns_Names_skimmed[0]
						}
						,
						new String[]{word});
		
		wdb.close();
		
		return result;
		
	}//insertData_Patterns(Activity actv, String word)

	public static void saveTempText(Activity actv) {
		// TODO Auto-generated method stub
		SharedPreferences prefs = actv
				.getSharedPreferences(
						actv.getString(R.string.prefs_shared_prefs_name),
						Context.MODE_PRIVATE);
		
		boolean saveText = prefs.getBoolean(
				actv.getString(R.string.prefs_save_text_key), false);
		
		if (saveText == true
				&& CONS.UIS_Twt.et_Twt != null
				&& CONS.UIS_Twt.et_Twt.getText() != null) {
			
			String msg = CONS.UIS_Twt.et_Twt.getText().toString();
			
			
			SharedPreferences.Editor editor = prefs.edit();
			
			editor.putString(
						actv.getString(R.string.prefs_temp_saved_text_key),
						msg);
			
			editor.commit();
			
			// Log
			String log_msg = "Tweet text => saved";

			Log.d("["
					+ "TwtActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		}//if (saveText == true

	}//public static void saveTempText()

	public static void sort_PatternsList(List<String> patternsList) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/*----------------------------
		 * 1. Prep => Comparator
		 * 2. Sort
			----------------------------*/
		/*----------------------------
		 * 1. Prep => Comparator
			----------------------------*/
		Comparator<String> filecomparator = new Comparator<String>(){
			
			public int compare(String s1, String s2) {
				/*----------------------------
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					----------------------------*/
				
				int res = s1.compareTo(s2);
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/*----------------------------
		 * 2. Sort
			----------------------------*/
		Collections.sort(patternsList, filecomparator);
//		Arrays.sort(patternsList, filecomparator);

	}//public static void sortFileList(File[] files)

	public static void
	delete_PatternsItem
	(Activity actv, Dialog dlg1, Dialog dlg2, String pattItem) {
		// TODO Auto-generated method stub
		/*********************************
		 * Delete item: From DB
		 *********************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_twt);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		String sql = "DELETE FROM " + CONS.DB.tname_Patterns +
				" WHERE word='" + pattItem + "'";

		try {
			
			wdb.execSQL(sql);
			
			// debug
			Toast.makeText(actv, "Pattern deleted", Toast.LENGTH_LONG).show();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Pattern deleted => " + pattItem);
			
			/*----------------------------
			 * 3. Dismiss dialogues
				----------------------------*/
			dlg2.dismiss();
			dlg1.dismiss();
			
			/*********************************
			 * Refresh: Patterns list
			 *********************************/
			Methods_twt.refresh_PatternsList(actv, wdb);
			
		} catch (SQLException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Pattern deletion => Failed:  " + pattItem);
			
			// debug
			String toa_Msg = "Pattern deletion => Failed:  " + pattItem;
			Toast.makeText(
					actv,
					toa_Msg,
					Toast.LENGTH_LONG).show();
			
			
		} finally {
		
			wdb.close();
		
		}//try
		
		// Log
//		String log_msg = "Delete pattern => " + pattItem;
//
//		Log.d("[" + "Methods_twt.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ " : "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", log_msg);
		
		
	}//delete_PatternsItem

	public static void
	refresh_PatternsList(Activity actv, SQLiteDatabase wdb) {
		
		CONS.TwitterData.patternsList.clear();
		
		CONS.TwitterData.patternsList.addAll(
				Methods_twt._setup_GridView__GetPatternsList(actv, wdb));
				
		/*********************************
		 * Sort: Patterns list
		 *********************************/
		Methods_twt.sort_PatternsList(CONS.TwitterData.patternsList);

		/*********************************
		 * Notify: Adapter
		 *********************************/
		CONS.TwitterData.adp_Patterns.notifyDataSetChanged();
		
		// Log
		String log_msg = "Patterns list => Refreshed";

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
	}//refresh_PatternsList(Activity actv)

	public static void
	setup_GridView_Filter_Timeline
	(Activity actv, Dialog dlg) {
		// TODO Auto-generated method stub
		/*********************************
		 * Get: GV
		 *********************************/
		GridView gv_Patterns =
				(GridView) dlg.findViewById(R.id.dlg_filter_timeline_gv);
		
		/*********************************
		 * Setup: DB
		 *********************************/
		if (CONS.TwitterData.patternsList == null) {
			
			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_twt);
			
			SQLiteDatabase rdb = dbu.getReadableDatabase();
			
			boolean res = dbu.tableExists(rdb, CONS.DB.tname_Patterns);
			
			if (res == true) {
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Table exists: " + CONS.DB.tname_Patterns);
				
			} else {//if (res == false)
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Table doesn't exist: " + CONS.DB.tname_Patterns);
	
				rdb.close();
				
				return;
				
			}		
			
			/*********************************
			 * Get: Patterns list
			 *********************************/
	//		List<String> patterns =
			CONS.TwitterData.patternsList =
					_setup_GridView__GetPatternsList(actv, rdb);
			
			Methods_twt.sort_PatternsList(CONS.TwitterData.patternsList);
			
			/*********************************
			 * Close: DB
			 *********************************/
			rdb.close();
			
		}
		
		/*********************************
		 * Adapter
		 *********************************/
		if (CONS.TwitterData.adp_Patterns == null) {
			
			CONS.TwitterData.adp_Patterns = new ArrayAdapter<String>(
	//				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					actv,
					R.layout.actv_tweet_grid_view,
					CONS.TwitterData.patternsList
	//				patterns
			);

		}
		
		gv_Patterns.setAdapter(CONS.TwitterData.adp_Patterns);
//		gv.setAdapter(adapter);
		
		/*********************************
		 * Set: Listener
		 *********************************/
		gv_Patterns.setTag(Tags.DialogItemTags.GV_Filter_Timeline);
		
		// OnClick
		gv_Patterns.setOnItemClickListener(new DOI_CL(actv, dlg));
		
	}//setup_GridView_Filter_Timeline(GridView gv_Patterns)

	public static void
	filter_Timeline(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		/*********************************
		 * memo
		 *********************************/
		EditText et = (EditText) dlg1.findViewById(R.id.dlg_filter_timeline_et_content);
		
		String[] kwList = et.getText().toString().split(" |　");
		
		Methods_twt.build_TwtsList_Filtered(actv, kwList, dlg1);
		
//		// Log
//		String log_msg = "";
//		
//		for (String kw : kwList) {
//			
//			log_msg += kw + "/";
//					
//		}
//
//		Log.d("[" + "Methods_twt.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ " : "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", log_msg);
		
	}//filter_Timeline(Activity actv, Dialog dlg1)

	private static void
	build_TwtsList_Filtered
	(Activity actv, String[] kwList, Dialog dlg1) {
		// TODO Auto-generated method stub
		// Log
		String log_msg = "Starts => build_TwtsList_Filtered()";

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		/*********************************
		 * Validate: CONS.TwitterData.statuses
		 *********************************/
		if (CONS.TwitterData.statuses == null) {
			
			// Log
			log_msg = "CONS.TwitterData.statuses => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			Toast.makeText(actv, log_msg, Toast.LENGTH_SHORT).show();
		
			return;
			
		}
		
		/*********************************
		 * Validate: twts_Full
		 *********************************/
		if (CONS.TwitterData.twts_Full == null) {
			
			// Log
			log_msg = "CONS.TwitterData.twts_Full => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Full =
					Methods_twt.get_TwtsFromStatuses(
							actv,
							CONS.TwitterData.statuses);
			
		} else {
			
			// Log
			log_msg = "CONS.TwitterData.twts_Full => not null"
							+ "("
							+ "size="
							+ String.valueOf(CONS.TwitterData.twts_Full.size());

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
		}

		/*********************************
		 * Reset: twts_Filtered
		 *********************************/
		if (CONS.TwitterData.twts_Filtered != null) {
			
			CONS.TwitterData.twts_Filtered.clear();
			
			// Log
			log_msg = "CONS.TwitterData.twts_Filtered => cleared";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		} else if (CONS.TwitterData.twts_Filtered == null) {
			
			// Log
			log_msg = "CONS.TwitterData.twts_Filtered => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Filtered = new ArrayList<Twt>();
			
		}
		
		/*********************************
		 * Build
		 *********************************/
		// Log
		log_msg = "CONS.TwitterData.twts_Full => "
						+ String.valueOf(CONS.TwitterData.twts_Full.size());

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		for (Twt twt : CONS.TwitterData.twts_Full) {
			
			String text = twt.getText();
			
			boolean contained = true;
			
			for (String kw : kwList) {
				
				if (!text.toLowerCase().contains(kw.toLowerCase())) {
					
					contained = false;
					
					break;
							
				}
				
			}//for (String kw : kwList)
			
			if (contained == true) {
				
				CONS.TwitterData.twts_Filtered.add(twt);
				
				// Log
				log_msg = "tweet => added// " + twt.getText();

				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
			} else {
				
				// Log
				log_msg = "contained => false"
								+ "//"
								+ twt.getText();

				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
			}
			
		}//for (Twt twt : CONS.TwitterData.twts_Full)
		
		/*********************************
		 * Refresh: twts_Show
		 *********************************/
		if (CONS.TwitterData.twts_Show == null) {
			
			// Log
			log_msg = "CONS.TwitterData.twts_Show => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Show = new ArrayList<Twt>();
			
			CONS.TwitterData.twts_Show.addAll(CONS.TwitterData.twts_Filtered);
//			CONS.TwitterData.twts_Show = CONS.TwitterData.twts_Filtered;
			
		} else {//if (CONS.TwitterData.twts_Show == null)
			
			// Log
			log_msg = "CONS.TwitterData.twts_Show => not null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Show.clear();
			
			CONS.TwitterData.twts_Show.addAll(CONS.TwitterData.twts_Filtered);
			
		}//if (CONS.TwitterData.twts_Show == null)
		
		/*********************************
		 * Notify: Adapter
		 *********************************/
		CONS.TwitterData.adp_Twt.notifyDataSetChanged();
		
		// Log
		log_msg = "Adapter => notified"
						+ "("
						+ "CONS.TwitterData.twts_Show => now "
						+ String.valueOf(CONS.TwitterData.twts_Show.size());

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		/*********************************
		 * Dismiss: dlg1
		 *********************************/
		dlg1.dismiss();
		
		
	}//build_TwtsList_Filtered

	public static void
	reset_Filter(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		/*********************************
		 * Clear: twts_Filtered
		 *********************************/
		if (CONS.TwitterData.twts_Filtered != null) {
			
			CONS.TwitterData.twts_Filtered.clear();
			
			// Log
			String log_msg = "CONS.TwitterData.twts_Filtered => cleared";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		} else if (CONS.TwitterData.twts_Filtered == null) {
			
			// Log
			String log_msg = "CONS.TwitterData.twts_Filtered => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Filtered = new ArrayList<Twt>();
			
		}
		
		/*********************************
		 * Validate: twts_Full
		 *********************************/
		if (CONS.TwitterData.twts_Full == null) {
			
			// Log
			String log_msg = "CONS.TwitterData.twts_Full => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Full =
					Methods_twt.get_TwtsFromStatuses(
							actv,
							CONS.TwitterData.statuses);
			
		} else {
			
			// Log
			String log_msg = "CONS.TwitterData.twts_Full => not null"
							+ "("
							+ "size="
							+ String.valueOf(CONS.TwitterData.twts_Full.size());

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
		}

		/*********************************
		 * Set: twts_Full to twts_Filtered
		 *********************************/
		CONS.TwitterData.twts_Filtered.addAll(CONS.TwitterData.twts_Full);
		
		/*********************************
		 * Set: twts_Filtered to twts_Show
		 *********************************/
		if (CONS.TwitterData.twts_Show == null) {
			
			// Log
			String log_msg = "CONS.TwitterData.twts_Show => null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Show = new ArrayList<Twt>();
			
		} else {//if (CONS.TwitterData.twts_Show == null)
			
			// Log
			String log_msg = "CONS.TwitterData.twts_Show => not null";

			Log.d("["
					+ "Methods_twt.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			CONS.TwitterData.twts_Show.clear();
			
		}//if (CONS.TwitterData.twts_Show == null)
		
		CONS.TwitterData.twts_Show.addAll(CONS.TwitterData.twts_Filtered);
		
		/*********************************
		 * Notify: Adapter
		 *********************************/
		CONS.TwitterData.adp_Twt.notifyDataSetChanged();
		
		// Log
		String log_msg = "Adapter => notified"
						+ "("
						+ "CONS.TwitterData.twts_Show => now "
						+ String.valueOf(CONS.TwitterData.twts_Show.size());

		Log.d("[" + "Methods_twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		/*********************************
		 * Dismiss: dlg1
		 *********************************/
		dlg1.dismiss();
		
	}//reset_Filter(Activity actv, Dialog dlg1)
	
}//public class Methods_twt
