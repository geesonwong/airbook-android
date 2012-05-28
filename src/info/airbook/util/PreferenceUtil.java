package info.airbook.util;

import info.airbook.entity.Data;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	public static SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences spfl = context.getSharedPreferences(
				Data.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
		return spfl;
	}
}
