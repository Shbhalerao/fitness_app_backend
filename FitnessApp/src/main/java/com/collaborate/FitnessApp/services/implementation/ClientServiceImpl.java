package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.requests.ClientRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientResponse;
import com.collaborate.FitnessApp.domain.entities.Client;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.domain.base.UserContext;
import com.collaborate.FitnessApp.exceptions.BadRequestException;
import com.collaborate.FitnessApp.exceptions.DuplicateResourceException;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.mappers.ClientMapper;
import com.collaborate.FitnessApp.repository.ClientRepository;
import com.collaborate.FitnessApp.repository.TrainerRepository;
import com.collaborate.FitnessApp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, TrainerRepository trainerRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.trainerRepository = trainerRepository;
    }


    @Override
    public ClientResponse register(ClientRequest clientRequest) {
        if(clientRepository.existsByEmailId(clientRequest.getEmailId())){
            throw new DuplicateResourceException("Email Id already exists : "+ clientRequest.getEmailId());
        }
        if (clientRepository.existsByContactNo(clientRequest.getContactNo())){
            throw  new DuplicateResourceException("Contact No already exists : "+ clientRequest.getContactNo());
        }
        Optional<Trainer> trainer = trainerRepository.findById(clientRequest.getTrainerId());
        Client client = clientMapper.toEntity(clientRequest, trainer.orElse(null));
        client.setCreatedBy(UserContext.getAuditField());
        client.setUpdatedBy(UserContext.getAuditField());
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    @Override
    public ClientResponse getById(Long id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new ResourceNotFoundException("Client not found for id : "+id);
        }
        return clientMapper.toResponse(client.get());
    }

    @Override
    public ClientResponse getByEmailId(String emailId){
        Optional<Client> client = clientRepository.findByEmailId(emailId);
        if(client.isEmpty()){
            throw new ResourceNotFoundException("Client not found for email : "+emailId);
        }
        return clientMapper.toResponse(client.get());
    }

    @Override
    public ClientResponse getByContactNo(String contactNo){
        Optional<Client> client = clientRepository.findByContactNo(contactNo);
        if(client.isEmpty()){
            throw new ResourceNotFoundException("Client not found for contact : "+contactNo);
        }
        return clientMapper.toResponse(client.get());
    }

    @Override
    public List<ClientResponse> getByStatus(String status){
        List<Client> clientList = clientRepository.findByStatus(status);
        if(clientList.isEmpty()){
            throw new ResourceNotFoundException("Client not found for status : "+status);
        }
        return clientMapper.toResponseList(clientList);
    }

    @Override
    public ClientResponse update(ClientRequest clientRequest){
        if(clientRequest.getId() == null){
            throw new BadRequestException("Please Provide Id to update");
        }
        Optional<Client> client = clientRepository.findById(clientRequest.getId());
        if (client.isEmpty()){
            throw new ResourceNotFoundException("No such client present for id : "+clientRequest.getId());
        }
        Optional<Trainer> trainer = trainerRepository.findById(clientRequest.getTrainerId());
        client.get().setUpdatedBy(UserContext.getAuditField());
        Client saved = clientRepository.save(clientMapper.toEntity(clientRequest, trainer.get()));
        return clientMapper.toResponse(saved);
    }

    @Override
    public boolean updateStatusByEmailId(String status, String emailId){
        if(!Status.isExactStatus(status)){
            throw new BadRequestException("Invalid status : "+status);
        }
        return clientRepository.disableByEmailById(status, emailId);
        //Also disable ClientDetails after this if thes status is disabled
    }

    @Override
    public boolean updateStatusByContactNo(String status, String contactNo){
        if(!Status.isExactStatus(status)){
            throw new BadRequestException("Invalid status : "+status);
        }
        return clientRepository.disableByContactNo(status, contactNo);
        //Also disable ClientDetails after this
    }

    @Override
    public void delete(Long id){
        clientRepository.deleteById(id);
        //Also delete ClientDetails after this
    }

    @Override
    public Page<ClientResponse> getClients(List<Status> statuses, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Client> clientPage;
        if (statuses == null || statuses.isEmpty()) {
            clientPage = clientRepository.findAll(pageable);
        } else {
            for (Status s : statuses) {
                if (!Status.isExactStatus(s.name())) {
                    throw new BadRequestException("Invalid status: " + s);
                }
            }
            clientPage = clientRepository.findAllByStatusIn(statuses, pageable);
            if (clientPage.isEmpty()) {
                throw new ResourceNotFoundException("No records found for status: " + statuses);
            }
        }
        if (clientPage.isEmpty()) {
            throw new ResourceNotFoundException("No records found");
        }
        return clientPage.map(clientMapper::toResponse);
    }
}
