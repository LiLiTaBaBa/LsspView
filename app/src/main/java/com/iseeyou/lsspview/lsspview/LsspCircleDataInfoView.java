package com.iseeyou.lsspview.lsspview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.iseeyou.lsspview.util.DensityUtils;

/**
 * 作者：張利军 on 2017/9/29 0029 16:09
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 *
 *
 * 用于呈现Lssp数据详情的View
 * 背景未圆形的
 */
public class LsspCircleDataInfoView extends View{
    //画笔
    private Paint mPaint;
    //画笔
    private Paint mPaintText;
    //显示的数值
    private int num=200;
    //显示的文字描述
    private String content="业务组件";
    //color1
    private String color1="#1DC5C5";
    //color2
    private String color2="#47AEF9";
    //color3
    private String color3="#3CB5EC";
    //color4
    private String color4="#3AB5F0";
    //宽度
    private float strokeWidth= DensityUtils.dip2px(getContext(),5);
    //数字的文字大小
    private float numTextSize=DensityUtils.dpToPx(35);
    //文字的大小
    private float contentTextSize=DensityUtils.dpToPx(16);

    public LsspCircleDataInfoView(Context context) {
        this(context,null);
    }

    public LsspCircleDataInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LsspCircleDataInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heigthMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        if(heigthMode==MeasureSpec.AT_MOST
                ||widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(getResources().getDisplayMetrics().widthPixels/5*2,
                    getResources().getDisplayMetrics().widthPixels/5*2);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void init() {
        //初始化画笔相关参数
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor(color1));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintText=new Paint();
        mPaintText.setColor(Color.GRAY);
        mPaintText.setAntiAlias(true);
        mPaintText.setDither(true);
        mPaintText.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        drawBackGroundCircle(canvas);
        //画数字
        drawNum(canvas);
        //画文本
        drawText(canvas);
    }

    private void drawNum(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(numTextSize);
        Rect rect=new Rect();
        mPaint.getTextBounds(String.valueOf(num),0,String.valueOf(num).length(),rect);
        canvas.drawText(String.valueOf(num),0,String.valueOf(num).length(),
                getWidth()/2-mPaint.measureText(String.valueOf(num))/2,getHeight()/2,mPaint);
    }

    private void drawText(Canvas canvas) {
        mPaintText.setColor(Color.GRAY);
        mPaintText.setTextSize(contentTextSize);
        Rect rect=new Rect();
        mPaint.getTextBounds(String.valueOf(num),0,String.valueOf(num).length(),rect);
        canvas.drawText(String.valueOf(content),0,String.valueOf(content).length(),
                getWidth()/2-mPaintText.measureText(content)/2,getHeight()/2+rect.height(),mPaintText);
    }

    private void drawBackGroundCircle(Canvas canvas) {
        //设置Shader
        mPaint.setShader(new SweepGradient(getWidth()/2,getHeight()/2,
                new int[] {Color.parseColor(color2),Color.parseColor(color1),
                        Color.parseColor(color2), Color.parseColor(color1)},null));
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth()/2,getHeight()/2,Math.min(getWidth()/2,getHeight()/2)-strokeWidth/2,mPaint);
    }

    public void setNum(int num){
        this.num=num;
        invalidate();
    }

    public void setContent(String content){
        this.content=content;
        invalidate();
    }

    public void setNumTextSize(int size){
        this.numTextSize=size;
        invalidate();
    }

    public void setContextTextSize(int size){
        this.contentTextSize=size;
        invalidate();
    }


}
