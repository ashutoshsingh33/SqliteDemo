package com.examapp.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.examapp.utils.MyDatePickerDialog;
import com.examapp.utils.SnackBarFactory;
import com.examapp.utils.StringUtils;

import java.util.ArrayList;
import java.util.Random;

public class NewApplicantActivity extends AppCompatActivity implements View.OnClickListener, AppConstants {

    private TextView textQualifiedExam;
    private EditText editApplicantName;
    private EditText editPhoneNo;
    private EditText editAddress;
    private TextView textDob;
    private Button buttonSubmit;
    private EditText ediEmail;
    private RelativeLayout relativeRoot;
    private EditText editPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_aaplicant);
        initView();
    }

    private void initView() {
        editApplicantName = (EditText) findViewById(R.id.edit_applicant_name);
        editPhoneNo = (EditText) findViewById(R.id.edit_phone_number);
        editAddress = (EditText) findViewById(R.id.edit_address);
        ediEmail = (EditText) findViewById(R.id.edit_email_id);
        editPassword = (EditText) findViewById(R.id.edit_password);

        textQualifiedExam = (TextView) findViewById(R.id.text_qualifying_exam);
        textQualifiedExam.setOnClickListener(this);

        textDob = (TextView) findViewById(R.id.text_dob);
        textDob.setOnClickListener(this);

        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);

        relativeRoot = (RelativeLayout) findViewById(R.id.relative_root);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.text_qualifying_exam:
                openSelection();
                break;

            case R.id.text_dob:
                showDatePicker();
                break;

            case R.id.button_submit:
                saveDetail();
                break;
        }
    }

    private void saveDetail() {
        String name = editApplicantName.getText().toString();
        String exam = textQualifiedExam.getText().toString();
        String dob = textDob.getText().toString();
        String email = ediEmail.getText().toString();
//        String password = editPassword.getText().toString();
        String phone = editPhoneNo.getText().toString();
        String address = editAddress.getText().toString();

        if (validate(name, exam, dob, email, phone, address)) {
            User user = new User();
            user.setName(name);
            user.setQualifyingExam(exam);
            user.setDob(dob);
            user.setEmailId(email);
            user.setPhoneNumber(phone);
            user.setAddress(address);
            saveData(user);
        }
    }

    private void saveData(User user) {

        long milis = System.currentTimeMillis();
        Random random = new Random(milis);
        int aplicatioId = random.nextInt(999999);
        user.setId(aplicatioId);
        Log.e("application Id", aplicatioId + "");

        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String password = generateRandomChars(candidateChars, 6);
        Log.e("password", password);
        user.setPassword(password);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        dataBaseHandler.addUser(user);

        showPopUp(aplicatioId, password);

    }

    private void showPopUp(final int aplicatioId, final String password) {
        DialogHelper.showAlertPopup(this, "Your application ID is " + aplicatioId + " and password is " + password+ ". Please save for future reference",
                new DialogHelper.ConfirmPopUp() {
                    @Override
                    public void onConfirm(boolean isConfirm) {
                        Intent intent = new Intent(NewApplicantActivity.this, ChangePasswordActivity.class);
                        intent.putExtra(APPLICATION_ID, aplicatioId);
                        intent.putExtra(PASSWORD, password);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private boolean validate(String name, String exam, String dob, String emailId, String phone, String address) {
        if (TextUtils.isEmpty(name)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please enter the name");
            return false;
        }

        if (TextUtils.isEmpty(exam) || textQualifiedExam.getText().toString().equals("Qualifying exam")) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please select exam");
            return false;
        }


        if (TextUtils.isEmpty(dob) || textDob.getText().toString().equals("DOB")) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please select DOB");
            return false;
        }

        if (TextUtils.isEmpty(emailId)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter emailId");
            return false;
        } else if (!StringUtils.isValidEmailId(emailId)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter valid emailId");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please enter phone number");
            return false;
        } else if (phone.length() < 10) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "Please enter 10 digit phone number");
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, relativeRoot, "please enter the address");
            return false;
        }


        return true;
    }


    private void openSelection() {
        ArrayList<String> exams = new ArrayList<>();
        exams.add("1");
        exams.add("2");
        exams.add("3");
        exams.add("4");
        exams.add("5");
        exams.add("6");
        exams.add("7");
        exams.add("8");
        exams.add("9");
        exams.add("10");
        exams.add("11");
        exams.add("12");

        Intent selectionIntent = new Intent(this, SelectActivity.class);
        selectionIntent.putExtra(DATA, exams);
        selectionIntent.putExtra(TITLE, "Exam");
        startActivityForResult(selectionIntent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case 1000:
                    textQualifiedExam.setText(data.getExtras().get(SELECTED_DATA).toString());
                    break;
            }
        }
    }

    private void showDatePicker() {
        DialogFragment myDatePickerDialog = new MyDatePickerDialog(new MyDatePickerDialog.DateListener() {
            @Override
            public void onDateSelected(String date) {
                textDob.setText(date);
            }
        }, false);
        myDatePickerDialog.show(getFragmentManager(), "DatePicker");
    }

    public String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
}
