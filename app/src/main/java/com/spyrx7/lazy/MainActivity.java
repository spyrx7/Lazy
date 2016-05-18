package com.spyrx7.lazy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quseit.lazylib.widget.BannerModel;
import com.example.quseit.lazylib.widget.HeadBannerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<BannerModel> bannerModels = new ArrayList<>();
    private HeadBannerView headBannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        for(int i = 0 ;i < 4;i++){
            BannerModel temp = new BannerModel();
            temp.setImgUrl("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1408/07/c0/37179063_1407421362265_800x600.jpg");
            temp.setUrl(""+i);
            bannerModels.add(temp);
        }

        headBannerView = (HeadBannerView)findViewById(R.id.header_container);
        headBannerView.initView(bannerModels);
        headBannerView.StartLockWindowTimer();
        headBannerView.setOnUrlOnClick(new HeadBannerView.OnUrlOnClick() {
            @Override
            public void onUrlOnClick(String url) {
                Toast.makeText(MainActivity.this,url,Toast.LENGTH_LONG).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
