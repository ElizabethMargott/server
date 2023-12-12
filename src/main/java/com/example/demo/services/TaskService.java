package com.example.demo.services;

import com.example.demo.dto.TaskDto;
import com.example.demo.models.TaskListModel;
import com.example.demo.models.TaskModel;
import com.example.demo.repositories.ITaskListRepository;
import com.example.demo.repositories.ITaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ITaskListRepository taskListRepository; // Agregar esto

    public TaskService(ITaskRepository taskRepository, ITaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository; // Inicializarlo
    }

    public List<TaskDto> getTasksByTaskListId(Long taskListId) {
        return taskRepository.findByTaskList_Id(taskListId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        TaskModel task = taskRepository.findById(id).orElseThrow();
        return convertToDto(task);
    }

    public TaskDto createOrUpdateTask(TaskDto taskDto) {
        TaskModel task;
    
        if (taskDto.getId() != null) {
            task = taskRepository.findById(taskDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        } else {
            task = new TaskModel();
        }

        task.setContent(taskDto.getContent());
        task.setIs_completed(taskDto.getIsCompleted());
    
        // Buscar y asignar la TaskListModel correspondiente
        TaskListModel taskList = taskListRepository.findById(taskDto.getTaskListId())
               .orElseThrow(() -> new EntityNotFoundException("Task list not found"));
        task.setTaskList(taskList);
    
        // Guardar en la base de datos
        task = taskRepository.save(task);
    
        // Convertir de nuevo a DTO para la respuesta
        return convertToDto(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDto convertToDto(TaskModel task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setContent(task.getContent());
        dto.setIsCompleted(task.getIs_completed());
        dto.setTaskListId(task.getTaskList().getId());
        dto.setCreationDate(task.getCreationDate());
        dto.setModificationDate(task.getModificationDate());
        return dto;
    }
}
