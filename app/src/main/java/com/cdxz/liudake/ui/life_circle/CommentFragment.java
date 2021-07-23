package com.cdxz.liudake.ui.life_circle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.life_circle.LifeCircleCommentAdapter;
import com.cdxz.liudake.adapter.life_circle.StoreTuiJianAdapter;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleCommentDetailDto;
import com.cdxz.liudake.bean.LifeCircleDetailBean;
import com.cdxz.liudake.bean.TuiJianStoreDto;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentFragment extends BaseFragment {



    public static CommentFragment newInstance(String id){
        CommentFragment commentFragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        commentFragment.setArguments(args);
        return commentFragment;
    }
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.tv_notok)
    TextView tv_notok;
    @BindView(R.id.tv_badok)
    TextView tv_badok;
    @BindView(R.id.tv_hasimg)
    TextView tv_hasimg;
    @BindView(R.id.img_rank_head)
    RoundedImageView img_rank_head;
    @BindView(R.id.et_search)
    EditText et_search;
    private int page = 1;



    LifeCircleCommentAdapter lifeCircleCommentAdapter;
    private List<LifeCircleCommentDetailDto.CommentsDTO> lifeCircleBeanList = new ArrayList<>();

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_store_comment;
    }


    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tv_all, R.id.tv_ok, R.id.tv_notok, R.id.tv_badok, R.id.tv_hasimg})
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_all:
                tv_all.setSelected(true);
                tv_ok.setSelected(false);
                tv_notok.setSelected(false);
                tv_badok.setSelected(false);
                tv_hasimg.setSelected(false);
                break;

            case R.id.tv_ok:
                tv_all.setSelected(false);
                tv_ok.setSelected(true);
                tv_notok.setSelected(false);
                tv_badok.setSelected(false);
                tv_hasimg.setSelected(false);
                break;

            case R.id.tv_notok:
                tv_all.setSelected(false);
                tv_ok.setSelected(false);
                tv_notok.setSelected(true);
                tv_badok.setSelected(false);
                tv_hasimg.setSelected(false);
                break;

            case R.id.tv_badok:
                tv_all.setSelected(false);
                tv_ok.setSelected(false);
                tv_notok.setSelected(false);
                tv_badok.setSelected(true);
                tv_hasimg.setSelected(false);
                break;

            case R.id.tv_hasimg:
                tv_all.setSelected(false);
                tv_ok.setSelected(false);
                tv_notok.setSelected(false);
                tv_badok.setSelected(false);
                tv_hasimg.setSelected(true);
                break;
        }
    }

        @Override
    protected void initView() {
            rv_comment.setLayoutManager(new LinearLayoutManager(getContext()));

        tv_all.setSelected(true);
            Glide.with(getContext())
                    .load(UserInfoUtil.getHeadIMG())
                    .placeholder(R.mipmap.img_default)
                    .into(img_rank_head);
    }

    @Override
    protected void initData() {
        lifeCircleCommentAdapter = new LifeCircleCommentAdapter(lifeCircleBeanList);
        rv_comment.setAdapter(lifeCircleCommentAdapter);
        lifeCircleCommentAdapter.setEmptyView(R.layout.public_no_data);
        getCommentList();
    }

    private void getCommentList() {
        HttpsUtil.getInstance(getContext()).nearShopDetail(getArguments().getString("id"), page, 1, new HttpsCallback() {
            @Override
            public void onResult(Object object) {
                LifeCircleCommentDetailDto detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LifeCircleCommentDetailDto.class);
               List<LifeCircleCommentDetailDto.CommentsDTO> comments = detailBean.getComments();
               tv_all.setText(String.format("全部(%s)",detailBean.getComments_count()));
               tv_ok.setText(String.format("好评(%s)",detailBean.getHigh_star_count()));
               tv_notok.setText(String.format("中评(%s)",detailBean.getMid_star_count()));
               tv_badok.setText(String.format("差评(%s)",detailBean.getLow_star_count()));
               tv_hasimg.setText(String.format("有图(%s)",detailBean.getHave_image()));
                if (CollectionUtils.isEmpty(comments)) {
                    if (page == 1) {
                        refresh.finishRefreshWithNoMoreData();
                    } else {
                        refresh.finishLoadMoreWithNoMoreData();
                    }
//                lifeCircleBeanList.clear();
                } else {
                    if (page == 1) {
                        lifeCircleBeanList.clear();
                        if (comments.size() < Constants.LIST_SIZE) {
                            refresh.finishLoadMoreWithNoMoreData();
                        } else {
                            if (refresh!=null){
                                refresh.finishRefresh();
                            }
                        }
                    } else {
                        refresh.finishLoadMore();
                    }
                    lifeCircleBeanList.addAll(comments);
                }
                lifeCircleCommentAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void initListener() {
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String content = et_search.getText().toString();
                sendComment(content);
            }
            return false;
        });
    }

    public void sendComment(String content){
        HttpsUtil.getInstance(getContext()).commentShop(getArguments().getString("id"), content, new HttpsCallback() {
            @Override
            public void onResult(Object object) {
                ToastUtils.showShort("发表成功,待审核通过后显示");
            }
        });
    }

}
