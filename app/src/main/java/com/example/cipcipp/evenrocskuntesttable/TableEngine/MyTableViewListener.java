package com.example.cipcipp.evenrocskuntesttable.TableEngine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.evrencoskun.tableview.listener.ITableViewListener;
import com.example.cipcipp.evenrocskuntesttable.MainActivity;

public class MyTableViewListener implements ITableViewListener {
    private Context context;
    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
        if(columnHeaderView != null && columnHeaderView instanceof MyTableViewAdapter.MyColumnHeaderViewHolder) {
            Toast.makeText(MainActivity.getAppContext(), "mymessage ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }
}
