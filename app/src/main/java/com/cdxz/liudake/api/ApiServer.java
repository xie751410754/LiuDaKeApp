package com.cdxz.liudake.api;


import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.AppUtils;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.bean.IndexAllInfoBean;
import com.cdxz.liudake.bean.MessageListBean;
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

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServer {


    @FormUrlEncoded
    @POST("mobile/my/getdistributor")
    Observable<BaseBean> getMerchant(
            @Field("region") int region,
            @Field("page") int page,
            @Field("page_size") int pageSize
    );




    //检查更新
    @FormUrlEncoded
    @POST("version/api/update")
    Observable<BaseBean> checkUpdate(
            @Field("version") String version,
            @Field("type") String type
    );

    //注册
    @FormUrlEncoded
    @POST("User/Api/regist")
    Observable<BaseBean> register(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String code,
            @Field("invitecode") String invitecode,
            @Field("phone_sys") String phone_sys,
            @Field("phone_type") String phone_type,
            @Field("mark") String mark
    );

    //获取手机验证码
    @FormUrlEncoded
    @POST("User/Api/getCode")
    Observable<BaseBean> getSmsCode(
            @Field("phone") String phone,
            @Field("type") int type
    );

    //重置密码
    @FormUrlEncoded
    @POST("User/Api/resetPassword")
    Observable<BaseBean> resetPassword(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String code,
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //密码登录
    @FormUrlEncoded
    @POST("User/Api/login")
    Observable<BaseBean> loginByPwd(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("mark") String mark
    );

    //搜索
    @FormUrlEncoded
    @POST("Shop/Api/shopSearch")
    Observable<BaseBean> search(
            @Field("uid") String uid,
            @Field("keyword") String keyword,
            @Field("type") int type,
            @Field("searchtype") int searchtype,
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("comid") String comid,
            @Field("pageSize") int pageSize,
            @Field("page") int page,
            @Field("xizuetoken") String xizuetoken
    );

    //热门搜索
    @FormUrlEncoded
    @POST("Shop/Api/hotSearch")
    Observable<BaseBean> hotSearch(
            @Field("type") int type
    );

    //历史搜索
    @FormUrlEncoded
    @POST("Shop/Api/userSearch")
    Observable<BaseBean> historySearch(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //清空搜索
    @FormUrlEncoded
    @POST("Shop/Api/removeSearch")
    Observable<BaseBean> clearSearch(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //个人信息
    @FormUrlEncoded
    @POST("User/App/userInfo")
    Observable<BaseBean> userInfo(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //首页
    @POST("Home/api/index")
    Observable<BaseBean> homeIndex();

    //商品详情
    @FormUrlEncoded
    @POST("Goods/Api/detail")
    Observable<BaseBean> goodsDetail(
            @Field("uid") String uid,
            @Field("goodsid") String goodsid,
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("size") String size,
            @Field("city") String city,
            @Field("xizuetoken") String xizuetoken
    );

    //加入购物车
    @FormUrlEncoded
    @POST("order/Api/addCart")
    Observable<BaseBean> orderAddCar(
            @Field("uid") String uid,
            @Field("size") String size,
            @Field("id") String id,
            @Field("count") int count,
            @Field("xizuetoken") String xizuetoken
    );

    //购物车列表
    @FormUrlEncoded
    @POST("order/Api/cart")
    Observable<BaseBean> getCarList(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //商品分类列表
    @POST("goods/api/categoryLists")
    Observable<BaseBean> categoryLists();

    //商品列表
    @FormUrlEncoded
    @POST("goods/api/lists")
    Observable<BaseBean> goodsList(
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("activity_id") String activity_id,
            @Field("guxiao_id") String guxiao_id,
            @Field("cateid") String cateid,
            @Field("brand") String brand,
            @Field("keywords") String keywords,
            @Field("shopid") String shopid,
            @Field("shopcateid") String shopcateid,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("sort") int sort,
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken

    );

    //添加收货地址
    @FormUrlEncoded
    @POST("order/api/addAddress")
    Observable<BaseBean> addAddress(
            @Field("uid") String uid,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("detail") String detail,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("selected") int selected,
            @Field("province") String province,
            @Field("city") String city,
            @Field("district") String district,
            @Field("xizuetoken") String xizuetoken
    );

    //收货地址列表
    @FormUrlEncoded
    @POST("order/api/listAddress")
    Observable<BaseBean> addressList(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //修改收货地址
    @FormUrlEncoded
    @POST("order/api/editAddress")
    Observable<BaseBean> editAddress(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("detail") String detail,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("selected") int selected,
            @Field("province") String province,
            @Field("city") String city,
            @Field("district") String district,
            @Field("xizuetoken") String xizuetoken
    );

    //删除收货地址
    @FormUrlEncoded
    @POST("order/api/deleteAddress")
    Observable<BaseBean> deleteAddress(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("xizuetoken") String xizuetoken
    );

    //积分账单
    @FormUrlEncoded
    @POST("user/app/user_integral")
    Observable<BaseBean> integralBill(
            @Field("uid") String uid,
            @Field("type") int type,
            @Field("time") String time,
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("xizuetoken") String xizuetoken
    );

    //红米账单
    @FormUrlEncoded
    @POST("user/app/user_balance")
    Observable<BaseBean> redmiBill(
            @Field("uid") String uid,
            @Field("type") int type,
            @Field("time") String time,
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("shop_id") String shopId,
            @Field("xizuetoken") String xizuetoken
    );

    //购物车选择
    @FormUrlEncoded
    @POST("order/api/selected")
    Observable<BaseBean> selectCar(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("action") int action,
            @Field("type") int type,
            @Field("xizuetoken") String xizuetoken
    );

    //购物车结算
    @FormUrlEncoded
    @POST("order/api/settlement")
    Observable<BaseBean> settlement(
            @Field("uid") String uid,
            @Field("pay_type") int pay_type,
            @Field("is_balance") int is_balance,
            @Field("is_gold") int is_gold,
            @Field("xizuetoken") String xizuetoken
    );

    //提交订单
    @FormUrlEncoded
    @POST("order/Api/submitOrder")
    Observable<BaseBean> submitOrder(
            @Field("uid") String uid,
            @Field("is_balance") int is_balance,
            @Field("is_gold") int is_gold,
            @Field("addressid") String addressid,
            @Field("pay_type") int pay_type,
            @Field("pay_password") String pay_password,
            @Field("xizuetoken") String xizuetoken
    );

    //订单列表
    @FormUrlEncoded
    @POST("order/Api/orderLists")
    Observable<BaseBean> orderList(
            @Field("uid") String uid,
            @Field("action") int action,
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("shopid") String shopid,
            @Field("xizuetoken") String xizuetoken
    );


    //个人中心
    @FormUrlEncoded
    @POST("user/app/index")
    Observable<BaseBean> userIndex(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //订单详情
    @FormUrlEncoded
    @POST("order/Api/orderDetail")
    Observable<BaseBean> orderDetail(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("xizuetoken") String xizuetoken
    );

    //邀请码
    @FormUrlEncoded
    @POST("User/App/invitQrcode")
    Observable<BaseBean> invitQrcode(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );


    //购物车数量调整
    @FormUrlEncoded
    @POST("order/api/adjustCount")
    Observable<BaseBean> adjustCount(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("count") int count,
            @Field("xizuetoken") String xizuetoken
    );

    //获取商户首页信息(店铺管理首页调用)
    @FormUrlEncoded
    @POST("shop/api/getIndexAllInfo")
    Observable<BaseBean<IndexAllInfoBean>> storeIndex(
            @Field("uid") String uid,
            @Field("shop_id") String shopId,
            @Field("xizuetoken") String xizuetoken
    );

    //获取商户首页未读消息数量(店铺管理首页调用)
    @FormUrlEncoded
    @POST("shop/api/getNoreadMessage")
    Observable<BaseBean<StoreUnderMsgResult>> storeIndexUnderMsg(
            @Field("uid") String uid,
            @Field("shop_id") String shopId,
            @Field("xizuetoken") String xizuetoken
    );

    //修改商家营业状态(店铺管理首页调用)
    @FormUrlEncoded
    @POST("shop/api/changeOpenStatus")
    Observable<BaseBean<StoreOpenStatus>> changeOpenStatus(
            @Field("uid") String uid,
            @Field("id") String shopId,
            @Field("xizuetoken") String xizuetoken,
            @Field("open_status") int status
    );

    //查询商家营业状态(店铺管理首页调用)
    @FormUrlEncoded
    @POST("shop/api/changeOpenStatus")
    Observable<BaseBean<StoreOpenStatus>> changeOpenStatus(
            @Field("uid") String uid,
            @Field("id") String shopId,
            @Field("xizuetoken") String xizuetoken
    );

    //查询商家店铺信息(店铺管理编辑页面调用)

    @GET("Shop/Api/detail")
    Observable<BaseBean<StoreInfoDetailBean>> getStoreInfo(
            @Query("uid") String uid,
            @Query("id") String shopId,
            @Query("xizuetoken") String xizuetoken
    );

    //查询商家店铺分类信息(店铺管理分类页面调用)
    //类型，1周边商家，2便利店
    @FormUrlEncoded
    @POST("Nearshop/Api/nearShopCat")
    Observable<BaseBean> getStoreClass(
            @Field("uid") String uid,
            @Field("type") int type,
            @Field("xizuetoken") String xizuetoken
    );


    //生活圈
    @FormUrlEncoded
    @POST("Shop/Api/nearShop")
    Observable<BaseBean> nearShop(
            @Field("uid") String uid,
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("pageSize") int pageSize,
            @Field("page") int page,
            @Field("type") int type,
            @Field("cat_id") String cat_id,
            @Field("fastcateid") String fastcateid,
            @Field("sort") int sort,
            @Field("xizuetoken") String xizuetoken
    );

    //搜索生活圈
    @FormUrlEncoded
    @POST("Shop/Api/nearShop")
    Observable<BaseBean> searchNearShop(
            @Field("uid") String uid,
            @Field("keywords") String keywords,
            @Field("page") int page,
            @Field("sort") int sort,
            @Field("cat_id") String cat_id,
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("pageSize") int pageSize,
            @Field("type") int type,
            @Field("xizuetoken") String xizuetoken
    );

    //生活圈列表点击详情
    @FormUrlEncoded
    @POST("Shop/Api/detail")
    Observable<BaseBean> shopDetail(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("xizuetoken") String xizuetoken
    );

    //个人中心 我的推广
    @FormUrlEncoded
    @POST("user/app/user_tuiguang")
    Observable<BaseBean> userTuiguang(
            @Field("uid") String uid,
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("xizuetoken") String xizuetoken
    );

    //个人中心 我的钱包
    @FormUrlEncoded
    @POST("user/app/my_balance")
    Observable<BaseBean> myWallet(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //提现 获取手续费
    @FormUrlEncoded
    @POST("user/api/get_set")
    Observable<BaseBean> withdrawFeeValue(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    @FormUrlEncoded
    @POST("User/Api/get_set")
    Observable<BaseBean> get_set(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //我的钱包 红米提现
    @FormUrlEncoded
    @POST("user/app/balance_withdraw_from")
    Observable<BaseBean> redmiTixian(
            @Field("uid") String uid,
            @Field("account_from") String account_from,
            @Field("account_number") String account_number,
            @Field("account_fee") String account_fee,
            @Field("type") int type,
            @Field("type_name") String type_name,
            @Field("user_real_name") String user_real_name,
            @Field("shop_id") String shopId,
            @Field("bank") String bank,
            @Field("sub_bank") String subBank,
            @Field("xizuetoken") String xizuetoken
    );

    //个人中心 开店入驻
    @FormUrlEncoded
    @POST("Shop/Api/regShop")
    Observable<BaseBean> regShop(
            @Field("uid") String uid,
            @Field("type") int type,
            @Field("name") String name,
            @Field("contactperson") String contactperson,
            @Field("contact") String contact,
            @Field("number") String number,
            @Field("license") String license,
            @Field("picture") String picture,
            @Field("code") String code,
            @Field("xizuetoken") String xizuetoken
    );

    @FormUrlEncoded
    @POST("Shop/Api/regShop")
    Observable<BaseBean> regShopNew(
            @Field("uid") String uid,
            @Field("type") int type,
            @Field("name") String name,
            @Field("contactperson") String contactperson,
            @Field("contact") String contact,
            @Field("number") String number,
            @Field("license") String license,
            @Field("picture") String picture,
            @Field("shop_type") int shop_type,
            @Field("contactperson_number") String contactperson_number,
            @Field("shop_picture") String shop_picture,
            @Field("shop_env_picture1") String shop_env_picture1,
            @Field("shop_env_picture2") String shop_env_picture2,
            @Field("permit_picture") String permit_picture,
            @Field("zhekou") String zhekou,
            @Field("code") String code,
            @Field("xizuetoken") String xizuetoken
    );

    //企业商户入驻申请
    @FormUrlEncoded
    @POST("Shop/Api/regShop")
    Observable<BaseBean> regShopCompany(
            @Field("uid") String uid,
            @Field("type") int type,
            @Field("shop_type") int shop_type,

            @Field("name") String name,//商家名称
            @Field("jianjie") String jianjie,//商家简介
            @Field("xukeNum") String xukeNum,//开户许可证编号
            @Field("zhengjian") String zhengjian,//法人证件号码
            @Field("emall") String email,//联系人邮箱
            @Field("contact") String contact,//联系电话
            @Field("province_name") String province_name,//省
            @Field("city_name") String city_name,//市
            @Field("region_name") String region_name,//区
            @Field("address") String address,//详细地址
            @Field("contactperson_number") String contactperson_number,//结算卡
            @Field("kaihu") String kaihu,//银行账户开户总行编码
            @Field("number") String number,//营业执照号
            @Field("license") String license,//营业执照副本
            @Field("contactperson") String contactperson,//法人姓名
            @Field("picture") String picture,//法人身份证照 正面反面逗号隔开
            @Field("permit_picture") String permit_picture,//开户许可证照
            @Field("shop_picture") String shop_picture,//门头合影照
            @Field("shop_env_picture1") String shop_env_picture1,//环境照
            @Field("shop_env_picture2") String shop_env_picture2,
            @Field("zhekou") String zhekou,

            @Field("code") String code,
            @Field("phone") String phone,
            @Field("xizuetoken") String xizuetoken
    );


    //个人中心 领取积分
    @FormUrlEncoded
    @POST("user/app/get_wait_integral")
    Observable<BaseBean> lingQuScore(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

//    //获取用户或者商户积分
//    @FormUrlEncoded
//    @POST("user/api/getGoldRecord")
//    Observable<BaseBean> lingQuScore(
//            @Field("uid") String uid,
//            @Field("gid") String gid,
//            @Field("xizuetoken") String xizuetoken
//    );

    //商品详情收藏
    @FormUrlEncoded
    @POST("goods/api/goodsCollect")
    Observable<BaseBean> goodsCollect(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("action") int action,
            @Field("xizuetoken") String xizuetoken
    );

    //收藏列表
    @FormUrlEncoded
    @POST("user/api/collectLists")
    Observable<BaseBean> collectList(
            @Field("uid") String uid,
            @Field("action") int action,
            @Field("xizuetoken") String xizuetoken
    );

    //广告列表（Banner）
    @FormUrlEncoded
    @POST("Home/Api/position_list")
    Observable<BaseBean> positionList(
            @Field("uid") String uid,
            @Field("position") int position,
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("xizuetoken") String xizuetoken
    );

    //获取系统手机号
    @FormUrlEncoded
    @POST("shop/api/getSysPhone")
    Observable<BaseBean> getSysPhone(
            @Field("xizuetoken") String xizuetoken
    );

    //周边商家类别(生活圈分类)
    @FormUrlEncoded
    @POST("Nearshop/Api/nearShopCat")
    Observable<BaseBean> nearShopCat(
            @Field("xizuetoken") String xizuetoken,
            @Field("type") int type
    );

    //获取常见问题
    @FormUrlEncoded
    @POST("shop/api/getsysteminfos")
    Observable<BaseBean> getSystemProblem(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //店铺管理收款码
    @FormUrlEncoded
    @POST("Shop/Api/getSMQrcode")
    Observable<BaseBean<StoreQRBean>> getStoreQR(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("shopid") String shopId,
            @Field("money") String money
    );

    //根据规格获取商品价格
    @FormUrlEncoded
    @POST("Goods/Api/detailBySize")
    Observable<BaseBean> getPriceBySize(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("goodsid") String goodsid,
            @Field("string") String string
    );

    //删除购物车中的商品
    @FormUrlEncoded
    @POST("order/api/deleteGoods")
    Observable<BaseBean> deleteGoods(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("id") String id
    );

    /**
     * 店铺管理-商户今日结算列表
     *
     * @param uid
     * @param xizuetoken
     * @param shopId
     * @return
     */
    @GET("shop/api/getHongmiToday")
    Observable<BaseBean<List<StoreTodaySettlementBean>>> storeTodaySettlement(
            @Query("uid") String uid,
            @Query("xizuetoken") String xizuetoken,
            @Query("shop_id") String shopId

    );
    /**
     * 店铺管理-商户今日结算现金列表
     *
     * @param uid
     * @param xizuetoken
     * @param shopId
     * @return
     */
    @GET("shop/api/getYibaoToday")
    Observable<BaseBean<List<StoreTodaySettlementCashBean>>> storeTodaySettlementCash(
            @Query("uid") String uid,
            @Query("xizuetoken") String xizuetoken,
            @Query("shop_id") String shopId

    );

    /**
     * 店铺管理-商户今日推广列表
     *
     * @param uid
     * @param xizuetoken
     * @param shopId
     * @return
     */
    @GET("shop/api/getInviterToday")
    Observable<BaseBean<List<StoreTodayInviteBean>>> storeTodayInvite(
            @Query("uid") String uid,
            @Query("xizuetoken") String xizuetoken,
            @Query("shop_id") String shopId,
            @Query("page") String page
    );

    //信息修改
    @FormUrlEncoded
    @POST("user/app/user_edit")
    Observable<BaseBean> editUser(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("name") String name,
            @Field("birthday") long birthday,
            @Field("gender") String gender,
            @Field("head") String head
    );

    //获取消息列表
    @FormUrlEncoded
    @POST("User/App/get_user_message")
    Observable<BaseBean> getMessageList(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("shop_id") String shop_id,
            @Field("type") int type
    );

    //用户端获取未读消息数
    @FormUrlEncoded
    @POST("user/api/getNoreadMessage")
    Observable<BaseBean> getNoreadMessage(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken
    );

    //用户端获取未读消息数
    @FormUrlEncoded
    @POST("User/App/get_message_info")
    Observable<BaseBean> getMessageDetail(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("xizuetoken") String xizuetoken
    );

    //扫码支付
    @FormUrlEncoded
    @POST("Order/Api/appCreateOrder")
    Observable<BaseBean> appCreateOrder(
            @Field("uid") String uid,
            @Field("totalprice") String totalprice,
            @Field("is_balance") int is_balance,
            @Field("shopid") String shopid,
            @Field("payment") int payment,
            @Field("pay_password") String pay_password,
            @Field("xizuetoken") String xizuetoken
    );

    //确认收货
    @FormUrlEncoded
    @POST("order/api/sureReceipt")
    Observable<BaseBean> sureReceipt(
            @Field("uid") String uid,
            @Field("id") String id,
            @Field("xizuetoken") String xizuetoken
    );


    //获取商户红米信息
    @FormUrlEncoded
    @POST("shop/api/shop_balance")
    Observable<BaseBean<ShopBalance>> getStoreMoney(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("id") String shopId
    );

    //商户消息
    @GET("User/App/get_user_message")
    Observable<BaseBean<StoreMessageListBean>> getStoreMessage(
            @Query("uid") String uid,
            @Query("xizuetoken") String xizuetoken,
            @Query("type") int type,
            @Query("shop_id") String shopId,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    //商户指南
    @POST("User/Api/get_set")
    Observable<BaseBean<List<StoreGuideList>>> getStoreGuide();

    //商户评论
    @GET("goods/api/getComment")
    Observable<BaseBean<List<StoreComment>>> getStoreComment(
            @Query("uid") String uid,
            @Query("xizuetoken") String xizuetoken,
            @Query("shop_id") String shopId,
            @Query("page") int page);

    //设置支付密码
    @GET("user/api/set_pay_password")
    Observable<BaseBean> setPayPwd(
            @Query("uid") String uid,
            @Query("xizuetoken") String xizuetoken,
            @Query("phone") String phone,
            @Query("code") String code,
            @Query("pay_password") String pay_password
    );

    //获取省市区
    @GET("user/api/region")
    Observable<BaseBean> getRegion();

    //版本更新
    @GET("version/api/update")
    Observable<BaseBean> update_version(
            @Query("version") String version,
            @Query("type") String type
    );

    //商城支付
    @FormUrlEncoded
    @POST("order/api/pay")
    Observable<BaseBean> pay(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("id") String id,
            @Field("payment") int payment
    );

    //立即购买
    @FormUrlEncoded
    @POST("order/api/nowSettlement01")
    Observable<BaseBean> buy(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("id") String id,
            @Field("count") int count,
            @Field("size") String size
    );

    //申请售后
    @FormUrlEncoded
    @POST("order/api/returnGoods")
    Observable<BaseBean> applyAfterSales(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("id") String id,
            @Field("goods_id") String goods_id,
            @Field("type") int type,
            @Field("reason") String reason
    );

    //取消订单
    @FormUrlEncoded
    @POST("order/api/orderCancel")
    Observable<BaseBean> cancelOrder(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("id") String id
    );
    /**
     * 入住申请信息
     *
     * @param uid
     * @param xizuetoken
     * @param shop_type
     * @return
     */
    @FormUrlEncoded
    @POST("shop/api/getShopInfo")
    Observable<BaseBean> getShopInfo(
            @Field("uid") String uid,
            @Field("xizuetoken") String xizuetoken,
            @Field("shop_type") int shop_type
    );
}
