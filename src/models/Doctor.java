package models;

public class Doctor {
    private String name;
    private String specialization;
    private boolean available;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
        this.available = true;
    }

    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}