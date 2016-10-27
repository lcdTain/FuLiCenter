package day1010.fulicenter.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.Adapter.CartAdapter;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.bean.CartBean;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.ResultUtils;
import day1010.fulicenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment {

    @Bind(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.sl)

    SwipeRefreshLayout sl;
    LinearLayoutManager llm;
    MainActivity context;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    @Bind(R.id.tvNothing)
    TextView tvNothing;
    @Bind(R.id.tvSumPrice)
    TextView tvSumPrice;
    @Bind(R.id.tvJieSheng)
    TextView tvJieSheng;
    @Bind(R.id.rlBuy)
    RelativeLayout rlBuy;
    updateCartReceiver mReceiver;

    public CartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        context = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(context, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void setListener() {
        setPullDown();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATE_CART);
        mReceiver = new updateCartReceiver();
        context.registerReceiver(mReceiver,filter);

    }

    private void setPullDown() {
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }


    @Override
    protected void initData() {
        downloadCart();

    }

    private void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    sl.setRefreshing(false);
                    tvRefreshHint.setVisibility(View.GONE);
                    if (list != null && list.size() > 0) {
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.initData(mList);
                        setCartLayout(true);
                    }else{
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCartLayout(false);
                    sl.setRefreshing(false);
                    tvRefreshHint.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                }
            });
        }

    }

    @Override
    protected void initView() {
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
        setCartLayout(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_buy)
    public void onClick() {
    }

    public void setCartLayout(boolean hasCart) {
        rlBuy.setVisibility(hasCart?View.VISIBLE:View.GONE);
        tvNothing.setVisibility(hasCart?View.GONE: View.VISIBLE);
        rv.setVisibility(hasCart?View.VISIBLE:View.GONE);
        sumPrince();
    }
    private void sumPrince(){
        int sumPrince = 0;
        int ranPrince = 0;
        if (mList != null && mList.size()>0){
            for (CartBean c : mList){
                if (c.isChecked()){
                    sumPrince += getPrince(c.getGoods().getCurrencyPrice())*c.getCount();
                    ranPrince += getPrince(c.getGoods().getRankPrice())*c.getCount();
                }
            }
            tvSumPrice.setText("总价：￥"+Double.valueOf(sumPrince));
            tvJieSheng.setText("节省：￥"+Double.valueOf(sumPrince-ranPrince));
        }else{
            tvSumPrice.setText("总价：￥0");
            tvJieSheng.setText("节省：￥0");
        }
    }
    private int getPrince(String price){
        price = price.substring(price.indexOf("￥")+1);
        return Integer.valueOf(price);
    }
    class updateCartReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            sumPrince();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null){
            context.unregisterReceiver(mReceiver);
        }
    }
}
