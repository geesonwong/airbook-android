package info.airbook.activity;

import info.airbook.R;
import info.airbook.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FriendListActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);

		ListView lsListView = (ListView) findViewById(R.id.friends);
		List<Map<String, Object>> listItems = getListItems();
		lsListView.setAdapter(new MyAdapter(this, listItems));

		lsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(FriendListActivity.this,
						FriendInfoActivity.class));
			}
		});
	}

	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.avatar);
			map.put("name", "name" + i);
			map.put("call", R.drawable.phone);
			listItems.add(map);
		}
		return listItems;

	}
}