package com.example.xtxb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnDriver,btnManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_psw);
        btnDriver = findViewById(R.id.btnDriver);
        btnManager = findViewById(R.id.btnManager);
        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名和密码
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 模拟登录验证，这里可以根据实际情况调用服务器接口验证用户名和密码
                if (isValidCredentials(username, password)) {
                    // 登录成功，跳转到其他界面

                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    redirectToOtherPage("Driver");
                    // 在这里跳转到其他界面，例如主页或者用户个人信息界面
                } else {
                    // 登录失败，显示错误提示信息
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名和密码
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 模拟登录验证，这里可以根据实际情况调用服务器接口验证用户名和密码
                if (isValidCredentials(username, password)) {
                // 登录成功，跳转到其他界面
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                redirectToOtherPage("Manager");
                } else {
                // 登录失败，显示错误提示信息
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
}

    // 模拟登录验证，这里可以根据实际情况调用服务器接口验证用户名和密码
    private boolean isValidCredentials(String username, String password) {
        // 假设用户名为 "admin"，密码为 "123456"
        return username.equals("admin") && password.equals("123456");
    }
    private void redirectToOtherPage(String userType) {
        // 根据传入的参数来判断跳转到哪个Activity
        if ("Driver".equals(userType)) {
            // 跳转到主页
            Intent intent = new Intent(LoginActivity.this,DriverActivity.class);
            startActivity(intent);
        } else if ("Manager".equals(userType)) {
            // 跳转到用户个人信息界面
            Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
            startActivity(intent);
        }
        // 跳转之后，可以调用 finish() 方法关闭当前登录界面，防止用户通过返回按钮回到登录界面
        finish();
    }
}

