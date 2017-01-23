package com.view.myviewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.R.id.tabs;

/**
 * 自定义控件循环走马灯的实现
 *
 * @author cyf 继承自TextView
 */
public class MarqueeView extends View implements Runnable {
    private static final String TAG = "MarqueeTextView";
    // 设置跑马灯重复的次数，次数
    private int circleTimes = 3;
    //记录已经重复了多少遍
    private int hasCircled = 0;
    private int currentScrollPos = 0;
    // 跑马灯走一遍需要的时间（秒数）
    private int circleSpeed = 5;
    // 文字的宽度
    private int textWidth = 0;
    // Handler机制
    private boolean flag = false;

    private Paint mTextPaint;

    private int height;

    private String text = "这是一个很长很长很长很长的标题，这个标题具体是做什么用的呢？别捉急，让我们慢慢来看，等你读完这个标题的时候，你就知道它是做什么的了。好了，现在，你知道它是做什么用的了吧！没有错，就是我闲着无聊逗你的呢！";

    // 构造方法
    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initData();
    }

    private void initData(){

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(60);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        height = (int) Math.ceil(fm.descent - fm.ascent);

        textWidth = (int) mTextPaint.measureText(text);

        this.removeCallbacks(this);

        Log.e(TAG, "getWidth():" + ((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth());

        if (textWidth>((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth()){
            post(this);
        }


    }

    /**
     * 画笔工具
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text, 0, height, mTextPaint);

    }

    @Override
    public void setVisibility(int visibility) {
        // 二次进入时初始化成员变量
        flag = false;
        this.hasCircled = 0;
        super.setVisibility(visibility);
    }

    @Override
    public void run() {
        // 起始滚动位置
        currentScrollPos += 1;
        scrollTo(currentScrollPos, 0);
        // Log.i(TAG, "pos"+currentScrollPos);
        // 判断滚动一次
        if (currentScrollPos >= textWidth) {
            // 从屏幕右侧开始出现
            if (hasCircled == 0) {
                currentScrollPos = 0;
            } else {
                currentScrollPos = -this.getWidth();
            }

            //记录的滚动次数大设定的次数代表滚动完成，这个控件就可以隐藏了
            if (hasCircled >= this.circleTimes) {
                this.setVisibility(View.GONE);
                flag = true;
            }
            hasCircled += 1;
        }

        if (!flag) {
            // 滚动时间间隔
            postDelayed(this, circleSpeed);
        }
    }


    /**
     * 设置滚动次数，达到次数后设置不可见
     *
     * @param circleTimes
     */
    public void setCircleTimes(int circleTimes) {
        this.circleTimes = circleTimes;
    }

    public void setSpeed(int speed) {
        this.circleSpeed = speed;
    }

    public void startScrollShow() {
        if (this.getVisibility() == View.GONE)
            this.setVisibility(View.VISIBLE);
        this.removeCallbacks(this);
        post(this);
    }

    private void stopScroll() {
        this.removeCallbacks(this);
    }
}