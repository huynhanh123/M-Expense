package vn.edu.greenwich.dbsqlite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import vn.edu.greenwich.dbsqlite.Modal.TripModal;
import vn.edu.greenwich.dbsqlite.R;
import vn.edu.greenwich.dbsqlite.Trip.ShowTrip;
import vn.edu.greenwich.dbsqlite.Trip.UpdateTripActivity;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private ArrayList<TripModal> tripModalArrayList;
    private ArrayList<TripModal> tripModalArrayListFilter;
    private Context context;

    public CustomAdapter(Context context,  ArrayList<TripModal> tripModalArrayList) {
        this.tripModalArrayList = tripModalArrayList;
        this.tripModalArrayListFilter = new ArrayList<>(tripModalArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TripModal tripModal = tripModalArrayList.get(position);
        holder.tvName.setText(tripModal.getName());
        holder.tvDestination.setText(tripModal.getDestination());
        holder.tvDate.setText(tripModal.getDate());

        holder.ItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(tripModal);
            }
        });

    }

    private void onClickGoToDetail(TripModal tripModal ) {
        Intent intent = new Intent(context, ShowTrip.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object_Trip", tripModal);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return tripModalArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvDestination, tvDate;
        LinearLayout ItemLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.Name);
            tvDestination = itemView.findViewById(R.id.Destination);
            tvDate = itemView.findViewById(R.id.Date);
            ItemLayout = itemView.findViewById(R.id.layout_item);


        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TripModal> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(tripModalArrayListFilter);
            }
            else {
                for (TripModal tripModal: tripModalArrayListFilter) {
                    if (tripModal.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(tripModal);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tripModalArrayList.clear();
            tripModalArrayList.addAll((Collection<? extends TripModal>) results.values);
            notifyDataSetChanged();
        }
    };

}
