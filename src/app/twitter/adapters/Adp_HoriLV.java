package app.twitter.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.twitter.R;
import app.twitter.listeners.dialogs.DOI_CL;
import app.twitter.specials.HorizontalListViewDemo;
import app.twitter.utils.CONS;

public class Adp_HoriLV extends BaseAdapter {

	Activity actv;
	
	Vibrator vib;
	
	public Adp_HoriLV(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}
	

        private OnClickListener mOnButtonClicked = new OnClickListener() {
           
            @Override
            public void onClick(View v) {
            	
            	vib.vibrate(CONS.Others.VIB_LENGTH);
            	
            	// Log
				String log_msg = "Click!";

				Log.d("["
						+ "Adp_HoriLV.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
            	
//                AlertDialog.Builder builder = new AlertDialog.Builder(HorizontalListViewDemo.this);
//                builder.setMessage("hello from " + v);
//                builder.setPositiveButton("Cool", null);
//                builder.show();
               
            }
        };

		@Override
        public int getCount() {
            return CONS.TwitterData.patternsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	
            View retval = LayoutInflater
            			.from(parent.getContext())
            				.inflate(R.layout.list_row_simple_1, null);
            
            TextView title =
            		(TextView) retval.findViewById(R.id.list_row_simple_1_tv);
            
//            Button button = (Button) retval.findViewById(R.id.clickbutton);
//            button.setOnClickListener(mOnButtonClicked);
            
//            title.setOnItemClickListener(new DOI_CL(actv, title));
            
            title.setText(CONS.TwitterData.patternsList.get(position));
//            title.setText(dataObjects[position]);
           
            return retval;
        }
}
