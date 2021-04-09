package com.cdxz.liudake.ui.main.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.MyServiceAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.Bus.UpdateUserInfoBean;
import com.cdxz.liudake.bean.InviteCodeBean;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.bean.ServiceBean;
import com.cdxz.liudake.bean.UserIndexBean;
import com.cdxz.liudake.ui.LookUpActivity;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.ui.my.InviteCodeActivity;
import com.cdxz.liudake.ui.my.SetActivity;
import com.cdxz.liudake.ui.my.UserInfoActivity;
import com.cdxz.liudake.ui.my.service.AddressListActivity;
import com.cdxz.liudake.ui.my.service.CollectActivity;
import com.cdxz.liudake.ui.my.service.FAQActivity;
import com.cdxz.liudake.ui.my.service.OpenStoreTypeActivity;
import com.cdxz.liudake.ui.my.service.RedmiBillActivity;
import com.cdxz.liudake.ui.my.service.ScoreBillActivity;
import com.cdxz.liudake.ui.my.service.ToPromoteActivity;
import com.cdxz.liudake.ui.my.service.WithdrawalActivity;
import com.cdxz.liudake.ui.order.OrderListActivity;
import com.cdxz.liudake.ui.store_manager.StoreHomeActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment2 extends BaseFragment {

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.ivAvatar)
    RoundedImageView ivAvatar;

    @BindView(R.id.tvNick)
    TextView tvNick;

    @BindView(R.id.tvPhone)
    TextView tvPhone;

    @BindView(R.id.tvDaiLing)
    TextView tvDaiLing;

    @BindView(R.id.tvKeYong)
    TextView tvKeYong;

    @BindView(R.id.tvRedmi)
    TextView tvRedmi;
    @BindView(R.id.redMi)
    TextView redMi;

    @BindView(R.id.tvLingquTag)
    TextView tvLingquTag;

    @BindView(R.id.tv_kouling)
    TextView tv_kouling;

    @BindView(R.id.bannerMy)
    Banner bannerMy;

    @BindView(R.id.recyclerService)
    RecyclerView recyclerService;

    private MyServiceAdapter mAdapter;

    public MyFragment2() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static MyFragment newInstance() {
//        MyFragment fragment = new MyFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_my_new;
    }

    @Override
    protected void initView() {
        recyclerService.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerService.addItemDecoration(new GridSpacingItemDecoration(5, SizeUtils.dp2px(10), false));
    }

    @Override
    protected void initData() {
        BusUtils.register(this);
        initMyService();
        userIndex();

    }



    @BusUtils.Bus(tag = BusTag.UPDATE_USER_INFO)
    public void onUpdateUserInfo(UpdateUserInfoBean bean) {
        userIndex();
        //
        tvNick.setText(bean.getName());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initListener() {
        refresh.setOnRefreshListener(refreshLayout ->
                HttpsUtil.getInstance(getContext()).userIndex(object -> {
                    UserIndexBean indexBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), UserIndexBean.class);
                    getActivity().findViewById(R.id.ivAvatar).setOnClickListener(v -> {
                        UserInfoActivity.startUserInfoActivity(getContext(), indexBean);
                    });
                    Glide.with(getContext())
                            .load(Constants.PICTURE_HTTPS_URL + indexBean.getHead())
                            .placeholder(R.mipmap.img_default)
                            .into(ivAvatar);
                    tvNick.setText(indexBean.getName());
                    tvPhone.setText("TEL："+indexBean.getPhone().substring(0, 3) + "****" + indexBean.getPhone().substring(7, indexBean.getPhone().length()));
                    tvDaiLing.setText(indexBean.getWait_integral());
                    tvKeYong.setText(indexBean.getIntegral());
                    tvRedmi.setText(indexBean.getBalance());
                    redMi.setText(indexBean.getBalance());
                    if (StringUtils.isEmpty(indexBean.getWait_integral()) ||
                            indexBean.getWait_integral().equals("null") ||
                            indexBean.getWait_integral().equals("") ||
                            Float.parseFloat(indexBean.getWait_integral()) <= 0) {
                        tvLingquTag.setVisibility(View.GONE);
                    } else {
                        tvLingquTag.setVisibility(View.VISIBLE);
                    }
                    getBannerList();
                    refresh.finishRefresh();
                })
        );
        tvLingquTag.setOnClickListener(v -> {
            OrderListActivity.startOrderListActivity(getContext(), 0);
//            HttpsUtil.getInstance(getContext()).lingQuScore(object -> {
//                userIndex();
//            });
        });
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivSet, R.id.tvInviteCode,
            R.id.scoreLayout1, R.id.scoreLayout2, R.id.redmiLayout,
            R.id.tvOrderAll, R.id.tvOrder1, R.id.tvOrder2, R.id.tvOrder3,R.id.tv_tiXian,R.id.tv_sendGoods,R.id.tvCopyInviteCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSet:
                SetActivity.startSetActivity(getContext());
                break;
            case R.id.tvInviteCode:
                InviteCodeActivity.startInviteCodeActivity(getContext(),headImg);
                break;
            case R.id.scoreLayout1:
                OrderListActivity.startOrderListActivity(getContext(), 0);
                break;
            case R.id.scoreLayout2:
                ScoreBillActivity.startScoreBillActivity(getContext());
                break;
            case R.id.redmiLayout:
                RedmiBillActivity.startRedmiBillActivity(getContext());
                break;
            case R.id.tvOrderAll:
                OrderListActivity.startOrderListActivity(getContext(), 0);
                break;
            case R.id.tvOrder1:
                OrderListActivity.startOrderListActivity(getContext(), 3);
                break;
            case R.id.tvOrder2:
                OrderListActivity.startOrderListActivity(getContext(), 1);
                break;
            case R.id.tvOrder3:
                OrderListActivity.startOrderListActivity(getContext(), 4);
                break;
            case R.id.tv_tiXian:
                WithdrawalActivity.startWithdrawalActivity(getContext(), tvRedmi.getText().toString());

                break;
            case R.id.tv_sendGoods:
                OrderListActivity.startOrderListActivity(getContext(), 2);

                break;
            case R.id.tvCopyInviteCode:

                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", tv_kouling.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showShort("复制成功");
                break;
        }
    }

    private void initMyService() {
        List<ServiceBean> serviceBeanList = new ArrayList<>();
        ServiceBean serviceBean;

        serviceBean = new ServiceBean(R.mipmap.shop_my_extension, "我的推广");
        serviceBeanList.add(serviceBean);
        serviceBean = new ServiceBean(R.mipmap.shop_my_withdrawal, "提现");
        serviceBeanList.add(serviceBean);
        serviceBean = new ServiceBean(R.mipmap.shop_my_settle, "开店入驻");
        serviceBeanList.add(serviceBean);

        serviceBean = new ServiceBean(R.mipmap.shop_my_address, "地址管理");
        serviceBeanList.add(serviceBean);
        serviceBean = new ServiceBean(R.mipmap.shop_my_collection, "我的收藏");
        serviceBeanList.add(serviceBean);
        serviceBean = new ServiceBean(R.mipmap.shop_my_customer, "联系客服");
        serviceBeanList.add(serviceBean);

        serviceBean = new ServiceBean(R.mipmap.shop_my_problem, "常见问题");
        serviceBeanList.add(serviceBean);

        serviceBean = new ServiceBean(R.mipmap.shop_my_operationcenter, "运营中心");
        serviceBeanList.add(serviceBean);

        serviceBean = new ServiceBean(R.mipmap.shop_my_shop, "我的店铺");
        serviceBeanList.add(serviceBean);

        serviceBean = new ServiceBean(R.mipmap.shop_my_collectmoney, "收米");
        serviceBeanList.add(serviceBean);

        mAdapter = new MyServiceAdapter(serviceBeanList);
        recyclerService.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (position) {
                    case 0:
                        ToPromoteActivity.startToPromoteActivity(getContext());
                        break;
                    case 1:
                        WithdrawalActivity.startWithdrawalActivity(getContext(), tvRedmi.getText().toString());
//                    WalletActivity.startWalletActivity(getContext());
                        break;
                    case 2:
//                        OpenStoreActivity.startOpenStoreActivity(getContext());
                        HttpsUtil.getInstance(getContext()).userInfo(object -> {
                            try {
                                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                                LogUtils.e("开店入驻点击 = " + jsonObject.toString());
                                if (jsonObject.has("shop_list")) {
                                    String shop_list = jsonObject.getString("shop_list");
                                    List<LoginBean.ShopBean> shopBeanList = ParseUtils.parseJsonArray(shop_list, LoginBean.ShopBean.class);
                                    if (CollectionUtils.isEmpty(shopBeanList)) {
                                        startActivity(new Intent(getActivity(), OpenStoreTypeActivity.class));
                                    } else {
                                        LoginBean.ShopBean bean = shopBeanList.get(0);
                                        if (bean.getStatus().equals("0")) {
                                            ToastUtils.showShort("您的入驻申请正在审核！");
                                        } else if (bean.getStatus().equals("1")) {
                                            ToastUtils.showShort("您已经入住成功啦！");
                                        }else if (bean.getStatus().equals("2")){
                                            startActivity(new Intent(getActivity(), OpenStoreTypeActivity.class));

                                        }
                                    }
                                } else {
                                    startActivity(new Intent(getActivity(), OpenStoreTypeActivity.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    case 3:
                        AddressListActivity.startAddressListActivity(getContext());
                        break;
                    case 4:
                        CollectActivity.startCollectActivity(getContext());
                        break;
                    case 5:
                        HttpsUtil.getInstance(getContext()).getSysPhone(object -> {
                            String json = GsonUtils.toJson(object);
                            try {
                                JSONArray jsonArray = new JSONArray(json);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String phone = jsonObject.getString("phone");
                                    //
                                    new XPopup.Builder(getContext())
                                            .asConfirm("拨打电话", "是否拨打" + phone, () -> {
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                Uri data = Uri.parse("tel:" + phone);
                                                intent.setData(data);
                                                getContext().startActivity(intent);
                                            }).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        break;

                    case 6:
                        FAQActivity.startFAQActivity(getContext());
                        break;

                    case 7:
                        //经销商查询

                        Intent it = new Intent(getContext(), LookUpActivity.class);
                        startActivity(it);

                        break;

                    case 8:
                        HttpsUtil.getInstance(getContext()).userInfo(object -> {
                            try {
                                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                                LogUtils.e("店铺点击 = " + jsonObject.toString());
                                if (jsonObject.has("shop_list")) {
                                    String shop_list = jsonObject.getString("shop_list");
                                    List<LoginBean.ShopBean> shopBeanList = ParseUtils.parseJsonArray(shop_list, LoginBean.ShopBean.class);
                                    if (CollectionUtils.isEmpty(shopBeanList)) {
                                        ToastUtils.showShort("请先入驻商铺");
                                    } else {
                                        LoginBean.ShopBean bean = shopBeanList.get(0);
                                        if (bean.getStatus().equals("0")) {
                                            ToastUtils.showShort("店铺审核中");
                                        } else if (bean.getStatus().equals("1")) {
                                            StoreHomeActivity.startStoreHomeActivity(getContext(), shopBeanList.get(0));
                                        }
                                    }
                                } else {
                                    ToastUtils.showShort("请先入驻商铺");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        break;

                    case 9:
                        WebActivity.startWebActivity(getContext(), 0, "http://liudake.cn/#/pages/index/index"+"?uid="+ UserInfoUtil.getUid());

                        break;
                }
            }
        });
    }



    /**
     * banner数据
     */
    private void getBannerList() {
        HttpsUtil.getInstance(getContext()).positionList(2, Constants.LNG, Constants.LAT, object -> {
            List<BannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), BannerBean.class);
            bannerMy.setAdapter(new HomeBannerAdapter(bannerBeanList), true)
                    .setIndicator(new CircleIndicator(getContext()));
        });
    }

    String headImg;
    @SuppressLint("SetTextI18n")
    private void userIndex() {

        HttpsUtil.getInstance(getContext()).userIndex(object -> {
            UserIndexBean indexBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), UserIndexBean.class);
            getActivity().findViewById(R.id.ivAvatar).setOnClickListener(v -> {
                UserInfoActivity.startUserInfoActivity(getContext(), indexBean);
            });
            Glide.with(getContext())
                    .load(Constants.PICTURE_HTTPS_URL + indexBean.getHead())
                    .placeholder(R.mipmap.img_default)
                    .into(ivAvatar);
            headImg = indexBean.getHead();
            tvNick.setText(indexBean.getName());
            tv_kouling.setText("邀请口令："+indexBean.getUid());
            tvPhone.setText("TEL："+indexBean.getPhone().substring(0, 3) + "****" + indexBean.getPhone().substring(7, indexBean.getPhone().length()));
            tvDaiLing.setText(indexBean.getWait_integral());
            tvKeYong.setText(indexBean.getIntegral());
            tvRedmi.setText(indexBean.getBalance());
            redMi.setText(indexBean.getBalance());
            //
            if (StringUtils.isEmpty(indexBean.getWait_integral()) ||
                    indexBean.getWait_integral().equals("null") ||
                    indexBean.getWait_integral().equals("") ||
                    Float.parseFloat(indexBean.getWait_integral()) <= 0) {
                tvLingquTag.setVisibility(View.GONE);
            } else {
                tvLingquTag.setVisibility(View.VISIBLE);
            }
            //
            getBannerList();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}