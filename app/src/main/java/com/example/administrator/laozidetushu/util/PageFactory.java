package com.example.administrator.laozidetushu.util;

import com.example.administrator.laozidetushu.View.PageWidget;

/**
 * Created by Administrator on 2017/10/30.
 */
public class PageFactory {

    private static Status mStatus = Status.OPENING;
    //书本widget
    private PageWidget mBookPageWidget;

    public enum Status {
        OPENING,
        FINISH,
        FAIL,
    }

    public static Status getStatus(){
        return mStatus;
    }

    public void setPageWidget (PageWidget mBookPageWidget){
        this.mBookPageWidget = mBookPageWidget;
    }
}
