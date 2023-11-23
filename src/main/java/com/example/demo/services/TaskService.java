package com.example.demo.services;

import com.example.demo.dto.TaskDto;
import com.example.demo.models.TaskListModel;
import com.example.demo.models.TaskModel;
import com.example.demo.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        TaskModel task = taskRepository.findById(id).orElseThrow();
        return convertToDto(task);
    }

    public List<TaskDto> getTasksByTaskListId(Long taskListId) {
        List<TaskModel> tasks = taskRepository.findByTaskList_Id(taskListId);
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TaskDto createOrUpdateTask(TaskDto taskDto, TaskListModel taskList) {
        TaskModel task = new TaskModel();
        task.setId(taskDto.getId());
        task.setContent(taskDto.getContent());
        task.setIs_completed(taskDto.getIsCompleted());
        task.setTaskList(taskList);

        task = taskRepository.save(task);
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
        dto.setCreatedAt(task.getCreated_at());
        dto.setUpdatedAt(task.getUpdated_at());
        return dto;
    }
}
