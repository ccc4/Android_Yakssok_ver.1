package com.example.tje.yakssok.board;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;

public class Board_Holder extends RecyclerView.ViewHolder {
    TextView str_board_idx;
    TextView str_board_title;
    TextView str_board_nickname;
    Button btn_board_view;

    public Board_Holder(View v, final String type, final Member loginMember) {
        super(v);

        str_board_idx = (TextView) v.findViewById(R.id.str_board_idx);
        str_board_title = (TextView) v.findViewById(R.id.str_board_title);
        str_board_nickname = (TextView) v.findViewById(R.id.str_board_nickname);
        btn_board_view = (Button) v.findViewById(R.id.btn_board_view);

        btn_board_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Board_ViewActivity.class);
                intent.putExtra("type", type);
                if(loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                intent.putExtra("b_idx", Integer.parseInt(str_board_idx.getText().toString()));
                v.getContext().startActivity(intent);
            }
        });
    }

}
