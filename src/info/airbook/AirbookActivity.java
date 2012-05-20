package info.airbook;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AirbookActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.login);
		setContentView(R.layout.friend_list);

		ListView lsListView = (ListView) findViewById(R.id.friends);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.avatar);
			map.put("ItemTitle", "Level " + i);
			listItem.add(map);
		}
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,
				R.layout.entry, new String[] { "ItemImage", "ItemTitle" },
				new int[] { R.id.avatar_list, R.id.name_list });
		lsListView.setAdapter(listItemAdapter);
	}

}
