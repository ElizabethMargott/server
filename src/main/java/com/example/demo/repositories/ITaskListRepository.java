package com.example.demo.repositories;

import com.example.demo.models.TaskListModel;
import com.example.demo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskListRepository extends JpaRepository<TaskListModel, Long> {
    List<TaskListModel> findByUser(UserModel user);

    // List<TaskListModel> findByKanbanColumnId(Long columnId);
}
