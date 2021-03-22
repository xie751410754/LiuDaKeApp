package com.cdxz.liudake.ui.store_manager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.api.UploadUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.StoreInfoDetailBean;
import com.cdxz.liudake.bean.StoreInfoResult;
import com.cdxz.liudake.bean.UploadBean;
import com.cdxz.liudake.databinding.ActivityStoreManagerBinding;
import com.cdxz.liudake.map.MapActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.util.GlideEngine;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.PictureUtil;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.SquareCenterFrameLayout;
import com.cdxz.liudake.view.TimeRangePickerDialog;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StoreManagerActivity extends BaseTitleActivity<ActivityStoreManagerBinding> implements View.OnClickListener {

    String shopId;
    String logo;
    String open_start_time;
    String open_end_time;
    String contact;
    String address;
    String lng;
    String lat;
    String name;
    String description;
    String cat_id;
    String average_money;

    private ArrayList<String> mPhotoList;

    private PostArticleImgAdapter postArticleImgAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_manager;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);

        shopId = getIntent().getStringExtra("shopId");

        binding.rlAvatar.setOnClickListener(this);
        binding.storeLocal.setOnClickListener(this);
        binding.storeType.setOnClickListener(this);
        binding.storeInfo.setOnClickListener(this);
        binding.storeLocal.setOnClickListener(this);
        binding.nameEdit.setOnClickListener(this);
        binding.storeXiaofei.setOnClickListener(this);
        binding.storePhone.setOnClickListener(this);
        binding.storeTime.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.storePinlun.setOnClickListener(this);
        getStoreInfo(shopId);

        initRcv();
    }

    private void initRcv() {

        mPhotoList = new ArrayList<>();
        postArticleImgAdapter = new PostArticleImgAdapter(this, mPhotoList);

        binding.rcvImg.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        binding.rcvImg.setAdapter(postArticleImgAdapter);


        //事件监听
//        binding.rcvImg.addOnItemTouchListener(new OnRecyclerItemClickListener(binding.rcvImg) {
//
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder vh) {
//                int viewType = postArticleImgAdapter.getItemViewType(vh.getAdapterPosition());
////                if (viewType == 1) {
////                    showSelectPictureDialog();
////                } else {
////                    showPictureActionDialog(vh.getAdapterPosition());
////                }
////                openPicture(false);
//
//                PictureUtil.getInstance(StoreManagerActivity.this).openGallerySingle(false, false, path -> {
//                    UploadUtil.getInstance().postFile(path, uploadBean -> {
//                        String license = uploadBean.getImage().getUrllarge();
//                        runOnUiThread(() -> {
//                            mPhotoList.add(license);
//                            postArticleImgAdapter.notifyDataSetChanged();
//                        });
//                    });
//                });
//            }
//
//            @Override
//            public void onItemLongClick(RecyclerView.ViewHolder vh) {
//                //如果item不是最后一个，则执行拖拽
//                if (vh.getLayoutPosition() != mPhotoList.size()) {
////                    itemTouchHelper.startDrag(vh);
//                }
//            }
//        });


    }

    private void getStoreInfo(String shopId) {
        HttpsUtil.getInstance(this).getStoreInfo(shopId, new BaseObserver<BaseBean<StoreInfoDetailBean>>(this, true) {
            @Override
            public void onSuccess(BaseBean<StoreInfoDetailBean> storeInfoResultBaseBean) {
                Glide.with(binding.storeLocal).load(Constants.PICTURE_HTTPS_URL + storeInfoResultBaseBean.getData().getLogo()).into(binding.avatarImg);
                binding.nameEdit.setText(storeInfoResultBaseBean.getData().getName());
                binding.tvStoreType.setText(storeInfoResultBaseBean.getData().getCate_name());
                binding.tvStoreLocal.setText(storeInfoResultBaseBean.getData().getAddress());
                binding.tvStoreTime.setText(storeInfoResultBaseBean.getData().getOpen_start_time() + "-" + storeInfoResultBaseBean.getData().getOpen_end_time());
                binding.tvStoreXiaofei.setText(storeInfoResultBaseBean.getData().getAverage_money());
                binding.tvStoreInfo.setText(storeInfoResultBaseBean.getData().getDescription());
                binding.tvStorePhone.setText(storeInfoResultBaseBean.getData().getContact());

                logo = storeInfoResultBaseBean.getData().getLogo();
                open_start_time = storeInfoResultBaseBean.getData().getOpen_start_time();
                open_end_time = storeInfoResultBaseBean.getData().getOpen_end_time();
                contact = storeInfoResultBaseBean.getData().getContact();
                address = storeInfoResultBaseBean.getData().getAddress();
                lng = storeInfoResultBaseBean.getData().getLng();
                lat = storeInfoResultBaseBean.getData().getLat();
                name = storeInfoResultBaseBean.getData().getName();
                description = storeInfoResultBaseBean.getData().getDescription();
                cat_id = storeInfoResultBaseBean.getData().getCat_id();
                average_money = storeInfoResultBaseBean.getData().getAverage_money();

                if (!TextUtils.isEmpty(storeInfoResultBaseBean.getData().getShop_photos())) {
                    String[] arr = storeInfoResultBaseBean.getData().getShop_photos().split(",");
                    List<String> list = Arrays.asList(arr);
                    mPhotoList.addAll(list);
                    postArticleImgAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    public void submit(String file, UploadUtil.OnUploadCallback uploadCallback) {
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

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid", UserInfoUtil.getUid()) // 提交内容字段
                .addFormDataPart("xizuetoken", UserInfoUtil.getToken()) // 提交内容字段
                .addFormDataPart("id", shopId)
                .addFormDataPart("logo", file) // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")和我们之前说的那个type其实是一样的
                .addFormDataPart("contact", contact)
                .addFormDataPart("address", address)
                .addFormDataPart("lng", lng)
                .addFormDataPart("lat", lat)
                .addFormDataPart("name", name)
                .addFormDataPart("open_start_time",open_start_time)
                .addFormDataPart("open_end_time",open_end_time)
                .addFormDataPart("description", description)
                .addFormDataPart("cat_id", cat_id)
                .addFormDataPart("average_money", average_money)
                .addFormDataPart("shop_photos", shop_photos)
                .build();
        Request request = new Request.Builder()
                .url(Constants.BASE_HTTPS_URL + "Shop/Api/updateInfo")
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
                    ToastUtils.showShort("提交成功");
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.rl_avatar://logo
//                openPicture(true);
                PictureUtil.getInstance(StoreManagerActivity.this).openGallerySingle(false, false, path -> {
                    UploadUtil.getInstance().postFile(path, uploadBean -> {
                        String license = uploadBean.getImage().getUrllarge();
                        runOnUiThread(() -> {
                            logo = license;
                            Glide.with(StoreManagerActivity.this).load(Constants.PICTURE_HTTPS_URL + logo).into((ImageView) findViewById(R.id.avatar_img));
                        });
                    });
                });
                break;
            case R.id.name_edit://名称
                new XPopup.Builder(StoreManagerActivity.this).asInputConfirm("商铺名称", "请输入商铺名称", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.nameEdit.setText(text);
                        name = text;
                    }
                }).show();
                break;
            case R.id.store_local://店铺地址
                startActivityForResult(new Intent(StoreManagerActivity.this, MapActivity.class), 0);
                break;
            case R.id.store_type://店铺类别
                intent.setClass(StoreManagerActivity.this, StoreClassActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.store_info://店铺简介
                new XPopup.Builder(StoreManagerActivity.this).asInputConfirm("商铺简介", "请输入简介", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.tvStoreInfo.setText(text);
                        description = text;
                    }
                }).show();
                break;
            case R.id.store_time://营业时间
                TimeRangePickerDialog dialog = new TimeRangePickerDialog(StoreManagerActivity.this, "07:00 - 09:00", new TimeRangePickerDialog.ConfirmAction() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick(String startAndEndTime, String start, String end) {
                        binding.tvStoreTime.setText(startAndEndTime);
                        open_start_time = start;
                        open_end_time = end;
                    }
                });
                dialog.show();
                break;
            case R.id.store_xiaofei://人均消费
                new XPopup.Builder(StoreManagerActivity.this).asInputConfirm("人均消费", "请人均消费", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.tvStoreXiaofei.setText(text);
                        average_money = text;
                    }
                }).show();
                break;
            case R.id.store_pinlun:
                Bundle bundle3 = new Bundle();
                bundle3.putString("shopId",shopId);
                startActivity(StoreCommentActivity.class,bundle3);
                break;
            case R.id.store_phone://联系电话
                new XPopup.Builder(StoreManagerActivity.this).asInputConfirm("联系电话", "请联系电话", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort("内容不能为空");
                            return;
                        }
                        binding.tvStorePhone.setText(text);
                        contact = text;
                    }
                }).show();
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(logo)) {
                    ToastUtils.showShort("请上传店铺头像");
                    return;
                }
                if (TextUtils.isEmpty(open_start_time)||TextUtils.isEmpty(open_end_time)){
                    ToastUtils.showShort("请设置营业时间");
                    return;
                }
                if (TextUtils.isEmpty(contact)) {
                    ToastUtils.showShort("请填写店铺电话");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    ToastUtils.showShort("请选择店铺地址");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showShort("请填写店铺名称");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    ToastUtils.showShort("请填写店铺简介");
                    return;
                }
                if (TextUtils.isEmpty(cat_id)) {
                    ToastUtils.showShort("请选择店铺分类");
                    return;
                }
                if (TextUtils.isEmpty(average_money)) {
                    ToastUtils.showShort("请填写店铺人均消费");
                    return;
                }

                LogUtils.e("xzl","lng="+lng+"lat"+lat);
                submit(logo, new UploadUtil.OnUploadCallback() {
                    @Override
                    public void onSuccess(UploadBean uploadBean) {

                    }
                });
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0://定位
                    lat = data.getStringExtra("lat");
                    lng = data.getStringExtra("lng");
                    address = data.getStringExtra("address");
                    binding.tvStoreLocal.setText(address);
                    break;
                case 1://店铺类别
                    cat_id = data.getStringExtra("cat_id");
                    binding.tvStoreType.setText(data.getStringExtra("cat_name"));
                    break;
            }
        }
    }

    private void openPicture(boolean isLogo) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .compress(true)
                .minimumCompressSize(1024)
                .queryMaxFileSize(2048)
                .selectionMode(PictureConfig.SINGLE)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调
                        // 例如 LocalMedia 里面返回五种path
                        // 1.media.getPath(); 原图path，但在Android Q版本上返回的是content:// Uri类型
                        // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                        // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                        // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                        // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路
                        //径；注意：.isAndroidQTransform(false);此字段将返回空
                        // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                        for (LocalMedia media : result) {
