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
import android.widget.TextView;

import com.example.yumyumnow.dao.BillDAO;
import com.example.yumyumnow.dto.BillDTO;
import com.example.yumyumnow.dto.BillDetailDTO;
import com.example.yumyumnow.util.adapters.BillDetailAdapter;
import com.example.yumyumnow.util.adapters.BillHistoryAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillDetailFragment newInstance(String param1, String param2) {
        BillDetailFragment fragment = new BillDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        loadData();

        bindElements(view);

        setRecyclerViewData(billDetailList);

        return view;
    }

    BillDAO billDAO;
    BillDTO bill;
    List<BillDetailDTO> billDetailList;
    int billId;
    public void setBillId(int billId){
        this.billId = billId;
    }
    private void loadData(){
        billDAO = new BillDAO(getActivity());
        bill = billDAO.getBillById(billId);
        billDetailList = bill.getBillDetails();
    }

    ImageButton backBtn;
    RecyclerView billDetailRVList;
    TextView id, date, price;
    private void bindElements(View view){
        backBtn = view.findViewById(R.id.historyBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new CheckoutHistoryFragment());
            }
        });

        billDetailRVList = view.findViewById(R.id.billDetailList);
        id = view.findViewById(R.id.billDetailId);
        date = view.findViewById(R.id.billDetailDate);
        price = view.findViewById(R.id.billDetailPrice);

        id.setText(String.valueOf(bill.getId()));
        date.setText(String.valueOf(bill.getCreateDate()));
        DecimalFormat df = new DecimalFormat("#.##");
        String totalPriceString = df.format(bill.getTotalPrice());
        price.setText(String.valueOf(totalPriceString));


    }

    private void switchFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    private void setRecyclerViewData(List<BillDetailDTO> billDetails) {
        BillDetailAdapter billDetailAdapter = new BillDetailAdapter(getActivity(), billDetails);
        billDetailRVList.setAdapter(billDetailAdapter);
        billDetailRVList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}