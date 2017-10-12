package com.iseeyou.lsspview.lsspview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.iseeyou.lsspview.bean.PointBean;
import com.iseeyou.lsspview.util.DensityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：張利军 on 2017/10/9 0009 10:08
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 *
 * 折线图
 */
public class LsspBrokenLineView extends View{
    //画笔
    private Paint mPaint;
    //颜色
    private String color="#c3c2c2";
    //颜色
    private String color2="#3E4657";
    //背景颜色
    private String backGroundColor="#293144";//
    //时间
    private String [] times={"00:00","02:00","04:00","06:00","08:00","10:00"};
    //时间
    private int [] times_={0,2,4,6,8,10};
    //健康度
    private String [] healths={"53","52","51","50","49","48"};
    //圆点半径
    private float pointRadius=DensityUtils.dpToPx(4);
    //所有的点
    private  List<PointBean>  points=null;
    //健康度文字宽度
    private float textWidth;
    //是否为贝瑟尔曲线
    private boolean isBeiz=false;
    //线宽度的控制
    private float mLineWidthController=0;
    private Path mPath;
    private Path mDst;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private float mLength;

    public LsspBrokenLineView(Context context) {
        this(context,null);
    }

    public LsspBrokenLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LsspBrokenLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mPath= new Path();
        mDst=new Path();
        mPathMeasure=new PathMeasure();


