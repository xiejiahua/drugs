package com.example.develop2.drugs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.develop2.drugs.R;
import com.example.develop2.drugs.contract.ILoginContract;
import com.example.develop2.drugs.entity.UserInfo;
import com.example.develop2.drugs.model.UserImpl;
import com.example.develop2.drugs.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.develop2.drugs.common.Const.CAPTCHA;
import static com.example.develop2.drugs.common.Const.PHONE;

/**
 * Created by Orange on 2018/6/4.
 */

public class ReSetPasswrodActivity extends BaseActivity implements ILoginContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password_again)
    EditText mEtPasswordAgain;
    @BindView(R.id.btn_entel)
    Button mBtnEntel;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private String mPhone;
    private String mCaptcha;
    private ILoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_reset_password_toolbar_title));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mPhone = getIntent().getStringExtra(PHONE);
        mCaptcha = getIntent().getStringExtra(CAPTCHA);
        new LoginPresenter(UserImpl.getInstance(getApplicationContext()), this);
    }

    @OnClick(R.id.btn_entel)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
            showMessage("请输入密码");
            return;
        }
        if (!mEtPassword.getText().toString().trim().equals(mEtPasswordAgain.getText().toString().trim())) {
            showMessage("两次密码不一致");
            return;
        }
        mPresenter.forgetPassword(mPhone, mCaptcha, mEtPassword.getText().toString(), mEtPasswordAgain.getText().toString());
    }

    @Override
    public void setPresenter(ILoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessfullyLogin(UserInfo userInfo) {

    }

    @Override
    public void fetchCaptchaSuccessfull() {

    }


    @Override
    public void showSuccessfullyForgetPasswrod() {
        showMessage("修改成功");
        Intent data = new Intent(this, LoginActivity.class);
        data.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(data);
        this.finish();
    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.Modifying_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }
}
