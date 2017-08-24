package com.dxc.mycollector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dxc.mycollector.dbhelp.SqliteDB;
import com.dxc.mycollector.serviecs.UserService;

/**
 * Created by gospel on 2017/8/18.
 * About Login
 */
public class BlueToothFolder extends Activity {
    private Button button;//登录按钮
    private EditText username;
    private EditText lgpwd;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setTitle("Login");
        button = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        username.setText("Gospel");
        lgpwd = (EditText) findViewById(R.id.lgpwd);
        lgpwd.setText("111111");
        context = this;
        /**
         * 登录按钮的点击事件
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username != null && username.length() > 0) {
                    if (lgpwd != null && lgpwd.length() >= 6 && lgpwd.length() <= 20) {
                        UserService userServices = new UserService(context);
//                        boolean isTure = userServices.login(username.getText().toString(), lgpwd.getText().toString());
                        int isTure = SqliteDB.getInstance(getApplicationContext()).Quer(lgpwd.getText().toString(), username.getText().toString());
                        if (isTure == 1) {
                            Toast.makeText(BlueToothFolder.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            /*登录成功，进入任务下载页面*/
                            startActivity(new Intent(context, TaskDownloadActivity.class));
//                            startActivity(new Intent(context, PersonAcitvity.class));
                        } else if (isTure == 0) {
                            Toast.makeText(BlueToothFolder.this, "This User is no Register!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BlueToothFolder.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(BlueToothFolder.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BlueToothFolder.this, "username is not null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*注册*/
    public void register(View view) {
        startActivity(new Intent(this, RegisterAcitvity.class));
    }
}
