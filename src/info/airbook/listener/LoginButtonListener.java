package info.airbook.listener;

import info.airbook.activity.LoginActivity;
import info.airbook.entity.Account;
import info.airbook.entity.Data;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginButtonListener implements OnClickListener {

	private Handler handler;
	private EditText accountEditText;
	private EditText passwEditText;
	private CheckBox rememberMe;
	private Context context;
	private CheckBox loginAuto;

	public LoginButtonListener(Context context, Handler handler,
			EditText accountEditText, EditText passwordEditText,
			CheckBox rememberMe, CheckBox loginAuto) {
		this.context = context;
		this.handler = handler;
		this.accountEditText = accountEditText;
		this.passwEditText = passwordEditText;
		this.rememberMe = rememberMe;
		this.loginAuto = loginAuto;
	}

	@Override
	public void onClick(View v) {
		String name = accountEditText.getText().toString();
		String password = passwEditText.getText().toString();
		boolean reMe = rememberMe.isChecked();
		boolean isLoginAuto = loginAuto.isChecked();
		rememberMe(name, password, reMe, isLoginAuto);
		Account account = new Account();
		account.setName(name);
		account.setPassword(password);
		Message msg = Message.obtain();
		msg.what = LoginActivity.LOGIN;
		msg.obj = account;
		Bundle bundle = new Bundle();
		bundle.putBoolean("rememberMe", reMe);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	protected void rememberMe(String airName, String airPwd,
			boolean remenberMe, boolean loginAuto) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Data.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(Data.SHARE_PREFERENCE_USER, airName);
		editor.putBoolean(Data.SHARE_PREFERENCE_LOGIN_AUTO, loginAuto);
		if (remenberMe) {
			editor.putString(Data.SHARE_PREFERENCE_PSW, airPwd);
		} else {
			editor.remove(Data.SHARE_PREFERENCE_PSW);
		}
		editor.commit();
	}
}
