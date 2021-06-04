package com.cdxz.liudake.ui.my;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.ZhiTuiRankAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.ToPromoteBean;
import com.cdxz.liudake.bean.TransferAccountDto;
import com.cdxz.liudake.bean.ZhiTuiRankDto;
import com.cdxz.liudake.pop.PopPayPwd;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TransferAccountActivity extends BaseActivity {




    @BindView(R.id.tv_redMi_balance)
    TextView tv_redMi_balance;

    @BindView(R.id.tv_jifen_balance)
    TextView tv_jifen_balance;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_balance)
    EditText et_balance;

    String jifen,redMi;

    String type = "balance";

    public static void startTransferAccountActivity(Context context,String jifen,String redMi) {
        Intent intent = new Intent(context, TransferAccountActivity.class);
        intent.putExtra("jifen",jifen);
        intent.putExtra("redMi",redMi);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_transfer_account;
    }

    @Override
    protected void initViews() {
        tv_redMi_balance.setSelected(true);

       jifen =  getIntent().getStringExtra("jifen");
       redMi =  getIntent().getStringExtra("redMi");

       tv_balance.setText(redMi);

       et_balance.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {

               tv_submit.setText("转账￥"+et_balance.getText().toString());
           }
       });
    }


    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_redMi_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_redMi_balance.setSelected(true);
                tv_jifen_balance.setSelected(false);
                tv_balance.setText(redMi);
                type = "balance";
            }
        });
        tv_jifen_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_jifen_balance.setSelected(true);
                tv_redMi_balance.setSelected(false);
                tv_balance.setText(jifen);
                type = "gold";

            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastUtils.showShort("没有电话号码怎么转账");
                    return;
                }

                if (TextUtils.isEmpty(et_balance.getText().toString())){
                    ToastUtils.showShort("金额至少大于0吧");
                    return;
                }

                if (Double.parseDouble(et_balance.getText().toString())>Double.parseDouble(tv_balance.getText().toString())){
                    ToastUtils.showShort("你哪有这么多");
                }else {
                    new XPopup.Builder(context).asCustom(new PopPayPwd(context, text -> {
                        HttpsUtil.getInstance(context).transferAccount(et_balance.getText().toString(), type, et_phone.getText().toString(), text, object -> {
                            TransferAccountDto transferAccountDto = ParseUtils.parseJsonObject(GsonUtils.toJson(object), TransferAccountDto.class);
                            TransferAccountSuccessfulActivity.startTransferAccountSuccessfulActivity(context,transferAccountDto.getAmount(),transferAccountDto.getTophone(),transferAccountDto.getCreate_time(),transferAccountDto.getOrder_id());
                            finish();
                        });
                    })).show();
                }

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