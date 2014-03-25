package app.twitter.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.twitter.R;
import app.twitter.adapters.Adp_Admin;
import app.twitter.listeners.dialogs.DB_OCL;
import app.twitter.listeners.dialogs.DB_OTL;
import app.twitter.listeners.dialogs.DOI_CL;
import app.twitter.utils.Tags.DialogTags;

public class Methods_Dlg {

	public static void
	dlg_Admin(Activity actv) {
		// TODO Auto-generated method stub
		Dialog dlg = Methods_Dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_list_cancel, 
				R.string.dlg_admin_title,
				
				R.id.dlg_tmpl_list_cancel_bt_cancel, 
				Tags.DialogTags.dlg_generic_dismiss);

		String[] choices = {
				actv.getString(R.string.dlg_admin_exec_sql),
				actv.getString(R.string.dlg_admin_backup_db),
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
	
	public static Dialog
	dlg_Template_Cancel
	(Activity actv,
			int layoutId, String title,
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
		dlg.setTitle(title);
		
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

	public static Dialog
	dlg_Template_OkCancel_1Dialog
	(Activity actv,
			int layoutId, int titleStringId,
			
			int okButtonId, int cancelButtonId,
			DialogTags okTag, DialogTags cancelTag,
			
			Dialog dlg1, String message, String item) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, item));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
	
	}//public static Dialog dlg_template_okCancel()

	public static void
	dlg_Patterns(Activity actv) {
		/*----------------------------
		 * memo
			----------------------------*/
		Dialog dlg = Methods_Dlg.dlg_Template_Cancel(
						actv, R.layout.dlg_admin_patterns, 
						R.string.dlg_admin_patterns_title, 
						R.id.dlg_admin_patterns_bt_cancel, 
						Tags.DialogTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. Prep => List
			----------------------------*/
		String[] choices = {
				actv.getString(R.string.generic_tv_register),
//				actv.getString(R.string.generic_tv_edit),
//				actv.getString(R.string.generic_tv_delete)
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);

		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_admin_patterns_lv);
		
		lv.setAdapter(adapter);

		/*----------------------------
		 * 5. Set listener to list
			----------------------------*/
		lv.setTag(Tags.DialogItemTags.AdminPatterns_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg.show();
		
	}//dlg_Patterns(Activity actv)

	public static void
	dlg_RegisterPatterns(Activity actv, Dialog dlg) {
		/*----------------------------
		 * Steps
		 * 1. Dialog
		 * 9. Show
			----------------------------*/
//		Dialog dlg2 = dlg_template_okCancel(
//					actv, , ,
//				, , 
//				, );
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_register_patterns);
		
		// Title
		dlg2.setTitle(R.string.dlg_register_patterns_title);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_register_patterns_btn_create);
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_register_patterns_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.dlg_register_patterns_register);
		btn_cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg, dlg2));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg, dlg2));
		
		/*----------------------------
		 * 9. Show
			----------------------------*/
		dlg2.show();
		
	}//dlg_RegisterPatterns(Activity actv, Dialog dlg)

	public static void
	dlg_RegisterPatterns_isInputEmpty
	(Activity actv, Dialog dlg1, Dialog dlg2) {
		/*----------------------------
		 * Steps
		 * 1. Get views
		 * 2. Prepare data
		 * 3. Register data
		 * 4. Dismiss dialog
			----------------------------*/
		// Get views
		EditText et_word = (EditText) dlg2.findViewById(
								R.id.dlg_register_patterns_et_word);
		
		if (et_word.getText().length() == 0) {
			// debug
			Toast.makeText(actv, "語句を入れてください", 3000).show();
			
			return;
		}// else {//if (et_column_name.getText().length() == 0)
		
		/*----------------------------
		 * 2. Prepare data
			----------------------------*/
		String word = et_word.getText().toString();
		
		/*----------------------------
		 * 3. Register data
			----------------------------*/
		boolean result = Methods_twt
						.insertData_Patterns(
								actv,
								word);
		
		/*----------------------------
		 * 4. Dismiss dialog
			----------------------------*/
		if (result == true) {
		
			/*********************************
			 * Refresh GridView
			 *********************************/
			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_twt);
			
			SQLiteDatabase rdb = dbu.getReadableDatabase();
			
			CONS.TwitterData.patternsList.clear();
			
			CONS.TwitterData.patternsList.addAll(
					Methods_twt._setup_GridView__GetPatternsList(actv, rdb));
					
			/*********************************
			 * Sort: Patterns list
			 *********************************/
			Methods_twt.sort_PatternsList(CONS.TwitterData.patternsList);

			/*********************************
			 * Notify: Adapter
			 *********************************/
			CONS.TwitterData.adp_Patterns.notifyDataSetChanged();
			
			
			/*********************************
			 * Dialogues: Dismiss
			 *********************************/
			dlg2.dismiss();
			dlg1.dismiss();
			
			// debug
			Toast.makeText(actv, "定型句を保管しました", Toast.LENGTH_LONG).show();
			
		} else {//if (result == true)

			dlg2.dismiss();
			
			// debug
			Toast.makeText(actv, "定型句を保管できませんでした", 3000).show();

		}//if (result == true)
		
		
	}//dlg_register_patterns_isInputEmpty

	public static void
	dlg_AdminPatterns_Item(Activity actv, String pattItem) {
		// TODO Auto-generated method stub

		Dialog dlg = Methods_Dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_list_cancel, 
				actv.getString(R.string.dlg_admin_patterns_item_title)
					+ ": " + pattItem,
				
				R.id.dlg_tmpl_list_cancel_bt_cancel, 
				Tags.DialogTags.dlg_generic_dismiss);

		String[] choices = {
				actv.getString(R.string.generic_tv_edit),
				actv.getString(R.string.generic_tv_delete),
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String choice : choices) {
			
			list.add(choice);
			
		}
		
		Collections.sort(list);

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
		lv.setTag(Tags.DialogItemTags.AdminPatterns_Item_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg, pattItem));
