package com.example.yumyumnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.dto.UserDTO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetail newInstance(String param1, String param2) {
        ProductDetail fragment = new ProductDetail();
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
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        bindElements(view);

        return view;
    }

    Fragment fragment;
    public void setParentFragment(Fragment fragment){
        this.fragment = fragment;
    }

    ProductDTO product;
    public void setProduct(ProductDTO product){
        this.product = product;
    }

    TextView id, name, description, category, price;
    ImageButton backBtn, addBtn;
    ImageView productImg;

    private void bindElements(View view){
        id = view.findViewById(R.id.productDetailId);
        name = view.findViewById(R.id.productDetailName);
        description = view.findViewById(R.id.productDetailDes);
        category = view.findViewById(R.id.productDetailCate);
        price = view.findViewById(R.id.productDetailPrice);
        backBtn = view.findViewById(R.id.productBackBtn);
        addBtn = view.findViewById(R.id.addBtn);
        productImg = view.findViewById(R.id.productDetailImg);

        id.setText(String.valueOf(product.getId()));
        name.setText(String.valueOf(product.getName()));
        description.setText(String.valueOf(product.getDescription()));
        category.setText(String.valueOf(product.getCategory()));
        price.setText(String.valueOf(product.getPrice()));
        productImg.setImageResource(product.getImage());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.commit();
            }
        });


    }
}