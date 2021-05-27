package com.creapple.cafe_manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderFinalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private OrderUserAdapter mAdapter;
    private ArrayList<OrderPersonalData> mArrayList;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_final);

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mArrayList = new ArrayList<>();

        Intent intent = getIntent();
        mArrayList = (ArrayList<OrderPersonalData>) intent.getSerializableExtra("발주 품목");



        // TODO: 버그 최소화하기 위해서는 remove 대신 이 코드 사용해야 할 듯 (아닐 수도?)
//        ArrayList<OrderPersonalData> mArrayListUpdate = new ArrayList<OrderPersonalData>();
//        for (int i = 0; i < mArrayList.size(); i++) {
//            int stock_num = Integer.parseInt(String.valueOf(mArrayList.get(i).getMember_pdt_stock_num()));
//            if (stock_num != 0) {
//                mArrayListUpdate.add(new OrderPersonalData(mArrayList.get(i).getMember_pdt_name(),
//                        mArrayList.get(i).getMember_pdt_classification(),
//                        mArrayList.get(i).getMember_pdt_unit(),
//                        mArrayList.get(i).getMember_pdt_price(),
//                        mArrayList.get(i).getMember_pdt_stock(),
//                        mArrayList.get(i).getMember_pdt_stock_num()));
//            }
//        }

        for (int i = mArrayList.size()-1; i > 0; i--) {
            int stock_num = Integer.parseInt(String.valueOf(mArrayList.get(i).getMember_pdt_stock_num()));
            if (stock_num == 0) {
                mArrayList.remove(i);
            }
        }

        mAdapter = new OrderUserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        // mArrayList.clear();
        // mAdapter.notifyDataSetChanged();

        Button orderFinal = findViewById(R.id.order_final);
        orderFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(OrderFinalActivity.this);
                builder.setMessage("발주 신청하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OrderFinalActivity.this, "발주 신청이 성공적으로 이루어졌습니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OrderFinalActivity.this, OrderMainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("아니오", ((dialog, which) -> { dialog.cancel(); }));
                builder.show();
            }
        });
    }

}
