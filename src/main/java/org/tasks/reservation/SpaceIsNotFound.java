package org.tasks.reservation;

public class SpaceIsNotFound extends RuntimeException {
    public SpaceIsNotFound(String massage) {
        super(massage);
    }

}
