package com.example.front.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.front.model.Course;
import com.example.front.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*") // 允許 Angular 預設的 4200 port 存取

public class CourseController {

	@Autowired
    private CourseRepository courseRepository;

	@GetMapping("/list")
	public List<Course> getAllCourses() {
	    return courseRepository.findAll();
	}

    @GetMapping("{id}/image")
    public ResponseEntity<byte[]> getCourseImage(@PathVariable Integer id){
    	Course course = courseRepository.findById(id).orElse(null);

    	if (course != null && course.getImage()!=null) {
    		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(course.getImage());
    	}
    	return ResponseEntity.notFound().build();
    }





}
