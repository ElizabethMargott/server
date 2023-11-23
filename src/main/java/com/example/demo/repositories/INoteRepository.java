package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.NoteModel;
import com.example.demo.models.UserModel;

@Repository
public interface INoteRepository extends JpaRepository<NoteModel, Long> {

    List<NoteModel> findByUser(UserModel user);

}
