package info.airbook.manager;

import info.airbook.dao.ContactDao;
import info.airbook.entity.Contact;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class ContactManager {

	private ContactDao contactDao;

	public ContactManager(SQLiteDatabase db) {
		contactDao = new ContactDao(db);
	}

	public List<Contact> getContacts() {
		List<Contact> contacts = contactDao.getAll();
		return contacts;
	}

	public void saveContacts(List<Contact> contacts) {
		for (Contact contact : contacts) {
			contactDao.save(contact);
		}
	}

	public void deleteAll() {
		contactDao.deleteAll();
	}
}