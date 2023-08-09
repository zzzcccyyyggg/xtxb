package com.example.xtxb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CarDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);


        // 从Intent中获取车辆信息
        String licensePlate = getIntent().getStringExtra("licensePlate");
        String owner = getIntent().getStringExtra("owner");
        String parkingTime = getIntent().getStringExtra("parkingTime");

        // 在TextView中显示车辆信息
        TextView licensePlateTextView = findViewById(R.id.licensePlateTextView);
        TextView ownerTextView = findViewById(R.id.ownerTextView);
        TextView parkingTimeTextView = findViewById(R.id.parkingTimeTextView);

        licensePlateTextView.setText("车牌号：" + licensePlate);
        ownerTextView.setText("车主：" + owner);
        parkingTimeTextView.setText("停车时间：" + parkingTime);

        // 处理按钮点击事件
        Button showDialogButton = findViewById(R.id.showDialogButton);
        Button changeKeyButton = findViewById(R.id.changeKeyButton);

        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog("该车已下链");
            }
        });

        changeKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newKey = generateNewKey();
                showConfirmDialog("密钥已更新\n新密钥为：" + newKey);
            }
        });
    }

    // 显示确认对话框的方法
    private void showConfirmDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 处理确认按钮点击事件
                        // 在此处添加您想要执行的操作
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 处理取消按钮点击事件
                        dialog.dismiss();
                    }
                });
        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 生成新的密钥
    private int generateNewKey() {
        Random random = new Random();
        return random.nextInt(5000000 - 1000000 + 1) + 1000000;
    }
}
