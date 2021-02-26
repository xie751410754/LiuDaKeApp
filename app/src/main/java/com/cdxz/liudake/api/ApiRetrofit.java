package com.cdxz.liudake.api;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.util.UserInfoUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    public final String BASE_SERVER_URL = Constants.BASE_HTTPS_URL;

    private static ApiRetrofit apiRetrofit;
    private Retrofit retrofit;
    private OkHttpClient client;
    private ApiServer apiServer;

    private String TAG = "ApiRetrofit";

    /**
     * 请求访问quest
     * response拦截器
     */
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader("xizuetoken", UserInfoUtil.getToken())
//                    .addHeader("accept", "*/*")
//                    .addHeader("Content-Type", "application/json")
                    .build();
            LogUtils.e(TAG, UserInfoUtil.getToken());
            LogUtils.e(TAG, UserInfoUtil.getUid());
            return chain.proceed(request);
        }
    };


    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            message = toEmptyIfNull(message);
            if (message.startsWith("{") && message.endsWith("}")) {
                //此处debug时可以打断点，copy json
                final String json = unicode2Chinese(message);
                message = !TextUtils.isEmpty(json) ? json : message;
            } else if (message.contains("=")) {
                try {
                    message = URLDecoder.decode(message, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Platform.get().log(message,Platform.INFO,null);
        }

    });

    public static String toEmptyIfNull(String s) {
        return s == null ? "" : s;

    }

    /**
     * 字符串中包含unicode的部分转中文
     *
     * @param src unicode
     * @return 中文
     */
    public static String unicode2Chinese(String src) {
        //{"error_code":"1",
        // "message":"\u64cd\u4f5c\u6210\u529f",
        // "data":[
        // {"id":"1","title":"\u6700\u65b0\u6d3b\u52a8\uff0190\u540e\u8f70\u8db4\u6d3b\u52a8\uff01"},
        // {"id":"2","title":"\u6700\u6700\u6700\u65b0\u6d3b\u52a8\uff0110\u540e\u7ec4\u5efa\u7684\u8f70\u8db4\uff01"}]}
        if (TextUtils.isEmpty(src)) {
            return src;
        }
        try {
            int index = src.indexOf("\\u");
            while (index != -1) {
                //\u64cd
                String unicodeStr = src.substring(index, index + 6);
                //64cd
                String hexStr = src.substring(index + 2, index + 6);
                int intVal = Integer.parseInt(hexStr, 16);
                String chineseStr = Character.valueOf((char) intVal).toString();
                src = src.replace(unicodeStr, chineseStr);
                index = src.indexOf("\\u");
            }
        } catch (Exception e) {
            return null;
        }
        return src;
    }


    public ApiRetrofit() {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        client = new OkHttpClient.Builder()
                //添加log拦截器
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        apiServer = retrofit.create(ApiServer.class);
    }

    public static ApiRetrofit getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }

    public ApiServer getApiService() {
        return apiServer;
    }
}
