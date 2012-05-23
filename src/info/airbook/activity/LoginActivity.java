package info.airbook.activity;

import info.airbook.R;
import info.airbook.dao.AccountDao;
import info.airbook.entity.Account;
import info.airbook.handler.LoginHandler;
import info.airbook.listener.LoginButtonListener;
import info.airbook.sql.OpenOrCloseDB;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText accountEditText;
	private EditText passwordEditText;
	private CheckBox rememberMe;

	private Handler loginHandler;

	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginHandler = new LoginHandler(this);

		db = OpenOrCloseDB.openDB(this);
		AccountDao accountDao = new AccountDao(db);
		Account account = accountDao.findFirst();

		loginButton = (Button) findViewById(R.id.login_button);
		accountEditText = (EditText) findViewById(R.id.login_edit_account);
		passwordEditText = (EditText) findViewById(R.id.login_edit_password);
		rememberMe = (CheckBox) findViewById(R.id.remember_me);

		if (account != null) {
			accountEditText.setText(account.getName());
			passwordEditText.setText(account.getPassword());
		}
		OnClickListener listener = new LoginButtonListener(loginHandler,
				accountEditText, passwordEditText, rememberMe);

		loginButton.setOnClickListener(listener);
	}

	@Override
	protected void onDestroy() {
		OpenOrCloseDB.closeDB(db);
		super.onDestroy();
	}
}
