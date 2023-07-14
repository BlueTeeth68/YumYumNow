package com.example.yumyumnow.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.yumyumnow.database.DBHelper;
import com.example.yumyumnow.dto.BillDTO;
import com.example.yumyumnow.dto.BillDetailDTO;
import com.example.yumyumnow.dto.CartProductDTO;
import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.provider.Provider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //create bill
    public boolean createBill(int userId, List<CartProductDTO> productItems) {
        double totalPrice = 0;
        List<ProductDTO> productDTOList = productItems.stream().map(item -> productDAO.getProductById(item.getProductId())).collect(Collectors.toList());

        for (int i = 0; i < productDTOList.size(); i++) {
            totalPrice += productDTOList.get(i).getPrice() * productItems.get(i).getQuantity();
        }

        ContentResolver contentResolver = context.getContentResolver();

        //Create bill
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_BILL_USER_ID, userId);
        cv.put(DBHelper.COL_BILL_TOTAL_PRICE, totalPrice);
        cv.put(DBHelper.COL_BILL_CREATE_DATE, LocalDateTime.now().toString());

        Uri result = contentResolver.insert(uriBill, cv);

        long billId = ContentUris.parseId(result);
        //create bill

        //Create list Bill detail

        for (int i = 0; i < productDTOList.size(); i++) {
            cv = new ContentValues();
            cv.put(DBHelper.COL_BILL_DETAIL_BILL_ID, billId);
            cv.put(DBHelper.COL_BILL_DETAIL_PRODUCT_ID, productDTOList.get(i).getId());
            cv.put(DBHelper.COL_BILL_DETAIL_QUANTITY, productItems.get(i).getQuantity());
            cv.put(DBHelper.COL_BILL_DETAIL_PRICE, productDTOList.get(i).getPrice());
//
//            result = contentResolver.insert(uriBillDetail, cv);
        }
        //Create list Bill detail

        //we can use transaction here
        return true;
    }


    //get bill list

    public List<BillDTO> getBillListOfUser(int userId) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_BILL_ID,
                DBHelper.COL_BILL_USER_ID,
                DBHelper.COL_BILL_TOTAL_PRICE,
                DBHelper.COL_BILL_CREATE_DATE
        };
        String selection = DBHelper.COL_BILL_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(userId)};
        Cursor cursor = contentResolver.query(uriBill, projection, selection, selectionArgs, DBHelper.COL_BILL_ID + " desc");
        return getBillsFromCursor(cursor);
    }

    public BillDTO getBillById(int billId) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_BILL_ID,
                DBHelper.COL_BILL_USER_ID,
                DBHelper.COL_BILL_TOTAL_PRICE,
                DBHelper.COL_BILL_CREATE_DATE
        };
        String selection = DBHelper.COL_BILL_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(billId)};
        Cursor cursor = contentResolver.query(uriBill, projection, selection, selectionArgs, DBHelper.COL_BILL_ID + " desc");
        return getBillFromCursor(cursor);
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
                int productId = cursor.getInt(nProductId);
                int quantity = cursor.getInt(nQuantity);
                double price = cursor.getDouble(nPrice);

                ProductDTO productDTO = productDAO.getProductById(productId);

                billDetailDTO = new BillDetailDTO();
                billDetailDTO.setBillId(billId);
                billDetailDTO.setPrice(price);
                billDetailDTO.setQuantity(quantity);
                billDetailDTO.setProduct(productDTO);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return billDetailDTO;
    }

    private List<BillDetailDTO> getBillDetailsFromCursor(Cursor cursor) {
        List<BillDetailDTO> result = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nBillId = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_BILL_ID);
                int nProductId = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_PRODUCT_ID);
                int nQuantity = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_QUANTITY);
                int nPrice = cursor.getColumnIndex(DBHelper.COL_BILL_DETAIL_PRICE);

                do {
                    int billId = cursor.getInt(nBillId);
                    int productId = cursor.getInt(nProductId);
                    int quantity = cursor.getInt(nQuantity);
                    double price = cursor.getDouble(nPrice);

                    ProductDTO productDTO = productDAO.getProductById(productId);

                    BillDetailDTO tmp = new BillDetailDTO();
                    tmp.setBillId(billId);
                    tmp.setPrice(price);
                    tmp.setQuantity(quantity);
                    tmp.setProduct(productDTO);
                    result.add(tmp);
                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    private List<BillDTO> getBillsFromCursor(Cursor cursor) {
        List<BillDTO> result = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nBillId = cursor.getColumnIndex(DBHelper.COL_BILL_ID);
                int nUserId = cursor.getColumnIndex(DBHelper.COL_BILL_USER_ID);
                int nTotalPrice = cursor.getColumnIndex(DBHelper.COL_BILL_TOTAL_PRICE);
                int nCreateDate = cursor.getColumnIndex(DBHelper.COL_BILL_CREATE_DATE);

                do {
                    int billId = cursor.getInt(nBillId);
                    int userId = cursor.getInt(nUserId);
                    double totalPrice = cursor.getDouble(nTotalPrice);
                    String createDate = cursor.getString(nCreateDate);

                    BillDTO tmp = new BillDTO();
                    tmp.setId(billId);
                    tmp.setUserId(userId);
                    tmp.setCreateDate(createDate);
                    tmp.setTotalPrice(totalPrice);
                    List<BillDetailDTO> billDetailDTOS = getBillDetail(billId);
                    tmp.setBillDetails(billDetailDTOS);
                    result.add(tmp);

                } while (cursor.moveToNext());
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return result;
    }

    private BillDTO getBillFromCursor(Cursor cursor) {
        BillDTO result = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nBillId = cursor.getColumnIndex(DBHelper.COL_BILL_ID);
                int nUserId = cursor.getColumnIndex(DBHelper.COL_BILL_USER_ID);
                int nTotalPrice = cursor.getColumnIndex(DBHelper.COL_BILL_TOTAL_PRICE);
                int nCreateDate = cursor.getColumnIndex(DBHelper.COL_BILL_CREATE_DATE);

                int billId = cursor.getInt(nBillId);
                int userId = cursor.getInt(nUserId);
                double totalPrice = cursor.getDouble(nTotalPrice);
                String createDate = cursor.getString(nCreateDate);

                BillDTO tmp = new BillDTO();
                tmp.setId(billId);
                tmp.setUserId(userId);
                tmp.setCreateDate(createDate);
                tmp.setTotalPrice(totalPrice);
                List<BillDetailDTO> billDetailDTOS = getBillDetail(billId);
                tmp.setBillDetails(billDetailDTOS);
                result = tmp;
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return result;
    }

    private List<BillDetailDTO> getBillDetail(int billId) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                DBHelper.COL_BILL_DETAIL_BILL_ID,
                DBHelper.COL_BILL_DETAIL_QUANTITY,
                DBHelper.COL_BILL_DETAIL_PRICE,
                DBHelper.COL_BILL_DETAIL_PRODUCT_ID
        };

        String selection = DBHelper.COL_BILL_DETAIL_BILL_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(billId)};

        Cursor cursor = contentResolver.query(uriBillDetail, projection, selection, selectionArgs, null);
        return getBillDetailsFromCursor(cursor);
    }


}
