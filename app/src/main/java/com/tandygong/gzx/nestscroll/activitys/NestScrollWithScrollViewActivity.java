package com.tandygong.gzx.nestscroll.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tandygong.gzx.nestscroll.R;
import com.tandygong.gzx.nestscroll.adapters.SchoolDeatilAdapter;


public class NestScrollWithScrollViewActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private boolean isApply;
    private String englishName;
    private String chineseName;
    private String imgUrl;
    private int schoolId;
    private RelativeLayout rlShowSlideMenu;
    private ListView lvMenu;
    private DrawerLayout drawerLayout;
    private TextView tvContentNum;
    private LinearLayout llSearchSchool;
    private View applyBtn;
    private TextView tSchool;
    private View backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtraData();
        setContentView(R.layout.activity_nest_scroll_with_scroll_view);
        findViews();
        setData();
        setListeners();
    }

    public void getExtraData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        isApply = extras.getBoolean("isApply", false);
        englishName = extras.getString("englishName");
        chineseName = extras.getString("chineseName");
        imgUrl = extras.getString("imgUrl");
        schoolId = extras.getInt("schoolId");
    }

    private void findViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        ImageView tvToolBarBg = (ImageView) findViewById(R.id.toobar_bg);
        TextView tvNameEn = (TextView) findViewById(R.id.tv_name_english);
        TextView tvNameCn = (TextView) findViewById(R.id.tv_name_chinese);
        backBtn = findViewById(R.id.toobar_back);
        applyBtn = findViewById(R.id.toobar_apply);

        rlShowSlideMenu = (RelativeLayout) findViewById(R.id.rl_show_slide_menu);
        lvMenu = (ListView) findViewById(R.id.lv_menuList);
        tvContentNum = (TextView) findViewById(R.id.tv_contentNum);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        //ImageLoader.getInstance().displayImage(imgUrl, tvToolBarBg);
        tvNameCn.setText(chineseName);
        tvNameEn.setText(englishName);


    }

    private void setData() {
        mViewPager.setAdapter(new SchoolDeatilAdapter(getSupportFragmentManager(), schoolId));
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        rlShowSlideMenu.setVisibility(View.INVISIBLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setListeners() {
        mViewPager.addOnPageChangeListener(this);
        backBtn.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
    }

    public ListView getLvMenu() {
        return lvMenu;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public TextView getTvContentNum() {
        return tvContentNum;
    }

    public RelativeLayout getRlShowSlideMenu() {
        return rlShowSlideMenu;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {//当切换tab时设置悬浮按钮显示/隐藏,DrawLayout锁定/解锁
        if (position == 0) {
            rlShowSlideMenu.setVisibility(View.INVISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            rlShowSlideMenu.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            case R.id.toobar_apply:
                break;
        }
    }
}
