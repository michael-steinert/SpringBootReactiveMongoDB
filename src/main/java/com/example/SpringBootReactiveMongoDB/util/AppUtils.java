package com.example.SpringBootReactiveMongoDB.util;

import com.example.SpringBootReactiveMongoDB.dto.StudentDto;
import com.example.SpringBootReactiveMongoDB.entity.Student;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static StudentDto entityToDTO(Student student) {
        StudentDto studentDTO = new StudentDto();
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    public static Student dtoToEntity(StudentDto studentDto) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        return student;
    }
}
