package day1010.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.utils.L;

public class GoodsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        int goodsID = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID,0);
        L.e("main","goodsId="+goodsID);
    }
}
