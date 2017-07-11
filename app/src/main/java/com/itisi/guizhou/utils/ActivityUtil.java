package com.itisi.guizhou.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


/**
 ***********************
 * 功 能:Activity工具类 copy
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/11 10:34
 * 修改人:itisi
 * 修改时间: 2017/7/11 10:34
 * 修改内容:itisi
 * *********************
 */
public class ActivityUtil {
	
	private static final String TAG = ActivityUtil.class.getSimpleName();
	private static ActivityUtil instance = null;
//	private ProgressDialog dialog;
//	private final List<Activity> activitys = new ArrayList<>();

	public static ActivityUtil getInstance() {
		if(instance==null) {
			synchronized(ActivityUtil.class) {
				if(instance==null) {
					instance = new ActivityUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 保存Activity
	 * @param act
	 */
	public void save(Activity act) {
//		if(act!=null) {
//			activitys.add(act);
//		}
	}

	/**
	 * 移出并注销所有Activity
	 */
	public void finishAll() {
//		for(int i=activitys.size()-1;i>=0;i--) {
//			// 逆序才可以边移出边注销
//			Activity act = activitys.remove(i);
//			if(act!=null) act.finish();
//		}

	}

	/**
	 * 移出Activity
	 * @param act
	 */
	public void remove(Activity act) {
//		activitys.remove(act);
	}

	
	/**
	 * 显示确认对话框
	 * @param context 上下文
	 * @param title 标题
	 * @param message 提示信息
	 */
	public void showConfirmDialog(Context context, String title, String message){
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	/**
	 * 加载系统等待框
	 * @param context  上下文
     */
	public void showDialog(Context context){
//		dialog =  new ProgressDialog(context);
//		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		    // 设置ProgressDialog 提示信息
//		dialog.setMessage(ResourcesUtil.getResourceString(context, R.string.wait));
//		dialog.show();
	}

	/**
	 * 关闭系统等待框
	 */
	public void dismissDialog(){
//		if(dialog != null && dialog.isShowing()) {
//			dialog.cancel();
//			dialog = null;
//		}
	}

	/**
	 * 不带bundle的Activity跳转-带有转场动画的
	 * @param activity
	 * @param pClass
	 */
	public void openActivity(Activity activity,Class<?> pClass) {
		openActivity(activity,pClass, null);
	}

	/**
	 * 带bundle的Activity跳转-带有转场动画的
	 * @param activity
	 * @param pClass
	 * @param pBundle
	 */
	public void openActivity(Activity activity,Class<?> pClass, Bundle pBundle) {

		Intent intent = new Intent(activity, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		activity.startActivity(intent);
		SceneAnim.openActivityByScaleAlpha(activity);

	}

	/**
	 * 关闭当前界面--带有转场动画的
	 * @param activity
	 */
	public void closeActivity(Activity activity) {
		activity.finish();
		SceneAnim.closeActivityByScaleAlpha(activity);

	}

}
