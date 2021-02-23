package com.cdxz.liudake.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.InviteCodeBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;

import java.io.File;

import butterknife.BindView;

public class InviteCodeActivity extends BaseActivity {

    @BindView(R.id.tvInviteCode)
    TextView tvInviteCode;

    @BindView(R.id.ivQrcode)
    ImageView ivQrcode;

    @BindView(R.id.qrcodeLayout)
    RelativeLayout qrcodeLayout;

    public static void startInviteCodeActivity(Context context) {
        Intent intent = new Intent(context, InviteCodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_invite_code;
    }

    @Override
    protected void initViews() {
        setTitleText("邀请码");
    }

    @Override
    protected void initDatas() {
        HttpsUtil.getInstance(this).invitQrcode(object -> {
            InviteCodeBean inviteCodeBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), InviteCodeBean.class);
            tvInviteCode.setText(inviteCodeBean.getUid());
            Glide.with(this)
                    .load(Constants.PICTURE_HTTPS_URL + inviteCodeBean.getMy_invitecode())
                    .placeholder(R.mipmap.img_default)
                    .into(ivQrcode);
            findViewById(R.id.tvCopyInviteCode).setOnClickListener(v -> {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", inviteCodeBean.getUid());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showShort("复制成功");
            });
            findViewById(R.id.tvShareCode).setOnClickListener(v -> {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, Constants.BASE_HTTPS_URL + "Home/index/regist/invitecode/" + inviteCodeBean.getUid());
                startActivity(Intent.createChooser(textIntent, "分享"));
            });
        });
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tvSaveQRCode).setOnClickListener(v -> {
            File file = ImageUtils.save2Album(drawMeasureView(qrcodeLayout), Bitmap.CompressFormat.PNG);
            if (file != null) {
                ToastUtils.showShort("保存成功");
            } else {
                ToastUtils.showShort("保存失败");
            }
        });
    }

    /**
     * 绘制已经测量过的View
     */
    private Bitmap drawMeasureView(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}