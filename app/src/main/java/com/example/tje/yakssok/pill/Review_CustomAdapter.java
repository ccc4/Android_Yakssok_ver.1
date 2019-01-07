package com.example.tje.yakssok.pill;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;
import com.example.tje.yakssok.model.P_review;

import java.text.SimpleDateFormat;
import java.util.List;

public class Review_CustomAdapter extends RecyclerView.Adapter<Review_Holder> {

    List<P_review> list;
    Member loginMember;

    public Review_CustomAdapter(List<P_review> list, Member loginMember) {
        this.list = list;
        this.loginMember = loginMember;
    }

    @NonNull
    @Override
    public Review_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_list, parent, false);
        return new Review_Holder(view, loginMember);
    }

    @Override
    public void onBindViewHolder(@NonNull Review_Holder holder, int position) {
        P_review contact = list.get(position);

        holder.str_p_review_contents.setText(contact.getContents());
        holder.str_p_review_nickname.setText(contact.getNickname());
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        holder.str_p_review_write_date.setText(sdf.format(contact.getWriteDate()));
        holder.str_p_review_idx.setText(Integer.toString(contact.getP_review_idx()));
        holder.str_p_review_m_idx.setText(Integer.toString(contact.getM_idx()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
