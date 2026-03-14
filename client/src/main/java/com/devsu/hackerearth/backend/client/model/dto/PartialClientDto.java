package com.devsu.hackerearth.backend.client.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PartialClientDto {
    @JsonProperty("isActive")
    private boolean active;

    public PartialClientDto(boolean active) {
        this.active = active;
    }
}
