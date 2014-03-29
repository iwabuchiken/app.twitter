package app.twitter.listeners.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.twitter.R;
import app.twitter.utils.CONS;
import app.twitter.utils.M_sql;
import app.twitter.utils.Methods;
import app.twitter.utils.Methods_Dlg;
import app.twitter.utils.Methods_twt;
import app.twitter.utils.Tags;

public class DOI_CL implements OnItemClickListener {

	Activity actv;
	Dialog dlg1;
	Dialog dlg2;

	String item;
	
	//
	Vibrator vib;
	

	
	public DOI_CL(Activity actv, Dialog dlg1) {
		// 
		this.actv = actv;
		this.dlg1 = dlg1;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)
	
	public DOI_CL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DOI_CL(Activity actv, Dialog dlg1, String item) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		this.dlg1 = dlg1;
		
		this.item = item;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

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
		
		case GV_Tweet://------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			_case_Tweet_GV(item);
			
			break;//case Admin_LV
			
		case AdminPatterns_LV://------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			_case_AdminPatterns_LV(item);
			
			break;//case Admin_LV
			
		case AdminPatterns_Item_LV://------------------------------------
			
			String listItem = (String) parent.getItemAtPosition(position);
			
			_case_AdminPatterns_Item_LV(listItem);
			
			break;//case Admin_LV
			
		case GV_Filter_Timeline://------------------------------------
			
			listItem = (String) parent.getItemAtPosition(position);
			
			_case_GV_Filter_Timeline(listItem);
			
			break;//case Admin_LV
			
		case HoriLV_TwtActv://------------------------------------
			
//			// Log
//			log_msg = "v => " + v.getClass().getName();
//			
//			Log.d("["
//					+ "DOI_CL.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//							+ Thread.currentThread().getStackTrace()[2].getMethodName()
//							+ "]", log_msg);
//			
//			TextView tv = (TextView) v;
//			
//			// Log
//			log_msg = "text => " + tv.getText().toString();
//
//			Log.d("["
//					+ "DOI_CL.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//			// Log
//			log_msg = "parent => " + parent.getClass().toString();
//
//			Log.d("["
//					+ "DOI_CL.java : "
//					+ +Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + " : "
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", log_msg);
//			
//			listItem = (String) parent.getItemAtPosition(position);
			
			_case_HoriLV_TwtActv(v);
//			_case_HoriLV_TwtActv(listItem);
			
			break;//case Admin_LV
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick

	private void _case_HoriLV_TwtActv(View v) {
		// TODO Auto-generated method stub
		
		TextView tv = (TextView) v;
		
		String item = tv.getText().toString();
		
		String text = CONS.UIS_Twt.et_Twt.getText().toString();
		
		text += item;
		
		CONS.UIS_Twt.et_Twt.setText(text);
		
		/*********************************
		 * Set: Cursor position
		 *********************************/
		if (item.equals("「」")
				|| item.equals("『』")
				|| item.equals("（）")
				|| item.equals("()")
				) {
			
			CONS.UIS_Twt.et_Twt.setSelection(
					CONS.UIS_Twt.et_Twt.getText().toString().length() - 1);
			
		} else {//if (item.equals("「」"))
			
			CONS.UIS_Twt.et_Twt.setSelection(
					CONS.UIS_Twt.et_Twt.getText().toString().length());
			
		}//if (item.equals("「」"))
		
		
//		// Log
//		String log_msg = "item => " + v;
//
//		Log.d("[" + "DOI_CL.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ " : "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", log_msg);
		
	}

	private void _case_GV_Filter_Timeline(String listItem) {
		// TODO Auto-generated method stub
		/*********************************
		 * Get: EditText
		 * Set: Text
		 *********************************/
		EditText et = (EditText) dlg1.findViewById(R.id.dlg_filter_timeline_et_content);
		
		String content = "";
		
		if (et.getText() != null) {
//		if (et.getText() != null
//				&& et.getText().toString().equals("")) {
//			
//			return;
//			
//		} else if (et.getText() != null) {
			
			content += et.getText().toString();
			
		}
		
		et.setText(content + listItem);
		
		et.setSelection(et.getText().toString().length());
		
	}

	private void
	_case_AdminPatterns_LV(String item) {
		// TODO Auto-generated method stub
		// Log
		String log_msg = "item => " + item;

		Log.d("[" + "DOI_CL.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		if (item.equals(actv.getString(
				R.string.generic_tv_register))) {
			
			Methods_Dlg.dlg_RegisterPatterns(actv, dlg1);
			
		} else if (item.equals(actv.getString(
				R.string.generic_tv_edit))) {
		
//			Methods_Dlg.dlg_EditPatterns(actv, dlg1);
			
		} else if (item.equals(actv.getString(
				R.string.generic_tv_delete))) {
			
//			Methods_Dlg.dlg_DeletePatterns(actv, dlg1);
			
		}
			
		
	}//_case_AdminPatterns_LV(String item)
	
	private void
	_case_AdminPatterns_Item_LV(String listItem) {
		// TODO Auto-generated method stub
		// Log
		String log_msg = "item => " + listItem;
		
		Log.d("[" + "DOI_CL.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ " : "
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", log_msg);
		
		if (listItem.equals(actv.getString(
				R.string.generic_tv_edit))) {
			
//			Methods_Dlg.dlg_EditPatterns(actv, dlg1);
			
		} else if (listItem.equals(actv.getString(
				R.string.generic_tv_delete))) {
			
			String pattItem = this.item;
			
			Methods_Dlg.dlg_Conf_Delete_PatternsItem(actv, dlg1, pattItem);
			
		}
		
		
	}//_case_AdminPatterns_LV(String item)

	private void _case_Tweet_GV(String item) {
		// TODO Auto-generated method stub
		Methods_twt.add_Pattern2Text(actv, item);
	}

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
