package com.example.sw15_usermanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    TextView text_info, text_contact;
    LinearLayout LL_notice;
    Button btn_salary;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        text_info = findViewById(R.id.text_info);
        text_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingInfoActivity.class);
                startActivity(intent);
            }
        });

        LL_notice = findViewById(R.id.LL_notice);
        LL_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingNoticeActivity.class);
                startActivity(intent);
            }
        });

        text_contact = findViewById(R.id.text_contact);
        text_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/01012345678"));
                startActivity(mIntent);
            }
        });

        final LinearLayout linear = (LinearLayout) View.inflate(SettingActivity.this, R.layout.dialog_num, null);
        // final String[] array = {"첫","둘둘","셋셋셋"};
        btn_salary = findViewById(R.id.btn_salary);
        btn_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setView(linear)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText salary = linear.findViewById(R.id.et_salary);
                                value = Integer.parseInt(salary.getText().toString());
                                Toast.makeText(getApplicationContext(), Integer.toString(value),  Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

                /*
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("제목")
                        .setItems(array, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), array[which],Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();*/
            }
        });


    }
}