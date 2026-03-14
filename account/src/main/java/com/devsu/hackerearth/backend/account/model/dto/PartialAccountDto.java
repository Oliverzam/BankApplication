package com.devsu.hackerearth.backend.account.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PartialAccountDto {
    @JsonProperty("isActive")
    private boolean active;

    public PartialAccountDto(boolean active) {
        this.active = active;
    }
}
