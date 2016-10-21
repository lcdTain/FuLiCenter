package day1010.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by Administrator on 2016/10/21.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etNick)
    EditText etNick;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPasswordCompare)
    EditText etPasswordCompare;
    String username;
    String nickname;
    String password;
    String confirmPws;
    RegisterActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = this;
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


    @OnClick(R.id.brn_register)
    public void onClick() {
        username = etUsername.getText().toString().trim();
        nickname = etNick.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPws = etPasswordCompare.getText().toString().trim();
        if (username == null){
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            etUsername.requestFocus();
            return;
        }else if(!username.matches("[a-zA-Z]\\w{5,15}")){
            CommonUtils.showShortToast(R.string.illegal_user_name);
            etUsername.requestFocus();
            return;
        }else if(nickname == null){
             CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            etNick.requestFocus();
            return;
        }else if(password == null){
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            etPassword.requestFocus();
            return;
        }else if(confirmPws == null){
            CommonUtils.showShortToast(R.string.two_input_password);
            etPasswordCompare.requestFocus();
            return;
        }else if(!password.equals(confirmPws)){
            CommonUtils.showShortToast(R.string.two_input_password);
            etPasswordCompare.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(context, username, nickname, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null){
                    CommonUtils.showShortToast(R.string.register_fail);
                }else{
                    if (result.isRetMsg()){
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(context);
                    }else{
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        etUsername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
            }
        });

    }
}
