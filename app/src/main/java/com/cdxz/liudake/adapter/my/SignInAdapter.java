package com.cdxz.liudake.adapter.my;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.ServiceBean;
import com.cdxz.liudake.bean.SignInListDto;
import com.cdxz.liudake.view.DrawableTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class SignInAdapter extends BaseQuickAdapter<SignInListDto.DataDTO, BaseViewHolder> {
    public SignInAdapter(List<SignInListDto.DataDTO> data) {
        super(R.layout.item_sign_in, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SignInListDto.DataDTO serviceBean) {


        LinearLayout linearLayout = baseViewHolder.getView(R.id.ll_bg);
        TextView tv_vipScore = baseViewHolder.getView(R.id.tv_vipScore);
        TextView tv_day = baseViewHolder.getView(R.id.tv_day);
        baseViewHolder.setText(R.id.tv_vipScore, "+"+serviceBean.getJifen()).setText(R.id.tv_day, serviceBean.getWeek());
        if (serviceBean.getIssign().equals("1")) {
            linearLayout.setBackgroundResource(R.mipmap.bg_week);
            tv_vipScore.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF8900));
            tv_day.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        } else {

            if (serviceBean.getWeek().equals("今日")) {
                linearLayout.setBackgroundResource(R.mipmap.bg_week_today);
                tv_vipScore.setTextColor(ContextCompat.getColor(getContext(), R.color.color_F3A672));
                tv_day.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

            } else {
                linearLayout.setBackgroundResource(R.mipmap.bg_week_normal);
                tv_vipScore.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
                tv_day.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
            }


        }
    }
}
