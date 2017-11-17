package com.example.administrator.laozidetushu;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/11/6.
 */
public class Config {
    private final static String SP_NAME = "config";

    public final static int PAGE_MODE_SIMULATION = 0;
    public final static int PAGE_MODE_COVER = 1;
    public final static int PAGE_MODE_SLIDE = 2;
    public final static int PAGE_MODE_NONE = 3;

    private Context context;
    private static Config config;
    private SharedPreferences sp;

    private Config(Context context){
        this.context = context.getApplicationContext();
        sp = this.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}
