package com.example.sw15_usermanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class FindingActivity extends AppCompatActivity {
    Button btn_find_id, btn_find_pw;
    String tem_pw;
    private EditText et_num_id, et_store_id, et_id_pw, et_num_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding);

        et_store_id = findViewById(R.id.find_store);
        et_num_id = findViewById(R.id.find_num);
        et_id_pw = findViewById(R.id.find_by_id);
        et_num_pw = findViewById(R.id.find_by_num);


        btn_find_id = findViewById(R.id.find_id);
        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int find_num = Integer.parseInt(et_num_id.getText().toString());
                String find_store = et_store_id.getText().toString();

                if (find_store.length() == 0) {
                    Toast.makeText(FindingActivity.this, "매장명을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_store_id.requestFocus();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "사용자가 있습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "해당 정보에 맞는 사용자가 없습니다", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FindingRequest findingRequest = new FindingRequest(find_store, responseListener); //담아줌 넘 빼고
                RequestQueue queue = Volley.newRequestQueue(FindingActivity.this);
                queue.add(findingRequest);
            }
        });

        btn_find_pw = findViewById(R.id.find_pw);
        btn_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String find_id_pw = et_id_pw.getText().toString();
                String find_num_pw = et_num_pw.getText().toString();
                if (find_id_pw.equals("")) {
                    Toast.makeText(FindingActivity.this, "아이디룰 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_id_pw.requestFocus();
                    return;
                }
                if (find_num_pw.equals("")) {
                    Toast.makeText(FindingActivity.this, "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_num_pw.requestFocus();
                    return;
                }
                tem_pw = "qwer1234"; //임시 비밀번호, 비밀번호 찾아서 지정하는 형식
                new AlertDialog.Builder(FindingActivity.this)
                        .setTitle("당신의 비밀번호는")
                        .setMessage(tem_pw + "\n입니다")
                        .setPositiveButton("확인", null)
                        .show();
            }
        });
    }
}