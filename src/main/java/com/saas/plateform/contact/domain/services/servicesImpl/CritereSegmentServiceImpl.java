package com.saas.plateform.contact.domain.services.servicesImpl;

import com.saas.plateform.contact.domain.services.CritereSegmentService;
import com.saas.plateform.contact.domain.models.CritereSegment;
import com.saas.plateform.contact.domain.models.Segment;
import com.saas.plateform.contact.infrastructure.repositories.CritereSegmentRepository;
import com.saas.plateform.contact.infrastructure.repositories.SegmentRepository;
import com.saas.plateform.contact.application.dtos.requests.CritereSegmentRequest;
import com.saas.plateform.contact.application.dtos.responses.CritereSegmentResponse;
import com.saas.plateform.contact.application.mappers.CritereSegmentMapper;
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
public class CritereSegmentServiceImpl implements CritereSegmentService {

    private final CritereSegmentRepository critereRepository;
    private final SegmentRepository segmentRepository;
    private final CritereSegmentMapper critereMapper;

    @Override
    public CritereSegmentResponse createCritere(CritereSegmentRequest request) {
        Segment segment = segmentRepository.findByTrackingId(request.getSegmentTrackingId())
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + request.getSegmentTrackingId()));

        CritereSegment critere = critereMapper.toEntity(request);
        critere.setSegment(segment);
        CritereSegment savedCritere = critereRepository.save(critere);
        return critereMapper.toResponse(savedCritere);
    }

    @Override
    @Transactional(readOnly = true)
    public CritereSegmentResponse getCritereByTrackingId(UUID trackingId) {
        CritereSegment critere = critereRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Critere not found with trackingId: " + trackingId));
        return critereMapper.toResponse(critere);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CritereSegmentResponse> getCriteresBySegment(UUID segmentTrackingId) {
        Segment segment = segmentRepository.findByTrackingId(segmentTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Segment not found with trackingId: " + segmentTrackingId));
        
        return critereRepository.findBySegmentOrderByOrderIndexAsc(segment).stream()
                .map(critereMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CritereSegmentResponse updateCritere(UUID trackingId, CritereSegmentRequest request) {
        CritereSegment critere = critereRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Critere not found with trackingId: " + trackingId));

        critereMapper.updateEntityFromRequest(request, critere);
        CritereSegment updatedCritere = critereRepository.save(critere);
        return critereMapper.toResponse(updatedCritere);
    }

    @Override
    public void deleteCritere(UUID trackingId) {
        CritereSegment critere = critereRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Critere not found with trackingId: " + trackingId));
        critereRepository.delete(critere);
    }
}
