package com.tandygong.gzx.nestscroll.enums;

import java.io.Serializable;

/**
 * 切换内容时的动画
 * Created by gzx on 2016/3/17 at 15:01.
 */
public enum AnimStyle implements Serializable {
    /**
     * 从上往下移动
     */
    TOP_TO_BOTTOM,
    /**
     * 从底部往上滑动
     */
    BOTTOM_TO_TOP,
    /**
     * 默认加载,无动画
     */
    DEFAULT_NO_ANIM
}
