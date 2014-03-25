package app.twitter.listeners.button;

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.twitter.utils.CONS;
import app.twitter.utils.Methods_Dlg;
import app.twitter.utils.Methods_twt;
import app.twitter.utils.Tags;

public class BOCL implements OnClickListener {

	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;

	public BOCL(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public void onClick(View v) {
//		//
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
//
		vib.vibrate(CONS.Others.VIB_LENGTH);
		
		//
		switch (tag) {
		
		case LOGIN://-------------------------------------
			
			_case_Login();
			
			break;
			
		case LOGOUT://-------------------------------------
			
			_case_Logout();
			
			break;
			
		case TIMELINE://-------------------------------------
			
			_case_TimeLine();
			
			break;
			
		case TWEET://-------------------------------------
			
			_case_Tweet();
			
			break;
			
		case BACK://-------------------------------------
			
			_case_Back();
			
			break;
			
		case SEND_TWEET://-------------------------------------
			
			_case_SendTweet();
			
			break;
			
		case PATTERN://-------------------------------------
			
			_case_PATTERN();
			
			break;
			
		default:
			break;
			
		}//switch (tag)
		
	}//public void onClick(View v)

	private void _case_PATTERN() {
		// TODO Auto-generated method stub
		Methods_Dlg.dlg_Patterns(actv);
		
	}

	private void _case_SendTweet() {
		// TODO Auto-generated method stub
		/*********************************
		 * Text there?
		 *********************************/
		// UI there?
		if (CONS.UIS_Twt.et_Twt == null) {
			
			// Log
			String log_msg = "CONS.UIS_Twt.et_Twt => null";

			Log.d("["
					+ "BOCL.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			String toa_msg = "EditText => null";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
			
			return;
			
		} else if (CONS.UIS_Twt.et_Twt.getText() == null) {
			
			// Log
			String log_msg = "getText() => null";

			Log.d("["
					+ "BOCL.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			String toa_msg = "EditText => Can't get text";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		// Any text?
		String text = CONS.UIS_Twt.et_Twt.getText().toString();
		
		if (text == null || text.equals("")) {
			
			// Log
			String log_msg = "text => null, or no content";
			
			Log.d("["
					+ "BOCL.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
							+ Thread.currentThread().getStackTrace()[2].getMethodName()
							+ "]", log_msg);
			
			// debug
			Toast.makeText(actv, log_msg, Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		// Send tweet
		Methods_twt.send_Tweet(actv);
		
	}//private void _case_SendTweet()

	private void _case_Back() {
		// TODO Auto-generated method stub
		
		Methods_twt.backTo_MainActv(actv);
		
	}

	private void _case_Tweet() {
		// TODO Auto-generated method stub
		Methods_twt.start_TwtActv(actv);
		
	}

	private void _case_Login() {
		// TODO Auto-generated method stub
		Methods_twt.loginToTwitter(actv);
	}
	
	private void _case_Logout() {
		// TODO Auto-generated method stub
		Methods_twt.logoutFromTwitter(actv);
	}
	
	private void _case_TimeLine() {
		
		Methods_twt.start_TimeLine(actv);
//		Methods_twt.start_TimeLine(actv, CONS.TwitterData.defaultNumOfTweets);
//		Methods_twt.get_TimeLine(actv, CONS.TwitterData.numOfTweets);
		
	}//private void _case_TimeLine()

}//public class BOCL implements OnClickListener
