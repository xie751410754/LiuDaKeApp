package com.cdxz.liudake.adapter.life_circle;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class LifeCircleBannerAdapter extends BannerAdapter<String, LifeCircleBannerAdapter.BannerViewHolder> {

    public LifeCircleBannerAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, String data, int position, int size) {
        if (data.startsWith("http://") || data.startsWith("https://")) {
            Glide.with(holder.imageView.getContext())
                    .load(data)
                    .placeholder(R.mipmap.img_default)
                    .into(holder.imageView);
        } else {
            Glide.with(holder.imageView.getContext())
                    .load(Constants.PICTURE_HTTPS_URL + data)
                    .placeholder(R.mipmap.img_default)
                    .into(holder.imageView);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
