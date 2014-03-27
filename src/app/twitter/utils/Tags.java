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
		
		dlg_Delete_PatternsItem_OK,
		
		// dlg_filter_timeline
		dlg_Filter_Timeline_OK, dlg_Filter_Timeline_Reset,
		
	}//public static enum DialogTags

	public static enum DialogItemTags {
		// dlg_db_admin.xml
		Admin_LV,
		
		GV_Tweet,
		
		GV_Filter_Timeline,
		
		AdminPatterns_LV,
		
		AdminPatterns_Item_LV,
		
	}//public static enum DialogItemTags

}
