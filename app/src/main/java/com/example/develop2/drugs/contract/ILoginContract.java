package com.example.develop2.drugs.contract;

import com.example.develop2.drugs.entity.UserInfo;
//import com.example.develop2.drugs.entity.UserWxInfo;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public interface ILoginContract {


    interface Presenter extends BasePresenter {

        void onLogin(String phone, String password);

        void forgetPassword(String phone, String inputCaptcha, String password, String passwordAgain);

        void fetchCaptcha(String phone);

//        void onWxLogin(UserWxInfo wxInfo);

    }

    interface View extends BaseView<Presenter> {

        void showSuccessfullyLogin(UserInfo userInfo);

        void fetchCaptchaSuccessfull();

        void showSuccessfullyForgetPasswrod();


        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
