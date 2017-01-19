package com.view.myviewpager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Destiny on 2017/1/19.
 */

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private final int VIDEO_ITEM = 1;//视频
    private final int RADIO_ITEM = 2;//音频
    private final int TEXT_ITEM = 3;//纯文本
    private final int IMAGE_TEXT_ITEM = 4;//图文并存

    private FrameLayout.LayoutParams lineParams;
    private RelativeLayout.LayoutParams flParams;

    public RvAdapter(Context mContext) {
        this.mContext = mContext;
        lineParams = new FrameLayout.LayoutParams(dip2px(0.5f), ViewGroup.LayoutParams.MATCH_PARENT);
        flParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lineParams.setMargins(dip2px(10), dip2px(10), 0, 0);
        flParams.setMargins(0, dip2px(20), 0, 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIDEO_ITEM) {
            return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false));
        } else if (viewType == RADIO_ITEM) {
            return new RadioViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_radio, parent, false));
        } else if (viewType == TEXT_ITEM) {
            return new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text, parent, false));
        } else if (viewType == IMAGE_TEXT_ITEM) {
            return new ImageTextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoViewHolder) {
            if (position == 0) {
                ((VideoViewHolder) holder).line.setLayoutParams(lineParams);
                ((VideoViewHolder) holder).fl_main.setLayoutParams(flParams);
            }
        } else if (holder instanceof RadioViewHolder) {
            if (position == 0) {
                ((VideoViewHolder) holder).line.setLayoutParams(lineParams);
                ((VideoViewHolder) holder).fl_main.setLayoutParams(flParams);
            }
        } else if (holder instanceof TextViewHolder) {
            if (position == 0) {
                ((VideoViewHolder) holder).line.setLayoutParams(lineParams);
                ((VideoViewHolder) holder).fl_main.setLayoutParams(flParams);
            }
        } else if (holder instanceof ImageTextViewHolder) {
            if (position == 0) {
                ((VideoViewHolder) holder).line.setLayoutParams(lineParams);
                ((VideoViewHolder) holder).fl_main.setLayoutParams(flParams);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIDEO_ITEM;
        } else if (position % 3 == 0) {
            return RADIO_ITEM;
        } else if (position % 1 == 0) {
            if (position > 10) {
                return IMAGE_TEXT_ITEM;
            }
            return TEXT_ITEM;
        }
        return super.getItemViewType(position);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        View line;
        FrameLayout fl_main;

        public VideoViewHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            fl_main = (FrameLayout) itemView.findViewById(R.id.fl_main);
        }
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder {
        View line;
        FrameLayout fl_main;

        public RadioViewHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            fl_main = (FrameLayout) itemView.findViewById(R.id.fl_main);
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        View line;
        FrameLayout fl_main;

        public TextViewHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            fl_main = (FrameLayout) itemView.findViewById(R.id.fl_main);
        }
    }

    public class ImageTextViewHolder extends RecyclerView.ViewHolder {
        View line;
        FrameLayout fl_main;

        public ImageTextViewHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            fl_main = (FrameLayout) itemView.findViewById(R.id.fl_main);
        }
    }

    /*************
     * dp转px
     *************/
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
