package com.iseeyou.lsspview.bean;

import android.support.annotation.NonNull;

/**
 * 作者：張利军 on 2017/10/10 0010 20:07
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 */
public class PointBean implements Comparable<PointBean>{
    private float x;

    public PointBean() {
    }

    public PointBean(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float y;

    @Override
    public int compareTo(@NonNull PointBean o) {
        return (this.getX()==o.getX())?0:(this.getX()<o.getX()?-1:1);
    }
}
