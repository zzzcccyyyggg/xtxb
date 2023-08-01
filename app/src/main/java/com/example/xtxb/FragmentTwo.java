package com.example.xtxb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FragmentTwo extends Fragment {

    private List<ParkingRequest> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ParkingRequestAdapter requestAdapter;

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        // 初始化RecyclerView和适配器
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestAdapter = new ParkingRequestAdapter(requestList, new ParkingRequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ParkingRequest request) {
                // 处理点击停车申请记录的操作，在此处弹窗询问用户是否允许或拒绝申请
                showConfirmDialog(request);
            }
        });
        recyclerView.setAdapter(requestAdapter);

        // 在这里添加20条随机生成的停车申请记录
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < 20; i++) {
            String applicantName = "申请人" + (i + 1);
            String licensePlate = "车牌号：" + generateRandomLicensePlate();
            String requestTime = "申请时间：" + sdf.format(generateRandomTime());

            requestList.add(new ParkingRequest(applicantName, licensePlate, requestTime));
        }

        return view;
    }

    // 显示确认对话框的方法
    private void showConfirmDialog(ParkingRequest request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("停车申请")
                .setMessage("是否允许申请人 " + request.getApplicantName() + " 的车辆（车牌号：" + request.getLicensePlate() + "）停放？")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 处理允许按钮点击事件，在此处添加一条车辆记录
                        Random random = new Random();
                        String licensePlate = "车位：" + generateRandomLetter() + (random.nextInt(101) + 100);
                        String owner = "密钥：" + (random.nextInt(400001) + 100000);

                        // 添加车辆记录

                        // 显示成功信息
                        Handler handler = new Handler();

                        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_LONG).show();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), licensePlate, Toast.LENGTH_LONG).show();
                            }
                        }, 1000); // 2000 milliseconds (2 seconds) delay between the first and second Toast messages

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), owner, Toast.LENGTH_LONG).show();
                            }
                        }, 2000); // 4000 milliseconds (4 seconds) delay between the second and third Toast messages
                        requestList.remove(request);
                        requestAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 处理拒绝按钮点击事
                        requestList.remove(request);
                        requestAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 生成随机字母的方法
    private char generateRandomLetter() {
        Random random = new Random();
        return (char) (random.nextInt(26) + 'A');
    }

    // 生成随机车牌号的方法
    private String generateRandomLicensePlate() {
        Random random = new Random();
        StringBuilder licensePlate = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            char randomChar = (char) (random.nextInt(26) + 'A');
            licensePlate.append(randomChar);
        }
        return licensePlate.toString();
    }

    // 生成随机时间的方法
    private Date generateRandomTime() {
        Random random = new Random();
        long currentTime = System.currentTimeMillis();
        long randomTime = currentTime - random.nextInt(365) * 24 * 60 * 60 * 1000; // 随机生成过去一年内的时间
        return new Date(randomTime);
    }
}
