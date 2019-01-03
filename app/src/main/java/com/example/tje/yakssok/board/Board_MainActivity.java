package com.example.tje.yakssok.board;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tje.yakssok.MainActivity;
import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;

public class Board_MainActivity extends AppCompatActivity {

    Member loginMember;

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
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });
        btn_board_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                intent.putExtra("type", "share");
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });
        btn_board_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board_SelectedActivity.class);
                intent.putExtra("type", "free");
                if (loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                startActivity(intent);
            }
        });

        btn_board_back.setOnClickListener(new View.OnClickListener() {
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
        setContentView(R.layout.activity_board__main);

        Intent intent = getIntent();
        loginMember = (Member) intent.getSerializableExtra("loginMember");

        setRefs();
        setEvents();
    }
}
