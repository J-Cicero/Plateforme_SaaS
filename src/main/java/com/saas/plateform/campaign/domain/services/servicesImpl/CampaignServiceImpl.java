package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.Shared.security.user.domain.models.User;
import com.saas.plateform.Shared.security.user.infrastructure.repositories.UserRepository;
import com.saas.plateform.campaign.application.dtos.requests.CampaignRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignResponse;
import com.saas.plateform.campaign.application.mappers.CampaignMapper;
import com.saas.plateform.campaign.domain.enums.StatutCampaign;
import com.saas.plateform.campaign.domain.enums.TypeCampaign;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.services.CampaignService;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final CampaignMapper campaignMapper;

    @Override
    public CampaignResponse createCampaign(CampaignRequest request, UUID creatorTrackingId) {
        User creator = userRepository.findByTrackingId(creatorTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with trackingId: " + creatorTrackingId));

        Campaign campaign = campaignMapper.toEntity(request);
        campaign.setCreatedBy(creator);
        Campaign saved = campaignRepository.save(campaign);
        return campaignMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignResponse getCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + id));
        return campaignMapper.toResponse(campaign);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getAll() {
        return campaignRepository.findAll().stream()
                .map(campaignMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getByStatus(StatutCampaign status) {
        return campaignRepository.findByStatus(status).stream()
                .map(campaignMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getByType(TypeCampaign type) {
        return campaignRepository.findByType(type).stream()
                .map(campaignMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getByCreator(UUID creatorTrackingId) {
        User creator = userRepository.findByTrackingId(creatorTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with trackingId: " + creatorTrackingId));
        return campaignRepository.findByCreatedBy(creator).stream()
                .map(campaignMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignResponse updateCampaign(Long id, CampaignRequest request) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + id));

        campaignMapper.updateEntityFromRequest(request, campaign);
        Campaign updated = campaignRepository.save(campaign);
        return campaignMapper.toResponse(updated);
    }

    @Override
    public void deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + id));
        campaignRepository.delete(campaign);
    }
}
