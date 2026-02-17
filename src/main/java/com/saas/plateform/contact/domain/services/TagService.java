package com.saas.plateform.contact.domain.services;

import com.saas.plateform.contact.application.dtos.requests.TagRequest;
import com.saas.plateform.contact.application.dtos.responses.TagResponse;

import java.util.List;
import java.util.UUID;

public interface TagService {
    
    TagResponse createTag(TagRequest request);
    
    TagResponse getTagByTrackingId(UUID trackingId);
    
    List<TagResponse> getAllTags();
    
    TagResponse updateTag(UUID trackingId, TagRequest request);
    
    void deleteTag(UUID trackingId);
}
