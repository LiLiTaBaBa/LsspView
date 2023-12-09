# LsspView
[![](https://jitpack.io/v/LiLiTaBaBa/LsspView.svg)](https://jitpack.io/#LiLiTaBaBa/LsspView)

### Lssp自定义View的实列
### Lssp原型头像显示View
### Lssp圆形数据显示View
### Lssp南丁格尔玫瑰图View
### Lssp南丁格尔玫瑰饼状图View
### Lssp折线图View
### Lssp柱状图View 

![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/index.jpg)
![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/LsspHeaderView.jpg)
![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/LsspBrokenLineView.jpg)
![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/LsspCircleDataInfoView.jpg)
![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/LsspRoseLeafView.jpg)
![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/LsspWarningRankView.jpg)
![image](https://github.com/LiLiTaBaBa/LsspView/blob/master/pic/LsspColumnarView.jpg)

```
//折线图添加随机点
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
```
