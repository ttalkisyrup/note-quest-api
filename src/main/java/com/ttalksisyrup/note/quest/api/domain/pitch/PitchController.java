package com.ttalksisyrup.note.quest.api.domain.pitch;

import com.ttalksisyrup.note.quest.api.common.model.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/pitches", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PitchController {
    private final PitchService pitchService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<?> pitchNames = pitchService.getAllPitches();
        return ResponseDto.ok(pitchNames);
    }

    @GetMapping("/quiz")
    public ResponseEntity<?> getPitchNameRandomQuiz(
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        List<?> pitchQuiz = pitchService.getPitchNameQuiz(size);
        return ResponseDto.ok(pitchQuiz);
    }
}
