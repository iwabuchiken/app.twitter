package app.twitter.utils;

public class Tags {

	public static enum ButtonTags {
		// actv_main
		LOGIN, LOGOUT, TIMELINE, TWEET,
		
		// actv_twt
		BACK, SEND_TWEET, PATTERN,
		
	}
	
	public static enum ListTags {
		// MainActivity.java
		actv_main_lv,
		
		// Main
		admin_adapter,
		
		actv_main_lv_locs,
		
	}//public static enum ListTags

	public static enum DialogTags {
		// Generics
		dlg_generic_dismiss,
		
		dlg_generic_dismiss_second_dialog,
		
		dlg_generic_dismiss_third_dialog,
		
		dlg_edit_locs_btn_ok,
		
		dlg_register_patterns_register,
		
	}//public static enum DialogTags

	public static enum DialogItemTags {
		// dlg_db_admin.xml
		Admin_LV,
		
		Tweet_GV,
		
		AdminPatterns_LV,
		
	}//public static enum DialogItemTags

}
