package info.airbook.thread;

import info.airbook.entity.Account;
import info.airbook.entity.Data;
import info.airbook.entity.LoginResult;
import info.airbook.handler.LoginHandler;
import info.airbook.manager.AccountManager;
import info.airbook.util.Json2Entity;
import info.airbook.util.NetConnection;

import java.io.InputStream;
import java.net.HttpURLConnection;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoginThread implements Runnable {

	private Account account;
	private AccountManager accountManager;
	private Context context;
	private Handler handler;

	public LoginThread(Account account, Context context, Handler handler) {
		this.account = account;
		accountManager = new AccountManager();
		this.context = context;
		this.handler = handler;
	}

	@Override
	public void run() {

		String addr = Data.AIR_BOOK_INFO + "/m/login";

		NetConnection netConnection = new NetConnection();
		HttpURLConnection httpURLConnection = netConnection.getConnection(addr);

		Message message = Message.obtain();
		try {
			if (httpURLConnection != null) {
				String username = "name=" + account.getName();
				String password = "password=" + account.getPassword();
				httpURLConnection.getOutputStream().write(username.getBytes());
				httpURLConnection.getOutputStream().write("&".getBytes());
				httpURLConnection.getOutputStream().write(password.getBytes());
				httpURLConnection.getOutputStream().flush();
				httpURLConnection.getOutputStream().close();
				httpURLConnection.connect();
				InputStream inputStream = httpURLConnection.getInputStream();
				Json2Entity json2Entity = new Json2Entity();
				LoginResult loginResult = json2Entity
						.json2LoginResult(netConnection
								.getResponeInfo(inputStream));
				Log.i("air", loginResult.getMessage());
				Log.i("air", httpURLConnection.getURL().toString());

				String cookieVal = null;
				String key = null;
				StringBuffer buffer = new StringBuffer();
				int i = 0;
				while ((cookieVal = httpURLConnection.getHeaderField(i)) != null) {
					key = httpURLConnection.getHeaderFieldKey(i);
					if (key != null) {
						if (key.equalsIgnoreCase("set-cookie")) {
							buffer.append(cookieVal + ";");
						}
					}
					i++;
				}
				Log.i("air", buffer.toString());
				if (loginResult != null) {
					if (loginResult.isSuccess()) {
						message.what = LoginHandler.LOGIN_SUCESS;
						message.obj = loginResult.getAccount();
					} else {
						message.what = LoginHandler.LOGIN_FAIL;
						message.obj = loginResult.getMessage();
					}
					handler.sendMessage(message);
				}
			} else {
				message.what = LoginHandler.LOGIN_FAIL;
				message.obj = "系统出错";
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			message.what = LoginHandler.LOGIN_FAIL;
			message.obj = "系统出错";
			handler.sendMessage(message);
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}
}
