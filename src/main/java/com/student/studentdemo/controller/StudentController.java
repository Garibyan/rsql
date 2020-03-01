package com.student.studentdemo.controller;


import com.student.studentdemo.model.Classroom;
import com.student.studentdemo.model.Student;
import com.student.studentdemo.service.ClassroomService;
import com.student.studentdemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final ClassroomService classroomService;

    public StudentController(StudentService studentService, ClassroomService classroomService) {
        this.studentService = studentService;
        this.classroomService = classroomService;
    }

//    @GetMapping("/students")
//    public List<Student> getAllStudents() {
//        List<Student> listOfStudents = studentService.getAllUsers();
//
//        return listOfStudents;
//    }

//    @GetMapping("/students")
//    @ResponseBody
//    public List<Student> findAllByRsql(@RequestParam(value = "search") String search) {
//        Node rootNode = new RSQLParser().parse(search);
//        Specification<Student> spec = rootNode.accept(new CustomRsqlVisitor<>());
//        return studentService.getAllUsers(spec);
//    }
//
@GetMapping(path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public ResponseEntity<List<Student>> query(@RequestParam(value = "search") String query) {
    List<Student> result = null;
    try {
        result = studentService.searchByQuery(query);
    }catch (IllegalArgumentException iae){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }
    return  ResponseEntity.status(HttpStatus.OK)
            .body(result);
}

    @GetMapping("/classrooms")
    public List<Classroom> getAllClassrooms() {
        List<Classroom> listOfClassrooms = classroomService.getAllClassrooms();

        return listOfClassrooms;
    }
}
