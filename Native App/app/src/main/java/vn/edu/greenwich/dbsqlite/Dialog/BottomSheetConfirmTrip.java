package vn.edu.greenwich.dbsqlite.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.greenwich.dbsqlite.Database.TripEntry;
import vn.edu.greenwich.dbsqlite.MainActivity;
import vn.edu.greenwich.dbsqlite.R;
import vn.edu.greenwich.dbsqlite.Modal.TripModal;

public class BottomSheetConfirmTrip extends BottomSheetDialogFragment {
    public static final String KEY_TRIP_OBJECT = "trip_object";
    private TripModal mtripModal;

    private TextView tvName, tvType, tvDestination, tvStatus, tvDate, tvDescription, tvRisk;
    private Button btnEdit, btnConfirm;

    public BottomSheetConfirmTrip() {

    }




    public static BottomSheetConfirmTrip newInstance(TripModal tripModal ) {
        BottomSheetConfirmTrip bottomSheetConfirmTrip = new BottomSheetConfirmTrip();
        Bundle bundle = new Bundle();
        bundle.putSerializable("trip_object", tripModal);
        bottomSheetConfirmTrip.setArguments(bundle);
        return bottomSheetConfirmTrip;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            mtripModal = (TripModal) bundleReceive.get(KEY_TRIP_OBJECT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View viewDialog  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sheet_confirm, null);
        bottomSheetDialog.setContentView(viewDialog);

        tvName = viewDialog.findViewById(R.id.tvName1);
        tvType = viewDialog.findViewById(R.id.tvType1);
        tvDestination = viewDialog.findViewById(R.id.tvDestination1);
        tvStatus = viewDialog.findViewById(R.id.tvStatus1);
        tvDate = viewDialog.findViewById(R.id.tvDate1);
        tvDescription = viewDialog.findViewById(R.id.tvDescription1);
        tvRisk = viewDialog.findViewById(R.id.tvRisk1);

        tvName.setText(mtripModal.getName());
        tvType.setText(mtripModal.getType());
        tvDestination.setText(mtripModal.getDestination());
        tvStatus.setText(mtripModal.getStatus());
        tvDate.setText(mtripModal.getDate());
        tvDescription.setText(mtripModal.getDescription());
        tvRisk.setText(mtripModal.getRisk());

        btnEdit = viewDialog.findViewById(R.id.Edit);
        btnEdit.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnConfirm = viewDialog.findViewById(R.id.Confirm);
        btnConfirm.setOnClickListener(v -> {
            TripEntry tripEntry = new TripEntry(this.getContext());
            tripEntry.addNewTrip(tvName.getText().toString().trim(), tvType.getText().toString().trim(),
                    tvDestination.getText().toString().trim(), tvStatus.getText().toString().trim(), tvDate.getText().toString().trim(),
                    tvDescription.getText().toString().trim(), tvRisk.getText().toString().trim());
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);

        });
        return bottomSheetDialog;

    }



}
