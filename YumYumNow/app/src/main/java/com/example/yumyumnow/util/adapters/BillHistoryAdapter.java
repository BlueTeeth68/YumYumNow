package com.example.yumyumnow.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.BillDetailFragment;
import com.example.yumyumnow.R;
import com.example.yumyumnow.dto.BillDTO;

import java.text.DecimalFormat;
import java.util.List;

public class BillHistoryAdapter extends RecyclerView.Adapter<BillHistoryAdapter.BillViewHolder> {
    Context context;
    List<BillDTO> billList;
    FragmentManager fragmentManager;

    public BillHistoryAdapter(Context context, List<BillDTO> billList, FragmentManager fragmentManager) {
        this.context = context;
        this.billList = billList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public BillHistoryAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bill_view_holder, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHistoryAdapter.BillViewHolder holder, int position) {
        BillDTO bill = billList.get(position);
        if(bill == null){
            return;
        }
        holder.billDate.setText(bill.getCreateDate());
        DecimalFormat df = new DecimalFormat("#.##");
        String totalPriceString = df.format(bill.getTotalPrice());
        holder.billPrice.setText(totalPriceString + " $");

        holder.billLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillDetailFragment billDetailFragment = new BillDetailFragment();
                billDetailFragment.setBillId(bill.getId());

                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frameLayout, billDetailFragment);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (billList != null) ? billList.size() : 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        TextView billDate, billPrice;
        ConstraintLayout billLayout;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            billDate = itemView.findViewById(R.id.billDateTxt);
            billPrice = itemView.findViewById(R.id.billTotalTxt);
            billLayout = itemView.findViewById(R.id.billMainLayout);
        }
    }
}
