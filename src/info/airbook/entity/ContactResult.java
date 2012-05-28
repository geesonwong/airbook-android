package info.airbook.entity;

import java.util.List;

public class ContactResult {

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	private boolean success;
	private List<Contact> contacts;

}
