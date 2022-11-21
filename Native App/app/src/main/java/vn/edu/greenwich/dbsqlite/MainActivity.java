package vn.edu.greenwich.dbsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

import vn.edu.greenwich.dbsqlite.Database.TripEntry;
import vn.edu.greenwich.dbsqlite.Adapter.CustomAdapter;
import vn.edu.greenwich.dbsqlite.Trip.TripDetailActivity;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;
import vn.edu.greenwich.dbsqlite.databinding.ActivityMainBinding;


public class MainActivity extends DrawerNav {

    ActivityMainBinding activityMainBinding;

    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    FloatingActionButton btnAdd;
    TripEntry tripEntry;
    private SearchView btnSearch;
    private ArrayList<TripModal> tripModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        allocateActivityTitle("Trip Expense");

        btnSearch = findViewById(R.id.svSearch);
        btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TripDetailActivity.class);
            startActivity(intent);
        });

        tripModalArrayList = new ArrayList<>();
        tripEntry = new TripEntry(MainActivity.this);

        tripModalArrayList = tripEntry.readTrip();

        customAdapter = new CustomAdapter(MainActivity.this, tripModalArrayList);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(customAdapter);
    }




}