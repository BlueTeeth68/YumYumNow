package com.example.yumyumnow.util.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.MainActivity;
import com.example.yumyumnow.ProductDetail;
import com.example.yumyumnow.ProductFragment;
import com.example.yumyumnow.R;
import com.example.yumyumnow.dao.CartDAO;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.CartProductDTO;
import com.example.yumyumnow.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

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
                addProductToCart(product);
            }
        });
    }

    private void addProductToCart(ProductDTO product) {
        CartDAO cartDAO = new CartDAO(context);
        List<CartDTO> productsList = cartDAO.getCartOfUser(MainActivity.user.getId());
        // if products list empty
        // add product to cart with quantity = 1
        if (productsList.isEmpty() || productsList == null) {
            CartProductDTO item = new CartProductDTO();
            item.setProductId(product.getId());
            item.setQuantity(1);
            boolean result = cartDAO.addProductToCart(MainActivity.user.getId(), item);
            if (result) {
                makeToastText("Add product to cart successfully!");
            } else {
                makeToastText("Failed to add product to cart!");
            }
        } else {
            // check if product is already in cart
            boolean isInCart = false;
            int itemPosition = -1;
            for (CartDTO pd : productsList) {
                if (pd.getProduct().getId() == product.getId()) {
                    isInCart = true;
                    itemPosition = productsList.indexOf(pd);
                    break;
                }
            }
            // if product is in cart
            // increase product quantity by 1
            if (isInCart && itemPosition != -1) {
                CartProductDTO item = new CartProductDTO();
                item.setProductId(product.getId());
                item.setQuantity(productsList.get(itemPosition).getQuantity() + 1);
                boolean result = cartDAO.updateCartProductQuantity(MainActivity.user.getId(), item);

                if (result) {
                    makeToastText("Product is in cart. Increase product quantity!");
                } else {
                    makeToastText("Failed to add product to cart!");
                }
            } else {
                // if product is not in cart
                // add product to cart with quantity = 1
                CartProductDTO item = new CartProductDTO();
                item.setProductId(product.getId());
                item.setQuantity(1);
                boolean result = cartDAO.addProductToCart(MainActivity.user.getId(), item);
                if (result) {
                    makeToastText("Add product to cart successfully!");
                } else {
                    makeToastText("Failed to add product to cart!");
                }
            }
        }
    }

    private void makeToastText(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
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
