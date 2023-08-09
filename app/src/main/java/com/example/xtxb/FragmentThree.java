package com.example.xtxb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentThree extends Fragment {

    TextView remainingParkingTextView;
    Button changeParkingButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        remainingParkingTextView = view.findViewById(R.id.remainingParkingTextView);
        // 设置剩余停车位数，您可以从合适的地方获取该数值
        remainingParkingTextView.setText("剩余停车位：" + getRemainingParkingCount());
        changeParkingButton = view.findViewById(R.id.changeParkingButton);
        changeParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeParkingDialog();
            }
        });
        return view;
    }

    // 显示修改停车位数的对话框
    public void showChangeParkingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改停车位数");

        final EditText input = new EditText(getActivity());
        builder.setView(input);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取用户输入的停车位数并更新界面
                String newParkingCount = input.getText().toString();
                updateRemainingParkingCount(newParkingCount);
                showSuccessDialog();
            }
        });

        builder.setNegativeButton("取消", null);

        Dialog dialog = builder.create();
        dialog.show();
    }

    // 更新剩余停车位数
    private void updateRemainingParkingCount(String newCount) {
        remainingParkingTextView.setText("剩余停车位：" + newCount);
    }

    // 显示成功对话框
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("在区块链上修改成功！");
        builder.setPositiveButton("确定", null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    // 获取剩余停车位数的逻辑，您可以根据实际情况实现
    private int getRemainingParkingCount() {
        return 426; // 这里示意为 10，您需要根据实际情况返回正确的数值
    }
}
