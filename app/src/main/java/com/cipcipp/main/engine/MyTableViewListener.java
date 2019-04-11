package com.cipcipp.main.engine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cipcipp.main.R;
import com.cipcipp.main.model.CellModel;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyTableViewListener implements ITableViewListener {
    private final Context context;
    private final boolean isResultReport;
    private final List<List<CellModel>> cellModels ;

    public MyTableViewListener(final Context context, final boolean isResultReport) {
        this.context = context;
        this.isResultReport = isResultReport;
        this.cellModels = new ArrayList<>();
    }

    public MyTableViewListener(final Context context, final boolean isResultReport,final List<List<CellModel>> cellModels) {
        this.context = context;
        this.isResultReport = isResultReport;
        this.cellModels = cellModels;
    }
    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        if(isResultReport) {
            if(column==3) {
                if(cellModels.size()>0) {
                    String url = cellModels.get(row).get(column).getData().toString();
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Screenshot");
                    LayoutInflater factory = LayoutInflater.from(context);
                    View view = factory.inflate(R.layout.report_image,null);
                    ImageView ss = view.findViewById(R.id.report_alert_image);
                    Picasso.get().load(url).error(R.drawable.ic_error_red_24dp).into(ss);
                    alert.setView(view);
                    alert.setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                }

            }
        }

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

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
