package info.airbook.dao;

import info.airbook.entity.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContactDao {

	private SQLiteDatabase db;

	public ContactDao(SQLiteDatabase db) {
		this.db = db;
	}

	public void save(Contact contact) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateString = sdf.format(contact);
		db.execSQL(
				"INSERT INTO contact(name, base_email,base_phone,last_name,first_name.photo_path,create_time,remark,qq,home_page,addr,type) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { contact.getName(), contact.getBaseEmail(),
						contact.getBasePhone(), contact.getLastName(),
						contact.getFirstName(), contact.getPhotoPath(),
						dateString, contact.getRemark(), contact.getQq(),
						contact.getHomePage(), contact.getAddr(),
						contact.getType() });
	}

	public void update(Contact contact) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateString = sdf.format(contact);
		db.execSQL(
				"UPDATE contact SET name= ?,  base_email = ?, base_phone = ?, last_name = ?, first_name = ?, photo_path = ?, create_time = ?,remark = ?,qq = ?,home_page=?,addr=?,type=?   where id = ?",
				new Object[] { contact.getName(), contact.getBaseEmail(),
						contact.getBasePhone(), contact.getLastName(),
						contact.getFirstName(), contact.getPhotoPath(),
						dateString, contact.getRemark(), contact.getQq(),
						contact.getHomePage(), contact.getAddr(),
						contact.getType(), contact.getId() });
	}

	public List<Contact> getAll() {
		List<Contact> contacts = new ArrayList<Contact>();

		Cursor cursor = db.rawQuery("SELECT * FROM contact ", null);

		while (cursor.moveToNext()) {
			Contact contact = new Contact();
			contact.setId(cursor.getInt(0));
			contact.setName(cursor.getString(1));
			contact.setBaseEmail(cursor.getString(2));
			contact.setBasePhone(cursor.getString(3));
			contact.setLastName(cursor.getString(4));
			contact.setFirstName(cursor.getString(5));
			contact.setPhotoPath(cursor.getString(6));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				contact.setCreateTime(sdf.parse(cursor.getString(7)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			contact.setRemark(cursor.getString(8));
			contact.setQq(cursor.getString(9));
			contact.setHomePage(cursor.getString(10));
			contact.setAddr(cursor.getString(11));
			contact.setType(cursor.getInt(12));
			contacts.add(contact);
		}

		return contacts;
	}

	public Contact findById(String id) {
		Contact contact = new Contact();
		Cursor cursor = db.rawQuery("SELECT * FROM contact where id=?",
				new String[] { id });
		if (cursor.moveToNext()) {
			contact.setId(cursor.getInt(0));
			contact.setName(cursor.getString(1));
			contact.setBaseEmail(cursor.getString(2));
			contact.setBasePhone(cursor.getString(3));
			contact.setLastName(cursor.getString(4));
			contact.setFirstName(cursor.getString(5));
			contact.setPhotoPath(cursor.getString(6));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				contact.setCreateTime(sdf.parse(cursor.getString(7)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			contact.setRemark(cursor.getString(8));
			contact.setQq(cursor.getString(9));
			contact.setHomePage(cursor.getString(10));
			contact.setAddr(cursor.getString(11));
			contact.setType(cursor.getInt(12));
		}
		return contact;
	}

	public Contact findByName(String name) {
		Contact contact = new Contact();
		Cursor cursor = db.rawQuery("SELECT * FROM contact where name=?",
				new String[] { name });
		if (cursor.moveToNext()) {
			contact.setId(cursor.getInt(0));
			contact.setName(cursor.getString(1));
			contact.setBaseEmail(cursor.getString(2));
			contact.setBasePhone(cursor.getString(3));
			contact.setLastName(cursor.getString(4));
			contact.setFirstName(cursor.getString(5));
			contact.setPhotoPath(cursor.getString(6));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				contact.setCreateTime(sdf.parse(cursor.getString(7)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			contact.setRemark(cursor.getString(8));
			contact.setQq(cursor.getString(9));
			contact.setHomePage(cursor.getString(10));
			contact.setAddr(cursor.getString(11));
			contact.setType(cursor.getInt(12));
		}
		return contact;
	}
}
