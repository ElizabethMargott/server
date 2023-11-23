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
    public ResponseEntity<List<TaskListDto>> getAllTaskListsForCurrentUser() {
        UserModel currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TaskListDto> taskLists = taskListService.getAllTaskListsForUser(currentUser);
        return ResponseEntity.ok(taskLists);
    }

    @PostMapping
    public ResponseEntity<TaskListDto> createTaskList(@RequestBody TaskListDto taskListDto) {
        UserModel currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TaskListDto savedTaskList = taskListService.createOrUpdateTaskList(taskListDto, currentUser);
        return ResponseEntity.ok(savedTaskList);
    }

    // Similar endpoints for GET by ID, PUT, DELETE...
}
