package com.example.yumyumnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumyumnow.dao.BillDAO;
import com.example.yumyumnow.dao.CartDAO;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.CartProductDTO;
import com.example.yumyumnow.util.adapters.CheckoutProductAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        loadData();

        bindElements(view);

        setRecyclerViewData(products);

        return view;
    }

    ImageButton cartBackBtn;
    Button confirmBtn, cancelBtn;
    List<CartDTO> products;
    CartDAO cartDAO;
    BillDAO billDAO;
    RecyclerView checkoutProducts;
    TextView totalCostTxt;
    double totalCost = 0;

    private void bindElements(View view) {
        cartBackBtn = view.findViewById(R.id.cartBackBtn);
        cartBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCart();
            }
        });

        checkoutProducts = view.findViewById(R.id.checkoutProductList);

        confirmBtn = view.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCheckout();
            }
        });

        cancelBtn = view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCart();
            }
        });

        totalCostTxt = view.findViewById(R.id.totalCostTxt);
        DecimalFormat df = new DecimalFormat("#.##");
        String totalCostString = df.format(totalCost);
        totalCostTxt.setText(String.valueOf(totalCostString + " $"));

    }

    private void confirmCheckout() {
        int userId = MainActivity.user.getId();
        List<CartProductDTO> billProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            CartProductDTO item = new CartProductDTO();
            item.setQuantity(products.get(i).getQuantity());
            item.setProductId(products.get(i).getProduct().getId());
            billProducts.add(item);
        }

        // add bill to db
        boolean result = billDAO.createBill(userId, billProducts);
        if (result) {
            cartDAO.removeUserCart(userId);
            Toast.makeText(getActivity(), "Checkout successfully!", Toast.LENGTH_SHORT).show();
            backToCart();
        } else {
            Toast.makeText(getActivity(), "Failed to checkout!", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadData() {
        cartDAO = new CartDAO(getActivity());
        billDAO = new BillDAO(getActivity());
        products = cartDAO.getCartOfUser(MainActivity.user.getId());

        for (CartDTO item : products) {
            totalCost += item.getQuantity() * item.getProduct().getPrice();
        }
    }

    private void backToCart() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, new CartFragment());
        ft.commit();
    }

    private void setRecyclerViewData(List<CartDTO> products) {
        CheckoutProductAdapter checkoutProductAdapter = new CheckoutProductAdapter(getActivity(), products);
        checkoutProducts.setAdapter(checkoutProductAdapter);
        checkoutProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}