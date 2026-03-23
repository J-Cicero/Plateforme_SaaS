package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.application.dtos.requests.CampaignAudienceRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignAudienceResponse;
import com.saas.plateform.campaign.application.mappers.CampaignAudienceMapper;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignAudience;
import com.saas.plateform.campaign.domain.services.CampaignAudienceService;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignAudienceRepository;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import com.saas.plateform.contact.domain.models.Segment;
import com.saas.plateform.contact.infrastructure.repositories.SegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignAudienceServiceImpl implements CampaignAudienceService {

    private final CampaignRepository campaignRepository;
    private final CampaignAudienceRepository campaignAudienceRepository;
    private final SegmentRepository segmentRepository;
    private final CampaignAudienceMapper campaignAudienceMapper;

    @Override
    public CampaignAudienceResponse addAudience(Long campaignId, CampaignAudienceRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        Segment segment = segmentRepository.findById(request.getSegmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Segment not found with id: " + request.getSegmentId()));

        CampaignAudience audience = campaignAudienceMapper.toEntity(request);
        audience.setCampaign(campaign);
        audience.setSegment(segment);

        CampaignAudience saved = campaignAudienceRepository.save(audience);
        return campaignAudienceMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignAudienceResponse get(Long id) {
        CampaignAudience audience = campaignAudienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CampaignAudience not found with id: " + id));
        return campaignAudienceMapper.toResponse(audience);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignAudienceResponse> getByCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        return campaignAudienceRepository.findByCampaign(campaign).stream()
                .map(campaignAudienceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignAudienceResponse update(Long id, CampaignAudienceRequest request) {
        CampaignAudience audience = campaignAudienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CampaignAudience not found with id: " + id));

        if (request.getSegmentId() != null) {
            Segment segment = segmentRepository.findById(request.getSegmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Segment not found with id: " + request.getSegmentId()));
            audience.setSegment(segment);
        }

        campaignAudienceMapper.updateEntityFromRequest(request, audience);
        CampaignAudience updated = campaignAudienceRepository.save(audience);
        return campaignAudienceMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        CampaignAudience audience = campaignAudienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CampaignAudience not found with id: " + id));
        campaignAudienceRepository.delete(audience);
    }
}
