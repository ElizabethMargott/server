package com.example.demo.services;

import com.example.demo.dto.KanbanColumnDto;
import com.example.demo.dto.NoteDto;
import com.example.demo.models.KanbanColumn;
import com.example.demo.models.NoteModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.INoteRepository;
import com.example.demo.repositories.KanbanColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KanbanService {
    @Autowired
    private KanbanColumnRepository kanbanColumnRepository;

    @Autowired
    private INoteRepository noteRepository;

    @Autowired
    private UserService userService;

    public List<KanbanColumnDto> getAllColumns() {
        return kanbanColumnRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public KanbanColumnDto getColumnById(Long id) {
        KanbanColumn column = kanbanColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id " + id));
        return convertToDto(column);
    }

    @Transactional
    public KanbanColumnDto createColumn(KanbanColumnDto columnDto) {
        KanbanColumn column = convertToEntity(columnDto);
        column = kanbanColumnRepository.save(column);
        return convertToDto(column);
    }

    @Transactional
    public KanbanColumnDto updateColumn(Long id, KanbanColumnDto columnDto) {
        KanbanColumn column = kanbanColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id " + id));
        column.setName(columnDto.getName());
        column = kanbanColumnRepository.save(column);
        return convertToDto(column);
    }

    @Transactional
    public void deleteColumn(Long id) {
        kanbanColumnRepository.deleteById(id);
    }

    @Transactional
    public NoteDto createNote(Long columnId, NoteDto noteDto) {
        UserModel currentUser = userService.getCurrentUser();
        KanbanColumn column = kanbanColumnRepository.findById(columnId)
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id " + columnId));
        NoteModel note = new NoteModel();
        // Configura la nota con los datos del DTO y la columna correspondiente
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());
        note.setContent(noteDto.getContent());
        note.setUser(currentUser);
        note.setKanbanColumn(column);
        // Guarda la nota y conviértela a DTO para devolverla
        note = noteRepository.save(note);
        return convertNoteToDto(note);
    }

    @Transactional
    public NoteDto updateNote(Long noteId, NoteDto noteDto) {
        NoteModel note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + noteId));
        // Actualiza la nota con los datos del DTO
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setModificationDate(LocalDateTime.now());
        // Guarda la nota y conviértela a DTO para devolverla
        note = noteRepository.save(note);
        return convertNoteToDto(note);
    }

    @Transactional
    public void deleteNote(Long noteId) {
        // Verifica si la nota existe antes de intentar eliminarla
        if (!noteRepository.existsById(noteId)) {
            throw new EntityNotFoundException("Note not found with id " + noteId);
        }
        noteRepository.deleteById(noteId);
    }

    @Transactional
    public void moveNoteToColumn(Long noteId, Long columnId) {
        NoteModel note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + noteId));
        KanbanColumn column = kanbanColumnRepository.findById(columnId)
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id " + columnId));
        // Asigna la nota a la nueva columna y guarda los cambios
        note.setKanbanColumn(column);
        noteRepository.save(note);
    }

    private KanbanColumnDto convertToDto(KanbanColumn column) {
        KanbanColumnDto dto = new KanbanColumnDto();
        dto.setId(column.getId());
        dto.setName(column.getName());
        // Añadir la conversión para listas de notas y tareas si es necesario
        return dto;
    }

    private KanbanColumn convertToEntity(KanbanColumnDto dto) {
        KanbanColumn column = new KanbanColumn();
        // Si el DTO ya tiene ID, entonces estamos actualizando una columna existente
        if (dto.getId() != null) {
            column.setId(dto.getId());
        }
        column.setName(dto.getName());
        // Añadir más campos del DTO si son necesarios
        return column;
    }

    private NoteDto convertNoteToDto(NoteModel note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setDescription(note.getDescription());
        noteDto.setContent(note.getContent());
        noteDto.setUserId(note.getUser().getId());
        noteDto.setCreationDate(note.getCreationDate());
        noteDto.setModificationDate(note.getModificationDate());
        return noteDto;
    }
}
