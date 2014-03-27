package app.twitter.listeners;

import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import app.twitter.utils.CONS;
import app.twitter.utils.Methods_Dlg;
import app.twitter.utils.Tags;

public class ILCL implements OnItemLongClickListener {

	Activity actv;
	static Vibrator vib;
	
	public ILCL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	@Override
	public boolean onItemLongClick
	(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
		
		vib.vibrate(CONS.Others.VIB_LENGTH);
		
		switch (tag) {
		
		case GV_Tweet://--------------------------------------
			
			case_Tweet_GV(parent, position);
			
			break;// case actv_tn_lv
		
		}
		
		return true;
		
	}//public boolean onItemLongClick

	private void
	case_Tweet_GV(AdapterView<?> parent, int position) {
		// TODO Auto-generated method stub
		String pattItem = (String) parent.getItemAtPosition(position);

		Methods_Dlg.dlg_AdminPatterns_Item(actv, pattItem);
		
//		// Log
//		String log_msg = "item => " + item;
//
//		Log.d("[" + "ILCL.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ " : "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", log_msg);
	}//case_Tweet_GV(AdapterView<?> parent, int position)

}
