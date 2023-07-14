package com.example.yumyumnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yumyumnow.dao.CartDAO;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.util.adapters.CartProductAdapter;
import com.example.yumyumnow.util.adapters.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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

    List<CartDTO> cartProducts;
    CartDAO cartDao;
    RecyclerView productsRecyclerView;
    CartProductAdapter cartProductAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartDao = new CartDAO(getActivity());

        bindElements(view);

        loadCartProducts();

        return view;
    }

    private void loadCartProducts() {
        List<CartDTO> list = cartDao.getCartOfUser(MainActivity.user.getId());
        if (list == null || list.isEmpty()) {
            Toast.makeText(getActivity(), "User has no item in cart!", Toast.LENGTH_SHORT).show();
        } else {
            setCartProductsList(list);
        }
    }

    private void setCartProductsList(List<CartDTO> products) {
        cartProductAdapter = new CartProductAdapter(products, getActivity(), getActivity().getSupportFragmentManager());
        productsRecyclerView.setAdapter(cartProductAdapter);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void bindElements(View view) {
        // cart product recycler view
        productsRecyclerView = view.findViewById(R.id.cartProductRecyclerView);
    }
}