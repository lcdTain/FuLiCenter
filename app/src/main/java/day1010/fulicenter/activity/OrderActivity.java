package day1010.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.utils.MFGT;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String cartIds = getIntent().getStringExtra(I.Cart.ID);

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
                break;
        }
    }
}
