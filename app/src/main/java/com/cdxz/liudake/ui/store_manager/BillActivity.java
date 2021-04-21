package com.cdxz.liudake.ui.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.store_manager.StoreBillAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.bean.IndexAllInfoBean;
import com.cdxz.liudake.bean.StoreTodayInviteBean;
import com.cdxz.liudake.bean.StoreTodaySettlementBean;
import com.cdxz.liudake.bean.StoreTodaySettlementCashBean;
import com.cdxz.liudake.databinding.ActivityBillBinding;
import com.cdxz.liudake.databinding.ActivityBillNewBinding;
import com.cdxz.liudake.pop.PopBillView;
import com.cdxz.liudake.pop.PopCalendar;
import com.cdxz.liudake.pop.PopMap;
import com.cdxz.liudake.pop.PopSuggestion;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.life_circle.LifeCircleMapActivity;
import com.cdxz.liudake.util.ThirdPartyMapsGuide;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import io.reactivex.Observable;

public class BillActivity extends BaseTitleActivity<ActivityBillNewBinding> {

    String shopId;
    StoreBillAdapter adapter;
    private String startTime = null;
    private String endTime = null;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bill_new;
    }


    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }
    @Override
    public void initViewObservable() {
        super.initViewObservable();
//        initToolbar(binding.toolbar);

        shopId = getIntent().getStringExtra("shopId");

        binding.tvJiesuan.setTextSize(16);
        binding.tvJiesuan.setTextColor(Color.parseColor("#333333"));
        binding.tvTuiguang.setTextSize(14);
        binding.tvTuiguang.setTextColor(Color.parseColor("#999999"));
        binding.refresh.setEnableLoadMore(true);
        binding.refresh.setEnableRefresh(true);

        adapter = new StoreBillAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        getStoreTodaySettlement("", "");

        binding.tvAllBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvAllBill.setTextColor(ContextCompat.getColor(BillActivity.this, R.color.appColor));
                binding.tvAllBill.setRightDrawable(ContextCompat.getDrawable(BillActivity.this, R.mipmap.icon_pull_down_select));
                new XPopup.Builder(BillActivity.this)
                        .atView(view)
                        .asCustom(new PopBillView(BillActivity.this, new PopBillView.OnSelectListener() {
                            @Override
                            public void onSelect(String text) {
                                binding.tvAllBill.setText(text);
                                binding.tvAllBill.setTextColor(ContextCompat.getColor(BillActivity.this, R.color.color_222222));
                                binding.tvAllBill.setRightDrawable(ContextCompat.getDrawable(BillActivity.this, R.mipmap.icon_pull_down_nomal));
                                if (text.equals("红米")) {
                                    adapter.getData().clear();
                                    adapter.notifyDataSetChanged();
                                    getStoreTodaySettlement("", "");
                                    adapter.setType(0);
                                } else {
                                    adapter.getData().clear();
                                    adapter.notifyDataSetChanged();
                                    getStoreTodaySettlementCash("", "");
                                    adapter.setType(1);
                                }
                            }
                        })).show();

            }
        });
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvDate.setTextColor(ContextCompat.getColor(BillActivity.this, R.color.appColor));
                binding.tvDate.setRightDrawable(ContextCompat.getDrawable(BillActivity.this, R.mipmap.icon_pull_down_select));
