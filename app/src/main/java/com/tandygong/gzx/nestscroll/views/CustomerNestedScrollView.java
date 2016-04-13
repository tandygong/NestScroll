package com.tandygong.gzx.nestscroll.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gzx on 2016/3/24 at 20:20.
 */
public class CustomerNestedScrollView extends NestedScrollView implements Pullable {

    private boolean isScrollToBottom;
    private boolean canLoadMore = true;
    private boolean canRefresh = true;


    public CustomerNestedScrollView(Context context) {
        super(context);
    }

    public CustomerNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public boolean isCanRefresh() {
        return canRefresh;
    }

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }


    @Override
    public boolean canPullDown() {
        return getScrollY() == 0 && canRefresh;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        int scrollY = getScrollY();
        super.onScrollChanged(l, t, oldl, oldt);
    }


    @Override
    public boolean canPullUp() {
        return isScrollToBottom && canLoadMore;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollY = getScrollY();
                int height = getHeight();
                int scrollViewMeasuredHeight = getChildAt(0).getMeasuredHeight();
                if (scrollY == 0) {
                }
                if ((scrollY + height) == scrollViewMeasuredHeight) {
                    isScrollToBottom = true;
                } else {
                    isScrollToBottom = false;
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}