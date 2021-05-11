package com.example.SpringBootReactiveMongoDB;

import com.example.SpringBootReactiveMongoDB.controller.StudentController;
import com.example.SpringBootReactiveMongoDB.dto.StudentDto;
import com.example.SpringBootReactiveMongoDB.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

@RunWith(SpringRunner.class)
@WebFluxTest(StudentController.class)
class SpringBootReactiveMongoDbApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private StudentService studentService;

    @Test
    public void itShouldSaveStudent() {
        /* Given */
        StudentDto studentDto = new StudentDto("1", "Michael", "michael@mail.com", 27);
        Mono<StudentDto> studentDtoMono = Mono.just(studentDto);
        /* When */
        when(studentService.saveStudent(studentDtoMono)).thenReturn(studentDtoMono);
        /* Then */
        webTestClient.post().uri("/v1/students")
                .body(Mono.just(studentDtoMono), StudentDto.class).exchange().expectStatus().isOk();
    }

    @Test
    public void itShouldGetStudents() {
        /* Given */
        StudentDto studentDto1 = new StudentDto("1", "Michael", "michael@mail.com", 27);
        StudentDto studentDto2 = new StudentDto("2", "Marie", "marie@mail.com", 26);
        Flux<StudentDto> studentDtoFlux = Flux.just(studentDto1, studentDto2);
        /* When */
        when(studentService.getStudents()).thenReturn(studentDtoFlux);
        /* Then */
        Flux<StudentDto> responseBody = webTestClient.get().uri("/v1/students").exchange()
                .expectStatus().isOk().returnResult(StudentDto.class).getResponseBody();
        StepVerifier.create(responseBody).expectSubscription()
                .expectNext(studentDto1).expectNext(studentDto2).verifyComplete();
    }

    @Test
    public void itShouldGetStudent() {
        /* Given */
        StudentDto studentDto = new StudentDto("1", "Michael", "michael@mail.com", 27);
        Mono<StudentDto> studentDtoMono = Mono.just(studentDto);
        /* When */
        when(studentService.getStudent(any(String.class))).thenReturn(studentDtoMono);
        /* Then */
        Flux<StudentDto> responseBody = webTestClient.get().uri("/v1/student/1").exchange()
                .returnResult(StudentDto.class).getResponseBody();
        StepVerifier.create(responseBody).expectSubscription()
                .expectNextMatches(s -> s.getName().equals(studentDto.getName())).verifyComplete();
    }

    @Test
    public void itShouldUpdateStudent() {
        /* Given */
        StudentDto studentDto = new StudentDto("1", "Michael", "michael@mail.com", 27);
        Mono<StudentDto> studentDtoMono = Mono.just(studentDto);
        /* When */
        when(studentService.updateStudent(studentDtoMono, any(String.class))).thenReturn(studentDtoMono);
        /* Then */
        webTestClient.put().uri("/v1/students/update/1")
                .body(Mono.just(studentDtoMono), StudentDto.class).exchange().expectStatus().isOk();
    }

    @Test
    public void itShouldDeleteStudent() {
        /* Given */
        /* When */
        given(studentService.deleteStudent(any(String.class))).willReturn(Mono.empty());
        /* Then */
        webTestClient.delete().uri("/v1/students/delete/1").exchange().expectStatus().isOk();
    }
}
