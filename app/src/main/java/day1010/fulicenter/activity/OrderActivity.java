package day1010.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.CartBean;
import day1010.fulicenter.bean.MessageBean;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.utils.ResultUtils;

/**
 * Created by Administrator on 2016/10/28.
 */
public class OrderActivity extends BaseActivity implements PaymentHandler {
    private static String URL = "http://218.244.151.190/demo/charge";

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
    String[] ids = new String[]{};
    int ranPrince = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        context = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String cartIds = getIntent().getStringExtra(I.Cart.ID);
        user = FuLiCenterApplication.getUser();
        if (cartIds == null || cartIds.equals("") || user == null) {
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
                if (list == null || list.size() == 0) {
                    finish();
                } else {
                    mList.addAll(list);
                    sumPrince();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void sumPrince() {
        ranPrince = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                for (String id : ids) {
                    if (id.equals(String.valueOf(c.getId()))) {
                        ranPrince += getPrince(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
        }
        tvOrderPrince.setText("合计：￥" + Double.valueOf(ranPrince));
    }

    private int getPrince(String price) {
        price = price.substring(price.indexOf("￥") + 1);
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
                if (TextUtils.isEmpty(receiverName)) {
                    etConsignee.setError("收货人姓名不能为空");
                    etConsignee.requestFocus();
                    return;
                }
                String mobile = etTel.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    etTel.setError("手机号码不能为空");
                    etTel.requestFocus();
                    return;
                }
                if (!mobile.matches("[\\d]{11}")) {
                    etTel.setError("手机号码格式错误");
                    etTel.requestFocus();
                    return;
                }
                String area = spPlace.getSelectedItem().toString();
                if (TextUtils.isEmpty(area)) {
                    Toast.makeText(OrderActivity.this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String address = etStreet.getText().toString();
                if (TextUtils.isEmpty(address)) {
                    etStreet.setError("街道地区不能为空");
                    etStreet.requestFocus();
                    return;
                }
                gotoStatements();
                break;
        }
    }

    private void gotoStatements() {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 构建账单json对象
        JSONObject bill = new JSONObject();

        // 自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", ranPrince*100);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);

    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {

            // result：支付结果信息
            // code：支付结果码
            //-2:用户自定义错误
            //-1：失败
            // 0：取消
            // 1：成功
            // 2:应用内快捷支付支付结果

            if (data.getExtras().getInt("code") != 2) {
                PingppLog.d(data.getExtras().getString("result") + "  " + data.getExtras().getInt("code"));
            } else {
                String result = data.getStringExtra("result");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    if (resultJson.has("error")) {
                        result = resultJson.optJSONObject("error").toString();
                    } else if (resultJson.has("success")) {
                        result = resultJson.optJSONObject("success").toString();
                    }
                    PingppLog.d("result::" + result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            int resultCode = data.getExtras().getInt("code");
            switch(resultCode){
                case 1:
                    paySuccess();
                    CommonUtils.showLongToast(R.string.pingpp_title_activity_pay_sucessed);
                    break;
                case -1:
                    CommonUtils.showLongToast(R.string.pingpp_pay_failed);
                    finish();
                    break;
            }
        }
    }

    private void paySuccess() {
        for (String id:ids){
            NetDao.delCart(context, Integer.valueOf(id), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {

                }

                @Override
                public void onError(String error) {

                }
            });
        }
        finish();
    }
}
