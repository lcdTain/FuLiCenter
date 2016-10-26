package day1010.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import day1010.fulicenter.I;
import day1010.fulicenter.activity.AmendNickActivity;
import day1010.fulicenter.activity.BoutiqueChildActivity;
import day1010.fulicenter.activity.CategoryChildActivity;
import day1010.fulicenter.activity.CollectActivity;
import day1010.fulicenter.activity.GoodsDetailActivity;
import day1010.fulicenter.activity.LoginActivity;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.PersonalInformationActivity;
import day1010.fulicenter.activity.RegisterActivity;
import day1010.fulicenter.bean.BoutiqueBean;
import day1010.fulicenter.bean.CategoryChildBean;

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        startActivity(context, intent);
    }

    public static void gotoGoodsDetailsActivity(Activity context, int goodsId) {
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity(context, intent);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoBoutiqueChildActivity(Activity context, BoutiqueBean bean) {
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID, bean);
        startActivity(context, intent);
    }

    public static void gotoCategoryChildActivity(Activity context, int catId, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID, catId);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.ID, list);
        startActivity(context, intent);
    }

    public static void gotoLoginActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivityForResult(context, intent, I.REQUEST_CODE_LOGIN);
    }

    public static void gotoRegisterActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        startActivityForResult(context, intent, I.REQUEST_CODE_REGISTER);
    }

    public static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void gotoAmendActivity(Activity context){
        startActivityForResult(context,new Intent(context,AmendNickActivity.class),I.REQUEST_CODE_NICK);
    }
    public static void gotoCollects(Activity context){
        startActivity(context, CollectActivity.class);
    }

}
