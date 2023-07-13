package com.example.yumyumnow.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.yumyumnow.database.DBHelper;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.CartProductDTO;
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

    public CartDTO getCartByUserIdAndProductId(int userId, int productID) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_CART_USER_ID,
                DBHelper.COL_CART_QUANTITY,
                DBHelper.COL_CART_PRODUCT_ID
        };

        String selection = DBHelper.COL_CART_USER_ID + " = ? AND " + DBHelper.COL_CART_PRODUCT_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(userId), String.valueOf(productID)};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        return getCartFromCursor(cursor);
    }

    public boolean addProductToCart(int userId, CartProductDTO productItem) {
        ContentResolver contentResolver = context.getContentResolver();

        CartDTO cartDTO = getCartByUserIdAndProductId(userId, productItem.getProductId());
        if (cartDTO != null) {
            return updateCartProductQuantity(userId, productItem);
        } else {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COL_CART_USER_ID, userId);
            cv.put(DBHelper.COL_CART_PRODUCT_ID, productItem.getProductId());
            cv.put(DBHelper.COL_CART_QUANTITY, productItem.getQuantity());

            Uri result = contentResolver.insert(uri, cv);
            return (result != null);
        }

    }

    public boolean updateCartProductQuantity(int userId, CartProductDTO productItem) {
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_CART_QUANTITY, productItem.getQuantity());
        String selection = DBHelper.COL_CART_USER_ID + " = ? AND " + DBHelper.COL_CART_PRODUCT_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(userId), String.valueOf(productItem.getProductId())};

        int result = contentResolver.update(uri, cv, selection, selectionArgs);
        return (result > 0);

    }

    public boolean removeProductFromCart(int userId, CartProductDTO productItem) {
        ContentResolver contentResolver = context.getContentResolver();
        String selection = DBHelper.COL_CART_USER_ID + " = ? AND " + DBHelper.COL_CART_PRODUCT_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(userId), String.valueOf(productItem.getProductId())};

        int row = contentResolver.delete(uri, selection, selectionArgs);
        return (row > 0);
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
                    int quantity = cursor.getInt(nQuantity);
                    int productId = cursor.getInt(nProductId);
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

    private CartDTO getCartFromCursor(Cursor cursor) {
        CartDTO cartDTO = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nUserId = cursor.getColumnIndex(DBHelper.COL_CART_USER_ID);
                int nProductId = cursor.getColumnIndex(DBHelper.COL_CART_PRODUCT_ID);
                int nQuantity = cursor.getColumnIndex(DBHelper.COL_CART_QUANTITY);

                int userId = cursor.getInt(nUserId);
                int quantity = cursor.getInt(nQuantity);
                int productId = cursor.getInt(nProductId);
                ProductDTO product = productDAO.getProductById(productId);

                cartDTO = new CartDTO();
                cartDTO.setUserId(userId);
                cartDTO.setQuantity(quantity);
                cartDTO.setProduct(product);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return cartDTO;
    }
}
