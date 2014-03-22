package app.twitter;

import android.app.Activity;
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
		
		this.finish();
		
	}

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
		
		_Setup_UIs();
		
		_Setup_UIs__GridView();
		
		_Setup_Listeners();
		
	}//protected void onStart()

	private void _Setup_UIs__GridView() {
		// TODO Auto-generated method stub
		Methods_twt.setup_GridView(this);
	}

	private void _Setup_Listeners() {
		// TODO Auto-generated method stub
		CONS.UIS_Twt.btn_Back.setTag(Tags.ButtonTags.BACK);
		CONS.UIS_Twt.btn_Back.setOnClickListener(new BOCL(this));
		
		CONS.UIS_Twt.btn_Twt.setTag(Tags.ButtonTags.SEND_TWEET);
		CONS.UIS_Twt.btn_Twt.setOnClickListener(new BOCL(this));
		
	}//private void _Setup_Listeners()

	private void _Setup_UIs() {
		
		CONS.UIS_Twt.btn_Back = (Button) findViewById(R.id.dlg_add_memos_bt_back);
		CONS.UIS_Twt.btn_Twt = (Button) findViewById(R.id.dlg_add_memos_bt_tweet);
		CONS.UIS_Twt.btn_Pattern =
						(Button) findViewById(R.id.dlg_add_memos_bt_pattern);
		
		CONS.UIS_Twt.et_Twt =
				(EditText) findViewById(R.id.dlg_add_memos_et_content);
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	
}
