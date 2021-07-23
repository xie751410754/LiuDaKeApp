package com.cdxz.liudake.adapter.life_circle;

import android.annotation.SuppressLint;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleCommentDetailDto;
import com.cdxz.liudake.bean.TuiJianStoreDto;
import com.cdxz.liudake.ui.life_circle.StoreDetailActivity2;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class LifeCircleCommentAdapter extends BaseQuickAdapter<LifeCircleCommentDetailDto.CommentsDTO, BaseViewHolder> {
    public LifeCircleCommentAdapter(List<LifeCircleCommentDetailDto.CommentsDTO> data) {
        super(R.layout.item_lifecircle_comment, data);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, LifeCircleCommentDetailDto.CommentsDTO bean) {

        if (bean.getHead().startsWith("http://") || bean.getHead().startsWith("https://")) {
            Glide.with(getContext())
                    .load(bean.getHead())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.img_head));
        } else {
            Glide.with(getContext())
                    .load(Constants.PICTURE_HTTPS_URL + bean.getHead())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.img_head));
        }
        baseViewHolder.setText(R.id.tv_name, bean.getName())
                .setText(R.id.tv_time, bean.getCreate_time())
                .setText(R.id.tv_content, bean.getContent());
    }
}
