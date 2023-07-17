package com.example.yumyumnow.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.yumyumnow.database.DBHelper;
import com.example.yumyumnow.dto.UserDTO;
import com.example.yumyumnow.dto.UserLoginDTO;
import com.example.yumyumnow.dto.UserRegisterDTO;
import com.example.yumyumnow.provider.Provider;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class UserDAO {

    private final Context context;
    private static final Uri uri = Provider.uriUser;

    public UserDAO(Context context) {
        this.context = context;
    }

    public UserDTO getUserByUsername(String username) {

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_USER_ID,
                DBHelper.COL_USER_USERNAME,
                DBHelper.COL_USER_FULL_NAME,
                DBHelper.COL_USER_AVATAR
        };

        //check equal ignore case
        String selection = "LOWER(" + DBHelper.COL_USER_USERNAME + ") = ? ";
        String[] selectionArgs = new String[]{username.toLowerCase()};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        return retrieveUserFromCursor(cursor);

    }

    public UserDTO getUserById(int id) {

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_USER_ID,
                DBHelper.COL_USER_USERNAME,
                DBHelper.COL_USER_FULL_NAME,
                DBHelper.COL_USER_AVATAR
        };

        //check equal ignore case
        String selection = DBHelper.COL_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        return retrieveUserFromCursor(cursor);

    }

    public UserDTO login(UserLoginDTO credential) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_USER_ID,
                DBHelper.COL_USER_USERNAME,
                DBHelper.COL_USER_FULL_NAME,
                DBHelper.COL_USER_AVATAR
        };
        //check equal ignore case
        String selection = "LOWER(" + DBHelper.COL_USER_USERNAME + ") = ? AND " +
                DBHelper.COL_USER_PASSWORD + " = ?";
        String[] selectionArgs = new String[]{credential.getUsername().toLowerCase(), credential.getPassword()};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        return retrieveUserFromCursor(cursor);
    }

    public UserDTO register(UserRegisterDTO register) {
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_USER_USERNAME, register.getUsername());
        cv.put(DBHelper.COL_USER_FULL_NAME, register.getFullName());
        cv.put(DBHelper.COL_USER_PASSWORD, register.getPassword());
        try {
            Uri result = contentResolver.insert(uri, cv);
            if (result != null) {
                long userId = ContentUris.parseId(result);
                return getUserById((int) userId);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {

        if (newPassword == null || newPassword.trim().length() < 4 || oldPassword == null) {
            return false;
        }

        ContentResolver contentResolver = context.getContentResolver();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_USER_PASSWORD, newPassword);
        String selection = DBHelper.COL_USER_ID + " = ? AND " + DBHelper.COL_USER_PASSWORD + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(userId), oldPassword};

        int result = contentResolver.update(uri, cv, selection, selectionArgs);
        return (result > 0);

    }

    private String getUserPassword(int id) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_USER_PASSWORD
        };

        //check equal ignore case
        String selection = DBHelper.COL_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);

        String password = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nPassword = cursor.getColumnIndex(DBHelper.COL_USER_PASSWORD);

                password = cursor.getString(nPassword);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return password;
    }

    private UserDTO retrieveUserFromCursor(Cursor cursor) {
        UserDTO tmp = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nId = cursor.getColumnIndex(DBHelper.COL_USER_ID);
                int nUsername = cursor.getColumnIndex(DBHelper.COL_USER_USERNAME);
                int nFullName = cursor.getColumnIndex(DBHelper.COL_USER_FULL_NAME);
                int nAvatar = cursor.getColumnIndex(DBHelper.COL_USER_AVATAR);

                int id = cursor.getInt(nId);
                String username = cursor.getString(nUsername);

                String fullName = cursor.getString(nFullName);
                int avatar = cursor.getInt(nAvatar);
                tmp = new UserDTO();
                tmp.setUsername(username);
                tmp.setAvatar(avatar);
                tmp.setId(id);
                tmp.setFullName(fullName);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return tmp;
    }

    @NonNull
    private List<UserDTO> retrieveUsersFromCursor(Cursor cursor) {
        List<UserDTO> result = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nId = cursor.getColumnIndex(DBHelper.COL_USER_ID);
                int nUsername = cursor.getColumnIndex(DBHelper.COL_USER_USERNAME);
                int nFullName = cursor.getColumnIndex(DBHelper.COL_USER_FULL_NAME);
                int nAvatar = cursor.getColumnIndex(DBHelper.COL_USER_AVATAR);

                do {
                    int id = cursor.getInt(nId);
                    String username = cursor.getString(nUsername);

                    String fullName = cursor.getString(nFullName);
                    int avatar = cursor.getInt(nAvatar);
                    UserDTO tmp = new UserDTO();
                    tmp.setUsername(username);
                    tmp.setAvatar(avatar);
                    tmp.setId(id);
                    tmp.setFullName(fullName);

                    result.add(tmp);
                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }
}
