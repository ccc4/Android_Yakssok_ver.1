package com.example.tje.yakssok.pill;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Board;
import com.example.tje.yakssok.model.Member;
import com.example.tje.yakssok.model.P_mList_Helper;
import com.example.tje.yakssok.model.P_mOne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pill_ViewActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Yakssok";

    String SERVER_ADDRESS;

    int p_idx;
    int current_page_value;
    String choice;
    Member loginMember;

    P_mOne p_mOne;

    Button btn_p_view_go_back;

    ImageView img_p_view;
    TextView str_p_view_name;
    ImageView img_p_view_state;
    TextView str_p_view_percent;

    Button btn_p_view_ingredients;
    Button btn_p_view_company;
    Button btn_p_view_effects;
    Button btn_p_view_details;

    RecyclerView review_recyclerView;

    EditText str_p_review_write;
    Button btn_p_review_write;

    Button btn_p_view_go_up;
    Button btn_p_view_go_first;
    Button btn_p_view_prev;
    Button btn_p_view_next;

    private void setRefs() {
        SERVER_ADDRESS = getString(R.string.SERVER_ADDRESS_STR);

        btn_p_view_go_back = findViewById(R.id.btn_p_view_go_back);
        btn_p_view_ingredients = findViewById(R.id.btn_p_view_ingredients);
        btn_p_view_company = findViewById(R.id.btn_p_view_company);
        btn_p_view_effects = findViewById(R.id.btn_p_view_effects);
        btn_p_view_details = findViewById(R.id.btn_p_view_details);
        btn_p_review_write = findViewById(R.id.btn_p_review_write);
        btn_p_view_go_up = findViewById(R.id.btn_p_view_go_up);
        btn_p_view_go_first = findViewById(R.id.btn_p_view_go_first);
        btn_p_view_prev = findViewById(R.id.btn_p_view_prev);
        btn_p_view_next = findViewById(R.id.btn_p_view_next);
        img_p_view = findViewById(R.id.img_p_view);
        img_p_view_state = findViewById(R.id.img_p_view_state);
        str_p_view_name = findViewById(R.id.str_p_view_name);
        str_p_view_percent = findViewById(R.id.str_p_view_percent);
        str_p_review_write = findViewById(R.id.str_p_review_write);
        review_recyclerView = findViewById(R.id.review_recyclerView);
    }

    private void setView() {
        if(p_idx == 0) {
            Log.d(LOG_TAG, "pill view: 전달받은 p_idx 값이 0 입니다.");
            finish();
            return;
        }

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL endPoint = new URL(SERVER_ADDRESS + "/pill/mView/" + p_idx);
                    HttpURLConnection myConnection = (HttpURLConnection)endPoint.openConnection();

                    if(myConnection.getResponseCode() == 200) {
                        Log.d(LOG_TAG, "pill view 200번 성공으로 들어옴");
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
                        p_mOne = gson.fromJson(buffer.toString(), P_mOne.class);
                        if(p_mOne != null) {
                            Log.d(LOG_TAG, p_mOne.getP_idx() + " 의 pill one view 받아옴");

                            if(p_mOne.getImgPath() != null) {
                                Log.d(LOG_TAG, "이미지 path: " + p_mOne.getImgPath());
                                // 이미지 가져오기.. how?
                                img_p_view.setImageURI(Uri.parse(SERVER_ADDRESS + "/resources/img/pill/img/" + p_mOne.getImgPath()));
                            } else {
                                img_p_view.setImageResource(R.drawable.no_image);
                            }
                            str_p_view_name.setText(p_mOne.getName());
                            if(p_mOne.getRating() >= 50) {
                                img_p_view_state.setImageResource(R.drawable.good);
                            } else if(p_mOne.getTotal() != 0 && p_mOne.getRating() < 50 && p_mOne.getRating() != -1) {
                                img_p_view_state.setImageResource(R.drawable.bad);
                            } else if(p_mOne.getTotal() == 0 || p_mOne.getRating() == -1) {
                                img_p_view_state.setImageResource(R.drawable.none);
                            }
                            str_p_view_percent.setText(Integer.toString(p_mOne.getRating()) + " %");

                        } else{
                            Log.d(LOG_TAG, "pill one null");
                            finish();
                        }
                    } else {
                        Log.d(LOG_TAG, "서버연결 실패");
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
                    }
                } catch(Exception e) {
                    Log.d(LOG_TAG, "pill one 받아오기 - 연결실패");
                    Log.d(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(Object o) {
                // 해당 pill view 에 대한 review 작업할 곳

//                p_recyclerView = (RecyclerView)findViewById(R.id.p_recyclerView);
//                Pill_CustomAdapter adapter = new Pill_CustomAdapter(list, current_page_value, choice, loginMember);
//                p_recyclerView.setAdapter(adapter);
//                p_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                Log.d(LOG_TAG, "p_list_activity onPostExecute 실행");
            }
        }.execute();
    }

    private void setEnabled(final Button btn, final Boolean bool) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.setEnabled(bool);
            }
        });
    }

    private void setEvents() {
        btn_p_view_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pill_ListActivity.class);
                intent.putExtra("current_page_value", current_page_value);
                intent.putExtra("choice", choice);
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
        setContentView(R.layout.activity_pill__view);

        Log.d(LOG_TAG, "pill view 들어옴");
        Intent intent = getIntent();
        p_idx = intent.getIntExtra("p_idx", 0);
        current_page_value = intent.getIntExtra("current_page_value", 0);
        choice = intent.getStringExtra("choice");
        loginMember = (Member) intent.getSerializableExtra("loginMember");

        setRefs();
        setView();
        setEvents();
    }
}
