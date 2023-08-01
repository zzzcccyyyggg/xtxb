package com.example.xtxb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xtxb.Car;
import com.example.xtxb.CarListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FragmentOne extends Fragment {

    private List<Car> carList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CarListAdapter carListAdapter;

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        // 初始化RecyclerView和适配器
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carListAdapter = new CarListAdapter(carList, new CarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Car car) {
                // 创建对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("操作选项")
                        .setItems(new CharSequence[]{"取消", "下链", "修改密钥"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        // 取消操作，什么也不做
                                        break;
                                    case 1:
                                        // 下链操作，删除该车辆记录并显示提示
                                        car.setState("未被占用");
                                        carListAdapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "下链成功", Toast.LENGTH_LONG).show();
                                        break;
                                    case 2:
                                        // 修改密钥操作，生成新的密钥并更新车辆记录
                                        Random random = new Random();
                                        String newOwner = "密钥：" + (random.nextInt(50000000 - 1000000 + 1) + 1000000);
                                        car.setOwner(newOwner);
                                        carListAdapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "密钥修改成功", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        });

                // 显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        recyclerView.setAdapter(carListAdapter);

        // 在这里添加20条随机车辆信息到carList
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < 20; i++) {
            String licensePlate = "停车位："+generateRandomLicensePlate();
            String owner = "密钥：" + (random.nextInt(50000000 - 1000000 + 1) + 1000000);
            String parkingTime = "开始停车时间："+sdf.format(generateRandomTime());

            carList.add(new Car(licensePlate, owner, parkingTime,"已被预约"));
        }

        return view;
    }

    // 生成随机车牌号的方法
    private String generateRandomLicensePlate() {
        Random random = new Random();
        char randomChar = (char) (random.nextInt(26) + 'A');
        int randomNumber = random.nextInt(401) + 100; // Generate a random number between 100 and 500
        return String.valueOf(randomChar) + randomNumber;
    }

    // 生成随机时间的方法
    private Date generateRandomTime() {
        Random random = new Random();
        long currentTime = System.currentTimeMillis();
        long randomTime = currentTime - random.nextInt(365) * 24 * 60 * 60 * 1000; // 随机生成过去一年内的时间
        return new Date(randomTime);
    }
}
