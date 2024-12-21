package org.tasks.reservation;

public class CoworkingSpace {
    private final String name;
    private final String type;
    private final double price;
    private boolean availability = true;

    public CoworkingSpace(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nType: %s\nPrice %.2f\nAvailability: %b", name, type, price, availability);
    }
}
