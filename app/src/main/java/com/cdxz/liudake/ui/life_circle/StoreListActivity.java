package com.cdxz.liudake.ui.life_circle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.Bus.GetStoreIdBean;
import com.cdxz.liudake.bean.LifeCircleCatBean;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;

public class StoreListActivity extends BaseActivity {

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabTitle = {"推荐", "推荐", "推荐", "推荐", "推荐"};

    public static void startStoreListActivity(Context context) {
        Intent intent = new Intent(context, StoreListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_list;
    }

    @Override
    protected void initViews() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initDatas() {
        getStoreCat();
    }




    @Override
    protected void initListener() {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = etSearch.getText().toString();
                if (!StringUtils.isEmpty(keyword)) {
                    BusUtils.post(BusTag.KEYWORDS, keyword);
                    hideKeyboard();
                }
            }
            return false;
        });
    }

    private void getStoreCat() {
        HttpsUtil.getInstance(this).nearShopCat(1,"", object -> {
            List<LifeCircleCatBean> catBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), LifeCircleCatBean.class);
            LifeCircleCatBean bean = new LifeCircleCatBean();
            bean.setId("");
            bean.setName("推荐");
            catBeanList.add(0, bean);
            //
            parseData(catBeanList);
            for (int i = 0; i < catBeanList.size(); i++) {
                fragmentList.add(SearchLifeCircleChildFragment.newInstance(catBeanList.get(i).getId()));
            }
            viewPager.setAdapter(new BasePagerAdapter(this, fragmentList));
            TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                tab.setText(catBeanList.get(position).getName());
            });
            mediator.attach();
            findViewById(R.id.tvAll).setOnClickListener(v -> {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v1) -> {
                    BusUtils.post(BusTag.GET_SEARCH_STORE_ID,
                            new GetStoreIdBean(catBeanList.get(options1).getId(),
                                    childBeanList.get(options1).get(options2).getId())
                    );
                })
                        .setCancelColor(ContextCompat.getColor(this, R.color.appColor))
                        .setSubmitColor(ContextCompat.getColor(this, R.color.appColor))
                        .build();
                pvOptions.setPicker(catBeanList, childBeanList);
                pvOptions.show();
            });
        });
    }

    List<List<LifeCircleCatBean.ChildBean>> childBeanList = new ArrayList<>();

    private void parseData(List<LifeCircleCatBean> catBeanList) {
        for (LifeCircleCatBean oneBean : catBeanList) {
            List<LifeCircleCatBean.ChildBean> childrenBeans = new ArrayList<>();
            if (CollectionUtils.isEmpty(oneBean.getChild())) {
                LifeCircleCatBean.ChildBean emptyBean = new LifeCircleCatBean.ChildBean();
                emptyBean.setId("");
                emptyBean.setName("暂无数据");
                childrenBeans.add(emptyBean);
            } else {
                for (LifeCircleCatBean.ChildBean twoBean : oneBean.getChild()) {
                    childrenBeans.add(twoBean);
                }
            }
            childBeanList.add(childrenBeans);
        }
    }
}