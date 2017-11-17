package com.example.administrator.laozidetushu.View.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/10/30.
 */
public class SimulationAnimation extends AnimationProvider {
    public SimulationAnimation(Bitmap mCurrentBitmap, Bitmap mNextBitmap, int width, int height) {
        super(mCurrentBitmap, mNextBitmap, width, height);

    }

    @Override
    public void drawMove(Canvas canvas) {

    }

    @Override
    public void drawStatic(Canvas canvas) {

    }

    @Override
    public void startAnimation(Scroller scroller) {

    }
}
