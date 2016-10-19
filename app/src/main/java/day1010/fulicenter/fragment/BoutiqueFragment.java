package day1010.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.Adapter.BoutiqueAdapter;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.bean.BoutiqueBean;
import day1010.fulicenter.bean.NewGoodsBean;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.ConvertUtils;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment{


    @Bind(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sl)
    SwipeRefreshLayout sl;
    LinearLayoutManager llm;
    MainActivity context;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        context = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new BoutiqueAdapter(context,mList);
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        setPullDown();

    }

    private void setPullDown() {
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                downloadBoutique();
            }
        });
    }


    private void initData() {
        downloadBoutique();

    }
    private void downloadBoutique() {
        NetDao.downloadBoutique(context, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                sl.setRefreshing(false);
                tvRefreshHint.setVisibility(View.GONE);
                if (result != null && result.length > 0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                        mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                sl.setRefreshing(false);
                tvRefreshHint.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void initView() {
        sl.setColorSchemeColors(
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_blue)
        );
        llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
