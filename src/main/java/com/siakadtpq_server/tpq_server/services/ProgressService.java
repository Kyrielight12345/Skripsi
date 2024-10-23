package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Progress;
import com.siakadtpq_server.tpq_server.repositories.ProgressRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ProgressService {

    private ProgressRepository progressRepository;

    public List<Progress> getAll() {
        return progressRepository.findAll();
    }

    public Progress getById(Integer id) {
        return progressRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found!!!"));
    }

    public Progress create(Progress progress) {
        return progressRepository.save(progress);
    }

    public Progress update(Integer id, Progress progress) {
        getById(id);
        progress.setId(id);
        return progressRepository.save(progress);
    }

    public Progress delete(Integer id) {
        Progress progress = getById(id);
        progressRepository.delete(progress);
        return progress;
    }

}