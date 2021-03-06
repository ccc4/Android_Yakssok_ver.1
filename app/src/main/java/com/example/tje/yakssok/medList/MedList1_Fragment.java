package com.example.tje.yakssok.medList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tje.yakssok.R;

public class MedList1_Fragment extends Fragment implements View.OnClickListener  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medlist_list1, container, false);

        Button coldBtn = (Button)view.findViewById(R.id.coldBtn);
        Button antipyreticBtn = (Button)view.findViewById(R.id.antipyreticBtn);
        Button digestiveBtn = (Button)view.findViewById(R.id.digestiveBtn);
        Button painBtn = (Button)view.findViewById(R.id.painBtn);

        coldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Cold_Activity.class);
                startActivity(intent);
            }
        });

        antipyreticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Antipyretic_Activity.class);
                startActivity(intent);
            }
        });

        digestiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Digestive_Activity.class);
                startActivity(intent);
            }
        });

        painBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Pain_Activity.class);
                startActivity(intent);
            }
        });




        return view;
    }

    @Override
    public void onClick(View v) {

    }

}