package info.airbook.activity;

import info.airbook.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource

		addPreferencesFromResource(R.layout.setting_preference);
	}
}
