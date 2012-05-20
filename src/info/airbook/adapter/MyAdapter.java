package info.airbook.adapter;

import info.airbook.R;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {
	private Context context;

	private List<Map<String, Object>> listItems;

	private LayoutInflater listContainer;

	private boolean[] hasChecked;

	public final class ListItemView {
		public QuickContactBadge image;
		public TextView name;
		public ImageButton call;
		public ImageButton itemMenu;
	}

	public MyAdapter(Context context, List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context);
		this.listItems = listItems;
		hasChecked = new boolean[getCount()];
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.entry, null);
			// 获得控件对象
			listItemView.image = (QuickContactBadge) convertView
					.findViewById(R.id.avatar_list);
			listItemView.name = (TextView) convertView
					.findViewById(R.id.name_list);
			listItemView.call = (ImageButton) convertView
					.findViewById(R.id.phone_btn);
			listItemView.itemMenu = (ImageButton) convertView
					.findViewById(R.id.item_menu);
			// 设置空间集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字图片
		listItemView.image.setBackgroundResource((Integer) listItems.get(
				position).get("image"));
		listItemView.name.setText((String) listItems.get(position).get("name"));
		listItemView.call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "拨打电话", Toast.LENGTH_LONG).show();
			}
		});

		listItemView.itemMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "点击菜单", Toast.LENGTH_LONG).show();
			}
		});

		return convertView;
	}
}
