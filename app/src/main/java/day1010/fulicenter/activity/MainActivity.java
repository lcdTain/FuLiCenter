package day1010.fulicenter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.I;
import day1010.fulicenter.fragment.CartFragment;
import day1010.fulicenter.fragment.CategoryFragment;
import day1010.fulicenter.R;
import day1010.fulicenter.fragment.BoutiqueFragment;
import day1010.fulicenter.fragment.NewGoodsFragment;
import day1010.fulicenter.fragment.PresonalCenterFragment;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {

    @Bind(R.id.rb_new_good)
    RadioButton rbNewGood;
    @Bind(R.id.rb_boutique)
    RadioButton rbBoutique;
    @Bind(R.id.rb_category)
    RadioButton rbCategory;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_personal)
    RadioButton rbPersonal;

    Fragment[] mFragments;
    int index;
    int currentIndex;
    RadioButton[] rbs;
    NewGoodsFragment newGoodsFragment;
    BoutiqueFragment boutiqueFragment;
    CategoryFragment categoryFragment;
    PresonalCenterFragment presonalCenterFragment;
    CartFragment cartFragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("main", "onCreate");
        super.onCreate(savedInstanceState);
    }


    private void initFragment() {
        mFragments = new Fragment[5];
        newGoodsFragment = new NewGoodsFragment();
        boutiqueFragment = new BoutiqueFragment();
        categoryFragment = new CategoryFragment();
        presonalCenterFragment = new PresonalCenterFragment();
        cartFragment = new CartFragment();
        mFragments[0] = newGoodsFragment;
        mFragments[1] = boutiqueFragment;
        mFragments[2] = categoryFragment;
        mFragments[3] = cartFragment;
        mFragments[4] = presonalCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, newGoodsFragment)
//                .add(R.id.fragment, boutiqueFragment)
//                .add(R.id.fragment, categoryFragment)
//                .add(R.id.fragment,cartFragment)
//                .hide(boutiqueFragment)
//                .hide(categoryFragment)
                .show(newGoodsFragment)
                .commit();
    }

    @Override
    protected void initView() {
        rbs = new RadioButton[5];
        rbs[0] = rbNewGood;
        rbs[1] = rbBoutique;
        rbs[2] = rbCategory;
        rbs[3] = rbCart;
        rbs[4] = rbPersonal;
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.rb_new_good:
                index = 0;
                break;
            case R.id.rb_boutique:
                index = 1;
                break;
            case R.id.rb_category:
                index = 2;
                break;
            case R.id.rb_cart:
                if (FuLiCenterApplication.getUser() == null){
                    MFGT.gotoLoginFromCart(this);
                }else{
                    index = 3;
                }
                break;
            case R.id.rb_personal:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLoginActivity(this);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (index != currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fragment, mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    protected void setRadioButtonStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index == 4 && FuLiCenterApplication.getUser() == null){
            index = 0;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FuLiCenterApplication.getUser() != null) {
            if (requestCode == I.REQUEST_CODE_LOGIN ){
                index = 4;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART){
                index = 3;
            }
        }
    }

}
