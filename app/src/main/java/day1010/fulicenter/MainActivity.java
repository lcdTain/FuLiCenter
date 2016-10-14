package day1010.fulicenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import day1010.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("main","onCreate");
    }
    public void onCheckedChange(View v){

    }
}
