package com.example.tje.yakssok.board;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Board;
import com.example.tje.yakssok.model.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Board_SelectedActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Yakssok";
//    public static final String SERVER_ADDRESS = "http://192.168.10.132:8080/Yakssok";
public static final String SERVER_ADDRESS = "http://192.168.0.24:8080/Yakssok";

    String type;
    Member loginMember;
    List<Board> list;
    RecyclerView recyclerView;

    Button btn_b_selected_write;
    Button btn_b_selected_back;
    Button btn_b_selected_go_main;

    private void setRefs() {
        btn_b_selected_write = (Button)findViewById(R.id.btn_b_selected_write);
        btn_b_selected_back = (Button)findViewById(R.id.btn_b_selected_back);
        btn_b_selected_go_main = (Button)findViewById(R.id.btn_b_selected_go_main);
    }

    private void setEvents() {
        if(type.length() != 0 && loginMember != null) {
            set_visible(btn_b_selected_write, View.VISIBLE);
            if (type.equals("notice") && loginMember.getType() != 2) {
                set_visible(btn_b_selected_write, View.GONE);
            }
        }
        btn_b_selected_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_MainActivity.class);
                if(loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });
        btn_b_selected_go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });
        btn_b_selected_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_WriteActivity.class);
                intent.putExtra("type", type);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                intent.putExtra("choice", "write");
                startActivity(intent);
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
        setContentView(R.layout.activity_board__selected);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        loginMember = (Member) intent.getSerializableExtra("loginMember");
        Log.d(LOG_TAG, "type : " + type);
        if (loginMember != null) {
            Log.d(LOG_TAG, "seleted 에서의 loginMember_idx : " + loginMember.getM_idx());
        }

        setRefs();
        init();
        setEvents();
    }

    public void init() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL endPoint = new URL(SERVER_ADDRESS + "/mBoard/" + type);
                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                    if(myConnection.getResponseCode() == 200) {
                        Log.d(LOG_TAG, "board list 200번 성공으로 들어옴");
                        BufferedReader in =
                                new BufferedReader(
                                        new InputStreamReader(
                                                myConnection.getInputStream()));
                        StringBuffer buffer = new StringBuffer();
                        String temp = null;
                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        Type type = new TypeToken<List<Board>>(){}.getType();
                        list = gson.fromJson(buffer.toString(), type);
                        Log.d(LOG_TAG, "list size : " + list.size());
                    } else {
                        Log.d(LOG_TAG, "서버연결 실패");
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "list 받아오기 - 연결실패");
                    Log.d(LOG_TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                //1. 리사이클러뷰 화면 연결
                recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
                //2. 아답터 생성
                BoardCustomAdapter adapter = new BoardCustomAdapter(getApplicationContext(), list, type, loginMember);
                //3.리사이클러뷰와 아답터 연결
                recyclerView.setAdapter(adapter);
                //4.리사이클러뷰매니저
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Log.d(LOG_TAG, "list size : " + list.size());
            }
        }.execute();
    }
}
