package com.devsu.hackerearth.backend.client.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String dni;
    private String name;
    private String password;
    private String gender;
    private int age;
    private String address;
    private String phone;
    @JsonProperty("isActive")
    private boolean active;

    public ClientDto(Long id, String dni, String name, String password, String gender, int age, String address, String phone, boolean active) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.active = active;
    }
}
