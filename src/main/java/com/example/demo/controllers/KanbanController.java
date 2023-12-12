package com.example.demo.controllers;

import com.example.demo.dto.KanbanColumnDto;
import com.example.demo.dto.NoteDto;
import com.example.demo.services.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kanban")
public class KanbanController {
    private final KanbanService kanbanService;

    @Autowired
    public KanbanController(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    @GetMapping("/columns")
    public ResponseEntity<List<KanbanColumnDto>> getAllColumns() {
        return ResponseEntity.ok(kanbanService.getAllColumns());
    }

    @PostMapping("/columns")
    public ResponseEntity<KanbanColumnDto> createColumn(@RequestBody KanbanColumnDto columnDto) {
        return ResponseEntity.ok(kanbanService.createColumn(columnDto));
    }

    @PutMapping("/columns/{id}")
    public ResponseEntity<KanbanColumnDto> updateColumn(@PathVariable Long id, @RequestBody KanbanColumnDto columnDto) {
        return ResponseEntity.ok(kanbanService.updateColumn(id, columnDto));
    }

    @DeleteMapping("/columns/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        kanbanService.deleteColumn(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteDto> createNote(@RequestParam Long columnId, @RequestBody NoteDto noteDto) {
        return ResponseEntity.ok(kanbanService.createNote(columnId, noteDto));
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id, @RequestBody NoteDto noteDto) {
        return ResponseEntity.ok(kanbanService.updateNote(id, noteDto));
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        kanbanService.deleteNote(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notes/{noteId}/move/{columnId}")
    public ResponseEntity<Void> moveNoteToColumn(@PathVariable Long noteId, @PathVariable Long columnId) {
        kanbanService.moveNoteToColumn(noteId, columnId);
        return ResponseEntity.ok().build();
    }

    // Aquí puedes agregar endpoints adicionales para las listas de tareas si es necesario
}
