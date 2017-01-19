package com.view.myviewpager;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Destiny on 2017/1/13.
 */

public class MyHorizontalScrollView extends View {
    private Context mContext;//上下文对象

    private String tabs[];//TAB集合

    private Paint mCheckTextPaint;//选中字体画笔
    private Paint mNormalTextPaint;//未选中字体画笔

    private final int SPACING = 40;//文字与文字之间的距离
    private final int CHECK_TEXT_SIZE = 28;//默认选中字体大小
    private final int NORMAL_TEXT_SIZE = 16;//默认未选中字体大小
    private int CHECK_TEXT_COLOR = Color.WHITE;//默认选中字体颜色
    private int NORMAL_TEXT_COLOR = Color.parseColor("#666666");//默认未选中字体颜色

    private float positionOffset;//移动距离百分比
    private int offsetPixels;//移动距离
    private float coordinateX;//中间选中文字X坐标
    private float behindCoordinateX;//右面未选中字体X坐标
    private float frontCoordinateX;//左面未选中字体X做坐标
    private float moveX;//TAB移动到屏幕中间位置所需的距离
    private int height;//控件高度
    private int centerPosition = 0;//当前选中TAB的下标
    private float downX;//第一次按下X坐标
    private boolean isMove = true;//是否在移动

    private OnCheckChangedListener onCheckChangedListener;//中间选中TAB改变监听器
    private ArgbEvaluator evaluator = new ArgbEvaluator();

    private int checkTextSize = CHECK_TEXT_SIZE;//已选中字体大小
    private int normalTextSize = NORMAL_TEXT_SIZE;//未选中字体大小
    private int checkTextColor = CHECK_TEXT_COLOR;//已选中字体颜色
    private int normalTextColor = NORMAL_TEXT_COLOR;//未选中字体颜色


    public interface OnCheckChangedListener {
        void onCheckChangedListener(int position);
    }

    public MyHorizontalScrollView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    /*************
     * 初始化画笔以及计算控件高度
     *************/
    private void initPaint() {
        /***********初始化选中文字画笔***************/
        mCheckTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckTextPaint.setColor(checkTextColor);
        mCheckTextPaint.setTextSize(dip2px(checkTextSize));
        /************初始化选中文字画笔**************/

        /***********初始化未选中文字画笔***************/
        mNormalTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalTextPaint.setColor(normalTextColor);
        mNormalTextPaint.setTextSize(dip2px(normalTextSize));
        /**********初始化未选中文字画笔****************/

        /**********计算控件高度****************/
        Paint.FontMetrics fm = mCheckTextPaint.getFontMetrics();
        height = (int) Math.ceil(fm.descent - fm.ascent);
        /**********计算控件高度****************/

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (tabs!=null&&tabs.length>0) {
            //计算移动距离
            offsetPixels = (int) (moveX * positionOffset);

            if (positionOffset != 0) {//当前正在移动
                //字体大小根据移动距离动态改变
                mCheckTextPaint.setTextSize(dip2px(checkTextSize - (checkTextSize - normalTextSize) * positionOffset));
                mNormalTextPaint.setTextSize(dip2px(normalTextSize + (checkTextSize - normalTextSize) * positionOffset));

                //设置字体颜色渐变效果
                mCheckTextPaint.setColor((Integer) evaluator.evaluate(positionOffset, checkTextColor, normalTextColor));
                mNormalTextPaint.setColor((Integer) evaluator.evaluate(positionOffset, normalTextColor, checkTextColor));

            } else {//当前移动完毕
                //字体大小初始化还原为默认值
                mCheckTextPaint.setTextSize(dip2px(checkTextSize));
                mCheckTextPaint.setColor(checkTextColor);
                //字体颜色初始化还原为默认值
                mNormalTextPaint.setTextSize(dip2px(normalTextSize));
                mNormalTextPaint.setColor(normalTextColor);
            }

            /************中间TAB*************/
            //计算选中TAB的初始X坐标
            coordinateX = getWidth() / 2 - mCheckTextPaint.measureText(tabs[centerPosition]) / 2;
            //开始绘画
            canvas.drawText(tabs[centerPosition], coordinateX - offsetPixels, height - 15, mCheckTextPaint);
            /************中间TAB*************/

            /**********************右面未选中TAB************************/
            for (int i = 1; i < tabs.length - centerPosition; i++) {
                if (i == 1) {
                    //右面据中间最近的TAB，始终可见（透明度为0）
                    mNormalTextPaint.setAlpha(255);
                    //计算开始X坐标
                    behindCoordinateX = coordinateX + mCheckTextPaint.measureText(tabs[centerPosition]) + SPACING;
                    //计算当前TAB移动到中间位置所需要移动的距离
                    moveX = behindCoordinateX - getWidth() / 2 + mNormalTextPaint.measureText(tabs[centerPosition + i]) / 2;
                } else {
                    //右面第一个以外的TAB

                    //初始化字体颜色
                    if (mNormalTextPaint.getColor() != normalTextColor)
                        mNormalTextPaint.setColor(normalTextColor);

                    if (i > 2) {
                        //如果TAB是第二个以后，始终不可见（透明度100%）
                        mNormalTextPaint.setAlpha(0);
                    } else {
                        //如果是第二个TAB，则TAB透明度动态设置(随着滑动距离百分比设置)
                        mNormalTextPaint.setAlpha((int) (0 + 255 * positionOffset));
                    }
                    //计算TAB开始X坐标
                    behindCoordinateX = behindCoordinateX + mNormalTextPaint.measureText(tabs[centerPosition + i - 1]) + SPACING;
                    //将未选中字体大小设置成初始化默认字体大小
                    mNormalTextPaint.setTextSize(dip2px(normalTextSize));
//
                }
                //开始绘画
                canvas.drawText(tabs[centerPosition + i], behindCoordinateX - offsetPixels, height - 15, mNormalTextPaint);
            }
            /**********************右面未选中TAB************************/

            /**********************左面未选中TAB************************/
            //将字体大小设置为初始未选中字体大小
            mNormalTextPaint.setTextSize(dip2px(normalTextSize));
            mNormalTextPaint.setColor(normalTextColor);

            for (int i = 0; i < centerPosition; i++) {
                if (i == 0) {
                    //左面距离中间最近的TAB，TAB透明度随着滑动距离动态改变
                    mNormalTextPaint.setAlpha((int) (255 - 255 * positionOffset));
                    //计算开始X坐标
                    frontCoordinateX = coordinateX - mNormalTextPaint.measureText(tabs[centerPosition - i - 1]) - SPACING;
                } else {
                    //其余TAB射中不可见
                    mNormalTextPaint.setAlpha(0);
                    //计算开始X坐标
                    frontCoordinateX = frontCoordinateX - mNormalTextPaint.measureText(tabs[centerPosition - i - 1]) - SPACING;
                }
                //开始绘画
                canvas.drawText(tabs[centerPosition - i - 1], frontCoordinateX - offsetPixels, height - 15, mNormalTextPaint);
            }
            /**********************左面未选中TAB************************/
        }
    }

