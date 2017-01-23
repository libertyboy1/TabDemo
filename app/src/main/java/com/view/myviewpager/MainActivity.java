package com.view.myviewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.tabs;
import static android.os.Build.VERSION_CODES.M;
import static com.view.myviewpager.R.id.hsv_main;
import static com.view.myviewpager.R.id.ll_tab;


/**
 * Created by Destiny on 2017/1/12.
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, MyHorizontalScrollView.OnCheckChangedListener {
    private ViewPager vp_main;
    private MyHorizontalScrollView mhsv_main;
    private ImageView iv_change;
//        private String tabs[] = new String[]{"热点热点热点热点热点热点", "精", "军事军事", "娱乐娱乐娱乐娱乐", "糗事", "美女美女美女美女美女美女", "体育体育体育", "国际国际国际国"};
//    private String tabs[] = new String[]{"热点热点热点热点热点热点", "精选精选精选精选精选精选", "军事军事军事军事军事军事", "娱乐娱乐娱乐娱乐娱乐娱乐", "糗事糗事糗事糗事糗事糗事", "美女美女美女美女美女美女", "体育体育体育体育体育体育", "国际国际国际国际国际国际"};
//    private String tabs[] = new String[]{"热点", "精选", "军事", "娱乐", "糗事", "美女", "体育", "国际", "科技", "美术", "视频", "图片"};
        private String tabs[] = new String[]{"热点", "精选精", "军", "娱", "糗事糗事", "美", "体育体", "国际国", "科技科技技", "美术", "视频频频频", "图片"};
    private ArrayList<TextView> tvs = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        mhsv_main = (MyHorizontalScrollView) findViewById(R.id.mhsv_main);
        iv_change = (ImageView) findViewById(R.id.iv_change);

        mhsv_main.setTabs(tabs);

        for (int i = 0; i < tabs.length; i++) {
            TextView data = new TextView(this);
            data.setText(tabs[i]);
            data.setTextSize(50);
            data.setGravity(Gravity.CENTER);
            tvs.add(data);
        }
        vp_main.setAdapter(new MyViewPagerAdapter());
        vp_main.addOnPageChangeListener(this);

        mhsv_main.setOnCheckChangedListener(this);
        mhsv_main.setCheckedTextSize(26);
        mhsv_main.setNormalTextSize(14);

        iv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabs = new String[]{"精选", "热点", "军事", "体育", "娱乐", "糗事", "美术", "美女", "国际", "图片", "科技", "视频"};
                mhsv_main.setTabs(tabs);
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mhsv_main.setPositionOffset(positionOffset, position);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckChangedListener(int position) {
        vp_main.setCurrentItem(position);
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tvs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(tvs.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(tvs.get(position));
            return tvs.get(position);
        }

    }


}
