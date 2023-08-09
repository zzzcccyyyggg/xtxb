package com.example.xtxb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 初始化列表用于显示停车历史记录
        ListView historyListView = findViewById(R.id.historyListView);

        // 创建停车历史记录数据
        List<ParkingHistory> parkingHistoryList = generateParkingHistoryList();

        // 接收传递的历史记录信息
        ParkingHistory newParkingHistory = getIntent().getParcelableExtra("newParkingHistory");
        if (newParkingHistory != null) {
            parkingHistoryList.add(0, newParkingHistory); // 将新的历史记录添加到列表的开头
        }


        // 创建自定义的适配器，并将数据与UI元素绑定
        ParkingHistoryAdapter adapter = new ParkingHistoryAdapter(this, R.layout.parking_history_item, parkingHistoryList);
        historyListView.setAdapter(adapter);

        Button ret = findViewById(R.id.backButton);
        ret.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<ParkingHistory> generateParkingHistoryList() {
        // 在这里生成20条停车历史记录，并返回一个列表
        MapApplication myApp = (MapApplication) getApplication();
        List<ParkingHistory> historyList = myApp.historyList;

        return historyList;
    }
}
