package day1010.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import day1010.fulicenter.I;
import day1010.fulicenter.activity.BoutiqueChildActivity;
import day1010.fulicenter.activity.GoodsDetailActivity;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.BoutiqueBean;

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
       startActivity(context,intent);
    }
    public static void gotoGoodsDetailsActivity(Activity context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }
    public static void startActivity(Activity context,Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoBoutiqueChildActivity(Activity context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }
}
