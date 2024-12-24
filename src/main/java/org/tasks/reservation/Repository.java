package org.tasks.reservation;

import java.io.*;
import java.util.LinkedList;

public class Repository implements Serializable {
    private LinkedList<CoworkingSpace> spaces = new LinkedList<>();
    private LinkedList<CoworkingSpaceBooking> myReservations = new LinkedList<>();

    public Repository() {
    }

    public LinkedList<CoworkingSpace> getSpaces() {
        return spaces;
    }

    public LinkedList<CoworkingSpaceBooking> getMyReservations() {
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
