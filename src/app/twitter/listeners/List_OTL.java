package app.twitter.listeners;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import app.twitter.R;
import app.twitter.utils.Tags;

public class List_OTL implements OnTouchListener {

	Activity actv;

	//
	Vibrator vib;

	public List_OTL(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public boolean
	onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Tags.ListTags tag = (Tags.ListTags) v.getTag();
		
		// Log
		String log_msg = "tag => " + tag.toString();

		Log.d("[" + "List_OTL.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			// Log
			log_msg = "DOWN";

			Log.d("["
					+ "List_OTL.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			switch (tag) {
			
			case admin_adapter://---------------------------
				
				TextView tv = (TextView) v.findViewById(R.id.list_row_simple_1_tv);
				
				tv.setBackgroundColor(Color.GRAY);
				
				break;// case main_list_adapter
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:
			
			
		case MotionEvent.ACTION_UP:
			
			// Log
			Log.d("ListOnTouchListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "UP");
			
			switch (tag) {
			
			case admin_adapter://---------------------------
				
				TextView tv = (TextView) v.findViewById(R.id.list_row_simple_1_tv);
				
				tv.setBackgroundColor(Color.WHITE);
				
				break;// case main_list_adapter

			}//switch (tag)
		
			break;//case MotionEvent.ACTION_UP
			
		default:
			
			// Log
			log_msg = "Unknown event => " + String.valueOf(event.getActionMasked());

			Log.d("["
					+ "List_OTL.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			break;//case MotionEvent.ACTION_UP:
			
		}//switch (event.getActionMasked())
		
//		return true;
		return false;
		
	}//onTouch(View v, MotionEvent event)

}//public class List_OTL implements OnTouchListener
