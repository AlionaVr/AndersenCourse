package org.tasks.reservation;

import java.io.*;

public class Repository implements Serializable {
    private CustomList<CoworkingSpace> spaces = new CustomList<>();
    private CustomList<CoworkingSpaceBooking> myReservations = new CustomList<>();

    public Repository() {
    }

    public CustomList<CoworkingSpace> getSpaces() {
        return spaces; // never null!
    }

    public CustomList<CoworkingSpaceBooking> getMyReservations() {
        return myReservations;
    }

    //Serialization
    protected void saveObject() {
        try (FileOutputStream fos = new FileOutputStream("save.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);
            System.out.println("Object has been serialized");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Deserialization
    protected void readFile() {
        try (FileInputStream fis = new FileInputStream("save.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Repository loadedRepository = (Repository) ois.readObject();

            this.spaces = loadedRepository.getSpaces();
            this.myReservations = loadedRepository.getMyReservations();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
