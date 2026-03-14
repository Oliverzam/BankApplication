package com.devsu.hackerearth.backend.client.service;
import java.util.List;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
public interface ClientService {
    List<ClientDto> getAll();
    ClientDto getById(Long id);
    ClientDto create(ClientDto dto);
    ClientDto update(ClientDto dto);
    ClientDto partialUpdate(Long id, PartialClientDto dto);
    void deleteById(Long id);
}
