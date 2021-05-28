package com.cdxz.liudake.adapter.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.ToPromoteBean;
import com.cdxz.liudake.bean.ZhiTuiRankDto;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ZhiTuiRankAdapter extends BaseQuickAdapter<ZhiTuiRankDto, BaseViewHolder> {
    public ZhiTuiRankAdapter(List<ZhiTuiRankDto> data) {
        super(R.layout.item_zhitui_rank_new, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhiTuiRankDto bean) {
        baseViewHolder.setText(R.id.tv_phone, bean.getPhone())
                .setText(R.id.tv_rankNum, bean.getCount_zhitui());
        Glide.with(getContext())
                .load(Constants.PICTURE_HTTPS_URL +bean.getHead())
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.img_rank_head));
        TextView textView = baseViewHolder.getView(R.id.tv_position);
        textView.setText(baseViewHolder.getAdapterPosition()+1+"");
        ImageView imageView = baseViewHolder.getView(R.id.img_rank);
        if (bean.getRank()==1){
            imageView.setImageResource(R.mipmap.icon_rank_one);
            imageView.setVisibility(View.VISIBLE);

            textView.setVisibility(View.GONE);
        }else if (bean.getRank()==2){
            imageView.setImageResource(R.mipmap.icon_rank_two);
            imageView.setVisibility(View.VISIBLE);

            textView.setVisibility(View.GONE);

        }else if (bean.getRank()==3){
            imageView.setImageResource(R.mipmap.icon_rank_three);
            imageView.setVisibility(View.VISIBLE);

            textView.setVisibility(View.GONE);

        }else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);

        }



    }
}
