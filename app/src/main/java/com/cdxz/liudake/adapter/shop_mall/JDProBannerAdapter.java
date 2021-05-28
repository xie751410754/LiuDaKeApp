package com.cdxz.liudake.adapter.shop_mall;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.JDBannerProDto;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsDetailActivity;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class JDProBannerAdapter extends BannerAdapter<JDBannerProDto.DataDTO, JDProBannerAdapter.BannerViewHolder> {

    public JDProBannerAdapter(List<JDBannerProDto.DataDTO> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, JDBannerProDto.DataDTO data, int position, int size) {
        Glide.with(holder.imageView.getContext())
                .load(data.getImg().startsWith("http") ? data.getImg() : Constants.BANNER_HTTPS_URL + data.getImg())
                .placeholder(R.mipmap.img_default)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {

        });
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
