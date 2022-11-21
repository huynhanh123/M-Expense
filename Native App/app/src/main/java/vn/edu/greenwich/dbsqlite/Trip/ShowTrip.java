package vn.edu.greenwich.dbsqlite.Trip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Attributes;

import vn.edu.greenwich.dbsqlite.Adapter.ExpenseAdapter;
import vn.edu.greenwich.dbsqlite.Database.ExpenseEntry;
import vn.edu.greenwich.dbsqlite.Database.TripEntry;
import vn.edu.greenwich.dbsqlite.Dialog.BottomSheetConfirmTrip;
import vn.edu.greenwich.dbsqlite.Expense.DemoExpense;
import vn.edu.greenwich.dbsqlite.MainActivity;
import vn.edu.greenwich.dbsqlite.Modal.ExpenseModal;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;
import vn.edu.greenwich.dbsqlite.R;
import vn.edu.greenwich.dbsqlite.Trip.UpdateTripActivity;

public class ShowTrip extends AppCompatActivity {

    TripEntry tripEntry;
    ExpenseEntry expenseEntry;
    private Button btnUpdate;
    private Button btnAddExpense;
    private TextView rTrip;
    private TextView rType;
    private TextView rDestination;
    private TextView rStatus;
    private TextView rDate;
    private TextView rDescription;
    private TextView rRisk;
    private RecyclerView recyclerView;
    private ArrayList<ExpenseModal> expenseModalArrayList;
    private ExpenseAdapter expenseAdapter;
    private TripModal tripModal;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip);
        rTrip = findViewById(R.id.readName);
        rType = findViewById(R.id.readType);
        rDestination = findViewById(R.id.readDestination);
        rStatus = findViewById(R.id.readStatus);
        rDate = findViewById(R.id.readDate);
        rDescription = findViewById(R.id.readDescription);
        rRisk = findViewById(R.id.readRisk);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        TripModal tripModal = (TripModal) bundle.get("Object_Trip");

        rTrip.setText(tripModal.getName());
        rType.setText(tripModal.getType());
        rDestination.setText(tripModal.getDestination());
        rStatus.setText(tripModal.getStatus());
        rDate.setText(tripModal.getDate());
        rDescription.setText(tripModal.getDescription());
        rRisk.setText(tripModal.getRisk());


        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> {
            Intent i = new Intent(ShowTrip.this, UpdateTripActivity.class);
            i.putExtra("ID", tripModal.getId());
            i.putExtra("Name", tripModal.getName());
            i.putExtra("Type", tripModal.getType());
            i.putExtra("Destination", tripModal.getDestination());
            i.putExtra("Status", tripModal.getStatus());
            i.putExtra("Date", tripModal.getDate());
            i.putExtra("Description", tripModal.getDescription());
            i.putExtra("Risk", tripModal.getRisk());
            startActivity(i);
        });



        btnAddExpense = findViewById(R.id.btnCreateExpense);
        btnAddExpense.setOnClickListener(v -> {
            Intent i = new Intent(ShowTrip.this, DemoExpense.class);
            String Name = tripModal.getName().toString();
            i.putExtra("Name", Name);
            startActivity(i);
            finish();
        });


        expenseModalArrayList = new ArrayList<>();
        expenseEntry = new ExpenseEntry(ShowTrip.this);
        expenseModalArrayList = expenseEntry.readExpense(tripModal.getName());
        expenseAdapter = new ExpenseAdapter(ShowTrip.this, expenseModalArrayList);
        recyclerView = findViewById(R.id.rcvExpense);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowTrip.this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapter(expenseAdapter);
    }







}