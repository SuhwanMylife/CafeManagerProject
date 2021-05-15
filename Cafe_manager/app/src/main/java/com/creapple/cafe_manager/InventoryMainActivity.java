package com.creapple.cafe_manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class InventoryMainActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "203.255.3.246";
    private static String TAG = "phpexample";

    //        private EditText mEditTextpdtname;
//        private EditText mEditTextpdtclassification;
//        private EditText mEditTextpdtunit;
//        private EditText mEditTextpdtprice;
//        private EditText mEditTextpdtstock;
//        private TextView mTextViewResult;
    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_main);

//            mEditTextpdtname = (EditText)findViewById(R.id.editText_main_pdt_name);
//            mEditTextpdtclassification = (EditText)findViewById(R.id.editText_main_pdt_classification);
//            mEditTextpdtunit = (EditText)findViewById(R.id.editText_main_pdt_unit);
//            mEditTextpdtprice = (EditText)findViewById(R.id.editText_main_pdt_price);
//            mEditTextpdtstock = (EditText)findViewById(R.id.editText_main_pdt_stock);
//            mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEditTextSearchKeyword = (EditText) findViewById(R.id.editText_main_searchKeyword);

//            mTextViewResult.setMovementMethod(new ScrollingMovementMethod());



        mArrayList = new ArrayList<>();

        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


//            // 재고 품목 추가 버튼
//            Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
//            buttonInsert.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String pdt_name = mEditTextpdtname.getText().toString();
//                    String pdt_classification = mEditTextpdtclassification.getText().toString();
//                    String pdt_unit = mEditTextpdtunit.getText().toString();
//                    String pdt_price = mEditTextpdtprice.getText().toString();
//                    String pdt_stock = mEditTextpdtstock.getText().toString();
//
//                    InsertData task = new InsertData();
//                    task.execute( "http://" + IP_ADDRESS + "/ListViewPractice/insert.php", pdt_name, pdt_classification, pdt_unit, pdt_price, pdt_stock);
//
////                    mEditTextpdtname.setText("");
////                    mEditTextpdtclassification.setText("");
////                    mEditTextpdtunit.setText("");
////                    mEditTextpdtprice.setText("");
////                    mEditTextpdtstock.setText("");
//
//                }
//            });


        Button button_search = (Button) findViewById(R.id.button_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                mAdapter.notifyDataSetChanged();


                String Keyword =  mEditTextSearchKeyword.getText().toString();
                mEditTextSearchKeyword.setText("");

                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/ListViewPractice/query.php", Keyword);
            }
        });


        Button button_all = (Button) findViewById(R.id.button_main_all);
        button_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/ListViewPractice/getjson.php", "");
            }
        });

    }

    // 팝업 버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        // intent.putExtra("data", "Test Popup");
        startActivity(intent);
        // startActivityForResult(intent, 1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                //데이터 받기
//                String result = data.getStringExtra("result");
//                txtResult.setText(result);
//            }
//        }
//    }




    public class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        // String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InventoryMainActivity.this, "Please Wait", null, true, true);
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
        String TAG_PDT_ID = "pdt_id";
        String TAG_PDT_NAME = "pdt_name";
        String TAG_PDT_CLASSIFICATION ="pdt_classification";
        String TAG_PDT_UNIT ="pdt_unit";
        String TAG_PDT_PRICE ="pdt_price";
        String TAG_PDT_STOCK ="pdt_stock";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                // switching frames is possible only from the frames view (not threads view)???
                String pdt_id = item.optString(TAG_PDT_ID);
                String pdt_name = item.getString(TAG_PDT_NAME);
                String pdt_classification = item.getString(TAG_PDT_CLASSIFICATION);
                String pdt_unit = item.getString(TAG_PDT_UNIT);
                String pdt_price = item.getString(TAG_PDT_PRICE);
                String pdt_stock = item.getString(TAG_PDT_STOCK);

                PersonalData personalData = new PersonalData();

                personalData.setMember_pdt_id(pdt_id);
                personalData.setMember_pdt_name(pdt_name);
                personalData.setMember_pdt_classification(pdt_classification);
                personalData.setMember_pdt_unit(pdt_unit);
                personalData.setMember_pdt_price(pdt_price);
                personalData.setMember_pdt_stock(pdt_stock);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}