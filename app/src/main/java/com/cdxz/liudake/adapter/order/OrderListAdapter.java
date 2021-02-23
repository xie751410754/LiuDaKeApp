package com.cdxz.liudake.adapter.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.OrderListBean;
import com.cdxz.liudake.ui.order.ApplyAfterSalesActivity;
import com.cdxz.liudake.ui.order.OrderDetailActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OrderListAdapter extends BaseQuickAdapter<OrderListBean, BaseViewHolder> {
    private OnStatusListener onStatusListener;

    public OrderListAdapter(List<OrderListBean> data, OnStatusListener onStatusListener) {
        super(R.layout.item_order, data);
        this.onStatusListener = onStatusListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderListBean listBean) {
        baseViewHolder.itemView.setOnClickListener(v -> {
            OrderDetailActivity.startOrderDetailActivity(getContext(), listBean.getId());
        });
        if (listBean.getShoplogo().startsWith("http://") || listBean.getShoplogo().startsWith("https://")) {
            Glide.with(getContext())
                    .load(listBean.getShoplogo())
                    .placeholder(R.mipmap.logo)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivStorePic));
        } else {
            Glide.with(getContext())
                    .load(Constants.PICTURE_HTTPS_URL + listBean.getShoplogo())
                    .placeholder(R.mipmap.logo)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivStorePic));
        }
        baseViewHolder.setText(R.id.tvStoreName, listBean.getShopname())
                .setText(R.id.tvOrderGoodsNum, String.format("共 %s 件商品", listBean.getList().size()))
                .setText(R.id.tvOrderPrice, String.format("合计：¥%s（含运费 ¥%s)", listBean.getTotalprice(), listBean.getFareprice()));
        LinearLayout goodsLayout = baseViewHolder.getView(R.id.goodsLayout);
        goodsLayout.setOnClickListener(v -> {
            OrderDetailActivity.startOrderDetailActivity(getContext(), listBean.getId());
        });
        goodsLayout.removeAllViews();
        for (int i = 0; i < listBean.getList().size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_order_child, null);
            Glide.with(getContext())
                    .load(listBean.getList().get(i).getLogo())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) view.findViewById(R.id.ivGoodsPic));
            TextView tvGoodsName = view.findViewById(R.id.tvGoodsName);
            tvGoodsName.setText(listBean.getList().get(i).getName());

            TextView tvGoodsPrice = view.findViewById(R.id.tvGoodsPrice);
            tvGoodsPrice.setText("￥" + listBean.getList().get(i).getGoods_price());

            TextView tvGoodsNum = view.findViewById(R.id.tvGoodsNum);
            tvGoodsNum.setText("x" + listBean.getList().get(i).getBuycount());

            TextView tvShouhou = view.findViewById(R.id.tvShouhou);
            switch (listBean.getList().get(i).getStatus()) {//0-正常 1-售后中 2-售后完成 3-已拒绝
                case "0":
                    tvShouhou.setVisibility(View.VISIBLE);
                    int finalI = i;
                    tvShouhou.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", listBean.getId());
                        bundle.putSerializable("goods", listBean.getList().get(finalI));
                        ActivityUtils.startActivity(bundle, ApplyAfterSalesActivity.class);
                    });
                    break;
                case "1":
                    tvShouhou.setVisibility(View.VISIBLE);
                    tvShouhou.setText("售后中");
                    break;
                case "2":
                    tvShouhou.setVisibility(View.VISIBLE);
                    tvShouhou.setText("售后完成");
                    break;
                case "3":
                    tvShouhou.setVisibility(View.VISIBLE);
                    tvShouhou.setText("已拒绝");
                    break;
                default:
                    tvShouhou.setVisibility(View.GONE);
                    break;
            }

            TextView tvJifen = view.findViewById(R.id.tvJifen);
            if (listBean.getList().get(i).getGold().equals("0")) {
                tvJifen.setVisibility(View.GONE);
            } else {
                tvJifen.setVisibility(View.VISIBLE);
                tvJifen.setText("+积分" + listBean.getList().get(i).getGold());
            }
            goodsLayout.addView(view);
        }

        TextView tvOrderRightButton = baseViewHolder.getView(R.id.tvOrderRightButton);
        TextView tvOrderLeftButton = baseViewHolder.getView(R.id.tvOrderLeftButton);
        TextView tvOrderStatus = baseViewHolder.getView(R.id.tvOrderStatus);

        switch (listBean.getStatus()) {
            case "0":
                tvOrderStatus.setText("待付款");
                tvOrderLeftButton.setText("取消订单");
                tvOrderRightButton.setText("立即支付");

                tvOrderLeftButton.setVisibility(View.VISIBLE);
                tvOrderRightButton.setVisibility(View.VISIBLE);

                tvOrderLeftButton.setOnClickListener(v -> {
                    new XPopup.Builder(getContext())
                            .asConfirm("取消订单", "是否取消订单？", () -> {
                                HttpsUtil.getInstance(getContext()).cancelOrder(listBean.getId(), object -> {
                                    removeAt(baseViewHolder.getAdapterPosition());
                                });
                            }).show();
                });
                tvOrderRightButton.setOnClickListener(v -> {
                    new XPopup.Builder(getContext())
                            .asBottomList("", new String[]{"微信", "支付宝"}, (position, text) -> {
                                HttpsUtil.getInstance(getContext()).pay(listBean.getId(), position == 0 ? 5 : 4, object -> {
                                    onStatusListener.onOrderPay(position == 0 ? 5 : 4, GsonUtils.toJson(object));
                                });
                            }).show();
                });
                break;
            case "1"://待发货
                tvOrderStatus.setText("待发货");
                tvOrderLeftButton.setText("取消订单");
                tvOrderRightButton.setText("催单");

                tvOrderLeftButton.setVisibility(View.GONE);
                tvOrderRightButton.setVisibility(View.GONE);
                tvOrderRightButton.setOnClickListener(v -> {
                    ToastUtils.showShort("催单成功");
                });
                break;
            case "2"://配送中
                tvOrderStatus.setText("待收货");
                tvOrderLeftButton.setText("查看物流");
                tvOrderRightButton.setText("确认收货");

                tvOrderLeftButton.setVisibility(View.GONE);
                tvOrderRightButton.setVisibility(View.VISIBLE);

                tvOrderRightButton.setOnClickListener(v -> {
                    new XPopup.Builder(getContext())
                            .asConfirm("收货", "确定收货？", () ->
                                    HttpsUtil.getInstance(getContext()).sureReceipt(listBean.getId(), object -> {
                                        onStatusListener.onOrderSureReceipt();
                                    })).show();
                });
                break;
            case "4"://申请退货
            case "8"://申请换货
                tvOrderStatus.setText("售后");

                tvOrderLeftButton.setVisibility(View.GONE);
                tvOrderRightButton.setVisibility(View.GONE);
                break;
            case "3":
            case "5":
            case "7":
                tvOrderStatus.setText("已完成");
                tvOrderLeftButton.setText("申请售后");
                tvOrderRightButton.setText("再来一单");

                tvOrderLeftButton.setVisibility(View.GONE);
                tvOrderRightButton.setVisibility(View.GONE);
                break;
        }
    }

    public interface OnStatusListener {
        void onOrderPay(int payType, String data);

        void onOrderSureReceipt();
    }
}
