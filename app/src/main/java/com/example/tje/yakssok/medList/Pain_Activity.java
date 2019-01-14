package com.example.tje.yakssok.medList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tje.yakssok.R;

public class Pain_Activity extends AppCompatActivity {
    int count = 0;

    Button btn_back;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;

    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_10;

    TextView text_1;
    TextView text_2;
    TextView text_3;
    TextView text_4;
    TextView text_5;

    TextView text_6;
    TextView text_7;
    TextView text_8;
    TextView text_9;
    TextView text_10;

    View pain_layout;
    FloatingActionButton pain_up_btn;

    public void setRefs(){

        pain_layout = (View)findViewById(R.id.pain_layout);
        pain_up_btn = (FloatingActionButton)findViewById(R.id.pain_up_btn);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_4 = (Button)findViewById(R.id.btn_4);
        btn_5 = (Button)findViewById(R.id.btn_5);

        btn_6 = (Button)findViewById(R.id.btn_6);
        btn_7 = (Button)findViewById(R.id.btn_7);
        btn_8 = (Button)findViewById(R.id.btn_8);
        btn_9 = (Button)findViewById(R.id.btn_9);
        btn_10 = (Button)findViewById(R.id.btn_10);

        text_1 = (TextView)findViewById(R.id.text_1);
        text_2 = (TextView)findViewById(R.id.text_2);
        text_3 = (TextView)findViewById(R.id.text_3);
        text_4 = (TextView)findViewById(R.id.text_4);
        text_5 = (TextView)findViewById(R.id.text_5);

        text_6 = (TextView)findViewById(R.id.text_6);
        text_7 = (TextView)findViewById(R.id.text_7);
        text_8 = (TextView)findViewById(R.id.text_8);
        text_9 = (TextView)findViewById(R.id.text_9);
        text_10 = (TextView)findViewById(R.id.text_10);
    }

    public void setEvents(){

        pain_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pain_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        pain_layout.scrollTo(0,0);
                    }
                });
            }
        });

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_1.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_1,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_1.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_1,  View.GONE);
                    count++;
                }
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_2.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_2,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_2.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_2,  View.GONE);
                    count++;
                }
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_3.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_3,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_3.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_3,  View.GONE);
                    count++;
                }
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_4.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_4,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_4.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_4,  View.GONE);
                    count++;
                }
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_5.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_5,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_5.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_5,  View.GONE);
                    count++;
                }
            }
        });

        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_6.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_6,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_6.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_6,  View.GONE);
                    count++;
                }
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_7.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_7,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_7.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_7,  View.GONE);
                    count++;
                }
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_8.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_8,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_8.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_8,  View.GONE);
                    count++;
                }
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_9.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_9,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_9.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_9,  View.GONE);
                    count++;
                }
            }
        });
        btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0){
                    btn_10.setTextColor(getResources().getColorStateList(R.color.medbutton_pressed));
                    set_text(text_10,  View.VISIBLE);
                    count++;
                }else if(count%2 == 1) {
                    btn_10.setTextColor(getResources().getColorStateList(R.color.medbutton_unpressed));
                    set_text(text_10,  View.GONE);
                    count++;
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void set_text(final TextView target, final int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                target.setVisibility(state);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist_pain);

        setRefs();
        setEvents();
    }
}
