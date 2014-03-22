package app.twitter.listeners.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import app.twitter.R;
import app.twitter.utils.CONS;
import app.twitter.utils.M_sql;
import app.twitter.utils.Methods;
import app.twitter.utils.Tags;

public class DOI_CL implements OnItemClickListener {

	Activity actv;
	Dialog dlg1;
	Dialog dlg2;

	//
	Vibrator vib;
	

	
	public DOI_CL(Activity actv, Dialog dlg1) {
		// 
		this.actv = actv;
		this.dlg1 = dlg1;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)
	
	@Override
	public void onItemClick
	(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
//		
		// Log
		String log_msg = "tag => " + tag.toString();

		Log.d("[" + "DOI_CL.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		vib.vibrate(CONS.Others.VIB_LENGTH);
		
		switch (tag) {
		
		case Admin_LV://------------------------------------
			
			String item = (String) parent.getItemAtPosition(position);
			
			_case_Admin_LV(item);
			
			break;//case Admin_LV
		
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick

	private void _case_Admin_LV(String item) {
		// TODO Auto-generated method stub
		
		if (item.equals(actv.getString(
				R.string.dlg_admin_exec_sql))) {
			
//			String sql = CONS.Sqls._CreateTable_Patterns_20140321_113430;
			String sql = CONS.Sqls._InsertDummyData_Patterns_20140322_174245;
			
			boolean res = M_sql.exec_Sql(
							actv,
							CONS.DB.dbName_twt,
							CONS.DB.tname_Patterns,
							sql);

			// debug
			if (res == true) {
				
				String toa_msg = "Exec sql => successful";
				Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
				
			} else {//if (res == true)
				
				String toa_msg = "Exec sql => failed";
				Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
				
			}//if (res == true)
			
			
		} else if (item.equals(actv.getString(
				R.string.dlg_admin_backup_db))) {//if (item.equals(actv.getString(R.string.dl)))
			
			_case_Admin_LV__BackupDB();
			
		} else {//if (item.equals(actv.getString(R.string.dl)))
			
			// debug
			String toa_msg = "Other";
			Toast.makeText(actv, toa_msg, Toast.LENGTH_SHORT).show();
			
		}//if (item.equals(actv.getString(R.string.dl)))
		
	}//private void _case_Admin_LV(String item)

	private void _case_Admin_LV__BackupDB() {
		// TODO Auto-generated method stub
		int res = Methods.backupDb(
					actv,
					CONS.DB.dbName_twt,
					CONS.DB.dpath_Db_Backup);
		
	}

}//public class DOI_CL implements OnItemClickListener
