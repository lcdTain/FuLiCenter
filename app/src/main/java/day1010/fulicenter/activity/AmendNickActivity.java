package day1010.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.Result;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.dao.SharePreferenceUtils;
import day1010.fulicenter.dao.UserDao;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.ResultUtils;

/**
 * Created by Administrator on 2016/10/25.
 */
public class AmendNickActivity extends BaseActivity {
    @Bind(R.id.etAmendNick)
    EditText etAmendNick;

    AmendNickActivity context;
    User user = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.amend_nick);
        ButterKnife.bind(this);
        context = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            etAmendNick.setText(user.getMuserNick());
            etAmendNick.setSelectAllOnFocus(true);
        } else {
            finish();
        }

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.ivBack, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(context);
                break;
            case R.id.btn_save:
                if (user != null) {
                    String nick = etAmendNick.getText().toString().trim();
                    if (nick.equals(user.getMuserNick())) {
                        CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                    } else if (TextUtils.isEmpty(nick)) {
                        CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
                    } else {
                        updateNick(nick);
                    }
                }
                break;
        }
    }

    private void updateNick(String nick) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
        pd.show();
        NetDao.updateNick(context, user.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result == null){
                    CommonUtils.showShortToast(R.string.update_fail);
                }else{
                    if (result.isRetMsg()){
                        User u = (User) result.getRetData();
                        UserDao dao = new UserDao(context);
                        boolean isSuccess = dao.saveUser(u);
                        if (isSuccess){
                            FuLiCenterApplication.setUser(u);
                            setResult(RESULT_OK);
                            MFGT.finish(context);
                        }else{
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }
                    }else{
                        if (result.getRetCode() == I.MSG_USER_SAME_NICK){
                            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                        }else if(result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL){
                            CommonUtils.showLongToast(R.string.update_fail);
                        }else{
                            CommonUtils.showLongToast(R.string.update_fail);
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
}
