package day1010.fulicenter.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.Adapter.NewGoodsAdapter;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.CategoryChildBean;
import day1010.fulicenter.bean.NewGoodsBean;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.ConvertUtils;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.view.CatChildFilterButton;
import day1010.fulicenter.view.SpaceItemDecoration;

public class CategoryChildActivity extends BaseActivity {


    @Bind(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sl)
    SwipeRefreshLayout sl;

    CategoryChildActivity context;
    ArrayList<NewGoodsBean> mList;
    NewGoodsAdapter mAdapter;
    int mPageId = 1;
    GridLayoutManager glm;
    int catId;
    @Bind(R.id.btn_sort_price)
    Button btnSortPrice;
    @Bind(R.id.btn_sort_addtime)
    Button btnSortAddtime;
    boolean addTimeAsc = true;
    boolean priceAsc = false;
    int sortBy = I.SORT_BY_ADDTIME_DESC;
    @Bind(R.id.btnCatChildFilter)
    CatChildFilterButton btnCatChildFilter;
    String groupName;
    ArrayList<CategoryChildBean> childList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
        if (catId == 0) {
            finish();
        }
        groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        childList = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        context = this;
        mList = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(context, mList);
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
        btnCatChildFilter.setText(groupName);
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
                    downloadCategoryGoods(I.ACTION_PULL_UP);
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
                downloadCategoryGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(context, catId, mPageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                sl.setRefreshing(false);
                mAdapter.setMore(true);
                tvRefreshHint.setVisibility(View.GONE);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
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
        downloadCategoryGoods(I.ACTION_DOWNLOAD);
        btnCatChildFilter.setOnCatFilterClickListener(groupName, childList);
    }


    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                priceAsc = !priceAsc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                addTimeAsc = !addTimeAsc;
                break;
        }
        mAdapter.setSortBy(sortBy);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
