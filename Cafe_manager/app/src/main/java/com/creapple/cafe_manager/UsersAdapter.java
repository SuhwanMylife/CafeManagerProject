package com.creapple.cafe_manager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.DragStartHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<PersonalData> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView pdt_id;
        protected TextView pdt_name;
        protected TextView pdt_classificaion;
        protected TextView pdt_unit;
        protected TextView pdt_price;
        protected TextView pdt_stock;



        public CustomViewHolder(View view) {
            super(view);
            this.pdt_id = (TextView) view.findViewById(R.id.textView_list_pdt_id);
            this.pdt_name = (TextView) view.findViewById(R.id.textView_list_pdt_name);
            this.pdt_classificaion = (TextView) view.findViewById(R.id.textView_list_pdt_classification);
            this.pdt_unit = (TextView) view.findViewById(R.id.textView_list_pdt_unit);
            this.pdt_price = (TextView) view.findViewById(R.id.textView_list_pdt_price);
            this.pdt_stock = (TextView) view.findViewById(R.id.textView_list_pdt_stock);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventory_item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.pdt_id.setText(mList.get(position).getMember_pdt_id());
        viewholder.pdt_name.setText(mList.get(position).getMember_pdt_name());
        viewholder.pdt_classificaion.setText(mList.get(position).getMember_pdt_classification());
        viewholder.pdt_unit.setText(mList.get(position).getMember_pdt_unit());
        viewholder.pdt_price.setText(mList.get(position).getMember_pdt_price());
        viewholder.pdt_stock.setText(mList.get(position).getMember_pdt_stock());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
