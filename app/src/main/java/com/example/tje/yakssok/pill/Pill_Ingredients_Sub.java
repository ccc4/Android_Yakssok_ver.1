package com.example.tje.yakssok.pill;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.tje.yakssok.R;

public class Pill_Ingredients_Sub extends LinearLayout {

    public Pill_Ingredients_Sub(Context context) {
        super(context);

        init(context);
    }

    public Pill_Ingredients_Sub(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_pill_ingredient,this,true);
    }
}
