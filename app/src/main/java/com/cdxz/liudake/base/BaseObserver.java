package com.cdxz.liudake.base;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.LiudakeApplication;
import com.cdxz.liudake.pop.PopPayPwd;
import com.cdxz.liudake.ui.login.LoginActivity;
import com.cdxz.liudake.ui.my.SetPayPwdActivity;
import com.google.gson.JsonParseException;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T extends BaseBean> implements Observer<T> {
    private static final String TAG = BaseObserver.class.getSimpleName();
    private Context context;
    private boolean isShow;
    private LoadingPopupView loadingPopupView;

    public BaseObserver(Context context, boolean isShow) {
        this.context = context;
        this.isShow = isShow;
    }

    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.e(TAG, "onSubscribe");
        if (isShow) {
            loadingPopupView = (LoadingPopupView) new XPopup.Builder(ActivityUtils.getTopActivity())
                    .hasShadowBg(false)
                    .dismissOnTouchOutside(false)
                    .dismissOnBackPressed(false)
                    .asLoading()
                    .show();
            loadingPopupView.post(() -> LogUtils.e("onSubscribe"));
        }
    }

    @Override
    public void onNext(T t) {
        LogUtils.e(TAG, "onNext = " + GsonUtils.toJson(t));
        if (isShow) {
            loadingPopupView.dismissWith(() -> LogUtils.e("onNext"));
        }
        if (t.getState() == null) {
            onFail("null");
            return;
        }
        if (t.getState().getCode() == 0 || t.getState().getCode() == 2) {
            onSuccess(t);
        } else if (t.getState().getCode() == 8) {//登录失效
            onReLogin();
        } else if (t.getState().getCode() == 9) {//未设置支付密码
            new XPopup.Builder(ActivityUtils.getTopActivity())
                    .asConfirm("提示", "您未设置支付密码，是否去设置？", new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            SetPayPwdActivity.startSetPayPwdActivity(ActivityUtils.getTopActivity());
                        }
                    }).show();
        } else if (t.getState().getCode() == 1) {
            ToastUtils.showShort(t.getState().getMsg());
            onFail(t.getState().getMsg());
        } else {
            onFail(t.getState().getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(TAG, "onError = " + GsonUtils.toJson(e));
        if (isShow) {
            loadingPopupView.dismissWith(() -> LogUtils.e("onError"));
        }
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
        LogUtils.e(TAG, "onComplete");
    }

    public abstract void onSuccess(T t);

    public void onReLogin() {
        SPUtils.getInstance().put(Constants.IS_LOGIN, false);
        ActivityUtils.finishAllActivities();
        ActivityUtils.startActivity(LoginActivity.class);
    }

    public void onFail(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 网络请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case BAD_NETWORK:
                ToastUtils.showShort("网络问题");
                break;
            case CONNECT_ERROR:
                ToastUtils.showShort("连接错误");
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.showShort("连接超时");
                break;
            case PARSE_ERROR:
                ToastUtils.showShort("解析数据失败");
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }

    /**
     * 网络请求失败原因
     */
    public enum ExceptionReason {
        //网络问题
        BAD_NETWORK,
        //连接错误
        CONNECT_ERROR,
        //连接超时
        CONNECT_TIMEOUT,
        //解析数据失败
        PARSE_ERROR,
        //未知错误
        UNKNOWN_ERROR,
    }
}
