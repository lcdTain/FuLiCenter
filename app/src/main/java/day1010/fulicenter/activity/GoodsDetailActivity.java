package day1010.fulicenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.AlbumsBean;
import day1010.fulicenter.bean.GoodsDetailsBean;
import day1010.fulicenter.bean.MessageBean;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.CommonUtils;
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
    @Bind(R.id.ivCollectIn)
    ImageView ivCollectIn;

    boolean isCollected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsID = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("main", "goodsId=" + goodsID);
        if (goodsID == 0) {
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
                if (result != null) {
                    showGoodsDetail(result);
                } else {
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
        salv.startPlayLoop(FlowIndicator, getAblumsUrl(result), getAlbumCount(result));
        tvGoodsDetails.setText(result.getGoodsBrief());

    }

    private int getAlbumCount(GoodsDetailsBean result) {
        if (result.getProperties() != null & result.getProperties().length > 0) {
            return result.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAblumsUrl(GoodsDetailsBean result) {
        String[] urls = new String[]{};
        if (result.getProperties() != null & result.getProperties().length > 0) {
            AlbumsBean[] albums = result.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
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
    public void onBackClick() {
        MFGT.finish(context);
    }

    private void isCollected() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.isCollect(context, user.getMuserName(), goodsID, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                    } else {
                        isCollected = false;
                    }
                    updateGoodsCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    updateGoodsCollectStatus();
                }
            });
        }
        updateGoodsCollectStatus();
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            ivCollectIn.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivCollectIn.setImageResource(R.mipmap.bg_collect_in);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }

    @OnClick(R.id.ivCollectIn)
    public void onCollectClick() {
        User user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLoginActivity(context);
        } else {
            if (isCollected) {
                NetDao.deleteCollect(context, user.getMuserName(), goodsID, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.addCollect(context, user.getMuserName(), goodsID, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }

        }
    }

    @OnClick(R.id.ivShareNor)
        public void showShare() {
            ShareSDK.initSDK(this);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle("标题");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("http://sharesdk.cn");
            // text是分享文本，所有平台都需要这个字段
            oks.setText("我是分享文本");
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("http://sharesdk.cn");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite("ShareSDK");
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
            oks.show(this);
        }
}
