package app.twitter.utils;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import app.twitter.TLActv;
import app.twitter.TwtActv;
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
			
			int numberOfTweets = 40;
//			int numberOfTweets = 50;
//			int numberOfTweets = 100;
			long lastID = Long.MAX_VALUE;
			
//			List<Status> statuses = new ArrayList<Status>();
			
			while (statuses.size () < numberOfTweets) {
				
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
			
			for (Status st : statuses) {
				
				// Log
				log_msg = "st.getText()=" + st.getText()
								+ "(id="
								+ st.getId()
								+ "/"
								+ "created="
								+ st.getCreatedAt()
								+ ")";

				Log.d("["
						+ "Methods_twt.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
			}
			
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

	public static void start_TimeLine(Activity actv, int numOfTweets) {
		// TODO Auto-generated method stub
		CONS.TwitterData.statuses = Methods_twt.get_TimeLine(actv, numOfTweets);
//		List<Status> statuses = Methods_twt.get_TimeLine(actv, numOfTweets);
		
		if (CONS.TwitterData.statuses == null) {
			
			// Log
			String log_msg = "statuses => null";

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
				
//	            builder.setOAuthConsumerKey(CONS.TwitterData.TWITTER_CONSUMER_KEY);
//	            builder.setOAuthConsumerSecret(CONS.TwitterData.TWITTER_CONSUMER_SECRET);

			}
			
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
	
}//public class Methods_twt
