package com.saas.plateform.contact.application.mappers;

import com.saas.plateform.contact.domain.models.Contact;
import com.saas.plateform.contact.application.dtos.requests.ContactRequest;
import com.saas.plateform.contact.application.dtos.responses.ContactResponse;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toEntity(ContactRequest request) {
        return Contact.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .company(request.getCompany())
                .position(request.getPosition())
                .status(request.getStatus())
                .city(request.getCity())
                .country(request.getCountry())
                .archived(false)
                .build();
    }

    public ContactResponse toResponse(Contact contact) {
        return ContactResponse.builder()
                .trackingId(contact.getTrackingId())
                .email(contact.getEmail())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phone(contact.getPhone())
                .company(contact.getCompany())
                .position(contact.getPosition())
                .status(contact.getStatus())
                .city(contact.getCity())
                .country(contact.getCountry())
                .archived(contact.getArchived())
                .ownerTrackingId(contact.getOwner() != null ? contact.getOwner().getTrackingId() : null)
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(ContactRequest request, Contact contact) {
        if (request.getEmail() != null) contact.setEmail(request.getEmail());
        if (request.getFirstName() != null) contact.setFirstName(request.getFirstName());
        if (request.getLastName() != null) contact.setLastName(request.getLastName());
        if (request.getPhone() != null) contact.setPhone(request.getPhone());
        if (request.getCompany() != null) contact.setCompany(request.getCompany());
        if (request.getPosition() != null) contact.setPosition(request.getPosition());
        if (request.getStatus() != null) contact.setStatus(request.getStatus());
        if (request.getCity() != null) contact.setCity(request.getCity());
        if (request.getCountry() != null) contact.setCountry(request.getCountry());
    }
}
