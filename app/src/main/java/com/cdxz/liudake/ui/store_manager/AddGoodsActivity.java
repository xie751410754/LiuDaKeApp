package com.cdxz.liudake.ui.store_manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassNameAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.api.UploadUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.bean.CategoryListDto;
import com.cdxz.liudake.bean.IndexAllInfoBean;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.bean.ShopBalance;
import com.cdxz.liudake.bean.StoreOpenStatus;
import com.cdxz.liudake.bean.StoreUnderMsgResult;
import com.cdxz.liudake.bean.TuiJianStoreDto;
import com.cdxz.liudake.bean.TuijianDetailDto;
import com.cdxz.liudake.databinding.ActivityStoreAddgoodsBinding;
import com.cdxz.liudake.databinding.ActivityStoreHomeNewBinding;
import com.cdxz.liudake.pop.BottomSelect;
import com.cdxz.liudake.pop.PopMap;
import com.cdxz.liudake.pop.PopSignInSuccess;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.my.service.RedmiBillActivity;
import com.cdxz.liudake.ui.my.service.ToPromoteActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.PictureUtil;
import com.cdxz.liudake.util.ThirdPartyMapsGuide;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.SquareCenterFrameLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddGoodsActivity extends BaseTitleActivity<ActivityStoreAddgoodsBinding> implements View.OnClickListener {
    Disposable disposable;
    private ArrayList<String> mPhotoList;
    private ArrayList<String> mPhotoList2;
    PostArticleImgAdapter postArticleImgAdapter;
    PostArticleImgAdapter2 postArticleImgAdapter2;
    List<CategoryListDto> categoryListDtos;
    String imgDetailPath ;
    public static void startAddGoodsActivity(Context context, String shopId) {
        Intent intent = new Intent(context, AddGoodsActivity.class);
        intent.putExtra("shopId", shopId);
        context.startActivity(intent);
    }
    public static void startAddGoodsActivity(Context context, TuiJianStoreDto bean) {
        Intent intent = new Intent(context, AddGoodsActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_addgoods;
    }

    @Override
    public void initData() {
        super.initData();
    }

    String cateId;
    String keyword ="衣";
    private void getGoodsCategoryList() {
        HttpsUtil.getInstance(this).getGoodsCate(keyword, new HttpsCallback() {
            @Override
            public void onResult(Object object) {
                List<CategoryListDto> categoryListBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), CategoryListDto.class);
                if (categoryListBeanList!=null &&categoryListBeanList.size()>0){
                    categoryListDtos = categoryListBeanList;
                }

                new XPopup.Builder(AddGoodsActivity.this).asCustom(new BottomSelect(AddGoodsActivity.this, categoryListDtos, new BottomSelect.OnSelectListener() {
                    @Override
                    public void onSelect(int position, String id, String name) {
                        binding.goodsCate.setText(name);
                        cateId = id;
                    }
                })).show();
            }
        });


    }

    private String editID,editCateID,editGoodsId;

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        binding.title.setText("添加商品");
//        initToolbar(binding.toolbar);
        binding.rl1.setOnClickListener(this);
        binding.rl2.setOnClickListener(this);
        binding.rl3.setOnClickListener(this);
        binding.rl4.setOnClickListener(this);
        binding.rl5.setOnClickListener(this);
        binding.rl6.setOnClickListener(this);
        binding.rl7.setOnClickListener(this);
        binding.rl8.setOnClickListener(this);
        binding.submit.setOnClickListener(this);
        binding.imgGoods.setOnClickListener(this);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        initRcv();
        TuiJianStoreDto bean  = (TuiJianStoreDto) getIntent().getSerializableExtra("bean");
        if (bean!=null){
            binding.title.setText("修改商品");
            initView(bean);
        }
    }

    private void initView(TuiJianStoreDto bean) {
        binding.goodsName.setText(bean.getName());
        binding.goodsStock.setText(bean.getStock());
        binding.orginalPrice.setText(bean.getOrginalprice());
        binding.saleprice.setText(bean.getSaleprice());
        binding.brand.setText(bean.getBrand());
        binding.parameters.setText(bean.getParameters());
        binding.goodsCate.setText(bean.getCate_name());
        binding.description.setText(bean.getDescription());
        mPhotoList.addAll(bean.getGoods_image());
        postArticleImgAdapter.notifyDataSetChanged();
        mPhotoList2.addAll(bean.getDetail());
        postArticleImgAdapter2.notifyDataSetChanged();
        editID  = bean.getShopid();
        editCateID = bean.getGoodscateid();
        editGoodsId = bean.getId();
    }

    private void initRcv() {

        mPhotoList = new ArrayList<>();
        mPhotoList2 = new ArrayList<>();
        postArticleImgAdapter = new PostArticleImgAdapter(this, mPhotoList);
        postArticleImgAdapter2 = new PostArticleImgAdapter2(this, mPhotoList2);

        binding.rcvImg.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        binding.rcvImg.setAdapter(postArticleImgAdapter);
        binding.rcvImg2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        binding.rcvImg2.setAdapter(postArticleImgAdapter2);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_1:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品名称", "请输入商品名称", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.goodsName.setText(text);
                    }
                }).show();
                break;
            case R.id.rl_2:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品库存", "请输入商品库存数", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.goodsStock.setText(text);
                    }
                }).show();
                break;
            case R.id.rl_3:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品原价", "请输入商品原价", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.orginalPrice.setText(text);
                    }
                }).show();
                break;
            case R.id.rl_4:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品售价", "请输入商品售价", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.saleprice.setText(text);
                    }
                }).show();
                break;
            case R.id.rl_5:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品品牌", "请输入商品品牌", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.brand.setText(text);
                    }
                }).show();
                break;
            case R.id.rl_6:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品规格", "请输入商品规格", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.parameters.setText(text);
                    }
                }).show();
                break;
            case R.id.rl_7:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("商品类别查找", "请输入类别关键字", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        keyword = text;
                        getGoodsCategoryList();

                    }
                }).show();


                break;
            case R.id.rl_8:
                new XPopup.Builder(AddGoodsActivity.this).asInputConfirm("详细信息", "请输入商品的详细信息", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.description.setText(text);
                    }
                }).show();
                break;
            case R.id.imgGoods:
                PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                    UploadUtil.getInstance().postFile(path, uploadBean -> {
                        imgDetailPath = uploadBean.getImage().getUrllarge();
                        runOnUiThread(() -> {
                            Glide.with(this)
                                    .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                    .placeholder(R.mipmap.img_default)
                                    .into(binding.imgGoods);
                        });
                    });
                });
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(binding.goodsName.getText().toString())) {
                    ToastUtils.showShort("请填写商品名称 ");
                    return;
                }
                if (TextUtils.isEmpty(binding.goodsStock.getText().toString())) {
                    ToastUtils.showShort("请填写商品库存");
                    return;
                }
                if (TextUtils.isEmpty(binding.orginalPrice.getText().toString())) {
                    ToastUtils.showShort("请填写商品原价");
                    return;
                }
                if (TextUtils.isEmpty(binding.saleprice.getText().toString())) {
                    ToastUtils.showShort("请填写商品售价");
                    return;
                }
                if (TextUtils.isEmpty(binding.brand.getText().toString())) {
                    ToastUtils.showShort("请填写商品品牌");
                    return;
                }
                if (TextUtils.isEmpty(binding.parameters.getText().toString())) {
                    ToastUtils.showShort("请填写商品规格");
                    return;
                }
                if (TextUtils.isEmpty(binding.goodsCate.getText().toString())) {
                    ToastUtils.showShort("请选择商品类别");
                    return;
                }
                if (TextUtils.isEmpty(binding.description.getText().toString())) {
                    ToastUtils.showShort("请填写商品详细信息");
                    return;
                }

                onSubmit();
                break;
        }

    }


    public void onSubmit() {
        String url = "";
        if (TextUtils.isEmpty(editID)){
            url = "Nearshop/Api/addGoods";

            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                    .readTimeout(60000, TimeUnit.MILLISECONDS)
                    .build();

            String shop_photos = "";
            for (int i = 0; i < mPhotoList.size(); i++) {
                if (i == 0) {
                    shop_photos = mPhotoList.get(i);
                } else {
                    shop_photos = shop_photos + "," + mPhotoList.get(i);
                }
            }
            String shop_photos2 = "";
            for (int i = 0; i < mPhotoList2.size(); i++) {
                if (i == 0) {
                    shop_photos2 = mPhotoList2.get(i);
                } else {
                    shop_photos2 = shop_photos2 + "," + mPhotoList2.get(i);
                }
            }

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uid", UserInfoUtil.getUid()) // 提交内容字段
                    .addFormDataPart("xizuetoken", UserInfoUtil.getToken()) // 提交内容字段
                    .addFormDataPart("id", getIntent().getStringExtra("shopId"))
                    .addFormDataPart("detail", shop_photos2)
                    .addFormDataPart("name", binding.goodsName.getText().toString())
                    .addFormDataPart("stock", binding.goodsStock.getText().toString())
                    .addFormDataPart("orginalprice", binding.orginalPrice.getText().toString())
                    .addFormDataPart("saleprice", binding.saleprice.getText().toString())
                    .addFormDataPart("description", binding.description.getText().toString())
                    .addFormDataPart("parameters", binding.parameters.getText().toString())
                    .addFormDataPart("brand", binding.brand.getText().toString())
                    .addFormDataPart("goodscateid", cateId)
                    .addFormDataPart("goods_image", shop_photos)
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.BASE_HTTPS_URL + url)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ToastUtils.showShort("上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseBody = response.body().string();
                    BaseBean baseBean = ParseUtils.parseJsonObject(responseBody, BaseBean.class);
                    ToastUtils.showShort(baseBean.getState().getMsg());
                    if (baseBean.getState().getCode() == 0) {
//                    UploadBean uploadBean = ParseUtils.parseJsonObject(GsonUtils.toJson(baseBean.getData()), UploadBean.class);
//                    uploadCallback.onSuccess(uploadBean);
                        ToastUtils.showShort("添加成功,待审核...");
                        finish();
                    }
                }
            });
        }else {
            url = "Nearshop/Api/editGoods";

            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                    .readTimeout(60000, TimeUnit.MILLISECONDS)
                    .build();

            String shop_photos = "";
            for (int i = 0; i < mPhotoList.size(); i++) {
                if (i == 0) {
                    shop_photos = mPhotoList.get(i);
                } else {
                    shop_photos = shop_photos + "," + mPhotoList.get(i);
                }
            }
            String shop_photos2 = "";
            for (int i = 0; i < mPhotoList2.size(); i++) {
                if (i == 0) {
                    shop_photos2 = mPhotoList2.get(i);
                } else {
                    shop_photos2 = shop_photos2 + "," + mPhotoList2.get(i);
                }
            }

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uid", UserInfoUtil.getUid()) // 提交内容字段
                    .addFormDataPart("xizuetoken", UserInfoUtil.getToken()) // 提交内容字段
                    .addFormDataPart("id", editGoodsId)
                    .addFormDataPart("shopid", editID)
                    .addFormDataPart("detail", shop_photos2)
                    .addFormDataPart("name", binding.goodsName.getText().toString())
                    .addFormDataPart("stock", binding.goodsStock.getText().toString())
                    .addFormDataPart("orginalprice", binding.orginalPrice.getText().toString())
                    .addFormDataPart("saleprice", binding.saleprice.getText().toString())
                    .addFormDataPart("description", binding.description.getText().toString())
                    .addFormDataPart("parameters", binding.parameters.getText().toString())
                    .addFormDataPart("brand", binding.brand.getText().toString())
                    .addFormDataPart("goodscateid", editCateID)
                    .addFormDataPart("goods_image", shop_photos)
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.BASE_HTTPS_URL + url)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ToastUtils.showShort("上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseBody = response.body().string();
                    BaseBean baseBean = ParseUtils.parseJsonObject(responseBody, BaseBean.class);
                    ToastUtils.showShort(baseBean.getState().getMsg());
                    if (baseBean.getState().getCode() == 0) {
//                    UploadBean uploadBean = ParseUtils.parseJsonObject(GsonUtils.toJson(baseBean.getData()), UploadBean.class);
//                    uploadCallback.onSuccess(uploadBean);
                        ToastUtils.showShort("提交成功,审核中...");
                        finish();
                    }
                }
            });
        }

    }

    class PostArticleImgAdapter extends RecyclerView.Adapter<AddGoodsActivity.PostArticleImgAdapter.MyViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private List<String> mDatas;

        public PostArticleImgAdapter(Context context, List<String> datas) {
            this.mDatas = datas;
            this.mContext = context;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public AddGoodsActivity.PostArticleImgAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PostArticleImgAdapter.MyViewHolder(mLayoutInflater.inflate(R.layout.item_post_activity, parent, false));
        }

        @Override
        public void onBindViewHolder(final AddGoodsActivity.PostArticleImgAdapter.MyViewHolder holder, final int position) {
            if (getItemViewType(position) == 0) { // 普通的视图
                holder.squareCenterFrameLayout.setVisibility(View.GONE);

                Glide.with(AddGoodsActivity.this).load(Constants.PICTURE_HTTPS_URL + mDatas.get(position)).into(holder.imageView);
            } else {
                holder.squareCenterFrameLayout.setVisibility(View.VISIBLE);
            }

            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatas.remove(position);
                    postArticleImgAdapter.notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PictureUtil.getInstance(AddGoodsActivity.this).openGallerySingle(false, false, path -> {
                        UploadUtil.getInstance().postFile(path, uploadBean -> {
                            String license = uploadBean.getImage().getUrllarge();
                            runOnUiThread(() -> {
                                mPhotoList.add(license);
                                postArticleImgAdapter.notifyDataSetChanged();
                            });
                        });
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mDatas.size() >= 9) {
                return 9;
            }
            return mDatas.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (mDatas.size() == 0) {
                // View Type 1代表添加更多的视图
                return 1;
            } else if (mDatas.size() < 9) {
                if (position < mDatas.size()) {
                    // View Type 0代表普通的ImageView视图
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView, deleteImg;
            SquareCenterFrameLayout squareCenterFrameLayout;

            MyViewHolder(View itemView) {
                super(itemView);
                squareCenterFrameLayout = itemView.findViewById(R.id.add_sc);
                imageView = itemView.findViewById(R.id.sdv);
                deleteImg = itemView.findViewById(R.id.iap_btn_del);

            }

        }
    }

    class PostArticleImgAdapter2 extends RecyclerView.Adapter<AddGoodsActivity.PostArticleImgAdapter2.MyViewHolder2> {
        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private List<String> mDatas;

        public PostArticleImgAdapter2(Context context, List<String> datas) {
            this.mDatas = datas;
            this.mContext = context;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public AddGoodsActivity.PostArticleImgAdapter2.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PostArticleImgAdapter2.MyViewHolder2(mLayoutInflater.inflate(R.layout.item_post_activity, parent, false));
        }

        @Override
        public void onBindViewHolder(final AddGoodsActivity.PostArticleImgAdapter2.MyViewHolder2 holder, final int position) {
            if (getItemViewType(position) == 0) { // 普通的视图
                holder.squareCenterFrameLayout.setVisibility(View.GONE);

                Glide.with(AddGoodsActivity.this).load(Constants.PICTURE_HTTPS_URL + mDatas.get(position)).into(holder.imageView);
            } else {
                holder.squareCenterFrameLayout.setVisibility(View.VISIBLE);
            }

            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatas.remove(position);
                    postArticleImgAdapter2.notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PictureUtil.getInstance(AddGoodsActivity.this).openGallerySingle(false, false, path -> {
                        UploadUtil.getInstance().postFile(path, uploadBean -> {
                            String license = uploadBean.getImage().getUrllarge();
                            runOnUiThread(() -> {
                                mPhotoList2.add(license);
                                postArticleImgAdapter2.notifyDataSetChanged();
                            });
                        });
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mDatas.size() >= 9) {
                return 9;
            }
            return mDatas.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (mDatas.size() == 0) {
                // View Type 1代表添加更多的视图
                return 1;
            } else if (mDatas.size() < 9) {
                if (position < mDatas.size()) {
                    // View Type 0代表普通的ImageView视图
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        }

        class MyViewHolder2 extends RecyclerView.ViewHolder {
            ImageView imageView, deleteImg;
            SquareCenterFrameLayout squareCenterFrameLayout;

            MyViewHolder2(View itemView) {
                super(itemView);
                squareCenterFrameLayout = itemView.findViewById(R.id.add_sc);
                imageView = itemView.findViewById(R.id.sdv);
                deleteImg = itemView.findViewById(R.id.iap_btn_del);

            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(StoreHomeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }

    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
    }




}

