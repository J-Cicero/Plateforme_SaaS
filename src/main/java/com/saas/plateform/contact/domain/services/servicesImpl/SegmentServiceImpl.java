package com.saas.plateform.contact.domain.services.servicesImpl;

import com.saas.plateform.contact.domain.services.SegmentService;
import com.saas.plateform.contact.domain.models.Segment;
import com.saas.plateform.contact.infrastructure.repositories.SegmentRepository;
import com.saas.plateform.contact.application.dtos.requests.SegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.SegmentResponse;
import com.saas.plateform.contact.application.mappers.SegmentMapper;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.Shared.security.user.infrastructure.repositories.UserRepository;
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
public class SegmentServiceImpl implements SegmentService {

    private final SegmentRepository segmentRepository;
    private final UserRepository userRepository;
    private final SegmentMapper segmentMapper;

    @Override
    public SegmentResponse createSegment(SegmentRequest request, UUID creatorTrackingId) {
        User creator = userRepository.findByTrackingId(creatorTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with trackingId: " + creatorTrackingId));

        Segment segment = segmentMapper.toEntity(request);
        segment.setCreator(creator);
        Segment savedSegment = segmentRepository.save(segment);
        return segmentMapper.toResponse(savedSegment);
    }

    @Override
    @Transactional(readOnly = true)
    public SegmentResponse getSegmentByTrackingId(UUID trackingId) {
        Segment segment = segmentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + trackingId));
        return segmentMapper.toResponse(segment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SegmentResponse> getAllSegments() {
        return segmentRepository.findAll().stream()
                .map(segmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SegmentResponse> getSegmentsByCreator(UUID creatorTrackingId) {
        User creator = userRepository.findByTrackingId(creatorTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with trackingId: " + creatorTrackingId));
        
        return segmentRepository.findByCreator(creator).stream()
                .map(segmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SegmentResponse> getDynamicSegments() {
        return segmentRepository.findByDynamic(true).stream()
                .map(segmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SegmentResponse updateSegment(UUID trackingId, SegmentRequest request) {
        Segment segment = segmentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + trackingId));

        segmentMapper.updateEntityFromRequest(request, segment);
        Segment updatedSegment = segmentRepository.save(segment);
        return segmentMapper.toResponse(updatedSegment);
    }

    @Override
    public void deleteSegment(UUID trackingId) {
        Segment segment = segmentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + trackingId));
        segmentRepository.delete(segment);
    }
}
