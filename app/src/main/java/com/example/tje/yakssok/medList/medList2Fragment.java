package com.example.tje.yakssok.medList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tje.yakssok.R;

public class medList2Fragment extends Fragment implements View.OnClickListener  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_med_list2, container, false);

        Button cprBtn = (Button)view.findViewById(R.id.cprBtn);
        Button heimlichBtn = (Button)view.findViewById(R.id.heimlichBtn);
        Button loveBtn = (Button)view.findViewById(R.id.loveBtn);

        cprBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), cprActivity.class);
                startActivity(intent);
            }
        });

        heimlichBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), heimlichActivity.class);
                startActivity(intent);
            }
        });

        loveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), loveActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }

}
