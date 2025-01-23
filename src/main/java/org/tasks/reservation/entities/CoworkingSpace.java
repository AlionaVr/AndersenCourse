package org.tasks.reservation.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public CoworkingSpace(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public boolean isAvailable() {
        return availability;
    }

    @Override
    public String toString() {
        return String.format("id: %d\nName: %s\nType: %s\nPrice %.2f\nAvailability: %b", id, name, type, price, availability);
    }
}
