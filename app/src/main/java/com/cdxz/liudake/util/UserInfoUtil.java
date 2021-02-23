package com.cdxz.liudake.util;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdxz.liudake.LiudakeApplication;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LoginBean;

public class UserInfoUtil {

    public static LoginBean getLoginInfo() {
        return (LoginBean) ACache.get(LiudakeApplication.getContext()).getAsObject(Constants.CACHE_LOGIN);
    }

    public static String getToken() {
        return ObjectUtils.isEmpty(getLoginInfo()) ? "" : getLoginInfo().getXizuetoken();
    }

    public static String getUid() {
        return ObjectUtils.isEmpty(getLoginInfo()) ? "" : getLoginInfo().getUid();
    }

    public static String getPhone() {
        return ObjectUtils.isEmpty(getLoginInfo()) ? "" : getLoginInfo().getBind_phone();
    }
}