//		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg.show();
		
	}//dlg_AdminPatternsItem(Activity actv, String item)

	public static void
	dlg_Conf_Delete_PatternsItem
	(Activity actv, Dialog dlg1, String pattItem) {
		// TODO Auto-generated method stub
		/*********************************
		 * Dialog: Second
		 *********************************/
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_tmpl_ok_cancel);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		/*********************************
		 * Set: Item name, message
		 *********************************/
		TextView tv_Item = (TextView) dlg2.findViewById(R.id.dlg_tmpl_ok_cancel_tv_item);
		
		tv_Item.setText(pattItem);
		
		TextView tv_Msg =
				(TextView) dlg2.findViewById(R.id.dlg_tmpl_ok_cancel_tv_message);
		
		tv_Msg.setText(actv.getString(R.string.generic_tv_confirm_delete_en));
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_tmpl_ok_cancel_btn_ok);
		Button btn_cancel =
				(Button) dlg2.findViewById(R.id.dlg_tmpl_ok_cancel_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.dlg_Delete_PatternsItem_OK);
		btn_cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
//		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, pattItem, dlg2));
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, pattItem));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));

		/*********************************
		 * Show: dlg2
		 *********************************/
		dlg2.show();
		
//		Dialog dlg2 = Methods_Dlg
//				.dlg_Template_OkCancel_1Dialog(
//						actv,
//						R.layout.dlg_tmpl_ok_cancel,
//						R.string.generic_tv_confirm,	// Title
//						
//						R.id.dlg_tmpl_ok_cancel_btn_ok,
//						R.id.dlg_tmpl_ok_cancel_btn_cancel,
//						
//						okTag,
//						Tags.DialogTags.dlg_generic_dismiss_second_dialog,
//						
//						dlg1, message, item)
		
		
	}//dlg_Conf_Delete_PatternsItem

}//public class Methods_Dlg
