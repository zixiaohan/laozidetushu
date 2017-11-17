package com.example.administrator.laozidetushu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.laozidetushu.db.Book;

import cn.xfangfang.paperviewlibrary.PaperView;

public class ReadActivity extends AppCompatActivity {

    private final static String EXTRA_BOOK = "bookList";
//    private int screenWidth, screenHeight;
//    private PageWidget bookpage;
//    private PageFactory pageFactory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        initData();
    }

    protected void initData(){
//        bookpage = (PageWidget) findViewById(R.id.bookpage);
//        //获取屏幕的宽高
//        WindowManager manager = getWindowManager();
//        Display display = manager.getDefaultDisplay();
//        Point displaySize = new Point();
//        display.getSize(displaySize);
//        screenHeight = displaySize.y;
//        screenWidth = displaySize.x;
//        bookpage.setPageMode(Config.PAGE_MODE_NONE);
//        pageFactory.setPageWidget(bookpage);

        PaperView paperView = (PaperView) findViewById(R.id.paperview);
        paperView.setChapterName("第162章 诸葛月的视频");
        paperView.setContentPadding(15);
        paperView.setText(String.valueOf(R.string.text));
    }

    public static Boolean openBook(final Book book, Activity context){
        if (book == null){
            throw new NullPointerException("图书不能为空");
        }

        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        return true;
    }
}
