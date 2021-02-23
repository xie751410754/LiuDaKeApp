package com.cdxz.liudake.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdxz.liudake.R;

public class ItemView extends LinearLayout {
    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.public_item_view, this, true);
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
//        int drawable = ta.getResourceId(R.styleable.ItemView_itemBackground, Color.WHITE);
//        Integer padding = ta.getInteger(R.styleable.ItemView_itemPadding, 15);
//        String leftText = ta.getString(R.styleable.ItemView_itemLeftText);
//        String rightText = ta.getString(R.styleable.ItemView_itemRightText);
//
//        setBackgroundResource(drawable);
//        ta.recycle();
    }
}
