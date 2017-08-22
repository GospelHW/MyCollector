package com.dxc.mycollector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dxc.mycollector.dbhelp.SqliteDB;
import com.dxc.mycollector.model.User;
import com.dxc.mycollector.serviecs.UserService;
/**
 * Created by gospel on 2017/8/18.
 * About Register
 */
public class RegisterAcitvity extends Activity {
    Context context;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        setTitle("Register");
        button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService userServices = new UserService(context);
                User user = new User();
                user.setuName("Gospel");
                user.setuAge("30");
                user.setuPhone("15210769058");
                user.setuPwd("111111");
                user.setuSex("0");//0女1男
                user.setuAddress("北京市朝阳区金兴路一号院");
//                boolean isTure = userServices.register(user);
                int isTure = SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                if (isTure == 1) {
                    Toast.makeText(RegisterAcitvity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                } else if (isTure == -1) {
                    Toast.makeText(RegisterAcitvity.this, "this user is Registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterAcitvity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(RegisterAcitvity.this, "Create Success ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
