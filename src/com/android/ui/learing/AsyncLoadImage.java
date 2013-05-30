package com.android.ui.learing;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.android.ui.learing.utils.CustomMenu;
import com.android.ui.learing.utils.CustomMenu.DropDownMenu;
import com.android.ui.learing.utils.CLog;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class AsyncLoadImage extends Activity implements GridView.MultiChoiceModeListener{
	private GridView vRootLayout;
	private LAsyncLoader mLoader;
	private lAdapter mAdapter;
	private AsyncImageLoader mImageLoader;
	private boolean isSelectMode;
	private ActionMode mActionMode;
	private DropDownMenu mSelectionMenu;
	
	private class LImageData{
		int file_id;
		String file_path;
		
		public LImageData(int id ,String path){
			this.file_id = id;
			this.file_path = path;
		}
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		CLog.i("onCreateActionMode ... ");
		mActionMode = mode;
		getMenuInflater().inflate(R.menu.async_load_img_contextual_action, menu);
		CustomMenu customMenu = new CustomMenu(AsyncLoadImage.this);
        View customView = LayoutInflater.from(AsyncLoadImage.this).inflate(R.layout.async_load_image_action_mode, null);
        mActionMode.setCustomView(customView);
        mSelectionMenu = customMenu.addDropDownMenu(
                (Button) customView.findViewById(R.id.selection_menu),R.menu.selection);
        String format = getResources().getQuantityString(R.plurals.number_of_items_selected, 0);
		mSelectionMenu.setTitle(format);
        customMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		CLog.i("onPrepareActionMode ... ");
		return true;
	}
	
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		CLog.i("onActionItemClicked ... ");
		return true;
	}
	
	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,long id, boolean checked) {
		LImageData lid = (LImageData) mAdapter.getItem(position);
		CLog.i("onItemCheckedStateChanged : " + checked + " : " + lid.file_path);
		
		String format = getResources().getQuantityString(R.plurals.number_of_items_selected, 0);
		if(mSelectionMenu != null){
			mSelectionMenu.setTitle(String.format(format, 5));
		}
	}
	
	@Override
	public void onDestroyActionMode(ActionMode mode) {
		CLog.i("onDestroyActionMode ... ");
		isSelectMode = true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.async_load_image_layout);
		ActionBar actionbar = getActionBar();
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.color.action_bar_bg));
		
		vRootLayout = (GridView) findViewById(R.id.async_loader_root_layout);
		mAdapter = new lAdapter();
		vRootLayout.setAdapter(mAdapter);
		vRootLayout.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		vRootLayout.setMultiChoiceModeListener(this);
		
		mLoader = new LAsyncLoader();
		mImageLoader = new AsyncImageLoader(this);
		mLoader.execute("Begin select image's id from database.");
	}
	
	class lAdapter extends BaseAdapter{
		ArrayList<LImageData> mIMList = new ArrayList<LImageData>();
		
		public void addList(ArrayList<LImageData> list){
			mIMList.addAll(list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mIMList.size();
		}

		@Override
		public Object getItem(int position) {
			return mIMList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LImageData file = mIMList.get(position);
			if(convertView == null){
				convertView = View.inflate(AsyncLoadImage.this, R.layout.async_load_image_item_layout, null);
			}
			convertView.setTag(file);
			ImageView im_photo = (ImageView) convertView.findViewById(R.id.image_item);
	    	
			im_photo.setTag(file.file_path);
			mImageLoader.loadBitmap(file, new ImageCallback(){

				@Override
				public void imageLoaded(Bitmap bitmap, LImageData file) {
					if(bitmap == null) return;
					ImageView imv = (ImageView) vRootLayout.findViewWithTag(file.file_path);
					if(imv != null){
						imv.setImageBitmap(bitmap);
					}
				}
			});
			
			return convertView;
		}
	}
	
	class LAsyncLoader extends AsyncTask<String, String, ArrayList<LImageData>>{

		@Override
		protected ArrayList<LImageData> doInBackground(String... params) {
			String param = params[0];
			CLog.i("onPostExecute : param : " + param + "\n----thread : " + Thread.currentThread().getName());
			
			ArrayList<LImageData> result = null;
			Cursor _cursor = AsyncLoadImage.this.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					null, null, null, "date_added DESC");
			
			int _child_totle_count = _cursor.getCount();
			if(_child_totle_count > 0){
				result = new ArrayList<LImageData>();
				for(int index = 0 ; index < _child_totle_count ; index++){
					_cursor.moveToNext();
					int file_id = _cursor.getInt(_cursor.getColumnIndex(MediaStore.Images.Media._ID));
					String file_path = _cursor.getString(_cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					LImageData lid = new LImageData(file_id ,file_path);
					result.add(lid);
				}
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<LImageData> result) {
			CLog.i("doInBackground : thread : " + Thread.currentThread().getName());
			super.onPostExecute(result);
			mAdapter.addList(result);
		}
	}
	
	interface ImageCallback {
		public void imageLoaded(Bitmap imageBitmap, LImageData file);
	}
	
	class AsyncImageLoader {
		private Context mContext;
		private HashMap<String, WeakReference<Bitmap>> mPhotoBitmapCache = null;
		private ExecutorService executorService;
		
		public AsyncImageLoader(Context context){
			mContext = context;
			mPhotoBitmapCache = new HashMap<String, WeakReference<Bitmap>>();
			executorService = Executors.newFixedThreadPool(5);
		}
		
		
		public void loadBitmap(LImageData file ,ImageCallback callback){
			PhotoBitmapGenerater pbg = new PhotoBitmapGenerater(file ,callback);
	        executorService.execute(pbg);
		}
		
		class PhotoBitmapGenerater implements Runnable{
			private static final int BITMAP_WAS_FINDED = 31;
			private static final int BITMAP_WAS_NOT_FINDED = BITMAP_WAS_FINDED + 1;
			
			private LImageData psi;
			private ImageCallback callback;
			
			private Handler handler = new Handler() {
	        	public void handleMessage(Message message) {
	        		Object obj = message.obj;
	        		if(obj == null){
	        			callback.imageLoaded(null, psi);
	        			return;
	        		}
	        		callback.imageLoaded((Bitmap)obj, psi);
	        	}
	        };
	        
	        public PhotoBitmapGenerater(LImageData file ,ImageCallback callback){
	        	this.psi = file;
	        	this.callback = callback;
	        }
	        
			@Override
			public void run() {
				Bitmap bitmap = null;
				WeakReference<Bitmap> w_reference = mPhotoBitmapCache.get(psi.file_path);
				if(w_reference != null){
					bitmap = w_reference.get();
					if(bitmap != null){
						sendBitmap(BITMAP_WAS_FINDED ,bitmap);
						return;
					}
				}
				
				bitmap = getBitmapFromDB();
				if(bitmap != null){
					mPhotoBitmapCache.put(psi.file_path, new WeakReference<Bitmap>(bitmap));
					sendBitmap(BITMAP_WAS_FINDED ,bitmap);
					return;
				}
				
				sendBitmap(BITMAP_WAS_NOT_FINDED ,null);
			}
			
			private Bitmap getBitmapFromDB(){
			    BitmapFactory.Options options = new BitmapFactory.Options();
	            options.inDither = false;
	            options.inScaled = true;
	            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	            Bitmap mThumbnail = MediaStore.Images.Thumbnails.getThumbnail(
	            		mContext.getContentResolver(), psi.file_id, Thumbnails.MICRO_KIND, options);
	            return mThumbnail;
			}
			
			private void sendBitmap(int what ,Bitmap bitmap){
				if(bitmap != null && !bitmap.isRecycled()){
	                Message message = handler.obtainMessage(what, bitmap);
	                handler.sendMessage(message);
	            }
			}
		}
	}
}
