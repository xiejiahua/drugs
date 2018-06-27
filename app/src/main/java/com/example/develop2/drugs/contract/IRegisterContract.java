package com.example.develop2.drugs.contract;


import com.example.develop2.drugs.entity.UserInfo;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public interface IRegisterContract {
    interface Presenter extends BasePresenter {

        void onResgister(UserInfo userInfo, String password, String code);


        void fetchCaptcha(String phone);


    }

    interface View extends BaseView<Presenter> {

        void showSuccessfullyresgister(UserInfo userInfo);

        void fetchCaptchaSuccessfull();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
