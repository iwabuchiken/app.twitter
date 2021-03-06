package app.twitter.specials;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.twitter.R;

public class HorizontalListViewDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.listviewdemo);
       
        HorizontalListView listview = (HorizontalListView) findViewById(R.id.actv_twt_lv_horizontal);
        listview.setAdapter(mAdapter);
       
    }
    
    private static String[] dataObjects = new String[]{ "Text #1",
        "Text #2",
        "Text #3","Text #4","Text #5","Text #6","Text #7","Text #8","Text #9","Text #10" }; 
    
    private BaseAdapter mAdapter = new BaseAdapter() {

        private OnClickListener mOnButtonClicked = new OnClickListener() {
           
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HorizontalListViewDemo.this);
                builder.setMessage("hello from " + v);
                builder.setPositiveButton("Cool", null);
                builder.show();
               
            }
        };

        @Override
        public int getCount() {
            return dataObjects.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, null);
            TextView title = (TextView) retval.findViewById(R.id.title);
            Button button = (Button) retval.findViewById(R.id.clickbutton);
            button.setOnClickListener(mOnButtonClicked);
            title.setText(dataObjects[position]);
           
            return retval;
        }
       
    };

}