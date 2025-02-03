package org.tasks.reservation.dto;

import lombok.Data;

@Data
public class StatusResponseDto {
    private final boolean success;
    private final String error;

    public StatusResponseDto() {
        this.success = true;
        this.error = null;
    }

    public StatusResponseDto(String errorMsg) {
        this.success = false;
        this.error = errorMsg;
    }
}
