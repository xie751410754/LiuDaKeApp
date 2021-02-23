package com.cdxz.liudake.ui.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.StoreComment;
import com.cdxz.liudake.databinding.ActivityStoreCommentBinding;
import com.cdxz.liudake.databinding.ItemManagerCommentBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoreCommentActivity extends BaseTitleActivity<ActivityStoreCommentBinding> {
    String shopId;
    int page = 1;
    CommentAdapter adapter;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_comment;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);

        shopId = getIntent().getStringExtra("shopId");


        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(false);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(true);
                refreshLayout.finishRefresh();
            }
        });

        adapter = new CommentAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        getData(true);
    }

    private void getData(boolean refresh){
        if (refresh){
            page = 1;
        }else {
            page++;
        }

        HttpsUtil.getInstance(this).getStoreComment(shopId, page, new BaseObserver<BaseBean<List<StoreComment>>>(this,true) {
            @Override
            public void onSuccess(BaseBean<List<StoreComment>> listBaseBean) {
                if (listBaseBean.getData()==null)return;

                if (refresh){
                    adapter.setNewData(listBaseBean.getData());
                }else {
                    adapter.addData(listBaseBean.getData());
                }
            }
        });
    }

    static class CommentAdapter extends BaseQuickAdapter<StoreComment, BaseViewHolder>{

        public CommentAdapter() {
            super(R.layout.item_manager_comment);
        }

        @Override
        protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
            DataBindingUtil.bind(viewHolder.itemView);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreComment storeComment) {
            ItemManagerCommentBinding binding = baseViewHolder.getBinding();

            if (storeComment.getHead().startsWith("http://") || storeComment.getHead().startsWith("https://")) {
                Glide.with(getContext())
                        .load(storeComment.getHead())
                        .placeholder(R.mipmap.img_default)
                        .into(binding.ivHead);
            } else {
                Glide.with(getContext())
                        .load(Constants.PICTURE_HTTPS_URL + storeComment.getHead())
                        .placeholder(R.mipmap.img_default)
                        .into(binding.ivHead);
            }

            binding.rating.setRating(Float.valueOf(storeComment.getStar()));
            binding.tvContent.setText(storeComment.getContent());
            binding.tvName.setText(storeComment.getName());
//            binding.tvTime.setText(storeComment.g);
        }
    }
}
