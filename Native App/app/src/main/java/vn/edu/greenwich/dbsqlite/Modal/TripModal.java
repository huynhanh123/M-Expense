package vn.edu.greenwich.dbsqlite.Modal;

import java.io.Serializable;

public class TripModal implements Serializable {

    private String Name;
    private String Type;
    private String Destination;
    private String Status;
    private String Date;
    private String Description;
    private String Risk;
    private int id;

    // creating getter and setter methods
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getRisk() {
        return Risk;
    }

    public void setRisk(String Risk) {
        this.Risk = Risk;
    }


    // constructor
    public TripModal( String Name, String Type, String Destination, String Status, String Date, String Description, String Risk ) {
        this.Name = Name;
        this.Type = Type;
        this.Destination = Destination;
        this.Status = Status;
        this.Date = Date;
        this.Description = Description;
        this.Risk = Risk;
    }

}
