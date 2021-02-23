package com.cdxz.liudake.ui.store_manager;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassChildAdapter;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassNameAdapter;
import com.cdxz.liudake.adapter.store_manager.StoreClassChildAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;

public class StoreClassActivity extends BaseActivity {
    @BindView(R.id.recyclerClass)
    RecyclerView recyclerClass;
    @BindView(R.id.recyclerChild)
    RecyclerView recyclerChild;
    @BindView(R.id.toolbar)
    android.widget.Toolbar toolbar;

    private String cateid;
    private GoodsClassNameAdapter classNameAdapter;
    StoreClassChildAdapter childAdapter;

    private CategoryListBean currentOneClass;

    public static void startGoodsClassActivity(Context context) {
        Intent intent = new Intent(context, StoreClassActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_class;
    }

    protected void initToolbar(Toolbar toolbar){
        setActionBar(toolbar);
        ActionBar actionBar =  getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void initViews() {
        initToolbar(toolbar);

        recyclerClass.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        categoryLists();


        recyclerChild.setLayoutManager(new LinearLayoutManager(this));

        childAdapter = new StoreClassChildAdapter();
        childAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("cat_id",childAdapter.getItem(position).getId());
                intent.putExtra("cat_name",currentOneClass.getName()+"-"+childAdapter.getItem(position).getName());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        recyclerChild.setAdapter(childAdapter);
    }

    @Override
    protected void initListener() {

    }

    private void categoryLists() {
        HttpsUtil.getInstance(this).getStoreClass(object -> {
            List<CategoryListBean> categoryListBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), CategoryListBean.class);
            for (int i = 0; i < categoryListBeanList.size(); i++) {
                if (i == 0) {//默认第一个选中
                    currentOneClass = categoryListBeanList.get(i);
                    categoryListBeanList.get(i).setSelect(true);
                    refresh(categoryListBeanList.get(0).getChild());
                } else {
                    categoryListBeanList.get(i).setSelect(false);
                }
            }
            classNameAdapter = new GoodsClassNameAdapter(categoryListBeanList);
            recyclerClass.setAdapter(classNameAdapter);
            classNameAdapter.setOnItemClickListener((adapter, view, position) -> {
                for (int i = 0; i < categoryListBeanList.size(); i++) {
                    if (i == position) {
                        categoryListBeanList.get(i).setSelect(true);
                        currentOneClass = classNameAdapter.getItem(position);
                        refresh(categoryListBeanList.get(i).getChild());
                    } else {
                        categoryListBeanList.get(i).setSelect(false);
                    }
                }
                classNameAdapter.notifyDataSetChanged();
            });
        });
    }

    private void refresh(List<CategoryListBean.ChildBean> childBeans) {
        childAdapter.setList(childBeans);
    }
}
