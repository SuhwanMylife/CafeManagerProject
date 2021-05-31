package com.creapple.cafe_manager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "203.255.3.246";
    private static String TAG = "phpexample";
    
    //추가(5.31)
    // Channel에 대한 id 생성
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    // Channel을 생성 및 전달해 줄 수 있는 Manager 생성
    private NotificationManager mNotificationManager;
    // Notification에 대한 ID 생성
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.popup_activity_update);

        String name = "";
        String classification = "";
        String unit = "";
        String price = "";
        String stock = "";

        Bundle extras = getIntent().getExtras();

        name = extras.getString("name");
        classification = extras.getString("classification");
        unit = extras.getString("unit");
        price = extras.getString("price");
        stock = extras.getString("stock");

        TextView tv_name = (TextView) findViewById(R.id.et_name);
        EditText tv_classification = (EditText) findViewById(R.id.et_classification);
        EditText tv_unit = (EditText) findViewById(R.id.et_unit);
        EditText tv_price = (EditText) findViewById(R.id.et_price);
        EditText tv_stock = (EditText) findViewById(R.id.et_stock);

        String str1 = name; String str2 = classification; String str3 = unit; String str4 = price; String str5 = stock;

        tv_name.setText(str1);
        tv_classification.setText(str2);
        tv_unit.setText(str3);
        tv_price.setText(str4);
        tv_stock.setText(str5);

        // 수정한 내용 저장하기 버튼
        Button buttonUpdate = (Button)findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdt_name = tv_name.getText().toString();
                String pdt_classification = tv_classification.getText().toString();
                String pdt_unit = tv_unit.getText().toString();
                String pdt_price = tv_price.getText().toString();
                String pdt_stock = tv_stock.getText().toString();

                InsertData task = new InsertData();
                task.execute( "http://ghtjd8264.dothome.co.kr/inventory_update.php", pdt_name, pdt_classification, pdt_unit, pdt_price, pdt_stock);

                //추가(5.31)
                int temp = Integer.parseInt(pdt_stock);
                int set_st = ((MainActivity)MainActivity.context_main).set_state;
                if (set_st != 0) {
                    int lack2 = ((SettingActivity)SettingActivity.context_setting).lack;
                    if (temp<=lack2) {
                        sendNotification();
                    }
                }
                
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
        
        createNotificationChannel(); //추가(5.31)

        Button buttonDelete = (Button)findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pdt_name = tv_name.getText().toString();

                DeleteData task = new DeleteData();
                task.execute( "http://ghtjd8264.dothome.co.kr/inventory_delete.php", pdt_name);
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //바깥레이어 클릭시 안닫히게
//        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        //안드로이드 백버튼 막기
//        return;
//    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(UpdateActivity.this, "Please Wait", null, true, true);
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

    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(UpdateActivity.this, "Please Wait", null, true, true);
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

            String serverURL = (String)params[0];
            String postParameters = "pdt_name=" + pdt_name;

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
    
    //추가(5.31)
    public void createNotificationChannel()
    {
        //notification manager 생성
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // 기기(device)의 SDK 버전 확인 ( SDK 26 버전 이상인지 - VERSION_CODES.O = 26)
        if(android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.O){
            //Channel 정의 생성자( construct 이용 )
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID
                    ,"Test Notification",mNotificationManager.IMPORTANCE_HIGH);
            //Channel에 대한 기본 설정
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            // Manager을 이용하여 Channel 생성
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

    }

    // Notification Builder를 만드는 메소드
    private NotificationCompat.Builder getNotificationBuilder() {

        //클릭했을때 액티비티 이동, 액티비티명 변경
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        //PendingIntent 정의 (인텐트를 감싸는 인텐트)
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("재고 수량 부족 경고")
                .setContentText("현재 남은 재고 수량이 부족합니다!")
                .setSmallIcon(R.drawable.warning)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true);
        return notifyBuilder;
    }

    // Notification을 보내는 메소드
    public void sendNotification(){
        // Builder 생성
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        // Manager를 통해 notification 디바이스로 전달
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }
}

