package info.airbook.manager;

import info.airbook.R;
import info.airbook.dao.AccountDao;
import info.airbook.entity.Account;
import info.airbook.sql.OpenOrCloseDB;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class AccountManager {

	ProgressDialog dialog;

	public boolean login(Context context, Account account) {

		dialog = new ProgressDialog(context);
		dialog.show();
		dialog.setContentView(R.layout.loading);
		return true;
	}

	public void loginSuccesss(Context context) {

		TextView textView = (TextView) dialog.getWindow().findViewById(
				R.id.loading_info);
		textView.setText("加载中...");
	}

	public void loginFail(Context context, String message) {
		if (dialog != null && dialog.isShowing()) {
			dialog.cancel();
			dialog(context, message);
		}
	}

	public void rememberMe(Context context, Account account) {
		SQLiteDatabase db = OpenOrCloseDB.openDB(context);
		AccountDao accountDao = new AccountDao(db);
		Account account2 = accountDao.findFirst();
		if (account2 != null) {
			account.setId(account2.getId());
			accountDao.update(account);
		} else {
			accountDao.save(account);
		}
		OpenOrCloseDB.closeDB(db);
	}

	protected void dialog(Context context, String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle("登录失败！");

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
}
