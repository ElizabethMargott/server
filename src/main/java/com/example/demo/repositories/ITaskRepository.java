package com.example.demo.repositories;

import com.example.demo.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findByTaskList_Id(Long taskListId);
}
