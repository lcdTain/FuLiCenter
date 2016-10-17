package day1010.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.Adapter.NewGoodsAdapter;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.bean.NewGoodsBean;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    @Bind(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sl)
    SwipeRefreshLayout sl;
    MainActivity context;
    ArrayList<NewGoodsBean> mList;
    NewGoodsAdapter mAdapter;
    int mPageId = 1;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        initView();
        initData();
        ButterKnife.bind(this, view);
        return view;
    }

    private void initData() {
        NetDao.downloadNewGoods(context, mPageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean>() {
            @Override
            public void onSuccess(NewGoodsBean result) {

            }

            @Override
            public void onError(String error) {

            }
        });


    }

    private void initView() {
        context = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(context,mList);

        sl.setColorSchemeColors(
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_blue)
        );
        GridLayoutManager glm = new GridLayoutManager(context, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
