package com.cdxz.liudake.pop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CategoryListDto;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.List;

public class BottomSelect extends BottomPopupView {

    TextView cancel;
    RecyclerView recyclerView;
    private OnSelectListener onSelectListener;
    PopBottomSelectAdapter bottomSelectAdapter;
    List<CategoryListDto> data;
    public BottomSelect(@NonNull Context context, List<CategoryListDto> infoList , OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
        this.data = infoList;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_select;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        recyclerView =  findViewById(R.id.rv_bottom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bottomSelectAdapter = new PopBottomSelectAdapter(data);
        recyclerView.setAdapter(bottomSelectAdapter);
        bottomSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                onSelectListener.onSelect(position,data.get(position).getId(),data.get(position).getName());
                dismiss();

            }
        });
        cancel = findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public interface OnSelectListener {
        void onSelect(int position,String id,String name);
    }
}
