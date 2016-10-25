package day1010.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.Result;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.dao.SharePreferenceUtils;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.ImageLoader;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.OnSetAvatarListener;
import day1010.fulicenter.utils.ResultUtils;

public class PersonalInformationActivity extends BaseActivity {
    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvUsername)
    TextView tvUsername;
    @Bind(R.id.tvNick)
    TextView tvNick;
    PersonalInformationActivity context;
    User user = null;
    OnSetAvatarListener mOnSetAvatarListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_information);
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
        if (user != null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),context,ivAvatar);
            tvUsername.setText(user.getMuserName());
            tvNick.setText(user.getMuserNick());
        }else{
            finish();
        }
    }

    @Override
    protected void setListener() {

    }


    @OnClick({R.id.ivBack, R.id.btn_exit,R.id.twoUserAvatar,R.id.threeUsername,R.id.fourNick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(context);
                break;
            case R.id.btn_exit:
                logout();
                break;
            case R.id.twoUserAvatar:
                mOnSetAvatarListener = new OnSetAvatarListener(context,R.id.layout_upload_avatar,
                        user.getMuserName(),I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.threeUsername:
                CommonUtils.showLongToast("用户名作为登录名不能修改");
                break;
            case R.id.fourNick:
                MFGT.gotoAmendActivity(context);
                break;
        }
    }

    private void logout() {
        if (user != null){
            SharePreferenceUtils.getInstance(context).removeUser();
            FuLiCenterApplication.setUser(null);
            MFGT.gotoLoginActivity(context);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        if(mOnSetAvatarListener != null){
            mOnSetAvatarListener.setAvatar(requestCode,data,ivAvatar);
        }
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_NICK){
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO){
            updateAvatar();
        }
    }

    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(
                context,user.getMavatarPath()+"/"+
                        user.getMuserName()+I.AVATAR_SUFFIX_JPG));
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        NetDao.updateAvatar(context, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result == null){
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                }else{
                    User u = (User) result.getRetData();
                    if (result.isRetMsg()){
                        FuLiCenterApplication.setUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u),context,ivAvatar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    }else{
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showInfo(){
        user = FuLiCenterApplication.getUser();
        if (user != null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),context,ivAvatar);
            tvUsername.setText(user.getMuserName());
            tvNick.setText(user.getMuserNick());
        }
    }
}
