package day1010.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.R;
import day1010.fulicenter.fragment.BoutiqueFragment;
import day1010.fulicenter.fragment.NewGoodsFragment;
import day1010.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rb_new_good)
    RadioButton rbNewGood;
    @Bind(R.id.rb_boutique)
    RadioButton rbBoutique;
    @Bind(R.id.rb_category)
    RadioButton rbCategory;
    @Bind(R.id.tvCartCount)
    TextView tvCartCount;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_personal)
    RadioButton rbPersonal;

    Fragment [] mFragments;
    int index;
    int currentIndex;
    RadioButton[] rbs;
    NewGoodsFragment newGoodsFragment;
    BoutiqueFragment boutiqueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("main", "onCreate");
        initView();
        initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        newGoodsFragment = new NewGoodsFragment();
        boutiqueFragment = new BoutiqueFragment();
        mFragments[0] = newGoodsFragment;
        mFragments[1] = boutiqueFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment,newGoodsFragment)
                .add(R.id.fragment,boutiqueFragment)
                .hide(boutiqueFragment)
                .show(newGoodsFragment)
                .commit();
    }

    private void initView() {
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
                index = 3;
                break;
            case R.id.rb_personal:
                index = 4;
                break;
        }
        setFragment();
        setRadioButtonStatus();
    }

    private void setFragment() {
        if (index != currentIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()){
             ft.add(R.id.fragment,mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }
}
