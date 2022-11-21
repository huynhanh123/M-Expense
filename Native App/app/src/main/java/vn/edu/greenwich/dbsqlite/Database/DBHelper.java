package vn.edu.greenwich.dbsqlite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {


    //create a constant variables
    public static final String DATABASE_NAME = "TripDB";
    //int is database version
    public static final int DATABASE_VERSION = 10;



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TripEntry.SQL_CREATE_TABLE_TRIP);
        sqLiteDatabase.execSQL(ExpenseEntry.SQL_CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TripEntry.SQL_DELETE_TABLE_TRIP);
        sqLiteDatabase.execSQL(ExpenseEntry.SQL_DELETE_TABLE_EXPENSE);
        onCreate(sqLiteDatabase);
    }

    public void reset() {onUpgrade(getWritableDatabase(), 0, 0);}
}






