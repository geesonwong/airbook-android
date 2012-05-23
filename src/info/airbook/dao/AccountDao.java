package info.airbook.dao;

import info.airbook.entity.Account;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountDao {

	private SQLiteDatabase db;

	public AccountDao(SQLiteDatabase db) {
		this.db = db;
	}

	public void save(Account account) {
		db.execSQL("insert into account(name, password) values(?,?)",
				new Object[] { account.getName(), account.getPassword(), });
	}

	public void update(Account account) {
		db.execSQL("update account set name = ?,password=? where id=?",
				new Object[] { account.getName(), account.getPassword(),
						account.getId() });
	}

	public Account findFirst() {
		Account account = null;
		Cursor cursor = db.rawQuery("select * from account ", null);
		if (cursor.moveToNext()) {
			account = new Account();
			account.setId(cursor.getInt(0));
			account.setName(cursor.getString(1));
			account.setPassword(cursor.getString(2));
		}
		if (cursor != null) {
			cursor.close();
		}
		return account;
	}

}
