package vn.edu.greenwich.dbsqlite.Modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class BackupModal implements Serializable{
        protected Date Date;
        protected String DeviceName;
        protected ArrayList<TripModal> tripModal;
        protected ArrayList<ExpenseModal> expenseModal;



    public BackupModal(Date date, String deviceName, ArrayList<TripModal> trips, ArrayList<ExpenseModal> expenses) {
            Date = date;
            DeviceName = deviceName;
            tripModal = trips;
            expenseModal = expenses;
        }

        public void setDate(Date date) {
            Date = date;
        }

        public Date getDate() {
            return Date;
        }

        public void setDeviceName(String deviceName) {
            DeviceName = deviceName;
        }

        public String getDeviceName() {
            return DeviceName;
        }

        public void setTrips(ArrayList<TripModal> trips) {
            tripModal = trips;
        }

        public ArrayList<TripModal> getTrips() {
            return tripModal;
        }

        public void setExpenses(ArrayList<ExpenseModal> expenses) {
            expenseModal = expenses;
        }

        public ArrayList<ExpenseModal> getExpenses() {
            return expenseModal;
        }
}

