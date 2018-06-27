package com.example.develop2.drugs.model;

import android.support.annotation.NonNull;

import com.example.develop2.drugs.entity.UserInfo;
//import com.campusstreet.entity.UserWxInfo;

import java.util.Map;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public interface IUserBiz {

    void onLogin(String phone, String password, @NonNull onLoginCallback callback);

//    void onWxLogin(UserWxInfo wxInfo, @NonNull onWxLoginCallback callback);

    void onResgister(UserInfo userInfo, String password, String code, @NonNull
            onResgisterCallback callback);


    void fetchCaptcha(String mt, String mc, String phone, @NonNull GetCaptchaCallback callback);

    void getResgisterMc(String phone, @NonNull GetResgisterMcCallback callback);

    void getforgetPasswordrMc(String phone, @NonNull GetRForgetPasswordMcCallback callback);


    void forgetPassword(Map<String, Object> params, @NonNull ForgetPasswordCallback callback);

    interface onLoginCallback {

        void onLoginSuccess(UserInfo userInfo);

        void onLoginFailure(String errorMsg);
    }
    interface onWxLoginCallback {

        void onWxLoginSuccess(UserInfo userInfo);

        void onWxLoginFailure(String errorMsg);
    }


    interface onResgisterCallback {

        void onResgisterSuccess(UserInfo userInfo);

        void onResgisterFailure(String errorMsg);
    }

    interface GetCaptchaCallback {

        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface GetResgisterMcCallback {

        void GetResgisterMcSuccess(String mc);

        void GetResgisterMcFailure(String errorMsg);
    }

    interface GetRForgetPasswordMcCallback {

        void GetForgetPasswordMcSuccess(String mc);

        void GetForgetPasswordMcFailure(String errorMsg);
    }

    interface ForgetPasswordCallback {

        void onForgetPasswordSuccess();

        void onForgetPasswordFailure(String errorMsg);
    }
}
