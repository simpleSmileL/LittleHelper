package com.example.lookworld.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressDBDao {
	/**
	 * @param context
	 * @param mobileNum
	 * @return
	 */
	public static String findLocation(Context context, String mobileNum) {
		String path = context.getFilesDir() + "/address.db";
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		// 判断号码是否为电话号码
		boolean result = mobileNum.matches("^1[34578]\\d{9}");
		String location = "号码不存在";
		if (result) {
			Cursor cursor = db
					.rawQuery(
							"select location from data2 where id=(select outkey from data1 where id =?)",
							new String[] { mobileNum.substring(0, 7) });
			if (cursor.moveToNext()) {
				location = cursor.getString(0);
			}
			cursor.close();

		} else {
			switch (mobileNum.length()) {
			case 3:
				location = "报警电话";
				break;

			case 4:
				location = "模拟器";
				break;

			case 5:
				location = "商业客服电话";
				break;

			case 7:
				location = "本地电话";
				break;

			case 8:
				location = "本地电话";
				break;
			default:
				if (mobileNum.length() > 10 && mobileNum.startsWith("0")) {
					Cursor cursor = db.rawQuery(
							"select location from data2 where area=?",
							new String[] { mobileNum.substring(1, 3) });
					if (cursor.moveToNext()) {
						String temp = cursor.getString(0);

						location = temp.substring(0, temp.length() - 2);

					}
					cursor.close();

					cursor = db.rawQuery(
							"select location from data2 where area=?",
							new String[] { mobileNum.substring(1, 4) });
					if (cursor.moveToNext()) {
						String temp = cursor.getString(0);

						location = temp.substring(0, temp.length() - 2);

					}
					cursor.close();

				}
			}

		}
		db.close();
		return location;
	}

}
