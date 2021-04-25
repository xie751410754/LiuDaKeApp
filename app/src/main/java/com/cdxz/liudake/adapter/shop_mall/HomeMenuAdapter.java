package com.cdxz.liudake.adapter.shop_mall;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.pop.PopSelector;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Random;

public class HomeMenuAdapter extends BaseQuickAdapter<HomeIndexBean.GoodsActivityClassBean, BaseViewHolder> {


    private List<HomeIndexBean.GoodsActivityClassBean> list;

    public HomeMenuAdapter(List<HomeIndexBean.GoodsActivityClassBean> data, OnSelectListener onSelectListener) {
        super(R.layout.item_home_menu, data);
        this.list = data;
        this.onSelectListener = onSelectListener;
    }

    private OnSelectListener onSelectListener;


    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeIndexBean.GoodsActivityClassBean bean) {

        TextView proName = baseViewHolder.getView(R.id.tv_proName);
        ImageView img_selector = baseViewHolder.getView(R.id.img_selector);

        baseViewHolder.setText(R.id.tv_proName, bean.getName());

        if (bean.isSelected()) {

            img_selector.setVisibility(View.VISIBLE);
            proName.setAlpha(1);

        } else {
            proName.setAlpha(0.6f);
            img_selector.setVisibility(View.GONE);
        }
        baseViewHolder.itemView.setOnClickListener(v -> {


            onSelectListener.onClick(getItemPosition(bean));

            if (bean.getName().equals("京东")) {
                WebActivity.startWebActivity(getContext(), 6, "http://jd.liudake.cn/#/pages/index/index?uid="+ UserInfoUtil.getUid()+"&rd="+new Random().nextInt(100));

            } else {
                HomeToGoodsListActivity.startHomeToGoodsListActivity(
                        getContext(), bean.getId(), bean.getName(), HomeToGoodsListActivity.HOME1
                );
            }


        });
    }


    public interface OnSelectListener {
        void onClick(int position);
    }

}
