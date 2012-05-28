package info.airbook.listener;

import info.airbook.R;
import info.airbook.entity.Contact;
import info.airbook.thread.ContactDetailThread;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class FriendListItemListener implements OnItemClickListener {

	private Context context;
	private Handler handler;
	private Dialog dialog;
	private List<Contact> itemList;

	public FriendListItemListener(Context context, Handler handler,
			List<Contact> itemList, Dialog dialog) {
		this.context = context;
		this.handler = handler;
		this.dialog = dialog;
		this.itemList = itemList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (dialog == null) {
			dialog = new ProgressDialog(context);
		}
		dialog.show();
		dialog.setContentView(R.layout.loading);
		TextView textView = (TextView) dialog.getWindow().findViewById(
				R.id.loading_info);
		textView.setText("加载中...");
		Contact contact = itemList.get(position);
		Thread thread = new Thread(new ContactDetailThread(contact, handler));
		thread.start();
	}
}
