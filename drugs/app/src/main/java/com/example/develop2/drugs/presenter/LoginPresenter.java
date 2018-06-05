package com.example.develop2.drugs.presenter;

import com.example.develop2.drugs.contract.ILoginContract;
import com.example.develop2.drugs.entity.UserInfo;
//import com.example.develop2.drugs.entity.UserWxInfo;
import com.example.develop2.drugs.model.IUserBiz;
import com.example.develop2.drugs.model.UserImpl;

import java.util.HashMap;
import java.util.Map;

import static com.example.develop2.drugs.utils.Md5Util.encrypt;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class LoginPresenter implements ILoginContract.Presenter {

    public static final String TAG = "LoginPresenter";

    private UserImpl mUserImpl;
    private ILoginContract.View mView;


    public LoginPresenter(UserImpl userImpl, ILoginContract.View view) {
        mUserImpl = userImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void onLogin(String phone, String password) {
        if (phone.equals("") || password.equals("")) {
            mView.showErrorMsg("用户名或密码不能为空！");
        } else {
            mView.setLoadingIndicator(true);
            mUserImpl.onLogin(phone, password, new IUserBiz.onLoginCallback() {
                @Override
                public void onLoginSuccess(UserInfo userInfo) {
                    mView.showSuccessfullyLogin(userInfo);
                    mView.setLoadingIndicator(false);
                }

                @Override
                public void onLoginFailure(String errorMsg) {
                    mView.showErrorMsg(errorMsg);
                    mView.setLoadingIndicator(false);
                }
            });
        }
    }

    @Override
    public void forgetPassword(String phone, String inputCaptcha, String password, String passwordAgain) {
        mView.setLoadingIndicator(true);
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("mcode", inputCaptcha);
        params.put("newpwd", password);
        params.put("newpwd2", passwordAgain);
        mUserImpl.forgetPassword(params, new IUserBiz.ForgetPasswordCallback() {
            @Override
            public void onForgetPasswordSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullyForgetPasswrod();
            }

            @Override
            public void onForgetPasswordFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }


    @Override
    public void fetchCaptcha(final String phone) {
        mUserImpl.getforgetPasswordrMc(phone, new IUserBiz.GetRForgetPasswordMcCallback() {
            @Override
            public void GetForgetPasswordMcSuccess(String mc) {
                String newMc = mc.substring(3, 7) + mc.substring(9, 12);
                newMc = encrypt(newMc);
                String mt = "02";
                mUserImpl.fetchCaptcha(mt, newMc, phone, new IUserBiz.GetCaptchaCallback() {
                    @Override
                    public void onFetchSuccess() {
                        mView.fetchCaptchaSuccessfull();
                    }

                    @Override
                    public void onFetchFailure(String errorMsg) {
                        mView.showErrorMsg(errorMsg);
                    }
                });
            }

            @Override
            public void GetForgetPasswordMcFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }

//    @Override
//    public void onWxLogin(UserWxInfo wxInfo) {
//        mView.setLoadingIndicator(true);
//        mUserImpl.onWxLogin(wxInfo, new IUserBiz.onWxLoginCallback() {
//            @Override
//            public void onWxLoginSuccess(UserInfo userInfo) {
//                mView.showSuccessfullyLogin(userInfo);
//                mView.setLoadingIndicator(false);
//            }
//
//            @Override
//            public void onWxLoginFailure(String errorMsg) {
//                mView.showErrorMsg(errorMsg);
//                mView.setLoadingIndicator(false);
//            }
//        });
//    }
}
