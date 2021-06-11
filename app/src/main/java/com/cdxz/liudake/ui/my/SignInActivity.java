package com.cdxz.liudake.ui.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.SignInAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.bean.AdvertDto;
import com.cdxz.liudake.bean.SignInListDto;
import com.cdxz.liudake.bean.SignInMonthDto;
import com.cdxz.liudake.bean.VIPScoreDto;
import com.cdxz.liudake.pop.PopAdvert;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.MonthView;
import com.haibin.calendarview.MonthViewPager;
import com.lxj.xpopup.XPopup;


import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignInActivity extends BaseActivity implements CalendarView.OnMonthChangeListener{


    @BindView(R.id.continues)
    TextView continues;

    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_signIn)
    TextView tv_signIn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    @BindView(R.id.rv_signIn)
    RecyclerView rv_signIn;

    private int intContinue;

    private List<SignInListDto.DataDTO> signInList = new ArrayList<>();

    private OkHttpClient okHttpClient;

    private SignInAdapter signInAdapter;


    List<String> timeList= new ArrayList<>();
    HashMap<String,String > text =new LinkedHashMap<>();
    Map<String, Calendar> map = new HashMap<>();


    private int cYear ,cMonth;

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


        cYear = mCalendarView.getCurYear();
        cMonth = mCalendarView.getCurMonth();
        title.setText(String.valueOf(mCalendarView.getCurYear())+"年"+mCalendarView.getCurMonth() + "月");

        mCalendarView.setOnMonthChangeListener(this);



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
//                    continues.setText(intContinue);
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
//                        getSignInList();
                        getNewSignInList(cYear,cMonth);

                    }else {
                        ToastUtils.showShort(vipScoreDto.getMsg());
                    }
                    break;

                case 5:

                    continues.setText(intContinue+"");
                    List<SignInMonthDto.DataDTO> currentMonthList = (List<SignInMonthDto.DataDTO>) msg.obj;
                    timeList.clear();
                    text.clear();
                    map.clear();
                    int intDay =1;
                    for (int i=0;i<currentMonthList.size();i++){
                        if (currentMonthList.get(i).getIssign().equals("1")){
                            timeList.add(currentMonthList.get(i).getMydate());
                            text.put(currentMonthList.get(i).getMydate(),"+"+currentMonthList.get(i).getJifen());
                            String day = currentMonthList.get(i).getMydate();
                            if (day.length()==9){
                                intDay = Integer.parseInt(day.substring(day.length()-2,day.length()));
                            }else {
                                intDay = Integer.parseInt(day.substring(day.length()-1,day.length()));

                            }
                            map.put(getSchemeCalendar(cYear, cMonth, intDay, 0xFFE62129, currentMonthList.get(i).getJifen()).toString(),
                                    getSchemeCalendar(cYear, cMonth, intDay, 0xFFE62129, currentMonthList.get(i).getJifen()));
                        }
                    }

//
                    mCalendarView.setSchemeDate(map);


                    break;


            }
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDatas() {
        okHttpClient = new OkHttpClient();


        getNewSignInList(cYear,cMonth);


        getVIPScore();

        getSignInList();


        signInAdapter = new SignInAdapter(signInList);
        rv_signIn.setAdapter(signInAdapter);

        findViewById(R.id.img_last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.scrollToPre();
            }
        });
        findViewById(R.id.img_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.scrollToNext();
            }
        });


    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    private void getNewSignInList(int year ,int month) {
        String url = "http://liudake.cn/api/sign/signrcd?uid="+ UserInfoUtil.getUid()+"&year="+year+"&month="+month+"&tpe="+2;
        LogUtils.e("xzl"+url);
        Request request = new Request.Builder()
                .url(url)
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
                LogUtils.e("xzl"+responseBody);

                SignInMonthDto baseBean = ParseUtils.parseJsonObject(responseBody, SignInMonthDto.class);

                intContinue = baseBean.getCons();
                Message message = new Message();
                message.what = 5;
                message.obj = baseBean.getData();
                mHandler.sendMessage(message);


            }
        });
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
//                intContinue = baseBean.getContinueX();

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



    @Override
    public void onMonthChange(int year, int month) {
        intContinue = 0;
        cYear = year;
        cMonth = month;
        title.setText(String.valueOf(year)+"年"+month + "月");

        getNewSignInList(cYear,cMonth);
    }
}