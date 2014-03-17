package app.twitter.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.twitter.R;
import app.twitter.models.Twt;
import app.twitter.utils.CONS;

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
		// TODO Auto-generated method stub
		SharedPreferences prefs = ((Activity)con).getSharedPreferences(
				CONS.PREFS.pName_TWT,
				Context.MODE_PRIVATE);
		
		int savedPosition = prefs.getInt(
				CONS.PREFS.pKey_CurrentItemPosition,
				-1);

		if (savedPosition == position) {
			
			tv_Txt.setBackgroundResource(R.color.gold2);
			tv_Txt.setTextColor(Color.BLACK);
			
		} else if (savedPosition == -1) {//if (savedPosition == position)
			
		} else {//if (savedPosition == position)
			
			tv_Txt.setBackgroundColor(Color.WHITE);
			tv_Txt.setTextColor(Color.BLACK);
			
		}//if (savedPosition == position)
		
	}//_getView_SetTexts(View convertView, Twt loc)

}//public class ItemListAdapter extends ArrayAdapter<ShoppingItem>
