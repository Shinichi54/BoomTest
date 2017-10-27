package com.gg.unity_exchange;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

/**
 * Created by SherlockHolmes on 2017/10/27.
 */

public class AndroidKeyboard {
	public static Activity activity = null;
	InputMethodManager inputMethodManager = null;
	TextWatcher textWatcher = null;
	boolean mode = false;

	public AndroidKeyboard() {
		activity = UnityPlayer.currentActivity;
		inputMethodManager = (InputMethodManager) activity.getSystemService(Context
				.INPUT_METHOD_SERVICE);
	}

	//打开Activiy,并且显示输入法
	public void Open(final String text, final boolean mode) {
		if (activity == null) {
			Log.e("unity", "activity null when open keyboard");
			return;
		}
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(activity, "调用了显示键盘", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("text", text);
				intent.setClassName(activity, "com.gg.unity_exchange.SDKActivity");
				activity.startActivity(intent);
				activity.overridePendingTransition(R.anim.activity_open, 0);
			}
		});
	}
}