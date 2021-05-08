package com.example.SpringBootReactiveMongoDB.service;

import com.example.SpringBootReactiveMongoDB.dto.StudentDto;
import com.example.SpringBootReactiveMongoDB.repository.StudentRepository;
import com.example.SpringBootReactiveMongoDB.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class StudentService {
    private StudentRepository studentRepository;

    public Flux<StudentDto> getStudents() {
        return studentRepository.findAll().map(student -> AppUtils.entityToDTO(student));
    }

    public Mono<StudentDto> getStudent(String id) {
        return studentRepository.findById(id).map(student -> AppUtils.entityToDTO(student));
    }

    public Flux<StudentDto> getStudentsInRangeOfAge(int min, int max) {
        return studentRepository.findByAgeBetween(Range.closed(min, max));
    }

    public Mono<StudentDto> saveStudent(Mono<StudentDto> studentDtoMono) {
        return studentDtoMono.map(studentDto -> AppUtils.dtoToEntity(studentDto))
                .flatMap(student -> studentRepository.save(student))
                .map(student -> AppUtils.entityToDTO(student));
    }

    public Mono<StudentDto> updateStudent(Mono<StudentDto> studentDtoMono, String id) {
        return studentRepository.findById(id)
                .flatMap(student -> studentDtoMono.map(studentDto -> AppUtils.dtoToEntity(studentDto))
                        .doOnNext(s -> s.setId(id)))
                .flatMap(s -> studentRepository.save(s))
                .map(s -> AppUtils.entityToDTO(s));
    }

    public Mono<Void> deleteStudent(String id) {
        return studentRepository.deleteById(id);
    }
}