        textWidth=mPaint.measureText("55");
        //初始化
        points=new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //装点
//        points.add(new PointBean(DensityUtils.dpToPx(40)+textWidth+0*getWidth()/7, getHeight()/2));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+1*getWidth()/7, getHeight()/3));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+1.5f*getWidth()/7, getHeight()/5*3));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+2*getWidth()/7, getHeight()/9*5));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+2.5f*getWidth()/7, getHeight()/5*2));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+3*getWidth()/7,getHeight()/4));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+4*getWidth()/7, getHeight()/6));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+5*getWidth()/7, getHeight()/3));
//        points.add(new PointBean(DensityUtils.dpToPx(30)+textWidth+6*getWidth()/8, getHeight()/3*2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景颜色
        canvas.drawColor(Color.parseColor(backGroundColor));
        //绘制所有的水平直线
        drawHorizontalLines(canvas);
        //绘制垂直方向的文字
        drawVerticalTexts(canvas);
        //绘制水平方向的文字
        drawHorizontalTexts(canvas);
        //绘制点以及折线
        drawPointsAndBrokenLines(canvas);
    }

    /**
     * 绘制所有的水平直线
     * @param canvas
     */
    private void drawHorizontalLines(Canvas canvas) {
        mPaint.setShader(null);
        setPaintColor();
        for (int i = 0; i < 6; i++) {
           if(backGroundColor.equals("#ffffff")){
               if(i==0)mPaint.setStrokeWidth(DensityUtils.dpToPx(1f));
               else mPaint.setStrokeWidth(DensityUtils.dpToPx(0.5f));
           }else{
               mPaint.setStrokeWidth(1f);
           }
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
        if(backGroundColor.equals("#ffffff")){
            mPaint.setColor(Color.parseColor(color));
        }else{
            mPaint.setColor(Color.parseColor(color2));
        }
    }
    /**
     * 绘制垂直方向的文字
     * @param canvas
     */
    private void drawVerticalTexts(Canvas canvas) {
        setPaintColor();
        for (int i = 0; i < 6; i++) {
            Rect rect=new Rect();
            mPaint.getTextBounds(healths[i],0,healths[i].length(),rect);
            if(backGroundColor.equals("#ffffff"))
            mPaint.setShader(new LinearGradient(0, 0, getMeasuredWidth(), 0,new int[]{Color.parseColor("#409FE1"),
                    Color.parseColor("#1DC4BA")}, null, LinearGradient.TileMode.CLAMP));
            canvas.drawText(healths[i],DensityUtils.dpToPx(30),
                    DensityUtils.dpToPx(50)+i*getHeight()/8+rect.height()/2,mPaint);
        }
    }
    /**
     * 绘制水平方向的文字
     * @param canvas
     */
    private void drawHorizontalTexts(Canvas canvas) {
        setPaintColor();
        for (int i = 0; i < 6; i++) {
            Rect rect=new Rect();
            mPaint.getTextBounds(times[i],0,times[i].length(),rect);
            canvas.drawText(times[i],DensityUtils.dpToPx(30)+i*getWidth()/7+textWidth,
                    DensityUtils.dpToPx(60)+5*getHeight()/8+rect.height(),mPaint);
        }
    }
    /**
     * 绘制点以及折线
     * @param canvas
     */
    private void drawPointsAndBrokenLines(Canvas canvas) {
        if(points==null||points.size()==0)return;
        mPaint.setShader(new LinearGradient(0, 0, getMeasuredWidth(), 0,new int[]{Color.parseColor("#409FE1"),
                Color.parseColor("#1DC4BA")}, null, LinearGradient.TileMode.CLAMP));
        //绘制点
        for (int i = 0; i < points.size(); i++) {
            canvas.drawCircle(points.get(i).getX(), points.get(i).getY(),pointRadius,mPaint);
        }
        //绘制折线
        mPaint.setStrokeWidth(DensityUtils.dpToPx(3f));
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制Path
        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0,0);
        mPathMeasure.getSegment(0, mAnimatorValue*mLength, mDst, true);
        canvas.drawPath(mDst, mPaint);

    }


    /**
     *  根据用户设定的 时间值和健康度 获取图表中的点位
     *
     *  DensityUtils.dpToPx(40)+textWidth+times[0]*getWidth()/7, getHeight()/2
     *
     *  48  ----->  getHeight()/6*(48-48+1)
     *  49  ----->  getHeight()/6*(49-48+1)
     *
     *  健康值的范围刻度
     *  DensityUtils.dpToPx(50)~DensityUtils.dpToPx(50)+5*getHeight()/8
     *
     * @param time
     * @param health
     * @return
     */
    public PointBean getPointByTimeAndHealth(float time,float health){
        //健康度的最大值 maxHealth  minHealth
        //时间的最大值     maxTime  minTime
        if(time>times_[times_.length-1])time=times_[times_.length-1];
        else if(time<times_[0])time=times_[0];

        if(health<Integer.parseInt(healths[healths.length-1]))health=Integer.parseInt(healths[healths.length-1]);
        else if(health>Integer.parseInt(healths[0]))health=Integer.parseInt(healths[0]);
        //定义x和y的坐标
        float x=0f;
        float y=0f;
        //根据相关的刻度算出x 和 y 的坐标
        x=DensityUtils.dpToPx(35)+(time/2.0f)*(getWidth()/7)+textWidth;
        y=(Integer.parseInt(healths[0])-health)*(getHeight()*1.0f/8)+DensityUtils.dpToPx(50);
        return new PointBean(x,y);
    }

    /**
     * 添加点位
     * @param list
     */
    public void addPoints(final List<PointBean> list){
        post(new Runnable() {
            @Override
            public void run() {
                points.clear();
                Collections.sort(list);
                points.addAll(list);
                initPaths();
                invalidate();
            }
        });
    }

    private void initPaths() {
        mPath.reset();
        //绘制折线
        mPaint.setStrokeWidth(DensityUtils.dpToPx(3f));
        mPaint.setStyle(Paint.Style.STROKE);
        //初始化Path对象
        mPath.moveTo(points.get(0).getX(),points.get(0).getY());
        if(!isBeiz){
            for (int i = 1; i <points.size();i++) {
                mPath.lineTo(points.get(i).getX(),points.get(i).getY());
            }
        }else{
            PointBean startp = null;
            PointBean endp = null;
            for (int i = 0; i <points.size()-1;i++) {
                startp = points.get(i);
                endp =  points.get(i + 1);
                float wt = (startp.getX() + endp.getX()) / 2;
                PointBean p3 = new PointBean(wt,startp.getY());
                PointBean p4 = new PointBean(wt,endp.getY());
                // mPath.moveTo(startp.getX(), startp.getY());
                //moveTo代表一条完整的路径
                mPath.cubicTo(p3.getX(), p3.getY(), p4.getX(), p4.getY(), endp.getX(), endp.getY());
            }
        }
        //初始化
        mPathMeasure.setPath(mPath,false);
        mLength=mPathMeasure.getLength();
    }

    /**
     * 添加点位
     * @param time
     * @param health
     */
    public void addPoint(float time,float health){
        points.add(getPointByTimeAndHealth(time,health));
        invalidate();
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
     * 设置贝瑟尔曲线
     * @param isBeiz
     */
    public void setBeiz(boolean isBeiz){
        this.isBeiz=isBeiz;
    }


    /**
     * 执行动画
     */
    public void excuteAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();

    }

}
