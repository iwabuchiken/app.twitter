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
		
		case LOGIN:
			
			_case_Login();
			
			break;
			
		case LOGOUT:
			
			_case_Logout();
			
			break;
			
		case TIMELINE:
			
			_case_TimeLine();
			
			break;
			
		case TWEET:
			
			_case_Tweet();
			
			break;
			
		default:
			break;
			
		}//switch (tag)
		
	}//public void onClick(View v)

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
		
		Methods_twt.start_TimeLine(actv, CONS.TwitterData.numOfTweets);
//		Methods_twt.get_TimeLine(actv, CONS.TwitterData.numOfTweets);
		
	}//private void _case_TimeLine()

}//public class BOCL implements OnClickListener
