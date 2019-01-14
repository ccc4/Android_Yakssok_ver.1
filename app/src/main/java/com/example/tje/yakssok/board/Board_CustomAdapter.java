package com.example.tje.yakssok.board;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Board;
import com.example.tje.yakssok.model.Member;

import java.util.List;

public class Board_CustomAdapter extends RecyclerView.Adapter<Board_Holder> {
    List<Board> list;
    String type;
    Member loginMember;

    public Board_CustomAdapter(List<Board> list, String type, Member loginMember)  {
        this.list = list;
        this.type = type;
        this.loginMember = loginMember;
    }

    @Override
    public Board_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_list, parent, false);
        return new Board_Holder(view, type, loginMember);
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
