package com.example.demo.controllers;

import com.example.demo.controllers.UserController.MessageResponse;
import com.example.demo.dto.NoteDto;
import com.example.demo.models.UserModel;
import com.example.demo.services.NoteService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getNotesByCurrentUser() {
        UserModel currentUser = userService.getCurrentUser();
        
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        List<NoteDto> notesDto = noteService.getNotesByUserDto(currentUser);
        return ResponseEntity.ok(notesDto);
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody @Valid NoteDto noteDto) {
        UserModel currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        NoteDto savedNoteDto = noteService.saveNote(noteDto, currentUser);
        return ResponseEntity.ok(savedNoteDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id) {
        NoteDto noteDto = noteService.getNoteById(id);
        return ResponseEntity.ok(noteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNoteById(
            @PathVariable Long id, @RequestBody @Valid NoteDto noteDto) {
        NoteDto updatedNoteDto = noteService.updateNoteById(id, noteDto);
        return ResponseEntity.ok(updatedNoteDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{noteId}/attachments")
    public ResponseEntity<?> addAttachmentToNote(
        @PathVariable Long noteId, 
        @RequestParam("file") MultipartFile file) {
        try {
            noteService.addAttachmentToNote(noteId, file);
            return ResponseEntity.ok(new com.example.demo.exceptions.MessageResponse("Attachment added successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new com.example.demo.exceptions.MessageResponse("Error adding attachment: " + e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new com.example.demo.exceptions.MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/kanban")
    public ResponseEntity<List<NoteDto>> getNotesForKanban() {
        List<NoteDto> notes = noteService.getNotesForKanban();
        return ResponseEntity.ok(notes);
    }
}
