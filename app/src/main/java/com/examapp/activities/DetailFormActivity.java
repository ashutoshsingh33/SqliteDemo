package com.examapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examapp.R;
import com.examapp.database.DataBaseHandler;
import com.examapp.model.User;
import com.examapp.utils.AppConstants;
import com.examapp.utils.DialogHelper;
import com.examapp.utils.SnackBarFactory;
import com.examapp.utils.StringUtils;

import java.util.ArrayList;

public class DetailFormActivity extends AppCompatActivity implements View.OnClickListener, AppConstants{

    private EditText editFatherName;
    private EditText editMotherrName;
    private EditText editFatherOccupation;
    private EditText editMotherOccupation;
    private EditText editIncome;
    private EditText editPermanentAddress;
    private TextView textGender;
    private TextView textCategory;
    private Button buttonSubmit;
    private RelativeLayout relativeRoot;
    private int applicationId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_form);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(APPLICATION_ID)) {
            applicationId = getIntent().getIntExtra(APPLICATION_ID, 0);
        }
    }

    private void initView() {
        editFatherName = (EditText) findViewById(R.id.edit_father_name);
        editMotherrName = (EditText) findViewById(R.id.edit_mother_name);
        editFatherOccupation = (EditText) findViewById(R.id.edit_father_occupation);
        editMotherOccupation = (EditText) findViewById(R.id.edit_mother_occupation);
        editIncome = (EditText) findViewById(R.id.edit_yearly_income);
        textCategory = (TextView) findViewById(R.id.text_category);
        textGender = (TextView) findViewById(R.id.text_gender);
        editPermanentAddress = (EditText) findViewById(R.id.edit_permanent_address);
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        relativeRoot = (RelativeLayout) findViewById(R.id.relative_root);
        buttonSubmit.setOnClickListener(this);

        textCategory.setOnClickListener(this);
        textGender.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.text_category:
                openCategorySelection();
                break;

            case R.id.text_gender:
                openGenderSelection();
                break;

            case R.id.button_submit:
                submitTapped();
                break;
        }
    }

    private void submitTapped() {
        String fatherName = editFatherName.getText().toString();
        String motherName = editMotherrName.getText().toString();
        String fatherOcc = editFatherOccupation.getText().toString();
        String motherOcc = editMotherOccupation.getText().toString();
        String income = editIncome.getText().toString();
        String category = textCategory.getText().toString();
        String gender = textGender.getText().toString();
        String address = editPermanentAddress.getText().toString();

        if(validate(fatherName, motherName, fatherOcc, motherOcc, income, category, gender, address)){
            User user = new User();
            user.setFatherName(fatherName);
            user.setMotherName(motherName);
            user.setFatherOccupation(fatherOcc);
            user.setMotherOccupation(motherOcc);
            user.setYearlyIncome(income);
            user.setCategory(category);
            user.setGender(gender);
            user.setPermanentAddress(address);

            DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
            try {
                dataBaseHandler.updateUser(user, applicationId);
                showPopUp();
            } catch (Exception e){

            }

        }
    }

    private void showPopUp() {
        DialogHelper.showAlertPopup(this, "Please login to proceed",
                new DialogHelper.ConfirmPopUp() {
                    @Override
                    public void onConfirm(boolean isConfirm) {
                        Intent intent = new Intent(DetailFormActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private boolean validate(String fatherName, String motherName, String fatherOcc, String motherOcc, String income, String category,
                             String gender, String add) {
        if (TextUtils.isEmpty(fatherName)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please enter the father name");
            return false;
        }

        if (TextUtils.isEmpty(motherName)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter the mother name");
            return false;
        }


        if (TextUtils.isEmpty(fatherOcc)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter father occupation");
            return false;
        }

        if (TextUtils.isEmpty(motherOcc)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter mother occupation");
            return false;
        }

        if (TextUtils.isEmpty(income)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter yearly income");
            return false;
        }

        if (TextUtils.isEmpty(category) || category.equals("Category")) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please select category");
            return false;
        }

        if (TextUtils.isEmpty(gender) || gender.equals("Gender")) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please select gender");
            return false;
        }

        if (TextUtils.isEmpty(add)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter permanent address");
            return false;
        }


        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case 1000:
                    textGender.setText(data.getExtras().get(SELECTED_DATA).toString());
                    break;

                case 1001:
                    textCategory.setText(data.getExtras().get(SELECTED_DATA).toString());
                    break;
            }
        }
    }

    private void openGenderSelection() {
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");

        Intent selectionIntent = new Intent(this, SelectActivity.class);
        selectionIntent.putExtra(DATA, genders);
        selectionIntent.putExtra(TITLE, "Gender");
        startActivityForResult(selectionIntent, 1000);
    }

    private void openCategorySelection() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("General");
        categories.add("OBC");
        categories.add("SC");
        categories.add("ST");

        Intent selectionIntent = new Intent(this, SelectActivity.class);
        selectionIntent.putExtra(DATA, categories);
        selectionIntent.putExtra(TITLE, "Category");
        startActivityForResult(selectionIntent, 1001);
    }
}
