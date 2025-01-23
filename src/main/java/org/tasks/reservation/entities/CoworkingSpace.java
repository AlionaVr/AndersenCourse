package org.tasks.reservation.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "coworking_space")
public class CoworkingSpace {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SERIAL
    private int id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private double price;

    @Column
    private boolean availability = true;

    protected CoworkingSpace() {
    }

    public CoworkingSpace(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return availability;
    }

    @Override
    public String toString() {
        return String.format("id: %d\nName: %s\nType: %s\nPrice %.2f\nAvailability: %b", id, name, type, price, availability);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
