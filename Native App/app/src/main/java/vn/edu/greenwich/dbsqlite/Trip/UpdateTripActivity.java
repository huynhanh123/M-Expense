package vn.edu.greenwich.dbsqlite.Trip;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.greenwich.dbsqlite.Database.TripEntry;
//import vn.edu.greenwich.dbsqlite.Expense.DemoExpense;

import vn.edu.greenwich.dbsqlite.DrawerNav;
import vn.edu.greenwich.dbsqlite.Expense.DemoExpense;
import vn.edu.greenwich.dbsqlite.MainActivity;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;
import vn.edu.greenwich.dbsqlite.R;
import vn.edu.greenwich.dbsqlite.databinding.ActivityMainBinding;
import vn.edu.greenwich.dbsqlite.databinding.ActivityUpdateBinding;

public class UpdateTripActivity extends DrawerNav {

    ActivityUpdateBinding activityUpdateBinding;

    private EditText Name, Type, Destination, Status, Date, Description, Risk;
    private TripEntry tripEntry;
    private Button btnUpdateTrip, btnDeleteTrip;

    String tripName, tripType, tripDestination, tripStatus, tripDescription, tripDate, tripRisk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateBinding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(activityUpdateBinding.getRoot());


        Name = findViewById(R.id.update_Name);
        Type = findViewById(R.id.update_Type_trip);
        Destination = findViewById(R.id.update_Destination);
        Status = findViewById(R.id.update_Status);
        Date = findViewById(R.id.update_Date);
        Description = findViewById(R.id.update_Description);
        Risk = findViewById(R.id.update_Risk);

        tripEntry = new TripEntry(UpdateTripActivity.this);


        tripName = getIntent().getStringExtra("Name");
        tripType = getIntent().getStringExtra("Type");
        tripDestination = getIntent().getStringExtra("Destination");
        tripStatus = getIntent().getStringExtra("Status");
        tripDate = getIntent().getStringExtra("Date");
        tripDescription = getIntent().getStringExtra("Description");
        tripRisk = getIntent().getStringExtra("Risk");

        Name.setText(tripName);
        Type.setText(tripType);
        Destination.setText(tripDestination);
        Status.setText(tripStatus);
        Date.setText(tripDate);
        Description.setText(tripDescription);
        Risk.setText(tripRisk);

        allocateActivityTitle(tripName);

        btnUpdateTrip = findViewById(R.id.btnUpdateTrip);
        btnUpdateTrip.setOnClickListener(v -> {
            tripEntry.updateTrip(tripName, Name.getText().toString(),
                    Type.getText().toString(),
                    Destination.getText().toString(),
                    Status.getText().toString(),
                    Date.getText().toString(),
                    Description.getText().toString(),
                    Risk.getText().toString());
            Toast.makeText(UpdateTripActivity.this, "Trip is updating ...", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateTripActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        Button btnDelete = findViewById(R.id.btnDeleteTrip);
        btnDelete.setOnClickListener(v -> {
            confirmDialog();
        });



    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete " + tripName + "?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            tripEntry.deleteTrip(tripName);
            Toast.makeText(UpdateTripActivity.this, "Deleted the trip", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateTripActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();

    }

}