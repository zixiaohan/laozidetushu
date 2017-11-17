package com.example.administrator.laozidetushu.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.administrator.laozidetushu.Config;
import com.example.administrator.laozidetushu.View.animation.AnimationProvider;
import com.example.administrator.laozidetushu.View.animation.NoneAnimation;
import com.example.administrator.laozidetushu.util.PageFactory;

/**
 * Created by Administrator on 2017/10/30.
 */
public class PageWidget extends View {

    private final static String TAG = "BookPageWidget";
    private Context context;
    private int mScreenWidth = 0; //屏幕宽
    private int mScreenHeight = 0; //屏幕高

    //是否移动了
    private Boolean isMove = false;
    //是否翻到下一页
    private Boolean isNext = false;
    //是否没下一页或者上一页
    private Boolean noNext = false;
    //是否取消翻页
    private Boolean cancelPage = false;

    Bitmap mCurPageBitmap = null;//当前页
    Bitmap mNextPageBitmap = null;//下一页

    //手指施放时坐标
    private int downX = 0;
    private int downY = 0;

    //移动了的坐标
    private int moveX = 0;
    private int moveY = 0;
    //翻页动画是否在执行
    private Boolean isRuning = false;

    Scroller mScroller;
    private int mBgColor = 0xFFCEC29C;
    private TouchListener mTouchListener;

    private AnimationProvider animationProvider;

    public PageWidget(Context context) {
        this(context, null);
    }

    public PageWidget(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PageWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPage();
        mScroller = new Scroller(getContext(), new LinearInterpolator());
        animationProvider = new NoneAnimation(mCurPageBitmap, mNextPageBitmap,
                mScreenWidth, mScreenHeight);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            animationProvider.setTouchPoint(x, y);
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y){
                isRuning = false;
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (PageFactory.getStatus() == PageFactory.Status.OPENING) {
            return true;
        }

        int x = (int) event.getX();
        int y = (int) event.getY();

        animationProvider.setTouchPoint(x, y);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) event.getX();
            downY = (int) event.getY();
            moveX = 0;
            moveY = 0;
            isMove = false;
            isNext = false;
            noNext = false;
            isRuning = false;
            animationProvider.setStartPoint(downX, downY);
            abortAnimation();
            Log.e(TAG,"ACTION_DOWN");
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            final int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            //判断是否移动了
            if (!isMove) {
                isMove = Math.abs(downX - x) > slop || Math.abs(downY - y) > slop;
            }

            if (isMove) {
                isMove = true;
                if (moveX == 0 && moveY == 0) {
                    Log.e(TAG,"isMove");
                    //判断是翻的上一页还是下一页
                    if (x - downX > 0) {
                        isNext = false;
                    } else {
                        isNext = true;
                    }
                    cancelPage = false;
                    if (isNext) {
                        Boolean isNext = mTouchListener.nextPage();
                        animationProvider.setDirection(AnimationProvider.Direction.next);

                        if (!isNext) {
                            noNext = true;
                            return true;
                        }
                    }else {
                        Boolean isPre = mTouchListener.prePage();
                        animationProvider.setDirection(AnimationProvider.Direction.pre);

                        if (!isPre){
                            noNext = true;
                            return true;
                        }
                    }
                    Log.e(TAG,"isNext:" + isNext);
                } else {
                    //判断是否取消翻页
                    if (isNext){
                        if (x - moveX > 0){
                            cancelPage = true;
                            animationProvider.setCancel(true);
                        }else {
                            cancelPage = false;
                            animationProvider.setCancel(false);
                        }
                    }else {
                        if (x - moveX < 0){
                            animationProvider.setCancel(true);
                            cancelPage = true;
                        }else {
                            animationProvider.setCancel(false);
                            cancelPage = false;
                        }
                    }
                    Log.e(TAG,"cancelPage:" + cancelPage);
                }

                moveX = x;
                moveY = y;
                isRuning = true;
                this.postInvalidate();
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            Log.e(TAG,"ACTION_UP");
            if (!isMove){
                cancelPage = false;
                //是否点击了中间
                if (downX > mScreenWidth / 5 && downX < mScreenWidth * 4 / 5
                        && downY > mScreenHeight / 3 && downY < mScreenHeight * 2 / 3){
                    if (mTouchListener != null){
                        mTouchListener.center();
                    }
                    Log.e(TAG,"center");
                    return true;
                }else if (x < mScreenWidth / 2){
                    isNext = false;
                }else {
                    isNext = true;
                }

                if (isNext){
                    Boolean isNext = mTouchListener.nextPage();
                    animationProvider.setDirection(AnimationProvider.Direction.next);
                    if (!isNext){
                        return true;
                    }
                }else {
                    Boolean isPre = mTouchListener.prePage();
                    animationProvider.setDirection(AnimationProvider.Direction.pre);
                    if (!isPre){
                        return true;
                    }
                }
            }

            if (cancelPage && mTouchListener != null){
                mTouchListener.cancel();
            }

            Log.e(TAG,"isNext:" + isNext);
            if (!noNext){
                isRuning = true;
                animationProvider.startAnimation(mScroller);
                this.postInvalidate();
            }
        }

        return true;
    }

    private void abortAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
            animationProvider.setStartPoint(mScroller.getFinalX(), mScroller.getFinalY());
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mBgColor);
        if (isRuning) {
            animationProvider.drawMove(canvas);
        }
        {
            animationProvider.drawStatic(canvas);
        }
    }

    private void initPage() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metri = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metri);
        mScreenWidth = metri.widthPixels;
        mScreenHeight = metri.heightPixels;
        mCurPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565);
        mNextPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565);
    }

    public void setPageMode(int pageMode){
        switch (pageMode){
            case Config.PAGE_MODE_NONE:
                animationProvider = new NoneAnimation(mCurPageBitmap, mNextPageBitmap,
                        mScreenWidth, mScreenHeight);
                break;
        }
    }

    public Boolean isRunning(){
        return isRuning;
    }

    public void setTouchListener(TouchListener mTouchListener) {
        this.mTouchListener = mTouchListener;
    }

    public interface TouchListener {
        void center();

        Boolean prePage();

        Boolean nextPage();

        void cancel();
    }
}
