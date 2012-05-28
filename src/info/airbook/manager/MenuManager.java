package info.airbook.manager;

import info.airbook.R;
import info.airbook.listener.MenuListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MenuManager {

	private Context context;
	private GridView gridView;
	private Handler handler;
	private Dialog dialog;

	public MenuManager(Context context, GridView gridView, Handler handler,
			Dialog dialog) {
		this.context = context;
		this.gridView = gridView;
		this.handler = handler;
		this.dialog = dialog;
	}

	public void addMenu() {
		List<Map<String, Object>> items = items();
		SimpleAdapter adapter = createAdapter(items);
		gridView.setAdapter(adapter);
		MenuListener menuListener = new MenuListener(context, handler, dialog);
		gridView.setOnItemClickListener(menuListener);
	}

	public SimpleAdapter createAdapter(List<Map<String, Object>> items) {

		SimpleAdapter adapter = new SimpleAdapter(context, items,
				R.layout.menu_item, new String[] { "icon" },
				new int[] { R.id.ItemImage });
		return adapter;
	}

	public List<Map<String, Object>> items() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("icon", R.drawable.refresh);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("icon", R.drawable.setting);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("icon", R.drawable.about);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("icon", R.drawable.exit);

		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);

		return list;
	}
}
