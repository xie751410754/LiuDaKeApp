package com.cdxz.liudake.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.ui.WebActivity;
import com.lxj.xpopup.core.CenterPopupView;

public class PopCommonAdvert extends CenterPopupView {

    private String count;
    private String url;

    private KnowListener onPwdListener;

    public PopCommonAdvert(@NonNull Context context, String count,String url ) {
        super(context);
        this.count = count;
        this.url = url;
    }

    @Override
    protected int getImplLayoutId() {
         return R.layout.pop_common_advert;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        ImageView imageView = findViewById(R.id.img_advert);
        Glide.with(getContext())
                .load(Constants.PICTURE_HTTPS_URL + count)
                .placeholder(R.mipmap.img_default)
                .into(imageView);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(url))return;
                WebActivity.startWebActivity(getContext(),"招商广告",Constants.BASE_URL+url);
                dismiss();
            }
        });
        findViewById(R.id.img_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface KnowListener {
        void onSubmit();
    }
}
