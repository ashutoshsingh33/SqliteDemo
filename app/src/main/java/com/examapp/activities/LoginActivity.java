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
import android.widget.RelativeLayout;

import com.examapp.R;
import com.examapp.database.DataBaseHandler;
import com.examapp.utils.AppConstants;
import com.examapp.utils.SnackBarFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AppConstants {

    private EditText editApplicantionId;
    private EditText editPassword;
    private Button buttonLogin;
    private RelativeLayout relativeRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        editApplicantionId = (EditText) findViewById(R.id.edit_application_id);
        editPassword = (EditText) findViewById(R.id.edit_password);
        relativeRoot = (RelativeLayout) findViewById(R.id.relative_root);

        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String applicationId = editApplicantionId.getText().toString();
        String password = editPassword.getText().toString();

        if (validate(applicationId, password)) {
            getDetail(applicationId, password);
        }
    }

    private void getDetail(String applicationId, String password) {
        int appId = Integer.parseInt(applicationId);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        Cursor cursor = dataBaseHandler.getUser(appId, password);
        if (cursor == null) return;

        if (cursor.getCount() == 0) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please enter valid credential");
        } else {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.putExtra(TITLE, appId);
            homeIntent.putExtra(PASSWORD, password);
            startActivity(homeIntent);
        }
    }

    private boolean validate(String applicationId, String password) {
        if (TextUtils.isEmpty(applicationId)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please enter the application Id");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter the password");
            return false;
        }

        return true;
    }

    public void forgotPasswordTapped(View view) {
       startActivity(new Intent(this, ForgetPasswordActivity.class));
    }
}
