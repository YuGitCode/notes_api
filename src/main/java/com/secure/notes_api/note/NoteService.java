package com.secure.notes_api.note;

import org.springframework.stereotype.Service;
import java.util.List;
import com.secure.notes_api.user.User;
import com.secure.notes_api.user.UserService;


@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public Note createNote(String title, String content) {
        User owner = userService.getCurrentUser();        // stamp with current user
        Note note = new Note(title, content, owner);
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        User owner = userService.getCurrentUser();
        return noteRepository.findByOwner(owner);          // only THIS user's notes
    }

    public Note getNote(Long id) {
        User owner = userService.getCurrentUser();
        return noteRepository.findByIdAndOwner(id, owner)  // only if owned by this user
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }
}