package com.example.yumyumnow.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.yumyumnow.database.DBHelper;
import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.provider.Provider;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ProductDAO {

    public static final String ASC = "asc";
    public static final String DESC = "desc";
    private final Context context;
    private static final Uri uri = Provider.uriProduct;

    public ProductDAO(Context context) {
        this.context = context;
    }

    public List<ProductDTO> getProductByFilter(String name, String category, String sortName, String sortPrice) {
        List<ProductDTO> result = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_PRODUCT_ID,
                DBHelper.COL_PRODUCT_NAME,
                DBHelper.COL_PRODUCT_CATEGORY,
                DBHelper.COL_PRODUCT_IMAGE,
                DBHelper.COL_PRODUCT_PRICE,
                DBHelper.COL_PRODUCT_DESCRIPTION
        };

        //filter selection
        String selection = "";
        ArrayList<String> selectionArgs = new ArrayList<>();
        if (name != null && !name.trim().equals("")) {
            selection = selection + " LOWER(" + DBHelper.COL_PRODUCT_NAME + ") LIKE ? ";
            selectionArgs.add(name.toLowerCase());
        }

        if (category != null && !category.trim().equals("")) {
            if (!selection.equals("")) {
                selection = selection + " AND ";
            }
            selection = selection + " LOWER(" + DBHelper.COL_PRODUCT_CATEGORY + ") = ? ";
            selectionArgs.add(category.toLowerCase());
        }

        String sortOrder = null;
        if (sortName != null && !sortName.trim().equals("")) {
            sortOrder = DBHelper.COL_PRODUCT_NAME + " " + sortName;
        }
        if (sortPrice != null && !sortPrice.trim().equals("")) {
            sortOrder = DBHelper.COL_PRODUCT_PRICE + " " + sortPrice;
        }

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs.toArray(new String[selectionArgs.size()]), sortOrder);
        return retrieveProductsFromCursor(cursor);
    }

    public ProductDTO getProductById(int id) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_PRODUCT_ID,
                DBHelper.COL_PRODUCT_NAME,
                DBHelper.COL_PRODUCT_CATEGORY,
                DBHelper.COL_PRODUCT_IMAGE,
                DBHelper.COL_PRODUCT_PRICE,
                DBHelper.COL_PRODUCT_DESCRIPTION
        };

        //check equal ignore case
        String selection = DBHelper.COL_PRODUCT_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);

        return retrieveProductFromCursor(cursor);
    }


    private List<ProductDTO> retrieveProductsFromCursor(Cursor cursor) {
        List<ProductDTO> result = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nId = cursor.getColumnIndex(DBHelper.COL_PRODUCT_ID);
                int nName = cursor.getColumnIndex(DBHelper.COL_PRODUCT_NAME);
                int nDescription = cursor.getColumnIndex(DBHelper.COL_PRODUCT_DESCRIPTION);
                int nCategory = cursor.getColumnIndex(DBHelper.COL_PRODUCT_CATEGORY);
                int nImage = cursor.getColumnIndex(DBHelper.COL_PRODUCT_IMAGE);
                int nPrice = cursor.getColumnIndex(DBHelper.COL_PRODUCT_PRICE);

                do {
                    int id = cursor.getInt(nId);
                    String name = cursor.getString(nName);
                    String description = cursor.getString(nDescription);
                    int image = cursor.getInt(nImage);
                    String category = cursor.getString(nCategory);
                    double price = cursor.getDouble(nPrice);

                    ProductDTO tmp = new ProductDTO();
                    tmp.setId(id);
                    tmp.setCategory(category);
                    tmp.setDescription(description);
                    tmp.setName(name);
                    tmp.setPrice(price);
                    tmp.setImage(image);

                    result.add(tmp);
                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }


    private ProductDTO retrieveProductFromCursor(Cursor cursor) {
        ProductDTO result = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nId = cursor.getColumnIndex(DBHelper.COL_PRODUCT_ID);
                int nName = cursor.getColumnIndex(DBHelper.COL_PRODUCT_NAME);
                int nDescription = cursor.getColumnIndex(DBHelper.COL_PRODUCT_DESCRIPTION);
                int nCategory = cursor.getColumnIndex(DBHelper.COL_PRODUCT_CATEGORY);
                int nImage = cursor.getColumnIndex(DBHelper.COL_PRODUCT_IMAGE);
                int nPrice = cursor.getColumnIndex(DBHelper.COL_PRODUCT_PRICE);

                int id = cursor.getInt(nId);
                String name = cursor.getString(nName);
                String description = cursor.getString(nDescription);
                int image = cursor.getInt(nImage);
                String category = cursor.getString(nCategory);
                double price = cursor.getDouble(nPrice);

                result = new ProductDTO();
                result.setId(id);
                result.setCategory(category);
                result.setDescription(description);
                result.setName(name);
                result.setPrice(price);
                result.setImage(image);

            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }


}
