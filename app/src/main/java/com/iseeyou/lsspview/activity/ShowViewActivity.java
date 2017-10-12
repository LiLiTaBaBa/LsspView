package com.iseeyou.lsspview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.iseeyou.lsspview.R;
import com.iseeyou.lsspview.bean.PointBean;
import com.iseeyou.lsspview.lsspview.LsspBrokenLineView;
import com.iseeyou.lsspview.lsspview.LsspCircleDataInfoView;
import com.iseeyou.lsspview.lsspview.LsspColumnarView;
import com.iseeyou.lsspview.lsspview.LsspHeaderView;
import com.iseeyou.lsspview.lsspview.LsspRoseLeafView;
import com.iseeyou.lsspview.lsspview.LsspWarningRankView;
import com.iseeyou.lsspview.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者：張利军 on 2017/10/11 0011 17:36
 * 邮箱：282384507@qq.com
 * 公司：南京艾思优信息科技有限公司
 */
public class ShowViewActivity extends AppCompatActivity{

    private View [] views=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] titles=getResources().getStringArray(R.array.titles);
        int position=getIntent().getIntExtra("position",0);
        getSupportActionBar().setTitle(titles[position]);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        operateView(position);

        setContentView(views[position]);

    }

    private void initViews() {
        views=new View[]{
                new LsspHeaderView(this),
                new LsspCircleDataInfoView(this),
                new LsspRoseLeafView(this),
                new LsspWarningRankView(this),
                new LsspBrokenLineView(this),
                new LsspColumnarView(this)};
    }

    private void operateView(int position ) {
        if(position==0){
        final LsspHeaderView view= (LsspHeaderView) views[position];
            FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(DensityUtils.dip2px(this,100),
                    DensityUtils.dip2px(this,100));
            lp.gravity= Gravity.CENTER;
            view.setLayoutParams(lp);
            view.setImageResource(R.drawable.header);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.startLssper();
                }
            });
        }else if(position==1){
            final LsspCircleDataInfoView view= (LsspCircleDataInfoView) views[position];
            FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels/2
                    ,getResources().getDisplayMetrics().widthPixels/2);
            lp.gravity= Gravity.CENTER;
            view.setLayoutParams(lp);
            view.setNum(88);
            view.setContent("已完成");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setNum(new Random().nextInt(100));
                }
            });
        } else if(position==2){
            final LsspRoseLeafView view= (LsspRoseLeafView) views[position];
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.executeAnim();
                }
            });
        }else if(position==3){
            final LsspWarningRankView view= (LsspWarningRankView) views[position];
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.executeAnim();
                }
            });
        }else if(position==4){
            final LsspBrokenLineView view= (LsspBrokenLineView) views[position];
            setPoints(view);
            view.setBackGroundColor("#ffffff");
            view.setBeiz(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.isSelected()){
                        v.setSelected(false);
                        view.setBackGroundColor("#ffffff");
                    }else{
                        v.setSelected(true);
                        view.setBackGroundColor("#293144");
                    }
                    setPoints(view);
                }
            });
        }else if(position==5){
            final LsspColumnarView view= (LsspColumnarView) views[position];
            view.setBackGroundColor("#ffffff");
            view.setHeights(new float[]{160,90,60,8,5});
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.executeAnim();
                }
            });
        }

    }

    private void setPoints(final LsspBrokenLineView lsspBrokenLineView) {
        //设置点位
        final List<PointBean> list = new ArrayList<>();
        lsspBrokenLineView.post(new Runnable() {
            @Override
            public void run() {
                float time = 0;
                for (int i = 0; i < 11; i++) {
                    float health = 48 + ((new Random().nextFloat() * (53 - 48)));
                    list.add(lsspBrokenLineView.getPointByTimeAndHealth(time, health));
                    time++;
                }
                lsspBrokenLineView.addPoints(list);
                lsspBrokenLineView.excuteAnim();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
