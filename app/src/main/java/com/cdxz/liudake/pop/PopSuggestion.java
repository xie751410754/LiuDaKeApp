package com.cdxz.liudake.pop;

import android.content.Context;
import android.view.View;

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

public class PopSuggestion extends PartShadowPopupView {

    private RecyclerView recyclerPop;

    private List<SuggestionResult.SuggestionInfo> infoList;
    private PopSuggestionAdapter mAdapter;

    public PopSuggestion(@NonNull Context context, List<SuggestionResult.SuggestionInfo> infoList) {
        super(context);
        this.infoList = infoList;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_suggestion;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        LogUtils.e("数据 = " + GsonUtils.toJson(infoList));
        recyclerPop = findViewById(R.id.recyclerPop);
        recyclerPop.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < infoList.size(); i++) {
            if (StringUtils.isEmpty(infoList.get(i).getAddress())) {
                infoList.remove(i);
            }
        }
        mAdapter = new PopSuggestionAdapter(infoList);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SuggestionResult.SuggestionInfo info = infoList.get(position);
                BusUtils.post(BusTag.POP_SUGGESTION, new PopSuggestionBean(
                        info.getKey(), info.getAddress(), info.getPt().longitude, info.getPt().latitude
                ));
                dismiss();
                ActivityUtils.finishActivity(LifeCircleMapActivity.class);
            }
        });
        recyclerPop.setAdapter(mAdapter);
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        mAdapter = null;
    }

    @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight() * 0.6);
    }
}
