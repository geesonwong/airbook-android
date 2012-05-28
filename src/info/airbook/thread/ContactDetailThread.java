package info.airbook.thread;

import info.airbook.activity.FriendListActivity;
import info.airbook.entity.Contact;
import info.airbook.entity.Data;
import info.airbook.util.Json2Entity;
import info.airbook.util.NetConnection;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ContactDetailThread implements Runnable {

	private Handler handler;
	private Contact contact;

	public ContactDetailThread(Contact contact, Handler handler) {
		this.handler = handler;
		this.contact = contact;
	}

	@Override
	public void run() {
		String addr = Data.AIR_BOOK_INFO + "/m/getContactDetail";

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
