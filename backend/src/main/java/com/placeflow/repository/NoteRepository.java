package com.placeflow.repository;

import com.placeflow.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // ── Existing queries ──────────────────────────────
    List<Note> findAllByOrderByCreatedAtDesc();

    // ── User-scoped queries ───────────────────────────
    List<Note> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Note> findByIdAndUserId(Long id, Long userId);
    long countByUserId(Long userId);
}
