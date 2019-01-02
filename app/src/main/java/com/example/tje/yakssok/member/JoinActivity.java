package com.example.tje.yakssok.member;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Count;
import com.example.tje.yakssok.model.Member;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinActivity extends AppCompatActivity {

    private static final String LOG_TAG ="yakssokjoin";
    public static final String SERVER_ADDRESS = "http://192.168.0.24:8080/Yakssok";

    EditText join_id;
    EditText join_pw;
    EditText join_check_pw;
    EditText join_name;
    EditText join_age;
    EditText join_nickname;
    EditText join_tel;
    EditText join_email;

    TextView error_id;
    TextView error_nickname;
    // TextView error_phone;
    TextView error_pw;


    TextView str_address1, str_address2;
    EditText str_address3;

    RadioGroup join_gender;
    RadioButton join_gender_male, join_gender_female;

    Button btn_address;
    Button join_cancel, join_ok;



    public void initRefs(){
        join_id = findViewById(R.id.join_id);
        join_pw = findViewById(R.id.join_pw);
        join_check_pw = findViewById(R.id.join_check_pw);
        join_name = findViewById(R.id.join_name);
        join_age = findViewById(R.id.join_age);
        join_nickname = findViewById(R.id.join_nickname);
        join_tel = findViewById(R.id.join_tel);
        join_email = findViewById(R.id.join_email);

        error_id = findViewById(R.id.error_id);
        error_nickname = findViewById(R.id.error_nickname);
        // error_phone = findViewById(R.id.error_phone);
        error_pw = findViewById(R.id.error_pw);

        str_address1=findViewById(R.id.str_address1);
        str_address2=findViewById(R.id.str_address2);
        str_address3=findViewById(R.id.str_address3);

        join_gender = findViewById(R.id.radio_gender);
        join_gender_male = findViewById(R.id.join_gender_male);
        join_gender_female = findViewById(R.id.join_gender_female);

        btn_address = findViewById(R.id.btn_address);
        join_cancel = findViewById(R.id.join_cancel);
        join_ok = findViewById(R.id.join_ok);

    }

    public void setEvents(){
        // 다른 텍스트로 포커스가 넘어가면 앞뒤, 글자 사이의 공백 제거
        join_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = join_name.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                join_name.setText(trim);
            }
        });

        join_nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = join_nickname.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                join_nickname.setText(trim);
            }
        });

        join_tel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = join_tel.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                trim = trim.replaceAll("-", "");
                trim = trim.replaceAll("_", "");
                join_tel.setText(trim);

            }
        });

        join_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = join_email.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                join_email.setText(trim);

            }
        });
        //아이디 중복체크
        join_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                //공백 제거
                String trim = join_id.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                join_id.setText(trim);

                if(hasFocus == false && join_id.getText().toString().trim().length() > 0){
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL endPoint = new URL( SERVER_ADDRESS + "/member/mCheckId");
                                HttpURLConnection myConnection =(HttpURLConnection)endPoint.openConnection();
                                Log.d(LOG_TAG,"커넥션 객체 생성");

                                String id = join_id.getText().toString();

                                //전송모드설정
                                myConnection.setRequestMethod("POST");

                                String str = "id=%s";

                                String requestParam =String.format(str,id);
                                Log.d(LOG_TAG,requestParam);
                                Log.d(LOG_TAG,"서버로 전송");
                                myConnection.setDoOutput(true); //서버에서 읽기모드
                                myConnection.getOutputStream().write(requestParam.getBytes());//서버로 쓰기

                                if(myConnection.getResponseCode() == 200){
                                    Log.d(LOG_TAG,"200진입");
                                    BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;
                                    while((temp = in.readLine())!= null)
                                        buffer.append(temp);
                                    Gson gson = new Gson();
                                    Count count = gson.fromJson(buffer.toString(), Count.class);

                                    StringBuffer data = new StringBuffer();
                                    Log.d(LOG_TAG, buffer.toString());
                                    if( count.getCount() == 1 ) {
                                        data.append("이미 사용중이거나 탈퇴한 아이디 입니다.");
                                        error_id.setTextColor(Color.rgb(204,000,000));
                                        error_id.setText(data.toString());
                                    }else{
                                        data.append("멋진 아이디 입니다!");
                                        error_id.setTextColor(Color.rgb(102,204,204));
                                        error_id.setText(data.toString());
                                    }
                                }else{
                                    Log.d(LOG_TAG,"실패! 응답코드:"+ myConnection.getResponseCode());
                                }
                            } catch (Exception e) {
                                Log.d(LOG_TAG,"catch 진입");
                                e.printStackTrace();
                            }
                        }
                    });
                }else if( join_id.getText().toString().length() == 0 ){
                    error_id.setTextColor(Color.rgb(204,000,000));
                    error_id.setText("필수사항 입니다.");
                }

            }
        });
        //닉네임 중복체크
        join_nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                //닉네임 공백제거
                String trim = join_nickname.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                join_nickname.setText(trim);

                if(hasFocus == false && join_nickname.getText().toString().trim().length() > 0){

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL endPoint = new URL( SERVER_ADDRESS + "/member/mCheckNick");
                                HttpURLConnection myConnection =(HttpURLConnection)endPoint.openConnection();
                                Log.d(LOG_TAG,"커넥션 객체 생성");

                                String id = join_nickname.getText().toString();

                                //전송모드설정
                                myConnection.setRequestMethod("POST");

                                String str = "nickname=%s";

                                String requestParam =String.format(str,id);
                                Log.d(LOG_TAG,requestParam);
                                Log.d(LOG_TAG,"서버로 전송");
                                myConnection.setDoOutput(true); //서버에서 읽기모드
                                myConnection.getOutputStream().write(requestParam.getBytes());//서버로 쓰기

                                if(myConnection.getResponseCode() == 200){
                                    Log.d(LOG_TAG,"200진입");
                                    BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;
                                    while((temp = in.readLine())!= null)
                                        buffer.append(temp);
                                    Gson gson = new Gson();
                                    Count count = gson.fromJson(buffer.toString(), Count.class);

                                    StringBuffer data = new StringBuffer();
                                    Log.d(LOG_TAG, buffer.toString());
                                    if( count.getCount() == 1 ) {
                                        data.append("이미 사용중인 닉네임 입니다.");
                                        error_nickname.setTextColor(Color.rgb(204,000,000));
                                        error_nickname.setText(data.toString());
                                    }else{
                                        data.append("멋진 닉네임 입니다!");
                                        error_nickname.setTextColor(Color.rgb(102,204,204));
                                        error_nickname.setText(data.toString());
                                    }
                                }else{
                                    Log.d(LOG_TAG,"실패! 응답코드:"+ myConnection.getResponseCode());
                                }
                            } catch (Exception e) {
                                Log.d(LOG_TAG,"catch 진입");
                                e.printStackTrace();
                            }
                        }
                    });
                }else if( join_nickname.getText().toString().length() == 0 ){
                    error_nickname.setTextColor(Color.rgb(204,000,000));
                    error_nickname.setText("필수사항 입니다.");
                }

            }
        });
        // 비밀번호 실시간 확인 체크
        join_check_pw.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //변경전
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //변경 중
                String password = join_pw.getText().toString();
                String confirm = join_check_pw.getText().toString();

                if (password.equals(confirm)) {
                    error_pw.setTextColor(Color.rgb(102,204,204));
                    error_pw.setText("비밀번호 확인");
                } else {
                    error_pw.setTextColor(Color.rgb(204,000,000));
                    error_pw.setText("비밀번호가 다릅니다.");
                }
            }
            public void afterTextChanged(Editable s) {
                //변경후
                //비밀번호 공백제거
                String trim1 = join_pw.getText().toString();
                trim1 = trim1.trim();
                trim1 = trim1.replaceAll(" ", "");
                join_pw.setText(trim1);
            }
        });

        join_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentA = new Intent(getApplication(),DaumWebViewActivity.class);
                startActivityForResult(intentA, 15);
            }
        });

        join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (join_id.getText().length() == 0 || join_id.getText().toString().trim().isEmpty()) {
                    join_id.requestFocus();
                    join_id.setText("");
                    return;
                }
                if(error_id.getText().equals("이미 사용중이거나 탈퇴한 아이디 입니다.")) {
                    join_id.requestFocus();
                    return;
                }
                if(join_pw.getText().length() == 0 || !join_pw.getText().toString().equals(join_check_pw.getText().toString())
                        || join_pw.getText().toString().trim().isEmpty()) {
                    join_pw.requestFocus();
                    return;
                }
                if(join_check_pw.getText().length() == 0 || join_check_pw.getText().toString().trim().isEmpty()) {
                    join_check_pw.requestFocus();
                    return;
                }
                if(join_name.getText().length() == 0 || join_name.getText().toString().trim().isEmpty()){
                    join_name.requestFocus();
                    join_name.setText("");
                    return;
                }
                if(join_age.getText().length() == 0 || join_age.getText().toString().trim().isEmpty()) {
                    join_age.requestFocus();
                    join_age.setText("");
                    return;
                }
                if(join_nickname.getText().length() == 0 || error_nickname.getText().equals("이미 사용중인 닉네임 입니다.")
                        || join_nickname.getText().toString().trim().isEmpty()) {
                    join_nickname.requestFocus();
                    join_nickname.setText("");
                    return;
                }
                if(join_tel.getText().length() == 0 || join_tel.getText().toString().trim().isEmpty()) {
                    join_tel.requestFocus();
                    join_tel.setText("");
                    return;
                }
                if(join_email.getText().length() == 0 || join_email.getText().toString().trim().isEmpty()) {
                    join_email.requestFocus();
                    join_email.setText("");
                    return;
                }
                if(str_address1.getText().length()==0) {
                    str_address3.requestFocus();
                    return;
                }

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL endPoint = new URL( SERVER_ADDRESS + "/member/mJoin");
                            HttpURLConnection myConnection =(HttpURLConnection)endPoint.openConnection();
                            Log.d(LOG_TAG,"커넥션 객체 생성");

                            String id = join_id.getText().toString();
                            String pw = join_pw.getText().toString();
                            String name  = join_name.getText().toString();
                            String age = join_age.getText().toString();
                            String nickname = join_nickname.getText().toString();
                            String tel = join_tel.getText().toString();
                            String email = join_email.getText().toString();
                            int gender;
                            String address = str_address1.getText().toString()+"," +str_address2.getText().toString()+","+str_address3.getText().toString();


                            if(join_gender_male.isChecked())
                                gender=1;
                            else
                                gender =0;
                            Log.d(LOG_TAG,"선택된 젠더 값:"+gender);

                            //전송모드설정
                            myConnection.setRequestMethod("POST");

                            String str = "id=%s&pw=%s&name=%s&age=%s&nickname=%s&tel=%s&email=%s&gender=%d&address=%s";


                            String requestParam =String.format(str,id,pw,name,age,nickname,tel,email,gender,address);
                            Log.d(LOG_TAG,requestParam);
                            Log.d(LOG_TAG,"서버로 전송");
                            myConnection.setDoOutput(true); //서버에서 읽기모드
                            myConnection.getOutputStream().write(requestParam.getBytes());//서버로 쓰기


                            if(myConnection.getResponseCode() == 200){
                                Log.d(LOG_TAG,"200진입");
                                BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                StringBuffer buffer = new StringBuffer();
                                String temp = null;
                                while((temp = in.readLine())!= null)
                                    buffer.append(temp);
                                Gson gson = new Gson();
                                final Member member = gson.fromJson(buffer.toString(),Member.class);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),member.getId()+"로 가입완료!",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }else{
                                Toast.makeText(getApplicationContext(),"회원가입에 실패했습니다.",Toast.LENGTH_LONG).show();
                                Log.d(LOG_TAG,"실패! 응답코드:"+ myConnection.getResponseCode());
                            }
                        } catch (Exception e) {
                            Log.d(LOG_TAG,"catch 진입");
                            e.printStackTrace();
                        }
                    }
                });
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intentB) {
        if(resultCode == RESULT_CANCELED){
            return;
        }
        String str_add = intentB.getStringExtra("address");
        Log.d(LOG_TAG,"조인액티비티에서 주소값을 받았다!:"+str_add);
        str_address1.setText( str_add.substring(0,7));
        Log.d(LOG_TAG,"우편번호:"+str_address1);
        str_address2.setText( str_add.substring(7));
        Log.d(LOG_TAG,"도로명 주소:"+str_address2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        int permission_internet = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.INTERNET);

        if(permission_internet == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 12);


        }

        initRefs();
        setEvents();
    }
}
