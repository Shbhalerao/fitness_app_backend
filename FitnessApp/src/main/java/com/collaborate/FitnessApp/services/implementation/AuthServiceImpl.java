package com.collaborate.FitnessApp.services.implementation;

import com.collaborate.FitnessApp.domain.dto.responses.AuthUserResponse;
import com.collaborate.FitnessApp.domain.entities.Client;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.entities.FitnessCenter;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.repository.ClientRepository;
import com.collaborate.FitnessApp.repository.TrainerRepository;
import com.collaborate.FitnessApp.repository.FitnessCenterRepository;
import com.collaborate.FitnessApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;
    private final FitnessCenterRepository fitnessCenterRepository;

    @Override
    public AuthUserResponse findUserByEmail(String email) {
        Client client = clientRepository.findByEmailId(email).orElse(null);
        if (client != null) {
            return new AuthUserResponse(client.getEmailId(), client.getPassword(), client.getRole());
        }
        Trainer trainer = trainerRepository.findByEmailId(email).orElse(null);
        if (trainer != null) {
            return new AuthUserResponse(trainer.getEmailId(), trainer.getPassword(), trainer.getRole());
        }
        FitnessCenter center = fitnessCenterRepository.findByEmailId(email).orElse(null);
        if (center != null) {
            return new AuthUserResponse(center.getEmailId(), center.getPassword(), center.getRole());
        }
        throw new ResourceNotFoundException("No user found with email: " + email);
    }
}

