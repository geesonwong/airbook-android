package info.airbook.util;

import info.airbook.entity.Account;
import info.airbook.entity.Contact;
import info.airbook.entity.LoginResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json2Entity {

	public LoginResult json2LoginResult(String jsonString) {
		LoginResult loginResult = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			String success = jsonObject.getString("success");
			String message = jsonObject.getString("message");
			loginResult = new LoginResult();
			loginResult.setSuccess(new Boolean(success));
			if (loginResult.isSuccess()) {
				JSONObject accountObject = new JSONObject(
						jsonObject.getString("account"));
				Account account = new Account();
				account.setAccount_id(accountObject.getString("_id"));
				account.setName(accountObject.getString("name"));
				account.setPassword(accountObject.getString("password"));
				loginResult.setAccount(account);
			}
			loginResult.setMessage(message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return loginResult;
	}

	public List<Contact> json2Contacts(String jsonString) {
		List<Contact> contacts = null;
		try {
			contacts = new ArrayList<Contact>();
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				Contact contact = new Contact();
				JSONObject jsonObject = jsonArray.optJSONObject(i);
				contact.setName(jsonObject.getString("name"));
				contact.setFirstName(jsonObject.getString("first_name"));
				contact.setLastName(jsonObject.getString("last_name"));
				contact.setBasePhone(jsonObject.getString("base_phone"));
				contact.setBaseEmail(jsonObject.getString("base_email"));
				contact.setPhotoPath(jsonObject.getString("photo_path"));
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				try {
					contact.setCreateTime(sdf.parse(jsonObject
							.getString("create_time")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				contact.setQq(jsonObject.getString("qq"));
				contact.setHomePage(jsonObject.getString("homepage"));
				contact.setAddr(jsonObject.getString("addr"));
				contact.setType(jsonObject.getInt("type"));
				contacts.add(contact);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return contacts;
	}
}
