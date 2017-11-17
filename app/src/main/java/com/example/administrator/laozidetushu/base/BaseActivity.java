package com.example.administrator.laozidetushu.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.administrator.laozidetushu.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/9.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar mtoolbar;

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        transparent();
        context = this;
        ButterKnife.bind(this);
        mtoolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mtoolbar != null){
            initToolBar();
            setSupportActionBar(mtoolbar);
        }
        initData();
        configView();
    }

    protected abstract void configView();

    protected abstract void initData();

    protected abstract void initToolBar();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void transparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void toolbarSetElevation(float elevation) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mtoolbar.setElevation(elevation);
//        }
//    }


    public abstract int getLayoutId();
}
