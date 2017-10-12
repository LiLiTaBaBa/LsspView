package com.iseeyou.lsspview.lsspview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.iseeyou.lsspview.util.DensityUtils;

/**
 * 作者：張利军 on 2017/10/11 0011 15:09
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 */
public class LsspColumnarView extends View {
    //画笔
    private Paint mPaint;
    //颜色
    private String color="#99c3c2c2";
    //背景颜色
    private String backGroundColor="#293144";//
    //时间
    private int [] times_={0,2,4,6,8,10};
    //健康度
    private String [] healths={"180","150","120","90","60","30","0"};
    //健康度文字宽度
    private float textWidth;
    //线宽度的控制
    private float mLineWidthController=0;
    //颜色数组
    private String [] colors={"#e7454c","#e26c39","#bcb43a","#b2cf2a","#1ccb4f"};
    private float mRectWidth=DensityUtils.dpToPx(32);
    private float[] heights={162.2f,150.6f,132.6f,86.4f,63.5f};
    private String[] titles={"分类1","分类2","分类3","分类4","分类5"};
    private float mValueAnimation=1;
    private boolean isMoving=false;//

    public LsspColumnarView(Context context) {
        this(context,null);
    }

    public LsspColumnarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LsspColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(Math.min(widthMeasureSpec,heightMeasureSpec),
                Math.min(widthMeasureSpec,heightMeasureSpec));
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor(color));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(DensityUtils.dpToPx(0.5f));
        mPaint.setTextSize(DensityUtils.dpToPx(14));


        textWidth=mPaint.measureText("180");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景颜色
        canvas.drawColor(Color.parseColor(backGroundColor));
        //绘制所有的水平直线
        drawHorizontalLines(canvas);
        //绘制垂直方向的文字
        drawVerticalTexts(canvas);
        //绘制点以及折线
        drawCloumnarsAndTexts(canvas);
    }

    /**
     * 绘制所有的水平直线
     * @param canvas
     */
    private void drawHorizontalLines(Canvas canvas) {
        setPaintColor();
        for (int i = 0; i < 7; i++) {
            mPaint.setStrokeWidth(1f);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawLine(DensityUtils.dpToPx(30)+textWidth,
                    DensityUtils.dpToPx(50)+i*getHeight()/8,getWidth()-DensityUtils.dpToPx(30)-mLineWidthController,
                    DensityUtils.dpToPx(50)+i*getHeight()/8,mPaint);
        }
    }

    /**
     * 设置颜色
     */
    private void setPaintColor(){
            mPaint.setColor(Color.parseColor(color));
    }
    /**
     * 绘制垂直方向的文字
     * @param canvas
     */
    private void drawVerticalTexts(Canvas canvas) {
        setPaintColor();
        for (int i = 0; i < 7; i++) {
            Rect rect=new Rect();
            mPaint.getTextBounds(healths[0],0,healths[0].length(),rect);
            canvas.drawText(healths[i],DensityUtils.dpToPx(30),
                    DensityUtils.dpToPx(50)+i*getHeight()/8+rect.height()/2,mPaint);
        }
    }
    /**
     * 绘制点以及折线
     * @param canvas
     */
    private void drawCloumnarsAndTexts(Canvas canvas) {
        setPaintColor();
        for (int i = 0; i < 5; i++) {
            mPaint.setTextSize(DensityUtils.dpToPx(12));
            mPaint.setColor(Color.parseColor(colors[i]));
            RectF rect=new RectF(DensityUtils.dpToPx(60)*(i+1),
                    (1-heights[i]/180)*(getHeight()/4*3)-(getHeight()/4*3)*(mValueAnimation-1)
                    +DensityUtils.dpToPx(50), DensityUtils.dpToPx(60)*(i+1)+mRectWidth,
                    DensityUtils.dpToPx(50)+getHeight()/4*3);
            canvas.drawRect(rect,mPaint);

//            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
//            int baseline = (bounds.height()- fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
//            canvas.drawText(titles[i],rect.width() / 2 - bounds.width() / 2, baseline, mPaint);
            if(!isMoving){
                canvas.drawText(titles[i],DensityUtils.dpToPx(60)*(i+1),(1-heights[i]/180)*(getHeight()/4*3)-(getHeight()/4*3)*(mValueAnimation-1)
                        +DensityUtils.dpToPx(25),mPaint);
                canvas.drawText(String.valueOf(heights[i]),DensityUtils.dpToPx(60)*(i+1)
                                +(heights[i]>=100?0:(heights[i]>10?DensityUtils.dpToPx(5):DensityUtils.dpToPx(8))),
                        (1-heights[i]/180)*(getHeight()/4*3)-(getHeight()/4*3)*(mValueAnimation-1) +DensityUtils.dpToPx(40),mPaint);
            }
        }
    }




    /**
     * 设置背景色
     * @param backGroundColor
     */
    public void setBackGroundColor(String backGroundColor){
        this.backGroundColor=backGroundColor;
        invalidate();
    }

    /**
     * 执行动画
     */
    public void executeAnim(){
        isMoving=true;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValueAnimation= (float) animation.getAnimatedValue();
                if(mValueAnimation==1)isMoving=false;
                invalidate();
            }
        });
        valueAnimator.start();

    }

    /**
     * 设置高度
     * @param heights
     */
    public void setHeights(float[] heights){
        this.heights=heights;
        executeAnim();
    }

    /**
     * 设置标题
     * @param titles
     */
    public void setTitles(String [] titles){
        this.titles=titles;
    }

}
