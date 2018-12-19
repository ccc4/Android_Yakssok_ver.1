package com.example.tje.yakssok.board;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Board;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

public class Board_ViewActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Yakssok";
    public static final String SERVER_ADDRESS = "http://192.168.0.24:8080/Yakssok";

    String type;
    int loginMember_idx;
    int b_idx;
    Board board;

    TextView str_b_view_title;
    TextView str_b_view_nickname;
    TextView str_b_view_writeDate;
    TextView str_b_view_read_cnt;
    TextView str_b_view_contents;
    Button btn_b_view_back;
    Button btn_b_view_modify;
    Button btn_b_view_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__view);

        Log.d(LOG_TAG, "viewActivity 넘어옴");

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        loginMember_idx = intent.getIntExtra("loginMember_idx", 0);
        b_idx = intent.getIntExtra("b_idx", 0);

        Log.d(LOG_TAG, "view - loginMember_idx : " + loginMember_idx);
        Log.d(LOG_TAG, "view - b_idx : " + b_idx);

        setRefs();
        init();
        setEvents();
    }

    private void setRefs() {
        str_b_view_title = (TextView)findViewById(R.id.str_b_view_title);
        str_b_view_nickname = (TextView)findViewById(R.id.str_b_view_nickname);
        str_b_view_writeDate = (TextView)findViewById(R.id.str_b_view_writeDate);
        str_b_view_read_cnt = (TextView)findViewById(R.id.str_b_view_read_cnt);
        str_b_view_contents = (TextView)findViewById(R.id.str_b_view_contents);
        btn_b_view_back = (Button)findViewById(R.id.btn_b_view_back);
        btn_b_view_modify = (Button)findViewById(R.id.btn_b_view_modify);
        btn_b_view_delete = (Button)findViewById(R.id.btn_b_view_delete);
    }

    public void init() {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL endPoint = new URL(SERVER_ADDRESS + "/mBoard/" + type + "/view/" + b_idx);
                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                    if(myConnection.getResponseCode() == 200) {
                        Log.d(LOG_TAG, "view 200번 성공으로 들어옴");
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
                        board = gson.fromJson(buffer.toString(), Board.class);
                        if(board != null) {
                            Log.d(LOG_TAG, board.getB_idx() + " 의 board 받아옴");
                        } else{
                            Log.d(LOG_TAG, "board null !!!");
                        }
                    } else {
                        Log.d(LOG_TAG, "서버연결 실패");
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "board 받아오기 - 연결실패");
                    Log.d(LOG_TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                str_b_view_title.setText(board.getTitle());
                str_b_view_nickname.setText(board.getNickname());
                str_b_view_writeDate.setText(board.getWriteDate());
                str_b_view_read_cnt.setText(Integer.toString(board.getRead_cnt()));
                str_b_view_contents.setText(board.getTitle());

                Log.d(LOG_TAG, "view 에서 m_idx : " + loginMember_idx);

                if(loginMember_idx == 0 || loginMember_idx != board.getM_idx()) {
                    set_visible(btn_b_view_modify, View.INVISIBLE);
                    set_visible(btn_b_view_delete, View.INVISIBLE);
                } else if(loginMember_idx == board.getM_idx()){
                    set_visible(btn_b_view_modify, View.VISIBLE);
                    set_visible(btn_b_view_delete, View.VISIBLE);
                }
            }
        }.execute();
    }

    private void set_visible(final Button btn, final int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.setVisibility(state);
            }
        });
    }

    private void setEvents() {
        btn_b_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                finish();
            }
        });
    }
}
