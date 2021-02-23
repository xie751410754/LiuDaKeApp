package com.cdxz.liudake.ui.my.service;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.FAQAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.FAQBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;

import java.util.List;

import butterknife.BindView;

public class FAQActivity extends BaseActivity {

    @BindView(R.id.recyclerFAQ)
    RecyclerView recyclerFAQ;

    public static void startFAQActivity(Context context) {
        Intent intent = new Intent(context, FAQActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_faq;
    }

    @Override
    protected void initViews() {
        setTitleText("常见问题");
        recyclerFAQ.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        HttpsUtil.getInstance(this).getSystemProblem(object -> {
            List<FAQBean> faqBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), FAQBean.class);
            FAQAdapter mAdapter = new FAQAdapter(faqBeanList);
            recyclerFAQ.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((adapter, view, position) -> FAQDetailActivity.startFAQDetailActivity(FAQActivity.this, faqBeanList.get(position)));
        });
    }

    @Override
    protected void initListener() {

    }
}