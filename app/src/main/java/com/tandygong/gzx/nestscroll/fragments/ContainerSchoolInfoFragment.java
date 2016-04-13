package com.tandygong.gzx.nestscroll.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tandygong.gzx.nestscroll.R;
import com.tandygong.gzx.nestscroll.enums.AnimStyle;
import com.tandygong.gzx.nestscroll.enums.Page;
import com.tandygong.gzx.nestscroll.utils.Constants;
import com.tandygong.gzx.nestscroll.views.CustomerNestedScrollView;
import com.tandygong.gzx.nestscroll.views.ProgressBarWebView;
import com.tandygong.gzx.nestscroll.views.PullToRefreshLayout;


/**
 * 学校信息
 * Created by gzx on 2016/3/25 at 11:05.
 */
public class ContainerSchoolInfoFragment extends Fragment implements View.OnTouchListener, NestedScrollView.OnScrollChangeListener, PullToRefreshLayout.OnRefreshListener, View.OnLayoutChangeListener {
    private CustomerNestedScrollView mScrollView;
    private ProgressBarWebView webView;
    private String pageUrl;
    private int pageId;
    private Page currPage;//当前页的标识  第一页/最后一页/中间页
    private PullToRefreshLayout pulltoRefresh;
    private Intent receiverIntent;
    private TextView tvRealTitle;
    private TextView tvFakeTitle;
    private int[] locationReal = new int[2];//记录真标题栏在窗口位置的数组
    private int[] locationFake = new int[2];//记录假标题栏在窗口位置的数组
    private String subTitle;
    private LinearLayout llItemWhole;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        receiverIntent = new Intent();
        receiverIntent.setAction(Constants.ACTION_REPLACE_FRAGMENT_SCHOOL_INFO);

        View view = inflater.inflate(R.layout.layout_fragment_container_school_info, null);
        getBundleData();
        findViewIds(view);
        setListeners();
        initData();
        return view;
    }

    private void getBundleData() {
        Bundle extras = getArguments();
        if (extras != null) {//从TabSchoolInfoFragment获取数据
            pageUrl = extras.getString("PageUrl");
            subTitle = extras.getString("subTitle");
            int totalCount = extras.getInt("TotalCount");
            pageId = extras.getInt("PageId");


            //如果页码(pageid 从0开始)等于0,当前页标识=FirstPage
            //如果页码和(总页数-1)相等,当前页标识=LastPage
            //其它值 ,当前页标识=CenterPage
            currPage = pageId == 0 ? Page.FirstPage : pageId == totalCount - 1 ? Page.LastPage : Page.CenterPage;

            Log.e("pageUrl", pageUrl);
        }


    }

    private void findViewIds(View view) {
        pulltoRefresh = (PullToRefreshLayout) view.findViewById(R.id.ptf_detail);
        mScrollView = (CustomerNestedScrollView) view.findViewById(R.id.nestScrollView);//实现PullAble接扣得NestScrollView
        webView = (ProgressBarWebView) view.findViewById(R.id.webView);//包含在PUllToRefreshLayout的webView
        tvRealTitle = (TextView) view.findViewById(R.id.tv_real_title);//真标题栏
        tvFakeTitle = (TextView) view.findViewById(R.id.tv_fake_title);//浮在CoordinatorLayout下面的假标题栏
        View llHead = view.findViewById(R.id.ll_head);

        //这个线性布局包裹整个内容,因为在PullToRefreshLayout中,若子view(除了include的头尾布局)
        // 的高度小于PullToRefreshLayout的高度时,就会出现不能上拉的情况,所以需要在后面设置最小高度
        llItemWhole = (LinearLayout) view.findViewById(R.id.ll_item_whole);


        switch (currPage) {//根据Page标志设置可上拉/下拉
            case FirstPage:
                mScrollView.setCanRefresh(false);
                llHead.setVisibility(View.VISIBLE);
                break;
            case CenterPage:
                break;
            case LastPage:
                mScrollView.setCanLoadMore(false);
                break;
        }

    }


    private void setListeners() {
        pulltoRefresh.setOnRefreshListener(this);
        mScrollView.setOnScrollChangeListener(this);
        mScrollView.setOnTouchListener(this);
        mScrollView.addOnLayoutChangeListener(this);
    }

    /**
     * 计算真假标题滑动时在窗口位置,当他们相遇时分别做隐藏和显示动作
     */
    private void onTitleMeet() {
        tvRealTitle.getLocationOnScreen(locationReal);
        tvFakeTitle.getLocationOnScreen(locationFake);
        if (locationReal[1] <= locationFake[1]) {
            if (tvFakeTitle.getVisibility() != View.VISIBLE)
                tvFakeTitle.setVisibility(View.VISIBLE);
        } else {
            if (tvFakeTitle.getVisibility() == View.VISIBLE)
                tvFakeTitle.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        Context applicationContext = getContext().getApplicationContext();
        llItemWhole.setMinimumHeight(applicationContext.getResources().getDisplayMetrics().heightPixels);
        setRefreshLabel();
        tvFakeTitle.setText(subTitle);
        tvRealTitle.setText(subTitle);
        webView.setWebViewMargin(0);
        webView.loadUrl(pageUrl);

    }

    /**
     * 设置上拉下拉提示语
     */
    private void setRefreshLabel() {
        String loadMoreStr = "加载下一章内容";
        String refreshStr = "加载上一章内容";

        pulltoRefresh.setLoadMoreWord(loadMoreStr, loadMoreStr, loadMoreStr, loadMoreStr, "加载失败");
        pulltoRefresh.setRefreshWords(refreshStr, refreshStr, refreshStr, refreshStr, "加载失败");
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        onTitleMeet();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        onTitleMeet();
        return false;
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {//下拉PUllToRefreshLayout布局的操作
        pulltoRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
        receiverIntent.putExtra("animStyle", AnimStyle.TOP_TO_BOTTOM);
        getActivity().sendBroadcast(receiverIntent);//发送广播,通知TabSchoolFragment切换内容
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pulltoRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        receiverIntent.putExtra("animStyle", AnimStyle.BOTTOM_TO_TOP);
        getActivity().sendBroadcast(receiverIntent);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        onTitleMeet();
    }


}
