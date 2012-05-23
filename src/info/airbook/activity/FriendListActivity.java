package info.airbook.activity;

import info.airbook.R;
import info.airbook.adapter.MyAdapter;
import info.airbook.entity.Contact;
import info.airbook.listener.FriendListItemListener;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FriendListActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);
		Intent intent = getIntent();
		ListView lsListView = (ListView) findViewById(R.id.friends);
		List<Contact> listItems = getListItems(intent);
		lsListView.setAdapter(new MyAdapter(this, listItems));
		OnItemClickListener onItemClickListener = new FriendListItemListener(
				this, listItems);
		lsListView.setOnItemClickListener(onItemClickListener);
		/*
		 * lsListView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { startActivity(new
		 * Intent(FriendListActivity.this, FriendInfoActivity.class)); } });
		 */
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(FriendListActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						FriendListActivity.this.finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return false;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private List<Contact> getListItems(Intent intent) {
		Bundle bundle = intent.getExtras();
		ArrayList<Contact> listItems = (ArrayList<Contact>) bundle
				.get("contacts");
		return listItems;

	}
}