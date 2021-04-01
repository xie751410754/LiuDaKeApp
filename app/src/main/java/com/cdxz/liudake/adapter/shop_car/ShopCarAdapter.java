package com.cdxz.liudake.adapter.shop_car;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.ShopCarListBean;
import com.cdxz.liudake.view.SpacesItemDecoration;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.Collections;
import java.util.List;

public class ShopCarAdapter extends BaseQuickAdapter<ShopCarListBean, BaseViewHolder> {
    private OnSelectListener onSelectListener;

    public ShopCarAdapter(List<ShopCarListBean> data, OnSelectListener onSelectListener) {
        super(R.layout.item_shop_car, data);
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShopCarListBean listBean) {
        Glide.with(getContext())
                .load(listBean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivStorePic));
        baseViewHolder.setText(R.id.tvStoreName, listBean.getName());
        ImageView ivStoreCheck = baseViewHolder.getView(R.id.ivStoreCheck);
        if (listBean.getSelected().equals("1")) {
            ivStoreCheck.setImageResource(R.mipmap.icon_shop_car_goods_selector);
        } else {
            ivStoreCheck.setImageResource(R.mipmap.icon_pay_n);
        }
        SwipeRecyclerView recyclerGoods = baseViewHolder.getView(R.id.recyclerGoods);
        recyclerGoods.setLayoutManager(new LinearLayoutManager(getContext()));

        if (recyclerGoods.getItemDecorationCount() == 0) {//防止重复执行
            recyclerGoods.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10), false));
            SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, position) -> {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                deleteItem.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                deleteItem.setTextSize(14);
                deleteItem.setText("删除");
                deleteItem.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.appColor));
                deleteItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                deleteItem.setWidth(SizeUtils.dp2px(60));
                rightMenu.addMenuItem(deleteItem);
            };
            recyclerGoods.setSwipeMenuCreator(mSwipeMenuCreator);
            recyclerGoods.setOnItemMenuClickListener((menuBridge, adapterPosition) -> {
                menuBridge.closeMenu();
                // 左侧还是右侧菜单
                int direction = menuBridge.getDirection();
                if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                    // 菜单在Item中的Position
                    int menuPosition = menuBridge.getPosition();
                    if (menuPosition == 0) {
                        onSelectListener.onDeleteClick(listBean,adapterPosition);
                    }
                }
            });

        }
        ShopCarChildAdapter mAdapter = new ShopCarChildAdapter(listBean.getList());
        recyclerGoods.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            boolean isSelect = listBean.getList().get(position).isSelect();
//            if (isSelect) {
//                listBean.getList().get(position).setSelect(false);
//            } else {
//                listBean.getList().get(position).setSelect(true);
//            }
//            mAdapter.notifyDataSetChanged();
//            for (int i = 0; i < listBean.getList().size(); i++) {
//                if (listBean.getList().get(i).isSelect()) {
//                    listBean.setSelect(true);
//                } else {
//                    listBean.setSelect(false);
//                }
//            }
//            notifyDataSetChanged();
            onSelectListener.onChildClick(position, listBean);
        });
    }

    public interface OnSelectListener {
        void onChildClick(int position, ShopCarListBean listBean);

        void onDeleteClick(ShopCarListBean listBean, int position);
    }
}
