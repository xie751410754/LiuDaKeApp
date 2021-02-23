package com.cdxz.liudake.ui.store_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cdxz.liudake.R;
import com.cdxz.liudake.databinding.ActivityStoreWalletBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;

public class StoreWalletActivity extends BaseTitleActivity<ActivityStoreWalletBinding> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_wallet;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);
    }
}
