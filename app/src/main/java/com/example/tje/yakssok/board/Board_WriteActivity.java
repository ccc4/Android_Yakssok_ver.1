package com.example.tje.yakssok.board;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Board;
import com.example.tje.yakssok.model.Member;
import com.google.gson.Gson;
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

public class Board_WriteActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Yakssok";
//    public static final String SERVER_ADDRESS = "http://192.168.10.132:8080/Yakssok";
    public static final String SERVER_ADDRESS = "http://192.168.219.146:8181/Yakssok";

    String type;
    Member loginMember;
    String choice;
    Board board;

    EditText str_b_write_title;
    EditText str_b_write_contents;
    Button btn_b_write_cancel;
    Button btn_b_write_ok;
    Button btn_b_modify_ok;


    private void setRefs() {
        str_b_write_title = (EditText)findViewById(R.id.str_b_write_title);
        str_b_write_contents = (EditText)findViewById(R.id.str_b_write_contents);
        btn_b_write_cancel = (Button)findViewById(R.id.btn_b_write_cancel);

        if(choice.equals("write")) {
            btn_b_write_ok = (Button)findViewById(R.id.btn_b_write_ok);
            btn_b_write_ok.setVisibility(View.VISIBLE);
        } else if(choice.equals("modify")) {
            btn_b_modify_ok = (Button)findViewById(R.id.btn_b_modify_ok);
            btn_b_modify_ok.setVisibility(View.VISIBLE);
        }

        if(board != null) {
            str_b_write_title.setText(board.getTitle());
            str_b_write_contents.setText(board.getContents());
        }


    }

    private void setEvents() {
        btn_b_write_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(btn_b_write_ok != null) {
            btn_b_write_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String title = str_b_write_title.getText().toString();
                                String contents = str_b_write_contents.getText().toString();

                                URL endPoint = new URL(SERVER_ADDRESS + "/mBoard/" + type + "/" + choice);
                                HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                                // POST 값 전달
                                myConnection.setRequestMethod("POST");

                                String requestParam = String.format("title=%s&contents=%s&m_idx=%d", title, contents, loginMember.getM_idx());
                                myConnection.setDoOutput(true);
                                myConnection.getOutputStream().write(requestParam.getBytes());
                                // POST 값 전달 끝

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
                                    Type typeToken = new TypeToken<HashMap<String, Integer>>(){}.getType();
                                    Gson gson = new Gson();
                                    HashMap<String, Integer> myMap = gson.fromJson(buffer.toString(), typeToken);

                                    int result = (int) myMap.get("result");
                                    Log.d(LOG_TAG, "result = " + String.valueOf(result));

                                    if(result == 1) {
                                        show_Toast("작성완료!");
                                        Log.d(LOG_TAG, "작성완료");

                                        Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                                        intent.putExtra("type", type);
                                        intent.putExtra("loginMember", loginMember);
                                        startActivity(intent);

                                    } else {
                                        show_Toast("작성실패!");
                                        Log.d(LOG_TAG, "작성실패");
                                    }
                                } else {    // 200이 아닐 때
                                    show_Toast("200번x");
                                    Log.d(LOG_TAG, "200번x");
                                }
                            } catch (Exception e) {
                                show_Toast("연결실패!");
                                Log.d(LOG_TAG, "login catch - 연결실패" + e.getMessage());
                            }
                        }
                    });
                }
            });
        }
        if(btn_b_modify_ok != null) {
            btn_b_modify_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String title = str_b_write_title.getText().toString();
                                String contents = str_b_write_contents.getText().toString();

                                URL endPoint = new URL(SERVER_ADDRESS + "/mBoard/" + type + "/" + choice);
                                HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                                // POST 값 전달
                                myConnection.setRequestMethod("POST");

                                String requestParam = String.format("title=%s&contents=%s&m_idx=%d&b_idx=%d", title, contents, loginMember.getM_idx(), board.getB_idx());
                                myConnection.setDoOutput(true);
                                myConnection.getOutputStream().write(requestParam.getBytes());
                                // POST 값 전달 끝

                                if (myConnection.getResponseCode() == 200) {
                                    Log.d(LOG_TAG, "mBoard_modify 200 success");
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
                                    Type typeToken = new TypeToken<HashMap<String, Integer>>(){}.getType();
                                    Gson gson = new Gson();
                                    HashMap<String, Integer> myMap = gson.fromJson(buffer.toString(), typeToken);

                                    int result = (int) myMap.get("result");
                                    Log.d(LOG_TAG, "result = " + String.valueOf(result));

                                    if(result == 1) {
                                        show_Toast("수정완료!");
                                        Log.d(LOG_TAG, "수정완료");

                                        Intent intent = new Intent(getApplicationContext(), Board_ViewActivity.class);
                                        intent.putExtra("type", type);
                                        intent.putExtra("loginMember", loginMember);
                                        intent.putExtra("b_idx", board.getB_idx());
                                        startActivity(intent);

                                    } else {
                                        show_Toast("작성실패!");
                                        Log.d(LOG_TAG, "작성실패");
                                    }
                                } else {    // 200이 아닐 때
                                    show_Toast("200번x");
                                    Log.d(LOG_TAG, "200번x");
                                }
                            } catch (Exception e) {
                                show_Toast("연결실패!");
                                Log.d(LOG_TAG, "login catch - 연결실패" + e.getMessage());
                            }
                        }
                    });
                }
            });
        }
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
        setContentView(R.layout.activity_board__write);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        loginMember = (Member)intent.getSerializableExtra("loginMember");
        choice = intent.getStringExtra("choice");
        board = (Board) intent.getSerializableExtra("board");
        Log.d(LOG_TAG, "write 들어옴, type : " + type + ", choice : " + choice);

        setRefs();
        setEvents();
    }
}
