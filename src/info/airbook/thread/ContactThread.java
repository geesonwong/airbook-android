package info.airbook.thread;

import info.airbook.activity.LoginActivity;
import info.airbook.entity.Account;
import info.airbook.entity.Data;
import info.airbook.util.Json2Entity;
import info.airbook.util.NetConnection;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONObject;

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
				String resultString = netConnection.getResponeInfo(inputStream);
				JSONObject result = Json2Entity.string2jsonObject(resultString);
				if ((Boolean) result.optBoolean("success")) {
					message.what = LoginActivity.LOADING_SUCCESS;
					message.obj = resultString;
					handler.sendMessage(message);
				} else {
					message.what = LoginActivity.LOADING_FAIL;
					handler.sendMessage(message);
				}
			} else {
				message.what = LoginActivity.LOADING_FAIL;
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = LoginActivity.LOADING_FAIL;
			handler.sendMessage(message);
		}

	}
}
