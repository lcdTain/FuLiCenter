package day1010.fulicenter.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import day1010.fulicenter.FuLiCenterApplication;
import day1010.fulicenter.R;
import day1010.fulicenter.bean.User;
import day1010.fulicenter.dao.SharePreferenceUtils;
import day1010.fulicenter.dao.UserDao;
import day1010.fulicenter.utils.L;
import day1010.fulicenter.utils.MFGT;

;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private final long sleepTime = 2000;
    SplashActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.context = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = FuLiCenterApplication.getUser();
                L.e(TAG,"fulicenter,user="+user);
                String username = SharePreferenceUtils.getInstance(context).getUser();
                L.e(TAG,"fulicenter,username="+username);
                if (user == null && username != null){
                    UserDao dao = new UserDao(context);
                    user = dao.getUser(username);
                    L.e(TAG,"database,user="+user);
                    if (user != null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        }, sleepTime);
    }
}
