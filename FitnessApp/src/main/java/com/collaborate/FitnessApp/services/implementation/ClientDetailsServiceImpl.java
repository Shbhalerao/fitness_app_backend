package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.requests.ClientDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientDetailsResponse;
import com.collaborate.FitnessApp.domain.entities.Client;
import com.collaborate.FitnessApp.domain.entities.ClientDetails;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.exceptions.BadRequestException;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.mappers.ClientDetailsMapper;
import com.collaborate.FitnessApp.repository.ClientDetailsRepository;
import com.collaborate.FitnessApp.repository.ClientRepository;
import com.collaborate.FitnessApp.services.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;
    private final ClientRepository clientRepository;
    private final ClientDetailsMapper clientDetailsMapper;;

    @Autowired
    public ClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository, ClientRepository clientRepository, ClientDetailsMapper clientDetailsMapper) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.clientRepository = clientRepository;
        this.clientDetailsMapper = clientDetailsMapper;
    }

    @Override
    public ClientDetailsResponse create(ClientDetailsRequest clientDetailsRequest){
        Optional<Client> client = clientRepository.findById(clientDetailsRequest.getId());
        if(client.isEmpty()){
            throw new ResourceNotFoundException("Invalid Client Id : "+clientDetailsRequest.getClientId());
        }
        ClientDetails clientDetails = clientDetailsMapper.toEntity(clientDetailsRequest, client.get());
        ClientDetails saved = clientDetailsRepository.save(clientDetails);
        return clientDetailsMapper.toResponse(saved);
    }


    @Override
    public ClientDetailsResponse getByClientId(Long clientId){
        if(!clientRepository.existsById(clientId)){
            throw new BadRequestException("Invalid Client Id : "+ clientId);
        }
        if (clientDetailsRepository.existsByClient_Id(clientId)){
            throw new ResourceNotFoundException("No record found for client Id : "+clientId);
        }
        return clientDetailsMapper.toResponse(clientDetailsRepository.findByClient_Id(clientId).get());
    }

    @Override
    public ClientDetailsResponse getById(Long id){
        if(!clientDetailsRepository.existsById(id)){
            throw new ResourceNotFoundException("Invalid Id : "+ id);
        }
        return clientDetailsMapper.toResponse(clientDetailsRepository.findById(id).get());
    }

    @Override
    public List<ClientDetailsResponse> getAll(){
        List<ClientDetails> list = clientDetailsRepository.findAll();
        return clientDetailsMapper.toResponseList(list);
    }

    @Override
    public Page<ClientDetailsResponse> getByStatus(List<Status> statuses, Pageable pageable) {
        if (statuses == null || statuses.isEmpty()) {
            throw new BadRequestException("Status list cannot be empty");
        }
        Page<ClientDetails> page = clientDetailsRepository.findByStatusIn(statuses, pageable);
        return page.map(clientDetailsMapper::toResponse);
    }

    @Override
    public ClientDetailsResponse update(ClientDetailsRequest clientDetailsRequest){
        Optional<Client> client = clientRepository.findById(clientDetailsRequest.getClientId());
        if (client.isEmpty()){
            throw new BadRequestException("Invalid Client Id : "+clientDetailsRequest.getClientId());
        }
        if(clientDetailsRequest.getId() == null){
            throw new BadRequestException("Null Id, Can not update");
        }
        if(!clientDetailsRepository.existsById(clientDetailsRequest.getId())){
            throw new ResourceNotFoundException("Invalid Id : "+ clientDetailsRequest.getId());
        }
        ClientDetails clientDetails = clientDetailsMapper.toEntity(clientDetailsRequest, client.get());
        ClientDetails updated = clientDetailsRepository.save(clientDetails);
        return clientDetailsMapper.toResponse(updated);
    }

    @Override
    public boolean updateStatusById(String status, Long id){
        if(!Status.isExactStatus(status)){
            throw new BadRequestException("Invalid status : "+status);
        }
        if (!clientDetailsRepository.existsById(id)){
            throw new ResourceNotFoundException("Record not found for Id : "+id);
        }
        return clientDetailsRepository.updateStatusById(status, id);
    }

    @Override
    public boolean updateStatusByClientId(String status, Long clientId){
        if(!Status.isExactStatus(status)){
            throw new BadRequestException("Invalid status : "+status);
        }
        if (clientDetailsRepository.existsByClient_Id(clientId)){
            throw new ResourceNotFoundException("Record not found for Client Id : "+clientId);
        }
        return clientDetailsRepository.updateStatusByClient_Id(status, clientId);
    }

    @Override
    public void delete(Long id){
        clientDetailsRepository.deleteById(id);
    }

    @Override
    public Page<ClientDetailsResponse> getClients(List<Status> statuses, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ClientDetails> detailsPage;
        if (statuses == null || statuses.isEmpty()) {
            detailsPage = clientDetailsRepository.findAll(pageable);
        } else {
            for (Status s : statuses) {
                if (!Status.isExactStatus(s.name())) {
                    throw new BadRequestException("Invalid status: " + s);
                }
            }
            detailsPage = clientDetailsRepository.findByStatusIn(statuses, pageable);
            if (detailsPage.isEmpty()) {
                throw new ResourceNotFoundException("No records found for status: " + statuses);
            }
        }
        if (detailsPage.isEmpty()) {
            throw new ResourceNotFoundException("No records found");
        }
        return detailsPage.map(clientDetailsMapper::toResponse);
    }

}
