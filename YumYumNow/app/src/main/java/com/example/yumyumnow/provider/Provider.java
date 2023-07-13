package com.example.yumyumnow.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yumyumnow.database.DBHelper;

@SuppressWarnings("unused")
public class Provider extends ContentProvider {

    public static final String AUTHOR = "com.example.yumyumnow.provider";
    public static final String URL_TABLE_USER = "content://" + AUTHOR + "/" + DBHelper.TABLE_USER_NAME;
    public static final String URL_TABLE_PRODUCT = "content://" + AUTHOR + "/" + DBHelper.TABLE_PRODUCT_NAME;
    public static final String URL_TABLE_CART = "content://" + AUTHOR + "/" + DBHelper.TABLE_CART_NAME;
    public static final String URL_TABLE_BILL = "content://" + AUTHOR + "/" + DBHelper.TABLE_BILL_NAME;
    public static final String URL_TABLE_BILL_DETAIL = "content://" + AUTHOR + "/" + DBHelper.TABLE_BILL_DETAIL_NAME;
    public static final Uri uriUser = Uri.parse(URL_TABLE_USER);
    public static final Uri uriBill = Uri.parse(URL_TABLE_BILL);
    public static final Uri uriProduct = Uri.parse(URL_TABLE_PRODUCT);
    public static final Uri uriCart = Uri.parse(URL_TABLE_CART);
    public static final Uri uriBillDetail = Uri.parse(URL_TABLE_BILL_DETAIL);


    private SQLiteDatabase db;
    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOR, DBHelper.TABLE_USER_NAME, 1);
        uriMatcher.addURI(AUTHOR, DBHelper.TABLE_PRODUCT_NAME, 2);
        uriMatcher.addURI(AUTHOR, DBHelper.TABLE_CART_NAME, 3);
        uriMatcher.addURI(AUTHOR, DBHelper.TABLE_BILL_NAME, 4);
        uriMatcher.addURI(AUTHOR, DBHelper.TABLE_BILL_DETAIL_NAME, 5);
    }

    @Override
    public boolean onCreate() {
        DBHelper dbHelper = new DBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = db.query(DBHelper.TABLE_USER_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case 2:
                cursor = db.query(DBHelper.TABLE_PRODUCT_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case 3:
                cursor = db.query(DBHelper.TABLE_CART_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case 4:
                cursor = db.query(DBHelper.TABLE_BILL_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case 5:
                cursor = db.query(DBHelper.TABLE_BILL_DETAIL_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknow uri " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String mimeType;

        switch (uriMatcher.match(uri)) {
            case 1:
                mimeType = "vnd.android.cursor.dir/" + AUTHOR + "." + DBHelper.TABLE_USER_NAME;
                break;
            case 2:
                mimeType = "vnd.android.cursor.dir/" + AUTHOR + "." + DBHelper.TABLE_PRODUCT_NAME;
                break;
            case 3:
                mimeType = "vnd.android.cursor.dir/" + AUTHOR + "." + DBHelper.TABLE_CART_NAME;
                break;
            case 4:
                mimeType = "vnd.android.cursor.dir/" + AUTHOR + "." + DBHelper.TABLE_BILL_NAME;
                break;
            case 5:
                mimeType = "vnd.android.cursor.dir/" + AUTHOR + "." + DBHelper.TABLE_BILL_DETAIL_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
        return mimeType;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id;

        switch (uriMatcher.match(uri)) {
            case 1:
                id = db.insert(DBHelper.TABLE_USER_NAME, null, values);
                break;
            case 2:
                id = db.insert(DBHelper.TABLE_PRODUCT_NAME, null, values);
                break;
            case 3:
                id = db.insert(DBHelper.TABLE_CART_NAME, null, values);
                break;
            case 4:
                id = db.insert(DBHelper.TABLE_BILL_NAME, null, values);
                break;
            case 5:
                id = db.insert(DBHelper.TABLE_BILL_DETAIL_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }

        if (id != -1) {
            // Notify any observers that the data has changed
            getContext().getContentResolver().notifyChange(uri, null);
            //Return the uri of new item inserted
            return ContentUris.withAppendedId(uri, id);
        } else {
            throw new SQLException("Fail to insert row to uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case 1:
                count = db.delete(DBHelper.TABLE_USER_NAME, selection, selectionArgs);
                break;
            case 2:
                count = db.delete(DBHelper.TABLE_PRODUCT_NAME, selection, selectionArgs);
                break;
            case 3:
                count = db.delete(DBHelper.TABLE_CART_NAME, selection, selectionArgs);
                break;
            case 4:
                count = db.delete(DBHelper.TABLE_BILL_NAME, selection, selectionArgs);
                break;
            case 5:
                count = db.delete(DBHelper.TABLE_BILL_DETAIL_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknow Uri " + uri);
        }
        // Notify any observers that the data has changed
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case 1:
                count = db.update(DBHelper.TABLE_USER_NAME, values, selection, selectionArgs);
                break;
            case 2:
                count = db.update(DBHelper.TABLE_PRODUCT_NAME, values, selection, selectionArgs);
                break;
            case 3:
                count = db.update(DBHelper.TABLE_CART_NAME, values, selection, selectionArgs);
                break;
            case 4:
                count = db.update(DBHelper.TABLE_BILL_NAME, values, selection, selectionArgs);
                break;
            case 5:
                count = db.update(DBHelper.TABLE_BILL_DETAIL_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri " + uri);
        }
        // Notify any observers that the data has changed
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
