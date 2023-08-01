package com.example.xtxb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PoinDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poin_detail);

        // 获取传递的Intent
        Intent intent = getIntent();
        if (intent != null) {
            // 获取传递的停车场名称和地址
            String poiName = intent.getStringExtra("poiName");
            String poiAddress = intent.getStringExtra("poiAddress");

            // 生成随机的总车位、剩余车位和停车价格
            int totalSpaces = getRandomNumberInRange(1000, 2000);
            int availableSpaces = getRandomNumberInRange(100, 600);
            int parkingPrice = getRandomNumberInRange(5, 10);

            // 在此处可以使用poiName、poiAddress、totalSpaces、availableSpaces和parkingPrice
            // 显示相关详细信息
            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView addressTextView = findViewById(R.id.addressTextView);
            TextView totalSpacesTextView = findViewById(R.id.totalSpacesTextView);
            TextView availableSpacesTextView = findViewById(R.id.availableSpacesTextView);
            TextView priceTextView = findViewById(R.id.priceTextView);

            nameTextView.setText("停车场名称: " + poiName);
            addressTextView.setText("停车场地址: " + poiAddress);
            totalSpacesTextView.setText("总车位: " + totalSpaces);
            availableSpacesTextView.setText("剩余车位: " + availableSpaces);
            priceTextView.setText("停车价格: " + parkingPrice + " 元/小时");
        }



    }
    // 预定按钮点击事件处理
    public void onReserveButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认预定");
        builder.setMessage("是否购买预定停车位？");
        builder.setPositiveButton("购买", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 在这里处理“购买”按钮的点击事件
                // 添加处理购买操作的逻辑

                // 随机生成车位号
                String parkingNumber = generateParkingNumber();

                // 显示预定成功弹窗
                showReservationSuccessDialog(parkingNumber);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 在这里处理“取消”按钮的点击事件
                // 您可以添加处理取消操作的逻辑
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 随机生成车位号
    private String generateParkingNumber() {
        // 生成随机大写字母
        char randomLetter = (char) ('A' + Math.random() * ('Z' - 'A' + 1));

        // 生成随机整数，范围在50到100之间
        int randomNumber = (int) (Math.random() * (100 - 50 + 1) + 50);

        // 组合车位号
        return randomLetter + String.valueOf(randomNumber);
    }

    // 显示预定成功弹窗
    private void showReservationSuccessDialog(String parkingNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("预定成功");
        builder.setMessage("您已成功预定停车位，车位号为：" + parkingNumber);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 可以在预定成功弹窗点击确定后执行一些操作（如果需要的话）
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
