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
import day1010.fulicenter.bean.User;
import day1010.fulicenter.utils.ImageLoader;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresonalCenterFragment extends BaseFragment {


    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvUsername)
    TextView tvUsername;

    MainActivity context;


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
        User user = FuLiCenterApplication.getUser();
        if (user == null){
            MFGT.gotoLoginActivity(context);
        }else{
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),context,ivAvatar);
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

    @OnClick(R.id.tvSettings)
    public void onClick() {
    }
}
