package com.cdxz.liudake.adapter.shop_mall;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.bean.JDCategoryMenuDto;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.JDGoodsListActivity;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Random;

public class JDCategoryMenuAdapter extends BaseQuickAdapter<JDCategoryMenuDto.DataDTO, BaseViewHolder> {
    public JDCategoryMenuAdapter(List<JDCategoryMenuDto.DataDTO> data) {
        super(R.layout.item_shop_mall_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, JDCategoryMenuDto.DataDTO bean) {

        if (baseViewHolder.getAdapterPosition()==9){
            baseViewHolder.setText(R.id.tvTitle, "全部");
            Glide.with(getContext())
                    .load("http://47.108.198.70//Uploads//Picture//message//20201113//5fae26c8c9aac.png")
                    .placeholder(R.mipmap.shop_mall_more)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivPic));
        }else {
            baseViewHolder.setText(R.id.tvTitle, bean.getSTitle());
            Glide.with(getContext())
                    .load(bean.getMainPic().startsWith("http") ? bean.getMainPic() : Constants.PICTURE_HTTPS_URL + bean.getMainPic())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivPic));
        }
        baseViewHolder.itemView.setOnClickListener(v -> {

            if (baseViewHolder.getAdapterPosition() + 1 == getData().size()){
                GoodsClassActivity.startGoodsClassActivity(getContext());

            }else {

                JDGoodsListActivity.starJDGoodsListActivity(getContext(),bean.getCatId(),bean.getName());
            }
        });
    }
}
