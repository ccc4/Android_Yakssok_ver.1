package com.example.tje.yakssok.pill;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;
import com.example.tje.yakssok.model.P_mList;
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

    private static final String LOG_TAG = "Takssok";
//    private static final String SERVER_ADDRESS = "http://192.168.0.24:8080/Yakssok";
    private static final String SERVER_ADDRESS = "http://192.168.10.132:8080/Yakssok";

    Member loginMember;
    List<P_mList> list;
    RecyclerView p_recyclerView;

    Button btn_p_list_go_main;
    Spinner spn_p_list_choice;

    private void setRefs() {
        btn_p_list_go_main = (Button) findViewById(R.id.btn_p_list_go_main);
        spn_p_list_choice = (Spinner) findViewById(R.id.spn_p_list_choice);
    }

    private void setList() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL endPoint = new URL(SERVER_ADDRESS + "/pill/mList");
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
                        Type type = new TypeToken<List<P_mList>>(){}.getType();
                        list = gson.fromJson(buffer.toString(), type);
                        Log.d(LOG_TAG, "p_list size: " + list.size());
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
                Log.d(LOG_TAG, "p_list_activity onPostExecute 성공적 실행 완료");
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "선택: " + parent.getItemAtPosition(0), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "선택: " + parent.getItemAtPosition(1), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        setRefs();
        setList();
        setEvent();
    }
}
