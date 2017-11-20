package com.example.administrator.laozidetushu;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.laozidetushu.base.BaseActivity;

public class MainActivity extends BaseActivity {

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL){
                currentBackPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                finish();
            }
        }else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.seek:
                break;
            case R.id.add_local:
                Intent intent = new Intent(MainActivity.this, AddLocalActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    protected void configView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
