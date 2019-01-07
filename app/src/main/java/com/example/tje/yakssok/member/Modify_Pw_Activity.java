package com.example.tje.yakssok.member;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Modify_Pw_Activity extends AppCompatActivity {

    public static final String LOG_TAG = "Yakssok";
    String SERVER_ADDRESS;

    Member loginMember;

    EditText str_member_pw;
    EditText str_member_newpw;
    EditText str_member_check_newpw;

    TextView error_member_pw;
    TextView error_member_newpw;
    TextView modify_id;

    Button btn_modify_pw_ok;
    Button btn_modify_pw_cancle;

    public void initRefs(){
        SERVER_ADDRESS = getString(R.string.SERVER_ADDRESS_STR);

        str_member_pw = (EditText)findViewById(R.id.str_member_pw);
        str_member_newpw = (EditText)findViewById(R.id.str_member_newpw);
        str_member_check_newpw = (EditText)findViewById(R.id.str_member_check_newpw);

        modify_id = (TextView) findViewById(R.id.modify_id);

        error_member_pw = (TextView)findViewById(R.id.error_member_pw);
        error_member_newpw = (TextView)findViewById(R.id.error_member_newpw);

        btn_modify_pw_ok = (Button)findViewById(R.id.btn_modify_pw_ok);
        btn_modify_pw_cancle = (Button)findViewById(R.id.btn_modify_pw_cancle);
    }

    public void setEvents(){
        // ID 값을 가져와서 텍스트창에 넣어줌
        final Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginMember");
        modify_id.setText(loginMember.getId());

        // 다른 텍스트로 포커스가 넘어가면 앞뒤, 글자 사이의 공백 제거
        str_member_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = str_member_pw.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                str_member_pw.setText(trim);
            }
        });

        str_member_newpw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = str_member_newpw.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                str_member_newpw.setText(trim);
            }
        });

        str_member_check_newpw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                String trim = str_member_check_newpw.getText().toString();
                trim = trim.trim();
                trim = trim.replaceAll(" ", "");
                str_member_check_newpw.setText(trim);
            }
        });

        btn_modify_pw_cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        str_member_check_newpw.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //변경전
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //변경 (자판 입력 중)중
                String password = str_member_newpw.getText().toString();
                String confirm = str_member_check_newpw.getText().toString();


                    if (password.equals(confirm)) {
                        error_member_newpw.setTextColor(Color.rgb(102, 204, 204));
                        error_member_newpw.setText("비밀번호 확인");
                    } else {
                        error_member_newpw.setTextColor(Color.rgb(204, 000, 000));
                        error_member_newpw.setText("비밀번호가 다릅니다.");
                    }

            }
            public void afterTextChanged(Editable s) {
                //변경후
                //비밀번호 공백제거
                String trim1 = str_member_newpw.getText().toString();
                trim1 = trim1.trim();
                trim1 = trim1.replaceAll(" ", "");
                str_member_newpw.setText(trim1);
            }
        });

        btn_modify_pw_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (str_member_pw.getText().length() == 0 || str_member_pw.getText().toString().trim().isEmpty()) {
                    str_member_pw.requestFocus();
                    return;
                }
                if (str_member_newpw.getText().length() == 0 || !str_member_newpw.getText().toString().equals(str_member_check_newpw.getText().toString())
                        || str_member_newpw.getText().toString().trim().isEmpty()) {
                    str_member_newpw.requestFocus();
                    return;
                }
                if (str_member_check_newpw.getText().length() == 0 || str_member_check_newpw.getText().toString().trim().isEmpty()) {
                    str_member_check_newpw.requestFocus();
                    return;
                }

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        String pw = str_member_pw.getText().toString();
                        String newPw = str_member_newpw.getText().toString();
                        String id = loginMember.getId();
                        try {
                            URL endPoint = new URL(SERVER_ADDRESS + "/member/mModifyPw");
                            HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                            Log.d(LOG_TAG, "커넥션 객체 생성");

                            //전송모드설정
                            myConnection.setRequestMethod("POST");

                            String str = "id=%s&pw=%s&newPw=%s";

                            String requestParam = String.format(str, id, pw, newPw);
                            Log.d(LOG_TAG, requestParam);
                            Log.d(LOG_TAG, "서버로 전송");
                            myConnection.setDoOutput(true); //서버에서 읽기모드
                            myConnection.getOutputStream().write(requestParam.getBytes());//서버로 쓰기

                            if (myConnection.getResponseCode() == 200) {
                                Log.d(LOG_TAG, "200진입");

                                BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                StringBuffer buffer = new StringBuffer();
                                String temp = null;
                                while ((temp = in.readLine()) != null)
                                    buffer.append(temp);
                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                Count count = gson.fromJson(buffer.toString(), Count.class);


                                StringBuffer data = new StringBuffer();
                                Log.d(LOG_TAG, buffer.toString());

                                if (count.getCount() == 0) {
                                    data.append("잘못된 비밀번호입니다.");
                                    error_member_pw.setTextColor(Color.rgb(204, 000, 000));
                                    error_member_pw.setText(data.toString());
                                } else {
                                    show_Toast("변경 성공");
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    //intent.putExtra("loginMember",loginMember);
                                    startActivity(intent);
                                }
                            } else {
                                Log.d(LOG_TAG, "실패! 응답코드:" + myConnection.getResponseCode());
                            }
                        } catch (Exception e) {
                            Log.d(LOG_TAG, "catch 진입");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void show_Toast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_modify);

        int permission_internet = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.INTERNET);
        if(permission_internet == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 12);
        }

        initRefs();
        setEvents();
    }
}
