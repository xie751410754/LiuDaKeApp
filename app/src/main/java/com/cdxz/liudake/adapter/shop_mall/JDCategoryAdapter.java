package com.cdxz.liudake.adapter.shop_mall;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.bean.JDCategoryDto;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.JDGoodsListActivity;
import com.cdxz.liudake.util.UserInfoUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Random;

public class JDCategoryAdapter extends BaseQuickAdapter<JDCategoryDto.DataDTO, BaseViewHolder> {


    private List<JDCategoryDto.DataDTO> list;

    public JDCategoryAdapter(List<JDCategoryDto.DataDTO> data, OnSelectListener onSelectListener) {
        super(R.layout.item_home_menu, data);
        this.list = data;
        this.onSelectListener = onSelectListener;
    }

    private OnSelectListener onSelectListener;


    @Override
    protected void convert(BaseViewHolder baseViewHolder, JDCategoryDto.DataDTO bean) {

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

            JDGoodsListActivity.starJDGoodsListActivity(getContext(),bean.getCatId(),bean.getName());

        });
    }


    public interface OnSelectListener {
        void onClick(int position);
    }

}
