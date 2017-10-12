package com.iseeyou.lsspview.lsspview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.iseeyou.lsspview.util.DensityUtils;
import com.iseeyou.lsspview.util.DrawHelper;
import com.iseeyou.lsspview.util.MathHelper;

import java.util.Arrays;

/**
 * 作者：張利军 on 2017/10/9 0009 10:21
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 */
public class LsspWarningRankView extends View{
    private static final String MAIN_COLOR ="#51adee" ;
    private static final float SPANNED = DensityUtils.dpToPx(100);
    //绘制的画笔
    Paint mPaint;

    //玫瑰花瓣的五色
    private String color1="#E6454B";
    private String color2="#E26C3A";
    private String color3="#DCB43A";
    private String color4="#B3D123";
    //绘制扇形的宽度
    private float width;
    //绘制扇形的高度
    private float height;
    //起始点
    private float start;
    //绘制的起始角度          绘制时候是逆时针
    private int startSweepAngle=270;
    //五瓣花分别个字的份额比例
    private int firstLeaf=100;
    private int secondLeaf=100;
    private int thirdLeaf=90;
    private int fourLeaf=70;
    //需要减少的的半径
    private float firstRadius=0;
    private float secondRadius=0;
    private float thirdRadius=0;
    private float fourRadius=0;
    //图形的中心点
    private int centerX;
    private int centerY;
    private int [] arrs;

    private boolean isMoving=false;
    private String[] titles;

    public LsspWarningRankView(Context context) {
        this(context,null);
    }

    public LsspWarningRankView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LsspWarningRankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(Math.min(widthMeasureSpec,heightMeasureSpec)),
                (int) (MeasureSpec.getSize(Math.min(widthMeasureSpec,heightMeasureSpec))+DensityUtils.dpToPx(50)));
    }

    private void init() {
        //初始化画笔相关参数
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor(MAIN_COLOR));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(DensityUtils.dpToPx(12));
        mPaint.setStrokeWidth(DensityUtils.dpToPx(0.5f));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //初始化
        firstRadius=firstLeaf*1.0f/firstLeaf*SPANNED;
        secondRadius= SPANNED-secondLeaf*1.0f/firstLeaf*SPANNED;
        thirdRadius= SPANNED-thirdLeaf*1.0f/firstLeaf*SPANNED;
        fourRadius=SPANNED- fourLeaf*1.0f/firstLeaf*SPANNED;
        //赋值
        arrs=new int[]{firstLeaf,secondLeaf,thirdLeaf,fourLeaf};
        titles=new String[]{"致命","重大","一般","告警"};

        //关闭硬件加速
//        isHardwareAccelerated()
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = ( getRight() - getLeft() )/2;//中点
        centerY = (int) (((getBottom() - getTop()) / 2) - DensityUtils.dpToPx(20));

        start=DensityUtils.dpToPx(50);
        width=getWidth()-start;
        height=getHeight()-start;

        //====================
        firstRadius=(getWidth()-DensityUtils.dpToPx(110))/2;
        secondRadius= (secondLeaf*1.0f/firstLeaf*firstRadius);
        thirdRadius= (thirdLeaf*1.0f/firstLeaf*firstRadius);
        fourRadius= (fourLeaf*1.0f/firstLeaf*firstRadius);
        //====================
        if(secondRadius<DensityUtils.dpToPx(45))secondRadius=secondRadius+DensityUtils.dpToPx(65);
        if(thirdRadius<DensityUtils.dpToPx(45))thirdRadius=thirdRadius+DensityUtils.dpToPx(65);
        if(fourRadius<DensityUtils.dpToPx(45))fourRadius=fourRadius+DensityUtils.dpToPx(65);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制面板白色
        canvas.drawColor(Color.WHITE);
        //绘制标签线   一个基本的简单算法
        drawLineLabel(canvas);
        //绘制玫瑰直线
//        drawRoseLine(canvas);
        //绘制文本内容
