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
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.ConvertUtils;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends BaseFragment {


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
    GridLayoutManager glm;

    public NewGoodsFragment() {
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
        mAdapter = new NewGoodsAdapter(context,mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void setListener() {
        setPullDown();
        setPullUp();
    }

    private void setPullUp() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastPosition == mAdapter.getItemCount()-1){
                    mPageId++;
                    downloadNewGoods(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setPullDown() {
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                mPageId = 1;
                downloadNewGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadNewGoods(final int action) {
        NetDao.downloadNewGoods(context, mPageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                sl.setRefreshing(false);
                mAdapter.setMore(true);
                tvRefreshHint.setVisibility(View.GONE);
                if (result != null && result.length > 0){
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_PULL_DOWN || action == I.ACTION_DOWNLOAD){
                        mAdapter.initData(list);
                    }else{
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                    }
                }else{
                    mAdapter.setMore(false);
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

    @Override
    protected void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }

    @Override
    protected void initView() {
        L.i("main","initView()");
        sl.setColorSchemeColors(
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_blue)
        );
        glm = new GridLayoutManager(context, I.COLUM_NUM);
        rv.setLayoutManager(glm);
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
