package com.example.SpringBootReactiveMongoDB.controller;

import com.example.SpringBootReactiveMongoDB.dto.StudentDto;
import com.example.SpringBootReactiveMongoDB.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/students")
public class StudentController {
    private StudentService studentService;

    @GetMapping
    public Flux<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Mono<StudentDto> getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/students-in-range-of-age")
    public Flux<StudentDto> getStudentsInRangeOfAge(@RequestParam("min") int min, @RequestParam("max") int max) {
        return studentService.getStudentsInRangeOfAge(min, max);
    }

    @PostMapping
    public Mono<StudentDto> saveStudent(@RequestBody Mono<StudentDto> studentDtoMono) {
        return studentService.saveStudent(studentDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<StudentDto> updateStudent(@RequestBody Mono<StudentDto> studentDtoMono, @PathVariable String id) {
        return studentService.updateStudent(studentDtoMono, id);
    }

    @DeleteMapping("delete/{id}")
    public Mono<Void> deleteStudent(@PathVariable String id) {
        return studentService.deleteStudent(id);
    }
}
