package com.cdxz.liudake.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.api.UploadUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.Bus.UpdateUserInfoBean;
import com.cdxz.liudake.bean.UploadBean;
import com.cdxz.liudake.bean.UserIndexBean;
import com.cdxz.liudake.pop.PopSex;
import com.cdxz.liudake.pop.PopUploadAvatar;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.PictureUtil;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;

public class EditUserInfoActivity extends BaseActivity implements PictureUtil.OnPictureListener {

    @BindView(R.id.ivAvatar)
    RoundedImageView ivAvatar;

    @BindView(R.id.etNick)
    EditText etNick;

    @BindView(R.id.tvSex)
    DrawableTextView tvSex;

    @BindView(R.id.tvBirthday)
    DrawableTextView tvBirthday;

    private UserIndexBean bean;
    private String head;

    public static void startEditUserInfoActivity(Context context, UserIndexBean bean) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void initViews() {
        setTitleText("编辑个人信息");
    }

    @Override
    protected void initDatas() {
        bean = (UserIndexBean) getIntent().getSerializableExtra("bean");
        assert bean != null;
        head = bean.getHead();
        Glide.with(this)
                .load(Constants.PICTURE_HTTPS_URL + bean.getHead())
                .placeholder(R.mipmap.img_default)
                .into(ivAvatar);
        etNick.setText(bean.getName());
        etNick.setSelection(bean.getName().length());
        tvSex.setText(StringUtils.isEmpty(bean.getGender()) ? null : bean.getGender());
        //
        if (StringUtils.isEmpty(bean.getBirthday()) || bean.getBirthday().equals("0")) {
            tvBirthday.setText(null);
        } else {
            tvBirthday.setText(TimeUtils.millis2String(Long.parseLong(bean.getBirthday()) * 1000, "yyyy.MM.dd"));
        }
    }

    @Override
    protected void initListener() {
        ivAvatar.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asCustom(new PopUploadAvatar(this, i -> {
                        switch (i) {
                            case PopUploadAvatar.TAKE_PHOTO:
                                PictureUtil.getInstance(this).openCamera(true, true, this);
                                break;
                            case PopUploadAvatar.CHOOSE_PIC:
                                PictureUtil.getInstance(this).openGallerySingle(true, true, this);
                                break;
                        }
                    })).show();
        });
        tvSex.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asCustom(new PopSex(this, text -> {
                        tvSex.setText(text);
                    })).show();
        });
        tvBirthday.setOnClickListener(v -> {
            TimePickerView pickerView = new TimePickerBuilder(this, (date, v1) -> {
                tvBirthday.setText(TimeUtils.date2String(date, "yyyy.MM.dd"));
            })
                    .setCancelColor(ContextCompat.getColor(this, R.color.appColor))
                    .setSubmitColor(ContextCompat.getColor(this, R.color.appColor))
                    .build();
            pickerView.show();
        });
        findViewById(R.id.tvSubmitEditUser).setOnClickListener(v -> {
            editUser();
        });
    }

    private void editUser() {
        String name = etNick.getText().toString();
        String gender = tvSex.getText().toString();
        String birthdayStr = tvBirthday.getText().toString();
        if (StringUtils.isEmpty(head)) {
            ToastUtils.showShort("请上传头像");
            return;
        }
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入昵称");
            return;
        }
        if (StringUtils.isEmpty(gender)) {
            ToastUtils.showShort("请设置性别");
            return;
        }
        if (StringUtils.isEmpty(birthdayStr)) {
            ToastUtils.showShort("请设置生日");
            return;
        }
        long birthday = TimeUtils.string2Millis(birthdayStr, "yyyy.MM.dd") / 1000;
        HttpsUtil.getInstance(this).editUser(head, name, birthday, gender, object -> {
            BusUtils.post(BusTag.UPDATE_USER_INFO, new UpdateUserInfoBean(head, name, gender, birthday));
            finish();
        });
    }

    @Override
    public void onResult(String path) {
        UploadUtil.getInstance().postFile(path, uploadBean -> {
            head = uploadBean.getImage().getUrlsmall();
            ivAvatar.post(() ->
                    Glide.with(this)
                            .load(Constants.PICTURE_HTTPS_URL + uploadBean.getImage().getUrlsmall())
                            .placeholder(R.mipmap.img_default)
                            .into(ivAvatar)
            );
        });
    }
}