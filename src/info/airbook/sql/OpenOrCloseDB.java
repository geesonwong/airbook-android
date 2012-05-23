package info.airbook.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OpenOrCloseDB {

	public static SQLiteDatabase openDB(Context context) {
		SQLiteDatabase db;
		AirbookOpenHelper airbookOpenHelper = new AirbookOpenHelper(context);
		db = airbookOpenHelper.getReadableDatabase();
		return db;

	}

	public static void closeDB(SQLiteDatabase db) {
		if (db != null) {
			db.close();
		}
	}
}
