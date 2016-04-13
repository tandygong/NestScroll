package com.tandygong.gzx.nestscroll.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tandygong.gzx.nestscroll.utils.Constants;


/**
 * Created by gzx on 2016/3/11 at 15:42.
 */
public class AppWebView extends WebView {

    private String cacheDirPath;

    public AppWebView(Context context) {
        super(context);
        cacheDirPath = context.getFilesDir().getAbsolutePath() + Constants.APP_WEB_CACAHE_DIRNAME;
        initSetting();
    }

    public AppWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cacheDirPath = context.getFilesDir().getAbsolutePath() + Constants.APP_WEB_CACAHE_DIRNAME;
        initSetting();
    }

    public AppWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cacheDirPath = context.getFilesDir().getAbsolutePath() + Constants.APP_WEB_CACAHE_DIRNAME;
        initSetting();
    }

    public void initSetting() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
        // 缓存模式
        // 开启 DOM storage API 功能
        getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        getSettings().setDatabaseEnabled(true);
        // 设置数据库缓存路径
        getSettings().setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        getSettings().setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        getSettings().setAppCacheEnabled(true);
    }

}
