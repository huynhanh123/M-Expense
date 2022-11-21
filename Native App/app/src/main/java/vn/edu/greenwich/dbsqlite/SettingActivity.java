package vn.edu.greenwich.dbsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

import vn.edu.greenwich.dbsqlite.Database.BackupEntry;
import vn.edu.greenwich.dbsqlite.Database.DBHelper;
import vn.edu.greenwich.dbsqlite.Database.ExpenseEntry;
import vn.edu.greenwich.dbsqlite.Database.TripEntry;
import vn.edu.greenwich.dbsqlite.Modal.BackupModal;
import vn.edu.greenwich.dbsqlite.Modal.ExpenseModal;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;
import vn.edu.greenwich.dbsqlite.databinding.ActivitySetting2Binding;

public class SettingActivity extends DrawerNav {

    protected TripEntry tripEntry;
    protected ExpenseEntry expenseEntry;
    protected DBHelper dbHelper;
    protected ArrayList<TripModal> trips;
    protected ArrayList<ExpenseModal> expenses;


    ActivitySetting2Binding activitySetting2Binding;
    protected Button settingBackup, settingResetDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySetting2Binding = ActivitySetting2Binding.inflate(getLayoutInflater());
        allocateActivityTitle("Setting");
        setContentView(activitySetting2Binding.getRoot());

        tripEntry = new TripEntry(this);
        expenseEntry = new ExpenseEntry(this);

        settingBackup = findViewById(R.id.btnBackup);
        settingResetDatabase = findViewById(R.id.btnReset);

        settingBackup.setOnClickListener(v -> backup());
        settingResetDatabase.setOnClickListener(v -> resetDatabase());
    }

    protected void resetDatabase() {
        dbHelper.reset();
        Toast.makeText(this, R.string.label_reset_database, Toast.LENGTH_SHORT).show();
    }

    protected void backup() {
        trips = tripEntry.readTrip();
        expenses = expenseEntry.getAllExpense();

        if (null != trips && 0 < trips.size() && null != expenses && 0 < expenses.size()) {
            String deviceName = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            BackupModal backup = new BackupModal(new Date(), deviceName, trips, expenses);

            FirebaseFirestore.getInstance().collection(BackupEntry.TABLE_BACKUP_NAME)
                    .add(backup)
                    .addOnSuccessListener(document -> {
                        Toast.makeText(this, R.string.notification_backup_success, Toast.LENGTH_SHORT).show();
                        Log.d(getResources().getString(R.string.label_backup_firestore), document.getId());
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, R.string.notification_backup_fail, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        } else {
            Toast.makeText(this, R.string.error_empty_list, Toast.LENGTH_SHORT).show();
        }
    }
}