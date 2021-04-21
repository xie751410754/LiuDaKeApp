package com.cdxz.liudake.pop;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.life_circle.PopSuggestionAdapter;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.Bus.PopSuggestionBean;
import com.cdxz.liudake.ui.life_circle.LifeCircleMapActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.impl.PartShadowPopupView;

import java.util.List;

public class PopBillView extends PartShadowPopupView {

    private PopBillView.OnSelectListener onSelectListener;

    public PopBillView(@NonNull Context context, PopBillView.OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }



    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_pull_dwon;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvRedmi =findViewById(R.id.tvRedmi);
        TextView tvCash =findViewById(R.id.tvCash);
        LinearLayout ll1 =findViewById(R.id.ll1);
        LinearLayout ll2 =findViewById(R.id.ll2);

        ll1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelect(tvRedmi.getText().toString());
                dismiss();
            }
        });
        ll2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelect(tvCash.getText().toString());
                dismiss();
            }
        });

    }

    @Override
    protected void onDismiss() {
        super.onDismiss();

    }


    @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight() * 0.6);
    }

    public interface OnSelectListener {
        void onSelect(String text);
    }
}
