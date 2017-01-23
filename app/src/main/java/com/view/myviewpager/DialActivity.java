package com.view.myviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Destiny on 2017/1/22.
 */

public class DialActivity extends AppCompatActivity implements Runnable {
    private RecyclerView rv_main;

    private String[] strs = new String[]{"苹果", "鸭梨", "橙子", "榴莲", "开始", "蓝莓", "葡萄", "桃子", "西瓜"};
//    private Integer[] positions = new Integer[]{0, 1, 2, 5, 8, 7, 6, 3};

    private boolean isStop = false;

    private Random random = new Random();

    private GridLayoutManager manager;

    private int currentPosition=0, frontPosition=0;

    private int i = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isStop = true;
            i = 0;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        rv_main = (RecyclerView) findViewById(R.id.rv_main);

        manager = new GridLayoutManager(this, 3);

        rv_main.setLayoutManager(manager);

        rv_main.setAdapter(new MyAdapter());


    }

    @Override
    public void run() {
        while (!isStop) {

            if (i > 0) {
                i++;
            }

            try {
                Thread.sleep(80 + i * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /**************转圈***************/
//                    if (currentPosition == 0) {
//                        manager.getChildAt(positions[positions.length - 1]).setBackgroundColor(Color.WHITE);
//                    }
//
//                    manager.getChildAt(positions[currentPosition]).setBackgroundColor(Color.LTGRAY);
//
//                    if (currentPosition > 0) {
//                        manager.getChildAt(positions[currentPosition - 1]).setBackgroundColor(Color.WHITE);
//                    }
//
//                    currentPosition++;
//
//                    if (currentPosition > positions.length - 1) {
//                        currentPosition = 0;
//                    }
                    /**************转圈***************/

                    /**************随机***************/
                    currentPosition = random.nextInt(9);
                    if (currentPosition == frontPosition)
                        return;
                    if (currentPosition == 4)
                        return;
                    manager.getChildAt(currentPosition).setBackgroundColor(Color.LTGRAY);
                    manager.getChildAt(frontPosition).setBackgroundColor(Color.WHITE);
                    frontPosition = currentPosition;
                    /**************随机***************/
                }
            });
        }
    }

    private void start() {
        isStop = false;
        new Thread(this).start();
    }

    private void stop() {
        i = 1;
        handler.sendEmptyMessageDelayed(0x1, 5000);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dial, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyAdapter.MyViewHolder holder, int position) {
            holder.tv_name.setText(strs[position]);
            if (position == 4) {
                holder.tv_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.tv_name.getText().toString().equals("结束")) {
                            stop();
                            holder.tv_name.setText("开始");
                        } else {
                            start();
                            holder.tv_name.setText("结束");
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 9;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }

    }


}
