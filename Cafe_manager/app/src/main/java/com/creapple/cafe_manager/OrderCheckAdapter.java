package com.creapple.cafe_manager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderCheckAdapter extends RecyclerView.Adapter<OrderCheckAdapter.CustomViewHolder> {

    private ArrayList<OrderCheckData> mList = null;
    private Activity context = null;


    public OrderCheckAdapter(Activity context, ArrayList<OrderCheckData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView pdt_date;
        protected TextView pdt_price_total;



        public CustomViewHolder(View view) {
            super(view);
            this.pdt_date = (TextView) view.findViewById(R.id.textView_list_pdt_date);
            this.pdt_price_total = (TextView) view.findViewById(R.id.textView_list_pdt_price_total);
        }
    }




    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_check_itemlist, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        DecimalFormat formatter = new DecimalFormat("###,###");

        Integer a = Integer.parseInt(mList.get(position).getMember_pdt_price_total());

        viewholder.pdt_date.setText("발주일자: " + mList.get(position).getMember_pdt_date());
        viewholder.pdt_price_total.setText("총 금액: " + formatter.format(a) + "원"); // formatter.format(mList.get(position).getMember_pdt_price_total())
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
