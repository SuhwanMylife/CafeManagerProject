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

public class RegisterActivity extends AppCompatActivity {
    private Button btn_register, btn_check;
    private EditText et_id, et_pw, et_store, et_num, et_pw_re;
    private String check_message;
    private boolean validate = false;
    private AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_store = findViewById(R.id.et_store);
        et_num = findViewById(R.id.et_num);
        et_pw_re = findViewById(R.id.et_pw_re);


        btn_register = findViewById(R.id.btn_register);
        btn_check = findViewById(R.id.btn_check);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = et_id.getText().toString();
                String userPw = et_pw.getText().toString();
                String userStore = et_store.getText().toString();
                String userNum = et_num.getText().toString();
                String userPWre = et_pw_re.getText().toString();

                if (userPw.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    et_pw.requestFocus();
                    return;
                }
                if (userStore.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("매장명을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    et_store.requestFocus();
                    return;
                }
                if (userNum.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("전화번호를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    et_num.requestFocus();
                    return;
                }
                if (!userPWre.equals(userPw)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    et_pw_re.requestFocus();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setMessage("회원가입 신청이 완료되었습니다. 잠시 기다려 주세요. 확인 후 연락드리겠습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                                Toast.makeText(getApplicationContext(), "회원가입이 신청되었습니다", Toast.LENGTH_SHORT).show(); //토스트
                            } else {
                                Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userId, userPw, userStore, userNum, responseListener); //담아줌
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() { //아이디중복확인 체크되었는지 확인, 빈 곳이 있는지 체크
            @Override
            public void onClick(View v) {
                String userId = et_id.getText().toString();
                if (validate) {
                    return; //검증 완료
                }

                if (userId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    et_id.requestFocus();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            if (success) {
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                validate = true; //검증 완료
                                et_id.setEnabled(false);
                                btn_check.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });
    }
}
