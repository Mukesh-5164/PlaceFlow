package com.placeflow.service;

import com.placeflow.dto.NoteDTO;
import com.placeflow.entity.Note;
import com.placeflow.exception.ResourceNotFoundException;
import com.placeflow.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public NoteDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        return toDTO(note);
    }

    public NoteDTO createNote(NoteDTO dto) {
        Note note = toEntity(dto);
        return toDTO(noteRepository.save(note));
    }

    public NoteDTO updateNote(Long id, NoteDTO dto) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());

        return toDTO(noteRepository.save(existing));
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note", "id", id);
        }
        noteRepository.deleteById(id);
    }

    private NoteDTO toDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        return dto;
    }

    private Note toEntity(NoteDTO dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return note;
    }
}
