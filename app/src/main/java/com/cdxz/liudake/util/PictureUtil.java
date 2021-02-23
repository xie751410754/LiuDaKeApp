package com.cdxz.liudake.util;

import android.app.Activity;
import android.os.Build;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

/**
 * 选择图片，拍照，单选多选工具类
 */
public class PictureUtil {

    public interface OnPictureListener {
        void onResult(String path);
    }

    private Activity mActivity;
    private PictureSelector selector;

    private static PictureUtil instance;

    public PictureUtil(Activity mActivity) {
        this.mActivity = mActivity;
        selector = PictureSelector.create(mActivity);
    }

    public static PictureUtil getInstance(Activity mActivity) {
        if (null == instance) {
            synchronized (PictureUtil.class) {
                if (null == instance) {
                    instance = new PictureUtil(mActivity);
                }
            }
        }
        return instance;
    }

    /**
     * 打开相机
     *
     * @param isCrop            是否裁剪 true:裁剪 false:不裁剪
     * @param isHead            是否是上传头像
     * @param onPictureListener
     */
    public void openCamera(boolean isCrop, boolean isHead, OnPictureListener onPictureListener) {
        PictureSelectionModel model = new PictureSelectionModel(selector, PictureMimeType.ofImage(), true);
        model.imageEngine(GlideEngine.createGlideEngine());
        model.isCompress(true);
        model.isEnableCrop(isCrop);
        if (isCrop && isHead) {
            model.withAspectRatio(1, 1);
        }
        model.forResult(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
//                        List<LocalMedia> selectList = result;
                for (LocalMedia media : result) {
                    if (isCrop) {
                        if (media.isCut()) {
                            onPictureListener.onResult(media.getCutPath());
                        } else {
                            ToastUtils.showShort("图片获取失败");
                        }
                    } else {
                        if (media.isCompressed()) {
                            onPictureListener.onResult(media.getCompressPath());
                        } else {
                            ToastUtils.showShort("图片获取失败");
                        }
                    }
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 相册单选
     *
     * @param isCrop            是否裁剪 true:裁剪 false:不裁剪
     * @param isHead            是否是上传头像
     * @param onPictureListener
     */
    public void openGallerySingle(boolean isCrop, boolean isHead, OnPictureListener onPictureListener) {
        PictureSelectionModel model = new PictureSelectionModel(selector, PictureMimeType.ofImage(), false);

        model.imageEngine(GlideEngine.createGlideEngine());
        model.isGif(true);
        model.selectionMode(PictureConfig.SINGLE);
        model.isCompress(true);
        model.isEnableCrop(isCrop);
        if (isCrop && isHead) {
            model.withAspectRatio(1, 1);
        }
        model.isWeChatStyle(true);
        model.forResult(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
//                        List<LocalMedia> selectList = result;
                for (LocalMedia media : result) {
                    if (isCrop) {
                        if (media.isCut()) {
                            onPictureListener.onResult(media.getCutPath());
                        } else {
                            ToastUtils.showShort("图片获取失败");
                        }
                    } else {
                        if (media.isCompressed()) {
                            onPictureListener.onResult(media.getCompressPath());
                        } else {
                            ToastUtils.showShort("图片获取失败");
                        }
                    }
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 相册多选
     *
     * @param num
     * @param isCrop            是否裁剪 true:裁剪 false:不裁剪
     * @param isHead            是否是上传头像
     * @param onPictureListener
     */
    public void openGalleryMultiple(int num, boolean isCrop, boolean isHead, OnPictureListener onPictureListener) {
        PictureSelectionModel model = new PictureSelectionModel(selector, PictureMimeType.ofImage(), false);

        model.imageEngine(GlideEngine.createGlideEngine());
        model.selectionMode(PictureConfig.MULTIPLE);
        model.maxSelectNum(num == 0 ? 1 : num);
        model.isCompress(true);
        model.isEnableCrop(isCrop);
        if (isCrop && isHead) {
            model.withAspectRatio(1, 1);
        }
        model.isWeChatStyle(true);
        model.forResult(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
//                        List<LocalMedia> selectList = result;
                for (LocalMedia media : result) {
                    if (isCrop) {
                        if (media.isCut()) {
                            onPictureListener.onResult(media.getCutPath());
                        } else {
                            ToastUtils.showShort("图片获取失败");
                        }
                    } else {
                        if (media.isCompressed()) {
                            onPictureListener.onResult(media.getCompressPath());
                        } else {
                            ToastUtils.showShort("图片获取失败");
                        }
                    }
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
