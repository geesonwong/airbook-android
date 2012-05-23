package info.airbook.thread;

import info.airbook.entity.Account;
import info.airbook.entity.Contact;
import info.airbook.entity.Data;
import info.airbook.handler.LoginHandler;
import info.airbook.util.Json2Entity;
import info.airbook.util.NetConnection;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

import android.os.Handler;
import android.os.Message;

public class ContactThread implements Runnable {

	private Account account;
	private Handler handler;

	public ContactThread(Account account, Handler handler) {
		this.account = account;
		this.handler = handler;
	}

	@Override
	public void run() {
		String addr = Data.AIR_BOOK_INFO + "/m/getContacts";

		NetConnection netConnection = new NetConnection();
		HttpURLConnection httpURLConnection = netConnection.getConnection(addr);

		Message message = Message.obtain();
		try {
			if (httpURLConnection != null) {

				String accountId = "accountId=" + account.getAccount_id();
				httpURLConnection.getOutputStream().write(accountId.getBytes());
				httpURLConnection.getOutputStream().flush();
				httpURLConnection.getOutputStream().close();
				httpURLConnection.connect();
				InputStream inputStream = httpURLConnection.getInputStream();
				Json2Entity json2Entity = new Json2Entity();
				List<Contact> contacts = json2Entity
						.json2Contacts(netConnection
								.getResponeInfo(inputStream));
				if (contacts != null) {
					message.what = LoginHandler.LOADING_SUCCESS;
					message.obj = contacts;
				} else {
					message.what = LoginHandler.LOADING_FAIL;
				}
				handler.sendMessage(message);
			} else {
				message.what = LoginHandler.LOADING_FAIL;
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = LoginHandler.LOADING_FAIL;
			handler.sendMessage(message);
		}

	}
}
