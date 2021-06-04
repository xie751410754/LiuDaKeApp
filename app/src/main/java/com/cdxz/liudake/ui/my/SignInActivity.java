package com.cdxz.liudake.ui.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.SignInAdapter;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.AdvertDto;
import com.cdxz.liudake.bean.BankInfoDto;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.bean.JDGoodsDto;
import com.cdxz.liudake.bean.OrderDetailBean;
import com.cdxz.liudake.bean.RadioDto;
import com.cdxz.liudake.bean.SignInListDto;
import com.cdxz.liudake.bean.VIPScoreDto;
import com.cdxz.liudake.pop.PopAdvert;
import com.cdxz.liudake.pop.PopSelector;
import com.cdxz.liudake.pop.PopSignInSuccess;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.order.OrderDetailActivity;
import com.cdxz.liudake.ui.order.OrderListActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignInActivity extends BaseActivity {


    @BindView(R.id.continues)
    TextView continues;

    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_signIn)
    TextView tv_signIn;
    @BindView(R.id.rv_signIn)
    RecyclerView rv_signIn;

    private String intContinue;

    private List<SignInListDto.DataDTO> signInList = new ArrayList<>();

    private OkHttpClient okHttpClient;

    private SignInAdapter signInAdapter;


    public static void startSignInActivity(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initViews() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 7);
        rv_signIn.setLayoutManager(linearLayoutManager);
        rv_signIn.addItemDecoration(new GridSpacingItemDecoration(7, SizeUtils.dp2px(5), false));

    }

    private final Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    VIPScoreDto bean = (VIPScoreDto) msg.obj;
                    tv_score.setText(bean.getData());
                    break;

                case 2:
                    continues.setText(intContinue);
                    List<SignInListDto.DataDTO> signInListDto = (List<SignInListDto.DataDTO>) msg.obj;
                    signInList.clear();
                    signInList.addAll(signInListDto);
                    signInAdapter.notifyDataSetChanged();
                    break;

                case 3:
                    tv_signIn.setClickable(true);
                    VIPScoreDto vipScoreDto = (VIPScoreDto) msg.obj;
                    if (vipScoreDto.getCode()==1){

                        HttpsUtil.getInstance(context).getAd(new BaseObserver<BaseBean<List<AdvertDto>>>(context, false) {
                            @Override
                            public void onSuccess(BaseBean<List<AdvertDto>> listBaseBean) {
                                if (listBaseBean.getData() == null) return;
                                new XPopup.Builder(context).dismissOnTouchOutside(false)
                                        .dismissOnBackPressed(false).asCustom(new PopAdvert(context,listBaseBean.getData(),vipScoreDto.getMsg())).show();


                            }
                        });

//                        new XPopup.Builder(context).asCustom(new PopSignInSuccess(context,vipScoreDto.getCount())).show();
                        getSignInList();

                    }else {
                        ToastUtils.showShort(vipScoreDto.getMsg());
                    }
                    break;


            }
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDatas() {
        okHttpClient = new OkHttpClient();


        getVIPScore();

        getSignInList();


        signInAdapter = new SignInAdapter(signInList);
        rv_signIn.setAdapter(signInAdapter);

    }

    private void getSignInList() {

        Request request = new Request.Builder()
                .url("http://liudake.cn/api/sign/getlist?uid=" + UserInfoUtil.getUid())
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseBody = response.body().string();
                SignInListDto baseBean = ParseUtils.parseJsonObject(responseBody, SignInListDto.class);
                intContinue = baseBean.getContinueX();

                Message message = new Message();
                message.what = 2;
                message.obj = baseBean.getData();
                mHandler.sendMessage(message);


            }
        });
    }

    private void getVIPScore() {
        Request request = new Request.Builder()
                .url("http://liudake.cn/api/sign/getjifen?uid=" + UserInfoUtil.getUid())
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseBody = response.body().string();
                VIPScoreDto baseBean = ParseUtils.parseJsonObject(responseBody, VIPScoreDto.class);
                Message message = new Message();
                message.what = 1;
                message.obj = baseBean;
                mHandler.sendMessage(message);


            }
        });
    }

    @Override
    protected void initListener() {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        findViewById(R.id.tv_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("点击了签到");
                signIn();
            }
        });


    }

    private void signIn() {
        tv_signIn.setClickable(false);
        Request request = new Request.Builder()
                .url("http://liudake.cn/api/sign/signin?uid=" + UserInfoUtil.getUid())
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                ToastUtils.showShort("数据获取失败");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseBody = response.body().string();
                VIPScoreDto baseBean = ParseUtils.parseJsonObject(responseBody, VIPScoreDto.class);
                Message message = new Message();
                message.what = 3;
                message.obj = baseBean;
                mHandler.sendMessage(message);


            }
        });
    }

    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }

}