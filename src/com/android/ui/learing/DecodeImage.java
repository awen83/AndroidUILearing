package com.android.ui.learing;

import com.android.ui.learing.utils.CLog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * 这个类提供了一些测试代码用来学习Image的decode相关的内容
 * */
public class DecodeImage extends Activity{
	private String lImage;
	private ImageView lImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decode_image_layout);
		lImageView = (ImageView) findViewById(R.id.image);
		
		test1();
		test2();
		test3();
	}
	
	/**
	 * Android提供了BitmapFactory.Options类，用来设置一些解码参数。比如 inJustDecodeBounds。
	 * 当 inJustDecodeBounds设置为true的时候，BitmapFactory并不会真正的执行对图片的解码工作，当然也不会申请大内存。
	 * 不过此时BitmapFactory会返回我们要解码图片的一些基本信息，比如： outWidth, outHeight, outMimeType
	 * */
	private void test1(){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile("/sdcard/decode_resouce.jpg", options);
		CLog.i("outWidth : " + options.outWidth);
		CLog.i("outHeight : " + options.outHeight);
		CLog.i("outMimeType : " + options.outMimeType);
	}
	
	/**
	 * 经过test1我们知道了怎么获取资源图片的尺寸，那么接下来我们要做的就是根据要显示的尺寸来获取图片。
	 * */
	private void test2(){
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		CLog.i("width : " + width + " : height : " + height);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		CLog.i("metrics.density : " + metrics.density);
		CLog.i("metrics.densityDpi : " + metrics.densityDpi);
		CLog.i("metrics.widthPixels : " + metrics.widthPixels);
		CLog.i("metrics.heightPixels : " + metrics.heightPixels);
		CLog.i("metrics.scaledDensity : " + metrics.scaledDensity);
		CLog.i("metrics.xdpi : " + metrics.xdpi);
		CLog.i("metrics.ydpi : " + metrics.ydpi);
	}
	
	private void test3(){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile("/sdcard/decode_resouce.jpg", options);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int _real_width = metrics.widthPixels < options.outWidth 
				? (Math.round(options.outWidth / metrics.widthPixels)) 
				: (options.outWidth);
				
		int _real_height = metrics.heightPixels < options.outHeight 
				? (Math.round(options.outHeight / metrics.heightPixels)) 
				: (options.outHeight);
				
		options.inSampleSize = _real_width < _real_height ? _real_width : _real_height;
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/decode_resouce.jpg", options);
	    lImageView.setImageBitmap(bitmap);
	}
}
