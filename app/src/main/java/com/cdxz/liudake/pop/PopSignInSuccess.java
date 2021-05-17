package com.cdxz.liudake.pop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.CenterPopupView;

public class PopSignInSuccess extends CenterPopupView {

    TextView tv_count;
    private String count;
    private KnowListener onPwdListener;

    public PopSignInSuccess(@NonNull Context context, String count ) {
        super(context);
        this.count = count;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_sign_in_new;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        tv_count = findViewById(R.id.tv1);
        tv_count.setText(count+"");

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
