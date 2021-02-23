package com.cdxz.liudake.ui.store_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.databinding.ActivityStoreCoreBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.my.InviteCodeActivity;
import com.cdxz.liudake.ui.my.service.FAQActivity;
import com.cdxz.liudake.ui.my.service.ToPromoteActivity;
import com.cdxz.liudake.util.UserInfoUtil;

public class StoreCoreActivity extends BaseTitleActivity<ActivityStoreCoreBinding> implements View.OnClickListener {


    private LoginBean.ShopBean bean;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_core;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);
        bean = (LoginBean.ShopBean) getIntent().getSerializableExtra("shopList");

        Glide.with(this).load(UserInfoUtil.getLoginInfo().getHead()).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(binding.ivAvatar);
        binding.tvPhone.setText(UserInfoUtil.getLoginInfo().getBind_phone());
        binding.tvNick.setText(bean.getName());


        binding.tvShare.setOnClickListener(this);
//        binding.tvWallet.setOnClickListener(this);
        binding.tvInvite.setOnClickListener(this);
        binding.tvShoukuan.setOnClickListener(this);
        binding.tvTuiguang.setOnClickListener(this);
        binding.tvZhinan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_share:
                startActivity(InviteCodeActivity.class);
                break;
//            case R.id.tv_wallet:
//                startActivity(StoreWalletActivity.class);
//                break;
            case R.id.tv_invite:
                startActivity(InviteCodeActivity.class);
                break;
            case R.id.tv_shoukuan:
                Bundle bundle = new Bundle();
                bundle.putString("shopId", bean.getId());
                startActivity(StoreQRActivity.class, bundle);
                break;
            case R.id.tv_tuiguang:
                startActivity(ToPromoteActivity.class);
                break;
            case R.id.tv_zhinan:
                startActivity(StoreGuideActivity.class);
                break;
        }
    }
}
