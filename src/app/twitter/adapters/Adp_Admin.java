package app.twitter.adapters;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.twitter.R;
import app.twitter.listeners.List_OTL;
import app.twitter.utils.Tags;

public class Adp_Admin extends ArrayAdapter<String> {

	// Context
	Context con;

	Dialog dlg;
	
	// Inflater
	LayoutInflater inflater;

	public Adp_Admin
	(Context con, int resourceId, List<String> list) {
		
		super(con, resourceId, list);
		
		// Context
		this.con = con;

		// Inflater
		inflater = 
				(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}//public MainListAdapter

	public Adp_Admin
	(Context con, int resourceId, List<String> list,
			Dialog dlg) {
		// TODO Auto-generated constructor stub
		super(con, resourceId, list);
		
		// Context
		this.con = con;

		this.dlg	= dlg;
		
		// Inflater
		inflater = 
			(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*********************************
		 * 1. Setup view
		 * 2. Get item
		 * 
		 * 3. Get view => TextView
		 * 4. Modify text => "list.txt"
		 * 
		 * 5. Set text
		 * 
		 * 6. Set listener => On touch
		 *********************************/
//		// Log
//		Log.d("MainListAdapter.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "getView()");
		
    	View v = null;
    	
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_simple_1, null);

		}//if (convertView != null)

//    	v = inflater.inflate(R.layout.list_row_main_list, null);
    	
    	TextView tv = (TextView) v.findViewById(R.id.list_row_simple_1_tv);
    	
    	/*********************************
		 * 5. Set text
		 *********************************/
    	String name = getItem(position);
    	
    	tv.setText(name);
    	
    	/*********************************
		 * 6. Set listener => On touch
		 *********************************/
    	v.setTag(Tags.ListTags.admin_adapter);
    	
    	v.setOnTouchListener(new List_OTL((Activity) con));
    	
//    	v.setOnClickListener(new List_OCL((Activity) con), dlg));
//    	v.setOnClickListener(new List_OCL((Activity)con, dlg));
    	
    	return v;
//		return super.getView(position, convertView, parent);
	}//public View getView

	
}//public class MainListAdapter<T>
