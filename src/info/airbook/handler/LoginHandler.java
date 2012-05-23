package info.airbook.handler;

import info.airbook.activity.FriendListActivity;
import info.airbook.entity.Account;
import info.airbook.entity.Contact;
import info.airbook.manager.AccountManager;
import info.airbook.thread.ContactThread;
import info.airbook.thread.LoginThread;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoginHandler extends Handler {

	private Context context;

	public final static int LOGIN = 0;
	public final static int LOGIN_SUCESS = 1;
	public final static int LOGIN_FAIL = 2;
	public final static int LOADING_SUCCESS = 3;
	public final static int LOADING_FAIL = 4;

	private AccountManager accountManager;

	public LoginHandler(Context context) {
		this.context = context;
		accountManager = new AccountManager();
	}

	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		action(msg);
	}

	public void action(Message msg) {
		switch (msg.what) {
		case LoginHandler.LOGIN:
			Account account = (Account) msg.obj;
			accountManager.login(context, account);
			Thread thread = new Thread(new LoginThread(account, context, this));
			thread.start();
			break;
		case LOGIN_SUCESS:
			accountManager.loginSuccesss(context);
			account = (Account) msg.obj;
			Thread thread2 = new Thread(new ContactThread(account, this));
			thread2.start();
			break;
		case LOGIN_FAIL:
			accountManager.loginFail(context, (String) msg.obj);
			break;
		case LOADING_SUCCESS:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			ArrayList<Contact> contacts = (ArrayList<Contact>) msg.obj;
			bundle.putParcelableArrayList("contacts", contacts);
			intent.putExtras(bundle);
			intent.setClass(context, FriendListActivity.class);
			context.startActivity(intent);
			break;
		case LOADING_FAIL:
			Log.i("air", "加载出错...");
			break;
		default:
			break;
		}
	}
}
