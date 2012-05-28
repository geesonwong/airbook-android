package info.airbook.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class NetConnection {

	public HttpURLConnection getConnection(String addr) {

		URL url;
		HttpURLConnection httpURLConnection = null;
		try {
			url = new URL(addr);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setRequestMethod("POST");
		} catch (Exception e) {
			Log.i("air", "网络连接出错");
			e.printStackTrace();
		}

		return httpURLConnection;
	}

	public String getResponeInfo(InputStream inputStream) throws Exception {

		StringBuilder builder = new StringBuilder();
		BufferedReader bufferedReader2 = new BufferedReader(
				new InputStreamReader(inputStream));
		for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
				.readLine()) {
			builder.append(s);
		}
		Log.i("air", ">>>>>>" + builder.toString());
		return builder.toString();
	}
}
