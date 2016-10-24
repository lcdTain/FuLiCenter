package day1010.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.Result;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.ResultUtils;

/**
 * Created by Administrator on 2016/10/21.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    String Username;
    String Password;
    LoginActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        context = this;
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.ivBack)
    public void onClick() {
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                CheckedInput();
                break;
            case R.id.btn_register:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }
    private void CheckedInput(){
        Username = etUsername.getText().toString().trim();
        Password = etPassword.getText().toString().trim();
        if (Username == null){
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            etUsername.requestFocus();
            return;
        }else if (Password == null){
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            etPassword.requestFocus();
            return;
        }
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(getResources().getString(R.string.login));
        pd.show();
        NetDao.login(context, Username, Password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, I.User.class);
                if (result == null){
                    CommonUtils.showShortToast(R.string.login_fail);
                }else{
                    if (result.isRetMsg()){
                        CommonUtils.showShortToast(R.string.login_success);
                        MFGT.finish(context);
                    }else{
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER){
                            CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                        }else if(result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD){
                            CommonUtils.showLongToast(R.string.login_fail_error_password);
                        }else{
                            CommonUtils.showLongToast(R.string.login_fail);
                        }
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER){
            String name = data.getStringExtra(I.User.USER_NAME);
            etUsername.setText(name);
        }
    }
}
