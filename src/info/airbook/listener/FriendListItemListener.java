package info.airbook.listener;

import info.airbook.activity.FriendInfoActivity;
import info.airbook.entity.Contact;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FriendListItemListener implements OnItemClickListener {

	private Context context;
	private List<Contact> contacts;

	public FriendListItemListener(Context context, List<Contact> contacts) {
		this.contacts = contacts;
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Contact contact = contacts.get(position);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putParcelable("contact", contact);
		intent.putExtras(bundle);
		intent.setClass(context, FriendInfoActivity.class);
		context.startActivity(intent);
	}
}
