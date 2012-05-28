package info.airbook.activity;

import info.airbook.R;
import info.airbook.entity.Contact;
import info.airbook.util.AsyncViewTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class FriendInfoActivity extends Activity {

	private TextView name;
	private QuickContactBadge avatar;
	private TextView remark;
	private TextView trueName;
	private TextView basePhone;
	private TextView baseEmail;
	private TextView qq;
	private TextView homePage;
	private TextView addr;
	private TextView createTime;
	private TextView tags;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_info);

		trueName = (TextView) findViewById(R.id.name_info);
		avatar = (QuickContactBadge) findViewById(R.id.avatar_info);
		remark = (TextView) findViewById(R.id.comment);
		name = (TextView) findViewById(R.id.name);
		basePhone = (TextView) findViewById(R.id.base_phone);
		baseEmail = (TextView) findViewById(R.id.base_email);
		homePage = (TextView) findViewById(R.id.home_page);
		qq = (TextView) findViewById(R.id.qq);
		addr = (TextView) findViewById(R.id.addr);
		createTime = (TextView) findViewById(R.id.create_time);
		tags = (TextView) findViewById(R.id.tags);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		Contact contact = (Contact) bundle.getSerializable("contact");
		if (contact.getFirstName().isEmpty() && contact.getLastName().isEmpty()) {
			trueName.setText(contact.getName());
		} else {
			trueName.setText(contact.getLastName() + contact.getFirstName());
		}
		avatar.setTag(contact.getPhotoPath());
		new AsyncViewTask().execute(avatar);
		remark.setText(contact.getComment());
		name.setText(contact.getName());
		basePhone.setText(contact.getBasePhone());
		baseEmail.setText(contact.getBaseEmail());
		qq.setText(contact.getQq());
		homePage.setText(contact.getHomePage());
		addr.setText(contact.getAddr());
		tags.setText(contact.getTags());
		createTime.setText(contact.getCreateTime());
	}
}
