package info.airbook.adapter;

import info.airbook.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopuMenuAdapter extends BaseAdapter {

	private List<String> items;

	private LayoutInflater listContainer;
	private PopupWindow pWindow;

	public final class ListItemView {
		public TextView item;
	}

	public PopuMenuAdapter(Context context, List<String> items) {
		listContainer = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount() {
		if (items == null) {
			return 0;
		}
		return items.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.popu_menu_item, null);
			listItemView.item = (TextView) convertView
					.findViewById(R.id.menu_item);
			listItemView.item.setText(items.get(position));
		}
		return convertView;
	}

}
