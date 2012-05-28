package info.airbook.activity;

import info.airbook.R;
import info.airbook.adapter.MyAdapter;
import info.airbook.entity.Contact;
import info.airbook.entity.ContactResult;
import info.airbook.listener.FriendListItemListener;
import info.airbook.manager.ContactManager;
import info.airbook.manager.MenuManager;
import info.airbook.sql.OpenOrCloseDB;
import info.airbook.thread.StoreThread;
import info.airbook.util.Json2Entity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

public class FriendListActivity extends Activity {
	/** Called when the activity is first created. */

	private Handler handler;
	private Dialog dialog;
	private MyAdapter adapter;
	public final static int LOAD_DETAIL_SUCCESS = 0;
	public final static int LOAD_DETAIL_FAIL = 1;
	public final static int EXIT = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);
		Intent intent = getIntent();
		ListView lsListView = (ListView) findViewById(R.id.friends);
		List<Contact> listItems;
		if (intent.getExtras().getBoolean("stored")) {
			listItems = getListItemsFromeDB();
		} else {
			listItems = getListItems(intent);
			storeData(listItems);
		}
		adapter = new MyAdapter(this, listItems);
		lsListView.setAdapter(adapter);

		handler = new FriendListHandler();
		dialog = new ProgressDialog(this);
		OnItemClickListener onItemClickListener = new FriendListItemListener(
				this, handler, listItems, dialog);
		lsListView.setOnItemClickListener(onItemClickListener);

		GridView gridView = (GridView) findViewById(R.id.menu_gridview);

		MenuManager menuManager = new MenuManager(this, gridView, handler,
				dialog);
		menuManager.addMenu();

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

	private List<Contact> getListItems(Intent intent) {

		Bundle bundle = intent.getExtras();
		String resultsString = bundle.getString("results");
		ContactResult contactResult = Json2Entity
				.json2ContactResult(resultsString);
		List<Contact> listItems = contactResult.getContacts();

		return listItems;
	}

	private List<Contact> getListItems(String json) {
		ContactResult contactResult = Json2Entity.json2ContactResult(json);
		List<Contact> listItems = contactResult.getContacts();
		return listItems;
	}

	private List<Contact> getListItemsFromeDB() {
		SQLiteDatabase db = OpenOrCloseDB.openDB(this);
		ContactManager contactManager = new ContactManager(db);
		List<Contact> list = contactManager.getContacts();
		OpenOrCloseDB.closeDB(db);
		return list;
	}

	private void storeData(List<Contact> contacts) {
		Thread storeThread = new Thread(new StoreThread(
				FriendListActivity.this, contacts));
		storeThread.start();
	}

	class FriendListHandler extends Handler {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOAD_DETAIL_SUCCESS:
				loadSuccess((Contact) msg.obj);
				break;
			case LOAD_DETAIL_FAIL:
				loadFail();
				break;
			case LoginActivity.LOADING_SUCCESS:
				String json = (String) msg.obj;
				refreshSuccess(json);
				break;
			case LoginActivity.LOADING_FAIL:
				refreshFail();
				break;
			case EXIT:
				exit();
				break;
			default:
				break;
			}
		}

		public void refreshSuccess(String json) {
			cancelProcessDialog();
			List<Contact> listItems = getListItems(json);
			storeData(listItems);
			adapter.setListItems(listItems);
			adapter.notifyDataSetChanged();
		}

		public void refreshFail() {
			cancelProcessDialog();
			errorDialog();
		}

		public void loadSuccess(Contact contact) {
			cancelProcessDialog();
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("contact", contact);
			intent.putExtras(bundle);
			intent.setClass(FriendListActivity.this, FriendInfoActivity.class);
			FriendListActivity.this.startActivity(intent);
		}

		public void loadFail() {
			cancelProcessDialog();
			errorDialog();
		}

		private void cancelProcessDialog() {
			if (dialog != null && dialog.isShowing()) {
				dialog.cancel();
			}
		}

		private void exit() {
			dialog();
		}

		private void errorDialog() {
			AlertDialog.Builder builder = new Builder(FriendListActivity.this);
			builder.setMessage("数据加载失败，请稍后重试");
			builder.setTitle("加载失败！");

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
}