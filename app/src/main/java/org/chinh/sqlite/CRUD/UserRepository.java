package org.chinh.sqlite.CRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DbHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(DbHelper.COLUMN_ROLE, user.getRole());

        return database.insert(DbHelper.TABLE_USERS, null, values);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.TABLE_USERS, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_ID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_PASSWORD)));
        user.setRole(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_ROLE)));
        return user;
    }
    public User getUserById(long userId) {
        Cursor cursor = database.query(
                DbHelper.TABLE_USERS,
                null,
                DbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                cursor.close();
                return user;
            }
            cursor.close();
        }

        return null;
    }
    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DbHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(DbHelper.COLUMN_ROLE, user.getRole());

        return database.update(DbHelper.TABLE_USERS, values, DbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(long userId) {
        database.delete(DbHelper.TABLE_USERS, DbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(userId)});
    }
}
