package br.com.miller.muckup.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String SQL_CREATE_ENTRY =
            "CREATE TABLE IF NOT EXISTS "+ Constants.CartEntries.TABLE_NAME + " ( " +
                    Constants.CartEntries._ID + "INTEGER PRIMARY KEY, " +
                    Constants.CartEntries.FIREBASE_ID_COLUMN + "TEXT, " +
                    Constants.CartEntries.CITY_COLUMN + "TEXT, " +
                    Constants.CartEntries.TYPE_COLUMN + "TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.CartEntries.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Cart.db";

    public DataBaseHelper( @Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
