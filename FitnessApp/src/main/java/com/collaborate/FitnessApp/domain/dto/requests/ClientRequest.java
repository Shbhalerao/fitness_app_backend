package com.collaborate.FitnessApp.domain.dto.requests;

import com.collaborate.FitnessApp.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.info.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
public class ClientRequest {

    private Long id;

    @NotBlank(message = "Email id can not be blank")
    @Email
    private String emailId;

    @NotBlank(message = "Contact no can not be blank")
    private String contactNo;

    @NotBlank(message = "First name can not be blank")
    private String firstName;

    @NotBlank(message = "Last name can not be blank")
    private String lastName;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private Long trainerId;

    @NotBlank(message = "Role can not be blank")
    private Role role;

}
