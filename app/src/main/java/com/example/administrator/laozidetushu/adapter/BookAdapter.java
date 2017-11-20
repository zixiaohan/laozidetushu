package com.example.administrator.laozidetushu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.laozidetushu.R;
import com.example.administrator.laozidetushu.db.Book;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements View.OnClickListener{

    private List<Book> bookList;
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImg;
        TextView bookName;
        TextView bookSize;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            bookImg = (ImageView) itemView.findViewById(R.id.book_img);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
            bookSize = (TextView) itemView.findViewById(R.id.book_size);
            checkBox = (CheckBox) itemView.findViewById(R.id.contact_selected_checkbox);
            checkBox.setVisibility(View.GONE);
        }
    }

    public BookAdapter(List<Book> bookList){
        this.bookList = bookList;
    }

    public static interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookImg.setImageResource(book.getBookImg());
        holder.bookName.setText(book.getBookName());
        holder.bookSize.setText(book.getBookSize());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
