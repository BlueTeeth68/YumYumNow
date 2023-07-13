package com.example.yumyumnow.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class DBHelper extends SQLiteOpenHelper {

    private final Context context;
    public static final String DATABASE_NAME = "yumyum.db";

    public static final String TABLE_USER_NAME = "user";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_USERNAME = "username";
    public static final String COL_USER_FULL_NAME = "full_name";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_AVATAR = "avatar";

    public static final String TABLE_PRODUCT_NAME = "product";
    public static final String COL_PRODUCT_ID = "id";
    public static final String COL_PRODUCT_NAME = "name";
    public static final String COL_PRODUCT_DESCRIPTION = "description";
    public static final String COL_PRODUCT_IMAGE = "image";
    public static final String COL_PRODUCT_PRICE = "price";
    public static final String COL_PRODUCT_CATEGORY = "category";

    public static final String TABLE_CART_NAME = "cart";
    public static final String COL_CART_USER_ID = "user_id";
    public static final String COL_CART_PRODUCT_ID = "product_id";
    public static final String COL_CART_QUANTITY = "quantity";

    public static final String TABLE_BILL_NAME = "bill";
    public static final String COL_BILL_ID = "id";
    public static final String COL_BILL_USER_ID = "user_id";
    public static final String COL_BILL_TOTAL_PRICE = "total_price";
    public static final String COL_BILL_CREATE_DATE = "create_date";

    public static final String TABLE_BILL_DETAIL_NAME = "bill_detail";
    public static final String COL_BILL_DETAIL_BILL_ID = "bill_id";
    public static final String COL_BILL_DETAIL_PRODUCT_ID = "product_id";
    public static final String COL_BILL_DETAIL_QUANTITY = "quantity";
    public static final String COL_BILL_DETAIL_PRICE = "price";

    public static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USER_NAME +
                " (" + COL_USER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_USERNAME + " TEXT NOT NULL UNIQUE, " +
                COL_USER_PASSWORD + " TEXT NOT NULL, " +
                COL_USER_FULL_NAME + " TEXT NOT NULL, " +
                COL_USER_AVATAR + " INTEGER NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_PRODUCT_NAME +
                " (" + COL_PRODUCT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT NOT NULL, " +
                COL_PRODUCT_DESCRIPTION + " TEXT, " +
                COL_PRODUCT_IMAGE + " INTEGER NOT NULL, " +
                COL_PRODUCT_PRICE + " REAL NOT NULL, " +
                COL_PRODUCT_CATEGORY + " TEXT NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CART_NAME +
                " (" + COL_CART_USER_ID + " INTEGER NOT NULL, " +
                COL_CART_PRODUCT_ID + " INTEGER NOT NULL, " +
                COL_CART_QUANTITY + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COL_CART_USER_ID + ") REFERENCES " + TABLE_USER_NAME + "(" + COL_USER_ID + ")," +
                "FOREIGN KEY(" + COL_CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT_NAME + "(" + COL_PRODUCT_ID + ")," +
                "PRIMARY KEY (" + COL_CART_USER_ID + ", " + COL_CART_PRODUCT_ID + "));";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_BILL_NAME +
                " (" + COL_BILL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                COL_BILL_USER_ID + " INTEGER NOT NULL, " +
                COL_BILL_TOTAL_PRICE + " REAL NOT NULL, " +
                COL_BILL_CREATE_DATE + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_BILL_USER_ID + ") REFERENCES " + TABLE_USER_NAME + "(" + COL_USER_ID + "));";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_BILL_DETAIL_NAME +
                " (" + COL_BILL_DETAIL_BILL_ID + " INTEGER NOT NULL, " +
                COL_BILL_DETAIL_PRODUCT_ID + " INTEGER NOT NULL, " +
                COL_BILL_DETAIL_QUANTITY + " INTEGER NOT NULL, " +
                COL_BILL_DETAIL_PRICE + " REAL NOT NULL, " +
                "FOREIGN KEY(" + COL_BILL_DETAIL_BILL_ID + ") REFERENCES " + TABLE_BILL_NAME + "(" + COL_BILL_ID + ")," +
                "FOREIGN KEY(" + COL_BILL_DETAIL_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT_NAME + "(" + COL_PRODUCT_ID + ")," +
                "PRIMARY KEY (" + COL_BILL_DETAIL_BILL_ID + ", " + COL_BILL_DETAIL_PRODUCT_ID + "));";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL_NAME);
        // Create new tables
        onCreate(db);
    }
}
