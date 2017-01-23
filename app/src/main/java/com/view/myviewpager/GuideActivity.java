package com.view.myviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Destiny on 2017/1/19.
 */

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_main;
    private LinearLayout ll_round;
    private TextView tv_start;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<TextView> rounds = new ArrayList<TextView>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        vp_main = (ViewPager) findViewById(R.id.vp_main);
        ll_round = (LinearLayout) findViewById(R.id.ll_round);
        tv_start= (TextView) findViewById(R.id.tv_start);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
        params.setMargins(20, 20, 20, 20);

        for (int i = 0; i < 3; i++) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            if (i == 0) {
                tv.setBackgroundResource(R.drawable.check_round);
            } else {
                tv.setBackgroundResource(R.drawable.normal_round);
            }
            ll_round.addView(tv);

            rounds.add(tv);

            GuideFragment fragment = new GuideFragment(i);
            fragments.add(fragment);
        }

        vp_main.setPageTransformer(true, new DepthPageTransformer());

        vp_main.addOnPageChangeListener(this);
        vp_main.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (TextView tv : rounds) {
            tv.setBackgroundResource(R.drawable.normal_round);
        }

        rounds.get(position).setBackgroundResource(R.drawable.check_round);

        if (position==2){
            tv_start.setVisibility(View.VISIBLE);
            ll_round.setVisibility(View.INVISIBLE);
        }else{
            tv_start.setVisibility(View.INVISIBLE);
            ll_round.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
