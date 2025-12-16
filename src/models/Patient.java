package models;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String disease;
    private String assignedDoctor;
    private int roomNumber;
    private double bill;
    private boolean admitted;
    private boolean discharged;

    public Patient(int id, String name, int age, String disease) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.assignedDoctor = "Not assigned";
        this.roomNumber = 0;
        this.bill = 0.0;
        this.admitted = false;
        this.discharged = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDisease() { return disease; }
    public String getAssignedDoctor() { return assignedDoctor; }
    public void setAssignedDoctor(String assignedDoctor) { this.assignedDoctor = assignedDoctor; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public double getBill() { return bill; }
    public void setBill(double bill) { this.bill = bill; }
    public boolean isAdmitted() { return admitted; }
    public void setAdmitted(boolean admitted) { this.admitted = admitted; }
    public boolean isDischarged() { return discharged; }
    public void setDischarged(boolean discharged) { this.discharged = discharged; }
}