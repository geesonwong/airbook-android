package info.airbook.activity;

import info.airbook.R;
import info.airbook.entity.Account;
import info.airbook.entity.Data;
import info.airbook.listener.LoginButtonListener;
import info.airbook.thread.ContactThread;
import info.airbook.thread.LoginThread;
import info.airbook.util.PreferenceUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	public final static int LOGIN = 0;
	public final static int LOGIN_SUCESS = 1;
	public final static int LOGIN_SUCESS_WITH_STORE = 5;
	public final static int LOGIN_FAIL = 2;
	public final static int LOADING_SUCCESS = 3;
	public final static int LOADING_FAIL = 4;

	private Button loginButton;
	private EditText accountEditText;
	private EditText passwordEditText;
	private CheckBox rememberMe;
	private CheckBox loginAuto;
	private boolean stored = false;
	private boolean isAutoLogin = false;
	private String userName;
	private String password;
	private Handler loginHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginHandler = new LoginHandler(this);

		loginButton = (Button) findViewById(R.id.login_button);
		accountEditText = (EditText) findViewById(R.id.login_edit_account);
		passwordEditText = (EditText) findViewById(R.id.login_edit_password);
		rememberMe = (CheckBox) findViewById(R.id.remember_me);
		loginAuto = (CheckBox) findViewById(R.id.login_auto);
		SharedPreferences spfl = PreferenceUtil.getSharedPreferences(this);
		if (spfl != null) {
			userName = spfl.getString(Data.SHARE_PREFERENCE_USER, null);
			password = spfl.getString(Data.SHARE_PREFERENCE_PSW, null);
			stored = spfl.getBoolean(Data.SHARE_PREFERENCE_STORED, false);
			isAutoLogin = spfl.getBoolean(Data.SHARE_PREFERENCE_LOGIN_AUTO,
					false);
			Log.i("air", stored + "");
			accountEditText.setText(userName);
			passwordEditText.setText(password);
		}
		OnClickListener listener = new LoginButtonListener(this, loginHandler,
				accountEditText, passwordEditText, rememberMe, loginAuto);

		loginButton.setOnClickListener(listener);
		if (isAutoLogin) {
			Message msg = Message.obtain();
			msg.what = LoginActivity.LOGIN;
			Account account = new Account();
			account.setName(userName);
			account.setPassword(password);
			msg.obj = account;
			Bundle bundle = new Bundle();
			bundle.putBoolean("rememberMe", true);
			msg.setData(bundle);
			loginHandler.sendMessage(msg);
		}
	}

	class LoginHandler extends Handler {

		private Context context;

		private Dialog dialog;

		public LoginHandler(Context context) {
			this.context = context;
		}

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			action(msg);
		}

		public void action(Message msg) {
			switch (msg.what) {
			case LOGIN:
				Account account = (Account) msg.obj;
				logining();
				Thread thread = new Thread(new LoginThread(account, this,
						stored));
				thread.start();
				break;
			case LOGIN_SUCESS:
				account = (Account) msg.obj;
				loginSuccess(account);
				Thread thread2 = new Thread(new ContactThread(account, this));
				thread2.start();
				break;
			case LOGIN_FAIL:
				loginFail((String) msg.obj);
				break;
			case LOADING_SUCCESS:
				loadSuccess(msg);
				break;
			case LOADING_FAIL:
				Log.i("air", "加载出错...");
				loadFail();
				break;
			case LOGIN_SUCESS_WITH_STORE:
				account = (Account) msg.obj;
				loginSuccess(account);
				loginSuccessWithStored();
				break;
			default:
				break;
			}
		}

		protected void logining() {
			dialog = new ProgressDialog(context);
			dialog.show();
			dialog.setContentView(R.layout.loading);
		}

		protected void loginSuccess(Account account) {
			SharedPreferences sPreferences = PreferenceUtil
					.getSharedPreferences(context);
			Editor e = sPreferences.edit();
			e.putString(Data.SHARE_PREFERENCE_Id, account.getAccount_id());
			e.commit();
			TextView textView = (TextView) dialog.getWindow().findViewById(
					R.id.loading_info);
			textView.setText("加载中...");
		}

		protected void loginFail(String message) {
			if (dialog != null && dialog.isShowing()) {
				dialog.cancel();
				AlertDialog.Builder builder = new Builder(context);
				builder.setMessage(message);
				builder.setTitle("登录失败！");

				builder.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		}

		public void loadSuccess(Message msg) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			String jsonStrings = (String) msg.obj;
			bundle.putString("results", jsonStrings);
			bundle.putBoolean("stored", false);
			intent.putExtras(bundle);
			intent.setClass(context, FriendListActivity.class);
			context.startActivity(intent);
			if (dialog != null && dialog.isShowing()) {
				dialog.cancel();
			}
			LoginActivity.this.finish();
		}

		public void loadFail() {
			if (dialog != null && dialog.isShowing()) {
				dialog.cancel();
				AlertDialog.Builder builder = new Builder(context);
				builder.setMessage("加载出错");
				builder.setTitle("加载出错！");

				builder.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		}

		public void loginSuccessWithStored() {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putBoolean("stored", true);
			intent.putExtras(bundle);
			intent.setClass(context, FriendListActivity.class);
			context.startActivity(intent);
			if (dialog != null && dialog.isShowing()) {
				dialog.cancel();
			}
			LoginActivity.this.finish();
		}
	}
}
