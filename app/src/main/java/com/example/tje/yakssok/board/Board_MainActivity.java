package com.example.tje.yakssok.board;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;

public class Board_MainActivity extends AppCompatActivity {

    int loginMember_idx;

    Button btn_board_back;
    Button btn_board_notice;
    Button btn_board_share;
    Button btn_board_free;

    private void setRefs() {
        btn_board_back = (Button)findViewById(R.id.btn_board_back);
        btn_board_notice = (Button)findViewById(R.id.btn_board_notice);
        btn_board_share = (Button)findViewById(R.id.btn_board_share);
        btn_board_free = (Button)findViewById(R.id.btn_board_free);
    }


    private void setEvents() {
        btn_board_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                intent.putExtra("type", "notice");
                if (loginMember_idx != 0) {
                    intent.putExtra("loginMember_idx", loginMember_idx);
                }
                startActivity(intent);
            }
        });
        btn_board_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                intent.putExtra("type", "share");
                if (loginMember_idx != 0) {
                    intent.putExtra("loginMember_idx", loginMember_idx);
                }
                startActivity(intent);
            }
        });
        btn_board_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                intent.putExtra("type", "free");
                if (loginMember_idx != 0) {
                    intent.putExtra("loginMember_idx", loginMember_idx);
                }
                startActivity(intent);
            }
        });

        btn_board_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__main);

        Intent intent = getIntent();
        loginMember_idx = intent.getIntExtra("loginMember_idx", 0);

        setRefs();
        setEvents();
    }
}
