package com.cipcipp.main.holder;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.sort.SortState;
import com.cipcipp.main.model.ColumnHeaderModel;
import com.cipcipp.main.R;

public class MyColumnHeaderViewHolder extends AbstractSorterViewHolder {
    private final LinearLayout column_header_container;
    private final TextView column_header_textview;
    private final ImageButton column_header_sort_button;
    private final ITableView tableView;


    public MyColumnHeaderViewHolder(View itemView, ITableView pTableView) {
        super(itemView);
        tableView = pTableView;
        column_header_textview = itemView.findViewById(R.id.column_header_textView);
        column_header_container = itemView.findViewById(R.id.column_header_container);
        column_header_sort_button = itemView.findViewById(R.id.column_header_sort_imageButton);

        // Set click listener to the sort button
        column_header_textview.setOnClickListener(mSortButtonClickListener);
        column_header_container.setOnClickListener(mSortButtonClickListener);
        column_header_sort_button.setOnClickListener(mSortButtonClickListener);
    }

    public void setColumnHeaderModel(ColumnHeaderModel pColumnHeaderModel, int pColumnPosition) {

        // Change alignment of textView
        column_header_textview.setGravity(COLUMN_TEXT_ALIGNS[pColumnPosition] | Gravity
                .CENTER_VERTICAL);

        // Set text data
        column_header_textview.setText(pColumnHeaderModel.getData());

        // It is necessary to remeasure itself.
        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        column_header_textview.requestLayout();
    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
        super.setSelected(p_nSelectionState);

        int nBackgroundColorId;
        int nForegroundColorId;

        if (p_nSelectionState == SelectionState.SELECTED) {
            nBackgroundColorId = R.color.selected_background_color;
            nForegroundColorId = R.color.selected_text_color;

        } else if (p_nSelectionState == SelectionState.UNSELECTED) {
            nBackgroundColorId = R.color.unselected_header_background_color;
            nForegroundColorId = R.color.selected_text_color_2;

        } else { // SelectionState.SHADOWED

            nBackgroundColorId = R.color.shadow_background_color;
            nForegroundColorId = R.color.selected_text_color;
        }

        column_header_container.setBackgroundColor(ContextCompat.getColor(column_header_container
                .getContext(), nBackgroundColorId));
        column_header_textview.setTextColor(ContextCompat.getColor(column_header_container
                .getContext(), nForegroundColorId));
    }

    @Override
    public void onSortingStatusChanged(SortState pSortState) {
        super.onSortingStatusChanged(pSortState);

        // It is necessary to remeasure itself.
        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;

        controlSortState(pSortState);

        column_header_textview.requestLayout();
        column_header_sort_button.requestLayout();
        column_header_container.requestLayout();
        itemView.requestLayout();
    }

    private void controlSortState(SortState pSortState) {
        if (pSortState == SortState.ASCENDING) {
            Log.v("formalitas","asc");
//            column_header_sort_button.setVisibility(View.VISIBLE);
//            column_header_sort_button.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

        } else if (pSortState == SortState.DESCENDING) {
            Log.v("formalitas","desc");
//            column_header_sort_button.setVisibility(View.VISIBLE);
//            column_header_sort_button.setImageResource(R.drawable.ic_arrow_up);
        } else {
            Log.v("formalitas","null");
//            column_header_sort_button.setVisibility(View.GONE);
//            column_header_sort_button.setImageResource(R.drawable.ic_sort);
        }
    }

    private View.OnClickListener mSortButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getSortState() == SortState.ASCENDING) {
                column_header_sort_button.setVisibility(View.GONE);
                tableView.sortColumn(getAdapterPosition(), SortState.DESCENDING);
            } else if (getSortState() == SortState.DESCENDING) {
                column_header_sort_button.setVisibility(View.GONE);
                tableView.sortColumn(getAdapterPosition(), SortState.ASCENDING);
            } else {
                column_header_sort_button.setVisibility(View.GONE);
                tableView.sortColumn(getAdapterPosition(), SortState.ASCENDING);
            }
        }
    };

    private static final int[] COLUMN_TEXT_ALIGNS = {
            // Id
            Gravity.CENTER,
            // Name
            Gravity.START,
            // Nickname
            Gravity.START,
            // Email
            Gravity.START,
            // BirthDay
            Gravity.CENTER,
            // Gender (Sex)
            Gravity.CENTER,
            // Age
            Gravity.CENTER,
            // Job
            Gravity.START,
            // Salary
            Gravity.CENTER,
            // CreatedAt
            Gravity.CENTER,
            // UpdatedAt
            Gravity.CENTER,
            // Address
            Gravity.START,
            // Zip Code
            Gravity.END,
            // Phone
            Gravity.END,
            // Fax
            Gravity.END};
}
