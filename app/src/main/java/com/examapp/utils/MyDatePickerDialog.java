package com.examapp.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class MyDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private  DateListener dateListener;
    private boolean showPrevious;

    public interface DateListener{
        void onDateSelected(String strDate);
    }

    @SuppressLint("ValidFragment")
    public MyDatePickerDialog(DateListener dateListener, boolean showPrevious) {
        this.dateListener = dateListener;
        this.showPrevious = showPrevious;
    }

    public MyDatePickerDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);
        DatePickerDialog pickerDialog;
        if (showPrevious) {
            pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        } else pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

        return pickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String strDate=(month + 1) + "-" + String.valueOf(day) + "-" +  year;
        dateListener.onDateSelected(strDate);
    }
}
