package com.example.yumyumnow.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.R;
import com.example.yumyumnow.dao.BillDAO;
import com.example.yumyumnow.dto.CartDTO;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.CheckoutProductViewHolder> {

    Context context;
    List<CartDTO> products;

    public CheckoutProductAdapter(Context context, List<CartDTO> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CheckoutProductAdapter.CheckoutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.checkout_product_view_holder, parent, false);
        return new CheckoutProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutProductAdapter.CheckoutProductViewHolder holder, int position) {
        CartDTO product = products.get(position);
        if(product == null){
            return;
        }
        holder.index.setText(String.valueOf(position + 1));
        holder.name.setText(String.valueOf(product.getProduct().getName()));
        holder.quantity.setText(String.valueOf(product.getQuantity()));
        DecimalFormat df = new DecimalFormat("#.##");
        double itemTotal = product.getQuantity() * product.getProduct().getPrice();
        String itemTotalString = df.format(itemTotal);
        holder.price.setText(itemTotalString + " $");
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;
    }

    public class CheckoutProductViewHolder extends RecyclerView.ViewHolder {

        TextView index, name, quantity, price;

        public CheckoutProductViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.indexTxt);
            name = itemView.findViewById(R.id.checkoutProductName);
            quantity = itemView.findViewById(R.id.checkoutProductQuantity);
            price = itemView.findViewById(R.id.checkoutProductPrice);
        }
    }
}
