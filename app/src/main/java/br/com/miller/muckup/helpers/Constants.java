package br.com.miller.muckup.helpers;

import android.provider.BaseColumns;

public final class Constants {

    private Constants() {}

    public static final String PREF_NAME = "login_preferences";
    public static final String USER_NAME = "user_name";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_ADDRESS = "user_address";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_CITY = "user_city";
    public static final String USER_ID_FIREBASE = "user_id_firebase";
    public static final int START_CODE = 1222;
    public static final int INTERNAL_IMAGE = 1223;

    public static class CartEntries implements BaseColumns {

        static final String TABLE_NAME = "cart";
        static final String CITY_COLUMN = "city";
        public static final String TYPE_COLUMN = "type";
        static final String FIREBASE_ID_COLUMN = "firebase_id";


    }

}
