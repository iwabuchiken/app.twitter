package app.twitter;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import app.twitter.adapters.Adp_Twt;
import app.twitter.models.Twt;
import app.twitter.utils.CONS;
import app.twitter.utils.Methods_Dlg;
import app.twitter.utils.Methods_twt;

public class TLActv extends ListActivity {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.actv_tl);
		
		this.setTitle(this.getClass().getName());
		
	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		_Reset_Fields();
		
		
		this.finish();
		
	}

	private void _Reset_Fields() {
		// TODO Auto-generated method stub
		CONS.TwitterData.twts_Filtered = null;
		CONS.TwitterData.twts_Show = null;
		CONS.TwitterData.twts_Full = null;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_timeline_actv, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		
		case R.id.menu_timeline_filter://--------------------
			
			case_Menu_Timeline_Filter();
//			this.logoutFromTwitter();
			
			break;
			
		default://-------------------------------------
			break;
	
		}

		
		return super.onOptionsItemSelected(item);
	}

	private void case_Menu_Timeline_Filter() {
		// TODO Auto-generated method stub
	
		Methods_Dlg.dlg_Filter_TimeLine(this);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		// Statuses obtained?
		if (CONS.TwitterData.statuses == null) {
			
			// Log
			String log_msg = "statuses => null";

			Log.d("["
					+ "TLActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
			// debug
			String toa_msg = "No tweet obtained";
			Toast.makeText(this, toa_msg, Toast.LENGTH_SHORT).show();
			
			finish();
//			return;
			
		}

		/*********************************
		 * Build: Twts lists
		 *********************************/
		if (CONS.TwitterData.twts_Full == null) {
			
			CONS.TwitterData.twts_Full = Methods_twt.get_TwtsFromStatuses(
					this,
					CONS.TwitterData.statuses);
			
		}
		
//		List<Twt> twts = Methods_twt.get_TwtsFromStatuses(
		if (CONS.TwitterData.twts_Show == null) {
			
//			CONS.TwitterData.twts_Show = CONS.TwitterData.twts_Full;
			CONS.TwitterData.twts_Show = new ArrayList<Twt>();
			
			CONS.TwitterData.twts_Show.addAll(CONS.TwitterData.twts_Full);
			
		}
		
		/*********************************
		 * Build: Adapter
		 *********************************/
		CONS.TwitterData.adp_Twt = new Adp_Twt(
//				Adp_Twt adp_Twt = new Adp_Twt(
							this,
							R.layout.list_row_twt_list,
							CONS.TwitterData.twts_Show);
//		twts);
		
		this.setListAdapter(CONS.TwitterData.adp_Twt);
//		this.setListAdapter(adp_Twt);
		
//		// Log
//		String log_msg = "getTwtCreatedAt() => "
//						+ twts.get(0).getTwtCreatedAt();
//
//		Log.d("[" + "TLActv.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ " : "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", log_msg);
		
//		// Log
//		if (twts == null) {
//			
//			// Log
//			String log_msg = "twts => null";
//
//			Log.d("["
//					+ "TLActv.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//			
//		} else {//if (twts == null)
//			
//			String log_msg = "" + String.valueOf(twts.size());
//			
//			Log.d("[" + "TLActv.java : "
//					+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//		}//if (twts == null)
		
		
	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}//public class TLActv extends ListActivity
