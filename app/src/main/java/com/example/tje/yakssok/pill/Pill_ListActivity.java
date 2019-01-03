package com.example.tje.yakssok.pill;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;

import java.net.MalformedURLException;
import java.net.URL;

public class Pill_ListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Takssok";
    private static final String SERVER_ADDRESS = "http://192.168.0.24:8080/Yakssok";

    Member loginMember;

    Button btn_p_list_go_main;

    private void setRefs() {
        btn_p_list_go_main = findViewById(R.id.btn_p_list_go_main);
    }

    private void setList() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL endPoint = new URL(SERVER_ADDRESS + "/pill/mList");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "p_list 받아오기 - 연결실패");
                    Log.d(LOG_TAG, "j");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

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
