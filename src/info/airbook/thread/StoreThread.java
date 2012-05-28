package info.airbook.thread;

import info.airbook.entity.Contact;
import info.airbook.entity.Data;
import info.airbook.manager.ContactManager;
import info.airbook.sql.OpenOrCloseDB;
import info.airbook.util.PreferenceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StoreThread implements Runnable {

	private Context context;
	private List<Contact> contacts;

	public StoreThread(Context context, List<Contact> contacts) {
		this.context = context;
		this.contacts = contacts;
	}

	@Override
	public void run() {
		Log.i("air", "stored");
		SQLiteDatabase db = OpenOrCloseDB.openDB(context);
		if (contacts != null) {
			downloadAvatars();
			ContactManager contactManager = new ContactManager(db);
			contactManager.deleteAll();
			contactManager.saveContacts(contacts);
			SharedPreferences sharedPreferences = PreferenceUtil
					.getSharedPreferences(context);
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(Data.SHARE_PREFERENCE_STORED, true);
			editor.commit();
			Log.i("air", "stored2");
		}
		OpenOrCloseDB.closeDB(db);
	}

	public void downloadAvatars() {
		Log.i("air", "con");
		for (Contact contact : contacts) {
			HttpURLConnection httpURLConnection = null;
			InputStream inputStream = null;
			try {
				URL url = new URL(contact.getPhotoPath());
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setConnectTimeout(5000);

				Log.i("air", httpURLConnection + "con");
				if (httpURLConnection != null) {
					httpURLConnection.connect();
					inputStream = httpURLConnection.getInputStream();
					Log.i("air", "inputStream");
					writeFile(inputStream, contact);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void writeFile(InputStream input, Contact contact) {
		Log.i("air", "con");
		String filePathString = Data.getDataPath();
		Log.i("air", filePathString);
		if (filePathString != null) {
			File filePah = new File(filePathString);
			if (!filePah.exists()) {
				filePah.mkdirs();
			}
			File file = new File(filePah, contact.getId() + ".jpg");
			FileOutputStream fileout = null;
			try {
				if (!file.exists()) {
					Log.i("air", "create" + file.createNewFile());
				}
				fileout = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int data = input.read(b);
				while (data != -1) {
					fileout.write(b);
					data = input.read(b);
				}
				contact.setPhotoPath(file.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileout != null) {
					try {
						fileout.flush();
						fileout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}
}
