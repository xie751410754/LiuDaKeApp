package com.cdxz.liudake.api;

import android.content.Context;
import android.os.Build;

import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.AdvertDto;
import com.cdxz.liudake.bean.IndexAllInfoBean;
import com.cdxz.liudake.bean.MessageListBean;
import com.cdxz.liudake.bean.RadioDto;
import com.cdxz.liudake.bean.ShopBalance;
import com.cdxz.liudake.bean.StoreComment;
import com.cdxz.liudake.bean.StoreGuideList;
import com.cdxz.liudake.bean.StoreInfoDetailBean;
import com.cdxz.liudake.bean.StoreInfoResult;
import com.cdxz.liudake.bean.StoreMessageListBean;
import com.cdxz.liudake.bean.StoreOpenStatus;
import com.cdxz.liudake.bean.StoreQRBean;
import com.cdxz.liudake.bean.StoreTodayInviteBean;
import com.cdxz.liudake.bean.StoreTodaySettlementBean;
import com.cdxz.liudake.bean.StoreTodaySettlementCashBean;
import com.cdxz.liudake.bean.StoreUnderMsgResult;
import com.cdxz.liudake.util.UserInfoUtil;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.Query;

public class HttpsUtil {

    private static HttpsUtil mHttpsUtil = null;
    private Context context;

    private HttpsUtil(Context context) {
        this.context = context;
    }

    public static HttpsUtil getInstance(Context context) {
        if (mHttpsUtil == null) {
            synchronized (HttpsUtil.class) {
                mHttpsUtil = new HttpsUtil(context);
            }
        }
        return mHttpsUtil;
    }



