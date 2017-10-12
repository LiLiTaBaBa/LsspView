package com.iseeyou.lsspview.lsspview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 * 作者：張利军 on 2017/9/28 0028 19:49
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 *
 * Lssp登录导向View
 */
public class LsspHeaderView extends ImageView{
    static final String MAIN_COLOR ="#51adee" ;
    Paint mPaint;
    Paint mPaintCircle;
    private  int spanDistance=10;
    private int spanValue=0;

    public LsspHeaderView(Context context) {
        this(context,null);
    }

    public LsspHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LsspHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔相关参数
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor(MAIN_COLOR));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(spanDistance);
        mPaint.setStyle(Paint.Style.STROKE);
        spanDistance=dip2px(10);

        mPaintCircle=new Paint();
        mPaintCircle.setColor(Color.WHITE);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setDither(true);
        mPaintCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        setLayerType( LAYER_TYPE_SOFTWARE , null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setPadding(spanDistance,spanDistance,spanDistance,spanDistance);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }

        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //画源图像，为一个圆角矩形
        canvas.drawCircle(getWidth()/2,getHeight()/2,Math.min(getWidth()/2-spanDistance,getHeight()/2-spanDistance),mPaintCircle);
        //设置混合模式
        mPaintCircle.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //画目标图像
       drawTargetImage(canvas);
        // 还原混合模式
        mPaintCircle.setXfermode(null);
        canvas.restoreToCount(sc);
        //绘制半圆
        drawHalfCircle(canvas);

    }

    private void drawTargetImage(Canvas canvas) {
        mPaintCircle.setShadowLayer(dip2px(3), 0, 0, Color.GRAY);
        canvas.drawBitmap(drawableToBitamp(exChangeSize(getDrawable())), null, new Rect(spanDistance,spanDistance,getWidth()-spanDistance,getHeight()-spanDistance), mPaintCircle);
    }



    /**
     * 图片拉升
     *
     * @param drawable
     * @return
     */
    private Drawable exChangeSize(Drawable drawable){
        float scale = 1.0f;
        scale = Math.max(getWidth() * 1.0f / drawable.getIntrinsicWidth(), getHeight()
                * 1.0f / drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, (int) (scale * drawable.getIntrinsicWidth()),
                (int) (scale * drawable.getIntrinsicHeight()));

        return drawable;
    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return  bd.getBitmap();

        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
}

    private void drawHalfCircle(Canvas canvas) {
        RectF rect=new RectF(spanDistance/2,spanDistance/2,getWidth()-spanDistance/2,getHeight()-spanDistance/2);
        canvas.drawArc(rect,0,180+spanValue,false,mPaint);
    }

    /**
     * dip轉換PX
     * @param dip
     * @return
     */
    private int dip2px(int dip){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }


    /**
     * 启动动画
     */
    public void startLssper(){
        ValueAnimator animator=ValueAnimator.ofInt(0,180);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                spanValue= (int) animation.getAnimatedValue();
                invalidate();
                if(spanValue==180){
                    executeRightTraslate();
                }
            }
        });
        animator.setDuration(800);
        animator.start();

    }

    /**
     * 向右移动
     */
    private void executeRightTraslate() {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(this,"translationX",0,-(getResources()
                .getDisplayMetrics().widthPixels/2-getWidth()/2)).setDuration(300);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            if(listener!=null&&(float)(animation.getAnimatedValue())==
                    -(getResources().getDisplayMetrics().widthPixels/2-getWidth()/2))listener.doSomething();
            }
        });
        objectAnimator.start();
    }

    private AnimationSuccessListener listener;
    public interface AnimationSuccessListener{
        void doSomething();
    }
    public void setAnimationSuccessListener(AnimationSuccessListener listener){
        this.listener=listener;
    }
}
