package com.android.ui.learing;

import com.android.ui.learing.*;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActionBarLearing extends Activity{
	private Context mContext;
	private LayoutInflater mInflater;
	private static final ActionItem[] sClusterItems = new ActionItem[] {
		new ActionItem(1,"lilong_1",3),
		new ActionItem(2,"lilong_2",4),
		new ActionItem(4,"lilong_4",1),
		new ActionItem(8,"lilong_8",0),
		new ActionItem(16,"lilong_16",2)
    };
  

    private static class ActionItem {
        public int action;
        public String spinnerTitle;
        public int clusterBy;

        public ActionItem(int action, String title ,int clusterBy) {
        	 this.action = action;
             this.spinnerTitle = spinnerTitle;
             this.clusterBy = clusterBy;
        }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* ---------(5 : Begin)---------- */
//		MenuItem mi1 = menu.add(1 , 0 , 1 ,"lilong");
//		MenuItem mi2 = menu.add(1 , 1 , 0 ,"lushan");
//		mi1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//		mi2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//		mi1.setIcon(R.drawable.ic_launcher);
		/* ---------(5 : End)---------- */
		
		/* ---------(6 : Begin)---------- */
		getMenuInflater().inflate(R.menu.action_bar_actions, menu);
		/* ---------(6 : End)---------- */
		/* ---------(7 : Begin)---------- */
		/* ---------(7 : End)---------- */
		/* ---------(8 : Begin)---------- */
		/* ---------(8 : End)---------- */
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* ---------(5 : Begin)---------- */
//		if(item.getItemId() == 0){
//			Toast.makeText(ActionBarLearing.this, "lilong was select...", Toast.LENGTH_SHORT).show();
//		}else{
//			Toast.makeText(ActionBarLearing.this, "lushan was select...", Toast.LENGTH_SHORT).show();
//		}
		/* ---------(5 : End)---------- */
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		mInflater = getLayoutInflater();
		setContentView(R.layout.action_bar_layout);
		
		int opt_flag = ActionBar.DISPLAY_HOME_AS_UP ^ ActionBar.DISPLAY_SHOW_HOME;
		
		ActionBar actionbar = getActionBar();
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.color.action_bar_bg));
		
		
		/* ---------(1 : Begin)---------- */
//		actionbar.setDisplayOptions(opt_flag & ActionBar.DISPLAY_SHOW_HOME);//保留HOME,其他全部消失
//		actionbar.setDisplayOptions(opt_flag | ActionBar.DISPLAY_SHOW_TITLE);//保留已有,并增加TITLE
		/* ---------(1 : End)---------- */
		
		/* ---------(2 : Begin)---------- */
//		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		actionbar.setCustomView(R.layout.action_bar_custom_layout);
		/* ---------(2 : End)---------- */
		
		
		/* ---------(3 : Begin)---------- */
//		LSpinnerAdapter lsp = new LSpinnerAdapter();
//		actionbar.setListNavigationCallbacks(lsp,new OnNavigationListener(){
//
//			@Override
//			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//		});
//		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		 for (int i = 0, n = sClusterItems.length; i < n; i++) {
//            actionbar.setSelectedNavigationItem(sClusterItems[i].clusterBy);
//	    }
		/* ---------(3 : End)---------- */
		
		/* ---------(4 : Begin)---------- */
//		LTabListener ltl = new LTabListener();
//		Tab tab1 = actionbar.newTab();
//		tab1.setText("Lushan_1");
//		tab1.setTabListener(ltl);
//		
//		Tab tab2 = actionbar.newTab();
//		tab2.setText("Lushan_2");
//		tab2.setTabListener(ltl);
//		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		actionbar.addTab(tab1);
//		actionbar.addTab(tab2 ,true);
		/* ---------(4 : End)---------- */
		
		
	}
	
	class LSpinnerAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return sClusterItems.length;
		}

		@Override
		public Object getItem(int position) {
			return sClusterItems[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return sClusterItems[position].action;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
                convertView = mInflater.inflate(R.layout.action_bar_test, parent, false);
            }
            TextView view = (TextView) convertView;
            view.setText(sClusterItems[position].spinnerTitle);
            return convertView;
		}
	}
	
	class LTabListener implements TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			
		}
	}
}
