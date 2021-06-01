package com.creapple.cafe_manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

;

public class OrderCheckDetailActivity extends AppCompatActivity {

    private ArrayList<OrderCheckDetailsData> mArrayList;
    private OrderCheckDetailsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_check_details);

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mArrayList = new ArrayList<>();

        mAdapter = new OrderCheckDetailsAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        Intent intent = getIntent();
        String pdt_date_primary = intent.getStringExtra("pdt_date");

        TextView tv_pdt_date = (TextView) findViewById(R.id.pdt_date);
        tv_pdt_date.setText(pdt_date_primary + " 발주 내역입니다");

        try {
            String TAG_JSON="choi";
            // String TAG_PDT_ID = "pdt_id";
            String TAG_PDT_DATE = "pdt_date";
            String TAG_PDT_NAME = "pdt_name";
            String TAG_PDT_CLASSIFICATION ="pdt_classification";
            String TAG_PDT_UNIT ="pdt_unit";
            String TAG_PDT_PRICE ="pdt_price";
            String TAG_PDT_STOCK_NUM ="pdt_stock_num";

            JSONObject jsonObject = new JSONObject(intent.getStringExtra("order_check_detail"));
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                // switching frames is possible only from the frames view (not threads view)???
                // String pdt_id = item.optString(TAG_PDT_ID);
                String pdt_name = item.getString(TAG_PDT_NAME);
                String pdt_date = item.getString(TAG_PDT_DATE);
                String pdt_classification = item.getString(TAG_PDT_CLASSIFICATION);
                String pdt_unit = item.getString(TAG_PDT_UNIT);
                String pdt_price = item.getString(TAG_PDT_PRICE);
                String pdt_stock_num = item.getString(TAG_PDT_STOCK_NUM);

                SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date to = fm1.parse(pdt_date);

                SimpleDateFormat fm2 = new SimpleDateFormat("yyyy.MM.dd");
                pdt_date = fm2.format(to);

                if(pdt_date.equals(pdt_date_primary)) {
                    OrderCheckDetailsData ordercheckdetailsdata = new OrderCheckDetailsData();
                    // personalData.setMember_pdt_id(pdt_id);
                    ordercheckdetailsdata.setMember_pdt_name(pdt_name);
                    ordercheckdetailsdata.setMember_pdt_classification(pdt_classification);
                    ordercheckdetailsdata.setMember_pdt_unit(pdt_unit);
                    ordercheckdetailsdata.setMember_pdt_price(pdt_price);
                    ordercheckdetailsdata.setMember_pdt_stock_num(pdt_stock_num);

                    mArrayList.add(ordercheckdetailsdata);
                    mAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

    }
}

