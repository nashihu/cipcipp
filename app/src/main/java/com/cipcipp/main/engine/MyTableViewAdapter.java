package com.cipcipp.main.engine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.cipcipp.main.holder.MyColumnHeaderViewHolder;
import com.cipcipp.main.model.CellModel;
import com.cipcipp.main.model.ColumnHeaderModel;
import com.cipcipp.main.model.RowHeaderModel;
import com.cipcipp.main.R;

public class MyTableViewAdapter extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel, CellModel> {
    public MyTableViewAdapter(Context context) {
        super(context);
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.cell,parent,false);
        return new MyCellViewHolder(layout);
    }
    private class MyCellViewHolder extends AbstractViewHolder {

        private final TextView cell_textview;

        private MyCellViewHolder(View itemView) {
            super(itemView);
            cell_textview =  itemView.findViewById(R.id.cell_data);
        }
    }


    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.column_header,parent,false);
        return new MyColumnHeaderViewHolder(layout,getTableView());
    }


    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {
        ColumnHeaderModel columnHeader = (ColumnHeaderModel) columnHeaderItemModel;
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeaderModel(columnHeader,columnPosition);

    }
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.row_header,parent,false);
        return new MyRowHeaderViewHolder(layout);
    }
    private class MyRowHeaderViewHolder extends AbstractViewHolder {

        private final TextView cell_textview;

        private MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            cell_textview = itemView.findViewById(R.id.row_header_textview);
        }
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {
        RowHeaderModel rowHeader = (RowHeaderModel) rowHeaderItemModel;
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
        rowHeaderViewHolder.cell_textview.setText(rowHeader.getData());
    }




    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        CellModel cell = (CellModel) cellItemModel;
        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        viewHolder.cell_textview.setText(cell.getData().toString());
        viewHolder.itemView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();
    }
    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(mContext).inflate(R.layout.corner,null);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }






}
