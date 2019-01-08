package com.example.tje.yakssok.board;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.Server_Connect_Helper;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class Board_ViewActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Yakssok";
    String SERVER_ADDRESS;

    String type;
    Member loginMember;
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
        loginMember = (Member) intent.getSerializableExtra("loginMember");
        b_idx = intent.getIntExtra("b_idx", 0);

        if (loginMember != null) {
            Log.d(LOG_TAG, "view 에서의 loginMember_idx : " + loginMember.getM_idx());
        }
        Log.d(LOG_TAG, "view - b_idx : " + b_idx);

        setRefs();
        init();
        setEvents();
    }

    private void setRefs() {
        SERVER_ADDRESS = getString(R.string.SERVER_ADDRESS_STR);

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
        // 밑의 둘의 차이가 뭔지 테스트...
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                Server_Connect_Helper helper = new Server_Connect_Helper("board view");
                helper.connect(SERVER_ADDRESS + "/mBoard/" + type + "/view/" + b_idx);
                Type resultType = new TypeToken<Board>(){}.getType();
                board = (Board) helper.getResult(resultType);

                if(board != null) {
                    str_b_view_title.setText(board.getTitle());
                    str_b_view_nickname.setText(board.getNickname());
                    str_b_view_writeDate.setText(board.getWriteDate());
                    str_b_view_read_cnt.setText(Integer.toString(board.getRead_cnt()));
                    str_b_view_contents.setText(board.getContents());

                    if(loginMember == null || loginMember.getM_idx() != board.getM_idx()) {
                        set_visible(btn_b_view_modify, View.INVISIBLE);
                        set_visible(btn_b_view_delete, View.INVISIBLE);
                    } else if(loginMember.getM_idx() == board.getM_idx()){
                        set_visible(btn_b_view_modify, View.VISIBLE);
                        set_visible(btn_b_view_delete, View.VISIBLE);
                    }
                } else{
                    Log.d(LOG_TAG, "board null !!!");
                    finish();
                }


//                try {
//                    URL endPoint = new URL(SERVER_ADDRESS + "/mBoard/" + type + "/view/" + b_idx);
//                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();
//
//                    if(myConnection.getResponseCode() == 200) {
//                        Log.d(LOG_TAG, "view 200번 성공으로 들어옴");
//                        BufferedReader in =
//                                new BufferedReader(
//                                        new InputStreamReader(
//                                                myConnection.getInputStream()));
//                        StringBuffer buffer = new StringBuffer();
//                        String temp = null;
//                        while((temp = in.readLine()) != null) {
//                            buffer.append(temp);
//                        }
//                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//                        board = gson.fromJson(buffer.toString(), Board.class);
//                        if(board != null) {
//                            Log.d(LOG_TAG, board.getB_idx() + " 의 board 받아옴");
//
//                            str_b_view_title.setText(board.getTitle());
//                            str_b_view_nickname.setText(board.getNickname());
//                            str_b_view_writeDate.setText(board.getWriteDate());
//                            str_b_view_read_cnt.setText(Integer.toString(board.getRead_cnt()));
//                            str_b_view_contents.setText(board.getContents());
//
//                            if(loginMember == null || loginMember.getM_idx() != board.getM_idx()) {
//                                set_visible(btn_b_view_modify, View.INVISIBLE);
//                                set_visible(btn_b_view_delete, View.INVISIBLE);
//                            } else if(loginMember.getM_idx() == board.getM_idx()){
//                                set_visible(btn_b_view_modify, View.VISIBLE);
//                                set_visible(btn_b_view_delete, View.VISIBLE);
//                            }
//                        } else{
//                            Log.d(LOG_TAG, "board null !!!");
//                            finish();
//                        }
//                    } else {
//                        Log.d(LOG_TAG, "서버연결 실패");
//                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
//                    }
//                } catch(Exception e) {
//                    Log.d(LOG_TAG, "board 받아오기 - 연결실패");
//                    Log.d(LOG_TAG, e.getMessage());
//                    e.printStackTrace();
//                }
            }
        });

        /*
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

                            str_b_view_title.setText(board.getTitle());
                            str_b_view_nickname.setText(board.getNickname());
                            str_b_view_writeDate.setText(board.getWriteDate());
                            str_b_view_read_cnt.setText(Integer.toString(board.getRead_cnt()));
                            str_b_view_contents.setText(board.getContents());

                            if(loginMember == null || loginMember.getM_idx() != board.getM_idx()) {
                                set_visible(btn_b_view_modify, View.INVISIBLE);
                                set_visible(btn_b_view_delete, View.INVISIBLE);
                            } else if(loginMember.getM_idx() == board.getM_idx()){
                                set_visible(btn_b_view_modify, View.VISIBLE);
                                set_visible(btn_b_view_delete, View.VISIBLE);
                            }
                        } else{
                            Log.d(LOG_TAG, "board null !!!");
                            finish();
                        }
                    } else {
                        Log.d(LOG_TAG, "서버연결 실패");
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
                    }
                } catch(Exception e) {
                    Log.d(LOG_TAG, "board 받아오기 - 연결실패");
                    Log.d(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        */
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
                intent.putExtra("type", type);
                if(loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });
        btn_b_view_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_WriteActivity.class);
                intent.putExtra("type", type);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                intent.putExtra("choice", "modify");
                intent.putExtra("board", board);
                startActivity(intent);
            }
        });
        btn_b_view_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Board_ViewActivity.this);
                builder.setCancelable(true);
                builder.setTitle("게시글 삭제");
                builder.setMessage("정말 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL endPoint = new URL(SERVER_ADDRESS + "/mBoard/" + type + "/delete");
                                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                                    // POST 값 전달
                                    myConnection.setRequestMethod("POST");

                                    String requestParam = String.format("b_idx=%d", board.getB_idx());
                                    myConnection.setDoOutput(true);
                                    myConnection.getOutputStream().write(requestParam.getBytes());
                                    // POST 값 전달 끝

                                    if (myConnection.getResponseCode() == 200) {
                                        Log.d(LOG_TAG, "mBoard_delete 200 success");
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
                                            show_Toast("삭제완료!");
                                            Log.d(LOG_TAG, "삭제완료");

                                            Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                                            intent.putExtra("type", type);
                                            intent.putExtra("loginMember", loginMember);
                                            startActivity(intent);

                                        } else {
                                            show_Toast("삭제실패!");
                                            Log.d(LOG_TAG, "삭제실패");
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
                builder.setNegativeButton("취소", null);

                builder.show();
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
}
