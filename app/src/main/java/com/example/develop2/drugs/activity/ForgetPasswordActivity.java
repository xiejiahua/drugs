package com.example.develop2.drugs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.develop2.drugs.R;
import com.example.develop2.drugs.contract.ILoginContract;
import com.example.develop2.drugs.entity.UserInfo;
import com.example.develop2.drugs.model.UserImpl;
import com.example.develop2.drugs.presenter.LoginPresenter;
import com.example.develop2.drugs.utils.CreateRandomCodeValidate;
import com.example.develop2.drugs.utils.TimeCountUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.develop2.drugs.common.Const.CAPTCHA;
import static com.example.develop2.drugs.common.Const.PHONE;

/**
 * Created by xiejiahua on 2018/6/4.
 */

public class ForgetPasswordActivity extends BaseActivity implements ILoginContract.View {

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
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.et_random_code)
    EditText mEtRandomCode;
    @BindView(R.id.ib_random_code)
    ImageButton mIbRandomCode;
    private ILoginContract.Presenter mPresenter;
    private TimeCountUtil mTimeCountUtil;

    CreateRandomCodeValidate randomCodeValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwrod);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_forget_passwrod_toolbar_title));
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
        mTimeCountUtil = new TimeCountUtil(80000, 1000, mBtnFetchCaptcha);
        new LoginPresenter(UserImpl.getInstance(getApplicationContext()), this);

        randomCodeValidate = new CreateRandomCodeValidate();
        mIbRandomCode.setImageBitmap(randomCodeValidate.createRandomBitmap());
    }

    @OnClick({R.id.btn_fetch_captcha, R.id.btn_next, R.id.ib_random_code})
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
            case R.id.btn_next:
                if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
                    showMessage("请填写手机号");
                    return;
                }
                if (TextUtils.isEmpty(mEtCaptcha.getText().toString().trim())) {
                    showMessage("请填写验证码");
                    return;
                }
                if (mEtRandomCode.getText().toString().equalsIgnoreCase(randomCodeValidate.getRandomCode()) ==false)
                {
                    showMessage("随机验证码错误");
                    return;
                }
                Intent intent = new Intent(this, ReSetPasswrodActivity.class);
                intent.putExtra(PHONE, mEtPhone.getText().toString());
                intent.putExtra(CAPTCHA, mEtCaptcha.getText().toString());
                startActivity(intent);
                this.finish();
                break;
            case R.id.ib_random_code:
                mIbRandomCode.setImageBitmap(randomCodeValidate.createRandomBitmap());
                break;
        }
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
        showMessage("验证码已发送");
    }

    @Override
    public void showSuccessfullyForgetPasswrod() {
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }
}
