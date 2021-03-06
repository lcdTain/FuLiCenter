package day1010.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.Adapter.CollectsAdapter;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.CollectBean;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.ConvertUtils;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.view.SpaceItemDecoration;

public class CollectActivity extends BaseActivity {
    CollectActivity context;
    ArrayList<CollectBean> mList;
    CollectsAdapter mAdapter;
    int mPageId = 1;
    GridLayoutManager glm;

    @Bind(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sl)
    SwipeRefreshLayout sl;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        context = this;
        mList = new ArrayList<>();
        mAdapter = new CollectsAdapter(context, mList);
        super.onCreate(savedInstanceState);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastPosition == mAdapter.getItemCount() - 1) {
                    mPageId++;
                    downloadCollects(I.ACTION_PULL_UP);
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
                downloadCollects(I.ACTION_PULL_DOWN);
            }
        });
    }


    private void downloadCollects(final int action) {

        NetDao.downloadCollects(context, user.getMuserName(), mPageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                sl.setRefreshing(false);
                mAdapter.setMore(true);
                tvRefreshHint.setVisibility(View.GONE);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_PULL_DOWN || action == I.ACTION_DOWNLOAD) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
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
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            finish();
        }
        downloadCollects(I.ACTION_DOWNLOAD);
    }

    @Override
    protected void initView() {
        L.i("main", "initView()");
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

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
