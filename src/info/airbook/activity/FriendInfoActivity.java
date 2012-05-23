package info.airbook.activity;

import info.airbook.R;
import info.airbook.entity.Contact;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class FriendInfoActivity extends Activity {

	private TextView name;
	private QuickContactBadge avatar;
	private TextView remark;
	private TextView firstName;
	private TextView lastName;
	private TextView basePhone;
	private TextView baseEmail;
	private TextView createTime;
	private TextView qq;
	private TextView homePage;
	private TextView addr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_info);

		name = (TextView) findViewById(R.id.name_info);
		avatar = (QuickContactBadge) findViewById(R.id.avatar_info);
		remark = (TextView) findViewById(R.id.remark_info);
		firstName = (TextView) findViewById(R.id.first_name);
		lastName = (TextView) findViewById(R.id.last_name);
		basePhone = (TextView) findViewById(R.id.base_phone);
		baseEmail = (TextView) findViewById(R.id.base_email);
		createTime = (TextView) findViewById(R.id.create_time);
		qq = (TextView) findViewById(R.id.qq);
		homePage = (TextView) findViewById(R.id.home_page);
		addr = (TextView) findViewById(R.id.addr);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		Contact contact = (Contact) bundle.getSerializable("contact");
		name.setText(contact.getName());
		avatar.setBackgroundResource(R.drawable.avatar);
		remark.setText(contact.getRemark());
		firstName.setText(contact.getFirstName());
		lastName.setText(contact.getLastName());
		basePhone.setText(contact.getBasePhone());
		baseEmail.setText(contact.getBaseEmail());
		qq.setText(contact.getQq());
		homePage.setText(contact.getHomePage());
		addr.setText(contact.getAddr());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String time = simpleDateFormat.format(contact.getCreateTime());
		createTime.setText(time);
	}
}
