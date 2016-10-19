package day1010.fulicenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.AlbumsBean;
import day1010.fulicenter.bean.GoodsDetailsBean;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.MFGT;
import day1010.fulicenter.utils.OkHttpUtils;
import day1010.fulicenter.view.FlowIndicator;
import day1010.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.tvEnglishName)
    TextView tvEnglishName;
    @Bind(R.id.tvGoodsDetailPrince1)
    TextView tvGoodsDetailPrince1;
    @Bind(R.id.tvChinese_Name)
    TextView tvChineseName;
    @Bind(R.id.tvGoodsDetailPrince2)
    TextView tvGoodsDetailPrince2;
    @Bind(R.id.salv)
    SlideAutoLoopView salv;
    @Bind(R.id.FlowIndicator)
    day1010.fulicenter.view.FlowIndicator FlowIndicator;
    @Bind(R.id.tvGoodsDetails)
    TextView tvGoodsDetails;
    Activity context;
    int goodsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsID = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("main", "goodsId=" + goodsID);
        if (goodsID == 0){
            MFGT.finish(context);
        }
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(context, goodsID, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null){
                    showGoodsDetail(result);
                }else{
                    finish();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showGoodsDetail(GoodsDetailsBean result) {
        tvEnglishName.setText(result.getGoodsEnglishName());
        tvGoodsDetailPrince1.setText(result.getCurrencyPrice());
        tvChineseName.setText(result.getGoodsName());
        tvGoodsDetailPrince2.setText(result.getShopPrice());
        salv.startPlayLoop(FlowIndicator,getAblumsUrl(result),getAlbumCount(result));
        tvGoodsDetails.setText(result.getGoodsBrief());

    }

    private int getAlbumCount(GoodsDetailsBean result) {
        if (result.getProperties() != null & result.getProperties().length > 0){
            return result.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAblumsUrl(GoodsDetailsBean result) {
        String [] urls = new String[]{};
        if (result.getProperties() != null & result.getProperties().length > 0){
            AlbumsBean[] albums = result.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0;i < albums.length;i++){
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }


    @Override
    protected void initView() {
        context = this;
    }

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        MFGT.finish(context);
    }
}
