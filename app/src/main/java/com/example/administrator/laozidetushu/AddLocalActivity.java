package com.example.administrator.laozidetushu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.laozidetushu.adapter.BookAdapter;
import com.example.administrator.laozidetushu.base.BaseActivity;
import com.example.administrator.laozidetushu.db.Book;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.Bind;

public class AddLocalActivity extends BaseActivity {

    private BookAdapter adapter;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<Book> books;
    private List<Book> booklist;
    private File file;
    
    public static void startActivity(Context context){
        context.startActivity(new Intent(context, AddLocalActivity.class));
    }

    @Override
    protected void configView() {
        queryFiles();
    }

    private void queryFiles() {
        booklist = DataSupport.findAll(Book.class);
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)){
            file = Environment.getRootDirectory();
            listFileTxt(file);
        }else {
            Toast.makeText(this,"请插入CD卡",Toast.LENGTH_SHORT).show();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new BookAdapter(books);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Book book = books.get(position);
                new AlertDialog.Builder(AddLocalActivity.this)
                        .setTitle("提示")
                        .setMessage(String.format("是否将《%1$s》加入书架",
                                book.getBookName()))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (Book book1 : booklist){
                                    if (book1.getBookPath() != null &&
                                            book1.getBookPath().equals(book.getBookPath())){

                                    }
                                }
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * 递归查找SD卡上所有书籍
     */
    private void listFileTxt(File file) {
        File[] files = file.listFiles();
        if (files != null){
            for (File f : files) {
                if (!f.isDirectory()) {
                    if (f.getName().endsWith(".txt")) {
                        //获取并计算文件大小
                        long size = f.length();
                        String t_size = "";
                        if (size <= 1024) {
                            t_size = size + "B";
                        } else if (size > 1024 && size <= 1024 * 1024) {
                            size /= 1024;
                            t_size = size + "KB";
                        } else {
                            size = size / (1024 * 1024);
                            t_size = size + "MB";
                        }
                        Book book = new Book();
                        book.setBookImg(R.drawable.txt);
                        book.setBookName(f.getName());
                        book.setBookPath(f.getAbsolutePath());
                        book.setBookSize(t_size);
                        books.add(book);
                    }
                } else if (f.isDirectory()) {
                    //如果是目录，迭代进入该目录
                    listFileTxt(f);
                }
            }
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initToolBar() {
        mtoolbar.setTitle("扫描本地图书");
        mtoolbar.setNavigationIcon(R.drawable.back);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_local;
    }
}
