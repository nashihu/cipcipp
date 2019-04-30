package com.cipcipp.main.ui.aggactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.cipcipp.main.model.AggModel;

import java.util.ArrayList;
import java.util.List;

public class MultiSpinner extends android.support.v7.widget.AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<String> items;
    private boolean[] selected;
    private MultiSpinnerListener listener;
    private String title;
    private List<AggModel> aggModels;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        selected[which] = isChecked;

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        ArrayList<String> selectedz = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                selectedz.add(String.valueOf(i));
            }
        }
        String spinnerText;
        spinnerText="pilih aplikasi ("+selectedz.size()+")";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { spinnerText });
        setAdapter(adapter);
        listener.onItemsSelected(selected,title,aggModels);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[0]), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(List<String> items, String allText,
                         MultiSpinnerListener listener) {
        this.items = items;
        this.listener = listener;

        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = true;

        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, new String[] { allText });
        setAdapter(adapter);
    }

    public void setAggModels(List<AggModel> aggModels) {
        this.aggModels = aggModels;
    }

    public interface MultiSpinnerListener {
        void onItemsSelected(boolean[] selected,String title,List<AggModel> aggModels);
    }
}