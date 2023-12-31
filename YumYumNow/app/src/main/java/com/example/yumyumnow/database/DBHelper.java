package com.example.yumyumnow.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.yumyumnow.R;

@SuppressWarnings("unused")
public class DBHelper extends SQLiteOpenHelper {

    private final Context context;
    private int defaultAvatar;
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

    public static final int DATABASE_VERSION = 10;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        defaultAvatar = R.drawable.avatar;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USER_NAME + " (" + COL_USER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COL_USER_USERNAME + " TEXT NOT NULL UNIQUE, " + COL_USER_PASSWORD + " TEXT NOT NULL, " + COL_USER_FULL_NAME + " TEXT NOT NULL, " + COL_USER_AVATAR + " INTEGER NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_PRODUCT_NAME + " (" + COL_PRODUCT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COL_PRODUCT_NAME + " TEXT NOT NULL, " + COL_PRODUCT_DESCRIPTION + " TEXT, " + COL_PRODUCT_IMAGE + " INTEGER NOT NULL, " + COL_PRODUCT_PRICE + " REAL NOT NULL, " + COL_PRODUCT_CATEGORY + " TEXT NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CART_NAME + " (" + COL_CART_USER_ID + " INTEGER NOT NULL, " + COL_CART_PRODUCT_ID + " INTEGER NOT NULL, " + COL_CART_QUANTITY + " INTEGER NOT NULL, " + "FOREIGN KEY(" + COL_CART_USER_ID + ") REFERENCES " + TABLE_USER_NAME + "(" + COL_USER_ID + ")," + "FOREIGN KEY(" + COL_CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT_NAME + "(" + COL_PRODUCT_ID + ")," + "PRIMARY KEY (" + COL_CART_USER_ID + ", " + COL_CART_PRODUCT_ID + "));";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_BILL_NAME + " (" + COL_BILL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COL_BILL_USER_ID + " INTEGER NOT NULL, " + COL_BILL_TOTAL_PRICE + " REAL NOT NULL, " + COL_BILL_CREATE_DATE + " TEXT NOT NULL, " + "FOREIGN KEY(" + COL_BILL_USER_ID + ") REFERENCES " + TABLE_USER_NAME + "(" + COL_USER_ID + "));";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_BILL_DETAIL_NAME + " (" + COL_BILL_DETAIL_BILL_ID + " INTEGER NOT NULL, " + COL_BILL_DETAIL_PRODUCT_ID + " INTEGER NOT NULL, " + COL_BILL_DETAIL_QUANTITY + " INTEGER NOT NULL, " + COL_BILL_DETAIL_PRICE + " REAL NOT NULL, " + "FOREIGN KEY(" + COL_BILL_DETAIL_BILL_ID + ") REFERENCES " + TABLE_BILL_NAME + "(" + COL_BILL_ID + ")," + "FOREIGN KEY(" + COL_BILL_DETAIL_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCT_NAME + "(" + COL_PRODUCT_ID + ")," + "PRIMARY KEY (" + COL_BILL_DETAIL_BILL_ID + ", " + COL_BILL_DETAIL_PRODUCT_ID + "));";

        db.execSQL(query);
        initData(db);
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

    private void initData(SQLiteDatabase db) {

        //Insert sample data for User
        String insertQuery = "INSERT INTO " + TABLE_USER_NAME + " (" + COL_USER_USERNAME + ", " + COL_USER_PASSWORD + ", " + COL_USER_FULL_NAME + ", " + COL_USER_AVATAR + ") " + "VALUES (?, ?, ?, ?);";

        String username4 = "user";
        String password4 = "0000";
        String fullName4 = "Kim Da Mi";
        db.execSQL(insertQuery, new Object[]{username4, password4, fullName4, defaultAvatar});

        String username1 = "trihandsome";
        String password1 = "0000";
        String fullName1 = "Dao Minh Tri";
        db.execSQL(insertQuery, new Object[]{username1, password1, fullName1, defaultAvatar});

        String username2 = "trihandsomepro";
        String password2 = "0000";
        String fullName2 = "Dao Minh Tri";
        db.execSQL(insertQuery, new Object[]{username2, password2, fullName2, defaultAvatar});

        String username3 = "trihandsomepromax";
        String password3 = "0000";
        String fullName3 = "Dao Minh Tri";
        db.execSQL(insertQuery, new Object[]{username3, password3, fullName3, defaultAvatar});
        //Insert sample data for User


        // Insert sample data for product table
        initProducts(db);
        // Insert sample data for product table


        // Insert sample data for the cart table
        String insertCartQuery = "INSERT INTO " + TABLE_CART_NAME + " (" +
                COL_CART_USER_ID + ", " + COL_CART_PRODUCT_ID + ", " +
                COL_CART_QUANTITY + ") VALUES (?, ?, ?);";

        db.execSQL(insertCartQuery, new Object[]{1, 1, 2});
        db.execSQL(insertCartQuery, new Object[]{1, 2, 1});
        db.execSQL(insertCartQuery, new Object[]{1, 3, 3});
        db.execSQL(insertCartQuery, new Object[]{1, 10, 3});
        db.execSQL(insertCartQuery, new Object[]{1, 15, 3});
        db.execSQL(insertCartQuery, new Object[]{1, 25, 3});
        db.execSQL(insertCartQuery, new Object[]{1, 26, 2});
        // Insert sample data for the cart table


        // Insert sample data for the bill table
//        String insertBillQuery = "INSERT INTO " + TABLE_BILL_NAME + " (" +
//                COL_BILL_USER_ID + ", " + COL_BILL_TOTAL_PRICE + ", " +
//                COL_BILL_CREATE_DATE + ") VALUES (?, ?, ?);";
//
//        db.execSQL(insertBillQuery, new Object[]{1, 50.99, "2022-01-01"});
//        db.execSQL(insertBillQuery, new Object[]{2, 30.50, "2022-02-15"});
//        // Insert sample data for the bill table
//
//
//        // Insert sample data for the bill detail table
//        String insertBillDetailQuery = "INSERT INTO " + TABLE_BILL_DETAIL_NAME + " (" +
//                COL_BILL_DETAIL_BILL_ID + ", " + COL_BILL_DETAIL_PRODUCT_ID + ", " +
//                COL_BILL_DETAIL_QUANTITY + ", " + COL_BILL_DETAIL_PRICE + ") VALUES (?, ?, ?, ?);";
//
//        db.execSQL(insertBillDetailQuery, new Object[]{1, 1, 2, 20.99});
//        db.execSQL(insertBillDetailQuery, new Object[]{1, 2, 1, 19.99});
//        db.execSQL(insertBillDetailQuery, new Object[]{2, 3, 3, 15.50});
        // Insert sample data for the bill detail table
    }

    private void initProducts(SQLiteDatabase db) {
        // Insert sample data for product table
        String insertProductQuery = "INSERT INTO " + TABLE_PRODUCT_NAME + " (" +
                COL_PRODUCT_NAME + ", " + COL_PRODUCT_DESCRIPTION + ", " +
                COL_PRODUCT_IMAGE + ", " + COL_PRODUCT_PRICE + ", " +
                COL_PRODUCT_CATEGORY + ") VALUES (?, ?, ?, ?, ?);";

        // Fast food products for children
        db.execSQL(insertProductQuery, new Object[]{"Happy Meal", "A popular kid's meal from McDonald's that includes a burger, fries, and a toy.", R.drawable.happy_meal, 5.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Nuggets", "Bite-sized pieces of chicken that are breaded and fried. A favorite among kids!", R.drawable.nuggets, 3.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Kids Cheeseburger", "A classic cheeseburger from Wendy's that's just the right size for kids.", R.drawable.kid_cheese_burger, 2.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Mac and Cheese", "A creamy and cheesy pasta dish that's a staple on many kids' menus.", R.drawable.mac_and_cheese, 4.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Mini Corn Dogs", "Hot dogs coated in a cornmeal batter and deep-fried. A fun and tasty snack for kids!", R.drawable.mini_corn_dog, 3.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Pizza", "A cheesy and delicious pie that's a favorite among kids. Choose from toppings like pepperoni, sausage, and veggies.", R.drawable.pizza, 8.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Chicken Fingers", "Tender strips of chicken that are breaded and fried. Served with dipping sauce for extra fun!", R.drawable.chicken_finger, 5.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Grilled Cheese", "Melted cheese between two slices of bread. A classic and comforting sandwich that's perfect for kids.", R.drawable.grilled_cheese, 3.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Hot Dog", "A classic American snack that's especially popular among kids. Top with ketchup, mustard, and relish for extra flavor!", R.drawable.hot_dog, 2.99, "Food"});

        // Popular drinks
        db.execSQL(insertProductQuery, new Object[]{"Coca-Cola", "A classic soda that's been around for over a century. Known for its sweet and fizzy taste.", R.drawable.coca_cola, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Pepsi", "A rival to Coca-Cola, Pepsi is another popular soda that's beloved by many. It has a slightly sweeter taste than Coke.", R.drawable.pepsi, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Sprite", "A refreshing lemon-lime soda that's perfect for quenching your thirst on a hot day.", R.drawable.sprite, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Fanta", "A fruity and colorful soda that comes in a variety of flavors, including orange, grape, and strawberry.", R.drawable.fanta, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Mountain Dew", "A citrus-flavored soda that's known for its bright green color. It's a favorite among gamers and extreme sports enthusiasts.", R.drawable.mountain_dew, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Dr. Pepper", "A unique soda with a blend of 23 flavors. Its taste is hard to describe, but it's often compared to cherry cola.", R.drawable.dr_peper, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Gatorade", "A sports drink that's designed to replenish electrolytes lost during exercise. Comes in a variety of flavors.", R.drawable.gatorade, 2.49, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Iced Tea", "A refreshing and lightly sweetened tea that's perfect for sipping on a hot day.", R.drawable.ice_tea, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Lemonade", "A sweet and tangy drink made with lemon juice and sugar. Perfect for quenching your thirst on a summer day.", R.drawable.lemonade, 2.49, "Drink"});

        // More fast food products for children
        db.execSQL(insertProductQuery, new Object[]{"Chicken Nuggets", "Bite-sized pieces of chicken that are breaded and fried. A favorite among kids!", R.drawable.chicken_nugget, 3.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Cheese Pizza", "A classic pizza topped with tomato sauce and melted cheese. A favorite among kids!", R.drawable.cheese_pizza, 8.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"French Fries", "Thin and crispy potato sticks that are often served as a side dish with burgers and sandwiches.", R.drawable.french_fries, 2.49, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Chocolate Milkshake", "A creamy and indulgent drink made with chocolate ice cream and milk. Perfect for satisfying your sweet tooth!", R.drawable.chocolate_milkshake, 3.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Cheeseburger", "A classic burger topped with melted cheese. Can be customized with toppings like lettuce, tomato, and pickles.", R.drawable.cheese_burger, 5.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Chicken Sandwich", "A sandwich made with a crispy chicken patty, lettuce, and mayo. A favorite among kids and adults alike.", R.drawable.chicken_sandwich, 4.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Milk", "A nutritious drink that's rich in calcium and vitamins. Perfect for growing kids!", R.drawable.milk, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"Root Beer", "A sweet and bubbly soda with a unique flavor. Often served in a frosted mug.", R.drawable.root_beer, 1.99, "Drink"});
        db.execSQL(insertProductQuery, new Object[]{"M&M McFlurry", "A decadent dessert made with soft-serve ice cream and M&M candies. A popular treat at McDonald's!", R.drawable.mc_flurry, 3.99, "Food"});
        db.execSQL(insertProductQuery, new Object[]{"Oreo Milkshake", "A creamy and indulgent drink made with Oreo cookies and milk. Perfect for satisfying your sweet tooth!", R.drawable.oreo_milkshake, 3.99, "Drink"});
    }
}
