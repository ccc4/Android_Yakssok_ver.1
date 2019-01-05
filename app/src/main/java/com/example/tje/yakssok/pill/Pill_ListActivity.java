package com.example.tje.yakssok.pill;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;
import com.example.tje.yakssok.model.P_mList;
import com.example.tje.yakssok.model.P_mList_Helper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Pill_ListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Yakssok";
    public static final String SERVER_ADDRESS = "http://172.30.56.206:8080/Yakssok";
//    private static final String SERVER_ADDRESS = "http://192.168.10.132:8080/Yakssok";
    //    private static final String SERVER_ADDRESS = "http://192.168.0.24:8080/Yakssok";

    int current_page_value = 0;
    String choice = null;

    Member loginMember;
    P_mList_Helper list_helper;
    List<P_mList> list;

    RecyclerView p_recyclerView;
    Button btn_p_list_go_main;
    TextView str_p_list_page;
    Spinner spn_p_list_choice;
    Button btn_p_list_go_first;
    Button btn_p_list_prev;
    Button btn_p_list_next;

    private void setRefs() {
        btn_p_list_go_main = (Button) findViewById(R.id.btn_p_list_go_main);
        str_p_list_page = (TextView) findViewById(R.id.str_p_list_page);
        spn_p_list_choice = (Spinner) findViewById(R.id.spn_p_list_choice);
        btn_p_list_go_first = (Button) findViewById(R.id.btn_p_list_go_first);
        btn_p_list_prev = (Button) findViewById(R.id.btn_p_list_prev);
        btn_p_list_next = (Button) findViewById(R.id.btn_p_list_next);

        if(current_page_value == 0) {
            btn_p_list_prev.setEnabled(false);
            btn_p_list_go_first.setEnabled(false);
        }

        if(choice == null) {
            choice = "평점순";
        }

        if(choice.equals("이름순")) {
            spn_p_list_choice.setSelection(1);
        }
    }

    private void setList() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    Log.d(LOG_TAG, "pill list AsyncTask 진입, current_page_value => " + current_page_value + ", choice => " + choice);
                    int current_page = current_page_value * 8;
                    URL endPoint = new URL(SERVER_ADDRESS + "/pill/mList" + "/" + choice + "/" + current_page);
                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                    if (myConnection.getResponseCode() == 200) {
                        Log.d(LOG_TAG, "p_ilst 200 success");
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
                        Type type = new TypeToken<P_mList_Helper>(){}.getType();
                        list_helper = gson.fromJson(buffer.toString(), type);
                        list = list_helper.getList();
                        Log.d(LOG_TAG, "p_list size: " + list.size());

                        if (list.size() > 0) {
                            Log.d(LOG_TAG, "p_list 받아오기 성공");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    str_p_list_page.setText(current_page_value + 1 + " 번째 페이지");
                                    if(list_helper.getAll_count() <= (current_page_value + 1) * 8) {
                                        btn_p_list_next.setEnabled(false);
                                    }
                                }
                            });
                        }
                    } else {
                        Log.d(LOG_TAG, "서버연결 실패");
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "p_list 받아오기 - 연결실패");
                    Log.d(LOG_TAG, e.getMessage());
                }
                return null;
            }


            @Override
            protected void onPostExecute(Object o) {
                p_recyclerView = (RecyclerView)findViewById(R.id.p_recyclerView);
                Pill_CustomAdapter adapter = new Pill_CustomAdapter(list, loginMember);
                p_recyclerView.setAdapter(adapter);
                p_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Log.d(LOG_TAG, "p_list_activity onPostExecute 실행");
            }
        }.execute();
    }

    private void setEvent() {
        btn_p_list_go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if(loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

        spn_p_list_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if(choice.equals(adapterView.getSelectedItem())) {
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), Pill_ListActivity.class);
                intent.putExtra("current_page_value", 0);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }

                switch (position) {
                    case 0: // 평점순
                        Log.d(LOG_TAG, "pill list 평점순 선택됨");
                        intent.putExtra("choice", "평점순");
                        startActivity(intent);
                        break;
                    case 1: // 이름순
                        Log.d(LOG_TAG, "pill list 이름순 선택됨");
                        intent.putExtra("choice", "이름순");
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_p_list_go_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "pill list 처음으로 가기 버튼 눌림");

                Intent intent = new Intent(getApplicationContext(), Pill_ListActivity.class);
                intent.putExtra("current_page_value", 0);
                intent.putExtra("choice", choice);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

        btn_p_list_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_page_value == 0) {
                    return;
                }
                Log.d(LOG_TAG, "prev 버튼 눌림");

                current_page_value -= 1;
                Intent intent = new Intent(getApplicationContext(), Pill_ListActivity.class);
                intent.putExtra("current_page_value", current_page_value);
                intent.putExtra("choice", choice);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

        btn_p_list_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "next 버튼 눌림");
                current_page_value += 1;
                Intent intent = new Intent(getApplicationContext(), Pill_ListActivity.class);
                intent.putExtra("current_page_value", current_page_value);
                intent.putExtra("choice", choice);
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill__list);

        Log.d(LOG_TAG, "p_list 진입");
        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginMember");
        current_page_value = intent.getIntExtra("current_page_value", 0);
        choice = intent.getStringExtra("choice");

        setRefs();
        setList();
        setEvent();
    }
}
