package info.airbook.thread;

import info.airbook.activity.FriendListActivity;
import info.airbook.entity.Contact;
import info.airbook.entity.Data;
import info.airbook.util.Json2Entity;
import info.airbook.util.NetConnection;
import info.airbook.util.PreferenceUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ContactDetailThread implements Runnable {

	private Handler handler;
	private Contact contact;
	private Context context;

	public ContactDetailThread(Contact contact, Handler handler, Context context) {
		this.handler = handler;
		this.contact = contact;
		this.context = context;
	}

	@Override
	public void run() {
		SharedPreferences sharedPreferences = PreferenceUtil
				.getSharedPreferences(context);
		String host = sharedPreferences.getString(Data.SHARE_PREFERENCE_HOST,
				Data.AIR_BOOK_INFO);
		String addr = host + "/m/getContactDetail";
		// String addr = Data.AIR_BOOK_INFO + "/m/getContactDetail";

		NetConnection netConnection = new NetConnection();
		HttpURLConnection httpURLConnection = netConnection.getConnection(addr);

		Message message = Message.obtain();
		try {
			if (httpURLConnection != null) {

				setPamaters(httpURLConnection);

				httpURLConnection.connect();
				InputStream inputStream = httpURLConnection.getInputStream();
				JSONObject jsonObject = Json2Entity
						.string2jsonObject(netConnection
								.getResponeInfo(inputStream));
				if (jsonObject.optBoolean("success")) {
					String contacterString = jsonObject
							.optString("accountDetail");
					if (contacterString != null) {
						message.obj = Json2Entity.json2Contact(contact,
								contacterString);
						message.what = FriendListActivity.LOAD_DETAIL_SUCCESS;
					} else {
						message.what = FriendListActivity.LOAD_DETAIL_FAIL;
					}
					handler.sendMessage(message);
				} else {
					message.what = FriendListActivity.LOAD_DETAIL_FAIL;
					handler.sendMessage(message);
				}
			} else {
				message.what = FriendListActivity.LOAD_DETAIL_FAIL;
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = FriendListActivity.LOAD_DETAIL_FAIL;
			handler.sendMessage(message);
		}

	}

	public void setPamaters(HttpURLConnection httpURLConnection)
			throws Exception {
		Log.i("air", "id:" + contact.getId());
		String accountId = "contacterId=" + contact.getId();
		httpURLConnection.getOutputStream().write(accountId.getBytes());
		httpURLConnection.getOutputStream().flush();
		httpURLConnection.getOutputStream().close();
	}
}
