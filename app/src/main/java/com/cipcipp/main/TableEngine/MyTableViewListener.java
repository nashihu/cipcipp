package com.cipcipp.main.TableEngine;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.cipcipp.main.Helper.CellListGenerator;
import com.cipcipp.main.Helper.PulsaParams;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.cipcipp.main.Helper.OpenApp;
import com.cipcipp.main.PulseActivity;
import com.cipcipp.main.R;

public class MyTableViewListener implements ITableViewListener {

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }
    int i = 0;
    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
        if(i < 2){
            Toast.makeText(PulseActivity.getAppContext(), "klik tahan 2detik untuk sort", Toast.LENGTH_SHORT).show();
        }
        i = i + 1;

    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        String rowz = ((TextView) rowHeaderView.itemView.findViewById(R.id.row_header_textview)).getText().toString();
        Toast.makeText(PulseActivity.getAppContext(), "klik tahan untuk buka aplikasi "+rowz, Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        CellListGenerator datagen = new CellListGenerator(PulsaParams.Telkomsel,PulsaParams.Telkomsel);
        Toast.makeText(PulseActivity.getAppContext(),""+row,Toast.LENGTH_SHORT).show();
        datagen.RowDataGeneratorForListener();
        String rowz = ((TextView) rowHeaderView.itemView.findViewById(R.id.row_header_textview)).getText().toString();
        OpenApp.openApp(PulseActivity.getAppContext(),""+datagen.getRowData(rowz));

    }
}
