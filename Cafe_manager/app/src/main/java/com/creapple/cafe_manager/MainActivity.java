package com.creapple.cafe_manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button managementButton = (Button) findViewById(R.id.managementButton);

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
                Intent intent = new Intent(MainActivity.this,ManagementActivity.class);
                intent.putExtra("employeeList", result);
                MainActivity.this.startActivity(intent);
            }
        }

        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
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



}