    public void getMerchant(int region,int page,int pageSize,HttpsCallback callback){

        ApiRetrofit.getInstance().getApiService()
                .getMerchant(region,page,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {

                        callback.onResult(baseBean);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        callback.onResult(null);

                    }
                });



    }

    /**
     * 检查更新
     */
    public void checkUpdate(String version,HttpsCallback callback){

        ApiRetrofit.getInstance().getApiService()
                .checkUpdate(version,"android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context,true){

                    @Override
                    public void onSuccess(BaseBean baseBean) {

                        callback.onResult(baseBean);

                    }
                });


    }

    /**
     * 注册
     *
     * @param phone
     * @param callback
     */
    public void register(String name,String phone, String password, String code, String invitecode, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .register(name,phone, password, code, invitecode,
                        Build.VERSION.RELEASE, Build.MODEL, DeviceUtils.getUniqueDeviceId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
//                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response);
                    }
                });
    }


    /**
     * 获取验证码
     *
     * @param phone    0:注册获取验证码
     *                 1:找回密码时获取验证码
     *                 2:绑定手机号
     *                 3:更改手机号
     *                 4:消费熊猫币
     *                 5:获取短信验证码
     *                 6:商家入驻
     *                 7:设置支付密码
     * @param type
     * @param callback
     */
    public void getSmsCode(String phone, int type, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getSmsCode(phone, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response);
                    }
                });
    }

    /**
     * 重置密码
     *
     * @param phone
     * @param password
     * @param code
     * @param callback
     */
    public void resetPassword(String phone, String password, String code, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .resetPassword(phone, password, code, UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response);
                    }
                });
    }

    /**
     * 密码登录
     *
     * @param phone
     * @param password
     * @param callback
     */
    public void loginByPwd(String phone, String password, String jpushID,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .loginByPwd(phone, password, DeviceUtils.getUniqueDeviceId(),jpushID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }
    /**
     * 微信登录
     *
     * @param callback
     */
        public void loginByWechat(String openid,String unionid,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .loginByWechat(UserInfoUtil.getUid(),UserInfoUtil.getToken(),openid,unionid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 搜索
     *
     * @param keyword
     * @param type
     * @param searchtype
     * @param lng
     * @param lat
     * @param comid
     * @param page
     * @param callback
     */
    public void search(String keyword, int type, int searchtype, String lng, String lat,
                       String comid, int page, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .search(UserInfoUtil.getUid(), keyword, type, searchtype, lng, lat, comid, Constants.LIST_SIZE, page, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 热门搜索
     *
     * @param type
     * @param callback
     */
    public void hotSearch(int type, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .hotSearch(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    public void getPopupAd(int type, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getPopupAd(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 热门搜索
     *
     * @param callback
     */
    public void historySearch(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .historySearch(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 清空搜索
     *
     * @param callback
     */
    public void clearSearch(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .clearSearch(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 个人信息
     *
     * @param callback
     */
    public void userInfo(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .userInfo(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 首页
     *
     * @param callback
     */
    public void homeIndex(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .homeIndex(UserInfoUtil.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 商品详情
     *
     * @param goodsid
     * @param lng
     * @param lat
     * @param size
     * @param city
     * @param callback
     */
    public void goodsDetail(String goodsid, String lng, String lat, String size, String city,String cuxiao_id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .goodsDetail(UserInfoUtil.getUid(), goodsid, lng, lat, size, city, UserInfoUtil.getToken(),cuxiao_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 加入购物车
     *
     * @param size
     * @param id
     * @param count
     * @param callback
     */
    public void orderAddCar(String size, String id, int count, String cuxiao_id ,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .orderAddCar(UserInfoUtil.getUid(), size, id, count, cuxiao_id ,UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 购物车列表
     *
     * @param callback
     */
    public void getCarList(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getCarList(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 商品分类列表
     *
     * @param callback
     */
    public void categoryLists(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .categoryLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 商品列表
     *
     * @param cateid
     * @param brand
     * @param keywords
     * @param shopid
     * @param shopcateid
     * @param callback
     */
    public void goodsList(int page, String activity_id, String guxiao_id, String cateid, String brand, String keywords, String shopid, String shopcateid,
                          String lat, String lng, int sort, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .goodsList(page, Constants.LIST_SIZE, activity_id, guxiao_id, cateid, brand, keywords, shopid, shopcateid, lat, lng, sort, UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    /**
     * 活动商品列表
     *
     * @param cateid
     * @param brand
     * @param keywords
     * @param shopid
     * @param shopcateid
     * @param callback
     */
    public void activeGoodsList(int page, String activity_id, String guxiao_id, String cateid, String brand, String keywords, String shopid, String shopcateid,
                          String lat, String lng, int sort, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .activeGoodsList(page, Constants.LIST_SIZE, activity_id, guxiao_id, cateid, brand, keywords, shopid, shopcateid, lat, lng, sort, UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    public void disscountGoodsList(int page, String activity_id, String guxiao_id, String cateid, String brand, String keywords, String shopid, String shopcateid,
                          String lat, String lng, int sort, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .disscountGoodsList(page, Constants.LIST_SIZE, activity_id, guxiao_id, cateid, brand, keywords, shopid, shopcateid, lat, lng, sort, UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 添加收货地址
     *
     * @param name
     * @param phone
     * @param detail
     * @param lat
     * @param lng
     * @param selected
     * @param province
     * @param city
     * @param district
     * @param callback
     */
    public void addAddress(String name, String phone, String detail, String lat,
                           String lng, int selected, String province, String city, String district, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .addAddress(UserInfoUtil.getUid(), name, phone, detail, lat, lng, selected, province, city, district, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 收货地址列表
     *
     * @param callback
     */
    public void addressList(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .addressList(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 修改收货地址
     *
     * @param id
     * @param name
     * @param phone
     * @param detail
     * @param lat
     * @param lng
     * @param selected
     * @param province
     * @param city
     * @param district
     * @param callback
     */
    public void editAddress(String id, String name, String phone, String detail, String lat,
                            String lng, int selected, String province, String city, String district, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .editAddress(UserInfoUtil.getUid(), id, name, phone, detail, lat, lng, selected, province, city, district, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @param callback
     */
    public void deleteAddress(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .deleteAddress(UserInfoUtil.getUid(), id, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }
    public void deleteTuiJianGoods(String shopid,String goodId ,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .deleteTuiJianGoods(shopid,goodId,UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 积分账单
     *
     * @param type
     * @param time
     * @param callback
     */
    public void integralBill(int type, String time, int page, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .integralBill(UserInfoUtil.getUid(), type, time, page, Constants.LIST_SIZE, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 红米账单
     *
     * @param type
     * @param time
     * @param callback
     */
    public void redmiBill(int type, String time, int page, String shopId, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .redmiBill(UserInfoUtil.getUid(), type, time, page, Constants.LIST_SIZE, shopId, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 选择购物商品
     *
     * @param id
     * @param action
     * @param type
     * @param callback
     */
    public void selectCar(String id, int action, int type, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .selectCar(UserInfoUtil.getUid(), id, action, type, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 购物车结算
     *
     * @param pay_type   1商城红米 2分享红米 3交易红米 4支付宝 5微信
     * @param is_balance 是否余额抵扣 1-是 0-否
     * @param is_gold    是否积分抵扣 1-是 0-否
     * @param callback
     */
    public void settlement(int pay_type, int is_balance, int is_gold, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .settlement(UserInfoUtil.getUid(), pay_type, is_balance, is_gold, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 提交订单
     *
     * @param is_balance   是否余额抵扣，0否，1是
     * @param is_gold      是否熊猫币抵扣，0否，1是
     * @param pay_type     1商城红米 2分享红米 3交易红米
     * @param pay_password 支付密码
     * @param callback
     */
    public void submitOrder(int is_balance, int is_gold, String addressid, int pay_type, String pay_password, HttpsCallback callback) {
        LogUtils.e("支付密码 = " + pay_password);
        ApiRetrofit.getInstance().getApiService()
                .submitOrder(UserInfoUtil.getUid(), is_balance, is_gold, addressid, pay_type, pay_password, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 订单列表
     *
     * @param action   0-全部 1-待付款(status=0) 2-待发货(status=1) 3-待收货(status=2) 4-待评价(status=3) 5-换货(status=8）6-退货退款(status=4)
     * @param page
     * @param shopid
     * @param callback
     */
    public void orderList(int action, int page, String shopid, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .orderList(UserInfoUtil.getUid(), action, page, Constants.LIST_SIZE, shopid, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 个人中心
     *
     * @param callback
     */
    public void userIndex(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .userIndex(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 订单详情
     *
     * @param id
     * @param callback
     */
    public void orderDetail(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .orderDetail(UserInfoUtil.getUid(), id, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 邀请码
     *
     * @param callback
     */
    public void invitQrcode(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .invitQrcode(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 购物车数量改变
     *
     * @param id
     * @param count
     * @param callback
     */
    public void adjustCount(String id, int count, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .adjustCount(UserInfoUtil.getUid(), id, count, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 店铺管理首页
     */
    public void storeManagerIndex(String shopId, BaseObserver<BaseBean<IndexAllInfoBean>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .storeIndex(UserInfoUtil.getUid(), shopId, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 店铺管理首页
     */
    public void storeIndexUnderMsg(String shopId, BaseObserver<BaseBean<StoreUnderMsgResult>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .storeIndexUnderMsg(UserInfoUtil.getUid(), shopId, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 店铺管理首页 查询状态
     */
    public void changeOpenStatus(String shopId, BaseObserver<BaseBean<StoreOpenStatus>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .changeOpenStatus(UserInfoUtil.getUid(), shopId, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 店铺管理首页 修改状态 (1为营业，0为不营业)
     */
    public void changeOpenStatus(String shopId, int status, BaseObserver<BaseBean<StoreOpenStatus>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .changeOpenStatus(UserInfoUtil.getUid(), shopId, UserInfoUtil.getToken(), status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 获取店铺信息
     *
     * @param shopId
     * @param baseObserver
     */
    public void getStoreInfo(String shopId, BaseObserver<BaseBean<StoreInfoDetailBean>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreInfo(UserInfoUtil.getUid(), shopId, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);

    }

    /**
     * 生活圈
     *
     * @param lng
     * @param lat
     * @param page
     * @param cat_id
     * @param fastcateid
     * @param sort
     * @param callback
     */
    public void nearShop(String lng, String lat, int page, String cat_id, String fastcateid, int sort, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .nearShop(UserInfoUtil.getUid(), lng, lat, Constants.LIST_SIZE, page, 2, cat_id, fastcateid, sort, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, false) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });

    }
    /**
     *
     * @param page
     * @param callback
     */
    public void nearShopGoodsList( int page, String id, int status ,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .nearShopGoodsList( Constants.LIST_SIZE, page, id,status,UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });

    }
    /**
     *
     * @param callback
     */
    public void getGoodsInfo( String shopId, String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getGoodsInfo(shopId, id, UserInfoUtil.getToken(),UserInfoUtil.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });

    }

    /**
     * 搜索生活圈
     *
     * @param keywords
     * @param page
     * @param sort     1综合排序（距离） 2销量 3价格从低到高 4价格从高到低
     * @param cat_id
     * @param lng
     * @param lat
     * @param callback
     */
    public void searchNearShop(String keywords, int page, int sort, String cat_id, String lng, String lat, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .searchNearShop(UserInfoUtil.getUid(), keywords, page, sort, cat_id, lng, lat, Constants.LIST_SIZE, 2, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });

    }

    /**
     * 生活圈点击详情
     *
     * @param id
     * @param callback
     */
    public void shopDetail(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .shopDetail(UserInfoUtil.getUid(), id, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });

    }
    /**
     * 生活圈评价
     *
     * @param id
     * @param callback
     */
    public void nearShopDetail(String id,int page,int commentType, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .NearshopDetail(UserInfoUtil.getUid(), id, UserInfoUtil.getToken(),page,Constants.LIST_SIZE,commentType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });

    }

    /**
     * 获取店铺管理分类信息
     *
     * @param callback
     */
    public void getStoreClass(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreClass(UserInfoUtil.getUid(), 1, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    public void getGoodsCate(String keyword ,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getGoodsCate(UserInfoUtil.getUid(), keyword, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    public void commentShop(String shopId,String content,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .commentShop(UserInfoUtil.getUid(), shopId,content, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 个人中心 我的推广
     *
     * @param page
     * @param callback
     */
    public void userTuiguang(int page,String keyWord , HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .userTuiguang(UserInfoUtil.getUid(), page, 15, UserInfoUtil.getToken(),keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    public void zhituiRank(int page, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .zhituiRank(UserInfoUtil.getUid(), page, Constants.LIST_SIZE, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    public void activityRank(int page, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .activityRank(UserInfoUtil.getUid(), page, Constants.LIST_SIZE, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    public void rewardRank(int page, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .rewardRank(UserInfoUtil.getUid(), page, Constants.LIST_SIZE, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 个人中心 我的钱包
     *
     * @param callback
     */
    public void myWallet(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .myWallet(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 提现手续费
     *
     * @param callback
     */
    public void withdrawFeeValue(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .withdrawFeeValue(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * @param callback
     */
    public void get_set(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .get_set(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }

                    @Override
                    public void onFail(String msg) {
                        super.onFail(msg);
                        callback.onResult("-1");
                    }
                });
    }

    /**
     * 红米提现
     *
     * @param account_from
     * @param account_number
     * @param account_fee    手续费
     * @param type           1支付宝，2微信 3银行卡
     * @param type_name      支付宝微信 银行卡 账号
     * @param user_real_name 真实名（type=3）
     * @param callback
     */
    public void redmiTixian(String account_from, String account_number, String account_fee, int type,
                            String type_name, String user_real_name, String shopid, String back,String subBack,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .redmiTixian(UserInfoUtil.getUid(), account_from, account_number, account_fee, type, type_name,
                        user_real_name, shopid,back,subBack, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 开店入驻
     *
     * @param type          1：商城商家，2：周边商家，3：便利店（默认传2）
     * @param name          商家名称
     * @param contactperson 联系人
     * @param contact       联系电话
     * @param number        营业执照编号
     * @param license       营业执照图片url,调upload后返回
     * @param picture       身份证照片,调upload后返回，多张逗号拼接
     * @param code          验证码
     * @param callback
     */
    public void regShop(int type, String name, String contactperson, String contact,
                        String number, String license, String picture, String code, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .regShop(UserInfoUtil.getUid(), type, name, contactperson, contact, number, license, picture, code, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 商家入驻
     *
     * @param type                 商家类型，1：商城商家，2：周边商家，3：便利店（默认传2）
     * @param name                 商家名称
     * @param contactperson        联系人
     * @param contact              联系电话
     * @param number               营业执照编号
     * @param license              营业执照图片url
     * @param picture              身份证照片，多张逗号拼接
     * @param shop_type            商家类型 1个体户商户 2个体微小商户 3企业商户
     * @param contactperson_number 法人结算卡
     * @param shop_picture         门头合影照
     * @param shop_env_picture1    环境照1
     * @param shop_env_picture2    环境照2
     * @param permit_picture       开户许可证
     * @param zhekou               折扣比例
     * @param code
     * @param callback
     */
    public void regShopNew(int type, String name, String contactperson, String contact,
                           String number, String license, String picture, int shop_type, String contactperson_number,
                           String shop_picture, String shop_env_picture1, String shop_env_picture2, String permit_picture,
                           String zhekou, String code, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .regShopNew(UserInfoUtil.getUid(), type, name, contactperson, contact,
                        number, license, picture, shop_type, contactperson_number,
                        shop_picture, shop_env_picture1, shop_env_picture2, permit_picture,
                        zhekou, code, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 企业商户入驻
     *
     * @param shop_type
     * @param name
     * @param jianjie
     * @param xukeNum
     * @param zhengjian
     * @param email
     * @param contact
     * @param province_name
     * @param city_name
     * @param region_name
     * @param address
     * @param contactperson_number
     * @param kaihu
     * @param number
     * @param license
     * @param contactperson
     * @param picture
     * @param permit_picture
     * @param shop_picture
     * @param shop_env_picture1
     * @param shop_env_picture2
     * @param zhekou
     * @param code
     * @param callback
     */
    public void regShopCompany(
            int shop_type,//1个体户商户 2个体微小商户 3企业商户
            String name,//商家名称
            String jianjie,//商家简介
            String xukeNum,//开户许可证编号
            String zhengjian,//法人证件号码
            String email,//联系人邮箱
            String contact,//联系电话
            String province_name,//省
            String city_name,//市
            String region_name,//区
            String address,//详细地址
            String contactperson_number,//结算卡
            String kaihu,//银行账户开户总行编码
            String number,//营业执照号
            String license,//营业执照副本
            String contactperson,//法人姓名
            String picture,//法人身份证照 正面反面逗号隔开
            String permit_picture,//开户许可证照
            String shop_picture,//门头合影照
            String shop_env_picture1,//环境照
            String shop_env_picture2,
            String zhekou,
            String code, String phone ,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .regShopCompany(UserInfoUtil.getUid(), 2,
                        shop_type,
                        name,//商家名称
                        jianjie,//商家简介
                        xukeNum,//开户许可证编号
                        zhengjian,//法人证件号码
                        email,//联系人邮箱
                        contact,//联系电话
                        province_name,//省
                        city_name,//市
                        region_name,//区
                        address,//详细地址
                        contactperson_number,//结算卡
                        kaihu,//银行账户开户总行编码
                        number,//营业执照号
                        license,//营业执照副本
                        contactperson,//法人姓名
                        picture,//法人身份证照 正面反面逗号隔开
                        permit_picture,//开户许可证照
                        shop_picture,//门头合影照
                        shop_env_picture1,//环境照
                        shop_env_picture2,
                        zhekou,
                        code,
                        phone,
                        UserInfoUtil.getToken()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 领取积分
     *
     * @param callback
     */
    public void lingQuScore(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .lingQuScore(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 商品详情收藏
     *
     * @param id       商品id
     * @param action   0-添加收藏 1-取消收藏
     * @param callback
     */
    public void goodsCollect(String id, int action, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .goodsCollect(UserInfoUtil.getUid(), id, action, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 收藏列表
     *
     * @param action   1-商品 2-商城商家 3-活动 4-供给 5-需求 6-动态 7-采购店铺
     * @param callback
     */
    public void collectList(int action, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .collectList(UserInfoUtil.getUid(), action, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 广告列表 banner
     *
     * @param position 1、分类界面banner 2、个人中心广告图 3、商户中心广告图 4、生活圈banner 5. 首页banner
     * @param lng
     * @param lat
     * @param callback
     */
    public void positionList(int position, String lng, String lat, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .positionList(UserInfoUtil.getUid(), position, lng, lat, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, false) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 促销广告列表 banner
     *
     * @param position 1、分类界面banner 2、个人中心广告图 3、商户中心广告图 4、生活圈banner 5. 首页banner
     * @param lng
     * @param lat
     * @param callback
     */
    public void cxBannerList(int position, String lng, String lat, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .cxBannerList(UserInfoUtil.getUid(), position, lng, lat, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 获取系统手机号
     *
     * @param callback
     */
    public void getSysPhone(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getSysPhone(UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 生活圈分类
     *
     * @param type     1周边商家，2便利店
     * @param callback
     */
    public void nearShopCat(int type,String comId ,HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .nearShopCat(UserInfoUtil.getToken(),comId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 获取常见问题
     *
     * @param callback
     */
    public void getSystemProblem(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getSystemProblem(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 店铺管理收款码
     *
     * @param shopId
     * @param money
     * @param baseObserver
     */
    public void getStoreQR(String shopId, String money, BaseObserver<BaseBean<StoreQRBean>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreQR(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId, money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 根据商品规格获取商品价格
     *
     * @param goodsid  商品id
     * @param string   规格参数 ,号隔开
     * @param callback
     */
    public void getPriceBySize(String goodsid, String string, HttpsCallback callback) {
        LogUtils.e("获取规格价格 = " + goodsid + "，" + string);
        ApiRetrofit.getInstance().getApiService()
                .getPriceBySize(UserInfoUtil.getUid(), UserInfoUtil.getToken(), goodsid,string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }

                    @Override
                    public void onFail(String msg) {
                        super.onFail(msg);
                        callback.onResult("-1");
                    }
                });
    }

    /**
     * 删除购物车商品
     *
     * @param id
     * @param callback
     */
    public void deleteGoods(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .deleteGoods(UserInfoUtil.getUid(), UserInfoUtil.getToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }



                    @Override
                    public void onFail(String msg) {
//                        deleteGoods();
                    }
                });
    }

    /**
     * 店铺管理-商户今日结算列表
     *
     * @param shopId
     * @return
     */
    public void storeTodaySettlement(String shopId,String startTime,String endTime, BaseObserver<BaseBean<List<StoreTodaySettlementBean>>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .storeTodaySettlement(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId,startTime,endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }
    /**
     * 店铺管理-商户今日结算现金列表
     *
     * @param shopId
     * @return
     */
    public void storeTodaySettlementCash(String shopId,String startTime,String endTime, BaseObserver<BaseBean<List<StoreTodaySettlementCashBean>>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .storeTodaySettlementCash(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId,startTime,endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 店铺管理-商户今日推广列表
     *
     * @param shopId
     * @return
     */
    public void storeTodayInvite(String shopId, String page, BaseObserver<BaseBean<List<StoreTodayInviteBean>>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .storeTodayInvite(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

    /**
     * 修改资料
     *
     * @param name
     * @param birthday
     * @param gender
     * @param head
     * @param callback
     */
    public void editUser(String head, String name, long birthday, String gender, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .editUser(UserInfoUtil.getUid(), UserInfoUtil.getToken(), name, birthday, gender, head)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 获取消息列表
     *
     * @param page
     * @param shop_id
     * @param callback
     */
    public void getMessageList(int page, String shop_id, int type, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getMessageList(UserInfoUtil.getUid(), UserInfoUtil.getToken(), page, Constants.LIST_SIZE, shop_id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 获取未读消息数
     *
     * @param callback
     */
    public void getNoreadMessage(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getNoreadMessage(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 消息详情
     *
     * @param id
     * @param callback
     */
    public void getMessageDetail(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getMessageDetail(UserInfoUtil.getUid(), id, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 扫码支付
     *
     * @param totalprice
     * @param is_balance 是否余额支付 值为1
     * @param shopid
     * @param payment    1为商城红米，2为分享红米，3为交易红米
     * @param callback
     */
    public void appCreateOrder(String totalprice, int is_balance, String shopid, int payment, String pay_password, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .appCreateOrder(UserInfoUtil.getUid(), totalprice, is_balance, shopid, payment, pay_password, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }
    /**
     * 转账
     *
     * @param callback
     */
    public void transferAccount(String amount, String type, String phone, String pay_password, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .transfer(UserInfoUtil.getUid(), amount, type, phone,  pay_password, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }
    /**
     * 推荐
     *
     * @param callback
     */
    public void tuijian( String invateCode, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .tuijian(UserInfoUtil.getUid(),  invateCode, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 确认收货
     *
     * @param id
     * @param callback
     */
    public void sureReceipt(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .sureReceipt(UserInfoUtil.getUid(), id, UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 获取商户余额
     *
     * @param shopId
     * @param baseObserver
     */
    public void getStoreMoney(String shopId, BaseObserver<BaseBean<ShopBalance>> baseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreMoney(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);

    }

    /**
     * 获取商户消息
     *
     * @param type
     * @param shopId
     * @param baseBeanBaseObserver
     */
    public void getStoreMessage(int type, String shopId, int page, BaseObserver<BaseBean<StoreMessageListBean>> baseBeanBaseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreMessage(UserInfoUtil.getUid(), UserInfoUtil.getToken(), type, shopId, page, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanBaseObserver);
    }

    /**
     * 获取商户指南
     *
     * @param baseBeanBaseObserver
     */
    public void getStoreGuide(BaseObserver<BaseBean<List<StoreGuideList>>> baseBeanBaseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreGuide()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanBaseObserver);

    }

    /**
     * 商户评论
     *
     * @param shopId
     * @param page
     * @param baseBeanBaseObserver
     */
    public void getStoreComment(String shopId, int page, BaseObserver<BaseBean<List<StoreComment>>> baseBeanBaseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getStoreComment(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanBaseObserver);
    }
    /**
     * radio
     *
     */
    public void getRadioList(BaseObserver<BaseBean<List<RadioDto>>> baseBeanBaseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getRadioList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanBaseObserver);
    }

    /**
     * 设置支付密码
     *
     * @param phone
     * @param code
     * @param pay_password
     * @param callback
     */
    public void setPayPwd(String phone, String code, String pay_password, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .setPayPwd(UserInfoUtil.getUid(), UserInfoUtil.getToken(), phone, code, pay_password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 获取省市区
     *
     * @param callback
     */
    public void getRegion(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getRegion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, false) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 版本更新
     *
     * @param callback
     */
    public void update_version(HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .update_version(AppUtils.getAppVersionName(), "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 商城支付
     *
     * @param id
     * @param payment
     * @param callback
     */
    public void pay(String id, int payment, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .pay(UserInfoUtil.getUid(), UserInfoUtil.getToken(), id, payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 立即购买
     *
     * @param id
     * @param count
     * @param size
     * @param callback
     */
    public void buy(String id, int count, String size,String cuxiao_id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .buy(UserInfoUtil.getUid(), UserInfoUtil.getToken(), id, count, size,cuxiao_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * @param id
     * @param goods_id
     * @param type     1-退货，2-换货
     * @param reason
     * @param callback
     */
    public void applyAfterSales(String id, String goods_id, int type, String reason, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .applyAfterSales(UserInfoUtil.getUid(), UserInfoUtil.getToken(), id, goods_id, type, reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }

    /**
     * 取消订单
     *
     * @param id
     * @param callback
     */
    public void cancelOrder(String id, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .cancelOrder(UserInfoUtil.getUid(), UserInfoUtil.getToken(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }
    /**
     * 入住信息
     *
     */
    public void getShopInfo(int shopType, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getShopInfo(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, true) {
                    @Override
                    public void onSuccess(BaseBean response) {
//                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }
    /**
     * 提现到银行行卡信息
     *
     */
    public void getWithdrawalInfo(String shopId, HttpsCallback callback) {
        ApiRetrofit.getInstance().getApiService()
                .getWithdrawalInfo(UserInfoUtil.getUid(), UserInfoUtil.getToken(), shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseBean>(context, false) {
                    @Override
                    public void onSuccess(BaseBean response) {
//                        ToastUtils.showShort(response.getState().getMsg());
                        callback.onResult(response.getData());
                    }
                });
    }




    public void getAd(BaseObserver<BaseBean<List<AdvertDto>>> baseBeanBaseObserver) {
        ApiRetrofit.getInstance().getApiService()
                .getAd(UserInfoUtil.getUid(), UserInfoUtil.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBeanBaseObserver);
    }


}
