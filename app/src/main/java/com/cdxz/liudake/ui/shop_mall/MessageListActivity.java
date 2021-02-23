package com.cdxz.liudake.ui.shop_mall;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.MessageListAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsBean;
import com.cdxz.liudake.bean.MessageListBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageListActivity extends BaseActivity {

    @BindView(R.id.refreshMessage)
    SmartRefreshLayout refreshMessage;

    @BindView(R.id.recyclerMessage)
    RecyclerView recyclerMessage;

    private MessageListAdapter mAdapter;
    private List<MessageListBean> messageListBeans = new ArrayList<>();
    private int page = 1;

    public static void startMessageListActivity(Context context, int type) {
        Intent intent = new Intent(context, MessageListActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initViews() {
        setTitleText("消息列表");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMessage.setLayoutManager(layoutManager);
        recyclerMessage.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
    }

    @Override
    protected void initDatas() {
        mAdapter = new MessageListAdapter(messageListBeans);
        recyclerMessage.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        getMessageList();
    }

    @Override
    protected void initListener() {
        refreshMessage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getMessageList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getMessageList();
            }
        });
    }

    private void getMessageList() {
        HttpsUtil.getInstance(this).getMessageList(page, null, getIntent().getIntExtra("type", 1), object -> {
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                String list = jsonObject.getString("list");
                List<MessageListBean> beanList = ParseUtils.parseJsonArray(list, MessageListBean.class);
                if (CollectionUtils.isEmpty(beanList)) {
                    if (page == 1) {
                        refreshMessage.finishRefreshWithNoMoreData();
                    } else {
                        refreshMessage.finishLoadMoreWithNoMoreData();
                    }
                    messageListBeans.clear();
                } else {
                    if (page == 1) {
                        messageListBeans.clear();
                        if (beanList.size() < Constants.LIST_SIZE) {
                            refreshMessage.finishLoadMoreWithNoMoreData();
                        } else {
                            refreshMessage.finishRefresh();
                        }
                    } else {
                        refreshMessage.finishLoadMore();
                    }
                    messageListBeans.addAll(beanList);
                }
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}