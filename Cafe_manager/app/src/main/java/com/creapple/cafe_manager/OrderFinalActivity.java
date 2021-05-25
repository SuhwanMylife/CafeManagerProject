package com.creapple.cafe_manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderFinalActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_final);

        Button orderFinal = findViewById(R.id.order_final);
        orderFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(OrderFinalActivity.this);
                builder.setMessage("발주 신청하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OrderFinalActivity.this, "발주 신청이 성공적으로 이루어졌습니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OrderFinalActivity.this, OrderMainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("아니오", ((dialog, which) -> { dialog.cancel(); }));
                builder.show();
            }
        });
    }

}
