package vn.edu.greenwich.dbsqlite.Database;

import static vn.edu.greenwich.dbsqlite.Database.TripEntry.TABLE_NAME_TRIP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.util.ArrayList;

import vn.edu.greenwich.dbsqlite.Modal.ExpenseModal;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;

public class ExpenseEntry extends DBHelper {
    private Context context;

    public ExpenseEntry(Context context) {
        super(context);
        this.context = context;
    }


    public static final String TABLE_NAME_EXPENSE = "ListExpense";
    public static final String ID_COL = "ID_Expense";
    public static final String TYPE_COl = "Type";
    public static final String DATE_COl = "Date";
    public static final String TIME_COl = "Time";
    public static final String AMOUNT_COl = "Amount";
    public static final String Trip_Name = "TripName";
    public static final String ADDITIONAL_COl = "Additional";


    public static final String SQL_CREATE_TABLE_EXPENSE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EXPENSE + " (" +
                    ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TYPE_COl + " TEXT NOT NULL, " +
                    DATE_COl + " TEXT NOT NULL, " +
                    TIME_COl + " TEXT NOT NULL, " +
                    AMOUNT_COl + " INTEGER NOT NULL, " +
                    ADDITIONAL_COl + " TEXT," +
                    Trip_Name + " TEXT )";



    // DROP TABLE IF EXISTS contact
    public static final String SQL_DELETE_TABLE_EXPENSE =
            "DROP TABLE IF EXISTS " + TABLE_NAME_EXPENSE;

    public void addNewExpense(String Type, String Date, String Time, Integer Amount, String Additional, String TripName ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Trip_Name, TripName);
        values.put(TYPE_COl, Type);
        values.put(DATE_COl, Date);
        values.put(TIME_COl, Time);
        values.put(AMOUNT_COl, Amount);
        values.put(ADDITIONAL_COl, Additional);
        long result = db.insert(TABLE_NAME_EXPENSE, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "Added expense successfully", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<ExpenseModal> readExpense(String trip_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorExpense = db.rawQuery("SELECT * FROM  " + TABLE_NAME_EXPENSE + " WHERE " + Trip_Name + "='" + trip_name + "'", null);
        ArrayList<ExpenseModal> expenseModalArrayList = new ArrayList<>();
        if (cursorExpense.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                expenseModalArrayList.add(new ExpenseModal(cursorExpense.getString(1),
                        cursorExpense.getString(2),
                        cursorExpense.getString(3),
                        cursorExpense.getInt(4),
                        cursorExpense.getString(5)));
            } while (cursorExpense.moveToNext());
        }
        cursorExpense.close();
        return expenseModalArrayList;
    }

    public ArrayList<ExpenseModal> getAllExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorExpense = db.rawQuery("SELECT * FROM " + TABLE_NAME_EXPENSE, null);
        ArrayList<ExpenseModal> expenseModalArrayList = new ArrayList<>();
        if (cursorExpense.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                expenseModalArrayList.add(new ExpenseModal(cursorExpense.getString(1),
                        cursorExpense.getString(2),
                        cursorExpense.getString(3),
                        cursorExpense.getInt(4),
                        cursorExpense.getString(5)));
            } while (cursorExpense.moveToNext());
        }
        cursorExpense.close();
        return expenseModalArrayList;
    }

    public void updateExpense(String originalExpenseType, String expenseType, String expenseDate, String expenseTime, String expenseAmount, String expenseAdditional) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TYPE_COl, expenseType);
        values.put(DATE_COl, expenseDate);
        values.put(TIME_COl, expenseTime);
        values.put(AMOUNT_COl, expenseAmount);
        values.put(ADDITIONAL_COl, expenseAdditional);

        db.update(TABLE_NAME_EXPENSE, values, "name=?", new String[]{originalExpenseType});

    }

    public void deleteExpense(String expenseType) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_EXPENSE, "name=?", new String[]{expenseType});
        db.close();
    }

    public void DeleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_EXPENSE);
    }

}
