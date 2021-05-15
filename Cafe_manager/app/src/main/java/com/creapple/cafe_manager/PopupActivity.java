package com.creapple.cafe_manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PopupActivity extends Activity {

    private static String IP_ADDRESS = "203.255.3.246";
    private static String TAG = "phpexample";

    private EditText mEditTextpdtname;
    private EditText mEditTextpdtclassification;
    private EditText mEditTextpdtunit;
    private EditText mEditTextpdtprice;
    private EditText mEditTextpdtstock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.inventory_popup_activity);

        mEditTextpdtname = (EditText)findViewById(R.id.editText_main_pdt_name);
        mEditTextpdtclassification = (EditText)findViewById(R.id.editText_main_pdt_classification);
        mEditTextpdtunit = (EditText)findViewById(R.id.editText_main_pdt_unit);
        mEditTextpdtprice = (EditText)findViewById(R.id.editText_main_pdt_price);
        mEditTextpdtstock = (EditText)findViewById(R.id.editText_main_pdt_stock);

//        //UI 객체생성
//        txtText = (TextView)findViewById(R.id.txtText);
//
//        //데이터 가져오기
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("data");
//        txtText.setText(data);

        Button buttonInsert = (Button)findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdt_name = mEditTextpdtname.getText().toString();
                String pdt_classification = mEditTextpdtclassification.getText().toString();
                String pdt_unit = mEditTextpdtunit.getText().toString();
                String pdt_price = mEditTextpdtprice.getText().toString();
                String pdt_stock = mEditTextpdtstock.getText().toString();

                InsertData task = new InsertData();
                task.execute( "http://" + IP_ADDRESS + "/ListViewPractice/insert.php", pdt_name, pdt_classification, pdt_unit, pdt_price, pdt_stock);

//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
                //데이터 전달하기
//                Intent intent = new Intent();
//                intent.putExtra("result", "Close Popup");
//                setResult(RESULT_OK, intent);

                //액티비티(팝업) 닫기
                // finish();
            }
        });
    }








    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
//        Intent intent = new Intent();
//        intent.putExtra("result", "Close Popup");
//        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(PopupActivity.this, "Please Wait", null, true, true);
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
            String pdt_stock = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "pdt_name=" + pdt_name + "&pdt_classification=" + pdt_classification + "&pdt_unit=" + pdt_unit + "&pdt_price=" + pdt_price + "&pdt_stock=" + pdt_stock;

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




