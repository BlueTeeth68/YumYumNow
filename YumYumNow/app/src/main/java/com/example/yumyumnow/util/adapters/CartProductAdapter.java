package com.example.yumyumnow.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.R;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.CartProductDTO;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder> {
    List<CartDTO> products;
    Context context;

    public CartProductAdapter(List<CartDTO> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public CartProductAdapter.CartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_product_view_holder, parent, false);
        return new CartProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.CartProductViewHolder holder, int position) {
        CartDTO cartProduct = products.get(position);
        if (cartProduct == null) {
            return;
        }
        holder.name.setText(String.valueOf(cartProduct.getProduct().getName()));
        holder.quantity.setText(String.valueOf(cartProduct.getQuantity()));
        holder.cartProductImg.setImageResource(cartProduct.getProduct().getImage());

        holder.cartProductMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;
    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity;
        ConstraintLayout cartProductMainLayout;
        ImageView cartProductImg;
        ImageButton increaseBtn, decreaseBtn, removeBtn;

        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cartProductName);
            quantity = itemView.findViewById(R.id.cartProductQuantity);
            cartProductMainLayout = itemView.findViewById(R.id.cartProductMainLayout);
            cartProductImg = itemView.findViewById(R.id.cartProductImg);
            increaseBtn = itemView.findViewById(R.id.increaseItemBtn);
            decreaseBtn = itemView.findViewById(R.id.decreaseItemBtn);
            removeBtn = itemView.findViewById(R.id.removeItemBtn);
        }
    }
}
