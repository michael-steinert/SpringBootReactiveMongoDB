package com.example.SpringBootReactiveMongoDB.repository;

import com.example.SpringBootReactiveMongoDB.dto.StudentDto;
import com.example.SpringBootReactiveMongoDB.entity.Student;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
    Flux<StudentDto> findByAgeBetween(Range<Integer> ageRange);
}
