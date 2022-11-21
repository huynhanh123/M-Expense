package vn.edu.greenwich.dbsqlite.Expense;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.greenwich.dbsqlite.Adapter.ExpenseAdapter;

import vn.edu.greenwich.dbsqlite.Database.ExpenseEntry;

import vn.edu.greenwich.dbsqlite.Dialog.DatePicker;
import vn.edu.greenwich.dbsqlite.Dialog.TimePicker;
import vn.edu.greenwich.dbsqlite.DrawerNav;
import vn.edu.greenwich.dbsqlite.MainActivity;
import vn.edu.greenwich.dbsqlite.Modal.ExpenseModal;
import vn.edu.greenwich.dbsqlite.R;
import vn.edu.greenwich.dbsqlite.Trip.ShowTrip;
import vn.edu.greenwich.dbsqlite.Trip.UpdateTripActivity;
import vn.edu.greenwich.dbsqlite.databinding.ActivityDemoExpenseBinding;


public class DemoExpense extends DrawerNav implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ActivityDemoExpenseBinding activityDemoExpenseBinding;
    private EditText tbTypeExpense, tbAmountExpense, tbTimeExpense, tbAdditional, tbDateExpense;
    private Button btnCreateExpense;
    RecyclerView recyclerView;
    private ArrayList<ExpenseModal> expenseModalArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDemoExpenseBinding = ActivityDemoExpenseBinding.inflate(getLayoutInflater());
        setContentView(activityDemoExpenseBinding.getRoot());


        tbTypeExpense = findViewById(R.id.tbTypeExpense);
        tbAmountExpense = findViewById(R.id.tbAmountExpense);
        tbAdditional = findViewById(R.id.tbAdditional);

        recyclerView = findViewById(R.id.rcvExpense);

        tbDateExpense = findViewById(R.id.tbDateExpense);
        tbDateExpense.setOnClickListener(v -> {
            DatePicker mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "tbDateExpense");
        });

        tbTimeExpense = findViewById(R.id.tbTimeExpense);
        tbTimeExpense.setOnClickListener(v -> {
            TimePicker timePickerDialogFragment = new TimePicker();
            timePickerDialogFragment.show(getSupportFragmentManager(), "tbTimeExpense");
        });

        btnCreateExpense = findViewById(R.id.btnCreateExpense);
        btnCreateExpense.setOnClickListener(v -> {
            String ExpenseType = tbTypeExpense.getText().toString();
            String ExpenseDate = tbDateExpense.getText().toString();
            String ExpenseTime = tbTimeExpense.getText().toString();
            Integer ExpenseAmount = Integer.valueOf(tbAmountExpense.getText().toString());
            String Additional = tbAdditional.getText().toString();
            String name = getIntent().getStringExtra("Name");
            Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show();

            ExpenseEntry expenseEntry = new ExpenseEntry(DemoExpense.this);
            expenseEntry.addNewExpense(ExpenseType, ExpenseDate, ExpenseTime, ExpenseAmount, Additional, name.toString());
            Intent intent = new Intent(v.getContext(), ShowTrip.class);
            startActivity(intent);
        });


    }



    public void onDateSet(android.widget.DatePicker v, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tbDateExpense.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        tbTimeExpense.setText(hourOfDay + ":" + minute);

    }
}

