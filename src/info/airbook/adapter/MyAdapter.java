package info.airbook.adapter;

import info.airbook.R;
import info.airbook.entity.Contact;
import info.airbook.util.AsyncViewTask;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

	private List<Contact> listItems;

	private LayoutInflater listContainer;

	public final class ListItemView {
		public QuickContactBadge image;
		public TextView name;
		public ImageButton call;
		public ImageButton itemMenu;
	}

	public MyAdapter(Context context, List<Contact> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context);
		this.listItems = listItems;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.entry, null);
			listItemView.image = (QuickContactBadge) convertView
					.findViewById(R.id.avatar_list);
			listItemView.name = (TextView) convertView
					.findViewById(R.id.name_list);
			listItemView.call = (ImageButton) convertView
					.findViewById(R.id.phone_btn);
			listItemView.itemMenu = (ImageButton) convertView
					.findViewById(R.id.item_menu);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		listItemView.image.setTag(listItems.get(position).getPhotoPath());
		new AsyncViewTask().execute(listItemView.image);
		listItemView.name.setText((String) listItems.get(position).getName());
		listItemView.call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "拨打电话", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ listItems.get(position).getBasePhone()));
				context.startActivity(intent);

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
