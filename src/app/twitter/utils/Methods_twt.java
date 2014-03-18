package app.twitter.utils;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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

public class Methods_twt {

	public static void loginToTwitter(Activity actv) {
		// TODO Auto-generated method stub
        // Check if already logged in
        if (!Methods_twt.isTwitterLoggedInAlready()) {
        	
        	// Log
			String log_msg = "Starting => login process";

			Log.d("["
					+ "MainActv.java : "
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
					+ "MainActv.java : "
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
					+ "MainActv.java : "
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
						+ "MainActv.java : "
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
						+ "MainActv.java : "
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
						+ "MainActv.java : "
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
						+ "MainActv.java : "
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
					+ "MainActv.java : "
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
	        CONS.UIS.btnUpdateStatus.setVisibility(View.GONE);
	        CONS.UIS.txtUpdate.setVisibility(View.GONE);
	        CONS.UIS.lblUpdate.setVisibility(View.GONE);
	        CONS.UIS.lblUserName.setText("");
	        CONS.UIS.lblUserName.setVisibility(View.GONE);
	     
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
	
}//public class Methods_twt
