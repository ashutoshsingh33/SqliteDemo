package com.examapp.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examapp.R;
import com.examapp.database.DataBaseHandler;
import com.examapp.utils.AppConstants;
import com.examapp.utils.SnackBarFactory;

public class HomeActivity extends AppCompatActivity implements AppConstants {

    private TextView textName;
    private TextView textExam;
    private TextView textDob;
    private TextView textEmailId;
    private TextView textPassword;
    private TextView textPhone;
    private TextView textAddress;
    private TextView textFatherName;
    private TextView textMotherName;
    private TextView textFatherOcc;
    private TextView textMotherOcc;
    private TextView textYearlyIncome;
    private TextView textCategory;
    private TextView textGender;
    private TextView textPermanentAddress;

    private RelativeLayout relativeRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(TITLE)) {
            int appId = getIntent().getIntExtra(TITLE, 0);
            String password = getIntent().getStringExtra(PASSWORD);

            getDetail(appId, password);
        }
    }

    private void getDetail(int appId, String password) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        Cursor cursor = dataBaseHandler.getUser(appId, password);
        if (cursor == null) return;

        if (cursor.getCount() == 0) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Sorry, No record found");
        } else {
            updateView(cursor);
        }
    }

    private void updateView(Cursor cursor) {
        textName.setText(cursor.getString(1));
        textExam.setText(cursor.getString(2));
        textDob.setText(cursor.getString(3));
        textEmailId.setText(cursor.getString(4));
        textPassword.setText(cursor.getString(5));
        textPhone.setText(cursor.getString(6));
        textAddress.setText(cursor.getString(7));

        textFatherName.setText(cursor.getString(8));
        textMotherName.setText(cursor.getString(9));
        textFatherOcc.setText(cursor.getString(10));
        textMotherOcc.setText(cursor.getString(11));
        textYearlyIncome.setText(cursor.getString(12));
        textCategory.setText(cursor.getString(13));
        textGender.setText(cursor.getString(14));
        textPermanentAddress.setText(cursor.getString(15));
    }

    private void initView() {
        textName = (TextView) findViewById(R.id.text_applicant_name);
        textExam = (TextView) findViewById(R.id.text_qualifying_exam);
        textDob = (TextView) findViewById(R.id.text_dob);
        textEmailId = (TextView) findViewById(R.id.text_email_id);
        textPassword = (TextView) findViewById(R.id.text_password);
        textPhone = (TextView) findViewById(R.id.text_phone_number);
        textAddress = (TextView) findViewById(R.id.text_address);

        textFatherName = (TextView) findViewById(R.id.text_father_name);
        textMotherName = (TextView) findViewById(R.id.text_mother_name);
        textFatherOcc = (TextView) findViewById(R.id.text_father_occ);
        textMotherOcc = (TextView) findViewById(R.id.text_mother_occ);
        textYearlyIncome = (TextView) findViewById(R.id.text_yearly_income);
        textCategory = (TextView) findViewById(R.id.text_category);
        textGender = (TextView) findViewById(R.id.text_gender);
        textPermanentAddress = (TextView) findViewById(R.id.text_permanet_address);


        relativeRoot = (RelativeLayout) findViewById(R.id.relative_root);
    }
}
