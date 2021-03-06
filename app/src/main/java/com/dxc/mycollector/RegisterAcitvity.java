package com.dxc.mycollector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private Spinner countySpinner = null;    //县级（区、县、县级市）
    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;
    private EditText username;
    private EditText password;
    private EditText repassword;
    private EditText phone;
    private EditText adress;
    private RadioGroup sex;
    private RadioButton male;
    private RadioButton female;
    //省级选项值
    private String[] province = new String[]{"北京", "上海", "天津", "广东"};//,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
    //地级选项值
    private String[][] city = new String[][]
            {
                    {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
                            "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县",
                            "延庆县"},
                    {"长宁区", "静安区", "普陀区", "闸北区", "虹口区"},
                    {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区",
                            "东丽区"},
                    {"广州", "深圳", "韶关" // ,"珠海","汕头","佛山","湛江","肇庆","江门","茂名","惠州","梅州",
                            // "汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"
                    }
            };

    //县级选项值
    private String[][][] county = new String[][][]
            {
                    {   //北京
                            {"东单"}, {"人定湖北巷"}, {"天坛公园"}, {"三井社区"}, {"望京"}, {"北京大学"}, {"永定河"}, {"无"}, {"无"}, {"无"},
                            {"无"}, {"后沙峪"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //上海
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //天津
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //广东
                            {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"}, //广州
                            {"宝安区", "福田区", "龙岗区", "罗湖区", "南山区", "盐田区"}, //深圳
                            {"武江区", "浈江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源县"}  //韶关
                    }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        setSpinner();
        setTitle("Register");
        button = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.siguername);
        password = (EditText) findViewById(R.id.sigpassword);
        repassword = (EditText) findViewById(R.id.sigrepassword);
        phone = (EditText) findViewById(R.id.sigphone);
        adress = (EditText) findViewById(R.id.sigaddress);
        sex = (RadioGroup) this.findViewById(R.id.radiogroup1);
        male = (RadioButton) this.findViewById(R.id.male);
        female = (RadioButton) this.findViewById(R.id.female);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService userServices = new UserService(context);
//                User user = new User();
//                user.setuName("Gospel");
//                user.setuAge("30");
//                user.setuPhone("15210769058");
//                user.setuPwd("111111");
//                user.setuSex("0");//0女1男
//                user.setuAddress("北京市朝阳区金兴路一号院");
                User newUser = GetText();
                if (!isEmpty(newUser)) {
//                boolean isTure = userServices.register(user);
                    int isTure = SqliteDB.getInstance(getApplicationContext()).saveUser(newUser);
                    if (isTure == 1) {
                        Toast.makeText(RegisterAcitvity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    } else if (isTure == -1) {
                        Toast.makeText(RegisterAcitvity.this, "this user is Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterAcitvity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
//                Toast.makeText(RegisterAcitvity.this, "Create Success ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public User GetText() {
        User user = new User();
        user.setuName(username.getText().toString().trim());
        user.setuPwd(password.getText().toString().trim());
        user.setuPhone(phone.getText().toString().trim());
        user.setuAddress(adress.getText().toString().trim());
        user.setuRePwd(repassword.getText().toString().trim());
        user.setuAddress(adress.getText().toString().trim());
        user.setuSex(sex.getCheckedRadioButtonId() + "");

        return user;
    }

    private boolean isEmpty(User user) {
//        GetText();

        if (TextUtils.isEmpty(user.getuName())) {
            Toast.makeText(RegisterAcitvity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(user.getuPwd())) {
            Toast.makeText(RegisterAcitvity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else if (user.getuPwd().length() < 0) {
            Toast.makeText(RegisterAcitvity.this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(user.getuRePwd())) {
            Toast.makeText(RegisterAcitvity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!user.getuPwd().equals(user.getuRePwd())) {
            Toast.makeText(RegisterAcitvity.this, "确认密码和原密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(user.getuPhone())) {
            Toast.makeText(RegisterAcitvity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(user.getuAddress())) {
            Toast.makeText(RegisterAcitvity.this, "地址不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!male.isChecked() && !female.isChecked()) {
            Toast.makeText(RegisterAcitvity.this, "pls choose sex", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void setSpinner() {
        provinceSpinner = (Spinner) findViewById(R.id.spin_province);
        citySpinner = (Spinner) findViewById(R.id.spin_city);
        countySpinner = (Spinner) findViewById(R.id.spin_county);

        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(RegisterAcitvity.this,
                android.R.layout.simple_spinner_item, province);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(3, true);  //设置默认选中项，此处为默认选中第4个值

        cityAdapter = new ArrayAdapter<String>(RegisterAcitvity.this,
                android.R.layout.simple_spinner_item, city[3]);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0, true);  //默认选中第0个

        countyAdapter = new ArrayAdapter<String>(RegisterAcitvity.this,
                android.R.layout.simple_spinner_item, county[3][0]);
        countySpinner.setAdapter(countyAdapter);
        countySpinner.setSelection(0, true);


        //省级下拉框监听
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //position为当前省级选中的值的序号

                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        RegisterAcitvity.this, android.R.layout.simple_spinner_item, city[position]);
                // 设置二级下拉列表的选项内容适配器
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        //地级下拉监听
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                countyAdapter = new ArrayAdapter<String>(RegisterAcitvity.this,
                        android.R.layout.simple_spinner_item, county[provincePosition][position]);
                countySpinner.setAdapter(countyAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
}
