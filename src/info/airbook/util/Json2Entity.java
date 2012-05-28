package info.airbook.util;

import info.airbook.entity.Account;
import info.airbook.entity.Contact;
import info.airbook.entity.ContactResult;
import info.airbook.entity.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

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

	public static ContactResult json2ContactResult(String json) {
		ContactResult contactResult = new ContactResult();
		JSONObject resutlJsonObject = string2jsonObject(json);
		contactResult.setSuccess(resutlJsonObject.optBoolean("success"));
		if (contactResult.isSuccess()) {
			String contacterString = resutlJsonObject.optString("results");
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(contacterString);
				List<Contact> contacts = new ArrayList<Contact>();
				for (int i = 0; i < jsonArray.length(); i++) {
					if (jsonArray.optString(i) != null) {

						Contact contact = new Contact();
						JSONObject contactJsonObject = string2jsonObject(jsonArray
								.optString(i));
						if (contactJsonObject != null) {

							JSONObject contacterJsonObject = string2jsonObject(contactJsonObject
									.optString("_contacter"));

							Log.i("air",
									"contact》》"
											+ jsonArray.length()
											+ "》》"
											+ contactJsonObject
													.optString("_contacter"));

							if (contacterJsonObject != null) {
								contact.setId(contacterJsonObject
										.optString("_id"));
								contact.setName(contacterJsonObject
										.optString("name"));
								contact.setBasePhone(contacterJsonObject
										.optString("base_phone"));
								contact.setBaseEmail(contacterJsonObject
										.optString("base_email"));
								contact.setFirstName(contacterJsonObject
										.optString("first_name"));
								contact.setLastName(contacterJsonObject
										.optString("last_name"));
								contact.setPhotoPath(contacterJsonObject
										.optString("photo_path"));

								contact.setComment(contactJsonObject
										.optString("comment"));
								contact.setCreateTime(contactJsonObject
										.optString("create_time"));
								contact.setPigeohole(contactJsonObject
										.optString("pigeohole"));
								JSONArray jsonArray2 = new JSONArray(
										contactJsonObject.optString("tags"));
								StringBuilder stringBuilder = new StringBuilder();
								for (int j = 0; j < jsonArray2.length(); j++) {
									stringBuilder.append(jsonArray2
											.optString(j) + " ");
								}
								contact.setTags(stringBuilder.toString());
								contact.setState(contactJsonObject
										.optInt("state"));
								contacts.add(contact);
							}
							contactResult.setContacts(contacts);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return contactResult;
	}

	public static Contact json2Contact(Contact contact, String json) {
		JSONObject contactJsonObject = string2jsonObject(json);
		contact.setQq(contactJsonObject.optString("qq"));
		contact.setHomePage(contactJsonObject.optString("homepage"));
		contact.setAddr(contactJsonObject.optString("addr"));
		contact.setType(contactJsonObject.optInt("type"));
		return contact;
	}

	public Map<String, Object> json2Map(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject resultJjsonObject = new JSONObject(json);
			map.put("success", resultJjsonObject.optBoolean("success"));
			map.put("results", json2List(json));
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("air", "获取联系人json是出错");
		}
		return map;
	}

	public List<String> json2List(String json) {
		JSONObject resultJjsonObject;
		List<String> jsonList = new ArrayList<String>();
		JSONArray jsonArray;
		try {
			resultJjsonObject = new JSONObject(json);
			jsonArray = new JSONArray(resultJjsonObject.optString("results"));

			for (int i = 0; i < jsonArray.length(); i++) {
				jsonList.add(jsonArray.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("air", "获取果断jsonarray");
		}
		return jsonList;
	}

	public static JSONObject string2jsonObject(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("air", "将String转为jsonObject时出错");
		}
		return jsonObject;
	}
}
