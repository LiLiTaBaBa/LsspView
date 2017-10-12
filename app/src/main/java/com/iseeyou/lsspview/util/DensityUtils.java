/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iseeyou.lsspview.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class DensityUtils {

    private static final float DOT_FIVE = 0.5f;


    /**
     * dip to px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context, float dip) {
        float density = getDensity(context);
        return (int) (dip * density + DensityUtils.DOT_FIVE);
    }

    /**
     * px to dip
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, float px) {
        float density = getDensity(context);
        return (int) (px / density + DOT_FIVE);
    }

    private static DisplayMetrics sDisplayMetrics;

    /**
     * get screen width
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.widthPixels;
    }

    /**
     * get screen height
     *
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.heightPixels;
    }

    /**
     * get screen density
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.density;
    }


    /**
     * get screen density dpi
     *
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.densityDpi;
    }

    /**
     * init display metrics
     *
     * @param context
     */
    private static synchronized void initDisplayMetrics(Context context) {
        sDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    /**
     * is landscape
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * is portrait
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Converts the given dp measurement to pixels.
     *
     * @param dp The measurement, in dp
     * @return The corresponding amount of pixels based on the device's screen density
     */
    public static float dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /***
     * 获取状态来的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /***
     * 设置TextView右边的图片
     */
    public static void setDrawable(TextView textView, int drawableId, Context context) {
        Drawable img_on, img_off;
        Resources res = context.getResources();
        img_off = res.getDrawable(drawableId);
        textView.setCompoundDrawablePadding(15);
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img_off.setBounds(0, 0, img_off.getMinimumWidth() / 3 * 2, img_off.getMinimumHeight() / 3 * 2);
        textView.setCompoundDrawables(null, null, img_off, null); //设置左图标
    }
}