package com.example.tje.yakssok.pill;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;

public class Review_Holder extends RecyclerView.ViewHolder {

    ConstraintLayout layout_p_review_item;
    TextView str_p_review_contents;
    TextView str_p_review_write_date;
    TextView str_p_review_nickname;
    TextView str_p_review_idx;
    TextView str_p_review_m_idx;

    public Review_Holder(View v, Member loginMember) {
        super(v);

        layout_p_review_item = v.findViewById(R.id.layout_p_review_item);
        str_p_review_contents = v.findViewById(R.id.str_p_review_contents);
        str_p_review_write_date = v.findViewById(R.id.str_p_review_write_date);
        str_p_review_nickname = v.findViewById(R.id.str_p_review_nickname);
        str_p_review_idx = v.findViewById(R.id.str_p_review_idx);
        str_p_review_m_idx = v.findViewById(R.id.str_p_review_m_idx);
    }
}
