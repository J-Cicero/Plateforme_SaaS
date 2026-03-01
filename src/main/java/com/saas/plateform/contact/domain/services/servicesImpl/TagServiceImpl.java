package com.saas.plateform.contact.domain.services.servicesImpl;

import com.saas.plateform.contact.domain.services.TagService;
import com.saas.plateform.contact.domain.models.Tag;
import com.saas.plateform.contact.infrastructure.repositories.TagRepository;
import com.saas.plateform.contact.application.dtos.requests.TagRequest;
import com.saas.plateform.contact.application.dtos.responses.TagResponse;
import com.saas.plateform.contact.application.mappers.TagMapper;
import com.saas.plateform.Shared.security.exceptions.AlreadyExistException;
import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse createTag(TagRequest request) {

        Tag tag = tagMapper.toEntity(request);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toResponse(savedTag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getTagByTrackingId(UUID trackingId) {
        Tag tag = tagRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with trackingId: " + trackingId));
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TagResponse updateTag(UUID trackingId, TagRequest request) {
        Tag tag = tagRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with trackingId: " + trackingId));

        tagMapper.updateEntityFromRequest(request, tag);
        Tag updatedTag = tagRepository.save(tag);
        return tagMapper.toResponse(updatedTag);
    }

    @Override
    public void deleteTag(UUID trackingId) {
        Tag tag = tagRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with trackingId: " + trackingId));
        tagRepository.delete(tag);
    }
}
