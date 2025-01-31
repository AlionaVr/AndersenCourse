package org.tasks.reservation.dto;

import lombok.Data;

@Data
public class ReserveResponseDto {
    private final boolean reserved;
    private final String error;

    public ReserveResponseDto() { // if coworking space was reserved successfully
        reserved = true;
        error = null;
    }

    public ReserveResponseDto(String error) { // if error happened during reservation
        reserved = false;
        this.error = error;
    }
}
