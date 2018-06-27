package com.example.develop2.drugs.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.develop2.drugs.R;
import com.example.develop2.drugs.common.Const;
import com.example.develop2.drugs.contract.IRegisterContract;
import com.example.develop2.drugs.entity.UserInfo;
import com.example.develop2.drugs.model.UserImpl;
import com.example.develop2.drugs.presenter.RegisterPresenter;
import com.example.develop2.drugs.utils.PreferencesUtil;
import com.example.develop2.drugs.utils.TimeCountUtil;
import com.google.gson.GsonBuilder;
//import com.xiaomi.mipush.sdk.MiPushClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiejiahua on 2018/6/4.
 */

public class RegisterActivity extends BaseActivity implements IRegisterContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_fetch_captcha)
    Button mBtnFetchCaptcha;
    @BindView(R.id.et_captcha)
    EditText mEtCaptcha;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password_again)
    EditText mEtPasswordAgain;
    @BindView(R.id.et_nickname)
    EditText mEtNickname;

    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private int mIndex;
    private String mSex;

    private IRegisterContract.Presenter mPresenter;
    private TimeCountUtil mTimeCountUtil;
    private Integer mTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mTimeCountUtil = new TimeCountUtil(80000, 1000, mBtnFetchCaptcha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new RegisterPresenter(UserImpl.getInstance(getApplicationContext()), this);

    }

    @OnClick({ R.id.btn_fetch_captcha, R.id.btn_register, R.id.ll_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fetch_captcha:
                if (mEtPhone.length() != 11) {
                    showMessage("请输入有效的手机号码！");
                } else {
                    mPresenter.fetchCaptcha(mEtPhone.getText().toString());
                    mTimeCountUtil.start();
                }


                break;

            case R.id.ll_sex:
                new AlertDialog.Builder(this)
                        .setTitle("请选择性别")
                        .setSingleChoiceItems(Const.SEX, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSex = Const.SEX[i];
                                mTvSex.setText(Const.SEX[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_register:
                addUser();
                break;
        }
    }


    private void addUser() {
        if (!mTvSex.getText().toString().trim().equals(mSex)) {
            showMessage("请选择性别");
            return;
        }

        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            showMessage("请输入手机号");
            return;
        }
//        try {
//            mTel = Integer.valueOf(mEtPhone.getText().toString().trim());
//        } catch (NumberFormatException e) {
//            showMessage("请输入正确的手机号码");
//            return;//       }
        if (TextUtils.isEmpty(mEtCaptcha.getText().toString().trim())) {
            showMessage("请填写验证码");
            return;
        }
        if (TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
            showMessage("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(mEtNickname.getText().toString().trim())) {
            showMessage("请输入昵称");
            return;
        }
        if (!mEtPassword.getText().toString().trim().equals(mEtPasswordAgain.getText().toString().trim())) {
            showMessage("两次密码不一致");
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setSex(mTvSex.getText().toString());
        userInfo.setUsername(mEtNickname.getText().toString());
        userInfo.setMobile(mEtPhone.getText().toString());
        mPresenter.onResgister(userInfo,mEtPassword.getText().toString(),mEtCaptcha.getText().toString());
    }

    @Override
    public void setPresenter(IRegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessfullyresgister(UserInfo userInfo) {
        showMessage("注册成功");
        String userinfo = new GsonBuilder().create().toJson(userInfo, UserInfo.class);
        PreferencesUtil.getDefaultPreferences(this, Const.PREF_USER)
                .edit()
                .putString(Const.PREF_USERINFO_KEY, userinfo)
                .apply();

//        MiPushClient.setAlias(getApplicationContext(), "mi_"+String.valueOf(userInfo.getMobile()), null);
        // 设置返回的结果数据
        Intent data = new Intent(this, MainActivity.class);
        data.putExtra(Const.USERINFO_EXTRA, userInfo);
        data.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(data);
        this.finish();
    }

    @Override
    public void fetchCaptchaSuccessfull() {
        showMessage("验证码已发送");
    }


    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.register_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }
}
