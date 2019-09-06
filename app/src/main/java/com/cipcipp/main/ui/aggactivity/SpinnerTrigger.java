package com.cipcipp.main.ui.aggactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;

public class SpinnerTrigger extends android.support.v7.widget.AppCompatSpinner {

    public SpinnerTrigger(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
    }

    public SpinnerTrigger(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public SpinnerTrigger(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

//    @Override
//    public void setSelection(int position, boolean animate) {
//        ignoreOldSelectionByReflection();
//        super.setSelection(position, animate);
//    }
//
//    private void ignoreOldSelectionByReflection() {
////        Toast.makeText(getContext(), "kepencett", Toast.LENGTH_SHORT).show();
////        try {
////            Class<?> c = this.getClass().getSuperclass().getSuperclass().getSuperclass();
////            Field reqField = c.getDeclaredField("mOldSelectedPosition");
////            reqField.setAccessible(true);
////            reqField.setInt(this, -1);
////        } catch (Exception e) {
////            Log.d("Exception Private", "ex", e);
////            // TODO: handle exception
////        }
//    }
//
//    @Override
//    public void setSelection(int position) {
//        ignoreOldSelectionByReflection();
//        super.setSelection(position);
//    }

    @Override public void
    setSelection(int position, boolean animate)
    {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override public void
    setSelection(int position)
    {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

}