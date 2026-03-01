package com.saas.plateform.contact.application.dtos.requests;

import com.saas.plateform.contact.domain.enums.StatutContact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "phone Number is required")
    private String phone;

    @NotBlank(message = "if this contact hasn't contact write --None--")
    private String company;

    @NotBlank(message = " if company is null we can also write --None-- or --Chief--")
    private String position;

    private StatutContact status;
    private String city;
    private String country;
}
