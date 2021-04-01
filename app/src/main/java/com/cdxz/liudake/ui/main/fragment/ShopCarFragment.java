package com.cdxz.liudake.ui.main.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_car.ShopCarAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.ShopCarListBean;
import com.cdxz.liudake.bean.ShopCarSettlementBean;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.ui.order.OrderSubmitActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCarFragment extends BaseFragment implements ShopCarAdapter.OnSelectListener {

    @BindView(R.id.refreshShopCar)
    SmartRefreshLayout refreshShopCar;

    @BindView(R.id.recyclerShopCar)
    RecyclerView recyclerShopCar;

    @BindView(R.id.tvAll)
    DrawableTextView tvAll;

    @BindView(R.id.tvTotal)
    TextView tvTotal;

    @BindView(R.id.tvJifen)
    TextView tvJifen;

    @BindView(R.id.tvGoodsNum)
    TextView tvGoodsNum;
    private ShopCarAdapter mAdapter;
    private List<ShopCarListBean> shopCarListBeanList = new ArrayList<>();

    public ShopCarFragment() {
        // Required empty public constructor
    }

//    public static ShopCarFragment newInstance() {
//        ShopCarFragment fragment = new ShopCarFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_shop_car;
    }

    @Override
    protected void initView() {
        recyclerShopCar.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        LogUtils.e("购物车 initData");
        BusUtils.register(this);
        mAdapter = new ShopCarAdapter(shopCarListBeanList, this);
        recyclerShopCar.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        getCarList();
    }

    @Override
    protected void initListener() {
        refreshShopCar.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                shopCarListBeanList.clear();
                getCarList();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            String select = shopCarListBeanList.get(position).getSelected();
            if (select.equals("1")) {
                selectCar(shopCarListBeanList.get(position).getId(), 1, 0);
            } else {
                selectCar(shopCarListBeanList.get(position).getId(), 1, 1);
            }
//            if (isSelect) {
//                shopCarListBeanList.get(position).setSelect(false);
//                for (int i = 0; i < shopCarListBeanList.get(position).getList().size(); i++) {
//                    shopCarListBeanList.get(position).getList().get(i).setSelect(false);
//                }
//            } else {
//                shopCarListBeanList.get(position).setSelect(true);
//                for (int i = 0; i < shopCarListBeanList.get(position).getList().size(); i++) {
//                    shopCarListBeanList.get(position).getList().get(i).setSelect(true);
//                }
//            }
//            mAdapter.notifyDataSetChanged();
        });
    }

    @OnClick({R.id.tvAll, R.id.tvBuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAll:
                if (tvAll.getTag().equals("y")) {
                    selectCar(null, 3, 0);
                } else {
                    selectCar(null, 3, 1);
                }
                break;
            case R.id.tvBuy:
                HttpsUtil.getInstance(getContext()).settlement(2, 0, 0, object -> {
                    ShopCarSettlementBean settlementBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), ShopCarSettlementBean.class);
                    OrderSubmitActivity.startOrderSubmitActivity(getContext(), settlementBean);
                });
                break;
        }
    }

    @BusUtils.Bus(tag = BusTag.UPDATE_CAR)
    public void onUpdateCar() {
        getCarList();
    }

    @BusUtils.Bus(tag = BusTag.CAR_NUM_ADD)
    public void onCarNumAdd(ShopCarListBean.ListBean listBean) {
        int count = Integer.parseInt(listBean.getBuycount());
        count++;
        carNumChange(listBean.getGoods_id(), count);
    }

    @BusUtils.Bus(tag = BusTag.CAR_NUM_MINUS)
    public void onCarNumMinus(ShopCarListBean.ListBean listBean) {
        int count = Integer.parseInt(listBean.getBuycount());
        count--;
        carNumChange(listBean.getGoods_id(), count);
    }

    private void getCarList() {
        HttpsUtil.getInstance(getContext()).getCarList(object -> {
            if (refreshShopCar.getState() == RefreshState.Refreshing) {
                refreshShopCar.finishRefresh();
            }
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                String list = jsonObject.getString("list");
                List<ShopCarListBean> listBeans = ParseUtils.parseJsonArray(list, ShopCarListBean.class);
                shopCarListBeanList.clear();
                shopCarListBeanList.addAll(listBeans);
                mAdapter.notifyDataSetChanged();
                getTotalPrice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }

    @Override
    public void onChildClick(int position, ShopCarListBean listBean) {
        String select = listBean.getList().get(position).getSelected();
        if (select.equals("1")) {
            selectCar(listBean.getList().get(position).getGoods_id(), 0, 0);
        } else {
            selectCar(listBean.getList().get(position).getGoods_id(), 0, 1);
        }
    }

    @Override
    public void onDeleteClick(ShopCarListBean listBean ,int position) {
        HttpsUtil.getInstance(getContext()).deleteGoods(listBean.getList().get(position).getGoods_id(), object -> {
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                String list = jsonObject.getString("list");
                List<ShopCarListBean> listBeans = ParseUtils.parseJsonArray(list, ShopCarListBean.class);
                shopCarListBeanList.clear();
                shopCarListBeanList.addAll(listBeans);
                mAdapter.notifyDataSetChanged();
                getTotalPrice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void getTotalPrice() {
        float total = 0;
        int count = 0;
        int goodsNum = 0;//购物车商品数量
        float jifen = 0;
        for (int i = 0; i < shopCarListBeanList.size(); i++) {
            if (shopCarListBeanList.get(i).getSelected().equals("1")) {
                count++;
            } else {
                count--;
            }
            for (int j = 0; j < shopCarListBeanList.get(i).getList().size(); j++) {
                goodsNum += Integer.parseInt(shopCarListBeanList.get(i).getList().get(j).getBuycount());
                if (shopCarListBeanList.get(i).getList().get(j).getSelected().equals("1")) {
                    float price = Float.parseFloat(shopCarListBeanList.get(i).getList().get(j).getSaleprice())
                            * Integer.parseInt(shopCarListBeanList.get(i).getList().get(j).getBuycount());
                    float jf = Float.parseFloat(shopCarListBeanList.get(i).getList().get(j).getGold())
                            * Integer.parseInt(shopCarListBeanList.get(i).getList().get(j).getBuycount());
                    total += price;
                    jifen += jf;
                }
            }
        }
        if (count == shopCarListBeanList.size()) {
            tvAll.setLeftDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_pay_y));
            tvAll.setTag("y");
        } else {
            tvAll.setLeftDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_pay_n));
            tvAll.setTag("n");
        }
        tvTotal.setText(String.format("￥%.2f", total));
        if (jifen > 0) {
            tvJifen.setText(String.format("+积分%.2f", jifen));
            tvJifen.setVisibility(View.VISIBLE);
        } else {
            tvJifen.setVisibility(View.GONE);
        }
        BusUtils.post(BusTag.CAR_NUM, goodsNum);
    }

    /**
     * @param id     商品id或商家id
     * @param action 0-商品 1-商家 2-所有全选
     * @param type   0-未选中 1-选中
     */
    private void selectCar(String id, int action, int type) {
        HttpsUtil.getInstance(getContext()).selectCar(id, action, type, object -> {
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                String list = jsonObject.getString("list");
                List<ShopCarListBean> listBeans = ParseUtils.parseJsonArray(list, ShopCarListBean.class);
                shopCarListBeanList.clear();
                shopCarListBeanList.addAll(listBeans);
                mAdapter.notifyDataSetChanged();
                getTotalPrice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void carNumChange(String id, int count) {
        HttpsUtil.getInstance(getContext()).adjustCount(id, count, object -> {
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                String list = jsonObject.getString("list");
                List<ShopCarListBean> listBeans = ParseUtils.parseJsonArray(list, ShopCarListBean.class);
                shopCarListBeanList.clear();
                shopCarListBeanList.addAll(listBeans);
                mAdapter.notifyDataSetChanged();
                getTotalPrice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}