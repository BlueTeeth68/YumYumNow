package com.example.yumyumnow.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.yumyumnow.database.DBHelper;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.provider.Provider;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CartDAO {

    private final Context context;
    private final ProductDAO productDAO;
    private static final Uri uri = Provider.uriCart;

    public CartDAO(Context context) {
        this.context = context;
        productDAO = new ProductDAO(context);
    }

    public List<CartDTO> getCartOfUser(int userid) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_CART_USER_ID,
                DBHelper.COL_CART_QUANTITY,
                DBHelper.COL_CART_PRODUCT_ID
        };

        //check equal ignore case
        String selection = DBHelper.COL_CART_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(userid)};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        return getCartsFromCursor(cursor);
    }

    @NonNull
    private List<CartDTO> getCartsFromCursor(Cursor cursor) {
        List<CartDTO> result = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nUserId = cursor.getColumnIndex(DBHelper.COL_CART_USER_ID);
                int nProductId = cursor.getColumnIndex(DBHelper.COL_CART_PRODUCT_ID);
                int nQuantity = cursor.getColumnIndex(DBHelper.COL_CART_QUANTITY);

                do {
                    int userId = cursor.getInt(nUserId);
                    int quantity = cursor.getInt(nProductId);
                    int productId = cursor.getInt(nQuantity);
                    ProductDTO product = productDAO.getProductById(productId);

                    CartDTO tmp = new CartDTO();
                    tmp.setUserId(userId);
                    tmp.setQuantity(quantity);
                    tmp.setProduct(product);

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
