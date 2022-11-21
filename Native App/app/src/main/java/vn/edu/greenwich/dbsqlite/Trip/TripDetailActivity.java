package vn.edu.greenwich.dbsqlite.Trip;

import static vn.edu.greenwich.dbsqlite.Dialog.BottomSheetConfirmTrip.KEY_TRIP_OBJECT;
import android.app.DatePickerDialog;

import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;
import java.util.List;

import vn.edu.greenwich.dbsqlite.Dialog.BottomSheetConfirmTrip;
import vn.edu.greenwich.dbsqlite.Dialog.DatePicker;

import vn.edu.greenwich.dbsqlite.DrawerNav;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;
import vn.edu.greenwich.dbsqlite.R;
import vn.edu.greenwich.dbsqlite.databinding.ActivityMainBinding;
import vn.edu.greenwich.dbsqlite.databinding.ActivityTripDetailBinding;


public class TripDetailActivity extends DrawerNav implements DatePickerDialog.OnDateSetListener {

    ActivityTripDetailBinding activityTripDetailBinding;
    protected Button btnCreate;
    protected EditText tbName, tbType, tbDestination, tbDate, tbStatus, tbDescription, tbRisk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTripDetailBinding = ActivityTripDetailBinding.inflate(getLayoutInflater());
        setContentView(activityTripDetailBinding.getRoot());
        allocateActivityTitle("Trip Expense");

        tbName = findViewById(R.id.tbName);
        tbType = findViewById(R.id.tbType_trip);
        tbDestination = findViewById(R.id.tbDestination);
        tbStatus = findViewById(R.id.tbStatus);
        tbDescription = findViewById(R.id.tbDescription);
        tbRisk = findViewById(R.id.tbRisk);

        tbDate = findViewById(R.id.tbDate);
        tbDate.setOnClickListener(v -> {
            DatePicker mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "tbDate");
        });

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            String Name = tbName.getText().toString();
            String Type = tbType.getText().toString();
            String Destination = tbDestination.getText().toString();
            String Date = tbDate.getText().toString();
            String Status = tbStatus.getText().toString();
            String Description = tbDescription.getText().toString();
            String Risk = tbRisk.getText().toString();

            //Validate user's input.
            if (TextUtils.isEmpty(Name)) {
                tbName.setError("Required name of trip");
                tbName.requestFocus();
                return;
            } else if (TextUtils.isEmpty(Type)) {
                tbType.setError("Required type of trip");
                tbType.requestFocus();
                return;
            } else if (TextUtils.isEmpty(Destination)) {
                tbDestination.setError("Required destination");
                tbDestination.requestFocus();
                return;
            } else if (TextUtils.isEmpty(Status)) {
                tbStatus.setError("Required status");
                tbStatus.requestFocus();
                return;
            } else if (TextUtils.isEmpty(Risk)) {
                tbRisk.setError("Required risk");
                tbRisk.requestFocus();
                return;
            } else {
                Bundle bundle = new Bundle();
                TripModal tripModal = new TripModal(Name, Type, Destination, Status, Date, Description, Risk);
                bundle.putSerializable(KEY_TRIP_OBJECT, tripModal);
                BottomSheetConfirmTrip bottomSheetConfirmTripDialog = BottomSheetConfirmTrip.newInstance(tripModal);
                bottomSheetConfirmTripDialog.setArguments(bundle);
                bottomSheetConfirmTripDialog.show(getSupportFragmentManager(), bottomSheetConfirmTripDialog.getTag());
                bottomSheetConfirmTripDialog.setCancelable(false);

            }


        });
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tbDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }




}

