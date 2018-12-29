package com.example.tje.yakssok;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.yakssok.board.Board_MainActivity;
import com.example.tje.yakssok.member.JoinActivity;
import com.example.tje.yakssok.model.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Yakssok";
//    public static final String SERVER_ADDRESS = "http://192.168.10.132:8080/Yakssok";
    public static final String SERVER_ADDRESS = "http://192.168.219.146:8181/Yakssok";

    Gson gson;
    Member loginMember;






    TextView str_output;
    Button btn_main_login;
    Button btn_main_logout;
    Button btn_main_regist;
    Button btn_main_modify;
    Button btn_main_pill;
    Button btn_main_board;
    Button btn_main_drugstore;
    Button btn_main_emergency;

    private void setRefs() {
//        gson = new Gson();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        loginMember = null;

        str_output = (TextView)findViewById(R.id.str_output);
        btn_main_login = (Button)findViewById(R.id.btn_main_login);
        btn_main_logout = (Button)findViewById(R.id.btn_main_logout);
        btn_main_regist = (Button)findViewById(R.id.btn_main_regist);
        btn_main_modify = (Button)findViewById(R.id.btn_main_modify);
        btn_main_pill = (Button)findViewById(R.id.btn_main_pill);
        btn_main_board = (Button)findViewById(R.id.btn_main_board);
        btn_main_drugstore = (Button)findViewById(R.id.btn_main_drugstore);
        btn_main_emergency = (Button)findViewById(R.id.btn_main_emergency);
    }

    private void setEvents() {
        btn_main_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_main_drugstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_main_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_main_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_MainActivity.class);
                if(loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

        btn_main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = (View)View.inflate(getApplicationContext(), R.layout.dialog_login, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("로그인");
                dlg.setView(dialogView);
                dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    EditText str_id = (EditText) dialogView.findViewById(R.id.str_id);
                                    EditText str_pw = (EditText) dialogView.findViewById(R.id.str_pw);
                                    String id = str_id.getText().toString().trim();
                                    String pw = str_pw.getText().toString().trim();


                                    URL endPoint = new URL(SERVER_ADDRESS + "/member/mLogin");
                                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                                    // JSESSIONID 관련 - 1 시작
                                    String cookieString = CookieManager.getInstance().getCookie(
                                            SERVER_ADDRESS);
                                    if (cookieString != null) {
                                        myConnection.setRequestProperty("Cookie", cookieString);
                                    }
                                    // JSESSIONID 관련 - 1 끝

                                    // POST 값 전달
                                    myConnection.setRequestMethod("POST");

                                    String requestParam = String.format("id=%s&pw=%s", id, pw);
                                    myConnection.setDoOutput(true);
                                    myConnection.getOutputStream().write(requestParam.getBytes());
                                    // POST 값 전달 끝

                                    // JSESSIONID 관련 - 2 시작
                                    Map<String, List<String>> headerFields = myConnection.getHeaderFields();
                                    String COOKIES_HEADER = "Set-Cookie";
                                    List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                                    if (cookiesHeader != null) {
                                        for(String cookie : cookiesHeader) {
                                            String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                            String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                            cookieString = cookieName + "=" + cookieValue;
                                            CookieManager.getInstance().setCookie(SERVER_ADDRESS, cookieString);
                                        }
                                    }
                                    // JSESSIONID 관련 - 2 끝

                                    if (myConnection.getResponseCode() == 200) {
                                        Log.d(LOG_TAG, "200번 성공으로 들어옴");
                                        BufferedReader in =
                                                new BufferedReader(
                                                        new InputStreamReader(
                                                                myConnection.getInputStream()));
                                        StringBuffer buffer = new StringBuffer();
                                        String temp = null;
                                        while((temp = in.readLine()) != null) {
                                            buffer.append(temp);
                                        }
                                        Log.d(LOG_TAG, "중간체크");
                                        Log.d(LOG_TAG, buffer.toString());
                                        loginMember = gson.fromJson(buffer.toString(), Member.class);
                                        if(loginMember != null) {
                                            show_Toast("로그인성공!");
                                            Log.d(LOG_TAG, "로그인성공! " + loginMember.getNickname() + " 님 환영합니다 :)");
                                            check_login();
                                        } else {
                                            show_Toast("로그인실패!");
                                            Log.d(LOG_TAG, "로그인실패");
                                        }
                                    } else {    // 200이 아닐 때
                                        show_Toast("200번x");
                                        Log.d(LOG_TAG, "200번x");
                                    }
                                } catch (Exception e) {
                                    show_Toast("연결실패!");
                                    e.printStackTrace();
                                    Log.d(LOG_TAG, "login catch - 연결실패");
                                    Log.d(LOG_TAG, e.getMessage());
                                }
                            }
                        });
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        btn_main_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            URL endPoint = new URL(SERVER_ADDRESS + "/member/mLogout");
                            HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                            if(myConnection.getResponseCode() == 200) {
                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(myConnection.getInputStream()));

                                StringBuffer buffer = new StringBuffer();
                                String temp = null;

                                while((temp = in.readLine()) != null) {
                                    buffer.append(temp);
                                }
                                Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
                                HashMap<String, Integer> myMap = gson.fromJson(buffer.toString(), type);

                                int result = (int) myMap.get("result");

                                Log.d(LOG_TAG, "result = " + String.valueOf(result));

                                if(result == 1) {
                                    loginMember = null;
                                    show_Toast("로그아웃 되었습니다.");
                                    check_login();
                                } else {
                                    show_Toast("로그아웃 실패");
                                }
                            }
                        } catch (Exception e) {
                            show_Toast("연결실패!");
                            Log.d(LOG_TAG, "logout catch - 연결실패");
                            Log.d(LOG_TAG, e.getMessage());
                        }
                    }
                });
            }
        });
        btn_main_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void check_login() {
        Log.d(LOG_TAG, "체크로그인");
        if(loginMember == null) {
            Log.d(LOG_TAG, "체크로그인 널");
            set_text(str_output, "로그인 해주세요.");
            set_visible(btn_main_login, View.VISIBLE);
            set_visible(btn_main_regist, View.VISIBLE);
            set_visible(btn_main_logout, View.GONE);
            set_visible(btn_main_modify, View.GONE);
        } else {
            Log.d(LOG_TAG, "체크로그인 낫널");
            set_text(str_output, loginMember.getNickname() + " 님 환영합니다 :)");
            set_visible(btn_main_login, View.GONE);
            set_visible(btn_main_regist, View.GONE);
            set_visible(btn_main_logout, View.VISIBLE);
            set_visible(btn_main_modify, View.VISIBLE);
        }
    }

    private void set_text(final TextView target, final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                target.setText(str);
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

    private void set_visible(final Button btn, final int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.setVisibility(state);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //권한 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkedPermission();
        }else{
            init();
        }
    }

    public static final int REQ_PERM = 1;
    @TargetApi(Build.VERSION_CODES.M)
    public void checkedPermission(){
        if(checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){ //권한을 허용하지 않았을때

            String permarr[] = {Manifest.permission.INTERNET};
            requestPermissions(permarr, REQ_PERM);
        }else{ //권한을 허용했을때
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQ_PERM){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] ==PackageManager.PERMISSION_GRANTED
                    ){
                init();
            }else{
                Toast.makeText(getBaseContext(), "권한을 허용하지 않으시면 프로그램을 실행 시킬 수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void init(){
        setRefs();
        setEvents();

        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginMember");
        if(loginMember == null) {
            Log.d(LOG_TAG, "로그인 정보 업승ㅁ");
        } else {
            Log.d(LOG_TAG, "로그인 확인");
            Log.d(LOG_TAG, loginMember.getNickname());

        }

        check_login();
    }
}
