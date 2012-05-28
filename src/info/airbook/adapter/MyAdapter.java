package info.airbook.adapter;

import info.airbook.R;
import info.airbook.entity.Contact;
import info.airbook.util.AsyncViewTask;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {
	private Context context;

	private List<Contact> listItems;

	private LayoutInflater listContainer;
	private PopupWindow pWindow;

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

	public void setListItems(List<Contact> listItems) {
		this.listItems = listItems;
	}

	public List<Contact> getListItems() {
		return listItems;
	}

	@Override
	public int getCount() {
		if (listItems == null) {
			return 0;
		}
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
		final Contact contact = listItems.get(position);
		listItemView.image.setTag(contact.getPhotoPath());
		new AsyncViewTask().execute(listItemView.image);
		if (contact.getFirstName().isEmpty() && contact.getLastName().isEmpty()) {
			listItemView.name.setText(contact.getName());
		} else {
			listItemView.name.setText(contact.getLastName()
					+ contact.getFirstName());
		}
		listItemView.call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "拨打电话", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ contact.getPhotoPath()));
				context.startActivity(intent);

			}
		});

		listItemView.itemMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPupupMenu(context, v);
			}
		});

		return convertView;
	}

	public void showPupupMenu(Context context, View view) {
		if (pWindow == null) {
			pWindow = new PopupWindow(context);
		}
		if (pWindow != null && pWindow.isShowing()) {
			pWindow.dismiss();
			return;
		}
		View view2 = LayoutInflater.from(context).inflate(R.layout.popu_menu,
				null);
		List<String> items = new ArrayList<String>();
		items.add("查看");
		items.add("拨出");
		items.add("删除");
		pWindow.setContentView(view2);
		pWindow.setWidth(130);
		pWindow.setHeight(138);
		ListView listView = (ListView) pWindow.getContentView().findViewById(
				R.id.menu_list);
		listView.setAdapter(new PopuMenuAdapter(context, items));
		pWindow.setFocusable(true);
		pWindow.showAsDropDown(view);
	}

}
