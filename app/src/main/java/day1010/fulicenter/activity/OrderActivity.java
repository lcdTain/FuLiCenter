package day1010.fulicenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.CartBean;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.ResultUtils;

/**
 * Created by Administrator on 2016/10/28.
 */
public class OrderActivity extends BaseActivity {
    @Bind(R.id.etConsignee)
    EditText etConsignee;
    @Bind(R.id.etTel)
    EditText etTel;
    @Bind(R.id.sp_place)
    Spinner spPlace;
    @Bind(R.id.etStreet)
    EditText etStreet;
    @Bind(R.id.tvOrderPrince)
    TextView tvOrderPrince;

    OrderActivity context;
    User user = null;
    String cartIds = "";
    ArrayList<CartBean> mList = null;
    String [] ids = new String []{};
    int ranPrince = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        context = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String cartIds = getIntent().getStringExtra(I.Cart.ID);
        user = FuLiCenterApplication.getUser();
        if (cartIds == null || cartIds.equals("") || user == null){
            finish();
        }
        ids = cartIds.split(",");
        geOrderList();

    }

    private void geOrderList() {
        NetDao.downloadCart(context, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                if (list == null || list.size() == 0){
                    finish();
                }else{
                    mList.addAll(list);
                    sumPrince();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void sumPrince(){
        ranPrince = 0;
        if (mList != null && mList.size()>0){
            for (CartBean c : mList){
                for (String id : ids){
                    if (id.equals(String.valueOf(c.getId()))){
                        ranPrince += getPrince(c.getGoods().getRankPrice())*c.getCount();
                    }
                }
            }
        }
        tvOrderPrince.setText("合计：￥"+Double.valueOf(ranPrince));
    }
    private int getPrince(String price){
        price = price.substring(price.indexOf("￥")+1);
        return Integer.valueOf(price);
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.ivBack, R.id.btn_buyOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(context);
                break;
            case R.id.btn_buyOrder:
                String receiverName = etConsignee.getText().toString();
                if (TextUtils.isEmpty(receiverName)){
                    etConsignee.setError("收货人姓名不能为空");
                    etConsignee.requestFocus();
                    return;
                }
                String mobile = etTel.getText().toString();
                if (TextUtils.isEmpty(mobile)){
                    etTel.setError("手机号码不能为空");
                    etTel.requestFocus();
                    return;
                }
                if (!mobile.matches("[\\d]{11}")){
                    etTel.setError("手机号码格式错误");
                    etTel.requestFocus();
                    return;
                }
                String area = spPlace.getSelectedItem().toString();
                if (TextUtils.isEmpty(area)){
                    Toast.makeText(OrderActivity.this,"收货地区不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String address = etStreet.getText().toString();
                if (TextUtils.isEmpty(address)){
                    etStreet.setError("街道地区不能为空");
                    etStreet.requestFocus();
                    return;
                }
                gotoStatements();
                break;
        }
    }
    private void gotoStatements(){

    }
}
