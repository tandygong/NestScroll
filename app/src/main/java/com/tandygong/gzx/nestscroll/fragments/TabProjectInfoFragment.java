package com.tandygong.gzx.nestscroll.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tandygong.gzx.nestscroll.R;


/**
 * Created by gengxin on 2015/12/16.
 */
public class TabProjectInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_info, null);
        return view;
    }
}
