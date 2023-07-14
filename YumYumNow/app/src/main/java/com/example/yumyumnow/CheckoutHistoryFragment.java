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
import android.widget.ImageButton;

import com.example.yumyumnow.dao.BillDAO;
import com.example.yumyumnow.dto.BillDTO;
import com.example.yumyumnow.dto.CartDTO;
import com.example.yumyumnow.util.adapters.BillHistoryAdapter;
import com.example.yumyumnow.util.adapters.CheckoutProductAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckoutHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckoutHistoryFragment newInstance(String param1, String param2) {
        CheckoutHistoryFragment fragment = new CheckoutHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_checkout_history, container, false);

        loadData();

        bindElements(view);

        setRecyclerViewData(billList);

        return view;
    }
    BillDAO billDAO;
    List<BillDTO> billList;
    ImageButton profileBackBtn;
    RecyclerView billListView;
    private void bindElements(View view) {
        profileBackBtn = view.findViewById(R.id.profileBackBtn);
        profileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new ProfileFragment());
            }
        });

        billListView = view.findViewById(R.id.billsList);
    }

    private void loadData() {
        billDAO = new BillDAO(getActivity());
        billList = billDAO.getBillListOfUser(MainActivity.user.getId());
    }
    private void switchFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    private void setRecyclerViewData(List<BillDTO> bills) {
        BillHistoryAdapter billHistoryAdapter = new BillHistoryAdapter(getActivity(), bills, getActivity().getSupportFragmentManager());
        billListView.setAdapter(billHistoryAdapter);
        billListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}