    /*************
     * dp转px
     *************/
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                Log.e("MyHorizontalScrollView", "按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("MyHorizontalScrollView", "移动");
                if (downX - event.getX() > 100) {
                    Log.e("MyHorizontalScrollView", "左");
                    if (isMove) {
                        if (centerPosition < tabs.length - 1) {
                            isMove = false;
                            centerPosition++;
                            if (onCheckChangedListener != null) {
                                onCheckChangedListener.onCheckChangedListener(centerPosition);
                            }
                            invalidate();
                        }
                    }
                } else if (downX - event.getX() < -100) {
                    Log.e("MyHorizontalScrollView", "右");
                    if (isMove) {
                        if (centerPosition > 0) {
                            isMove = false;
                            centerPosition--;
                            if (onCheckChangedListener != null) {
                                onCheckChangedListener.onCheckChangedListener(centerPosition);
                            }
                            invalidate();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("MyHorizontalScrollView", "抬起");
                isMove = true;
                break;
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置测量尺寸
        setMeasuredDimension(widthMeasureSpec, height);
    }

    /*************
     * 设置当前选中TAB下标
     *************/
    public void setCHeckPosition(int position) {
        centerPosition = position;
        invalidate();
    }

    /*************
     * 设置TAB集合
     *************/
    public void setTabs(String[] tabs) {
        this.tabs = tabs;
        invalidate();
    }

    /*************
     * 设置移动距离百分比以及当前页面下标
     *************/
    public void setPositionOffset(float positionOffset, int position) {
        this.positionOffset = positionOffset;
        centerPosition = position;
        invalidate();
    }

    /*************
     * 设置TAB改变监听器
     *************/
    public void setOnCheckChangedListener(OnCheckChangedListener onCheckChangedListener) {
        this.onCheckChangedListener = onCheckChangedListener;
    }

    /*************
     * 设置已选中字体颜色
     *************/
    public void setCheckedTextColor(int checkTextColor) {
        this.checkTextColor = checkTextColor;
    }

    /*************
     * 设置未选中字体颜色
     *************/
    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    /*************
     * 设置已选中字体大小
     *************/
    public void setCheckedTextSize(int checkTextSize) {
        this.checkTextSize = checkTextSize;
    }

    /*************
     * 设置未选中字体大小
     *************/
    public void setNormalTextSize(int normalTextSize) {
        this.normalTextSize = normalTextSize;
    }

}
