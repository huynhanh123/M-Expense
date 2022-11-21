package vn.edu.greenwich.dbsqlite.Modal;


public class ExpenseModal {

    private int id;
    private String ExpenseType;
    private int ExpenseAmount;
    private String DateAmount;
    private String TimeAmount;
    private String Additional;



    public String getExpenseType() {
        return ExpenseType;
    }

    public void setExpenseType(String expenseType) {
        this.ExpenseType = expenseType;
    }

    public int getExpenseAmount() {
        return ExpenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.ExpenseAmount = expenseAmount;
    }

    public String getDateAmount() {
        return DateAmount;
    }

    public void setDateAmount(String dateAmount) {
        this.DateAmount = dateAmount;
    }

    public String getTimeAmount() {
        return TimeAmount;
    }

    public void setTimeAmount(String timeAmount) {
        this.TimeAmount = timeAmount;
    }

    public String getAdditional() {
        return Additional;
    }

    public void setAdditional(String additional) {
        this.Additional = additional;
    }


    public ExpenseModal( String expenseType, String dateAmount, String timeAmount, int expenseAmount, String additional) {
        this.ExpenseType = expenseType;
        this.DateAmount = dateAmount;
        this.TimeAmount = timeAmount;
        this.ExpenseAmount = expenseAmount;
        this.Additional = additional;

    }

}
