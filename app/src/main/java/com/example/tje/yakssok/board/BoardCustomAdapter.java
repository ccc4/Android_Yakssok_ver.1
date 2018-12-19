package com.example.tje.yakssok.board;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Board;

import java.util.List;

public class BoardCustomAdapter extends RecyclerView.Adapter<Board_Holder> {
    Context context;
    List<Board> list;
    String type;
    int loginMember_idx;

    public BoardCustomAdapter(Context context, List<Board> list, String type, int loginMember_idx)  {
        this.context = context;
        this.list = list;
        this.type = type;
        this.loginMember_idx = loginMember_idx;
    }

    @Override
    public Board_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false);
        return new Board_Holder(view, type, loginMember_idx);
    }

    @Override
    public void onBindViewHolder(@NonNull Board_Holder holder, int position) {
        final Board contact = list.get(position);
        holder.str_board_idx.setText(Integer.toString(contact.getB_idx()));
        holder.str_board_title.setText(contact.getTitle());
        holder.str_board_nickname.setText(contact.getNickname());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
