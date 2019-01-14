package com.example.tje.yakssok.pill;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tje.yakssok.R;
import com.example.tje.yakssok.model.Member;

public class Pill_Holder extends RecyclerView.ViewHolder {

    ConstraintLayout layout_p_item;
    ImageView img_p_item_state;
    TextView str_p_item_percent;
    TextView str_p_item_name;
    TextView str_p_item_effet_main;
    TextView str_p_item_idx;

    public Pill_Holder(View v, final int current_page_value, final String choice, final Member loginMember) {
        super(v);

        layout_p_item = (ConstraintLayout)v.findViewById(R.id.layout_p_item);
        img_p_item_state = (ImageView)v.findViewById(R.id.img_p_item_state);
        str_p_item_percent = (TextView)v.findViewById(R.id.str_p_item_percent);
        str_p_item_name = (TextView)v.findViewById(R.id.str_p_item_name);
        str_p_item_effet_main = (TextView)v.findViewById(R.id.str_p_item_effect_main);
        str_p_item_idx = (TextView)v.findViewById(R.id.str_p_item_idx);

        layout_p_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Yakssok", "pill view로 전해지는 idx 값: " + str_p_item_idx.getText().toString());
                if (loginMember != null) {
                    Log.d("Yakssok", "pill view로 전해지는 멤버아이디: " + loginMember.getId());
                }

                Intent intent = new Intent(v.getContext(), Pill_ViewActivity.class);
                intent.putExtra("p_idx", Integer.parseInt(str_p_item_idx.getText().toString()));
                intent.putExtra("current_page_value", current_page_value);
                intent.putExtra("choice", choice);
                if(loginMember != null) {
                    intent.putExtra("loginMember", loginMember);
                }
                v.getContext().startActivity(intent);
            }
        });
    }


}
