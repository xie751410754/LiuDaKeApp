package com.cdxz.liudake.ui;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.AddressRegionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RegionPopu extends PopupWindow {

    Activity context;
    ArrayList<AddressRegionBean> datas;
    Callback callback;


    public RegionPopu(Activity activity, ArrayList<AddressRegionBean> datas,Callback callback){
        this.context = activity;
        this.datas = datas;
        this.callback = callback;
        if(this.datas.size()==0||!this.datas.get(0).getName().equals("不限")){
            this.datas.add(0,new AddressRegionBean("-1","不限","-1"));
        }
        View contentView = LayoutInflater.from(activity).inflate(R.layout.dialog_region, null);
        init(contentView);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setWidth(getWidth(context));
        this.setHeight(getHeight(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
    }

    public  void showPopwindow( View anchor) {
        int[] locations = new int[2];
        anchor.getLocationOnScreen(locations);
        int screenHeight = getHeight(context);
        int height = screenHeight - anchor.getHeight() - locations[1];
        this.setHeight(height);
        this.showAsDropDown(anchor);
    }


    private void init(View contentView){

        RecyclerView rv  = contentView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));

        BaseQuickAdapter<AddressRegionBean,BaseViewHolder> adapter = new BaseQuickAdapter<AddressRegionBean,BaseViewHolder>(R.layout.item_region,datas) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressRegionBean addressRegionBean) {
                baseViewHolder.setText(R.id.tv,addressRegionBean.getName());
            }
        };

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                callback.onChoose(position,datas.get(position));

            }
        });

        rv.setAdapter(adapter);

    }



    public interface Callback{

        void onChoose(int position,AddressRegionBean addressRegionBean);

    }


    public  int getWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        return screenWidth;
    }

    public  int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.heightPixels;
        return screenWidth;
    }


}
