package com.examapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.examapp.model.User;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String TABLE_USER = "user";
    private static final String DATABASE_NAME = "userManager";
    private static final int version = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUALIFYING_EXAM = "qualifyingExam";
    private static final String KEY_DOB = "dob";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_PHONE= "phone";
    private static final String KEY_PASSWORD= "password";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_FATHER_NAME = "fatherName";
    private static final String KEY_MOTHER_NAME = "motherName";
    private static final String KEY_FATHER_OCCUPATION = "fatherOccupation";
    private static final String KEY_MOTHER_OCCUPATION = "motherOccupation";
    private static final String KEY_YEARLY_INCOME = "yearlyIncome";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PERMANET_ADDRESS = "permanentAddress";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "Create TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_QUALIFYING_EXAM + " TEXT,"
                + KEY_DOB + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_FATHER_NAME + " TEXT,"
                + KEY_MOTHER_NAME + " TEXT,"
                + KEY_FATHER_OCCUPATION + " TEXT,"
                + KEY_MOTHER_OCCUPATION + " TEXT,"
                + KEY_YEARLY_INCOME + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_PERMANET_ADDRESS + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   public void addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_QUALIFYING_EXAM, user.getQualifyingExam());
        values.put(KEY_DOB, user.getDob());
        values.put(KEY_EMAIL, user.getEmailId());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PHONE, user.getPhoneNumber());
        values.put(KEY_ADDRESS, user.getAddress());
        database.insert(TABLE_USER, null, values);
        database.close();
    }

    public Cursor getUser(int applicationId, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +" WHERE " + KEY_ID + " = "+ applicationId
                + " AND "
                + KEY_PASSWORD + " = '" + password + "'";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getUserByApplicationId(int applicationId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +" WHERE " + KEY_ID + " = "+ applicationId;
        Cursor cursor = database.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void updatePassword(int applicationId, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_USER + " SET " + KEY_PASSWORD + " = '" + password + "'" +
                " WHERE " + KEY_ID + " = "+ applicationId;
        Cursor c= database.rawQuery(query, null);

        c.moveToFirst();
        c.close();
    }

    public void updateUser(User user, int applicationId){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FATHER_NAME, user.getFatherName());
        contentValues.put(KEY_MOTHER_NAME, user.getMotherName());
        contentValues.put(KEY_FATHER_OCCUPATION, user.getFatherOccupation());
        contentValues.put(KEY_MOTHER_OCCUPATION, user.getMotherOccupation());
        contentValues.put(KEY_YEARLY_INCOME, user.getYearlyIncome());
        contentValues.put(KEY_CATEGORY, user.getCategory());
        contentValues.put(KEY_GENDER, user.getGender());
        contentValues.put(KEY_PERMANET_ADDRESS, user.getPermanentAddress());

        //database.update(TABLE_USER, contentValues, KEY_ID + " = ?", new int[]{applicationId});
        database.update(TABLE_USER, contentValues, "id = "+applicationId, null);
        database.close();

        getUserByApplicationId(applicationId);
    }
}
