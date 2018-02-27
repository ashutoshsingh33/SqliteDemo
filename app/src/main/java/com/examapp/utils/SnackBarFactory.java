package com.examapp.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

public class SnackBarFactory {

    public static Snackbar createSnackBar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();

        return snackbar;
    }

    public static Snackbar createSnackBarIndefinite(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        return snackbar;
    }

    public static Snackbar createSnackBarMultiLine(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        TextView snackBarTextView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackBarTextView.setMaxLines(999);
        snackbar.show();

        return snackbar;
    }
}

