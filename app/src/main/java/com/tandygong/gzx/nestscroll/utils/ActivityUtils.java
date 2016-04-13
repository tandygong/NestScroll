package com.tandygong.gzx.nestscroll.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tandygong.gzx.nestscroll.activitys.NestScrollWithScrollViewActivity;

/**
 * Created by usb on 2016/4/13.
 */
public class ActivityUtils {
    public static void toSchoolDetailActivityForResult(Context context, boolean isApply, String chineseName, String englishName, String imgUrl, int schoolId, int programId, boolean checked) {
        Intent intent = new Intent(context, NestScrollWithScrollViewActivity.class);
        intent.putExtra("isApply", isApply);
        intent.putExtra("chineseName", chineseName);
        intent.putExtra("englishName", englishName);
        intent.putExtra("imgUrl", imgUrl);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("programId", programId);
        intent.putExtra("checked", checked);
        context.startActivity(intent);
    }

}
