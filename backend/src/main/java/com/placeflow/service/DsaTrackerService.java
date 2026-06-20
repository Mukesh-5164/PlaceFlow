package com.placeflow.service;

import com.placeflow.dto.DsaTrackerDTO;
import com.placeflow.entity.DsaTracker;
import com.placeflow.exception.ResourceNotFoundException;
import com.placeflow.repository.DsaTrackerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DsaTrackerService {

    private final DsaTrackerRepository dsaTrackerRepository;

    public DsaTrackerService(DsaTrackerRepository dsaTrackerRepository) {
        this.dsaTrackerRepository = dsaTrackerRepository;
    }

    public List<DsaTrackerDTO> getAllTopics() {
        return dsaTrackerRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public DsaTrackerDTO getTopicById(Long id) {
        DsaTracker topic = dsaTrackerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DSA Topic", "id", id));
        return toDTO(topic);
    }

    public DsaTrackerDTO createTopic(DsaTrackerDTO dto) {
        DsaTracker topic = toEntity(dto);
        return toDTO(dsaTrackerRepository.save(topic));
    }

    public DsaTrackerDTO updateTopic(Long id, DsaTrackerDTO dto) {
        DsaTracker existing = dsaTrackerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DSA Topic", "id", id));

        existing.setTopicName(dto.getTopicName());
        existing.setProgressPercentage(dto.getProgressPercentage());
        existing.setLastPracticedDate(dto.getLastPracticedDate());

        return toDTO(dsaTrackerRepository.save(existing));
    }

    public void deleteTopic(Long id) {
        if (!dsaTrackerRepository.existsById(id)) {
            throw new ResourceNotFoundException("DSA Topic", "id", id);
        }
        dsaTrackerRepository.deleteById(id);
    }

    public Double getAverageProgress() {
        Double avg = dsaTrackerRepository.findAverageProgress();
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    private DsaTrackerDTO toDTO(DsaTracker topic) {
        DsaTrackerDTO dto = new DsaTrackerDTO();
        dto.setId(topic.getId());
        dto.setTopicName(topic.getTopicName());
        dto.setProgressPercentage(topic.getProgressPercentage());
        dto.setLastPracticedDate(topic.getLastPracticedDate());
        return dto;
    }

    private DsaTracker toEntity(DsaTrackerDTO dto) {
        DsaTracker topic = new DsaTracker();
        topic.setTopicName(dto.getTopicName());
        topic.setProgressPercentage(dto.getProgressPercentage());
        topic.setLastPracticedDate(dto.getLastPracticedDate());
        return topic;
    }
}
