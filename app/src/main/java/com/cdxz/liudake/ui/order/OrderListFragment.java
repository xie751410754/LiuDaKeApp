package com.cdxz.liudake.ui.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.order.OrderListAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.OrderListBean;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.PayResult;
import com.cdxz.liudake.view.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class OrderListFragment extends BaseFragment implements OrderListAdapter.OnStatusListener {
    @BindView(R.id.refreshOrder)
    SmartRefreshLayout refreshOrder;
    @BindView(R.id.recyclerOrder)
    RecyclerView recyclerOrder;

    private OrderListAdapter mAdapter;
    private int action;
    private int page = 1;
    private List<OrderListBean> orderListBeanList = new ArrayList<>();

    //微信支付
    private IWXAPI api;
    final Handler mHandler = new Handler(msg -> {
        int what = msg.what;
        if (what == 4) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (StringUtils.equals(resultStatus, "9000")) {
                ToastUtils.showShort("支付成功");
                page = 1;
                getOrderList();
            } else {
                ToastUtils.showShort("支付失败");
            }
        } else if (what == 5) {
            try {
                String data = (String) msg.obj;
                JSONObject jsonObject = new JSONObject(data);
                String appid = jsonObject.getString("appid");
                String noncestr = jsonObject.getString("noncestr");
                String packageX = jsonObject.getString("package");
                String partnerid = jsonObject.getString("partnerid");
                String prepayid = jsonObject.getString("prepayid");
                String timestamp = jsonObject.getString("timestamp");
                String sign = jsonObject.getString("sign");
                //
                PayReq req = new PayReq();
                req.appId = appid;
                req.partnerId = partnerid;
                req.prepayId = prepayid;
                req.nonceStr = noncestr;
                req.timeStamp = timestamp;
                req.packageValue = packageX;
                req.sign = sign;
                api.sendReq(req);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    });

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(int action) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt("action", action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void initView() {
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrder.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
    }

    @Override
    protected void initData() {
        BusUtils.register(this);
        api = WXAPIFactory.createWXAPI(getContext(), Constants.WX_APP_ID);
        if (getArguments() != null) {
            action = getArguments().getInt("action", 0);
            mAdapter = new OrderListAdapter(orderListBeanList, this);
            recyclerOrder.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.public_no_data);
            getOrderList();
        }
    }

    @Override
    protected void initListener() {
        refreshOrder.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getOrderList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getOrderList();
            }
        });
    }

    @BusUtils.Bus(tag = BusTag.WX_PAY_SUCCESS)
    public void onPaySuccess() {
        getOrderList();
    }

    private void getOrderList() {
        HttpsUtil.getInstance(getContext()).orderList(action, page, null, object -> {
            List<OrderListBean> list = ParseUtils.parseJsonArray(GsonUtils.toJson(object), OrderListBean.class);
            if (CollectionUtils.isEmpty(list)) {
                if (page == 1) {
                    refreshOrder.finishRefreshWithNoMoreData();
                } else {
                    refreshOrder.finishLoadMoreWithNoMoreData();
                }
                orderListBeanList.clear();
            } else {
                if (page == 1) {
                    orderListBeanList.clear();
                    if (list.size() < Constants.LIST_SIZE) {
                        refreshOrder.finishLoadMoreWithNoMoreData();
                    } else {
                        refreshOrder.finishRefresh();
                    }
                } else {
                    refreshOrder.finishLoadMore();
                }
                orderListBeanList.addAll(list);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onOrderPay(int payType, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String date = jsonObject.getString("data");
            if (payType == 4) {
                Runnable payRunnable = () -> {
                    PayTask alipay = new PayTask(getActivity());
                    Map<String, String> result = alipay.payV2(date, true);
                    Message msg = new Message();
                    msg.what = payType;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            } else if (payType == 5) {
                Message msg = new Message();
                msg.what = payType;
                msg.obj = date;
                mHandler.sendMessage(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOrderSureReceipt() {
        page = 1;
        getOrderList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}