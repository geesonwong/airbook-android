package info.airbook.listener;

import info.airbook.R;
import info.airbook.activity.FriendListActivity;
import info.airbook.activity.SettingActivity;
import info.airbook.entity.Account;
import info.airbook.entity.Data;
import info.airbook.thread.ContactThread;
import info.airbook.util.PreferenceUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MenuListener implements OnItemClickListener {

	private Context context;
	private Handler handler;
	private Dialog dialog;

	public MenuListener(Context context, Handler handler, Dialog dialog) {
		this.context = context;
		this.handler = handler;
		this.dialog = dialog;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			refresh();
			break;
		case 1:
			setting();
			break;
		case 2:
			about();
			break;
		case 3:
			exit();
			break;
		default:
			break;
		}
	}

	private void refresh() {
		if (dialog == null) {
			dialog = new ProgressDialog(context);
		}
		dialog.show();
		dialog.setContentView(R.layout.loading);
		TextView textView = (TextView) dialog.getWindow().findViewById(
				R.id.loading_info);
		textView.setText("加载中...");
		Account account = new Account();
		SharedPreferences sharedPreferences = PreferenceUtil
				.getSharedPreferences(context);

		account.setAccount_id(sharedPreferences.getString(
				Data.SHARE_PREFERENCE_Id, null));
		Thread thread = new Thread(new ContactThread(account, handler));
		thread.start();
	}

	private void about() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("关于...");
		builder.setTitle("关于...");

		builder.setPositiveButton("关闭",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	private void setting() {
		Intent intent = new Intent(context, SettingActivity.class);
		context.startActivity(intent);
	}

	private void exit() {
		Message message = Message.obtain();
		message.what = FriendListActivity.EXIT;
		handler.sendMessage(message);
	}
}
