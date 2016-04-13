package com.tandygong.gzx.nestscroll.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tandygong.gzx.nestscroll.R;
import com.tandygong.gzx.nestscroll.utils.ActivityUtils;

/**
 * Created by usb on 2016/4/13.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnScrollView;
    private Button btnRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findIds();
        initData();
        setListeners();
    }


    private void findIds() {
        btnScrollView = (Button) findViewById(R.id.btn_use_scrollview);
        btnRecycleView = (Button) findViewById(R.id.btn_use_recycle_view);
    }

    private void initData() {

    }

    private void setListeners() {
        btnScrollView.setOnClickListener(this);
        btnRecycleView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_use_scrollview:
                String url = "http://p3.qhimg.com/t01d0b5e6e40b60df20.png";
                ActivityUtils.toSchoolDetailActivityForResult(this, true, "北京大学", "BeijingUniversity", url, 100, 1, true);
                break;
            case R.id.btn_use_recycle_view:
                Toast.makeText(this, "正在开发", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
