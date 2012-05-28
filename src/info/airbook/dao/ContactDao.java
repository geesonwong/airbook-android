package info.airbook.dao;

import info.airbook.entity.Contact;

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
		db.execSQL(
				"INSERT INTO contact(id,name, base_email,base_phone,last_name,first_name,photo_path,create_time,comment,qq,home_page,addr,type,tags,state,pigeohole) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { contact.getId(), contact.getName(),
						contact.getBaseEmail(), contact.getBasePhone(),
						contact.getLastName(), contact.getFirstName(),
						contact.getPhotoPath(), contact.getCreateTime(),
						contact.getComment(), contact.getQq(),
						contact.getHomePage(), contact.getAddr(),
						contact.getType(), contact.getTags(),
						contact.getState(), contact.getPigeohole() });
	}

	public void update(Contact contact) {
		db.execSQL(
				"UPDATE contact SET name= ?,  base_email = ?, base_phone = ?, last_name = ?, first_name = ?, photo_path = ?, create_time = ?,comment = ?,qq = ?,home_page=?,addr=?,type=?  tags=?, state=?, pigeohole=?  WHERE	 id = ?",
				new Object[] { contact.getName(), contact.getBaseEmail(),
						contact.getBasePhone(), contact.getLastName(),
						contact.getFirstName(), contact.getPhotoPath(),
						contact.getCreateTime(), contact.getComment(),
						contact.getQq(), contact.getHomePage(),
						contact.getAddr(), contact.getType(),
						contact.getTags(), contact.getState(),
						contact.getPigeohole(), contact.getId() });
	}

	public List<Contact> getAll() {
		List<Contact> contacts = new ArrayList<Contact>();

		Cursor cursor = db.rawQuery("SELECT * FROM contact ", null);

		while (cursor.moveToNext()) {
			Contact contact = new Contact();
			contact.setId(cursor.getString(1));
			contact.setName(cursor.getString(2));
			contact.setBaseEmail(cursor.getString(3));
			contact.setBasePhone(cursor.getString(4));
			contact.setLastName(cursor.getString(5));
			contact.setFirstName(cursor.getString(6));
			contact.setPhotoPath(cursor.getString(7));
			contact.setCreateTime(cursor.getString(8));

			contact.setComment(cursor.getString(9));
			contact.setQq(cursor.getString(10));
			contact.setHomePage(cursor.getString(11));
			contact.setAddr(cursor.getString(12));
			contact.setType(cursor.getInt(13));
			contact.setTags(cursor.getString(14));
			contact.setState(cursor.getInt(15));
			contact.setPigeohole(cursor.getString(16));
			contacts.add(contact);
		}

		return contacts;
	}

	public Contact findById(String id) {
		Contact contact = new Contact();
		Cursor cursor = db.rawQuery("SELECT * FROM contact where id=?",
				new String[] { id });
		if (cursor.moveToNext()) {
			contact.setId(cursor.getString(1));
			contact.setName(cursor.getString(2));
			contact.setBaseEmail(cursor.getString(3));
			contact.setBasePhone(cursor.getString(4));
			contact.setLastName(cursor.getString(5));
			contact.setFirstName(cursor.getString(6));
			contact.setPhotoPath(cursor.getString(7));
			contact.setCreateTime(cursor.getString(8));

			contact.setComment(cursor.getString(9));
			contact.setQq(cursor.getString(10));
			contact.setHomePage(cursor.getString(11));
			contact.setAddr(cursor.getString(12));
			contact.setType(cursor.getInt(13));
			contact.setTags(cursor.getString(14));
			contact.setState(cursor.getInt(15));
			contact.setPigeohole(cursor.getString(16));
		}
		return contact;
	}

	public Contact findByName(String name) {
		Contact contact = new Contact();
		Cursor cursor = db.rawQuery("SELECT * FROM contact where name=?",
				new String[] { name });
		if (cursor.moveToNext()) {
			contact.setId(cursor.getString(1));
			contact.setName(cursor.getString(2));
			contact.setBaseEmail(cursor.getString(3));
			contact.setBasePhone(cursor.getString(4));
			contact.setLastName(cursor.getString(5));
			contact.setFirstName(cursor.getString(6));
			contact.setPhotoPath(cursor.getString(7));
			contact.setCreateTime(cursor.getString(8));

			contact.setComment(cursor.getString(9));
			contact.setQq(cursor.getString(10));
			contact.setHomePage(cursor.getString(11));
			contact.setAddr(cursor.getString(12));
			contact.setType(cursor.getInt(13));
			contact.setTags(cursor.getString(14));
			contact.setState(cursor.getInt(15));
			contact.setPigeohole(cursor.getString(16));
		}
		return contact;
	}

	public void deleteAll() {
		db.execSQL("delete  from contact");
	}

}
