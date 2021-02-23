package com.cdxz.liudake.ui.store_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cdxz.liudake.R;
import com.cdxz.liudake.databinding.ActivityStoreQuXianBinding;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;

public class StoreQuXianActivity extends BaseTitleActivity<ActivityStoreQuXianBinding> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_qu_xian;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);
    }
}