//                new XPopup.Builder(BillActivity.this)
//                        .asCustom(new PopCalendar(BillActivity.this, position -> {
//
//                        })).show();
                TimePickerView pvTime = new TimePickerBuilder(BillActivity.this, (date, v1) -> {
                    binding.tvDate.setTextColor(ContextCompat.getColor(BillActivity.this, R.color.color_222222));
                    binding.tvDate.setRightDrawable(ContextCompat.getDrawable(BillActivity.this, R.mipmap.icon_pull_down_nomal));
                    startTime = TimeUtils.date2String(date, "yyyy-MM-dd");
                    binding.tvDate.setText(startTime);
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .setCancelColor(ContextCompat.getColor(BillActivity.this, R.color.appColor))
                        .setSubmitColor(ContextCompat.getColor(BillActivity.this, R.color.appColor))
                        .build();
                pvTime.show(true);

            }
        });
        binding.tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvEndDate.setTextColor(ContextCompat.getColor(BillActivity.this, R.color.appColor));
                binding.tvEndDate.setRightDrawable(ContextCompat.getDrawable(BillActivity.this, R.mipmap.icon_pull_down_select));
                TimePickerView pvTime = new TimePickerBuilder(BillActivity.this, (date, v1) -> {
                    binding.tvEndDate.setTextColor(ContextCompat.getColor(BillActivity.this, R.color.color_222222));
                    binding.tvEndDate.setRightDrawable(ContextCompat.getDrawable(BillActivity.this, R.mipmap.icon_pull_down_nomal));
                    endTime = TimeUtils.date2String(date, "yyyy-MM-dd");
                    binding.tvEndDate.setText(endTime);
                    if (binding.tvDate.getText().toString().equals("起始时间")) {
                        ToastUtils.showShort("未选择起始时间");
                    } else {
                        if (binding.tvAllBill.getText().toString().equals("现金")) {
                            getStoreTodaySettlementCash(binding.tvDate.getText().toString(), binding.tvEndDate.getText().toString());
                        } else {
                            getStoreTodaySettlement(binding.tvDate.getText().toString(), binding.tvEndDate.getText().toString());

                        }
                    }

                }).setType(new boolean[]{true, true, true, false, false, false})
                        .setCancelColor(ContextCompat.getColor(BillActivity.this, R.color.appColor))
                        .setSubmitColor(ContextCompat.getColor(BillActivity.this, R.color.appColor))
                        .build();
                pvTime.show(true);
            }
        });


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvTuiguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvTuiguang.setTextSize(16);
                binding.tvTuiguang.setTextColor(Color.parseColor("#333333"));
                binding.tvJiesuan.setTextSize(14);
                binding.tvJiesuan.setTextColor(Color.parseColor("#999999"));
                binding.refresh.setEnableLoadMore(true);
                binding.refresh.setEnableRefresh(true);

                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                getStoreTodayInvite(true);
            }
        });


        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getStoreTodayInvite(false);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getStoreTodayInvite(true);
                refreshLayout.finishRefresh();
            }
        });


    }

    public void getStoreTodaySettlement(String startTime, String endTime) {
        HttpsUtil.getInstance(this).storeTodaySettlement(shopId, startTime, endTime, new BaseObserver<BaseBean<List<StoreTodaySettlementBean>>>(this, true) {
            @Override
            public void onSuccess(BaseBean<List<StoreTodaySettlementBean>> listBaseBean) {
                if (listBaseBean.getData() != null && listBaseBean.getData().size() > 0) {
                    adapter.setList(listBaseBean.getData());
                }

            }
        });
    }

    public void getStoreTodaySettlementCash(String startTime, String endTime) {
        HttpsUtil.getInstance(this).storeTodaySettlementCash(shopId, startTime, endTime, new BaseObserver<BaseBean<List<StoreTodaySettlementCashBean>>>(this, true) {
            @Override
            public void onSuccess(BaseBean<List<StoreTodaySettlementCashBean>> listBaseBean) {
                if (listBaseBean.getData() != null && listBaseBean.getData().size() > 0) {
                    adapter.setList(listBaseBean.getData());
                }

            }
        });
    }

    int page = 1;

    public void getStoreTodayInvite(boolean isRefresh) {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        HttpsUtil.getInstance(this).storeTodayInvite(shopId, String.valueOf(page), new BaseObserver<BaseBean<List<StoreTodayInviteBean>>>(this, true) {
            @Override
            public void onSuccess(BaseBean<List<StoreTodayInviteBean>> listBaseBean) {

                if (listBaseBean.getData() != null && listBaseBean.getData().size() > 0) {
                    if (isRefresh) {
                        adapter.setList(listBaseBean.getData());
                    } else {
                        adapter.addData(listBaseBean.getData());
                    }
                }


            }
        });
    }
}
