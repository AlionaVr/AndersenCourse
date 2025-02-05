package org.tasks.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tasks.reservation.helper.TypeOfSpace;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coworking_space")
public class CoworkingSpace {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SERIAL
    private Integer id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeOfSpace type;

    @Column
    private double price;

    @Column
    private boolean availability = true;

    public CoworkingSpace(String name, String type, double price) {
        this.name = name;
        this.type = TypeOfSpace.valueOf(type);
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
