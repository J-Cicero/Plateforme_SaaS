package com.saas.plateform.contact.application.mappers;

import com.saas.plateform.contact.domain.models.Tag;
import com.saas.plateform.contact.application.dtos.requests.TagRequest;
import com.saas.plateform.contact.application.dtos.responses.TagResponse;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag toEntity(TagRequest request) {
        return Tag.builder()
                .lastName(request.getLastName())
                .color(request.getColor())
                .description(request.getDescription())
                .build();
    }

    public TagResponse toResponse(Tag tag) {
        return TagResponse.builder()
                .trackingId(tag.getTrackingId())
                .lastName(tag.getLastName())
                .color(tag.getColor())
                .description(tag.getDescription())
                .createdAt(tag.getCreatedAt())
                .build();
    }

    public void updateEntityFromRequest(TagRequest request, Tag tag) {
        if (request.getLastName() != null) tag.setLastName(request.getLastName());
        if (request.getColor() != null) tag.setColor(request.getColor());
        if (request.getDescription() != null) tag.setDescription(request.getDescription());
    }
}