//        drawRoseContent(canvas);
        //绘制玫瑰花瓣
        drawRoseLeafCenter(canvas);
        //绘制白色圆圈
       drawWhiteCircle(canvas);
        //绘制颜色介绍
        drawColorIntroduce(canvas);
    }

    /**
     * 绘制标签线
     * @param canvas
     */
    private void drawLineLabel(Canvas canvas) {

        final String firstTitle=titles[0]+String .format("%.2f",(firstLeaf*1.0f/360*100))+"%";
        final String secondTitle=titles[1]+String .format("%.2f",secondLeaf*1.0f/360*100)+"%";
        final String thirdTitle=titles[2]+String .format("%.2f",thirdLeaf*1.0f/360*100)+"%";
        final  String fourTitle=titles[3]+String .format("%.2f",fourLeaf*1.0f/360*100)+"%";

//        mPaint.setColor(getSpecialColor(color1));
//        renderLabelLine(firstTitle,firstLeaf,centerX,centerY,firstRadius,
//                MathHelper.getInstance().add(startSweepAngle,-firstLeaf/2),canvas,mPaint);

        //第一条线
        mPaint.setColor(getSpecialColor(color1));
        canvas.drawLine(centerX,centerY,centerX-firstRadius/3*2,centerY-firstRadius,mPaint);
        canvas.drawLine(centerX-firstRadius/3*2,centerY-firstRadius,centerX-firstRadius/3*2-DensityUtils.dpToPx(10),centerY-firstRadius,mPaint);
        //绘制文本
        Rect rect=new Rect();
        mPaint.getTextBounds(firstTitle,0,firstTitle.length(),rect);
        mPaint.setColor(getSpecialColor(color1));
        canvas.drawText(firstTitle,centerX-firstRadius/3*2-DensityUtils.dpToPx(10)-mPaint.measureText(firstTitle),
                centerY-firstRadius+rect.height()/2,mPaint);

        if(secondLeaf!=0){
            mPaint.setColor(getSpecialColor(color2));
            renderLabelLine(secondTitle,secondLeaf,centerX,centerY,secondRadius,
                    MathHelper.getInstance().add(-firstLeaf+startSweepAngle,-secondLeaf/2),canvas,mPaint, false);
        }

        if(thirdLeaf!=0){
            mPaint.setColor(getSpecialColor(color3));
            renderLabelLine(thirdTitle,thirdLeaf,centerX,centerY,thirdRadius,
                    MathHelper.getInstance().add(-firstLeaf-secondLeaf+startSweepAngle,-thirdLeaf/2),canvas,mPaint, true);
        }

        if(fourLeaf!=0){
            mPaint.setColor(getSpecialColor(color4));
            renderLabelLine(fourTitle,fourLeaf,centerX,centerY,firstRadius,
                    MathHelper.getInstance().add(-firstLeaf-secondLeaf-thirdLeaf+startSweepAngle,-fourLeaf/2),canvas,mPaint, false);
            }

    }

    /**
     * 绘制颜色介绍
     * @param canvas
     */
    private void drawColorIntroduce(Canvas canvas) {
        float startY=getHeight()-DensityUtils.dpToPx(50);
        //保存画布的状态
        canvas.save();
        canvas.translate(-(getWidth()-(DensityUtils.dpToPx(50)+DensityUtils.dpToPx(70)*4)),0);
        //致命
        for (int i = 0; i < 4; i++) {
            if(i==0)mPaint.setColor(getSpecialColor(color1));
            else if(i==1)mPaint.setColor(getSpecialColor(color2));
            else if(i==2)mPaint.setColor(getSpecialColor(color3));
            else if(i==3)mPaint.setColor(getSpecialColor(color4));
            RectF rect1=new RectF(DensityUtils.dpToPx(70)*(i+1),
                    startY  ,DensityUtils.dpToPx(30)+DensityUtils.dpToPx(70)*(i+1),
                    startY+DensityUtils.dpToPx(15));
            canvas.drawRoundRect(rect1,DensityUtils.dpToPx(2),DensityUtils.dpToPx(2),mPaint);

            Rect rect=new Rect();
            mPaint.getTextBounds(titles[i],0,titles[i].length(),rect);
            mPaint.setColor(getSpecialColor("#000000"));
            canvas.drawText(titles[i],DensityUtils.dpToPx(40)+DensityUtils.dpToPx(70)*(i+1),
                    startY+DensityUtils.dpToPx(15)/2+rect.height()/3,mPaint);
        }
        //取出画布的状态
        canvas.restore();

    }

    /**
     * 绘制白色圆圈
     * @param canvas
     */
    private void drawWhiteCircle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(centerX,centerY,DensityUtils.dpToPx(45),mPaint);
    }

    /**
     * 绘制玫瑰直线
     * @param canvas
     */
    protected void drawRoseLine(Canvas canvas) {
        //第一条线
        mPaint.setColor(getSpecialColor(color1));
        canvas.drawLine(centerX,centerY,centerX-firstRadius/3*2,centerY-firstRadius,mPaint);
        canvas.drawLine(centerX-firstRadius/3*2,centerY-firstRadius,centerX-firstRadius/3*2-DensityUtils.dpToPx(10),centerY-firstRadius,mPaint);
        //第二条线
        mPaint.setColor(getSpecialColor(color2));
        canvas.drawLine(centerX,centerY,centerX-secondRadius/3*2,centerY+secondRadius,mPaint);
        canvas.drawLine(centerX-secondRadius/3*2,centerY+secondRadius,centerX-secondRadius/3*2-DensityUtils.dpToPx(10),centerY+secondRadius,mPaint);

        //第三条线
        mPaint.setColor(getSpecialColor(color3));
        canvas.drawLine(centerX,centerY,centerX+thirdRadius,centerY+thirdRadius,mPaint);
        canvas.drawLine(centerX+thirdRadius,centerY+thirdRadius,centerX+thirdRadius+DensityUtils.dpToPx(10),centerY+thirdRadius,mPaint);
        //第四条线
        mPaint.setColor(getSpecialColor(color4));
        canvas.drawLine(centerX,centerY,centerX+firstRadius/3*1,centerY-firstRadius,mPaint);
        canvas.drawLine(centerX+firstRadius/3*1,centerY-firstRadius,centerX+firstRadius/3*1+DensityUtils.dpToPx(10),centerY-firstRadius,mPaint);





    }


    /**
     * 处理特殊的颜色获取
     * @param color
     * @return
     */
    private int getSpecialColor(String color){
        return isMoving?Color.WHITE:Color.parseColor(color);
    }

    /**
     * 绘制玫瑰文本内容
     * @param canvas
     */
    protected void drawRoseContent(Canvas canvas) {
        if(isMoving)mPaint.setColor(Color.WHITE);
        else mPaint.setColor(Color.GRAY);

        final String firstTitle=titles[0]+String .format("%.2f",(firstLeaf*1.0f/360*100))+"%";
        final String secondTitle=titles[1]+String .format("%.2f",secondLeaf*1.0f/360*100)+"%";
        final String thirdTitle=titles[2]+String .format("%.2f",thirdLeaf*1.0f/360*100)+"%";
        final  String fourTitle=titles[3]+String .format("%.2f",fourLeaf*1.0f/360*100)+"%";

        Rect rect=new Rect();
        mPaint.getTextBounds(firstTitle,0,firstTitle.length(),rect);
        mPaint.setColor(getSpecialColor(color1));
        canvas.drawText(firstTitle,centerX-firstRadius/3*2-DensityUtils.dpToPx(10)-mPaint.measureText(firstTitle),
                centerY-firstRadius+rect.height()/2,mPaint);

        mPaint.setColor(getSpecialColor(color2));
        canvas.drawText(secondTitle, centerX-secondRadius/3*2-DensityUtils.dpToPx(10)-mPaint.measureText(secondTitle),
                centerY+secondRadius+rect.height()/2,mPaint);

        mPaint.setColor(getSpecialColor(color3));
        canvas.drawText(thirdTitle,centerX+thirdRadius+DensityUtils.dpToPx(10),centerY+thirdRadius+rect.height()/2,mPaint);

        mPaint.setColor(getSpecialColor(color4));
        canvas.drawText(fourTitle,centerX+firstRadius/3*1+DensityUtils.dpToPx(10),centerY-firstRadius+rect.height()/2,mPaint);
    }

    /**
     * 绘制玫瑰花瓣
     * 记录下规律
     *一共是360
     * 份额越大
     * 半径越大
     * 颜色越深
     * @param canvas
     */
    protected void drawRoseLeafCenter(Canvas canvas) {
        mPaint.setColor(Color.parseColor(color1));
        canvas.drawArc(new RectF(centerX - firstRadius,centerY-firstRadius,centerX+firstRadius,centerY+firstRadius),startSweepAngle,-firstLeaf,true,mPaint);
        mPaint.setColor(Color.parseColor(color2));
        canvas.drawArc(new RectF(centerX - secondRadius,centerY-secondRadius,centerX+secondRadius,centerY+secondRadius),
                -firstLeaf+startSweepAngle,-secondLeaf,true,mPaint);
        mPaint.setColor(Color.parseColor(color3));
        canvas.drawArc(new RectF(centerX - thirdRadius,centerY-thirdRadius,centerX+thirdRadius,centerY+thirdRadius),
                -(firstLeaf+secondLeaf)+startSweepAngle,-thirdLeaf,true,mPaint);
        mPaint.setColor(Color.parseColor(color4));
        canvas.drawArc(new RectF(centerX - fourRadius,centerY-fourRadius,centerX+fourRadius,centerY+fourRadius),
                -(firstLeaf+secondLeaf+thirdLeaf)+startSweepAngle,-fourLeaf,true,mPaint);
    }


    /**
     * 执行展开动画
     */
    public void executeAnim() {
        isMoving=true;
        final int VALUE=firstLeaf;
        for (int i = 0; i < 4; i++) {
            final int j=i;
            ValueAnimator valueAnimator=ValueAnimator.ofInt(0,arrs[i]);
            valueAnimator.setDuration(500);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value= (int) animation.getAnimatedValue();
                    if(j==0)firstLeaf=  value;
                    else if(j==1)secondLeaf= value;
                    else if(j==2)thirdLeaf= value;
                    else if(j==3)fourLeaf=  value;
                    if(j==0&&value==VALUE)isMoving=false;
                    invalidate();

                }
            });
            valueAnimator.start();
        }
    }

    /**
     * 设置份额
     * @param arrs
     */
    public void setArrarysAndTitles(int [] arrs,String [] titles){
        //数组排序
        Arrays.sort(arrs,arrs.length,0);
        this.arrs=arrs;
        this.titles=titles;
        executeAnim();
    }

    /**
     * 绘制折线label
     * @param text
     * @param cirX
     * @param cirY
     * @param radius
     * @param calcAngle
     * @param canvas
     * @param paintLabel
     * @param isRotate
     * @return
     */
    private PointF renderLabelLine(String text, float itemAngle,
                                   float cirX, float cirY, float radius, float calcAngle,
                                   Canvas canvas, Paint paintLabel, boolean isRotate)
    {
        float pointRadius = 0.0f;

        //显示在扇形的外部
        //1/4处为起始点
        float calcRadius = MathHelper.getInstance().sub(radius  , radius / 3.f);
        MathHelper.getInstance().calcArcEndPointXY(
                cirX, cirY, calcRadius, calcAngle);

        float startX = MathHelper.getInstance().getPosX();
        float startY = MathHelper.getInstance().getPosY();

        //延长原来半径的一半在外面
        calcRadius = radius / 2f;
        MathHelper.getInstance().calcArcEndPointXY(startX, startY, calcRadius, calcAngle);
        float stopX = MathHelper.getInstance().getPosX();
        float stopY = MathHelper.getInstance().getPosY();


        float borkenline = DensityUtils.dpToPx(15); //折线长度

        float endX = 0.0f,endLabelX = 0.0f;
        if(Float.compare(stopX, cirX) == 0){ //位于中间竖线上
            if(Float.compare(stopY, cirY) == 1 ) //中点上方,左折线
            {
                paintLabel.setTextAlign(Paint.Align.LEFT);
                endX = stopX + borkenline ; //+ pointRadius;
                endLabelX = endX + pointRadius;
            }else{ //中点下方,右折线
                paintLabel.setTextAlign(Paint.Align.RIGHT);
                endX = stopX - borkenline;
                endLabelX = endX - pointRadius;
            }
        }else if(Float.compare(stopY, cirY) == 0 ){ //中线横向两端
            endX = stopX;
            if(Float.compare(stopX, cirX) == 0 ||
                    Float.compare(stopX, cirX) == -1) //左边
            {
                paintLabel.setTextAlign(Paint.Align.RIGHT);
                endLabelX = endX - pointRadius;
            }else{
                paintLabel.setTextAlign(Paint.Align.LEFT);
                endLabelX = endX + pointRadius;
            }

        }else if(Float.compare(stopX + borkenline , cirX) == 1 ) //右边
        {
            paintLabel.setTextAlign(Paint.Align.LEFT);
            endX = stopX + borkenline;
            endLabelX = endX + pointRadius;
        }else if(Float.compare(stopX - borkenline ,cirX) == -1  ) //左边
        {
            paintLabel.setTextAlign(Paint.Align.RIGHT);
            endX = stopX - borkenline ;
            endLabelX = endX - pointRadius;
        }else {
            endLabelX = endX = stopX;
            paintLabel.setTextAlign(Paint.Align.CENTER);
        }

            //转折线
            drawBrokenLine(startX,startY,stopX, stopY, endX,canvas);
        //标签点NONE,BEGIN,END,ALL
//        drawPoint(startX,startY,stopX, stopY, endX,pointRadius,canvas);

            DrawHelper.getInstance().drawRotateText(text,endLabelX, stopY, itemAngle,
                        canvas, paintLabel,isRotate);

        return (new PointF(endLabelX, stopY));
    }

    private void drawBrokenLine(float startX,float startY,
                                float stopX,float stopY,float endX,
                                Canvas canvas)
    {
        //连接线
        canvas.drawLine(centerX, centerY,stopX, stopY, mPaint);

        //转折线
        canvas.drawLine(stopX, stopY, endX, stopY, mPaint);
    }

}
