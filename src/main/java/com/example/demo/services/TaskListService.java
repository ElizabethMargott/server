package com.example.demo.services;

import com.example.demo.dto.TaskListDto;
import com.example.demo.models.TaskListModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.ITaskListRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskListService {

    @Autowired
    private ITaskListRepository taskListRepository;

    public List<TaskListDto> getAllTaskListsByUser(UserModel user) {
        return taskListRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskListDto getTasklistById(Long id) {
        TaskListModel tasklist = taskListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tasklist not found"));
        return convertToDto(tasklist);
    }

    public TaskListDto createOrUpdateTaskList(TaskListDto taskListDto, UserModel user) {
        TaskListModel taskList;

        if (taskListDto.getId() != null) {
            // Si se proporciona un ID, busca la lista existente y actualiza sus campos
            taskList = taskListRepository.findById(taskListDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Task list not found"));
        } else {
            // Si no hay ID, crea una nueva lista de tareas
            taskList = new TaskListModel();
        }

        // Asignar valores del DTO al modelo
        taskList.setTitle(taskListDto.getTitle());
        taskList.setUser(user);

        // Guardar en la base de datos
        taskList = taskListRepository.save(taskList);

        // Convertir de nuevo a DTO para la respuesta
        return convertToDto(taskList);
    }

    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }

    private TaskListDto convertToDto(TaskListModel taskList) {
        TaskListDto dto = new TaskListDto();
        dto.setId(taskList.getId());
        dto.setTitle(taskList.getTitle());
        dto.setUserId(taskList.getUser().getId());
        dto.setCreationDate(taskList.getCreationDate());
        dto.setModificationDate(taskList.getModificationDate());
        return dto;
    }
}
