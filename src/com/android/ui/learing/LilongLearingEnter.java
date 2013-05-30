package com.android.ui.learing;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class LilongLearingEnter extends Activity {
	private ListView mLearingList;
	private LearingListAdapter mAdapter;
	private LayoutInflater mInflater;
	private ArrayList<String> mLearingClass = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lilong_learing_layout);
		mInflater = this.getLayoutInflater();
		
		initClass();
		mAdapter = new LearingListAdapter();
		mLearingList = (ListView) findViewById(R.id.learing_list);
		mLearingList.setAdapter(mAdapter);
		mLearingList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					Class _des_class = Class.forName("com.android.ui.learing." + mLearingClass.get(position));
					Intent intent = new Intent(LilongLearingEnter.this ,_des_class);
					LilongLearingEnter.this.startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private void initClass(){
		mLearingClass.add("ActionBarLearing");
		mLearingClass.add("AsyncLoadImage");
		mLearingClass.add("DecodeImage");
	}
	
	
	class LearingListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mLearingClass.size();
		}

		@Override
		public Object getItem(int position) {
			return mLearingClass.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
                convertView = mInflater.inflate(R.layout.lilong_learing_row_layout, parent, false);
            }
            TextView view = (TextView) convertView.findViewById(R.id.learing_item);
            view.setText(mLearingClass.get(position));
            return convertView;
		}
	}
}
