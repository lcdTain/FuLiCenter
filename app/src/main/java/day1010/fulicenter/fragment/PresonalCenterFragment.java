package day1010.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.activity.PersonalInformationActivity;
import day1010.fulicenter.bean.MessageBean;
import day1010.fulicenter.bean.Result;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.dao.UserDao;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.ImageLoader;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.ResultUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresonalCenterFragment extends BaseFragment {


    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvUsername)
    TextView tvUsername;

    MainActivity context;
    User user = null;
    @Bind(R.id.tvGoodsColumn)
    TextView tvGoodsColumn;


    public PresonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_presonal_center, container, false);
        ButterKnife.bind(this, view);
        context = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), context, ivAvatar);
            tvUsername.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), context, ivAvatar);
            tvUsername.setText(user.getMuserNick());
            syncUserInfo();
            syncCollectCount();
        }
    }


    private void syncUserInfo() {
        NetDao.syncUserInfo(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result != null) {
                    User u = (User) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(context);
                        boolean isSuccess = dao.saveUser(u);
                        if (isSuccess) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), context, ivAvatar);
                            tvUsername.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void syncCollectCount() {
        NetDao.syncCollectCount(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    tvGoodsColumn.setText(result.getMsg());
                } else {
                    tvGoodsColumn.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                tvGoodsColumn.setText(String.valueOf(0));
            }
        });
    }


    @OnClick({R.id.tvSettings, R.id.rlCollect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSettings:
                MFGT.startActivity(context, PersonalInformationActivity.class);
                break;
            case R.id.rlCollect:
                MFGT.gotoCollects(context);
                break;
        }
    }
}
