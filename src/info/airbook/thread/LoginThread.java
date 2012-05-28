package info.airbook.thread;

import info.airbook.activity.LoginActivity;
import info.airbook.entity.Account;
import info.airbook.entity.Data;
import info.airbook.entity.LoginResult;
import info.airbook.util.Json2Entity;
import info.airbook.util.NetConnection;

import java.io.InputStream;
import java.net.HttpURLConnection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoginThread implements Runnable {

	private Account account;
	private Handler handler;
	private boolean stored;

	public LoginThread(Account account, Handler handler, boolean stored) {
		this.account = account;
		this.handler = handler;
		this.stored = stored;
	}

	@Override
	public void run() {

		String addr = Data.AIR_BOOK_INFO + "/m/login";

		NetConnection netConnection = new NetConnection();
		HttpURLConnection httpURLConnection = netConnection.getConnection(addr);

		Message message = Message.obtain();
		try {
			if (httpURLConnection != null) {
				setPamaters(httpURLConnection);
				httpURLConnection.connect();
				LoginResult loginResult = getResult(httpURLConnection,
						netConnection);
				if (loginResult != null) {
					if (loginResult.isSuccess()) {
						if (stored) {
							message.what = LoginActivity.LOGIN_SUCESS_WITH_STORE;
						} else {
							message.what = LoginActivity.LOGIN_SUCESS;
						}
						message.obj = loginResult.getAccount();
					} else {
						message.what = LoginActivity.LOGIN_FAIL;
						message.obj = loginResult.getMessage();
					}
					handler.sendMessage(message);
				}
			} else {
				message.what = LoginActivity.LOGIN_FAIL;
				message.obj = "系统出错";
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			message.what = LoginActivity.LOGIN_FAIL;
			message.obj = "系统出错";
			handler.sendMessage(message);
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}

	public void setPamaters(HttpURLConnection httpURLConnection)
			throws Exception {
		String username = "name=" + account.getName();
		String password = "password=" + account.getPassword();
		httpURLConnection.getOutputStream().write(username.getBytes());
		httpURLConnection.getOutputStream().write("&".getBytes());
		httpURLConnection.getOutputStream().write(password.getBytes());
		httpURLConnection.getOutputStream().flush();
		httpURLConnection.getOutputStream().close();
	}

	public LoginResult getResult(HttpURLConnection httpURLConnection,
			NetConnection netConnection) throws Exception {
		InputStream inputStream = httpURLConnection.getInputStream();
		Json2Entity json2Entity = new Json2Entity();
		LoginResult loginResult = json2Entity.json2LoginResult(netConnection
				.getResponeInfo(inputStream));
		Log.i("air", loginResult.getMessage());
		Log.i("air", httpURLConnection.getURL().toString());
		return loginResult;
	}
}
