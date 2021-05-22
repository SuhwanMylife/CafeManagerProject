package com.example.sw15_usermanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeInfoActivity extends AppCompatActivity {
    private TextView tv_id, tv_pw, tv_num, tv_store;
    private EditText et_ch_num, et_ch_store, et_ch_pw, et_ch_pw_re, et_org_pw;
    private Button btn_ch_info, btn_ch_pw, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        CheckActivity Main = (CheckActivity) CheckActivity.activity; //이름 변경 필요

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String userNumber = intent.getStringExtra("userNumber");
        String userStore = intent.getStringExtra("userStore");

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        tv_num = findViewById(R.id.tv_num);
        tv_store = findViewById(R.id.tv_store);

        tv_id.setText(userID);
        tv_pw.setText(userPassword);
        tv_num.setText(userNumber);
        tv_store.setText(userStore);

        et_ch_num = findViewById(R.id.et_ch_num);
        et_ch_store = findViewById(R.id.et_ch_store);
        et_ch_pw = findViewById(R.id.et_ch_pw);
        et_ch_pw_re = findViewById(R.id.et_ch_pw_re);
        et_org_pw = findViewById(R.id.et_org_pw);

        btn_ch_info = findViewById(R.id.btn_ch_info);
        btn_ch_pw = findViewById(R.id.btn_ch_pw);
        btn_logout = findViewById(R.id.btn_logout);

        btn_ch_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ch_num = et_ch_num.getText().toString();
                String ch_store = et_ch_store.getText().toString();

                if (ch_store.equals("")) {
                    Toast.makeText(ChangeInfoActivity.this, "매장명을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_ch_store.requestFocus();
                    return;
                }
                if (ch_num.equals("")) {
                    Toast.makeText(ChangeInfoActivity.this, "전화번호룰 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_ch_num.requestFocus();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try  {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                new AlertDialog.Builder(ChangeInfoActivity.this)
                                        .setMessage("정보가 변경되었습니다. 다시 로그인 해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                Main.finish();
                                            }
                                        })
                                        .show();
                            } else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ChangeInfoRequest changeInfoRequest = new ChangeInfoRequest(userID, ch_num, ch_store, responseListener); //담아줌 넘 빼고
                RequestQueue queue = Volley.newRequestQueue(ChangeInfoActivity.this);
                queue.add(changeInfoRequest);
            }
        });

        btn_ch_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String org_pw = et_org_pw.getText().toString();
                String ch_pw = et_ch_pw.getText().toString();
                String ch_pw_re = et_ch_pw_re.getText().toString();

                if (!org_pw.equals(userPassword)) {
                    Toast.makeText(ChangeInfoActivity.this, "기존 비밀번호를 다시한번 확인해주세요", Toast.LENGTH_SHORT).show();
                    et_org_pw.requestFocus();
                    return;
                }
                if (ch_pw.equals("")) {
                    Toast.makeText(ChangeInfoActivity.this, "변경할 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_ch_pw.requestFocus();
                    return;
                }
                if (!ch_pw_re.equals(ch_pw)) {
                    Toast.makeText(ChangeInfoActivity.this, "비밀번호가 일치하지 않습니다. 다시한번 확인해주세요", Toast.LENGTH_SHORT).show();
                    et_ch_pw.requestFocus();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try  {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                new AlertDialog.Builder(ChangeInfoActivity.this)
                                        .setMessage("정보가 변경되었습니다. 다시 로그인 해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                Main.finish();
                                            }
                                        })
                                        .show();
                            } else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ChangePwRequest changePwRequest = new ChangePwRequest(userPassword, ch_pw, responseListener); //담아줌 넘 빼고
                RequestQueue queue = Volley.newRequestQueue(ChangeInfoActivity.this);
                queue.add(changePwRequest);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ChangeInfoActivity.this)
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Main.finish();
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
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
        });


    }
}