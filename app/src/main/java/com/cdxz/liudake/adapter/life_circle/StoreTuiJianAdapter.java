package com.cdxz.liudake.adapter.life_circle;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.AddressListBean;
import com.cdxz.liudake.bean.LifeCircleBean;
import com.cdxz.liudake.bean.TuiJianStoreDto;
import com.cdxz.liudake.ui.life_circle.StoreDetailActivity2;
import com.cdxz.liudake.ui.life_circle.TuiJianDetailActivity;
import com.cdxz.liudake.ui.my.service.AddAddressActivity;
import com.cdxz.liudake.ui.store_manager.AddGoodsActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;

import java.util.List;

public class StoreTuiJianAdapter extends BaseQuickAdapter<TuiJianStoreDto, BaseViewHolder> {
    public StoreTuiJianAdapter(List<TuiJianStoreDto> data) {
        super(R.layout.item_store_tuijian, data);
    }

    private boolean isEdit = false;
    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, TuiJianStoreDto bean) {

        if (isEdit){
            baseViewHolder.getView(R.id.tvEdit).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.tvDeleteAddress).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.tvBuy).setVisibility(View.GONE);
        }else {
            baseViewHolder.itemView.setOnClickListener(v -> {
                TuiJianDetailActivity.startTuiJianDetailActivity(getContext(), bean.getId(),bean.getShopid());
            });
            baseViewHolder.getView(R.id.tvEdit).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.tvDeleteAddress).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.tvBuy).setVisibility(View.VISIBLE);

        }


        if (bean.getGoods_image()!=null&&bean.getGoods_image().size()>0) {

            Glide.with(getContext())
                    .load(Constants.PICTURE_HTTPS_URL + bean.getGoods_image().get(0))
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivStorePic));
        }
        baseViewHolder.setText(R.id.tvStoreName, bean.getName())
                .setText(R.id.tvStoreSubTitle, bean.getDescription())
                .setText(R.id.tvSalePrice, String.format("¥%s", bean.getSaleprice()))
                .setText(R.id.tv_sales, "销量 "+bean.getSalescount())
                .setText(R.id.tvPrice, String.format("¥%s", bean.getOrginalprice()));
        TextView tv = baseViewHolder.itemView.findViewById(R.id.tvPrice);
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


        baseViewHolder.getView(R.id.tvEdit).setOnClickListener(v -> {
            AddGoodsActivity.startAddGoodsActivity(getContext(), bean);
        });
        baseViewHolder.getView(R.id.tvDeleteAddress).setOnClickListener(v -> {
            new XPopup.Builder(getContext())
                    .asConfirm("删除", "是否删除该推荐商品？", () -> delete(bean)).show();
        });
        baseViewHolder.getView(R.id.tvBuy).setOnClickListener(v -> {
            TuiJianDetailActivity.startTuiJianDetailActivity(getContext(), bean.getId(),bean.getShopid());

        });

    }

    private void delete(TuiJianStoreDto listBean) {
        HttpsUtil.getInstance(getContext()).deleteTuiJianGoods(listBean.getShopid(),listBean.getId(), object -> {
//            BusUtils.post("UpdateGoodsList");
            remove(listBean);
            notifyDataSetChanged();
        });
    }

    public void isGoodManger(boolean isEdit){
        this.isEdit = isEdit;
    }

}
