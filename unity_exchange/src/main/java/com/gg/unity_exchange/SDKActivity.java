package com.gg.unity_exchange;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

/**
 * Created by SherlockHolmes on 2017/10/27.
 */

public class SDKActivity extends Activity {

	private Toast toast;
	private Activity activity;
	private Button btn;
	private EditText editText;
	private String text = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_sdk);
		activity = this;

		try {
			text = getIntent().getStringExtra("text");
		} catch (Exception e) {
			text = "";
		}

		editText = (EditText) findViewById(R.id.textArea);
		if (!TextUtils.isEmpty(text)) {
			editText.setText(text);
			editText.setSelection(text.length());
		}

		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toast("点击了发送按钮：" + editText.getText().toString());
				if (editText.getText().toString().length() == 0) {
					return;
				}
				SendData(1, editText.getText().toString());
				finish();
			}
		});

		findViewById(R.id.rl_root).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (v instanceof RelativeLayout) {
					// 关闭键盘
					InputMethodManager imm = (InputMethodManager) activity
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});

//		// 点击空白处 关闭键盘
//		findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 关闭键盘
//				InputMethodManager imm = (InputMethodManager) activity
//						.getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//			}
//		});
	}

	@Override
	public void onBackPressed() {
		if (editText.getText().length() > 0) {
			SendData(0, editText.getText().toString());
		}
		finish();
	}

	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(0, R.anim.activity_close);

	}

	// 向unity返回数据
	void SendData(int code, String text) {
		if (code == 1) {
			UnityPlayer.UnitySendMessage("Plugins", "OnCustomInputAction", text);
		} else {
			UnityPlayer.UnitySendMessage("Plugins", "OnCustomInputActionBack", text);
		}
	}

	private void toast(final String msg) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (toast == null) {
					toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
				} else {
					toast.setText(msg);
				}
				toast.show();
			}
		});
	}
}
