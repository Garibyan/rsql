package com.student.studentdemo.repository;

import com.student.studentdemo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;

public interface StudentRepository extends JpaRepository<Student, Long>{
}
