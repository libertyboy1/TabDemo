package com.view.myviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Destiny on 2017/1/22.
 */

public class FrameListViewActivity extends AppCompatActivity {
    private RecyclerView rv_main;
    private TextView tv_title;
    private Toolbar tl_title;

    private int mCurrentPosition;
    private int mSuspensionHeight;

    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelist);

        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tl_title= (Toolbar) findViewById(R.id.tl_title);

        manager = new LinearLayoutManager(this);
        rv_main.setLayoutManager(manager);
        rv_main.setAdapter(new MyAdapter());
        rv_main.setHasFixedSize(true);

        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = tv_title.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                tv_title.setText(mCurrentPosition+"");

                View view = manager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= mSuspensionHeight) {
                        tv_title.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        tv_title.setY(0);
                    }
                }

                if (mCurrentPosition != manager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = manager.findFirstVisibleItemPosition();
                    tv_title.setY(0);
                }

            }
        });

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frame_title, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_title.setText(position+"");
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            public MyViewHolder(View itemView) {
                super(itemView);
                tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            }
        }

    }

}
