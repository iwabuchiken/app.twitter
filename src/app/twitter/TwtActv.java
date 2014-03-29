package app.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.twitter.listeners.button.BOCL;
import app.twitter.utils.CONS;
import app.twitter.utils.Methods_twt;
import app.twitter.utils.Tags;

public class TwtActv extends Activity {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
		
		setContentView(R.layout.actv_twt);
		
		this.setTitle(this.getClass().getName());
		
	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Methods_twt.saveTempText(this);
		
//		_onBackPressed__SaveTempText();
		
//		SharedPreferences prefs = this
//				.getSharedPreferences(
//						this.getString(R.string.prefs_shared_prefs_name),
//						Context.MODE_PRIVATE);
//		
//		boolean saveText = prefs.getBoolean(
//				this.getString(R.string.prefs_save_text_key), false);
//		
//		if (saveText == true
//				&& CONS.UIS_Twt.et_Twt != null
//				&& CONS.UIS_Twt.et_Twt.getText() != null) {
//			
//			String msg = CONS.UIS_Twt.et_Twt.getText().toString();
//			
//			
//			SharedPreferences.Editor editor = prefs.edit();
//			
//			editor.putString(
//						this.getString(R.string.prefs_temp_saved_text_key),
//						msg);
//			
//			editor.commit();
//			
//			// Log
//			String log_msg = "Tweet text => saved";
//
//			Log.d("["
//					+ "TwtActv.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//		}//if (saveText == true
		
		this.finish();
		
	}//public void onBackPressed()

	private void _onBackPressed__SaveTempText() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = this
				.getSharedPreferences(
						this.getString(R.string.prefs_shared_prefs_name),
						Context.MODE_PRIVATE);
		
		boolean saveText = prefs.getBoolean(
				this.getString(R.string.prefs_save_text_key), false);
		
		if (saveText == true
				&& CONS.UIS_Twt.et_Twt != null
				&& CONS.UIS_Twt.et_Twt.getText() != null) {
			
			String msg = CONS.UIS_Twt.et_Twt.getText().toString();
			
			
			SharedPreferences.Editor editor = prefs.edit();
			
			editor.putString(
						this.getString(R.string.prefs_temp_saved_text_key),
						msg);
			
			editor.commit();
			
			// Log
			String log_msg = "Tweet text => saved";

			Log.d("["
					+ "TwtActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
			
		}//if (saveText == true
		
	}//private void _onBackPressed__SaveTempText()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// Log
		String log_msg = "onPause()";

		Log.d("[" + "TwtActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		// Log
		String log_msg = "onRestart()";

		Log.d("[" + "TwtActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// Log
		String log_msg = "onResume()";

		Log.d("[" + "TwtActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		// Log
		String log_msg = "onStart()";

		Log.d("[" + "TwtActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		_Setup_UIs();
		
		_Setup_UIs__GridView();
		
		_Setup_Listeners();
		
		_Setup_TempText();
		
		_Setup_HoriLV();
		
	}//protected void onStart()

	private void _Setup_HoriLV() {
		// TODO Auto-generated method stub

		Methods_twt.setup_HoriLV(this);
		
	}

	private void _Setup_TempText() {
		// TODO Auto-generated method stub
		
		// Log
		String log_msg = "_Setup_TempText()";

		Log.d("[" + "TwtActv.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		SharedPreferences prefs = this
				.getSharedPreferences(
						this.getString(R.string.prefs_shared_prefs_name),
						Context.MODE_PRIVATE);
		
		boolean saveText = prefs.getBoolean(
				this.getString(R.string.prefs_save_text_key), false);
		
		if (saveText == true) {
			
			String tempTweetText = prefs.getString(
	//				int timeLineSize = prefs.getInt(
					this.getString(R.string.prefs_temp_saved_text_key), null);
			
			if (tempTweetText != null) {
				
				CONS.UIS_Twt.et_Twt.setText(tempTweetText);
				
				CONS.UIS_Twt.et_Twt.setSelection(
						CONS.UIS_Twt.et_Twt.getText().toString().length());
				
				// Log
				log_msg = "temp text => set";

				Log.d("["
						+ "TwtActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
			} else {
				
				// Log
				log_msg = "tempTweetText => null";

				Log.d("["
						+ "TwtActv.java : "
						+ +Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ " : "
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", log_msg);
				
			}
			
		} else {//if (saveText == true)
			
			// Log
			log_msg = "saveText => false";

			Log.d("["
					+ "TwtActv.java : "
					+ +Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + " : "
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", log_msg);
		}//if (saveText == true)
		
	}//private void _Setup_TempText()

	private void _Setup_UIs__GridView() {
		// TODO Auto-generated method stub
		Methods_twt.setup_GridView(this);
	}

	/*********************************
	 * @see GridView => _Setup_UIs__GridView()
	 *********************************/
	private void _Setup_Listeners() {
		// TODO Auto-generated method stub
		CONS.UIS_Twt.btn_Back.setTag(Tags.ButtonTags.BACK);
		CONS.UIS_Twt.btn_Back.setOnClickListener(new BOCL(this));
		
		CONS.UIS_Twt.btn_Twt.setTag(Tags.ButtonTags.SEND_TWEET);
		CONS.UIS_Twt.btn_Twt.setOnClickListener(new BOCL(this));
		
		CONS.UIS_Twt.btn_Pattern.setTag(Tags.ButtonTags.PATTERN);
		CONS.UIS_Twt.btn_Pattern.setOnClickListener(new BOCL(this));
		
	}//private void _Setup_Listeners()

	private void _Setup_UIs() {
		
		CONS.UIS_Twt.btn_Back = (Button) findViewById(R.id.actv_twt_bt_back);
		CONS.UIS_Twt.btn_Twt = (Button) findViewById(R.id.actv_twt_bt_tweet);
		CONS.UIS_Twt.btn_Pattern =
						(Button) findViewById(R.id.actv_twt_bt_pattern);
		
		CONS.UIS_Twt.et_Twt =
				(EditText) findViewById(R.id.actv_twt_et_content);
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	
}
