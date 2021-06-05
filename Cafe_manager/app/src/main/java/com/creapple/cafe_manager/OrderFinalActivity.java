package com.creapple.cafe_manager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderFinalActivity extends AppCompatActivity {

    private static String SERVER_ADDRESS = "http://ghtjd8264.dothome.co.kr";
    private static String TAG = "phpexample";

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

        for (int i = mArrayList.size()-1; i >= 0; i--) {
            int stock_num = Integer.parseInt(String.valueOf(mArrayList.get(i).getMember_pdt_stock_num()));
            if (stock_num == 0) {
                mArrayList.remove(i);
            }
        }

        Integer price_total = 0;
        for (int i = mArrayList.size()-1; i >= 0; i--) {
            int price = Integer.parseInt(String.valueOf(mArrayList.get(i).getMember_pdt_price()));
            int stock_num = Integer.parseInt(String.valueOf(mArrayList.get(i).getMember_pdt_stock_num()));
            price_total += price * stock_num;
        }
        DecimalFormat formatter = new DecimalFormat("###,###");
        Integer.toString(price_total);
        // formatter.format(price_total);


        mAdapter = new OrderUserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        TextView tv_price_total = (TextView) findViewById(R.id.tv_price_total);
        tv_price_total.setText("총 금액: " + formatter.format(price_total) + "원");


        Button orderFinal = findViewById(R.id.order_final);
        orderFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(OrderFinalActivity.this);
                builder.setMessage("발주 신청하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < mArrayList.size(); i++) {
                            int stock_num = Integer.parseInt(String.valueOf(mArrayList.get(i).getMember_pdt_stock_num()));
                            if (stock_num != 0) {

                                String pdt_name = mArrayList.get(i).getMember_pdt_name();
                                String pdt_classification = mArrayList.get(i).getMember_pdt_classification();
                                String pdt_unit = mArrayList.get(i).getMember_pdt_unit();
                                String pdt_price = mArrayList.get(i).getMember_pdt_price();
                                String pdt_stock_num = mArrayList.get(i).getMember_pdt_stock_num();

                                InsertData task1 = new InsertData();
                                task1.execute( SERVER_ADDRESS + "/order_insert.php", pdt_name, pdt_classification, pdt_unit, pdt_price, pdt_stock_num);

                                UpdateData task2 = new UpdateData();
                                task2.execute( SERVER_ADDRESS + "/order_update_stock.php", pdt_name, pdt_stock_num);
                            }
                        }

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

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(OrderFinalActivity.this, "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String pdt_name = (String)params[1];
            String pdt_classification = (String)params[2];
            String pdt_unit = (String)params[3];
            String pdt_price = (String)params[4];
            String pdt_stock_num = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "pdt_name=" + pdt_name + "&pdt_classification=" + pdt_classification + "&pdt_unit=" + pdt_unit + "&pdt_price=" + pdt_price + "&pdt_stock_num=" + pdt_stock_num;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class UpdateData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(OrderFinalActivity.this, "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String pdt_name = (String)params[1];
            String pdt_stock_num = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "pdt_name=" + pdt_name + "&pdt_stock_num=" + pdt_stock_num;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}