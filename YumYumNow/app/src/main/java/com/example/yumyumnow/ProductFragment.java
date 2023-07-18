package com.example.yumyumnow;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yumyumnow.dao.ProductDAO;
import com.example.yumyumnow.dto.ProductDTO;
import com.example.yumyumnow.util.adapters.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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

    ArrayList<ProductDTO> productsList;
    String[] categories = new String[]{"All", "Food", "Drink"};
    String[] sortOptions = new String[]{"None", "Name ASC", "Name DESC", "Price ASC", "Price DESC"};
    Spinner categorySpinner, sortSpinner;
    RecyclerView productList;
    EditText searchProductTxt;
    Button searchBtn;
    ProductDAO productDAO;
    ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        productDAO = new ProductDAO(getActivity());

        bindElements(view);

        loadInitialProduct();

        return view;
    }

    private void setProductsList(ArrayList<ProductDTO> products) {
        productAdapter = new ProductAdapter(getActivity(), products, getActivity().getSupportFragmentManager());
        productList.setAdapter(productAdapter);
        productList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadInitialProduct(){
        List<ProductDTO> list = productDAO.getProductByFilter(null, null, null, null);
        ArrayList<ProductDTO> arrayList = new ArrayList<>(list);

        setProductsList(arrayList);
    }

    private void bindElements(View view) {
        // category spinner
        categorySpinner = view.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                categories);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // sort spinner
        sortSpinner = view.findViewById(R.id.sortSpinner);
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                sortOptions);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        // product recycler view
        productList = view.findViewById(R.id.productRecyclerView);

        // search text
        searchProductTxt = view.findViewById(R.id.searchProductTxt);

        // search button
        searchBtn = view.findViewById(R.id.searchProductBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cateFilter = getCategoryFilter();
                String sortNameFilter = null, sortPriceFilter = null;
                sortNameFilter = getSortNameSelect();
                sortPriceFilter = getSortPriceSelect();
                String searchTxt = searchProductTxt.getText().toString().trim();
                List<ProductDTO> result = productDAO.getProductByFilter(searchTxt, cateFilter, sortNameFilter, sortPriceFilter);
                ArrayList<ProductDTO> arrayList = new ArrayList<>(result);

                setProductsList(arrayList);
            }
        });
    }

    private String getCategoryFilter() {
        int index = categorySpinner.getSelectedItemPosition();
        switch (index) {
            case 1:
                return "Food";
            case 2:
                return "Drink";
            default:
                return null;
        }
    }

    private String getSortNameSelect() {
        int index = sortSpinner.getSelectedItemPosition();
        switch (index) {
            case 1:
                return ProductDAO.ASC;
            case 2:
                return ProductDAO.DESC;
            default:
                return null;
        }
    }

    private String getSortPriceSelect() {
        int index = sortSpinner.getSelectedItemPosition();
        switch (index) {
            case 3:
                return ProductDAO.ASC;
            case 4:
                return ProductDAO.DESC;
            default:
                return null;
        }
    }


}