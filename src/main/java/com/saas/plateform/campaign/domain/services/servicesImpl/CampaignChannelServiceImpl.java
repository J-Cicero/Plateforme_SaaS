package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.application.dtos.requests.CampaignChannelRequest;
import com.saas.plateform.campaign.application.dtos.responses.CampaignChannelResponse;
import com.saas.plateform.campaign.application.mappers.CampaignChannelMapper;
import com.saas.plateform.campaign.domain.enums.TypeCanal;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.models.CampaignChannel;
import com.saas.plateform.campaign.domain.services.CampaignChannelService;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignChannelRepository;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignChannelServiceImpl implements CampaignChannelService {

    private final CampaignRepository campaignRepository;
    private final CampaignChannelRepository campaignChannelRepository;
    private final CampaignChannelMapper campaignChannelMapper;

    @Override
    public CampaignChannelResponse create(Long campaignId, CampaignChannelRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        CampaignChannel channel = campaignChannelMapper.toEntity(request);
        channel.setCampaign(campaign);
        CampaignChannel saved = campaignChannelRepository.save(channel);
        return campaignChannelMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignChannelResponse get(Long id) {
        CampaignChannel channel = campaignChannelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CampaignChannel not found with id: " + id));
        return campaignChannelMapper.toResponse(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignChannelResponse> getByCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        return campaignChannelRepository.findByCampaign(campaign).stream()
                .map(campaignChannelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignChannelResponse> getByCampaignAndType(Long campaignId, TypeCanal type) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        return campaignChannelRepository.findByCampaignAndTypeCanal(campaign, type).stream()
                .map(campaignChannelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignChannelResponse update(Long id, CampaignChannelRequest request) {
        CampaignChannel channel = campaignChannelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CampaignChannel not found with id: " + id));

        campaignChannelMapper.updateEntityFromRequest(request, channel);
        CampaignChannel updated = campaignChannelRepository.save(channel);
        return campaignChannelMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        CampaignChannel channel = campaignChannelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CampaignChannel not found with id: " + id));
        campaignChannelRepository.delete(channel);
    }
}
