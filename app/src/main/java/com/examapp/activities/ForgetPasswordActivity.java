package com.examapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.examapp.R;
import com.examapp.database.DataBaseHandler;
import com.examapp.utils.DialogHelper;
import com.examapp.utils.SnackBarFactory;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearLayoutData;
    private EditText editApplicationId;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private Button buttonSubmit;
    private LinearLayout linearRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    private void initView() {
        linearLayoutData = (LinearLayout) findViewById(R.id.linear_data);
        linearRoot = (LinearLayout) findViewById(R.id.linear_root);
        editApplicationId = (EditText) findViewById(R.id.edit_application_Id);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String applicationId = editApplicationId.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();

        if (validate(applicationId, password, confirmPassword)) {
            if (linearLayoutData.getVisibility() == View.GONE) {
                getUserData(applicationId);
            } else {
                updatePassword(applicationId, password);
            }
        }
    }

    private void updatePassword(String applicationId, String password) {
        try {
            int appId = Integer.parseInt(applicationId);
            DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
            dataBaseHandler.updatePassword(appId, password);
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
                        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void getUserData(String applicationId) {
        try {
            int appId = Integer.parseInt(applicationId);
            DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
            Cursor cursor = dataBaseHandler.getUserByApplicationId(appId);
            if (cursor == null) return;

            if (cursor.getCount() == 0) {
                SnackBarFactory.createSnackBar(this, linearRoot, "please enter valid credential");
            } else {
               linearLayoutData.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            SnackBarFactory.createSnackBar(this, linearRoot, "please enter valid application Id");
        }
    }

    private boolean validate(String applicationId, String password, String confirmPassword) {
        if (TextUtils.isEmpty(applicationId)) {
            SnackBarFactory.createSnackBar(this, linearRoot, "please enter the application Id");
            return false;
        }

        if (linearLayoutData.getVisibility() == View.GONE) return true;

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
