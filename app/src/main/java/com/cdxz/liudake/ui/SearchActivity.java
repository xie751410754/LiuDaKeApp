package com.cdxz.liudake.ui;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.SearchHistoryAdapter;
import com.cdxz.liudake.adapter.shop_mall.SearchHotAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsBean;
import com.cdxz.liudake.bean.HotSearchBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.tvHotSearch)
    TextView tvHotSearch;

    @BindView(R.id.tvHistorySearch)
    TextView tvHistorySearch;

    @BindView(R.id.tvClear)
    TextView tvClear;

    @BindView(R.id.recyclerHot)
    RecyclerView recyclerHot;

    @BindView(R.id.recyclerHistory)
    RecyclerView recyclerHistory;

    private SearchHotAdapter hotAdapter;
    private SearchHistoryAdapter historyAdapter;
    //
    private String keyword;

    public static void startSearchActivity(Context context, String keyword) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("keyword", keyword);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {
        recyclerHot.setLayoutManager(new FlexboxLayoutManager(this));
        recyclerHistory.setLayoutManager(new FlexboxLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        hotSearch();
        historySearch();
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tvSearch).setOnClickListener(v -> {
            keyword = etSearch.getText().toString();
            toSearchResultActivity(keyword);
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyword = etSearch.getText().toString();
                toSearchResultActivity(keyword);
            }
            return false;
        });
        findViewById(R.id.tvClear).setOnClickListener(v -> {
            clearSearch();
        });
    }

    private void hotSearch() {
        HttpsUtil.getInstance(this).hotSearch(2, object -> {
            List<HotSearchBean> hotSearchBeans = ParseUtils.parseJsonArray(GsonUtils.toJson(object), HotSearchBean.class);
            hotAdapter = new SearchHotAdapter(hotSearchBeans);
            hotAdapter.setOnItemClickListener((adapter, view, position) -> {
                toSearchResultActivity(hotSearchBeans.get(position).getKeyword());
            });
            recyclerHot.setAdapter(hotAdapter);
        });
    }

    private void historySearch() {
        HttpsUtil.getInstance(this).historySearch(object -> {
            List<HotSearchBean> historySearchBeans = ParseUtils.parseJsonArray(GsonUtils.toJson(object), HotSearchBean.class);
            historyAdapter = new SearchHistoryAdapter(historySearchBeans);
            historyAdapter.setOnItemClickListener((adapter, view, position) -> {
                toSearchResultActivity(historySearchBeans.get(position).getKeyword());
            });
            recyclerHistory.setAdapter(historyAdapter);
        });
    }

    private void clearSearch() {
        HttpsUtil.getInstance(this).clearSearch(object -> {
            if (historyAdapter != null) {
                historyAdapter.clearList();
            }
        });
    }

    private void toSearchResultActivity(String s) {
        if (StringUtils.isEmpty(s)) {
            ToastUtils.showShort("请输入搜索内容");
            return;
        }
        SearchResultActivity.startSearchResultActivity(this, s);
    }
}