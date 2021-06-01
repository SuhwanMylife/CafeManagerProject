package com.creapple.cafe_manager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SettingActivity extends AppCompatActivity {
    TextView text_info, text_contact;
    LinearLayout LL_notice;
    Button btn_salary, btn_lack; //추가
    int value;
    long now = System.currentTimeMillis();
    Date date = new Date(now);

    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    //세팅 기록 남기기
    private SharedPreferences sharedPreferences;
    private static final String ex = "Switch";
    private static final String ex2 = "Switch2";

    //추가(5.31)
    public static Context context_setting; // context 변수 선언
    public int lack = 1000; // 다른 Activity에서 접근할 변수

    //추가(5.31)
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        intent.putExtra("userID", ((MainActivity)MainActivity.context_main).userId);
        intent.putExtra("userPassword", ((MainActivity)MainActivity.context_main).userPassword);
        intent.putExtra("userNumber", ((MainActivity)MainActivity.context_main).userNumber);
        intent.putExtra("userStore", ((MainActivity)MainActivity.context_main).userStore);
        startActivity(intent);
        ((MainActivity)MainActivity.context_main).set_state++;

    }

    //추가(6.1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //추가(6.1)
        getSupportActionBar().setTitle("설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //추가(5.31)
        context_setting = this; // onCreate setting 에서 this 할당 (공유)

        //액션바 없애기...
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mCalender = new GregorianCalendar();
        Log.v("HelloAlarmActivity", mCalender.getTime().toString());

        //추가
        btn_salary = findViewById(R.id.btn_salary);
        btn_lack = findViewById(R.id.btn_lack);

        //sharedPreferences
        sharedPreferences = getSharedPreferences(" ", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //스위치 조절
        Switch sw_salary = (Switch)findViewById(R.id.sw_salary);
        sw_salary.setChecked(sharedPreferences.getBoolean(ex, false));
        sw_salary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //스위치 온
                    Toast.makeText(getApplicationContext(), "활성화 되었습니다. 날짜를 입력해주세요", Toast.LENGTH_SHORT).show();
                    btn_salary.setEnabled(true);
                    btn_salary.setBackgroundColor(getResources().getColor(R.color.btn));
                    editor.putBoolean(ex, true);

                } else {
                    //스위치 오프
                    Toast.makeText(getApplicationContext(), "비활성화 되었습니다", Toast.LENGTH_SHORT).show();
                    btn_salary.setEnabled(false);
                    btn_salary.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    value = 0; //알림 막음
                    editor.putBoolean(ex, false);
                }
                editor.commit();
            }
        });
        Switch sw_lack = (Switch)findViewById(R.id.sw_lack);
        sw_lack.setChecked(sharedPreferences.getBoolean(ex2, false));
        sw_lack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //스위치 온
                    Toast.makeText(getApplicationContext(), "활성화 되었습니다. 수량을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    btn_lack.setEnabled(true);
                    btn_lack.setBackgroundColor(getResources().getColor(R.color.btn));
                    editor.putBoolean(ex2, true);

                } else {
                    //스위치 오프
                    Toast.makeText(getApplicationContext(), "비활성화 되었습니다", Toast.LENGTH_SHORT).show();
                    btn_lack.setEnabled(false);
                    btn_lack.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    lack = 1000; //변경(5.31)
                    editor.putBoolean(ex2, false);
                }
                editor.commit();
            }
        });


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

        btn_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edit = new EditText(SettingActivity.this);

                android.app.AlertDialog.Builder dig = new AlertDialog.Builder(SettingActivity.this);
                dig.setMessage("1일~28일 중 하루를 입력해주세요\n(주의 : 숫자만 입력, 빈값 입력 X)");
                //글자수 제한
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(2);
                edit.setFilters(FilterArray);
                dig.setView(edit);
                dig.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //왜 빈값 체크 못하지...(
                        if(edit.equals("")) {
                            Toast.makeText(getApplicationContext(), "입력된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            int temp = Integer.parseInt(edit.getText().toString());
                            if (temp<0 || temp>28) {
                                Toast.makeText(getApplicationContext(), "값이 유효하지 않습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                value = Integer.parseInt(edit.getText().toString());
                                String mes = edit.getText().toString() + "일을 선택하였습니다.";
                                Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
                                setAlarm();
                            }
                        }
                    }
                });
                dig.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getApplicationContext(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dig.show();
            }
        });

        btn_lack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edit = new EditText(SettingActivity.this);

                android.app.AlertDialog.Builder dig = new AlertDialog.Builder(SettingActivity.this);
                dig.setMessage("1~9 사이의 수량을 입력해주세요\n(주의 : 숫자만 입력, 빈값 입력 X)");
                //글자수 제한
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(1);
                edit.setFilters(FilterArray);
                dig.setView(edit);
                dig.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //왜 빈값 체크 못하지...(
                        if(edit.equals("")) {
                            Toast.makeText(getApplicationContext(), "입력된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            int temp = Integer.parseInt(edit.getText().toString());
                            if (temp<0 || temp>28) {
                                Toast.makeText(getApplicationContext(), "값이 유효하지 않습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                lack = Integer.parseInt(edit.getText().toString());
                                String mes = "기준을"+edit.getText().toString() + "개로 설정하였습니다.";
                                Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
                dig.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getApplicationContext(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dig.show();
            }
        });


        /*


        final LinearLayout linear = (LinearLayout) View.inflate(SettingActivity.this, R.layout.dialog_num, null);
        // final String[] array = {"첫","둘둘","셋셋셋"};
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
                        .show(); */


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
        //    }
        //   });
    }
    /*
        @Override
        protected void onDestroy() {
            super.onDestroy();

            SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean st_salary = sharedPreferences.getBoolean("st_salary", false);
            if (st_salary = true) {
                tg.setChecked(true);
            } else {
                tg.setChecked(false);
            }
        }
    */
    private void setAlarm() {
        //AlarmReceiver에 값 전달
        Intent receiverIntent = new Intent(SettingActivity.this, AlarmRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, 0, receiverIntent, 0);

        //년, 달(month) 가져오기
        SimpleDateFormat sdfML = new SimpleDateFormat("yyyy-MM");
        String getML = sdfML.format(date);

        String from = getML+"-"+String.valueOf(value)+" 00:00:00"; //임의로 날짜와 시간을 지정

        //날짜 포맷을 바꿔주는 소스코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = null;
        try {
            datetime = dateFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent);

    }

}
