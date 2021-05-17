package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.AdvertAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeCXBannerAdapter;
import com.cdxz.liudake.bean.AdvertDto;
import com.cdxz.liudake.util.CountDownView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class PopAdvert extends BottomPopupView {

    private KnowListener onPwdListener;
    List<AdvertDto> dataList;
    Banner banner;
    CountDownView countDownView;
    String count;
    public PopAdvert(@NonNull Context context, List<AdvertDto> list,String count) {
        super(context);
        dataList = list;
        this.count = count;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_advert;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        banner = findViewById(R.id.banner);
        banner.setAdapter(new AdvertAdapter(dataList), true)
                .setIndicator(new CircleIndicator(getContext()));


        countDownView = findViewById(R.id.countDownView);

        countDownView.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                new XPopup.Builder(getContext()).asCustom(new PopSignInSuccess(getContext(),count)).show();
                dismiss();
            }
        });
        countDownView.startCountDown();

    }

    public interface KnowListener {
        void onSubmit();
    }
}
