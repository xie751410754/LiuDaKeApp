package com.cdxz.liudake.adapter.shop_mall;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.bean.Menu2Bean;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class Menu2Adapter extends BaseQuickAdapter<HomeIndexBean.GoodsCuxiaoBean, BaseViewHolder> {


    private CountDownTimer downTimer;

    List<HomeIndexBean.GoodsCuxiaoBean> list;

    public Menu2Adapter(List<HomeIndexBean.GoodsCuxiaoBean> data) {
        super(R.layout.item_shop_mall_menu2_new, data);
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeIndexBean.GoodsCuxiaoBean bean) {
        baseViewHolder.setText(R.id.tvTips, bean.getTitle())
                .setText(R.id.tvTips2, bean.getSubtitle());
        TextView tvTips2 = baseViewHolder.getView(R.id.tvTips2);
        List<HomeIndexBean.GoodsCuxiaoBean.GoodsDTO> goodsBeanList = bean.getGoods();

        if (!ObjectUtils.isEmpty(goodsBeanList)) {
            Glide.with(getContext())
                    .load(goodsBeanList.get(0).getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage1));
            Glide.with(getContext())
                    .load(goodsBeanList.get(1).getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage2));
            TextView tv_price = baseViewHolder.getView(R.id.tv_price);
            tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            TextView tv_price2 = baseViewHolder.getView(R.id.tv_price2);
            tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            tv_price.setText("￥" + goodsBeanList.get(0).getSaleprice());
            tv_price2.setText("￥" + goodsBeanList.get(1).getSaleprice());

            switch (goodsBeanList.get(0).getPrice_type()) {
                case "0":
                    baseViewHolder.setText(R.id.tv_jifen, goodsBeanList.get(0).getCx_points() + "积分")
                            .setText(R.id.tv_priceType, "运费￥" + goodsBeanList.get(0).getCx_postage());
                    break;
                case "1":
                    baseViewHolder.setText(R.id.tv_jifen, goodsBeanList.get(0).getCx_points() + "积分")
                            .setText(R.id.tv_priceType, "运费￥" + goodsBeanList.get(0).getCx_postage());
                    break;
                case "2":
                    baseViewHolder.setText(R.id.tv_jifen, goodsBeanList.get(0).getCx_points() + "积分")
                            .setText(R.id.tv_priceType, "￥" + goodsBeanList.get(0).getCx_price());
                    break;
                case "3":
                    baseViewHolder.setText(R.id.tv_jifen, "￥" + goodsBeanList.get(0).getCx_price());
                    break;
                default:
                    baseViewHolder.setText(R.id.tv_jifen, "￥" + goodsBeanList.get(0).getCx_price());


            }
            switch (goodsBeanList.get(1).getPrice_type()) {
                case "0":
                    baseViewHolder.setText(R.id.tv_jifen, goodsBeanList.get(0).getCx_points() + "积分")
                            .setText(R.id.tv_priceType, "运费￥" + goodsBeanList.get(0).getCx_postage());
                    break;
                case "1":
                    baseViewHolder.setText(R.id.tv_jifen, goodsBeanList.get(0).getCx_points() + "积分")
                            .setText(R.id.tv_priceType, "运费￥" + goodsBeanList.get(0).getCx_postage());
                    break;
                case "2":
                    baseViewHolder.setText(R.id.tv_jifen, goodsBeanList.get(0).getCx_points() + "积分")
                            .setText(R.id.tv_priceType, "￥" + goodsBeanList.get(0).getCx_price());
                    break;
                case "3":
                    baseViewHolder.setText(R.id.tv_jifen, "￥" + goodsBeanList.get(0).getCx_price());
                    break;
                default:
                    baseViewHolder.setText(R.id.tv_jifen, "￥" + goodsBeanList.get(0).getCx_price());


            }

        }
        if (list.size()>3 && baseViewHolder.getAdapterPosition()==3){

        }


        long startMills = TimeUtils.string2Millis(bean.getStart_time(), "yyyy-MM-dd HH:mm:ss");
        long endMills = TimeUtils.string2Millis(bean.getEnd_time(), "yyyy-MM-dd HH:mm:ss");
        long nowMills = TimeUtils.getNowMills();

        LogUtils.e("开始时间 = " + startMills);
        LogUtils.e("结束时间 = " + startMills);
        LogUtils.e("现在时间 = " + nowMills);
        if (nowMills < startMills) {
            tvTips2.setText("活动未开始");
        } else if (nowMills > endMills) {
            tvTips2.setText("活动已结束");
        } else {
            if (downTimer != null) {
                downTimer.cancel();
                downTimer = null;
            }
            long timeSpan = endMills - nowMills;
//                    TimeUtils.getM
            LogUtils.e("时间差1 = " + timeSpan);
            downTimer = new CountDownTimer(timeSpan, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    long ss = millisUntilFinished / 1000;
                    long h = ss / 3600;
                    long m = ss % 3600 / 60;
                    long s = ss % 3600 % 60;

                    String H;
                    if (h < 10) {
                        H = "0" + h;
                    } else {
                        H = String.valueOf(h);
                    }
                    String M;
                    if (m < 10) {
                        M = "0" + m;
                    } else {
                        M = String.valueOf(m);
                    }
                    String S;
                    if (s < 10) {
                        S = "0" + s;
                    } else {
                        S = String.valueOf(s);
                    }

                    tvTips2.setText(H + ":" + M + ":" + S);
                }

                @Override
                public void onFinish() {
                    tvTips2.setText("活动已结束");
                }
            };
            downTimer.start();
        }


//        String[] urls = bean.getUrl().split(",");
//        Glide.with(getContext())
//                .load(urls[0].startsWith("http") ? urls[0] : Constants.PICTURE_HTTPS_URL + urls[0])
//                .placeholder(R.mipmap.img_default)
//                .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage1));
//        Glide.with(getContext())
//                .load(urls[1].startsWith("http") ? urls[1] : Constants.PICTURE_HTTPS_URL + urls[1])
//                .placeholder(R.mipmap.img_default)
//                .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage2));
//        baseViewHolder.itemView.setOnClickListener(v -> {
////            GoodsListActivity.startGoodsListActivity(getContext());
//            SearchActivity.startSearchActivity(getContext(), "手机");
//        });
        baseViewHolder.itemView.setOnClickListener(v -> {
            HomeToGoodsListActivity.startHomeToGoodsListActivity(
                    getContext(), bean.getId(), bean.getTitle(), HomeToGoodsListActivity.HOME2
            );
        });
    }
}
