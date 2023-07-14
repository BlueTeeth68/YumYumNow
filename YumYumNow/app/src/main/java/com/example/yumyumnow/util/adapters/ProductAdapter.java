package com.example.yumyumnow.util.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.ProductDetail;
import com.example.yumyumnow.ProductFragment;
import com.example.yumyumnow.R;
import com.example.yumyumnow.dto.ProductDTO;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
    ArrayList<ProductDTO> products;
    FragmentManager fragmentManager;

    public ProductAdapter(Context context, ArrayList<ProductDTO> products, FragmentManager fragmentManager) {
        this.context = context;
        this.products = products;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_view_holder, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO product = products.get(position);
        if (product == null) {
            return;
        }
        holder.name.setText(String.valueOf(product.getName()));
        holder.price.setText(String.valueOf(product.getPrice()) + " $");
        holder.productImg.setImageResource(product.getImage());

        holder.productMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProduct(product);
                productDetail.setParentFragment(new ProductFragment());

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, productDetail);
                fragmentTransaction.commit();
            }
        });

        holder.addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ConstraintLayout productMainLayout;
        ImageView productImg;
        ImageButton addCartBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            productImg = itemView.findViewById(R.id.productImg);
            addCartBtn = itemView.findViewById(R.id.addToCartBtn);
            productMainLayout = itemView.findViewById(R.id.productMainLayout);
        }
    }
}
