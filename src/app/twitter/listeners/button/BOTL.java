package app.twitter.listeners.button;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import app.twitter.utils.Tags;

public class BOTL implements OnTouchListener {

	Activity actv;

	//
	Vibrator vib;
	
	public BOTL(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public boolean
	onTouch(View v, MotionEvent event) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			switch (tag) {
			
			case LOGIN:
				
				v.setBackgroundColor(Color.GRAY);
				
//				v.setBackgroundColor(
//						actv.getResources().getColor(R.color.gray1));
				
				break;
				
			default:
				
				v.setBackgroundColor(Color.GRAY);
				
				break;
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:
			
			
		case MotionEvent.ACTION_UP:
			switch (tag) {
			case LOGIN:
				
				v.setBackgroundColor(Color.WHITE);
				
				break;

			default:
				
				v.setBackgroundColor(Color.WHITE);
				
				break;
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
			
		}//switch (event.getActionMasked())
		
		return false;
		
	}//onTouch(View v, MotionEvent event)

}//public class BOTL implements OnTouchListener
