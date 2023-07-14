package com.example.yumyumnow.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumyumnow.R;
import com.example.yumyumnow.dto.BillDetailDTO;

import java.text.DecimalFormat;
import java.util.List;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder> {
    Context context;
    List<BillDetailDTO> billDetails;

    public BillDetailAdapter(Context context, List<BillDetailDTO> billDetails) {
        this.context = context;
        this.billDetails = billDetails;
    }

    @NonNull
    @Override
    public BillDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bill_detail_view_holder, parent, false);
        return new BillDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailAdapter.BillDetailViewHolder holder, int position) {
        BillDetailDTO billDetail = billDetails.get(position);
        if (billDetail == null) {
            return;
        }
        holder.image.setImageResource(billDetail.getProduct().getImage());
        holder.name.setText(String.valueOf(billDetail.getProduct().getName()));
        DecimalFormat df = new DecimalFormat("#.##");
        double totalPrice = billDetail.getPrice() * billDetail.getQuantity();
        String billDetailPriceString = df.format(totalPrice);
//        holder.price.setText(String.valueOf(billDetailPriceString) + " $");
        holder.price.setText(billDetailPriceString == null ? "" : billDetailPriceString + " $");
        holder.quantity.setText(String.valueOf(billDetail.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return (billDetails != null) ? billDetails.size() : 0;
    }

    public class BillDetailViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, price;
        ImageView image;

        public BillDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.billDetailName);
            quantity = itemView.findViewById(R.id.billDetailQuantity);
            price = itemView.findViewById(R.id.billDetailTotalPrice);
            image = itemView.findViewById(R.id.billDetailImg);
        }
    }
}
