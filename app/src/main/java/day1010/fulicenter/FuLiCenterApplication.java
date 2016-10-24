package day1010.fulicenter;

import android.app.Application;
import android.content.Context;

import day1010.fulicenter.bean.User;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FuLiCenterApplication extends Application{
    private static FuLiCenterApplication instance;
    private static String username;
    private static User user;

    public FuLiCenterApplication(){
        instance = this;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null){
            instance = new FuLiCenterApplication();
        }
        return instance;
    }
    public static String getUsername(){
        return username;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    public static User getUser() {
        return user;
    }
}
