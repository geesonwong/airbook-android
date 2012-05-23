package info.airbook.listener;

import info.airbook.entity.Account;
import info.airbook.handler.LoginHandler;
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

	public LoginButtonListener(Handler handler, EditText accountEditText,
			EditText passwordEditText, CheckBox rememberMe) {
		this.handler = handler;
		this.accountEditText = accountEditText;
		this.passwEditText = passwordEditText;
		this.rememberMe = rememberMe;
	}

	@Override
	public void onClick(View v) {
		String name = accountEditText.getText().toString();
		String password = passwEditText.getText().toString();
		boolean reMe = rememberMe.isChecked();

		Account account = new Account();
		account.setName(name);
		account.setPassword(password);
		Message msg = Message.obtain();
		msg.what = LoginHandler.LOGIN;
		msg.obj = account;
		Bundle bundle = new Bundle();
		bundle.putBoolean("rememberMe", reMe);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
}
