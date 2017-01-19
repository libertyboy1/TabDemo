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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Destiny on 2017/1/12.
 */

public class MainActivity2 extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private LinearLayout ll_tab;
    private ViewPager vp_main;
    private HorizontalScrollView hsv_main;

//        private String tabs[] = new String[]{"热点热点", "精选精选", "军事军事", "娱乐娱乐", "糗事糗事", "美女美女", "体育体育", "国际国际", "科技科技", "美术美术", "视频视频", "图片图片"};
    private String tabs[] = new String[]{"热点", "精选", "军事", "娱乐", "糗事", "美女", "体育", "国际", "科技", "美术", "视频", "图片"};
    //    private String tabs[] = new String[]{"热点", "精选精", "军", "娱", "糗事糗事", "美", "体育体", "国际国", "科技科技技", "美术", "视频频频频", "图片"};
    private ArrayList<TextView> tvs = new ArrayList<TextView>();
//    private ArrayList<Integer> checkTvWidth=new ArrayList<Integer>();
//    private ArrayList<Integer> normalTvWidth=new ArrayList<Integer>();

    private int offsetPixels;
    private int currentPixels;
    private int position;
    private float currentX;

    private LinearLayout.LayoutParams scrollViewParams;
    private boolean isMove = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        hsv_main = (HorizontalScrollView) findViewById(R.id.hsv_main);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(35));


        final TextView first = new TextView(this);
        first.setLayoutParams(params);
        first.setText(tabs[2]);
        first.setPadding(dip2px(10), 0, dip2px(10), 0);
        first.setVisibility(View.INVISIBLE);
        first.setTextSize(14);
        ll_tab.addView(first);

        for (int i = 0; i < tabs.length; i++) {
            final TextView tab = new TextView(this);
            tab.setLayoutParams(params);
            tab.setText(tabs[i]);
            tab.setGravity(Gravity.BOTTOM);
            tab.setPadding(dip2px(10), 0, dip2px(10), 0);

//            ViewTreeObserver vto1 = tab.getViewTreeObserver();
//            vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    tab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    Log.e("MainActivity", tab.getText() + "---" + tab.getTextSize() + "---" + tab.getWidth());
//                }
//            });


            tab.setOnClickListener(this);

            ll_tab.addView(tab);


            TextView data = new TextView(this);
            data.setText(i + "");
            data.setTextSize(30);
            tvs.add(data);
        }

        final TextView last = new TextView(this);
        last.setLayoutParams(params);
        last.setText(tabs[tabs.length - 2]);
        last.setPadding(dip2px(10), 0, dip2px(10), 0);
        last.setVisibility(View.INVISIBLE);
        last.setTextSize(14);

        ViewTreeObserver vto = last.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                last.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollViewParams = new LinearLayout.LayoutParams(first.getWidth() + ll_tab.getChildAt(1).getWidth() + ll_tab.getChildAt(2).getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                hsv_main.setLayoutParams(scrollViewParams);
            }
        });

        ll_tab.addView(last);


        vp_main.setAdapter(new MyViewPagerAdapter());
        vp_main.addOnPageChangeListener(this);


        hsv_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    currentX = arg1.getX();
                } else if (arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    Log.e("MainActivity", "position:" + position);

                    if (currentX == 0)
                        currentX = arg1.getX();

                    if (currentX - arg1.getX() > 50) {
                        if (isMove) {
                            hsv_main.smoothScrollTo(ll_tab.getChildAt(1).getWidth() * (position + 1), 0);
                            vp_main.setCurrentItem(position + 1);
                            if (position < tvs.size() - 1) {
                                isMove = false;
                            }

                        }
                    } else if (currentX - arg1.getX() < -50) {
                        if (isMove) {
                            hsv_main.smoothScrollTo(ll_tab.getChildAt(1).getWidth() * (position - 1), 0);
                            vp_main.setCurrentItem(position - 1);
                            if (position > 0) {
                                isMove = false;
                            }
                        }
                    }

                }

                return true;
            }
        });

    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        /*********计算随着手指滑动距离，TAB控件所移动的偏移量***********/
        offsetPixels = (int) (ll_tab.getChildAt(position + 1).getWidth() * positionOffset);

        /*********计算当前已选中TAB的位置***********/
        currentPixels = ll_tab.getChildAt(position).getWidth() * position;

        /*********移动视图***********/
        hsv_main.smoothScrollTo(currentPixels + offsetPixels, 0);

        /*********根据手指滑动距离，动态改变字体大小***********/
        ((TextView) ll_tab.getChildAt(position + 1)).setTextSize(22 - 8 * positionOffset);
        ((TextView) ll_tab.getChildAt(position + 2)).setTextSize(14 + 8 * positionOffset);

