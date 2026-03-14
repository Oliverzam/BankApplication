package com.devsu.hackerearth.backend.client.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.devsu.hackerearth.backend.client.exception.ResourceNotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    private ClientDto toDto(Client c) {
        return new ClientDto(c.getId(), c.getDni(), c.getName(), c.getPassword(), c.getGender(), c.getAge(), c.getAddress(), c.getPhone(), c.isActive());
    }
    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }
    @Override
    public ClientDto getById(Long id) {
        return toDto(clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found")));
    }
    @Override
    public ClientDto create(ClientDto dto) {
        Client c = new Client();
        c.setDni(dto.getDni());
        c.setName(dto.getName());
        c.setPassword(dto.getPassword());
        c.setGender(dto.getGender());
        c.setAge(dto.getAge());
        c.setAddress(dto.getAddress());
        c.setPhone(dto.getPhone());
        c.setActive(dto.isActive());
        return toDto(clientRepository.save(c));
    }
    @Override
    public ClientDto update(ClientDto dto) {
        if (dto.getId() == null) {
            throw new ResourceNotFoundException("Client not found");
        }
        Client c = clientRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        c.setDni(dto.getDni());
        c.setName(dto.getName());
        c.setPassword(dto.getPassword());
        c.setGender(dto.getGender());
        c.setAge(dto.getAge());
        c.setAddress(dto.getAddress());
        c.setPhone(dto.getPhone());
        c.setActive(dto.isActive());
        return toDto(clientRepository.save(c));
    }
    @Override
    public ClientDto partialUpdate(Long id, PartialClientDto dto) {
        Client c = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        c.setActive(dto.isActive());
        return toDto(clientRepository.save(c));
    }
    @Override
    public void deleteById(Long id) {
        clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        clientRepository.deleteById(id);
    }
}
