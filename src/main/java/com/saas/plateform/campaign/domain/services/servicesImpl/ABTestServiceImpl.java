package com.saas.plateform.campaign.domain.services.servicesImpl;

import com.saas.plateform.Shared.security.exceptions.ResourceNotFoundException;
import com.saas.plateform.campaign.application.dtos.requests.ABTestRequest;
import com.saas.plateform.campaign.application.dtos.responses.ABTestResponse;
import com.saas.plateform.campaign.application.mappers.ABTestMapper;
import com.saas.plateform.campaign.domain.models.ABTest;
import com.saas.plateform.campaign.domain.models.Campaign;
import com.saas.plateform.campaign.domain.services.ABTestService;
import com.saas.plateform.campaign.infrastructure.repositories.ABTestRepository;
import com.saas.plateform.campaign.infrastructure.repositories.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ABTestServiceImpl implements ABTestService {

    private final CampaignRepository campaignRepository;
    private final ABTestRepository abTestRepository;
    private final ABTestMapper abTestMapper;

    @Override
    public ABTestResponse create(Long campaignId, ABTestRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        ABTest abTest = abTestMapper.toEntity(request);
        abTest.setCampaign(campaign);
        ABTest saved = abTestRepository.save(abTest);
        return abTestMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ABTestResponse get(Long id) {
        ABTest abTest = abTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ABTest not found with id: " + id));
        return abTestMapper.toResponse(abTest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ABTestResponse> getByCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));

        return abTestRepository.findByCampaign(campaign).stream()
                .map(abTestMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ABTestResponse update(Long id, ABTestRequest request) {
        ABTest abTest = abTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ABTest not found with id: " + id));

        abTestMapper.updateEntityFromRequest(request, abTest);
        ABTest updated = abTestRepository.save(abTest);
        return abTestMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        ABTest abTest = abTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ABTest not found with id: " + id));
        abTestRepository.delete(abTest);
    }
}
