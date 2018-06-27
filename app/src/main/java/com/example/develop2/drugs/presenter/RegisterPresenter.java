package com.example.develop2.drugs.presenter;

import com.example.develop2.drugs.contract.IRegisterContract;
import com.example.develop2.drugs.entity.UserInfo;
import com.example.develop2.drugs.model.IUserBiz;
import com.example.develop2.drugs.model.UserImpl;

import static com.example.develop2.drugs.utils.Md5Util.encrypt;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class RegisterPresenter implements IRegisterContract.Presenter {

    public static final String TAG = "RegisterPresenter";

    private UserImpl mUserImpl;
    private IRegisterContract.View mView;


    public RegisterPresenter(UserImpl userImpl, IRegisterContract.View view) {
        mUserImpl = userImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void onResgister(UserInfo userInfo,String password,String code) {
        mView.setLoadingIndicator(true);
        mUserImpl.onResgister(userInfo,password,code, new IUserBiz.onResgisterCallback() {
            @Override
            public void onResgisterSuccess(UserInfo userInfo) {
                mView.showSuccessfullyresgister(userInfo);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onResgisterFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchCaptcha(final String phone) {
        mUserImpl.getResgisterMc(phone, new IUserBiz.GetResgisterMcCallback() {
            @Override
            public void GetResgisterMcSuccess(String mc) {
                String newMc = mc.substring(3, 7) + mc.substring(9, 12);
                newMc = encrypt(newMc);
                String mt = "01";
                mUserImpl.fetchCaptcha(mt,newMc, phone, new IUserBiz.GetCaptchaCallback() {
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
            public void GetResgisterMcFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }
}
