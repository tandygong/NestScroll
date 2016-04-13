package com.tandygong.gzx.nestscroll.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tandygong.gzx.nestscroll.fragments.TabProjectInfoFragment;
import com.tandygong.gzx.nestscroll.fragments.TabSchoolInfoFragment;

/**
 * viewPager的adapter,含有两个fragment
 */
public class SchoolDeatilAdapter extends FragmentPagerAdapter {
    private int schoolId;

    public SchoolDeatilAdapter(FragmentManager fm, int schoolId) {
        super(fm);
        this.schoolId = schoolId;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            TabSchoolInfoFragment tabSchoolInfoFragment = new TabSchoolInfoFragment();//右侧的学校信息fragment
            Bundle bundle = new Bundle();
            bundle.putInt("schoolId", schoolId);
            tabSchoolInfoFragment.setArguments(bundle);
            return tabSchoolInfoFragment;
        } else {
            TabProjectInfoFragment tabProjectInfoFragment = new TabProjectInfoFragment();//左侧的项目信息fragment
            return tabProjectInfoFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "项目信息" : "学校信息";
    }

  /*  @Override
    public long getItemId(int position) {
        int hashCode = dataList.get(position).hashCode();
        return hashCode;
    }*/
}
