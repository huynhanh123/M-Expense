package vn.edu.greenwich.dbsqlite.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.greenwich.dbsqlite.Modal.ExpenseModal;
import vn.edu.greenwich.dbsqlite.R;



public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private ArrayList<ExpenseModal> expenseModalArrayList;
    private Context context;

    public ExpenseAdapter(Context context, ArrayList<ExpenseModal> expenseModalArrayList) {
        this.expenseModalArrayList = expenseModalArrayList;
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseModal expenseModal = expenseModalArrayList.get(position);

        holder.tvExpenseType.setText(expenseModal.getExpenseType());
        holder.tvExpenseAmount.setText(String.valueOf(expenseModal.getExpenseAmount()));
        holder.tvExpenseTime.setText(expenseModal.getTimeAmount());
        holder.tvExpenseDate.setText(expenseModal.getDateAmount());
        holder.tvAdditional.setText(expenseModal.getAdditional());
    }

    @Override
    public int getItemCount() {
        return expenseModalArrayList.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
        private TextView tvExpenseType, tvExpenseAmount, tvExpenseTime, tvAdditional, tvExpenseDate;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpenseType = itemView.findViewById(R.id.tvType_Expense);
            tvExpenseAmount = itemView.findViewById(R.id.tvAmount_Expense);
            tvExpenseTime = itemView.findViewById(R.id.tvTime_Expense);
            tvAdditional = itemView.findViewById(R.id.tvAdditional_Comments);
            tvExpenseDate = itemView.findViewById(R.id.tvDate_Expense);
        }
    }
}
