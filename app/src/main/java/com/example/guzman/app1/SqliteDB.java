package com.example.guzman.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static org.xmlpull.v1.XmlPullParser.TEXT;

public class SqliteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hotels.db";
    private static final String TABLE_USER = "users";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_STREET = "user_street";
    private static final String COLUMN_USER_HOUSE_NO = "user_house";
    private static final String COLUMN_USER_PROFILE = "user_profile";

    private static final String TABLE_IP = "ipaddress";
    private static final String COLUMN_IP_ID = "id";
    private static final String COLUMN_IP_NUMBER = "ip_number";


    public SqliteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SqliteDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER +
                "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_name TEXT ,  " +
                "user_phone TEXT , " +
                "user_email TEXT ,  " +
                "user_password TEXT , " +
                "user_address TEXT , " +
                "user_street TEXT , " +
                "user_house TEXT , " +
                "user_profile TEXT )");
        db.execSQL("CREATE TABLE " + TABLE_IP +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ip_number TEXT)");

        String ip = "192.168.42.23";

        ContentValues values = new ContentValues();

        values.put(COLUMN_IP_NUMBER, ip);

        db.insert(TABLE_IP, null, values);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IP);

        onCreate(db);

    }

    public boolean addUser(String user_name, String user_phone, String user_email, String user_password, String user_address, String user_street, String user_house,String user_profile ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user_name);

        values.put(COLUMN_USER_PHONE, user_phone);

        values.put(COLUMN_USER_EMAIL, user_email);

        values.put(COLUMN_USER_PASSWORD, user_password);

        values.put(COLUMN_USER_ADDRESS, user_address);

        values.put(COLUMN_USER_STREET, user_street);

        values.put(COLUMN_USER_HOUSE_NO, user_house);

        values.put(COLUMN_USER_PROFILE, user_profile);

        long bool = db.insert(TABLE_USER, null, values);

        if (bool == -1) {

            return false;

        }

        return true;
    }

    public String CheckUser(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String em = cursor.getString(3);

                String pa = cursor.getString(4);

                if (email.equalsIgnoreCase(em)) {

                    if (password.equals(pa)) {

                        return "Success";

                    }

                    return "Incorrect Credentials";
                }
            }
            return "User does not Exist";
        }
        return "No data Found";

    }

    public Cursor check(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM users WHERE user_id=" + id + "", null);

        return res;

    }
    public Cursor checkIp(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM ipaddress WHERE id=" + id + "", null);

        return res;

    }

    public boolean updateIp(String name, String id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_IP_NUMBER, name);

        long bool = database.update(TABLE_IP, contentValues, "" + COLUMN_IP_ID + " =?", new String[]{id});

        if (bool == -1) {

            return false;

        }

        return true;
    }

    public String CheckPin(String pin, String email) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String em = cursor.getString(4);

                String pa = cursor.getString(6);

                if (email.equalsIgnoreCase(em)) {

                    if (pin.equals(pa)) {

                        return "Success";
                        }

                    return "Incorrect Pin";
                }
            }

            return "User does not Exist";
        }

        return "No data Found";
    }

    public Cursor searchEmail() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        return cursor;
    }

    public boolean changePass(String email, String pass) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER_PASSWORD, pass);

        long bool = database.update(TABLE_USER, contentValues, "" + COLUMN_USER_EMAIL + " =?", new String[]{email});

        if (bool == -1) {

            return false;

        }

        return true;
    }

    public boolean updateProfile(byte[] byteArray, String id) {
        return false;
    }

    public boolean logout() {

        SQLiteDatabase database = this.getWritableDatabase();

        long bool = database.delete(TABLE_USER,null,null);

        if (bool == -1) {

            return false;

        }
        return true;
    }

    public boolean updateProfilele(byte[] byteArray, String id) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_PROFILE, byteArray);
        long bool = database.update(TABLE_USER, contentValues, "" + COLUMN_USER_ID + " =?", new String[]{id});
        if (bool == -1) {
            return false;

        }
        return true;
    }

//    public boolean updateCar(String a, String b, String id) {
//
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_USER_CAR, a);
//        long bool = database.update(TABLE_USER, contentValues, "" + COLUMN_USER_ID + " =?", new String[]{id});
//        if (bool == -1) {
//            return false;
//
//        }
//        return true;
    }