//        if (positionOffset==0){
//            Log.e("MainActivity", ((TextView) ll_tab.getChildAt(position)).getText() + "----size：" + ((TextView) ll_tab.getChildAt(position)).getTextSize() + "----Width:" + ll_tab.getChildAt(position).getWidth());
//            Log.e("MainActivity", ((TextView) ll_tab.getChildAt(position + 1)).getText() + "----size：" + ((TextView) ll_tab.getChildAt(position + 1)).getTextSize() + "----Width:" + ll_tab.getChildAt(position + 1).getWidth());
//            Log.e("MainActivity", ((TextView) ll_tab.getChildAt(position + 2)).getText() + "----size：" + ((TextView) ll_tab.getChildAt(position + 2)).getTextSize() + "----Width:" + ll_tab.getChildAt(position + 2).getWidth());
//            scrollViewParams = new LinearLayout.LayoutParams(ll_tab.getChildAt(position).getWidth() + ll_tab.getChildAt(position + 1).getWidth() + ll_tab.getChildAt(position + 2).getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//            hsv_main.setLayoutParams(scrollViewParams);
//        }

//        Log.e("MainActivity", "onPageScrolled");

    }

    @Override
    public void onPageSelected(int position) {

//        totalPixels += ll_tab.getChildAt(position - 1).getWidth();

//        Log.e("MainActivity", "ll_tab.getChildAt(position).getWidth():" + ll_tab.getChildAt(position-1).getWidth());
//
//        Log.e("MainActivity", "totalPixels:" + totalPixels);
//
//

        this.position = position;

//        Log.e("MainActivity", "onPageSelected:");
//
//        hsv_main.smoothScrollTo(totalPixels, 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.e("MainActivity", "state:" + state);

//            this.position+=1;
        Log.e("MainActivity", "state:" + state);

        if (state == 0) {
            isMove = true;
//            Log.e("MainActivity", "onPageScrollStateChanged:");
        }

//        if (state == 1) {
//
//            Log.e("MainActivity", "position:" + position);
//            Log.e("MainActivity", ((TextView) ll_tab.getChildAt(position + 1)).getText() + "----size：" + ((TextView) ll_tab.getChildAt(position + 1)).getTextSize() + "----Width:" + ll_tab.getChildAt(position + 1).getWidth());
//            Log.e("MainActivity", ((TextView) ll_tab.getChildAt(position + 2)).getText() + "----size：" + ((TextView) ll_tab.getChildAt(position + 2)).getTextSize() + "----Width:" + ll_tab.getChildAt(position + 2).getWidth());
//            Log.e("MainActivity", ((TextView) ll_tab.getChildAt(position + 3)).getText() + "----size：" + ((TextView) ll_tab.getChildAt(position + 3)).getTextSize() + "----Width:" + ll_tab.getChildAt(position + 3).getWidth());
//        }
//
//        scrollViewParams = new LinearLayout.LayoutParams(ll_tab.getChildAt(position).getWidth() + ll_tab.getChildAt(position + 1).getWidth() + ll_tab.getChildAt(position + 2).getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//        hsv_main.setLayoutParams(scrollViewParams);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
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
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
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
