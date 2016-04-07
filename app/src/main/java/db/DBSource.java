package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.User;

public class DBSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_PICTURE,
            DBHelper.COLUMN_TITLE,
            DBHelper.COLUMN_TEXT};

    public DBSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createRecord(String id, String name, String picture, String title, String text) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, id);
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_PICTURE, picture);
        values.put(DBHelper.COLUMN_TITLE, title);
        values.put(DBHelper.COLUMN_TEXT, text);
        long insertId = database.insert(DBHelper.TABLE_USERS, null,
                values);
    }

    public boolean deleteRecord(int id) {
        return database.delete(dbHelper.TABLE_USERS, dbHelper.COLUMN_ID + "=" +
                String.valueOf(id), null) > 0;
    }


    public User getRecordsById(int id) {
        String query = "SELECT * FROM " + dbHelper.TABLE_USERS +
                " WHERE " + dbHelper.COLUMN_ID + " = " + String.valueOf(id);
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        return cursorToRecord(c);
    }

    public List<User> getAllRecords() {

        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + dbHelper.TABLE_USERS,
                        null);
        List<User> records = new ArrayList<User>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User u = cursorToRecord(cursor);
            records.add(u);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }


    private User cursorToRecord(Cursor cursor) {
        User record = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return record;
    }


}
