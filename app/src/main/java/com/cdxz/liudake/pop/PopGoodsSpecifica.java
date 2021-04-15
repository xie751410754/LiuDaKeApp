package com.cdxz.liudake.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.PopGoodsSpecificaAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.GoodsDetailBean;
import com.cdxz.liudake.bean.PriceBySizeBean;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PopGoodsSpecifica extends BottomPopupView {

    private RoundedImageView ivPic;
    private TextView tvPrice;
    private TextView tvKucun;
    private TextView tvGoodsType;
    private TextView tvNum;
    private LinearLayout viewLayout;

    private List<String> id = new ArrayList<>();
    private List<String> content = new ArrayList<>();
    private GoodsDetailBean bean;
    private OnSubmitListener onSubmitListener;

    public PopGoodsSpecifica(@NonNull Context context, GoodsDetailBean bean, OnSubmitListener onSubmitListener) {
        super(context);
        this.bean = bean;
        this.onSubmitListener = onSubmitListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_goods_specifica;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        id.clear();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate() {
        super.onCreate();
        ivPic = findViewById(R.id.ivPic);
        tvPrice = findViewById(R.id.tvPrice);
        tvKucun = findViewById(R.id.tvKucun);
        tvGoodsType = findViewById(R.id.tvGoodsType);
        tvNum = findViewById(R.id.tvNum);
        viewLayout = findViewById(R.id.viewLayout);
        //
        Glide.with(getContext())
                .load(bean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into(ivPic);
        tvPrice.setText("￥" + bean.getSaleprice());
        tvKucun.setText("库存 " + bean.getStock());
        //
        viewLayout.removeAllViews();
        String[] canshu = new String[bean.getSize().size()];
        for (int i = 0; i < bean.getSize().size(); i++) {
            canshu[i] = "选择";
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_pop_goods_specifica, null);
            TextView tvTypeTitle = view.findViewById(R.id.tvTypeTitle);
            tvTypeTitle.setText(bean.getSize().get(i).getName());
            //
            RecyclerView recyclerType = view.findViewById(R.id.recyclerType);
            recyclerType.setLayoutManager(new FlexboxLayoutManager(getContext()));
            PopGoodsSpecificaAdapter mAdapter = new PopGoodsSpecificaAdapter(bean.getSize().get(i).getList());
            recyclerType.setAdapter(mAdapter);
            int finalI = i;
            mAdapter.setOnItemClickListener((adapter, view1, position) -> {
                for (int j = 0; j < bean.getSize().get(finalI).getList().size(); j++) {
                    if (position == j) {
                        if (bean.getSize().get(finalI).getList().get(j).isSelect()) {
                            bean.getSize().get(finalI).getList().get(j).setSelect(false);
                            canshu[finalI] = "选择";
                            content.remove(bean.getSize().get(finalI).getList().get(j).getContent());
                        } else {
                            bean.getSize().get(finalI).getList().get(j).setSelect(true);
                            canshu[finalI] = bean.getSize().get(finalI).getList().get(j).getContent();
                            content.add(bean.getSize().get(finalI).getList().get(j).getContent());
                        }
                    } else {
                        bean.getSize().get(finalI).getList().get(j).setSelect(false);
                        content.remove(bean.getSize().get(finalI).getList().get(j).getContent());
                    }
                }
                mAdapter.notifyDataSetChanged();
                tvGoodsType.setText(String.format("选择：%s", Arrays.asList(canshu).toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", " ")));
                if (content.size() == bean.getSize().size()) {
                    getPriceBySize(content.toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "")
                    );
                }
            });
            //
            viewLayout.addView(view);
        }
        tvGoodsType.setText(String.format("选择：%s", Arrays.asList(canshu).toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", " ")));
        findViewById(R.id.ivMinus).setOnClickListener(v -> {
            int num = Integer.parseInt(tvNum.getText().toString());
            num--;
            if (num <= 1) {
                tvNum.setText("1");
                return;
            }
            tvNum.setText(String.valueOf(num));
        });
        findViewById(R.id.ivAdd).setOnClickListener(v -> {
            int num = Integer.parseInt(tvNum.getText().toString());
            num++;
            tvNum.setText(String.valueOf(num));
        });
        findViewById(R.id.ivCancel).setOnClickListener(v -> dismiss());
        findViewById(R.id.tvSubmit).setOnClickListener(v -> {
            id.clear();
            for (GoodsDetailBean.SizeBean sizeBean : bean.getSize()) {
                for (GoodsDetailBean.SizeBean.ListBean listBean : sizeBean.getList()) {
                    if (listBean.isSelect()) {
                        id.add(listBean.getContent());
                    }
                }
            }
            LogUtils.e("id - " + GsonUtils.toJson(id));
            LogUtils.e("id - " + GsonUtils.toJson(bean.getSize()));

            if (id.size() != bean.getSize().size()) {
                ToastUtils.showShort("请选择相应规格");
                return;
            }
            LogUtils.e("id - " + id.toString());

//            String result = "";
//            for (int i = 0; i < id.size(); i++) {
//                result = result + id.get(i) + "&&";
//            }
//            String content = result.substring(0, result.length() - 2);
//            LogUtils.e("result" + result + "zzz" + content);
            onSubmitListener.onSubmit(id.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", "&&"), Integer.parseInt(tvNum.getText().toString()));
            dismiss();
        });
    }

    /**
     * 根据规格获取价格
     *
     * @param string
     */
    @SuppressLint("SetTextI18n")
    private void getPriceBySize(String string) {

        HttpsUtil.getInstance(getContext()).getPriceBySize(bean.getId(), string, object -> {
            if (object.equals("-1")) {
                tvPrice.setText("￥" + bean.getSaleprice());
            } else {
                PriceBySizeBean sizeBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), PriceBySizeBean.class);
                tvPrice.setText("￥" + sizeBean.getNormal_price());
            }
        });
    }

    public interface OnSubmitListener {
        void onSubmit(String s, int num);
    }
}
