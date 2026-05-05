package com.example.front.repository;

import com.example.front.model.Courses;
import com.example.front.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    List<Courses> findByIdIn(List<Integer> ids);
}