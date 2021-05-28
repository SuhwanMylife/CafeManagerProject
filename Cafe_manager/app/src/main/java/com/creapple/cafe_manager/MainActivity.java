package com.creapple.cafe_manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;
    Button btn_change, btn_setting;
    String emp_list, work_list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        activity = MainActivity.this;
        TextView tv_store = findViewById(R.id.tv_store); // Hoxy?
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String userNumber = intent.getStringExtra("userNumber");
        String userStore = intent.getStringExtra("userStore");
        tv_store.setText(userStore);
        
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeInfoActivity.class);
                intent.putExtra("userID", userId);
                intent.putExtra("userPassword", userPassword);
                intent.putExtra("userNumber", userNumber);
                intent.putExtra("userStore", userStore);
                startActivity(intent);

            }
        });
        
        btn_setting = findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        new BackgroundTask().execute();
        Button btn_commute = findViewById(R.id.commute);
        Button btn_management = (Button) findViewById(R.id.managementButton);
        Button btn_inventory_check = (Button) findViewById(R.id.inventory_check);
        Button btn_order = (Button) findViewById(R.id.order);

        btn_inventory_check.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, InventoryMainActivity.class);
                        startActivity(intent);
                    }
                });

        btn_order.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, OrderMainActivity.class);
                        startActivity(intent);
                    }
                });


        btn_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                intent.putExtra("worklist", work_list);
                intent.putExtra("employeeList", emp_list);
                intent.putExtra("userStore", userStore);
                startActivity(intent);
            }
        });

        btn_commute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimeAll.class);
                intent.putExtra("worklist", work_list);
                intent.putExtra("employeeList", emp_list);
                intent.putExtra("userStore", userStore);
                startActivity(intent);
            }
        });
    }


    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("종료")
                .setMessage("앱을 종료 하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(MainActivity.this); //액티비티 종료
                        System.exit(0); //완전 어플 종료
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    class JsonParse extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute(){
            target = "http://ghtjd8264.dothome.co.kr/work.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            work_list = result;

        }
    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute(){
            target = "http://ghtjd8264.dothome.co.kr/employee.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            emp_list = result;
            new JsonParse().execute();
        }
    }



}
