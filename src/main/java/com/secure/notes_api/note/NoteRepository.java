package com.secure.notes_api.note;

import org.springframework.data.jpa.repository.JpaRepository;
import com.secure.notes_api.user.User;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByOwner(User owner);

    Optional<Note> findByIdAndOwner(Long id, User owner);
}