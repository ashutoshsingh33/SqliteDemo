package com.examapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.examapp.R;
import com.examapp.database.DataBaseHandler;
import com.examapp.utils.AppConstants;
import com.examapp.utils.DialogHelper;
import com.examapp.utils.SnackBarFactory;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, AppConstants {

    private EditText editPassword;
    private EditText editConfirmPassword;
    private Button buttonSubmit;
    private LinearLayout linearRoot;
    private int applicationId;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(APPLICATION_ID)) {
            applicationId = getIntent().getIntExtra(APPLICATION_ID, 0);
            password = getIntent().getStringExtra(PASSWORD);
        }
    }

    private void initView() {
        linearRoot = (LinearLayout) findViewById(R.id.linear_root);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String password = editPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();

        if (validate(password, confirmPassword)) {
            Log.e("passwordID", applicationId +"");
            updatePassword(applicationId, password);
        }
    }

    private void updatePassword(int applicationId, String password) {
        try {
            DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
            dataBaseHandler.updatePassword(applicationId, password);
            showPopUp();
        } catch (Exception e) {
            SnackBarFactory.createSnackBar(this, linearRoot, "please enter valid application Id");
        }
    }

    private void showPopUp() {
        DialogHelper.showAlertPopup(this, "Password updated successfully",
                new DialogHelper.ConfirmPopUp() {
                    @Override
                    public void onConfirm(boolean isConfirm) {
                        Intent intent = new Intent(ChangePasswordActivity.this, DetailFormActivity.class);
                        intent.putExtra(APPLICATION_ID, applicationId);
                        intent.putExtra(PASSWORD, password);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private boolean validate(String password, String confirmPassword) {

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, linearRoot, "Please enter the password");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, linearRoot, "Please enter the confirm password");
            return false;
        } else if (!password.equals(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, linearRoot, "Password and confirm password does not match");
            return false;
        }

        return true;
    }
}
