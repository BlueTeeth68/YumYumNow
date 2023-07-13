package com.example.yumyumnow.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.yumyumnow.database.DBHelper;
import com.example.yumyumnow.dto.BillDetailDTO;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.provider.Provider;

@SuppressWarnings("unused")
public class BillDAO {
    private final Context context;
    private final ProductDAO productDAO;
    private static final Uri uriBill = Provider.uriBill;
    private static final Uri uriBillDetail = Provider.uriBillDetail;

    public BillDAO(Context context) {
        this.context = context;
        productDAO = new ProductDAO(context);
    }

    private BillDetailDTO getBillDetailFromCursor(Cursor cursor) {
        BillDetailDTO billDetailDTO = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nBillId = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_BILL_ID);
                int nProductId = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_PRODUCT_ID);
                int nQuantity = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_QUANTITY);
                int nPrice = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_PRICE);

                int billId = cursor.getInt(nBillId);
                int quantity = cursor.getInt(nQuantity);
                int productId = cursor.getInt(nProductId);
                double price = cursor.getDouble(nPrice);


                ProductDTO product = productDAO.getProductById(productId);

//                cartDTO = new CartDTO();
//                cartDTO.setUserId(userId);
//                cartDTO.setQuantity(quantity);
//                cartDTO.setProduct(product);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return billDetailDTO;
    }


}
