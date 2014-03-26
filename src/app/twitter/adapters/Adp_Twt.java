package app.twitter.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.twitter.R;
import app.twitter.models.Twt;
import app.twitter.utils.CONS;
import app.twitter.utils.Methods;

public class Adp_Twt extends ArrayAdapter<Twt> {

	//
	private int resourceId;
	
	Context con;
	
	public Adp_Twt
	(Context context, int textViewResourceId, List<Twt> list) {
		super(context, textViewResourceId, list);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.con		= context;
		
		this.resourceId = textViewResourceId;
		
		
	}//public Adp_Twt

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		/*----------------------------
		 * Steps
		 * 1. Inflate
		 * 2. Get views
		 * 3. Get item
		 * 4. Set values
		 * 
		 * 5. Set background
			----------------------------*/
		
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
        if (v == null) {
        	
            LayoutInflater inflater = 
            		(LayoutInflater) getContext()
    					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            v = inflater.inflate(resourceId, null);
        }

        Twt twt = (Twt) getItem(position);

        _getView_SetTexts(v, twt, position);

		return v;
//		return super.getView(position, convertView, parent);
	}//public View getView(int position, View convertView, ViewGroup parent)

	private void
	_getView_SetTexts(View v, Twt twt, int position) {
		/*********************************
		 * Get: Views
		 *********************************/
		TextView tv_Date = (TextView) v.findViewById(R.id.listrow_twt_list_tv_date);
		TextView tv_Time = (TextView) v.findViewById(R.id.listrow_twt_list_tv_time);
		TextView tv_Txt = (TextView) v.findViewById(R.id.listrow_twt_list_tv_txt);
		
		/*********************************
		 * Set: Texts
		 *********************************/
		tv_Txt.setText(twt.getText());
		
		/*********************************
		 * Set: Date & time
		 *********************************/
		String date = null;
		String time = null;
		
		if (twt.getTwtCreatedAt() == null
				|| twt.getTwtCreatedAt().split(" ").length < 2) {
			
			date = "NO DATA";
			time = "NO DATA";
			
		} else {//if (twt.getTwtCreatedAt() == null)
			
			String[] timeData = twt.getTwtCreatedAt().split(" ");
			
			date = timeData[0];
			time = timeData[1];
			
		}//if (twt.getTwtCreatedAt() == null)
		
		tv_Date.setText(date);
		tv_Time.setText(time);
		
		/*********************************
		 * Background
		 *********************************/
		__getView_SetTexts__SetBackground(tv_Date, twt.getTwtCreatedAt());
//		// TODO Auto-generated method stub
//		SharedPreferences prefs = ((Activity)con).getSharedPreferences(
//				CONS.PREFS.pName_TWT,
//				Context.MODE_PRIVATE);
//		
//		int savedPosition = prefs.getInt(
//				CONS.PREFS.pKey_CurrentItemPosition,
//				-1);
//
//		if (savedPosition == position) {
//			
//			tv_Txt.setBackgroundResource(R.color.gold2);
//			tv_Txt.setTextColor(Color.BLACK);
//			
//		} else if (savedPosition == -1) {//if (savedPosition == position)
//			
//		} else {//if (savedPosition == position)
//			
//			tv_Txt.setBackgroundColor(Color.WHITE);
//			tv_Txt.setTextColor(Color.BLACK);
//			
//		}//if (savedPosition == position)
		
	}//_getView_SetTexts(View convertView, Twt loc)

	private void
	__getView_SetTexts__SetBackground
	(TextView tv_Date, String twt_TimeLabel) {
		// TODO Auto-generated method stub
//		long today = Methods.getMillSeconds_now();
//
//		long twt_Date = Methods.get_MillSeconds_FromTimeLabel(timeLabel);
//		
//		long diff = today - twt_Date;
//		
//		// Log
//		String log_msg = "today=" + String.valueOf(today)
//						+ "/"
//						+ "twt_Date=" + String.valueOf(twt_Date)
//						+ "/"
//						+ "diff=" + String.valueOf(diff);
//
//		Log.d("[" + "Adp_Twt.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ " : "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", log_msg);
		
		String today = Methods.getTimeLabel(
						Methods.getMillSeconds_now(),
						CONS.Others.TimeLabelTypes.Readable);
		
		String today_Date = today.split(" ")[0];
		String twt_TimeLabel_Date = twt_TimeLabel.split(" ")[0];
		
		
		long date_Diff = Methods.get_DateDiff(
						today_Date,
						twt_TimeLabel_Date,
						"/");

		/*********************************
		 * Set: Background
		 *********************************/
		int date_Diff_int = (int) date_Diff;
		
		if (date_Diff_int == 0) {
			
			tv_Date.setBackgroundResource(R.color.blue1);
			
		} else if (date_Diff_int == 1) {//if (date_Diff_int == 0)
			
			tv_Date.setBackgroundResource(R.color.yellow_bright);
			
		} else {//if (date_Diff_int == 0)
			
			tv_Date.setBackgroundResource(R.color.Arsenic);
			
		}//if (date_Diff_int == 0)
		
		
//		switch(date_Diff_int) {
//		
//		case 0:
//			
//			tv_Date.setBackgroundResource(R.color.blue1);
////			tv_Date.setBackgroundResource(R.color.yellow_bright);
//			
//			break;
//			
//		case 1:
//			
//			tv_Date.setBackgroundResource(R.color.yellow_bright);
////			tv_Date.setBackgroundResource(R.color.yellow_dark);
//			
//			break;
//			
//		default:
//			
//			tv_Date.setBackgroundResource(R.color.Arsenic);
//			
//			break;
//			
//		}//switch(date_Diff_int)
		
		// Log
		String log_msg = "today=" + today
						+ "/"
						+ "twt_TimeLabel=" + twt_TimeLabel
						+ "/"
						+ "date_Diff=" + String.valueOf(date_Diff)
						;

		Log.d("[" + "Adp_Twt.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
	}//__getView_SetTexts__SetBackground

}//public class ItemListAdapter extends ArrayAdapter<ShoppingItem>
