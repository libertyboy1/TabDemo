package com.view.myviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Destiny on 2017/1/19.
 */

public class GuideFragment extends Fragment {
    private View view;
    private int position;

    private ImageView iv_img, iv_text;

    public GuideFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guide, container, false);

        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        iv_text = (ImageView) view.findViewById(R.id.iv_text);


        switch (position) {
            case 0:
                iv_img.setImageResource(R.mipmap.map);
                iv_text.setImageResource(R.mipmap.map_text);
                break;
            case 1:
                iv_img.setImageResource(R.mipmap.language);
                iv_text.setImageResource(R.mipmap.language_text);
                break;
            case 2:
                iv_img.setImageResource(R.mipmap.camera);
                iv_text.setImageResource(R.mipmap.camera_text);
                break;
        }

        return view;
    }
}
