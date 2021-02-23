package com.cdxz.liudake.adapter.life_circle;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.blankj.utilcode.util.StringUtils;
import com.cdxz.liudake.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PopSuggestionAdapter extends BaseQuickAdapter<SuggestionResult.SuggestionInfo, BaseViewHolder> {
    public PopSuggestionAdapter(@Nullable List<SuggestionResult.SuggestionInfo> data) {
        super(R.layout.item_pop_suggestion, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SuggestionResult.SuggestionInfo suggestionInfo) {
        baseViewHolder.setText(R.id.title, suggestionInfo.getKey())
                .setText(R.id.content, suggestionInfo.getAddress());
    }
}
