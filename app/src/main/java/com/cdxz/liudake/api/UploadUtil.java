package com.cdxz.liudake.api;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.UploadBean;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UploadUtil {
    private OkHttpClient okHttpClient;

    private UploadUtil() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * 使用静态内部类的方式实现单例模式
     */
    private static class UploadUtilInstance {
        private static final UploadUtil INSTANCE = new UploadUtil();
    }

    public static UploadUtil getInstance() {
        return UploadUtilInstance.INSTANCE;
    }

    public void postFile(String path, OnUploadCallback uploadCallback) {
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        File file = new File(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid", UserInfoUtil.getUid()) // 提交内容字段
                .addFormDataPart("xizuetoken", UserInfoUtil.getToken()) // 提交内容字段
                .addFormDataPart("upload_file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file)) // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")和我们之前说的那个type其实是一样的
                .build();
        Request request = new Request.Builder()
                .url(Constants.BASE_HTTPS_URL + "shop/api/upload")
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
                LogUtils.e("上传结果 = " + responseBody);
                BaseBean baseBean = ParseUtils.parseJsonObject(responseBody, BaseBean.class);
                ToastUtils.showShort(baseBean.getState().getMsg());
                if (baseBean.getState().getCode() == 0) {
                    UploadBean uploadBean = ParseUtils.parseJsonObject(GsonUtils.toJson(baseBean.getData()), UploadBean.class);
                    uploadCallback.onSuccess(uploadBean);
                }
            }
        });
    }

    public interface OnUploadCallback {
        void onSuccess(UploadBean uploadBean);
    }
}
