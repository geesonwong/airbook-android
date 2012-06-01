package info.airbook.listener;

import info.airbook.R;
import info.airbook.entity.Data;
import info.airbook.util.PreferenceUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RigButtonListener implements OnClickListener {

	private EditText hostEditText;
	private Context context;

	public RigButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		final SharedPreferences sharedPreferences = PreferenceUtil
				.getSharedPreferences(context);
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("请输入");
		builder.setIcon(R.drawable.favicon);
		hostEditText = new EditText(context);
		String hostString = sharedPreferences.getString(
				Data.SHARE_PREFERENCE_HOST, Data.AIR_BOOK_INFO);
		hostEditText.setText(hostString);
		builder.setView(hostEditText);
		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String host = hostEditText.getText().toString();
						if (host != null && !host.isEmpty()) {
							Editor editor = sharedPreferences.edit();
							editor.putString(Data.SHARE_PREFERENCE_HOST, host);
							editor.commit();
							dialog.dismiss();
						} else {
							Toast.makeText(context, "请输入内容", Toast.LENGTH_LONG)
									.show();
						}
					}

				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	protected void inputHost() {
	}
}
