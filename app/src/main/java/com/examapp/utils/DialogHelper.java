package com.examapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DialogHelper {

    public interface ConfirmPopUp {
        void onConfirm(boolean isConfirm);
    }

    public static AlertDialog showAlertPopup(Activity activity, String message, final ConfirmPopUp confirmPopup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        confirmPopup.onConfirm(true);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
