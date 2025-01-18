package org.tasks.reservation;

import java.io.Serializable;

public class CoworkingSpace implements Serializable {
    private int id;

    protected CoworkingSpace(int id, String name, String type, double price, boolean availability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    private final String name;

    public String getType() {
        return type;
    }
    private final String type;
    private final double price;
    private boolean availability = true;

    protected CoworkingSpace(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    protected boolean isAvailable() {
        return availability;
    }

    @Override
    public String toString() {
        return String.format("id: %d\nName: %s\nType: %s\nPrice %.2f\nAvailability: %b", id, name, type, price, availability);
    }
}
