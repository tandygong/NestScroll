package com.tandygong.gzx.nestscroll.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tandygong.gzx.nestscroll.R;


public class ProgressBarWebView extends AppWebView {
    private int webViewMargin = 114;
    private ProgressBar progressbar;
    private OnLoadCompleteListener onLoadCompleteListener;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (getProgress() == 100) {
                        progressbar.setVisibility(INVISIBLE);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
                        params.topMargin = webViewMargin;
                        setLayoutParams(params);
                        if (onLoadCompleteListener != null) {
                            onLoadCompleteListener.onComplete();
                        }
                    } else {
                        if (progressbar.getVisibility() == INVISIBLE)
                            progressbar.setVisibility(VISIBLE);
                        progressbar.setProgress(getProgress());
                        handler.sendEmptyMessageDelayed(2, 10);
                    }
                    break;
                default:
                    break;
            }

            return false;
        }
    }
    );

    public ProgressBarWebView(Context context) {
        this(context, null, 0);

    }

    public void setWebViewMargin(int margin) {
        webViewMargin = margin;
    }

    public ProgressBarWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.progressbar));
        progressbar.setMax(100);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, 5);
        layoutParams.bottomMargin = 25;
        progressbar.setLayoutParams(layoutParams);
        addView(progressbar);
        setWebViewClient(new WebClient());
    }

    public OnLoadCompleteListener getOnLoadCompleteListener() {
        return onLoadCompleteListener;
    }

    public void setOnLoadCompleteListener(OnLoadCompleteListener onLoadCompleteListener) {
        this.onLoadCompleteListener = onLoadCompleteListener;
    }


    public class WebClient extends AppWebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            handler.sendEmptyMessage(2);
            super.onPageStarted(view, url, favicon);
        }

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }


    public interface OnLoadCompleteListener {
        void onComplete();
    }

}
