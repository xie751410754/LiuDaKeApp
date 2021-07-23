package com.cdxz.liudake.ui.life_circle;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.adapter.my.LifecircleCategoryAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.Bus.GetStoreIdBean;
import com.cdxz.liudake.bean.LifeCircleCatBean;
import com.cdxz.liudake.pop.PopCommonAdvert;
import com.cdxz.liudake.pop.PopSignInSuccess;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lxj.xpopup.XPopup;
import com.stx.xhb.pagemenulibrary.PageMenuLayout;
import com.stx.xhb.pagemenulibrary.holder.AbstractHolder;
import com.stx.xhb.pagemenulibrary.holder.PageMenuViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SubCategoryActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.recyclerService)
    RecyclerView recyclerService;
    @BindView(R.id.pagemenu)
    PageMenuLayout mPageMenuLayout;

    private LifecircleCategoryAdapter mAdapter;

    public static void startSubCategoryActivity(Context context,String id,String name) {
        Intent intent = new Intent(context, SubCategoryActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sub_category_;
    }

    @Override
    protected void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_title.setText(getIntent().getStringExtra("name"));
        recyclerService.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerService.addItemDecoration(new GridSpacingItemDecoration(5, SizeUtils.dp2px(10), false));
    }

    @Override
    protected void initDatas() {

        getStoreCat(true);

        LifeCircleChildFragment lifeCircleChildFragment = LifeCircleChildFragment.newInstance(getIntent().getStringExtra("id"),"2");
        FragmentUtils.add(getSupportFragmentManager(),lifeCircleChildFragment,R.id.flyt_container);

    }

    private void getStoreCat(boolean isCat) {
        HttpsUtil.getInstance(this).nearShopCat(1,getIntent().getStringExtra("id"), object -> {
            List<LifeCircleCatBean> catBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), LifeCircleCatBean.class);

            mPageMenuLayout.setPageDatas(catBeanList, new PageMenuViewHolderCreator() {
                @Override
                public AbstractHolder createHolder(View itemView) {
                    return new AbstractHolder<LifeCircleCatBean>(itemView) {
                        private TextView entranceNameTextView;
                        private ImageView entranceIconImageView;

                        @Override
                        protected void initView(View itemView) {
                            entranceIconImageView = itemView.findViewById(R.id.iv_logo);
                            entranceNameTextView = itemView.findViewById(R.id.tv_name);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ((float) ScreenUtils.getScreenWidth() / 4.0f));
                            itemView.setLayoutParams(layoutParams);
                        }

                        @Override
                        public void bindView(RecyclerView.ViewHolder holder, final LifeCircleCatBean data, int pos) {
                            entranceNameTextView.setText(data.getName());
//                            entranceIconImageView.setImageResource(data.getImage());
                            Glide.with(SubCategoryActivity.this)
                                    .load(Constants.PICTURE_HTTPS_URL + data.getLogo())
                                    .placeholder(R.mipmap.img_default)
                                    .into(entranceIconImageView);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Toast.makeText(MainActivity.this, data.getName(), Toast.LENGTH_SHORT).show();
                                    BusUtils.post("SubCategory", data.getId());
                                }
                            });
                        }
                    };
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_life_category;
                }
            });

            mAdapter = new LifecircleCategoryAdapter(catBeanList);
            recyclerService.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                    SubCategoryActivity.startSubCategoryActivity(getContext(),mAdapter.getData().get(position).getId());
                }
            });

        });
    }

    @Override
    protected void initListener() {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = etSearch.getText().toString();
                if (!StringUtils.isEmpty(keyword)) {
                    BusUtils.post("SubCategoryActivity", keyword);
                    hideKeyboard();
                }
            }
            return false;
        });
        findViewById(R.id.tv_serach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = etSearch.getText().toString();
                if (!StringUtils.isEmpty(keyword)) {
                    BusUtils.post("SubCategoryActivity", keyword);
                    hideKeyboard();
                }
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

}