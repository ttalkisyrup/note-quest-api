package com.ttalksisyrup.note.quest.api.domain.pitch.service;

import com.ttalksisyrup.note.quest.api.domain.pitch.entity.Pitch;
import com.ttalksisyrup.note.quest.api.domain.pitch.repository.PitchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PitchService {
    private final PitchRepository pitchRepository;

    public List<Pitch> getAllPitches() {
        return pitchRepository.findAll();
    }

    public long getPitchesCount() {
        return pitchRepository.count();
    }

    public List<Pitch> getPitchNameQuiz(int size) {
        long quantity = pitchRepository.count();
        List<Integer> indexList = new ArrayList<>();

        while (indexList.size() < size) {
            int randomIdx = (int) (Math.random() * quantity);

            if (indexList.contains(randomIdx)) continue;
            indexList.add(randomIdx);
        }

        return indexList.stream()
                .map(index -> {
                    List<Pitch> pitches = pitchRepository.findAll(PageRequest.of(index, 1)).getContent();
                    return pitches.get(0);
                })
                .toList();
    }
}
