package com.example.demo.services;

import com.example.demo.dto.TaskListDto;
import com.example.demo.models.TaskListModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.ITaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskListService {

    @Autowired
    private ITaskListRepository taskListRepository;

    public List<TaskListDto> getAllTaskListsForUser(UserModel user) {
        return taskListRepository.findByUser_Id(user.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskListDto getTaskListById(Long id) {
        TaskListModel taskList = taskListRepository.findById(id).orElseThrow(() -> new RuntimeException("TaskList not found"));
        return convertToDto(taskList);
    }
    public TaskListDto createOrUpdateTaskList(TaskListDto taskListDto, UserModel user) {
        TaskListModel taskList;
        if (taskListDto.getId() != null) {
            taskList = taskListRepository.findById(taskListDto.getId())
                    .orElse(new TaskListModel());
        } else {
            taskList = new TaskListModel();
        }
        
        taskList.setUser(user);
        taskList.setTitle(taskListDto.getTitle());
        // Set other fields from taskListDto to taskList...

        taskList = taskListRepository.save(taskList);
        return convertToDto(taskList);
    }

    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }

    private TaskListDto convertToDto(TaskListModel taskList) {
        TaskListDto dto = new TaskListDto();
        dto.setId(taskList.getId());
        dto.setTitle(taskList.getTitle());
        dto.setCreatedAt(taskList.getCreated_at());
        dto.setUpdatedAt(taskList.getUpdated_at());
        return dto;
    }
}
