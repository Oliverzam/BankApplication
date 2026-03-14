package com.devsu.hackerearth.backend.account.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String number;
    private String type;
    private Double initialAmount;
    @JsonProperty("isActive")
    private boolean active;
    private Long clientId;

    public AccountDto(Long id, String number, String type, Double initialAmount, boolean active, Long clientId) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.initialAmount = initialAmount;
        this.active = active;
        this.clientId = clientId;
    }
}
