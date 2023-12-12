package com.example.demo.controllers;

import com.example.demo.dto.TaskListDto;
import com.example.demo.models.UserModel;
import com.example.demo.services.TaskListService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasklists")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<TaskListDto>> getTasklistByCurrentUser() {
        UserModel currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<TaskListDto> taskListDtos = taskListService.getAllTaskListsByUser(currentUser);
        return ResponseEntity.ok(taskListDtos);
    }

    @PostMapping
    public ResponseEntity<TaskListDto> createTaskList(@RequestBody TaskListDto taskListDto) {
        UserModel currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TaskListDto savedTaskListDto = taskListService.createOrUpdateTaskList(taskListDto, currentUser);
        return ResponseEntity.ok(savedTaskListDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskListDto> updateTaskList(
            @PathVariable Long id, @RequestBody TaskListDto taskListDto) {
        UserModel currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        taskListDto.setId(id);
        TaskListDto updatedTaskListDto = taskListService.createOrUpdateTaskList(taskListDto, currentUser);
        return ResponseEntity.ok(updatedTaskListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable Long id) {
        taskListService.deleteTaskList(id);
        return ResponseEntity.ok().build();
    }
}
