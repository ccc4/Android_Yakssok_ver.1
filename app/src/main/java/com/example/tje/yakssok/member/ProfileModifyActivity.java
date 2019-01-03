package com.example.tje.yakssok.member;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Count;
import com.example.tje.yakssok.model.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileModifyActivity extends AppCompatActivity {


    private static final String LOG_TAG ="Yakssok";
    public static final String SERVER_ADDRESS = "http://172.30.1.59:8080/Yakssok";


    Member loginMember;


    Gson gson;

    EditText modify_id, modify_name, modify_age, modify_nickname,modify_tel, modify_email;
    TextView modify_address1, modify_address2;
    EditText modify_address3;
    TextView error_nickname, error_id;
    Button btn_address, modify_cancel, modify_ok;


    public void initRefs(){

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        modify_id = findViewById(R.id.modify_id);
        modify_name = findViewById(R.id.modify_name);
        modify_age = findViewById(R.id.modify_age);
        modify_tel = findViewById(R.id.modify_tel);
        modify_nickname = findViewById(R.id.modify_nickname);
        modify_email = findViewById(R.id.modify_email);
        modify_address1 = findViewById(R.id.modify_address1);
        modify_address2 = findViewById(R.id.modify_address2);
        modify_address3 = findViewById(R.id.modify_address3);
        error_nickname = findViewById(R.id.error_nickname);
        error_id = findViewById(R.id.error_id);
        btn_address = findViewById(R.id.btn_address);

        modify_cancel = findViewById(R.id.modify_cancel);
        modify_ok = findViewById(R.id.modify_ok);


        set_info();



    }

    public void setEvents(){
        //닉네임 중복체크
        modify_nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                //닉네임 공백제거
                String trim = modify_nickname.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                modify_nickname.setText(trim);

                if(hasFocus == false && modify_nickname.getText().toString().trim().length() > 0){

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL endPoint = new URL( SERVER_ADDRESS + "/member/mCheckNick");
                                HttpURLConnection myConnection =(HttpURLConnection)endPoint.openConnection();
                                Log.d(LOG_TAG,"체크닉네임 커넥션 객체 생성");

                                String nickname = modify_nickname.getText().toString();

                                //전송모드설정
                                myConnection.setRequestMethod("POST");

                                String str = "nickname=%s";

                                String requestParam =String.format(str,nickname);
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
                }else if( modify_nickname.getText().toString().length() == 0 ){
                    error_nickname.setTextColor(Color.rgb(204,000,000));
                    error_nickname.setText("필수사항 입니다.");
                }

            }
        });

        modify_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modify_name.getText().length() == 0 || modify_name.getText().toString().trim().isEmpty()){
                    modify_name.requestFocus();
                    modify_name.setText("");
                    return;
                }
                if(modify_age.getText().length() == 0 || modify_age.getText().toString().trim().isEmpty()) {
                    modify_age.requestFocus();
                    modify_age.setText("");
                    return;
                }
                if(modify_nickname.getText().length() == 0 || error_nickname.getText().equals("이미 사용중인 닉네임 입니다.")
                        || modify_nickname.getText().toString().trim().isEmpty()) {
                    modify_nickname.requestFocus();
                    modify_nickname.setText("");
                    return;
                }
                if(modify_tel.getText().length() == 0 || modify_tel.getText().toString().trim().isEmpty()) {
                    modify_tel.requestFocus();
                    modify_tel.setText("");
                    return;
                }
                if(modify_email.getText().length() == 0 || modify_email.getText().toString().trim().isEmpty()) {
                    modify_email.requestFocus();
                    modify_email.setText("");
                    return;
                }
                if(modify_address1.getText().length()==0) {
                    modify_address3.requestFocus();
                    return;
                }
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL endPoint = new URL( SERVER_ADDRESS + "/member/mModifyProfile");
                            HttpURLConnection myConnection =(HttpURLConnection)endPoint.openConnection();
                            Log.d(LOG_TAG,"커넥션 객체 생성");

                            String id = modify_id.getText().toString();
                            String name  = modify_name.getText().toString();
                            String age = modify_age.getText().toString();
                            String nickname = modify_nickname.getText().toString();
                            String tel = modify_tel.getText().toString();
                            String email = modify_email.getText().toString();
                            String address = modify_address1.getText().toString()+"," +modify_address2.getText().toString()+","+modify_address3.getText().toString();

                            //전송모드설정
                            myConnection.setRequestMethod("POST");

                            String str = "id=%s&name=%s&age=%s&nickname=%s&tel=%s&email=%s&address=%s";


                            String requestParam =String.format(str,id,name,age,nickname,tel,email,address);
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
                                //gson = new Gson();
                                loginMember = gson.fromJson(buffer.toString(),Member.class);
                                Log.d(LOG_TAG,"프로필수정 닉네임 값: "+loginMember.getNickname());
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("loginMember",loginMember);
                                startActivity(intent);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"수정완료",Toast.LENGTH_LONG).show();
                                    }
                                });

                                Log.d(LOG_TAG,"수정완료");




                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"수정에 실패했습니다.",Toast.LENGTH_LONG).show();
                                    }
                                });
                                Log.d(LOG_TAG,"실패! 응답코드:"+ myConnection.getResponseCode());
                            }
                        } catch (Exception e) {
                            Log.d(LOG_TAG,"catch 진입");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DaumWebViewActivity.class);
                startActivityForResult(intent,15);
            }
        });
        modify_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intentC) {
        if(resultCode == RESULT_CANCELED){
            return;
        }
        String str_add = intentC.getStringExtra("address");
        Log.d(LOG_TAG,"조인액티비티에서 주소값을 받았다!:"+str_add);
        modify_address1.setText( str_add.substring(0,7));
        Log.d(LOG_TAG,"우편번호:"+modify_address1);
        modify_address2.setText( str_add.substring(7));
        Log.d(LOG_TAG,"도로명 주소:"+modify_address2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modify);

        Intent intent = getIntent();
        loginMember = (Member)intent.getSerializableExtra("loginMember");


        initRefs();
        setEvents();

    }

    public void set_info(){
        modify_id.setText(loginMember.getId());
        modify_name.setText(loginMember.getName());
        modify_age.setText((Integer.toString(loginMember.getAge())));
        modify_tel.setText(loginMember.getTel());
        modify_nickname.setText(loginMember.getNickname());
        modify_email.setText(loginMember.getEmail());

        String address = loginMember.getAddress();
        String[] list = address.split(",");

        for(String item : list) {
            Log.d(LOG_TAG, item);
        }

        modify_address1.setText(list[0]);
        modify_address2.setText(list[1]);
        modify_address3.setText(list[2]);

    }

}
