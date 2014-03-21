package app.twitter.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ListView;
import app.twitter.R;
import app.twitter.adapters.Adp_Admin;
import app.twitter.listeners.dialogs.DB_OCL;
import app.twitter.listeners.dialogs.DB_OTL;
import app.twitter.listeners.dialogs.DOI_CL;
import app.twitter.utils.Tags;

public class Methods_Dlg {

	public static void
	dlg_DbAdmin(Activity actv) {
		// TODO Auto-generated method stub
		Dialog dlg = Methods_Dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_list_cancel, 
				R.string.dlg_admin_title,
				
				R.id.dlg_tmpl_list_cancel_bt_cancel, 
				Tags.DialogTags.dlg_generic_dismiss);

		String[] choices = {
				actv.getString(R.string.dlg_admin_exec_sql),
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		Collections.sort(list);

		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		Adp_Admin adapter = new Adp_Admin(
				actv,
//				R.layout.dlg_db_admin,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				list,
				dlg
				);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//						actv,
////				R.layout.dlg_db_admin,
//						R.layout.list_row_simple_1,
////				android.R.layout.simple_list_item_1,
//						list
//						);

		ListView lv = (ListView) dlg.findViewById(R.id.dlg_tmpl_list_cancel_lv);
		
		lv.setAdapter(adapter);

		/*----------------------------
		 * 5. Set listener to list
			----------------------------*/
		lv.setTag(Tags.DialogItemTags.Admin_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg));
//		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg.show();
		
	
	}//dlg_DbAdmin(Activity actv)

	public static Dialog
	dlg_Template_Cancel
	(Activity actv,
			int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
//		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()

}
