package vn.edu.greenwich.dbsqlite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Selection;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.greenwich.dbsqlite.Modal.TripModal;

public class TripEntry extends DBHelper {
    private Context context;
    public TripEntry(Context context) {
        super(context);
        this.context = context;
    }

//    public TripEntry(BottomSheetConfirmTrip bottomSheetConfirmTrip) {
//        super(bottomSheetConfirmTrip.getContext());
//    }


    public static final String TABLE_NAME_TRIP = "ListTrip";
    public static final String ID_COL = "ID";
    public static final String NAME_COL = "Name";
    public static final String TYPE_COl = "Type";
    public static final String DESTINATION_COl = "Destination";
    public static final String STATUS_COl = "Status";
    public static final String DATE_COl = "Date";
    public static final String DESCRIPTION_COl = "Description";
    public static final String RISK_COl = "Risk";

    public static final String SQL_CREATE_TABLE_TRIP =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TRIP + " (" +
                    ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COL + " TEXT NOT NULL, " +
                    TYPE_COl + " TEXT NOT NULL, " +
                    DESTINATION_COl + " TEXT NOT NULL," +
                    STATUS_COl + " TEXT NOT NULL, " +
                    DATE_COl + " TEXT NOT NULL, " +
                    DESCRIPTION_COl + " TEXT, " +
                    RISK_COl + " TEXT NOT NULL)";

    // DROP TABLE IF EXISTS contact
    public static final String SQL_DELETE_TABLE_TRIP =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TRIP;



    public void addNewTrip(String Name, String Type, String Destination, String Status, String Date, String Description, String Risk) {
        SQLiteDatabase db;
                db = this.getReadableDatabase();
        Cursor cursorTrip = db.rawQuery( "SELECT * FROM " + TABLE_NAME_TRIP + " WHERE " + " Name = ?", new String[]{Name});
        if (cursorTrip.getCount() > 0) {
            Toast.makeText(context, "Name already exist", Toast.LENGTH_SHORT).show();
        }
        else {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_COL, Name);
            values.put(TYPE_COl, Type);
            values.put(DESTINATION_COl, Destination);
            values.put(STATUS_COl, Status);
            values.put(DATE_COl, Date);
            values.put(DESCRIPTION_COl, Description);
            values.put(RISK_COl, Risk);
            long result = db.insert(TABLE_NAME_TRIP, null, values);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, "Added trip successfully", Toast.LENGTH_SHORT).show();
       }

    }
    public ArrayList<TripModal> readTrip() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursorTrip = db.rawQuery("SELECT * FROM " + TABLE_NAME_TRIP, null);
            ArrayList<TripModal> tripModalArrayList = new ArrayList<>();
            if (cursorTrip.moveToFirst()) {
                do {
                    // on below line we are adding the data from cursor to our array list.
                    tripModalArrayList.add(new TripModal(
                            cursorTrip.getString(1),
                            cursorTrip.getString(2),
                            cursorTrip.getString(3),
                            cursorTrip.getString(4),
                            cursorTrip.getString(5),
                            cursorTrip.getString(6),
                            cursorTrip.getString(7)));

                } while (cursorTrip.moveToNext());
            }
            cursorTrip.close();
            return tripModalArrayList;
        }

        public void updateTrip(String originalTripName, String tripName, String tripType, String tripDestination, String tripStatus, String tripDate, String tripDescription, String tripRisk) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(NAME_COL, tripName);
            values.put(TYPE_COl, tripType);
            values.put(DESTINATION_COl, tripDestination);
            values.put(STATUS_COl, tripStatus);
            values.put(DATE_COl, tripDate);
            values.put(DESCRIPTION_COl, tripDescription);
            values.put(RISK_COl, tripRisk);

            db.update(TABLE_NAME_TRIP, values, "name=?",new String[]{originalTripName});

        }

        public void deleteTrip(String tripName){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_TRIP, "name=?", new String[]{tripName});
            db.close();
        }

        public void deleteAll(){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_NAME_TRIP);
        }





}
