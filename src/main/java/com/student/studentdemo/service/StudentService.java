package com.student.studentdemo.service;

import com.github.tennaito.rsql.jpa.JpaCriteriaCountQueryVisitor;
import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import com.student.studentdemo.model.Student;
import com.student.studentdemo.repository.StudentRepository;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private EntityManager entityManager;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> searchByQuery(String queryString) {
        RSQLVisitor<CriteriaQuery<Student>, EntityManager> visitor = new JpaCriteriaQueryVisitor<>();
        CriteriaQuery<Student> query;
        query = getCriteriaQuery(queryString, visitor);
        List<Student> resultList = entityManager.createQuery(query).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return Collections.emptyList();
        }
        return resultList;
    }

    public Long countByQuery(String queryString) {
        RSQLVisitor<CriteriaQuery<Long>, EntityManager> visitor = new JpaCriteriaCountQueryVisitor<Student>();
        CriteriaQuery<Long> query;
        query = getCriteriaQuery(queryString, visitor);

        return entityManager.createQuery(query).getSingleResult();
    }

    private <T> CriteriaQuery<T> getCriteriaQuery(String queryString, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor) {
        Node rootNode;
        CriteriaQuery<T> query;
        try {
            rootNode = new RSQLParser().parse(queryString);
            query = rootNode.accept(visitor, entityManager);
        } catch (Exception e) {
//                log.error("An error happened while executing RSQL query", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return query;
    }
}




//    public List<Student> getAllUsers(Specification<Student> spec) {
//        List<Student> result = (List<Student>) studentRepository.findAll();
//
//        if (result.size() > 0) {
//            return result;
//        } else {
//            return new ArrayList<Student>();
//        }
//    }
//}
