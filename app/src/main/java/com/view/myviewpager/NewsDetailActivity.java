package com.view.myviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Destiny on 2017/1/19.
 */

public class NewsDetailActivity extends AppCompatActivity {
    RecyclerView rv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        initData();
    }

    private void initData() {
        LinearLayoutManager manger=new LinearLayoutManager(this);
        manger.setOrientation(LinearLayoutManager.VERTICAL);
        rv_main.setLayoutManager(manger);
        rv_main.setAdapter(new RvAdapter(this));
    }

    private void initView() {
        rv_main= (RecyclerView) findViewById(R.id.rv_main);
    }


}
