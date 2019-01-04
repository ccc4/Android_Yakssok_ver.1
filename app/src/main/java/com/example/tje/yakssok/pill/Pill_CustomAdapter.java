package com.example.tje.yakssok.pill;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;
import com.example.tje.yakssok.model.P_mList;

import java.util.List;

public class Pill_CustomAdapter extends RecyclerView.Adapter<Pill_Holder> {

    List<P_mList> list;
    Member loginMember;

    public Pill_CustomAdapter(List<P_mList> list, Member loginMember) {
        this.list = list;
        this.loginMember = loginMember;
    }

    @NonNull
    @Override
    public Pill_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pill_list, parent, false);
        return new Pill_Holder(view, loginMember);
    }

    @Override
    public void onBindViewHolder(@NonNull Pill_Holder holder, int position) {
        P_mList contact = list.get(position);

        if(contact.getRating() >= 50) {
            holder.img_p_item_state.setImageResource(R.drawable.good);
        } else if(contact.getTotal() != 0 && contact.getRating() < 50 && contact.getRating() != -1) {
            holder.img_p_item_state.setImageResource(R.drawable.bad);
        } else if(contact.getTotal() == 0 || contact.getRating() == -1) {
            holder.img_p_item_state.setImageResource(R.drawable.none);
        }
        holder.str_p_item_percent.setText(Integer.toString(contact.getRating()) + " %");
        holder.str_p_item_name.setText(contact.getName());
        holder.str_p_item_effet_main.setText(contact.getEffect_main());
        holder.str_p_item_idx.setText(Integer.toString(contact.getP_idx()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
