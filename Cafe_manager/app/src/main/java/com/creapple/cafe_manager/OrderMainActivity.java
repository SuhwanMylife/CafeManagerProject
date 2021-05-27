package com.creapple.cafe_manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OrderMainActivity extends AppCompatActivity {

    private static String SERVER_ADDRESS = "http://ghtjd8264.dothome.co.kr";
    private static String IP_ADDRESS = "203.255.3.246";
    private static String TAG = "phpexample";

    private ArrayList<OrderPersonalData> mArrayList;
    private OrderUserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditTextSearchKeyword = (EditText) findViewById(R.id.editText_main_searchKeyword);

        mArrayList = new ArrayList<>();

        mAdapter = new OrderUserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        GetData task = new GetData();
        task.execute( SERVER_ADDRESS + "/order_getjson.php", "");

        Button button_search = (Button) findViewById(R.id.button_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                mAdapter.notifyDataSetChanged();


                String Keyword =  mEditTextSearchKeyword.getText().toString();
                mEditTextSearchKeyword.setText("");

                GetData task = new GetData();
                task.execute( SERVER_ADDRESS + "/order_query.php", Keyword);
            }
        });


        Button button_all = (Button) findViewById(R.id.button_main_all);
        button_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                GetData task = new GetData();
                task.execute( SERVER_ADDRESS + "/order_getjson.php", "");
            }
        });

        // '발주' 버튼 눌렀을 때
        Button btn_order_final = (Button) findViewById(R.id.btn_order_final);
        btn_order_final.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderMainActivity.this, OrderFinalActivity.class);
                intent.putExtra("발주 품목", mArrayList);
                startActivityForResult(intent, 0);

//                Intent intent = new Intent(v.getContext(), OrderFinalActivity.class);
            }
        });
    }

    public void onOrderFinalActivityClick(View v) {
        Intent intent = new Intent(this, OrderFinalActivity.class);
        startActivity(intent);
    }

    public class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        // String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(OrderMainActivity.this, "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            mJsonString = result;
            showResult();

//            if (result == null){
//
//                mTextViewResult.setText(errorString);
//            }
//            else {
//
//                mJsonString = result;
//                showResult();
//            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "pdt_classification=" + params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
//                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="choi";
        // String TAG_PDT_ID = "pdt_id";
        String TAG_PDT_NAME = "pdt_name";
        String TAG_PDT_CLASSIFICATION ="pdt_classification";
        String TAG_PDT_UNIT ="pdt_unit";
        String TAG_PDT_PRICE ="pdt_price";
        String TAG_PDT_STOCK ="pdt_stock";
        String TAG_PDT_STOCK_NUM ="pdt_stock_num";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                // switching frames is possible only from the frames view (not threads view)???
                // String pdt_id = item.optString(TAG_PDT_ID);
                String pdt_name = item.getString(TAG_PDT_NAME);
                String pdt_classification = item.getString(TAG_PDT_CLASSIFICATION);
                String pdt_unit = item.getString(TAG_PDT_UNIT);
                String pdt_price = item.getString(TAG_PDT_PRICE);
                String pdt_stock = item.getString(TAG_PDT_STOCK);
                String pdt_stock_num = item.getString(TAG_PDT_STOCK_NUM);

                OrderPersonalData orderpersonalData = new OrderPersonalData();

                // personalData.setMember_pdt_id(pdt_id);
                orderpersonalData.setMember_pdt_name(pdt_name);
                orderpersonalData.setMember_pdt_classification(pdt_classification);
                orderpersonalData.setMember_pdt_unit(pdt_unit);
                orderpersonalData.setMember_pdt_price(pdt_price);
                orderpersonalData.setMember_pdt_stock(pdt_stock);
                orderpersonalData.setMember_pdt_stock_num(pdt_stock_num);

                mArrayList.add(orderpersonalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
