package com.tandygong.gzx.nestscroll.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tandygong.gzx.nestscroll.R;
import com.tandygong.gzx.nestscroll.activitys.NestScrollWithScrollViewActivity;
import com.tandygong.gzx.nestscroll.adapters.SubTitleMenuListAdapter;
import com.tandygong.gzx.nestscroll.enums.AnimStyle;
import com.tandygong.gzx.nestscroll.utils.Constants;

import java.util.ArrayList;

/**
 * Created by gzx on 2016/2/14.
 */
public class TabSchoolInfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, DrawerLayout.DrawerListener {


    private ArrayList<String> subTitles;
    private ArrayList<String> subUrls;
    private int destPageId;
    private AnimStyle animStyle;
    private int schoolId;
    private ListView lvContent;
    private DrawerLayout drawerLayout;
    private RelativeLayout rlShowSlideMenu;
    private TextView tvContentNum;
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_school_info, null);
        getExtraData();
        findViewIds(view);
        setListeners();
        initData();
        return view;
    }

    private void getExtraData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            schoolId = arguments.getInt("schoolId");
        }
    }


    private void findViewIds(View view) {

    }

    private void setListeners() {
        //注册广播接收者,当收到广播时的操作
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    animStyle = (AnimStyle) extras.getSerializable("animStyle");
                    destPageId += animStyle == AnimStyle.TOP_TO_BOTTOM ? -1 : 1;
                }
                switchFragment(destPageId, animStyle);//切换内容
            }
        };
        getContext().registerReceiver(receiver, new IntentFilter(Constants.ACTION_REPLACE_FRAGMENT_SCHOOL_INFO));
    }

    private void initData() {
        subTitles = new ArrayList<>();
        subUrls = new ArrayList<>();
        getViewFromActivity();
        setTestData();//设置测试数据
        initSlideMenu();
        switchFragment(destPageId, AnimStyle.DEFAULT_NO_ANIM);//首次加载没有切换动画
    }

    private void getViewFromActivity() {
        NestScrollWithScrollViewActivity activity = (NestScrollWithScrollViewActivity) getActivity();
        lvContent = activity.getLvMenu();
        drawerLayout = activity.getDrawerLayout();
        rlShowSlideMenu = activity.getRlShowSlideMenu();
        tvContentNum = activity.getTvContentNum();

        drawerLayout.setDrawerListener(this);
        rlShowSlideMenu.setOnClickListener(this);
    }


    /**
     * 设置DrawerLayout右侧的菜单
     */
    private void initSlideMenu() {
        lvContent.setAdapter(new SubTitleMenuListAdapter(getActivity(), subTitles));
        lvContent.setOnItemClickListener(this);
    }


    /**
     * 根据广播接收的"要求"来定制要显示的fragment
     *
     * @param destPageId 将要切换内容的页码
     * @param animStyle  切换动画效果
     */
    private void switchFragment(int destPageId, AnimStyle animStyle) {
        if (destPageId >= subUrls.size() || destPageId < 0) {
            return;
        }
        ContainerSchoolInfoFragment fragment = getSchoolInfoContainerFragment(destPageId);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        switch (animStyle) {
            case TOP_TO_BOTTOM:
                ft.setCustomAnimations(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
                break;
            case BOTTOM_TO_TOP:
                ft.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
                break;
            case DEFAULT_NO_ANIM:
                break;
        }
        try {
            ft.replace(R.id.fl_container, fragment);
            ft.commitAllowingStateLoss();
            tvContentNum.setText(destPageId + 1 + "/" + subUrls.size());
        } catch (Exception e) {
            Log.e("exception", e.getMessage());//捕捉页面还没加载就退出activity引起的异常
        }
    }

    @NonNull
    private ContainerSchoolInfoFragment getSchoolInfoContainerFragment(int destPageId) {
        ContainerSchoolInfoFragment fragment = new ContainerSchoolInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PageUrl", subUrls.get(destPageId));
        bundle.putInt("TotalCount", subUrls.size());
        bundle.putString("subTitle", subTitles.get(destPageId));
        bundle.putInt("PageId", destPageId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_show_slide_menu:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SubTitleMenuListAdapter adapter = (SubTitleMenuListAdapter) lvContent.getAdapter();
        if (position != adapter.getSelectPos()) {
            adapter.setSelectPos(position);
            drawerLayout.closeDrawer(Gravity.RIGHT);
            destPageId = position;
            switchFragment(destPageId, AnimStyle.DEFAULT_NO_ANIM);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        rlShowSlideMenu.setAlpha(1f - slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        SubTitleMenuListAdapter adapter = (SubTitleMenuListAdapter) lvContent.getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setSelectPos(destPageId);
        lvContent.smoothScrollToPosition(destPageId);

    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    private void setTestData() {
        subTitles.add("01.概况");
        subTitles.add("02.成都之必体验");
        subTitles.add("04.景点");
        subTitles.add("05.住宿");
        subTitles.add("06.餐饮");
        subTitles.add("07.购物");
        subTitles.add("08.娱乐");

        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=100");
        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=101");
        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=102");
        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=1868");
        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=103");
        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=104");
        subUrls.add("http://m.mafengwo.cn/gl/catalog/index?id=29&catalog_id=105");
    }
}
