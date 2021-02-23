package com.cdxz.liudake.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.Bus.UpdateUserInfoBean;
import com.cdxz.liudake.bean.UserIndexBean;
import com.cdxz.liudake.pop.PopSex;
import com.cdxz.liudake.pop.PopUploadAvatar;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.login.ResetPwdActivity;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.Date;

import butterknife.BindView;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.ivAvatar)
    RoundedImageView ivAvatar;

    @BindView(R.id.tvNick)
    TextView tvNick;

    @BindView(R.id.tvSex)
    TextView tvSex;

    @BindView(R.id.tvBirthday)
    TextView tvBirthday;

    @BindView(R.id.tvPhone)
    TextView tvPhone;

    private UserIndexBean bean;

    public static void startUserInfoActivity(Context context, UserIndexBean bean) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initViews() {
        setTitleText("个人信息");
        setTitleRightText("编辑");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDatas() {
        BusUtils.register(this);
        bean = (UserIndexBean) getIntent().getSerializableExtra("bean");
        Glide.with(this)
                .load(Constants.PICTURE_HTTPS_URL + bean.getHead())
                .placeholder(R.mipmap.img_default)
                .into(ivAvatar);
        tvNick.setText(bean.getName());
        //
        tvSex.setText(StringUtils.isEmpty(bean.getGender()) ? null : bean.getGender());
        //
        if (StringUtils.isEmpty(bean.getBirthday()) || bean.getBirthday().equals("0")) {
            tvBirthday.setText(null);
        } else {
            tvBirthday.setText(TimeUtils.millis2String(Long.parseLong(bean.getBirthday()) * 1000, "yyyy.MM.dd"));
        }
        tvPhone.setText(bean.getPhone().substring(0, 3) + "****" + bean.getPhone().substring(7, bean.getPhone().length()));
    }

    @BusUtils.Bus(tag = BusTag.UPDATE_USER_INFO)
    public void onUpdateUserInfo(UpdateUserInfoBean bean) {
        this.bean.setHead(bean.getHead());
        this.bean.setName(bean.getName());
        this.bean.setGender(bean.getGender());
        this.bean.setBirthday(String.valueOf(bean.getBirthday()));
        //
        Glide.with(this)
                .load(Constants.PICTURE_HTTPS_URL + bean.getHead())
                .placeholder(R.mipmap.img_default)
                .into(ivAvatar);
        tvNick.setText(bean.getName());
        tvSex.setText(bean.getGender());
        tvBirthday.setText(TimeUtils.millis2String(bean.getBirthday() * 1000, "yyyy.MM.dd"));
    }

    @Override
    protected void initListener() {
//        findViewById(R.id.avatarLayout).setOnClickListener(v -> {
//            new XPopup.Builder(this)
//                    .asCustom(new PopUploadAvatar(this, i -> {
//
//                    })).show();
//        });
        findViewById(R.id.tvResetPwd).setOnClickListener(v -> {
            ResetPwdActivity.startResetPwdActivity(this);
        });
    }

    @Override
    protected void onRightListener() {
        super.onRightListener();
        EditUserInfoActivity.startEditUserInfoActivity(this, bean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}