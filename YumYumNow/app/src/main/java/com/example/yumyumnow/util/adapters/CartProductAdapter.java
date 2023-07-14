package com.example.yumyumnow.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.CartFragment;
import com.example.yumyumnow.ProductDetail;
import com.example.yumyumnow.ProductFragment;
import com.example.yumyumnow.R;
import com.example.yumyumnow.dao.CartDAO;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.CartProductDTO;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder> {
    List<CartDTO> products;
    Context context;
    FragmentManager fragmentManager;
    CartFragment currentFragment;
    CartDAO cartDAO;

    public CartProductAdapter(List<CartDTO> products, Context context, FragmentManager fragmentManager, CartFragment currentFragment) {
        this.products = products;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.currentFragment = currentFragment;
        cartDAO = new CartDAO(context);
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
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProduct(cartProduct.getProduct());
                productDetail.setParentFragment(new CartFragment());

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, productDetail);
                fragmentTransaction.commit();
            }
        });

        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity(cartProduct);
            }
        });

        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity(cartProduct);
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(cartProduct);
            }
        });
    }

    private void removeItem(CartDTO item){
        CartProductDTO removingItem = new CartProductDTO();
        removingItem.setProductId(item.getProduct().getId());
        removingItem.setQuantity(item.getQuantity());
        boolean result = cartDAO.removeProductFromCart(item.getUserId(), removingItem);
        if(result){
            makeToastText("Remove product from cart successfully!");
            refreshCart();
        }else{
            makeToastText("Error when trying to remove product from cart");
        }
    }

    private void increaseQuantity(CartDTO item){
        CartProductDTO increasingItem = new CartProductDTO();
        increasingItem.setProductId(item.getProduct().getId());
        increasingItem.setQuantity(item.getQuantity() + 1);

        boolean result = cartDAO.updateCartProductQuantity(item.getUserId(), increasingItem);
        if(result){
            makeToastText("Product quantity increased!");
            refreshCart();
        }
        else{
            makeToastText("Error when trying to increase product quantity");
        }
    }

    private void decreaseQuantity(CartDTO item){
        if(item.getQuantity() <= 1){
            removeItem(item);
        }
        else{
            CartProductDTO decreasingItem = new CartProductDTO();
            decreasingItem.setProductId(item.getProduct().getId());
            decreasingItem.setQuantity(item.getQuantity() - 1);

            boolean result = cartDAO.updateCartProductQuantity(item.getUserId(), decreasingItem);
            if(result){
                makeToastText("Product quantity decreased!");
                refreshCart();
            }
            else{
                makeToastText("Error when trying to decrease product quantity");
            }
        }
    }

    private void refreshCart(){
        currentFragment.loadCartProducts();
    }

    private void makeToastText(String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
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