//                            Log.i(TAG, "压缩::" + media.getCompressPath());
//                            Log.i(TAG, "原图::" + media.getPath());
//                            Log.i(TAG, "裁剪::" + media.getCutPath());
//                            Log.i(TAG, "是否开启原图::" + media.isOriginal());
//                            Log.i(TAG, "原图路径::" + media.getOriginalPath());
//                            Log.i(TAG, "Android Q 特有Path::" + media.getAndroidQToPath());
                            // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，
                            if (isLogo) {
                                Glide.with(binding.avatarImg).load(media.getCompressPath()).into(binding.avatarImg);
//                                logo = new File(media.getCompressPath());
                            } else {
                                mPhotoList.add(media.getCompressPath());
                                postArticleImgAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }
                });
    }

    interface DragListener {
        /**
         * 用户是否将 item拖动到删除处，根据状态改变颜色
         *
         * @param delete
         */
        void deleteState(boolean delete);

        /**
         * 是否于拖拽状态
         *
         * @param start
         */
        void dragState(boolean start);

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         */
        void clearView();
    }

    public static class MyCallBack extends ItemTouchHelper.Callback {

        private int dragFlags;
        private int swipeFlags;
        private View removeView;
        private PostArticleImgAdapter adapter;
        private List<String> images;//图片经过压缩处理
        private List<String> originImages;//图片没有经过处理，这里传这个进来是为了使原图片的顺序与拖拽顺序保持一致
        private boolean up;//手指抬起标记位
        private DragListener dragListener;

        public MyCallBack(View tv, PostArticleImgAdapter adapter, List<String> images, List<String> originImages) {
            removeView = tv;
            this.adapter = adapter;
            this.images = images;
            this.originImages = originImages;
        }

        /**
         * 设置item是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
         *
         * @param recyclerView
         * @param viewHolder
         * @return
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //判断 recyclerView的布局管理器数据
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {//设置能拖拽的方向
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = 0;//0则不响应事件
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        /**
         * 当用户从item原来的位置拖动可以拖动的item到新位置的过程中调用
         *
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
            int toPosition = target.getAdapterPosition();//得到目标position
            if (toPosition == images.size() || images.size() == fromPosition) {
                return true;
            }
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(images, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(images, i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        /**
         * 设置是否支持长按拖拽
         *
         * @return
         */
        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        /**
         * @param viewHolder
         * @param direction
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         *
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            adapter.notifyDataSetChanged();
            initData();
            if (dragListener != null) {
                dragListener.clearView();
            }
        }

        /**
         * 重置
         */
        private void initData() {
            if (dragListener != null) {
                dragListener.deleteState(false);
                dragListener.dragState(false);
            }
            up = false;
        }

        /**
         * 自定义拖动与滑动交互
         *
         * @param c
         * @param recyclerView
         * @param viewHolder
         * @param dX
         * @param dY
         * @param actionState
         * @param isCurrentlyActive
         */
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (null == dragListener) {
                return;
            }
            int[] location = new int[2];
            viewHolder.itemView.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
            if (location[1] + viewHolder.itemView.getHeight() > removeView.getTop()) {//拖到删除处
                dragListener.deleteState(true);
                if (up) {//在删除处放手，则删除item
                    viewHolder.itemView.setVisibility(View.INVISIBLE);//先设置不可见，如果不设置的话，会看到viewHolder返回到原位置时才消失，因为remove会在viewHolder动画执行完成后才将viewHolder删除
                    images.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    initData();
                    return;
                }
            } else {//没有到删除处
                if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {//如果viewHolder不可见，则表示用户放手，重置删除区域状态
                    dragListener.dragState(false);
                }
                dragListener.deleteState(false);
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        /**
         * 当长按选中item的时候（拖拽开始的时候）调用
         *
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && dragListener != null) {
                dragListener.dragState(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 设置手指离开后ViewHolder的动画时间，在用户手指离开后调用
         *
         * @param recyclerView
         * @param animationType
         * @param animateDx
         * @param animateDy
         * @return
         */
        @Override
        public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
            //手指放开
            up = true;
            return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
        }

        void setDragListener(DragListener dragListener) {
            this.dragListener = dragListener;
        }

    }

    class PostArticleImgAdapter extends RecyclerView.Adapter<PostArticleImgAdapter.MyViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private List<String> mDatas;

        public PostArticleImgAdapter(Context context, List<String> datas) {
            this.mDatas = datas;
            this.mContext = context;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_post_activity, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if (getItemViewType(position) == 0) { // 普通的视图
                holder.squareCenterFrameLayout.setVisibility(View.GONE);

                Glide.with(StoreManagerActivity.this).load(Constants.PICTURE_HTTPS_URL + mDatas.get(position)).into(holder.imageView);
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
                    PictureUtil.getInstance(StoreManagerActivity.this).openGallerySingle(false, false, path -> {
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
            ImageView imageView,deleteImg;
            SquareCenterFrameLayout squareCenterFrameLayout;

            MyViewHolder(View itemView) {
                super(itemView);
                squareCenterFrameLayout = itemView.findViewById(R.id.add_sc);
                imageView = itemView.findViewById(R.id.sdv);
                deleteImg = itemView.findViewById(R.id.iap_btn_del);

            }

        }
    }

    public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private GestureDetectorCompat mGestureDetector;
        private RecyclerView recyclerView;

        public OnRecyclerItemClickListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (mGestureDetector.onTouchEvent(e)) {
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public abstract void onItemClick(RecyclerView.ViewHolder vh);

        public abstract void onItemLongClick(RecyclerView.ViewHolder vh);

        private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    onItemClick(vh);
                    return true;
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    onItemLongClick(vh);
                }
            }
        }
    }

}
