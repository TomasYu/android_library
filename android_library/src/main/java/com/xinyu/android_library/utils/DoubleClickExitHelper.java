package com.xinyu.android_library.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;


/***
 * 双击退出
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2015年1月5日 下午7:07:44
 *
 */
public class DoubleClickExitHelper {

	private final Activity mActivity;
	
	private boolean isOnKeyBacking;
	private Handler mHandler;
	private Toast mBackToast;
	
	public DoubleClickExitHelper(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(Looper.getMainLooper());
	}
	
	/**
	 * Activity onKeyDown事件
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		if(isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if(mBackToast != null){
				mBackToast.cancel();
			}
			// 退出逻辑
//			AppManager.getAppManager().AppExit(mActivity);
			mActivity.finish();
			return true;
		} else {
			isOnKeyBacking = true;
			if(mBackToast == null) {
				mBackToast = Toast.makeText(mActivity,"再按一次退出程序", Toast.LENGTH_SHORT);
			}
			mBackToast.show();
			mHandler.postDelayed(onBackTimeRunnable, 2000);
			return true;
		}
	}
	
	private Runnable onBackTimeRunnable = new Runnable() {
		
		@Override
		public void run() {
			isOnKeyBacking = false;
			if(mBackToast != null){
				mBackToast.cancel();
			}
		}
	};
}


//使用
//1.doubleClickExitHelpr = new DoubleClickExitHelper(this);
//
//2.
///**
// * 监听返回--是否退出程序
// */
//@Override
//public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//		// 是否退出应用
//		//下面这句户说的是，是不是双击退出，还是单急退出，都可以在里面设置
////            if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
//		return doubleClickExitHelpr.onKeyDown(keyCode, event);
////            }
//		}
//		return super.onKeyDown(keyCode, event);
//		}