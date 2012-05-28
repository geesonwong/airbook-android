package info.airbook.entity;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class Data {

	public final static String AIR_BOOK_INFO = "http://192.168.0.144:4000";

	// public final static String SHARE_PREFERENCE_NAME = "airbook_user";
	public final static String SHARE_PREFERENCE_NAME = "info.airbook_preferences";
	public final static String SHARE_PREFERENCE_USER = "airbook_name";
	public final static String SHARE_PREFERENCE_PSW = "airbook_psw";
	public final static String SHARE_PREFERENCE_Id = "airbook_id";
	public final static String SHARE_PREFERENCE_LOGIN_AUTO = "airbook_login_auto";
	public final static String SHARE_PREFERENCE_STORED = "air_data_stored";

	public static String getSDPath() {
		File sdcard = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdcard = Environment.getExternalStorageDirectory();
		}
		return sdcard.toString();
	}

	public static String getDataPath() {
		String dataPath = null;
		String sdpath = getSDPath();
		if (sdpath != null) {
			dataPath = sdpath + "/airbook/";
			File file = new File(dataPath);
			Log.i("air", "file:" + file.exists());
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		return dataPath;
	}
}