package com.example.administrator.laozidetushu.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/10/27.
 */
public class BookCatalogue extends DataSupport {
    private int id;
    private String bookPath;
    private String bookCatalogue;
    private long bookCatalogueStartPos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    public String getBookCatalogue() {
        return bookCatalogue;
    }

    public void setBookCatalogue(String bookCatalogue) {
        this.bookCatalogue = bookCatalogue;
    }

    public long getBookCatalogueStartPos() {
        return bookCatalogueStartPos;
    }

    public void setBookCatalogueStartPos(long bookCatalogueStartPos) {
        this.bookCatalogueStartPos = bookCatalogueStartPos;
    }
}
