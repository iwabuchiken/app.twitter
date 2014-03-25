package app.twitter.listeners.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import app.twitter.utils.CONS;
import app.twitter.utils.Methods_Dlg;
import app.twitter.utils.Methods_twt;
import app.twitter.utils.Tags;

//DB=DialogButton
public class DB_OCL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg1;
	Dialog dlg2;		//=> Used in dlg_input_empty_btn_XXX
	Dialog dlg3;

	AdapterView<?> parent;
	int position;
	String original_Memo;
	
	String item;	//=> Used in: Delete_PatternsItem
	
	//
	Vibrator vib;
	
	public DB_OCL(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg1 = dlg;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2, Dialog dlg3) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		this.dlg3 = dlg3;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DB_OCL
	(Activity actv, Dialog dlg1, Dialog dlg2, String item) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		
		this.item	= item;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public void onClick(View v) {
		//
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();

		// Log
		Log.d("DialogButtonOnClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag_name.name()=" + tag_name.name());
		
		vib.vibrate(CONS.Others.VIB_LENGTH);
		
		//
		switch (tag_name) {
		
		case dlg_generic_dismiss://------------------------------------------------
			
			dlg1.dismiss();
			
			break;

		case dlg_generic_dismiss_second_dialog: // ----------------------------------------------------
			
			dlg2.dismiss();
			
			break;// case dlg_generic_dismiss_second_dialog

		case dlg_generic_dismiss_third_dialog://------------------------------------------------
			
			dlg3.dismiss();
			
			break;

		case dlg_register_patterns_register://------------------------------------------------
			
			case_Dlg_RegisterPatterns_Register();
			
			break;
			
		case dlg_Delete_PatternsItem_OK://------------------------------------------------
			
			case_dlg_Delete_PatternsItem_OK();
			
			break;
			
		default: //----------------------------------------------------
			break;
			
		}//switch (tag_name)
		
	}//public void onClick(View v)

	private void case_dlg_Delete_PatternsItem_OK() {
		// TODO Auto-generated method stub
		String pattItem = this.item;
		
		Methods_twt.delete_PatternsItem(actv, dlg1, dlg2, pattItem);
		
	}

	private void case_Dlg_RegisterPatterns_Register() {
		// TODO Auto-generated method stub
		
		Methods_Dlg
			.dlg_RegisterPatterns_isInputEmpty(actv, dlg1, dlg2);
		
	}

}//DialogButtonOnClickListener
